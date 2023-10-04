package common.IntermediateRepresentation;


import java.util.Arrays;

public class IntermediateNode {

    private IntermediateNode next;
    private IntermediateNode prev;
    private int lineNum;
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

    public void setSourceRegister(int operandNum, int value) {
        this.operandArray[operandNum * 4] = value;
    }

    // 0 indexed
    public int getSourceRegister(int operandNum) {
        return this.operandArray[operandNum * 4];
    }

    public int getVirtualRegister(int operandNum) {
        return this.operandArray[(operandNum * 4) + 1];
    }

    @Override
    public String toString() {
        return "[" + lineNum + " ," + IntermediateList.tokenConversion[opCode] + " " + generateOperandStringArray() + "]";
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

    public int getOpCode() {
        return this.opCode;
    }

    private String generateOperandStringArray() {
        StringBuilder sb = new StringBuilder("[");
        for (int elem : operandArray) {
            if (elem == -1) {
                sb.append("null, ");
            } else {
                sb.append(elem).append(", ");
            }
        }
        return sb + "]";
    }
    public void setVirtualRegister(int operandNum, int value) {
        this.operandArray[(operandNum*4)+1] = value;
    }

    public void setPhysicalRegister(int operandNum, int value) {
        this.operandArray[(operandNum*4) + 2] = value;
    }

    public void setNextUseRegister(int operandNum, int value) {
        this.operandArray[(operandNum*4)+3] = value;
    }

    public int getLineNum() {
        return this.lineNum;
    }

    public String getLexeme() {
        return this.lexeme;
    }
}