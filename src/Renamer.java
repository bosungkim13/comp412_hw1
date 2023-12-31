import common.IntermediateRepresentation.IntermediateList;
import common.IntermediateRepresentation.IntermediateNode;
import common.IntermediateRepresentation.IntermediateStoreNode;

import java.util.ArrayList;
import java.util.Arrays;

public class Renamer {
    private int vrName = 0;
    private int maxSR;
    private IntermediateList IRList;
    private IntermediateNode currNode;
    private int SRToVR[];
    private int LU[];
    private int currRegVal;
    private int maxLive;
    private int currLive;

    public Renamer(IntermediateList IRList, int maxSR) {
        this.IRList = IRList;
        this.maxSR = maxSR;
        this.SRToVR = new int[maxSR+1];
        this.LU = new int[maxSR+1];
        this.currNode = IRList.getTail().getPrev();
        for (int i = 0; i < maxSR+1; i++) {
            this.SRToVR[i] = -1;
            this.LU[i] = -1;
        }
        this.maxLive = 0;
        this.currLive = 0;
    }

    public void addVirtualRegisters() {
        IntermediateNode head = this.IRList.getHead();
        while (this.currNode != head) {
            this.handleIntermediateNode();
            this.currNode = this.currNode.getPrev();
        }
    }

    private void handleIntermediateNode() {
        int numOps = 3;
        int currOpcode = currNode.getOpCode();
        // handle definitions
        for (int i = 0; i < numOps; i ++) {
            currRegVal = currNode.getSourceRegister(i);
            if (currRegVal > maxSR || currRegVal < 0) {
                continue;
            }
            if (currOpcode == Parser.MEMOP && i == 1 && !(currNode instanceof IntermediateStoreNode)) {
                this.handleDef(i);
            } else if (currOpcode == Parser.LOADI && i == 1) {
                this.handleDef(i);
            } else if (currOpcode == Parser.ARITHOP && i == 2) {
                this.handleDef(i);
            }
        }
        for (int i = 0; i < numOps; i ++) {
            currRegVal = currNode.getSourceRegister(i);
            if (currRegVal > maxSR || currRegVal < 0) {
                continue;
            }
            if (currOpcode == Parser.MEMOP) {
                if (currNode instanceof IntermediateStoreNode) {
                    this.handleUse(i);
                } else if (i == 0) {
                    this.handleUse(i);
                }
            } else if (currOpcode == Parser.ARITHOP && i < 2) {
                this.handleUse(i);
            }

        }

    }

    private void handleDef(int i) {
        if (SRToVR[this.currRegVal] == -1) {
            SRToVR[this.currRegVal] = vrName;
            vrName ++;
        }
        currNode.setVirtualRegister(i, SRToVR[this.currRegVal]);
        currNode.setNextUseRegister(i, LU[this.currRegVal]);
        // reset because we have encountered a definition
        SRToVR[this.currRegVal] = -1;
        LU[this.currRegVal] = -1;
    }

    private void handleUse(int i) {
        if (SRToVR[this.currRegVal] == -1) {
            SRToVR[this.currRegVal] = vrName;
            vrName ++;
            currLive ++;
            if (currLive > maxLive) {
                maxLive = currLive;
            }
        }
        currNode.setVirtualRegister(i, SRToVR[this.currRegVal]);
        currNode.setNextUseRegister(i, LU[currRegVal]);
        LU[currRegVal] = currNode.getLineNum();
    }

    public int getMaxLive() {
        return this.maxLive;
    }

    public int getVrName() {
        return this.vrName;
    }

}
