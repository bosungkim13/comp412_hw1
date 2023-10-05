import common.IntermediateRepresentation.IntermediateList;
import common.IntermediateRepresentation.IntermediateNode;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Allocator {
    private static final int INVAlID = -1;
    private static final int VALID = Integer.MAX_VALUE;

    private static final int MEMOP = 0;
    private static final int LOADI = 1;
    private static final int ARITHOP = 2;
    private int spillLocation = 32768;

    private IntermediateList renamedList;
    private int numRegisters;
    private int numVirtualRegisters;
    private int markedReg;
    private int maxLive;
    private int[] PRtoNU;
    private int[] VRtoSpillLoc;
    private int[] PRtoVR;
    private int[] VRtoPR;
    private Stack<Integer> registerStack;
    private IntermediateNode currNode;
    private int reservedSpillReg;

    public Allocator(IntermediateList renamedList, int numRegisters, int vrName, int maxLive) {
        this.renamedList = renamedList;
        this.numRegisters = numRegisters;
        this.maxLive = maxLive;
        this.reservedSpillReg = numRegisters - 1;
        this.registerStack = new Stack<>();
        this.VRtoSpillLoc = new int[vrName];
        this.VRtoPR = new int[vrName];
        this.PRtoNU = new int[numRegisters];
        this.PRtoVR = new int[numRegisters];
        this.markedReg = INVAlID;
        this.currNode = renamedList.getHead().getNext();
        for (int i = 0; i < vrName; i++) {
            this.VRtoPR[i] = INVAlID;
        }
        Arrays.setAll(VRtoSpillLoc, a -> INVAlID);
        Arrays.setAll(PRtoVR, a -> INVAlID);
        Arrays.setAll(PRtoNU, a -> INVAlID);

        // reserve last register to handle spill as needed
        if (maxLive <= numRegisters) {
            registerStack.push(reservedSpillReg);
        }
        for (int i = numRegisters - 2; i > -1; i--) {
            registerStack.push(i);
        }
    }

    /**
     * Free pr by pushing to stack and updating mappings
     * @param pr physical register to free
     */
    private void freePR(int pr) {
        VRtoPR[PRtoVR[pr]] = INVAlID;
        PRtoVR[pr] = INVAlID;
        PRtoNU[pr] = INVAlID;
        registerStack.push(pr);
    }

    private int getPR(int vr, int nu) {
        int reg;
        if (registerStack.size() != 0) {
            reg = registerStack.pop();
        } else {
            reg = spill();
        }
        VRtoPR[vr] = reg;
        PRtoVR[reg] = vr;
        PRtoNU[reg] = nu;
        return reg;
    }

    private int spill() {
        int currSpillAdrr;
        int highestNU = -1;
        int spillVR = 0;
        int spillReg = 0;
        for (int i = 0; i < numRegisters - 1; i ++) {
            // don't spill if we are at the current register under consideration
            if (i == markedReg) {
                continue;
            }
            // find farthest away reg
            if (PRtoNU[i] > highestNU) {
                spillReg = i;
                highestNU = PRtoNU[i];
                spillVR = PRtoVR[i];
            }
        }
        if (VRtoSpillLoc[spillVR] == INVAlID) {
            currSpillAdrr = spillLocation;
            VRtoSpillLoc[spillVR] = spillLocation;
            spillLocation += 4;
        } else {
            currSpillAdrr = VRtoSpillLoc[spillVR];
        }

        // cannot use this VR anymore bc it's assigned to a spill loc
        VRtoPR[spillVR] = INVAlID;

        // insert nodes as needed
        IntermediateNode loadINode = new IntermediateNode(Integer.MAX_VALUE, LOADI, "loadI");
        loadINode.setSourceRegister(0, currSpillAdrr);
        loadINode.setPhysicalRegister(1, reservedSpillReg);

        insertBeforeCurrNode(loadINode);

        IntermediateNode storeNode = new IntermediateNode(Integer.MAX_VALUE, MEMOP, "store");
        storeNode.setPhysicalRegister(0, spillReg);
        storeNode.setPhysicalRegister(1, reservedSpillReg);
        insertBeforeCurrNode(storeNode);

        return spillReg;
    }

    private void insertBeforeCurrNode(IntermediateNode node) {
        node.setPrev(this.currNode.getPrev());
        this.currNode.getPrev().setNext(node);
        this.currNode.setPrev(node);
        node.setNext(this.currNode);
    }

    public void allocate() {
        IntermediateNode tailNode = renamedList.getTail();
        while (currNode != tailNode) {
            clearMarkedReg();
            switch(currNode.getOpCode()) {
                // loadI case
                case LOADI: {
                    handleLOADI();
                    break;
                }
                // store and load case
                case MEMOP: {
                    handleMEMOP();
                    break;
                }
                // add, sub, mult, lshift, rshift case
                case ARITHOP: {
                    handleARITHOP();
                    break;
                }
            }
            currNode = currNode.getNext();
        }
    }

    private void handleLOADI() {
        handleDef(Arrays.asList(1));
    }

    private void handleARITHOP() {
        handleUses(Arrays.asList(0, 1));
        handleDef(Arrays.asList(2));
    }

    private void handleMEMOP() {
        if (currNode.getLexeme().equals("store")) {
            handleUses(Arrays.asList(0, 1));
        } else {
            // load case
            handleUses(Arrays.asList(0));
            handleDef(Arrays.asList(1));
        }
    }

    private void handleUses(List<Integer> list) {
        for (int i : list) {
            int pr = VRtoPR[currNode.getVirtualRegister(i)];
            if (pr == INVAlID) {
                pr = getPR(currNode.getVirtualRegister(i), currNode.getNextUse(i));
                currNode.setPhysicalRegister(i, pr);
                restore(currNode.getVirtualRegister(i), pr);
            } else {
                currNode.setPhysicalRegister(i, pr);
                PRtoNU[pr] = currNode.getNextUse(i);
            }
            markedReg = currNode.getPhysicalRegister(i);
        }
        for (int i : list) {
            if (currNode.getNextUse(i) == INVAlID && PRtoVR[currNode.getPhysicalRegister(i)] != INVAlID) {
                freePR(currNode.getPhysicalRegister(i));
            }
        }
        clearMarkedReg();
    }

    private void handleDef(List<Integer> list) {
        for (int i : list) {
            currNode.setPhysicalRegister(i, getPR(currNode.getVirtualRegister(i), currNode.getNextUse(i)));
        }
    }

    private void clearMarkedReg() {
        this.markedReg = INVAlID;
    }

    private void restore(int vr, int pr) {
        IntermediateNode loadINode = new IntermediateNode(Integer.MAX_VALUE, LOADI, "loadI");
        loadINode.setSourceRegister(0, VRtoSpillLoc[vr]);
        loadINode.setPhysicalRegister(1, reservedSpillReg);

        insertBeforeCurrNode(loadINode);

        IntermediateNode loadNode = new IntermediateNode(Integer.MAX_VALUE, MEMOP, "load");
        loadNode.setPhysicalRegister(0, reservedSpillReg);
        loadNode.setPhysicalRegister(1, pr);

        insertBeforeCurrNode(loadNode);
    }

}
