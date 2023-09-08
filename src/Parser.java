import common.IntermediateRepresentation.IntermediateList;
import common.IntermediateRepresentation.IntermediateNode;
import common.Token;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private Scanner scanner;
    private IntermediateList IRList;
    private Token currToken;
    private int lineNum = 1;
    private int maxSourceReg = 0;
    private int errorCount = 0;
    public static Map<String, Integer> tokenConversion =  new HashMap<>();

    public static final int MEMOP = 0;
    public static final int LOADI = 1;
    public static final int ARITHOP = 2;
    public static final int OUTPUT = 3;
    public static final int NOP = 4;
    public static final int CONSTANT = 5;
    public static final int REGISTER = 6;
    public static final int COMMA = 7;
    public static final int INTO = 8;
    public static final int EOF = 9;
    public static final int EOL = 10;
    public static final int REG = 11;

    public Parser(Scanner scanner) {
        tokenConversion.put("MEMOP", 0);
        tokenConversion.put("LOADI", 1);
        tokenConversion.put("ARITHOP", 2);
        tokenConversion.put("OUTPUT", 3);
        tokenConversion.put("NOP", 4);
        tokenConversion.put("CONSTANT", 5);
        tokenConversion.put("REGISTER", 6);
        tokenConversion.put("COMMA", 7);
        tokenConversion.put("INTO", 8);
        tokenConversion.put("EOF", 9);
        tokenConversion.put("EOL", 10);
        tokenConversion.put("REG", 11);
        this.scanner = scanner;
        this.IRList = new IntermediateList();
    }

    public int parse() {
        currToken = scanner.scanNextWord();
        while (currToken.getOpCode() != EOF) {
            switch (currToken.getOpCode()) {
                case MEMOP:
                    finishMEMOP();
                    break;
                case LOADI:
                    finishLOADI();
                    break;
                case ARITHOP:
                    finishARITHOP();
                    break;
                case OUTPUT:
                    finishOUTPUT();
                    break;
                case NOP:
                    finishNOP();
                    break;
                case EOL:
                    scanner.nextLine();
                default:
                    System.out.println("ERROR " + this.lineNum + ": Invalid ILOC opcode " + currToken.getOpCode());

            }
            // scan next and handle EOF
            currToken = scanner.scanNextWord();
        }
        return errorCount;
    }

    public void printIR() {
        System.out.println(this.IRList);
    }

    public void handleFaultyIR(int currLex, int prevLex, int opcode) {
        errorCount += 1;
        System.out.println("ERROR: There was no " + IntermediateList.tokenConversion[currLex] + " following " + IntermediateList.tokenConversion[prevLex] + " for opCode " + IntermediateList.tokenConversion[opcode]);
        while (this.currToken.getOpCode()!= EOL) {
            this.currToken = scanner.scanNextWord();
        }
    }
    private void finishMEMOP() {
        String lexeme = currToken.getLexeme();
        IntermediateNode newNode = new IntermediateNode(this.lineNum, 0, lexeme);
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == 11) {
            newNode.setSourceRegister(0, Integer.parseInt(currToken.getLexeme()));
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == 8) {
                currToken = scanner.scanNextWord();
                if (currToken.getOpCode() == 11) {
                    newNode.setSourceRegister(1, Integer.parseInt(currToken.getLexeme()));
                    currToken = scanner.scanNextWord();
                    if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                        IRList.append(newNode);
                    } else {
                        handleFaultyIR(10, 11, currToken.getOpCode());
//                        printCustomErrorMsg("There was no EOL operation at the end of the " + lexeme + " operation");
                    }
                } else {
                    handleFaultyIR(11, 8, currToken.getOpCode());
                    // printCustomErrorMsg("There was no Register value at the end in the " + lexeme + " operation");
                }
            } else {
                handleFaultyIR(8, 11, currToken.getOpCode());
                // printCustomErrorMsg("There was no => in the " + lexeme + " operation");
            }
        } else {
            System.out.println("There was no Register value at the start in the " + lexeme + " operation");
        }
    }

    private void finishLOADI() {
        // start with loadI
        IntermediateNode newNode = new IntermediateNode(this.lineNum, 1, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == 5) {
            // constant
            newNode.setSourceRegister(0, Integer.parseInt(currToken.getLexeme()));
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == 8) {
                // into
                currToken = scanner.scanNextWord();
                if (currToken.getOpCode() == 11) {
                    // register
                    newNode.setSourceRegister(1, Integer.parseInt(currToken.getLexeme()));
                    currToken = scanner.scanNextWord();
                    if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                        // eol
                        IRList.append(newNode);
                    } else {
                        handleFaultyIR(10, 11, currToken.getOpCode());
                        //printCustomErrorMsg("There was no EOL operation at the end of the LOADI");
                    }
                } else {
                    handleFaultyIR(11, 8, currToken.getOpCode());
                    // printCustomErrorMsg("There was no register following the into in LOADI");
                }
            } else {
                handleFaultyIR(8, 5, currToken.getOpCode());
                // printCustomErrorMsg("There was no INTO operation following the constant in LOADI");
            }
        } else {
            handleFaultyIR(5, currToken.getOpCode(), currToken.getOpCode());
            // printCustomErrorMsg("There was no constant following the LOADI operation");
        }

    }

    private void finishARITHOP() {
        IntermediateNode newNode = new IntermediateNode(this.lineNum, ARITHOP, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == REGISTER) {
            newNode.setSourceRegister(0, Integer.parseInt(currToken.getLexeme()));
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == COMMA) {
                if (currToken.getOpCode() == REGISTER) {
                    newNode.setSourceRegister(1, Integer.parseInt(currToken.getLexeme()));
                    currToken = scanner.scanNextWord();
                    if (currToken.getOpCode() == INTO) {
                        if (currToken.getOpCode() == REGISTER) {
                            newNode.setSourceRegister(2, Integer.parseInt(currToken.getLexeme()));
                            currToken = scanner.scanNextWord();
                            if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                                IRList.append(newNode);
                            } else {
                                handleFaultyIR(EOL, REGISTER, ARITHOP);
                            }
                        } else {
                            handleFaultyIR(REGISTER, INTO, ARITHOP);
                        }
                    } else {
                        handleFaultyIR(INTO, REGISTER, ARITHOP);
                    }
                } else {
                    handleFaultyIR(REGISTER, COMMA, ARITHOP);
                }
            } else {
                handleFaultyIR(COMMA, REGISTER, ARITHOP);
            }
        } else {
            handleFaultyIR(REGISTER, ARITHOP, ARITHOP);
        }

    }

    private void finishOUTPUT() {
        IntermediateNode newNode = new IntermediateNode(this.lineNum, OUTPUT, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == CONSTANT) {
            if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                IRList.append(newNode);
            } else {
                handleFaultyIR(EOL, OUTPUT, OUTPUT);
            }
        }
    }

    private void finishNOP() {
        IntermediateNode newNode = new IntermediateNode(this.lineNum, NOP, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
            IRList.append(newNode);
        } else {
            handleFaultyIR(EOL, NOP, NOP);
        }
    }


}
