import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 412fe –h When a –h flag is detected, 412fe will produce a list of valid command-line
 * arguments that includes a description of all command-line arguments required for Lab 1 as well
 * as any additional command-line arguments supported by your 412fe implementation.
 *
 * 412fe is not required to process command-line arguments that appear after the –h flag.
 *
 * 412fe -s <name> When a -s flag is present, 412fe should read the file specified by <name>
 * and print, to the standard output stream, a list of the tokens that the scanner found.
 * For each token, it should print the line number, the token’s type (or syntactic category), and its
 * spelling (or lexeme).
 *
 * 412fe -p <name> When a -p flag is present, 412fe should read the file specified by
 * <name>, scan it and parse it, build the intermediate representation, and report either success or
 * report all the errors that it finds in the input file.
 *
 * 412fe -r <name> When a -r flag is present, 412fe should read the file specified by <name>, scan
 * it, parse it, build the intermediate representation, and print out the informatio
 */
public class Main {
    // Constants
    public static final String H_FLAG = "-h";
    public static final String S_FLAG = "-s";
    public static final String P_FLAG = "-p";
    public static final String R_FLAG = "-r";
    public static Map<String, Integer> flagVal;

    public static void commandHelp() {
        System.out.println("List of flags for this program");
        System.out.println("* -h: produces a list of valid command-line arguemnts");
        System.out.println("* -s <name>: read specified file and print a list of tokens found. Print contains" +
                "the line number, token's type, and its spelling");
        System.out.println("* -p <name>: read specified file and report either a success or a report of the " +
                "errors found in the input file");
        System.out.println("* -r <name>: read the specified file and print out the intermediate representation");

    }


    /**
     * Main entry into the program.
     * @param args number of different flags that specify operation
     *
     * - h ---> Help option
     * - s <name> ----> read file specified by name and print tokens that the scanner finds
     * - p <name> ----> read file specified by name and report if parser successfully build IR
     * - r <name> ----> read file specified by name and print out the IR
     */
    public static void main(String[] args) {
        args = new String[] {"-p", "src/tests/scanner.i"};
        String[] parsedArgs = parseFlag(args);
        int error;
        Scanner scanner;
        Parser parser;
        switch (parsedArgs[0]) {
            case H_FLAG:
                commandHelp();
                break;
            case R_FLAG:
                // print IR
                scanner = new Scanner(parsedArgs[1]);
                if (!scanner.openFile()) {
                    return;
                }
                parser = new Parser(scanner);
                error = parser.parse();
                handleParseReturn(error, parsedArgs[1]);
                if (error == 0) {
                    parser.printIR();
                }
                break;
            case P_FLAG:
                // return IR success or failure
                scanner = new Scanner(parsedArgs[1]);
                if (!scanner.openFile()) {
                    return;
                }
                parser = new Parser(scanner);
                error = parser.parse();
                handleParseReturn(error, parsedArgs[1]);
                break;
            case S_FLAG:
                // scan and print
                scanner = new Scanner(parsedArgs[1]);
                if (!scanner.openFile()) {
                    return;
                }
                scanner.scanFile();
                break;
        }

    }

    private static void handleParseReturn(int errorCount, String fileName) {
        if (errorCount == 0) {
            System.out.println("Parser successfully parsed " + fileName);
        } else {
            System.out.println("Parser found " + errorCount + " errors while parsing " + fileName);
        }
    }

    private static String[] parseFlag(String[] args) {
        String currFlag = null;
        String filename = null;
        if (args.length == 0) {
            currFlag = H_FLAG;
        }
        for (String arg : args) {
            if (!arg.equals(H_FLAG) && !arg.equals(R_FLAG) && !arg.equals(P_FLAG) && !arg.equals(S_FLAG)) {
                if (filename != null) {
                    System.out.println("ERROR: 412fe can only process one input file at a time");
                } else {
                    filename = arg;
                }
            } else {
                if (currFlag != null) {
                    if (flagVal.get(currFlag) < flagVal.get(arg)) {
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