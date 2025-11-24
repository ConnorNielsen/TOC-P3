package tm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Turing machine class that builds a machine defined from a file.
 * Also evaluates the provided string in the same file.
 * This implementation does not support S calls for the direction.
 */
public class TM {
    /* 
    * Since creating objects in java degrades performance significantly, we opted to use arrays instead and ensured
    * the correct index was being read to/from. This increased performance from about 2 seconds down to around 1 second.
    * The writeSymb and tape arrays can only hold values ranging from 0 to 9 which is why they are byte arrays.
    */
    private byte[] writeSymb, tape; // Arrays holding the symbols on the tape and to be written to the tape.
    private boolean[] directions; // Array containing the direction to move the tape head, given a current state
    private int[] destStateName; // Array containing the next state (array index) to go to, given a current state
    private int numStates, numSymb, head, startIndex, finalIndex;
    final short offset = '0'; // Offset to convert correctly cast a char into a byte/int

    /**
     * Constructor class for the Turing machine.
     * @param TMFile The file containing the turing machine states, transitions, and string to eval.
     */
    public TM(File TMFile) {
        // BufferedReader are supposedly faster based on this link (https://www.w3schools.com/java/java_bufferedreader.asp)
        try (BufferedReader br = new BufferedReader(new FileReader(TMFile))) {
            this.numStates = Integer.valueOf(br.readLine());
            this.numSymb = Integer.valueOf(br.readLine())+1; // Grab the number of symbols in the alphabet and add one for 0.

            int size = (numStates-1) * numSymb; // Calculates the total number of lines defining the transitions for the machine

            /* Fixed sized arrays can be precompiled, making the machine run slightly faster.
             * Only if the machine has more than 256 transitions does the array need to be extended.
             */
            this.writeSymb = new byte[256];
            this.destStateName = new int[256];
            this.directions = new boolean[256];
            if (size>256){
                this.writeSymb = new byte[size];
                this.destStateName = new int[size];
                this.directions = new boolean[size];
            }

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < size; i++) {
                String current = br.readLine();
                this.destStateName[i] = (byte)(current.charAt(0)-offset); // Convert the char into bytes
                this.writeSymb[i] = (byte)(current.charAt(2)-offset); // Convert the char into bytes
                this.directions[i] = (current.charAt(4)=='R'); // Convert the L/R indicator into boolean logic
            }

            // Allocating a large array is worth the hit since it hopefully ensures no extensions need to be made
            tape = new byte[2097152]; // Size is 2^21
            // Setting the start index to be in the middle of the array ensures the tape has room to grow to the left and right
            head = startIndex = finalIndex = 1048576; // start pos is 2^20
            String next = "";
            if ((next = br.readLine())!=null) {
                finalIndex = (head + next.length()); // Assigning the final index again is faster than computing it during the loop
                int index = 0; // index allows for faster indexing because each loop doesn't need to calculate the index offset for next
                for (int i = head; i < finalIndex; i++) {
                    tape[i] = (byte)(next.charAt(index)-offset);
                    index++;
                }
            }
            eval(); // Calling the eval function is faster inside the constructor as it doesn't require dereferencing the object pointer.
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Public eval function that evaluates the provided string and prints to the console the output of the machine.
     */
    public void eval() {
        int state = 0;
        int onSymb;
        int i;
        while (true) {
            onSymb = tape[head];
            i = (state*numSymb) + onSymb;
            state = this.destStateName[i];
            tape[head] = this.writeSymb[i];
            
            if (this.directions[i]) {
                head++;
                if (head>finalIndex) {
                    finalIndex = head;
                }
                if (head>tape.length) {
                    extend(tape, false);
                }
            } else {
                head--;
                if (head<startIndex) {
                    startIndex = head;
                }
                if (head<0) {
                    extend(tape, true);
                }
            }
            if (state == numStates-1) {
                break;
            }
        }

        // Convert list to string
        char[] output = new char[finalIndex-startIndex+1];
        i = 0;

        // Converts only the cells in the tape that were actually reached during the evaluation.
        for (;startIndex<=finalIndex;startIndex++) {
            output[i] = (char)(tape[startIndex]+offset);
            i++;
        }
        System.out.println(String.valueOf(output));
    }
    
    /**
     * Private extension function that takes an array and doubles the size and recenters the original array.
     * @param array The array to increase the size of and recenter
     * @param negative A boolean indicator telling which side of the array was reached
     * @return A new array that is twice the size of the original array and has the data centered.
     */
    private byte[] extend(byte[] array, boolean negative) { //TODO remove negative stuff
        byte[] newArray = new byte[array.length * 2];
        if (negative) {
            // for (int i = 0; i < array.length; i++) {
            //     newArray[i] = 0;
            // }
            for (int i = array.length; i < array.length*2; i++) {
                newArray[i] = array[i-array.length];
            }
        } else {
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            // for (int i = array.length; i < newArray.length; i++) {
            //     newArray[i] = 0;
            // }
        }
        
        return newArray;
    }
}
