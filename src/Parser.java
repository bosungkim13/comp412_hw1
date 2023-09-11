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
                    break;
                default:
                    if (currToken.getOpCode() == -1) {
                        // we are skipping when we already skipped for an invalid character
                        // in the case we get a faulty start
                        System.out.println("ERROR " + currToken.getLineNum() + ": " + currToken.getLexeme());
//                        scanner.nextLine();
//                        currToken = scanner.scanNextWord();
                        errorCount += 1;
                    }
                    break;

            }
            // scan next and handle EOF
            currToken = scanner.scanNextWord();
//            if (currToken.getOpCode() == 10) {
//                scanner.nextLine();
//                currToken = scanner.scanNextWord();
//            } else {
//                currToken = scanner.scanNextWord();
//            }
        }
        return errorCount;
    }

    public void printIR() {
        System.out.println(this.IRList);
    }

    public void handleFaultyIR(int currLex, int prevLex, int opcode) {
        errorCount += 1;
        System.out.println("ERROR " + this.currToken.getLineNum() + ": There was no " + IntermediateList.tokenConversion[currLex] + " following " + IntermediateList.tokenConversion[prevLex] + " for opCode " + IntermediateList.tokenConversion[opcode]);
        while (this.currToken.getOpCode()!= EOL && this.currToken.getOpCode() != -1) {
            this.currToken = scanner.scanNextWord();
        }

    }
    private void finishMEMOP() {
        String lexeme = currToken.getLexeme();
        IntermediateNode newNode = new IntermediateNode(currToken.getLineNum(), 0, lexeme);
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == REG) {
            newNode.setSourceRegister(0, Integer.parseInt(currToken.getLexeme()));
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == 8) {
                currToken = scanner.scanNextWord();
                if (currToken.getOpCode() == REG) {
                    newNode.setSourceRegister(1, Integer.parseInt(currToken.getLexeme()));
                    currToken = scanner.scanNextWord();
                    if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                        // BOSUNG MOVE TO NEXT LINE
                        IRList.append(newNode);
                    } else {
                        handleFaultyIR(10, REG, 0);
//                        printCustomErrorMsg("There was no EOL operation at the end of the " + lexeme + " operation");
                    }
                } else {
                    handleFaultyIR(REG, 8, 0);
                    // printCustomErrorMsg("There was no Register value at the end in the " + lexeme + " operation");
                }
            } else {
                handleFaultyIR(8, REG, 0);
                // printCustomErrorMsg("There was no => in the " + lexeme + " operation");
            }
        } else {
            handleFaultyIR(REG, MEMOP, MEMOP);
//            System.out.println("There was no Register value at the start in the " + lexeme + " operation");
        }
    }

    private void finishLOADI() {
        // start with loadI
        IntermediateNode newNode = new IntermediateNode(currToken.getLineNum(), 1, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == 5) {
            // constant
            newNode.setSourceRegister(0, Integer.parseInt(currToken.getLexeme()));
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == 8) {
                // into
                currToken = scanner.scanNextWord();
                if (currToken.getOpCode() == REG) {
                    // register
                    newNode.setSourceRegister(1, Integer.parseInt(currToken.getLexeme()));
                    currToken = scanner.scanNextWord();
                    if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                        // eol
                        IRList.append(newNode);
                    } else {
                        handleFaultyIR(10, REG, 1);
                        //printCustomErrorMsg("There was no EOL operation at the end of the LOADI");
                    }
                } else {
                    handleFaultyIR(REG, 8, 1);
                    // printCustomErrorMsg("There was no register following the into in LOADI");
                }
            } else {
                handleFaultyIR(8, 5, 1);
                // printCustomErrorMsg("There was no INTO operation following the constant in LOADI");
            }
        } else {
            handleFaultyIR(5, currToken.getOpCode(), 1);
            // printCustomErrorMsg("There was no constant following the LOADI operation");
        }

    }

    private void finishARITHOP() {
        IntermediateNode newNode = new IntermediateNode(currToken.getLineNum(), ARITHOP, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == REG) {
            newNode.setSourceRegister(0, Integer.parseInt(currToken.getLexeme()));
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == COMMA) {
                currToken = scanner.scanNextWord();
                if (currToken.getOpCode() == REG) {
                    newNode.setSourceRegister(1, Integer.parseInt(currToken.getLexeme()));
                    currToken = scanner.scanNextWord();
                    if (currToken.getOpCode() == INTO) {
                        currToken = scanner.scanNextWord();
                        if (currToken.getOpCode() == REG) {
                            newNode.setSourceRegister(2, Integer.parseInt(currToken.getLexeme()));
                            currToken = scanner.scanNextWord();
                            if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                                IRList.append(newNode);
                            } else {
                                handleFaultyIR(EOL, REG, ARITHOP);
                            }
                        } else {
                            handleFaultyIR(REG, INTO, ARITHOP);
                        }
                    } else {
                        handleFaultyIR(INTO, REG, ARITHOP);
                    }
                } else {
                    handleFaultyIR(REG, COMMA, ARITHOP);
                }
            } else {
                handleFaultyIR(COMMA, REG, ARITHOP);
            }
        } else {
            handleFaultyIR(REG, ARITHOP, ARITHOP);
        }

    }

    private void finishOUTPUT() {
        IntermediateNode newNode = new IntermediateNode(currToken.getLineNum(), OUTPUT, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == CONSTANT) {
            currToken = scanner.scanNextWord();
            if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
                IRList.append(newNode);
            } else {
                handleFaultyIR(EOL, OUTPUT, OUTPUT);
            }
        }
    }

    private void finishNOP() {
        IntermediateNode newNode = new IntermediateNode(currToken.getLineNum(), NOP, currToken.getLexeme());
        currToken = scanner.scanNextWord();
        if (currToken.getOpCode() == EOL || currToken.getOpCode() == EOF) {
            IRList.append(newNode);
        } else {
            handleFaultyIR(EOL, NOP, NOP);
        }
    }


}
