import common.IntermediateRepresentation.IntermediateList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScheduleMain {
    // Constants
    public static final String H_FLAG = "-h";
    public static final String S_FLAG = "-s";
    public static Map<String, Integer> flagVal;

    public static void commandHelp() {
        System.out.println("List of flags for this program");
        System.out.println("* -h: produces a list of valid command-line arguemnts");
        System.out.println("* <name>: will produce, as output, an ILOC program that is\n" +
                "equivalent to the input program, albeit reordered to improve\n" +
                "(shorten) its execution time on the Lab 3 ILOC Simulator. The\n" +
                "output code uses the square bracket notation [ op1 ; op2 ] to\n" +
                "designate operations that should issue in the same cycle. ");
    }


    /**
     * Main entry into the program.
     * @param args number of different flags that specify operation
     * - h ---> Help option
     * - x <name> ----> scan, parse, rename, and print ILOC of the input block
     */
    public static void main(String[] args) {
//        args = new String[] {"src/tests/ehs2.i"};
        flagVal = new HashMap<>();
        flagVal.put("-h", 2);
        flagVal.put("-s", 1);
        String[] parsedArgs = parseFlag(args);
        int error;
        Scanner scanner;
        Parser parser;
        switch (parsedArgs[0]) {
            case H_FLAG:
                commandHelp();
                break;
            case S_FLAG:
                scanner = new Scanner(parsedArgs[1]);
                if (!scanner.openFile()) {
                    return;
                }
                parser = new Parser(scanner);
                error = parser.parse();
                if (error == 0) {
                    // the parse was successful and we can get the IR from the parser
                    IntermediateList representation = parser.getIRList();
                    Renamer renamer = new Renamer(representation, parser.getMaxSourceReg());
                    renamer.addVirtualRegisters();
//                    Allocator allocator = new Allocator(representation, numRegisters, renamer.getVrName(), renamer.getMaxLive());
//                    allocator.allocate();
//                    System.out.print(representation.getILoc());
//                    System.out.println("--------------------------------------------");
                    Grapher grapher = new Grapher(representation);
                    grapher.buildGraph();

                    Scheduler scheduler = new Scheduler(grapher.getNodeEdgeMap());
                    scheduler.computePriorities();
//                    grapher.printGraph();
                    scheduler.createSchedule();
//                    System.out.print(representation.getPRCode());
                } else {
                    // there was an error in the parse
                    handleParseReturn(error, args[0]);
                    return;
                }
                break;
        }

    }

    private static void handleParseReturn(int errorCount, String fileName) {
        if (errorCount == 0) {
//            System.out.println("Success! Parsed " + fileName);
        } else {
//            System.out.println("Parser found " + errorCount + " errors while parsing " + fileName);
        }
    }

    private static String[] parseFlag(String[] args) {
        String currFlag = null;
        String filename = null;
        if (args.length == 0 || args[0].equalsIgnoreCase(H_FLAG)) {
            currFlag = H_FLAG;
        } else {
            currFlag = S_FLAG;
            filename = args[0];
        }
        return new String[] {currFlag, filename};
    }

}