import common.IntermediateRepresentation.IntermediateList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AllocMain {
    // Constants
    public static final String H_FLAG = "-h";
    public static final String X_FLAG = "-x";
    public static final String K_FLAG = "k";
    public static Map<String, Integer> flagVal;

    public static void commandHelp() {
        System.out.println("List of flags for this program");
        System.out.println("* -h: produces a list of valid command-line arguemnts");
        System.out.println("* -x <name>: scan and parse the input block. Then, perform renaming and print results to stdout");
    }


    /**
     * Main entry into the program.
     * @param args number of different flags that specify operation
     * - h ---> Help option
     * - x <name> ----> scan, parse, rename, and print ILOC of the input block
     */
    public static void main(String[] args) {
        flagVal = new HashMap<>();
        flagVal.put("-h", 3);
        flagVal.put("k", 2);
        flagVal.put("-x", 1);
        String[] parsedArgs = parseFlag(args);
        int error;
        Scanner scanner;
        Parser parser;
        switch (parsedArgs[0]) {
            case H_FLAG:
                commandHelp();
                break;
            case X_FLAG:
                // print IR
                scanner = new Scanner(parsedArgs[1]);
                if (!scanner.openFile()) {
                    return;
                }
                parser = new Parser(scanner);
                error = parser.parse();
                handleParseReturn(error, parsedArgs[1]);
                if (error == 0) {
                    IntermediateList representation = parser.getIRList();
                    Renamer renamer = new Renamer(representation, parser.getMaxSourceReg());
                    renamer.AddVirtualRegisters();
                    System.out.println(representation.getILoc());
                } else {
                    handleParseReturn(error, parsedArgs[1]);
                }
                break;
        }

    }

    private static void handleParseReturn(int errorCount, String fileName) {
        if (errorCount == 0) {
            System.out.println("Success! Parsed " + fileName);
        } else {
            System.out.println("Parser found " + errorCount + " errors while parsing " + fileName);
        }
    }

    private static String[] parseFlag(String[] args) {
        String currFlag = H_FLAG;
        String filename = null;
        if (args.length == 0) {
            currFlag = H_FLAG;
        }
        for (String arg : args) {
            if (!arg.equals(H_FLAG) && !arg.equals(K_FLAG) && !arg.equals(X_FLAG)) {
                if (filename != null) {
                    System.out.println("ERROR: 412fe can only process one input file at a time");
                } else {
                    filename = arg;
                }
            } else {
                if (currFlag != null) {
                    if (flagVal.get(currFlag) > flagVal.get(arg)) {
                        currFlag = arg;
                    }
                } else {
                    currFlag = arg;
                }
            }
        }
        return new String[] {currFlag, filename};
    }

}