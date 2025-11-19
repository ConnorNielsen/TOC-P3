package tm;
import java.io.File;
import java.util.Scanner;

/**
 * Turning machine class that generates the machine from a specified file and evaluates
 * the string on the last line of the file.
 * @author Connor Nielsen & Phuc Le
 */
public class TM {
    private LinkedList tape; // The tape.
    private TMState[] statesList; // The turing machine's states.
    private int numStates; // The number of states in this machine.

    /**
     * Constructor for the turing machine.
     * @param TMFile The file to parse a turing machine from and evaluate the final string
     */
    public TM(File TMFile) {
        try (Scanner scanner = new Scanner(TMFile)) {
            this.numStates = scanner.nextInt();
            int numSymb = scanner.nextInt() + 1 ; //tape alphabet; + 1 is to account for blank character

            this.statesList = new TMState[numStates];

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < ((numStates-1) * numSymb); i++) {
                String current = scanner.next();
                if (i%numSymb==0) {
                    this.statesList[i/numSymb] = new TMState((char)('0' + (i/numSymb)), numSymb);
                }
                this.statesList[i/numSymb].addTransition((i%numSymb), current.charAt(0), current.charAt(2), current.charAt(4)=='R');
            }
            tape = new LinkedList();
            if (scanner.hasNext()) {
                tape = new LinkedList(scanner.next().toCharArray());
            }
            eval();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Turing machine evaluator that prints a string representation of the final tape after halting.
     */
    public void eval() {
        TMState curr = this.statesList[0];
        while (true) {
            curr.updateCurrDestination(tape.getCurrNodeData());
            tape.updateCurrNode(curr.getWriteSymb(), curr.getDirection());
            if ((int)(curr.getDest()-'0') == this.numStates-1) {
                break;
            }
            curr = this.statesList[(int)(curr.getDest()-'0')];
        }
        System.out.println(tape);
    }
}


/*
 * Use an array of a ton of bytes
 */