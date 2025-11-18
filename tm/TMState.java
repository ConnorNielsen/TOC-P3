package tm;

import java.util.Hashtable;
import java.util.Set;

public class TMState {
    public class DestinationSet {
        public int writeSymb;
        public int destStateName;
        public boolean direction;

        public DestinationSet(int writeSymb, boolean direction, int destStateName) {
            this.writeSymb = writeSymb;
            this.direction = direction;
            this.destStateName = destStateName;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(writeSymb) + (direction?1:0) + Integer.valueOf(destStateName);
        }
    }

    public DestinationSet[] transTable;
    public int name;
    public DestinationSet currDestinationSet;

    public TMState(int name, int numSymb) {
        this.name = name;
        this.transTable = new DestinationSet[numSymb];
        this.currDestinationSet = null;
    }

    public boolean addTransition(int onSymb, int destStateName, int writeSymb, boolean direction) {
        // System.out.println("State: " + this.name);
        // System.out.println("onSymb: " + onSymb);
        // System.out.println("destStateName: " + destStateName);
        // System.out.println("writeSymb: " + writeSymb);
        // System.out.println("direction: " + direction);
        // System.out.println();
        this.transTable[onSymb] = new DestinationSet(writeSymb, direction, destStateName);

        return true;
    }

    public boolean updateDestInfo(char onSymb) {
        this.currDestinationSet = this.transTable[(int)(onSymb-'0')];
        return this.currDestinationSet != null;
    }

    public boolean updateDestInfo(int onSymb) {
        this.currDestinationSet = this.transTable[onSymb];
        return this.currDestinationSet != null;
    }

    public int getDest() {
        return this.currDestinationSet.destStateName;
    }

    // public char getWriteSymbAlt() {
    //     return this.currDestinationSet.writeSymb;
    // }

    public int getWriteSymb() {
        return this.currDestinationSet.writeSymb;
    }

    public boolean getDirection() {
        return this.currDestinationSet.direction;
    }

    public int getName() {
        return this.name;
    }

}
