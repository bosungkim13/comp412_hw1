import common.ASCIIConstants;
import common.Token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
    private String lexeme;
    private int lineNum;
    private int token;
    private String filename;
    private String currLine;
    private BufferedReader reader;
    private char currChar;
    private int currPos;
    private boolean debug = false;
    public Scanner(String filename) {
        this.filename = filename;
    }

    public void skipWhitespace() {
        while (currPos < currLine.length() && isWhitespace()) {
            if (debug) {
                System.out.println("skipWhitespace: " + "skipped whitespace at" + this.currPos);
            }
            currPos = currPos + 1;
            try {
                currChar = currLine.charAt(currPos);
            } catch (Exception e) {
                System.out.println("skipWhitespace: Reached end of line");
            }
        }
    }

    public void skipUntilWhitespace() {
        while (this.currPos < this.currLine.length() && !isWhitespace()) {
            if (debug) {
                System.out.println("skipUntilWhitespace: " + "skipped " + this.currChar);
            }
            this.currPos = this.currPos + 1;
            try {
                this.currChar = this.currLine.charAt(this.currPos);
            } catch (Exception e) {
                if (debug) {
                    System.out.println("skipUntilWhitespace: Reached end of line");
                }
            }

        }
    }
    public void skipUntilWhitespace(int dummy) {
        while (this.currPos < this.currLine.length() && !isWhitespace() && this.currChar != ASCIIConstants.comma) {
            if (debug) {
                System.out.println("skipUntilWhitespace: " + "skipped " + this.currChar);
            }
            this.currPos = this.currPos + 1;
            try {
                this.currChar = this.currLine.charAt(this.currPos);
            } catch (Exception e) {
                if (debug) {
                    System.out.println("skipUntilWhitespace: Reached end of line");
                }
            }

        }
    }

    public boolean isWhitespace() {
        return this.currChar == ASCIIConstants.tab || this.currChar == ASCIIConstants.space;
    }

    public boolean isNextWhitespace() {
        int next = this.peekNext();
        return next == ASCIIConstants.tab || next == ASCIIConstants.space;
    }

    public boolean moveNextChar() {
        if (this.currPos < this.currLine.length()){
            this.currPos = this.currPos + 1;
            try {
                this.currChar = this.currLine.charAt(this.currPos);
            } catch (Exception e) {
                if (debug) {
                    System.out.println("DEBUG: unable to move to next character");
                }
                return false;
            }

            return true;
        }
        return false;
    }

    public char peekNext() {
        char returnChar;
        if (currPos + 1 <= currLine.length()){
            try{
                returnChar = currLine.charAt(currPos + 1);
                return returnChar;
            } catch (Exception e) {
                System.out.println("ERROR: Could not peek next character of current line" + this.currLine);
            }

        }
        return 0;
    }

    public boolean continueScan(Token scannedToken) {
        return scannedToken.getOpCode() != -1 && scannedToken.getOpCode() != 10;
    }

    public void scanFile() {
        this.skipWhitespace();
        Token prevToken = new Token(-1, "null", -1);
        while (prevToken.getOpCode() != 9) {
            prevToken = scanNextWord();
            // if new line or carriage we move to new line
            if (prevToken.getOpCode() == 10) {
                nextLine();
            } else if (prevToken.getOpCode() == 9) {
                System.out.println("Scanning complete");
                break;
            }
            if (prevToken.getOpCode() != -1) {
                System.out.println("SCANNED TOKEN: Line " + prevToken.getLineNum() + " " + prevToken.getLexeme() + " " + prevToken.getOpCode());
            }
        }
    }

    public Token scanNextWord(){
        if (this.currLine == null) {
            this.token = 9;
            this.lexeme = "EOF";
            return new Token(this.token, this.lexeme, -1);
        }

        if (this.currPos == this.currLine.length() || this.currLine.isEmpty()) {
            this.token = 10;
            this.lexeme = "EOL";
            return new Token(this.token, this.lexeme, this.lineNum);
        }
        this.skipWhitespace();
        this.currChar = this.currLine.charAt(currPos);
        boolean success = false;
        if (this.currChar == ASCIIConstants.a) {
            success = this.handleA();
        } else if (this.currChar == ASCIIConstants.m) {
            success = this.handleM();
        } else if (this.currChar == ASCIIConstants.o) {
            success = this.handleO();
        } else if (this.currChar == ASCIIConstants.n) {
            success = this.handleN();
        } else if (this.currChar == ASCIIConstants.equals) {
            success = this.handleEquals();
        } else if (this.currChar == ASCIIConstants.r) {
            success = this.handleR();
        } else if (this.currChar == ASCIIConstants.s) {
            success = this.handleS();
        } else if (this.currChar == ASCIIConstants.l) {
            success = this.handleL();
        } else if (this.currChar == ASCIIConstants.slash) {
            success = this.handleSlash();
        } else if (this.currChar <='9' && this.currChar >= '0') {
            success = this.handleDigit(5);
        } else if (this.currChar == ASCIIConstants.comma){
            success = this.handleComma();
        } else if (this.currChar == ASCIIConstants.carriageReturn || this.currChar == ASCIIConstants.newLine) {
            // new line!
            this.lexeme = "EOL";
            this.token = 10;
            success = true;
        }
        else {
            success = false;
            this.lexeme = "Invalid starting character of ILOC language";
            this.token = -1;
            System.out.println("ERROR " + this.lineNum + ": " + "'" + this.currChar + "'" + " is not a valid starting character of ILOC");
//            skipToNextToken();
        }
        Token returnToken = new Token(this.token, this.lexeme, this.lineNum);
        if (!success) {
            nextLine();
        }
        return returnToken;
    }

    private boolean handleSlash() {
        if (moveNextChar() && this.currChar == ASCIIConstants.slash) {
            this.lexeme = "Slash";
            this.token = 10;
            return true;
        } else {
            System.out.println("ERROR: '/' followed by " + this.currChar + " is not part of the ILOC language");
            return false;
        }
    }

    public void skipToNextToken() {
        // function used when we did not find a complete word and we need to skip to the start of the next
        // possible token
        if (debug) {
            System.out.println("skipToNextWord: called on char " + this.currChar + " at position " + this.currPos);
        }
        skipUntilWhitespace();
        skipWhitespace();
    }

    public void skipToNextToken(int dummy) {
        // function used when we did not find a complete word and we need to skip to the start of the next
        // possible token
        if (debug) {
            System.out.println("skipToNextWord: called on char " + this.currChar + " at position " + this.currPos);
        }
        skipUntilWhitespace(1);
        skipWhitespace();
    }
    public boolean openFile() {
        try {
            this.reader = new BufferedReader(new FileReader(this.filename));
            this.currLine = this.reader.readLine();
            this.lineNum = 1;
            this.currPos = 0;
            this.currChar = this.currLine.charAt(currPos);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Could not open file" + this.filename);
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("ERROR: Could not read file" + this.filename);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean finishValidWord(String lexeme, int token) {
        boolean isWhitespace = isNextWhitespace();
//        if (isWhitespace || this.currPos == this.currLine.length() - 1) {
        this.lexeme = lexeme;
        this.token = token;
//        skipToNextToken();
        if (isWhitespace) {
            // skip whitespaces
            skipToNextToken();
        } else {
            // move to next character
            moveNextChar();
        }
        return true;
    }

    public boolean finishValidNumber(String lexeme, int token) {
        // change this so you can't have a comma after a constant
//        if (isWhitespace() || this.currChar == ASCIIConstants.comma || this.currPos == this.currLine.length() - 1) {
            this.lexeme = lexeme;
            this.token = token;
            // case with comma we dont want to skip
            if (isWhitespace()) {
                skipToNextToken();
            }
            return true;
//            if (token == 11) {
//                skipToNextToken(1);
//            } else {
//                skipToNextToken();
//            }
//            return true;
//        } else {
//            // invalid digit
//            this.lexeme = "Invalid character" + this.currChar + " following number " + lexeme;
//            this.token = -1;
////            skipToNextToken();
//            nextLine();
//            return false;
//        }

    }

    public boolean finishInvalidWord(char prevChar) {
        System.out.println("ERROR " + this.lineNum + ": Invalid character followed by '" + prevChar + "'");
        this.lexeme = "Invalid character followed by '" + prevChar + "'";
        this.token = -1;
//        skipToNextToken();
//        nextLine();
        return false;
    }
    public boolean handleA() {
        if (moveNextChar() && this.currChar == ASCIIConstants.d) {
            if (moveNextChar() && this.currChar == ASCIIConstants.d) {
                return finishValidWord("add", 2);
            } else {
                return finishInvalidWord('d');
            }
        } else{
            return finishInvalidWord('a');
        }
    }

    public boolean handleM() {
        if (moveNextChar() && this.currChar == ASCIIConstants.u) {
            if (moveNextChar() && this.currChar == ASCIIConstants.l) {
                if (moveNextChar() && this.currChar == ASCIIConstants.t) {
                    return finishValidWord("mult", 2);
                } else {
                    return finishInvalidWord('l');
                }
            } else {
                return finishInvalidWord('u');
            }
        } else {
            return finishInvalidWord('m');
        }
    }

    public boolean handleO() {
        if (moveNextChar() && this.currChar == ASCIIConstants.u) {
            if (moveNextChar() && this.currChar == ASCIIConstants.t) {
                if (moveNextChar() && this.currChar == ASCIIConstants.p) {
                    if (moveNextChar() && this.currChar == ASCIIConstants.u) {
                        if (moveNextChar() && this.currChar == ASCIIConstants.t) {
                            return finishValidWord("output", 3);
                        } else {
                            return finishInvalidWord('u');
                        }
                    } else {
                        return finishInvalidWord('p');
                    }
                } else {
                    return finishInvalidWord('t');
                }
            } else {
                return finishInvalidWord('u');
            }
        } else {
            return  finishInvalidWord('o');
        }
    }

    public boolean handleN() {
        if (moveNextChar() && this.currChar == ASCIIConstants.o) {
            if (moveNextChar() && this.currChar == ASCIIConstants.p) {
                return finishValidWord("nop", 4);
            } else {
                return finishInvalidWord('o');
            }
        } else {
            return finishInvalidWord('n');
        }
    }

    public boolean handleEquals() {
        if (moveNextChar() && this.currChar == ASCIIConstants.rightCarrot) {
            return finishValidWord("=>", 8);
        } else {
            return finishInvalidWord('=');
        }
    }

    public boolean handleR() {
        if (moveNextChar() && this.currChar == ASCIIConstants.s) {
            if (moveNextChar() && this.currChar == ASCIIConstants.h) {
                if (moveNextChar() && this.currChar == ASCIIConstants.i) {
                    if (moveNextChar() && this.currChar == ASCIIConstants.f) {
                        if (moveNextChar() && this.currChar == ASCIIConstants.t) {
                            return finishValidWord("rshift", 2);
                        } else {
                            return finishInvalidWord('f');
                        }
                    } else {
                        return finishInvalidWord('i');
                    }
                } else {
                    return finishInvalidWord('h');
                }
            } else {
                return finishInvalidWord('s');
            }
        } else if (this.currChar <= '9' && this.currChar >= '0') {
            //handle digit
            return handleDigit(11);
        }
        return finishInvalidWord('r');
    }

    public boolean handleDigit(int token) {
        StringBuilder builder = new StringBuilder();
//        if (token == 6) {
//            builder.append('r');
//        }
        while (this.currChar <= '9' && this.currChar >= '0') {
            builder.append(this.currChar);
            if (!moveNextChar()) {
                break;
            }
        }
        return finishValidNumber(builder.toString(), token);
    }

    public boolean handleL() {
        if (moveNextChar() && this.currChar == ASCIIConstants.o) {
            if (moveNextChar() && this.currChar == ASCIIConstants.a) {
                if (moveNextChar() && this.currChar == ASCIIConstants.d) {
                    if (peekNext() == ASCIIConstants.capitalI) {
                        moveNextChar();
                        return finishValidWord("loadI", 1);
                    } else {
                        return finishValidWord("load", 0);
                    }

                } else {
                    return finishInvalidWord('a');
                }
            } else {
                return finishInvalidWord('o');
            }
        } else if (this.currChar == ASCIIConstants.s) {
            if (moveNextChar() && this.currChar == ASCIIConstants.h) {
                if (moveNextChar() && this.currChar == ASCIIConstants.i) {
                    if (moveNextChar() && this.currChar == ASCIIConstants.f) {
                        if (moveNextChar() && this.currChar == ASCIIConstants.t) {
                            return finishValidWord("lshift", 2);
                        } else {
                            return finishInvalidWord('f');
                        }
                    } else {
                        return finishInvalidWord('i');
                    }
                } else {
                    return finishInvalidWord('h');
                }
            } else {
                return finishInvalidWord('s');
            }
        } else {
            return finishInvalidWord('l');
        }
    }

    public boolean handleS() {
        if (moveNextChar() && this.currChar == ASCIIConstants.u) {
            if (moveNextChar() && this.currChar == ASCIIConstants.b) {
                return finishValidWord("sub", 2);
            } else {
                return finishInvalidWord('u');
            }
        } else if (this.currChar == ASCIIConstants.t) {
            if (moveNextChar() && this.currChar == ASCIIConstants.o) {
                if (moveNextChar() && this.currChar == ASCIIConstants.r) {
                    if (moveNextChar() && this.currChar == ASCIIConstants.e) {
                        return finishValidWord("store", 0);
                    } else {
                        return finishInvalidWord('r');
                    }
                } else {
                    return finishInvalidWord('o');
                }
            } else {
                return finishInvalidWord('t');
            }
        } else {
            return finishInvalidWord('s');
        }

    }

    public boolean nextLine() {
        try {
            this.currLine = this.reader.readLine();
            if (this.currLine == null) {
                return false;
            }
            this.currPos = 0;
            this.lineNum += 1;
            try {
                this.currChar = this.currLine.charAt(this.currPos);
            } catch (Exception e) {
                // We new line into an empty line
                this.nextLine();
            }

            this.skipWhitespace();
            return true;
        } catch (IOException e) {
            System.out.println("ERROR " + this.lineNum + ": Faulty file. Cannot read next line");
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean handleComma() {
        return finishValidWord(",", 7);
    }
}