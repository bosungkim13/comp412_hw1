package common;

public class Token {
    private int token;
    private String lexeme;
    private int lineNum;

    public Token(int token, String lexeme, int lineNum){
        this.token = token;
        this.lexeme = lexeme;
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getToken() {
        return token;
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

    public void setToken(int token) {
        this.token = token;
    }
}