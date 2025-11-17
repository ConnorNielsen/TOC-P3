package tm;
import java.io.File;

import java.util.Scanner;

public class TM2 {
    private TMStateLogic states;
    private boolean debug;
    private LinkedListInt list;
    private int numStates;

    public TM2(File TMFile, boolean debug) {
        this.debug = debug;
        try (Scanner scanner = new Scanner(TMFile)) {
            this.numStates = scanner.nextInt();
            int numSymb = scanner.nextInt() + 1 ; //tape alphabet; + 1 is to account for blank character

            this.states = new TMStateLogic(numStates, numSymb);
            // for (int i = 0; i < numStates; i++) {
            //     statesList[i] = new TMState(String.valueOf(i));
            // }

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < ((numStates-1) * numSymb); i++) {
                String current = scanner.next();
                //String[] parts = current.split(",");
                char[] parts = current.toCharArray();
                int currIndex = i/numSymb;
                // System.out.println(currIndex);
                // System.out.println((char)('0'+currIndex));
                // System.out.println((int)(parts[0]-'0'));
                // System.out.println(parts[2]);
                // System.out.println(parts[4] == 'R');
                states.addTransition(currIndex, (i%numSymb), (int)(parts[0]-'0'), (int)(parts[2]-'0'), (parts[4]=='R'));
            }
            // System.out.println("Here");
            // if (scanner.hasNext()) {
            //     String next = scanner.next();
            //     // System.out.println(next);
            //     this.isEmptyString = false;
            //     this.evalCharArray = next.toCharArray();//scanner.next().toCharArray();
            // } else {
            //     // System.out.println("Got in here");
            //     this.evalCharArray = new char[100];
            //     this.isEmptyString = true;
            //     for (int i = 0; i < evalCharArray.length; i++) {
            //         this.evalCharArray[i] = '0';
            //     }
            // }
            list = new LinkedListInt();
            if (scanner.hasNext()) {
                String next = scanner.next();
                list = new LinkedListInt(next.toCharArray());
            }

            //System.out.println("Evaluation string is: " + new String(this.evalCharArray));
            //System.out.println(Arrays.toString(statesList));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void eval() {
        //TMState curr = statesList[0];
        //boolean running = true;
        //int count = 0;
        while (states.is_running()) { //&& count<10) {
            System.out.println("List: "+list);
            System.out.println("State: " + states.currState);
            System.out.println("Destination: " + states.array[states.currState][list.getCurrNodeData()].destStateName);
            System.out.println("writeSymb: " + states.getWriteSymb());
            System.out.println("Direction: "+ states.getDirection());
            System.out.println();
            states.update(list.getCurrNodeData());
            list.updateCurrNode(states.getWriteSymb(), states.getDirection());
            states.update(list.getCurrNodeData());
            
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
