package tm;
import java.io.File;
import java.util.Scanner;

public class TM {
    private String evalString;

    public TM(File TMFile) {
        try (Scanner scanner = new Scanner(TMFile)) {
            int numStates = scanner.nextInt();
            int numSymb = scanner.nextInt();
            while (scanner.hasNext()) {
                //Read in transition

                //Parse transition

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean eval() {
        return false;
    }
}
