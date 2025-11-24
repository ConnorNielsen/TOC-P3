package tm;

import java.io.File;

/**
 * Driver class for the Turing Machine simulation.
 */
public class TMSimulator {
    /**
     * The driver function that is called upon starting the program.
     * @param args The command line arguments provided during the execution of this program.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            Usage();
        }
        new TM(new File(args[0]));
    }

    /**
     * A helper usage function that prints out what command to write to get this program to run
     * correctly.
     */
    public static void Usage() {
        System.out.println("Useage: java tm.TMSimulator <filename.txt>");
    }
}
