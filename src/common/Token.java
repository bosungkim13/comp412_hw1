package common;

public class Token {
    private int opCode;
    private String lexeme;
    private int lineNum;

    public Token(int opCode, String lexeme, int lineNum){
        this.opCode = opCode;
        this.lexeme = lexeme;
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getOpCode() {
        return opCode;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}