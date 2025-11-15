package tm;

import java.io.File;

public class TMSimulator {
    public static void main(String[] args) {
        if (args.length != 1) {
            Usage();
        }
        TM turingMachine = new TM(new File("../" + args[0]));
    }

    public static void Usage() {
        System.out.println("Useage: java tm.TMSimulator <filename.txt>");
    }
}
