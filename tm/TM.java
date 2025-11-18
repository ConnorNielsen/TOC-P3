package tm;
import java.io.File;

import java.util.Scanner;

public class TM {
    public class DestinationSet {
        public int writeSymb;
        public int destStateName;
        public boolean direction;

        public DestinationSet(int destStateName, int writeSymb, boolean direction) {
            this.writeSymb = writeSymb;
            this.direction = direction;
            this.destStateName = destStateName;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(writeSymb) + (direction?1:0) + Integer.valueOf(destStateName);
        }
    }

    private DestinationSet[][] states;
    private DestinationSet[] startState;
    private boolean debug;
    private LinkedListInt list;
    private int numStates;

    public TM(File TMFile, boolean debug) {
        this.debug = debug;
        try (Scanner scanner = new Scanner(TMFile)) {
            this.numStates = scanner.nextInt();
            int numSymb = scanner.nextInt() + 1 ; //tape alphabet; + 1 is to account for blank character

            //this.states = new TMStateLogic(numStates, numSymb);
            this.states = new DestinationSet[numStates-1][numSymb];

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < ((numStates-1) * numSymb); i++) {
                String current = scanner.next();
                //String[] parts = current.split(",");
                char[] parts = current.toCharArray();
                int currIndex = i/numSymb;
                //states.addTransition(currIndex, (i%numSymb), (int)(parts[0]-'0'), (int)(parts[2]-'0'), (parts[4]=='R'));
                this.states[currIndex][i%numSymb] = new DestinationSet((int)(parts[0]-'0'), (int)(parts[2]-'0'), (parts[4]=='R'));
            }

            list = new LinkedListInt();
            if (scanner.hasNext()) {
                String next = scanner.next();
                list = new LinkedListInt(next.toCharArray());
            }
            this.startState = this.states[0];
            eval();

            //System.out.println("Evaluation string is: " + new String(this.evalCharArray));
            //System.out.println(Arrays.toString(statesList));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void eval() {
        //TMState curr = statesList[0];
        boolean running = true;
        //int count = 0;
        while (running) { //&& count<10) {

            DestinationSet curr = this.startState[list.getCurrNodeData()];
            if (curr.destStateName == this.numStates-1) {
                running = false;
                continue;
            }
            list.updateCurrNode(curr.writeSymb,curr.direction);
            this.startState = this.states[curr.destStateName];

            // states.update(list.getCurrNodeData());
            // list.updateCurrNode(states.getWriteSymb(), states.getDirection());
            // states.update(list.getCurrNodeData());
            
            //curr = statesList[curr.getDest()];
            //count++;
        }
        System.out.println(list);
        if (debug) {
            System.out.println("output length: " + list.size());
            System.out.println("sum of symbols: " + list.sum());
        }
    }

    // public String evalAlt() {
    //     TMState curr = statesList[0];
    //     boolean running = true;
    //     int i = 0;
    //     while (running) {
    //         if (curr.getName() == this.numStates-1) {
    //             return new String(evalCharArray);
    //         }

    //         //Rejected due to no valid paths
    //         if (!curr.updateDestInfo(evalCharArray[i])) {
    //             System.out.println(evalCharArray[i]);
    //             return new String(evalCharArray);
    //         }
    //         System.out.println("State: " + curr.getName());
    //         System.out.println("i: " + i + ", onSymb: " + evalCharArray[i]);
    //         System.out.println("Destination: " + curr.getDest());
    //         System.out.println("writeSymb: " + curr.getWriteSymb());
    //         System.out.println("Direction: "+ curr.getDirection());

    //         evalCharArray[i] = curr.getWriteSymb();
    //         if (curr.getDirection()) {
    //             if (i==evalCharArray.length-1) {
    //                 i=evalCharArray.length;
    //                 evalCharArray = extend(evalCharArray, false);
    //             } else {
    //                 i++;
    //             }
    //         } else {
    //             i--;
    //             if (i<0) {
    //                 i = evalCharArray.length-1;
    //                 System.out.println(i);
    //                 evalCharArray = extend(evalCharArray, true);
    //             }
    //         }
    //         curr = statesList[curr.getDest()];
    //     }
    //     return new String(evalCharArray);
    // }

    // public char[] extend(char[] evalCharArray, boolean negative) {
    //     char[] newEvalCharArray = new char[evalCharArray.length * 2];
    //     if (negative) {
    //         for (int i = 0; i < evalCharArray.length; i++) {
    //             newEvalCharArray[i] = '0';
    //         }
    //         for (int i = evalCharArray.length; i < evalCharArray.length*2; i++) {
    //             // System.out.println(i);
    //             newEvalCharArray[i] = evalCharArray[i-evalCharArray.length];
    //             // System.out.println(newEvalCharArray[i]);
    //         }
    //     } else {
    //         for (int i = 0; i < evalCharArray.length; i++) {
    //             newEvalCharArray[i] = evalCharArray[i];
    //         }
    //         for (int i = evalCharArray.length; i < newEvalCharArray.length; i++) {
    //             newEvalCharArray[i] = '0';
    //         }
    //     }
        
    //     return newEvalCharArray;
    // }
}
