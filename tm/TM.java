package tm;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class TM {
    private String evalString;
    private TMState[] statesList;

    public TM(File TMFile) {
        try (Scanner scanner = new Scanner(TMFile)) {
            int numStates = scanner.nextInt();
            int numSymb = scanner.nextInt() + 1 ; //tape alphabet; + 1 is to account for blank character

            this.statesList = new TMState[numStates];
            for (int i = 0; i < numStates; i++) {
                statesList[i] = new TMState(String.valueOf(i));
            }

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < (numStates * numSymb); i++) {
                String current = scanner.nextLine();
                String[] parts = current.split(",");
                statesList[i % numSymb].addTransition(String.valueOf(i % numStates), parts[0], parts[1], parts[2]);
            }

            this.evalString = scanner.nextLine();

            System.out.println("Evaluation string is: " + evalString);
            System.out.println(Arrays.toString(statesList));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean eval() {
        return false;
    }
}
