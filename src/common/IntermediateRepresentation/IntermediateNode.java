package common.IntermediateRepresentation;


import java.util.Arrays;

public class IntermediateNode {

    private IntermediateNode next;
    private IntermediateNode prev;
    private int lineNum;
    private static final int USE = 1;
    private static final int DEF = 2;
    private int opCode;
    private String lexeme;
    private int operandArray[];
    private int[] useDef;
    public IntermediateNode(int lineNum, int opCode, String lexeme) {
        this.lineNum = lineNum;
        this.opCode = opCode;
        this.operandArray = new int[12];
        this.lexeme = lexeme;
        Arrays.setAll(operandArray, i -> -1);
        this.useDef = new int[3];
        Arrays.setAll(useDef, i -> -1);
    }

    public void setUse(int operandNum) {
        // set the use
        this.useDef[operandNum] = USE;
    }

    public void setDef(int operandNum) {
        this.useDef[operandNum] = DEF;
    }


    public IntermediateNode getPrev() {
        return prev;
    }
    public IntermediateNode getNext() {
        return next;
    }

    public void setNext(IntermediateNode n) {
        next = n;
    }

    public void setPrev(IntermediateNode p) {
        prev = p;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public int[] getOperandArray() {
        return operandArray;
    }

    public int getOperand(int position) {
        return operandArray[position];
    }

    public void setSourceRegister(int operandNum, int value) {
        this.operandArray[operandNum * 4] = value;
    }

    // 0 indexed
    public int getSourceRegister(int operandNum) {
        return this.operandArray[operandNum * 4];
    }

    public void setVirtualRegister(int operandNum, int value) {
        this.operandArray[(operandNum * 4) + 1] = value;
    }

    public void setNextUseRegister(int operandNum, int value) {
        this.operandArray[(operandNum * 4) + 3] = value;
    }
    public void setOperandArray(int position, int value) {
        this.operandArray[position] = value;
    }

    public int getVirtualRegister(int operandNum) {
        return this.operandArray[(operandNum * 4) + 1];
    }

    @Override
    public String toString() {
        return "[" + lineNum + " ," + IntermediateList.tokenConversion[opCode] + " " + genterateOperandStringArray() + "]";
    }

    public String getILOCRepresentation() {
        if (this.opCode == 2) {
            return this.lexeme + "  r" + this.getVirtualRegister(0) + ", r"
                    + this.getVirtualRegister(1)
                    + " => r" + this.getVirtualRegister(2);
        } else if (this.opCode == 1) {
            return this.lexeme + " " + this.getSourceRegister(0) + " => r" + this.getVirtualRegister(1);
        } else if (this.opCode == 0) {
            return this.lexeme + " r" + this.getVirtualRegister(0) + " => r" + this.getVirtualRegister(1);
        } else if (this.opCode == 3){
            return this.lexeme + " " + this.getSourceRegister(0);
        } else {
            return this.lexeme;
        }
    }

    private String genterateOperandStringArray() {
        String sb = "[";
        for (int i = 0; i < operandArray.length; i++) {
            if (operandArray[i] == -1) {
                sb += "null, ";
            } else {
                sb += operandArray[i] + ", ";
            }
        }
        return sb + "]";
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public void setPhysicalRegister(int operand, int physicalReg) {
        this.operandArray[(4 * operand) + 2] = physicalReg;
    }

    public int getnextUse(int operand) {
        return this.operandArray[(4 * operand) + 3];
    }

    public int getPhysicalRegister(int i) {
        return this.operandArray[(4 * i) + 2];
    }

    public String getPRrep() {
        if (this.opCode == 2) {
            return this.lexeme + "  r" + this.getPhysicalRegister(0) + ", r"
                    + this.getPhysicalRegister(1)
                    + " => r" + this.getPhysicalRegister(2);
        } else if (this.opCode == 1) {
            return this.lexeme + " " + this.getSourceRegister(0) + " => r" + this.getPhysicalRegister(1);
        } else if (this.opCode == 0) {
            return this.lexeme + " r" + this.getPhysicalRegister(0) + " => r" + this.getPhysicalRegister(1);
        } else if (this.opCode == 3){
            return this.lexeme + " " + this.getSourceRegister(0);
        } else {
            return this.lexeme;
        }
    }
}