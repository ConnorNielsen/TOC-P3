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
    private int[] writeSymb, destStateName;
    private boolean[] directions;
    private boolean debug;
    private int[] list;
    private int numStates, numSymb, listIndex, startIndex, finalIndex;

    public TM(File TMFile, boolean debug) {
        this.debug = debug;
        try (Scanner scanner = new Scanner(TMFile)) {
            this.numStates = scanner.nextInt();
            this.numSymb = scanner.nextInt() + 1 ; //tape alphabet; + 1 is to account for blank character

            //this.states = new TMStateLogic(numStates, numSymb);
            //this.states = new DestinationSet[numStates-1][numSymb];
            int size = (numStates-1) * numSymb;
            this.writeSymb = new int[size];
            this.destStateName = new int[size];
            this.directions = new boolean[size];

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < size; i++) {
                String current = scanner.next();
                this.destStateName[i] = (int)(current.charAt(0)-'0');
                this.writeSymb[i] = (int)(current.charAt(2)-'0');
                this.directions[i] = (boolean)(current.charAt(4)=='R');

                // System.out.println("w: " + this.writeSymb[i] + " d: " + this.destStateName[i] + " dir: " + this.directions[i]);

                // String current = scanner.next();
                // //String[] parts = current.split(",");
                // char[] parts = current.toCharArray();
                // int currIndex = i/numSymb;
                // // System.out.print("start: " + currIndex + " dest: " + ((int)(parts[0]-'0')));
                // // System.out.print(" writeSymb: " + ((int)(parts[2]-'0')) + " onSymb: " + i%numSymb);
                // // System.out.println();
                // //states.addTransition(currIndex, (i%numSymb), (int)(parts[0]-'0'), (int)(parts[2]-'0'), (parts[4]=='R'));
                // this.states[currIndex][i%numSymb] = new DestinationSet((int)(parts[0]-'0'), (int)(parts[2]-'0'), (parts[4]=='R'));
            }

            list = new int[10000000];
            listIndex = startIndex = finalIndex = 5000000;
            if (scanner.hasNext()) {
                String next = scanner.next();
                listIndex = startIndex = (list.length-next.length())/2;
                finalIndex = (listIndex + next.length());
                for (int i = 0; i < listIndex; i++) {
                    list[i] = 0;
                }
                int index = 0;
                for (int i = listIndex; i < finalIndex; i++) {
                    list[i] = next.charAt(index);
                    index++;
                }
                for (int i = finalIndex; i < list.length; i++) {
                    list[i] = 0;
                }
            }
            eval();

            // if (scanner.hasNext()) {
            //     String next = scanner.next();
            //     list = new int[next.length()*2];
            //     char[] array = next.toCharArray();
            //     listIndex = startIndex = finalIndex = next.length()/2;
            //     for (int i = 0; i<listIndex; i++) {
            //         list[i] = 0;
            //     }
            //     for (int i = 0; i<next.length(); i++) {
            //         list[i+listIndex] = array[i]-'0';
            //     }
            //     for (int i = next.length()+listIndex; i<list.length; i++) {
            //         list[i] = 0;
            //     }
            // } else {
            //     list = new int[10000000];
            //     listIndex = startIndex = finalIndex = 5000000;
            // }
            // this.startState = this.states[0];
            // eval();

            //System.out.println("Evaluation string is: " + new String(this.evalCharArray));
            //System.out.println(Arrays.toString(statesList));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void eval() {
        int state = 0;
        int onSymb = this.list[this.listIndex];
        int i = 0;
        while (true) {
            i = (state*numSymb) + onSymb;
            state = this.destStateName[i];
            list[listIndex] = this.writeSymb[i];
            if (this.directions[i]) {
                listIndex++;
                if (listIndex>finalIndex) {
                    finalIndex = listIndex;
                }
            } else {
                listIndex--;
                if (listIndex<startIndex) {
                    startIndex = listIndex;
                }
            }
            if (state == numStates-1) {
                break;
            }
            onSymb = list[listIndex];
        }

        // Convert list to string
        char[] output = new char[finalIndex-startIndex+1];
        i = 0;
        int sum = 0;
        for (;startIndex<=finalIndex;startIndex++) {
            sum += list[startIndex];
            output[i] = (char)(list[startIndex]+'0');
            i++;
        }
        System.out.println(String.valueOf(output));
        System.out.println("output length: " + output.length);
        System.out.println("sum of symbols: " + sum);
    }

    public void eval_alt() {
        //TMState curr = statesList[0];
        boolean running = true;
        //int count = 0;
        while (running) {// && count < 1000) {
            //count++;
            // DestinationSet curr = this.startState[list.getCurrNodeData()];
            DestinationSet curr = this.startState[list[listIndex]];
            //System.out.print(curr.destStateName + " ");

            //list.updateCurrNode(curr.writeSymb,curr.direction);
            // System.out.print(list[listIndex]);
            //System.out.print(""+ list[listIndex]);
            list[listIndex] = curr.writeSymb;
            //System.out.println(" "+ list[listIndex]);
            listIndex += (curr.direction)? 1:-1;
            if (curr.direction && finalIndex<listIndex) {
                finalIndex = listIndex;
            }
            if (!curr.direction && startIndex>listIndex) {
                startIndex = listIndex;
            }
            if (curr.destStateName == this.numStates-1) {
                running = false;
                continue;
            }

            if (listIndex<0) {
                listIndex = this.list.length/2;
                this.list=extend(this.list, true);
            } else if (listIndex==this.list.length) {
                listIndex += this.list.length/2;
                this.list=extend(this.list, false);
            }
            this.startState = this.states[curr.destStateName];

            // states.update(list.getCurrNodeData());
            // list.updateCurrNode(states.getWriteSymb(), states.getDirection());
            // states.update(list.getCurrNodeData());
            
            //curr = statesList[curr.getDest()];
            //count++;
        }
        //System.out.println(list);
        // for (int i = startIndex; i<=finalIndex; i++) {
        //     System.out.print(list[i]);
        // }
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i<=finalIndex;i++) {
            sb.append(list[i]);
        }
        System.out.println(sb.toString());
        if (debug) {
            //System.out.println("output length: " + list.size());
            //System.out.println("sum of symbols: " + list.sum());
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
    
    public int[] extend(int[] array, boolean negative) {
        int[] newArray = new int[array.length * 2];
        if (negative) {
            for (int i = 0; i < array.length; i++) {
                newArray[i] = 0;
            }
            for (int i = array.length; i < array.length*2; i++) {
                // System.out.println(i);
                newArray[i] = array[i-array.length];
                // System.out.println(newEvalCharArray[i]);
            }
        } else {
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            for (int i = array.length; i < newArray.length; i++) {
                newArray[i] = 0;
            }
        }
        
        return newArray;
    }
}
