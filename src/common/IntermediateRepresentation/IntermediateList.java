package common.IntermediateRepresentation;


public class IntermediateList {

    public static String[] tokenConversion = new String[12];

    private IntermediateNode head;
    private IntermediateNode tail;
    public IntermediateList() {
        tokenConversion[0] = "MEMOP";
        tokenConversion[1] = "LOADI";
        tokenConversion[2] = "ARITHOP";
        tokenConversion[3] = "OUTPUT";
        tokenConversion[4] = "NOP";
        tokenConversion[5] = "CONSTANT";
        tokenConversion[6] = "REGISTER";
        tokenConversion[7] = "COMMA";
        tokenConversion[8] = "INTO";
        tokenConversion[9] = "EOF";
        tokenConversion[10] = "EOL";
        tokenConversion[11] = "REG";
        head = new IntermediateNode(-1,-1, "");
        tail = new IntermediateNode(-1, -1, "");
        head.setNext(tail);
        tail.setPrev(head);

    }

    public void append(IntermediateNode node) {
        node.setNext(tail);
        node.setPrev(tail.getPrev());
        tail.getPrev().setNext(node);
        tail.setPrev(node);

    }

    public void prepend(IntermediateNode node) {
        node.setNext(head.getNext());
        head.getNext().setPrev(node);
        head.setNext(node);
        node.setPrev(head);
    }

    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        IntermediateNode currNode = head.getNext();
        while(currNode != tail && currNode != null) {
            toReturn.append(currNode.toString()).append("\n");
            currNode = currNode.getNext();
        }
        return toReturn.toString();
    }

    public String getILoc() {
        StringBuilder toReturn = new StringBuilder();
        IntermediateNode currNode = head.getNext();
        while(currNode != tail && currNode != null) {
            toReturn.append(currNode.getILOCRepresentation()).append("\n");
            currNode = currNode.getNext();
        }
        return toReturn.toString();
    }

    public String getPRCode() {
        StringBuilder toReturn = new StringBuilder();
        IntermediateNode currNode = head.getNext();
        while(currNode != tail && currNode != null) {
            toReturn.append(currNode.getPRrep()).append("\n");
            currNode = currNode.getNext();
        }
        return toReturn.toString();
    }

    public IntermediateNode getHead() {
        return this.head;
    }

    public IntermediateNode getTail() {
        return this.tail;
    }



}
