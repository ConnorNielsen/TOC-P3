package tm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class TMAlt {
    private TMState[] statesList;
    private char[] evalCharArray;
    private boolean debug;
    private LinkedListInt list;
    private int numStates;


            // char[] buf = new char[size*6];
            // br.read(buf);
            // for (int i = 0; i < size; i++) {
            //     int bufIndex = i*6;
            //     this.destStateName[i] = (int)(buf[bufIndex]-'0');
            //     this.writeSymb[i] = (int)(buf[bufIndex+2]-'0');
            //     this.directions[i] = (buf[bufIndex+4]=='R');
            // }

    public TMAlt(File TMFile) {
        this.debug = debug;
        try (BufferedReader bw = new BufferedReader(new FileReader(TMFile))) {
            this.numStates = Integer.valueOf(bw.readLine());
            int numSymb =Integer.valueOf(bw.readLine())+1; //tape alphabet; + 1 is to account for blank character
            char[] buf = new char[((numStates-1)*numSymb)*6];
            
            bw.read(buf);

            this.statesList = new TMState[numStates];
            // for (int i = 0; i < numStates; i++) {
            //     statesList[i] = new TMState(String.valueOf(i));
            // }

            //Read transitions -- next state, write symbol, move
            for(int i = 0; i < ((numStates-1) * numSymb); i++) {
                //String current = scanner.next();
                //String[] parts = current.split(",");
                //char[] parts = current.toCharArray();
                int currIndex = i/numSymb;
                int bufIndex = (i*6);
                // System.out.println(currIndex);
                if (i%numSymb==0) {
                    statesList[currIndex] = new TMState(currIndex, numSymb);
                }
                // System.out.println((char)('0'+currIndex));
                // System.out.println((int)(buf[bufIndex]-'0'));
                // System.out.println(buf[bufIndex+2]);
                // System.out.println(buf[bufIndex+4] == 'R');
                statesList[currIndex].addTransition((i%numSymb), (int)(buf[bufIndex]-'0'), (int)(buf[bufIndex+2]-'0'), (buf[bufIndex+4]=='R'));
            }
            statesList[numStates-1] = new TMState(numStates-1, numSymb);
            list = new LinkedListInt();
            String next = "";
            if ((next = bw.readLine())!=null) {
                // String next = scanner.next();
                list = new LinkedListInt(next.toCharArray());
            }
            eval();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void eval() {
        TMState curr = statesList[0];
        boolean running = true;
        //int count = 0;
        while (running) { //&& count<10) {
            if (curr.getName() == this.numStates-1) {
                running = false;
                continue;
            }

            //System.out.println(list.getCurrNodeData());
            // if (!curr.updateDestInfo(list.getCurrNodeData())) {
            //     running = false;
            //     continue;
            // }
            if (!curr.updateDestInfo(list.currNode.data)) {
                running = false;
                continue;
            }

            // System.out.println("List: "+list);
            // System.out.println("State: " + curr.getName());
            // System.out.println("Destination: " + curr.getDest());
            // System.out.println("writeSymb: " + curr.getWriteSymb());
            // System.out.println("Direction: "+ curr.getDirection());
            // System.out.println();

            //list.updateCurrNode(curr.getWriteSymb(), curr.getDirection());
            //curr = statesList[curr.getDest()];

            list.updateCurrNode(curr.currDestinationSet.writeSymb, curr.currDestinationSet.direction);
            curr = statesList[curr.currDestinationSet.destStateName];
            //count++;
        }
        System.out.println(list);
        //list.print();
        if (debug) {
            System.out.println("output length: " + list.size());
            System.out.println("sum of symbols: " + list.sum());
        }
    }
}