package tm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TM {
    private byte[] writeSymb, destStateName;
    private boolean[] directions;
    private byte[] tape;
    private int numStates, numSymb, head, startIndex, finalIndex;

    public TM(File TMFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(TMFile))) {
            this.numStates = Integer.valueOf(br.readLine());
            this.numSymb = Integer.valueOf(br.readLine())+1;

            int size = (numStates-1) * numSymb;
            this.writeSymb = new byte[size];
            this.destStateName = new byte[size];
            this.directions = new boolean[size];

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < size; i++) {
                String current = br.readLine();
                this.destStateName[i] = (byte)(current.charAt(0)-'0');
                this.writeSymb[i] = (byte)(current.charAt(2)-'0');
                this.directions[i] = (current.charAt(4)=='R');
                // System.out.println("d: " + this.destStateName[i] + " w: " + this.writeSymb[i] + " dir: " + this.directions[i]);
            }

            tape = new byte[2000000];
            head = startIndex = finalIndex = 1000000;
            String next = "";
            if ((next = br.readLine())!=null) {
                finalIndex = (head + next.length());
                int index = 0;
                for (int i = head; i < finalIndex; i++) {
                    tape[i] = (byte)(next.charAt(index)-'0');
                    index++;
                }
            }
            eval();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
        // int sum = 0;

        for (;startIndex<=finalIndex;startIndex++) {
            // sum += list[startIndex];
            output[i] = (char)(tape[startIndex]+'0');
            i++;
        }
        System.out.println(String.valueOf(output));
        System.out.println("output length: " + output.length);
        // System.out.println("sum of symbols: " + sum);
    }
    
    public byte[] extend(byte[] array, boolean negative) {
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
