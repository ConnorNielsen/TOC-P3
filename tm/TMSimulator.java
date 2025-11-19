package tm;

import java.io.File;

/**
 * Driver class for our turing machine that creates a turing machine from a given file and evaluates if the string
 * on the last line of the input file is a valid string.
 * @author Connor Nielsen & Phuc Le
 */
public class TMSimulator {
    /**
     * Driver function for the turing machine simulator.
     * @param args The file to parse for the turing machine.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            Usage();
        }
        // Turing machine auto evals the string provided in the file upon creation.
        TM turingMachine = new TM(new File(args[0]));
    }

    /**
     * Prints the command line call to run our program, given it is compiled.
     */
    public static void Usage() {
        System.out.println("Useage: java tm.TMSimulator <filename.txt>");
    }
}
