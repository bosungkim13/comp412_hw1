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
    private int errorFlag = 0;
    public static Map<String, Integer> tokenConversion =  new HashMap<>();
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
        while (currToken.getOpCode() != 9) {
            switch (currToken.getOpCode()) {
                case 0:
                    finishMEMOP();
            }
        }
    }

    public void handleFaultyIR(int currLex, int prevLex, int opcode) {
        System.out.println("ERROR: There was no " + IntermediateList.tokenConversion[currLex] + " following " + IntermediateList.tokenConversion[prevLex] + "for opCode " + IntermediateList.tokenConversion[opcode]);
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
                    if (currToken.getOpCode() == 10) {
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
                    if (currToken.getOpCode() == 10) {
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


}
