package tm;
import java.io.File;
import java.util.Scanner;

public class TM {
    private String evalString;
    private LinkedList list;
    private TMState[] statesList;
    private int numStates;

    public TM(File TMFile) {
        try (Scanner scanner = new Scanner(TMFile)) {
            this.numStates = scanner.nextInt();
            int numSymb = scanner.nextInt() + 1 ; //tape alphabet; + 1 is to account for blank character

            this.statesList = new TMState[numStates];
            // for (int i = 0; i < numStates; i++) {
            //     statesList[i] = new TMState(String.valueOf(i));
            // }

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < ((numStates-1) * numSymb); i++) {
                String current = scanner.next();
                //String[] parts = current.split(",");
                if (i%numSymb==0) {
                    //System.out.println(i/numSymb);
                    this.statesList[i/numSymb] = new TMState((char)('0' + (i/numSymb)), numSymb);
                }
                //statesList[i % numSymb].addTransition((char)('0'+(i % numStates)), parts[0], parts[1], parts[2]);
                // System.out.println(i/numSymb);
                // System.out.println((char)('0' + (i%numStates)));
                // System.out.println(current.charAt(0));
                // System.out.println(current.charAt(2));
                // System.out.println(current.charAt(4)=='R');
                this.statesList[i/numSymb].addTransition((i%numSymb), current.charAt(0), current.charAt(2), current.charAt(4)=='R');
            }
            list = new LinkedList();
            if (scanner.hasNext()) {
                this.evalString = scanner.next();
                list = new LinkedList(this.evalString.toCharArray());
            } else {
                this.evalString = "";
            }

            // System.out.println("Evaluation string is: " + this.evalString);
            // System.out.println(Arrays.toString(statesList));
            eval();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void eval() {
        boolean running = true;
        TMState curr = this.statesList[0];
        while (running) {
            curr.updateCurrDestination(list.getCurrNodeData());
            list.updateCurrNode(curr.getWriteSymb(), curr.getDirection());
            if ((int)(curr.getDest()-'0') == this.numStates-1) {
                running = false;
                break;
            }
            curr = this.statesList[(int)(curr.getDest()-'0')];
        }
        System.out.println(list);
    }
}


/*
 * Use an array of a ton of bytes
 */