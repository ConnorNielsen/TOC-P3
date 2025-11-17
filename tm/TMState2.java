package tm;

import java.util.Hashtable;
import java.util.Set;

public class TMState2 {
    private class DestinationSet {
        private char writeSymb;
        private int destStateName;
        private boolean direction;

        public DestinationSet(char writeSymb, boolean direction, int destStateName) {
            this.writeSymb = writeSymb;
            this.direction = direction;
            this.destStateName = destStateName;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(writeSymb) + (direction?1:0) + Integer.valueOf(destStateName);
        }
    }

    private Hashtable<Character, DestinationSet> transTable;
    private int name;
    private DestinationSet currDestinationSet;

    public TMState2(int name) {
        this.name = name;
        this.transTable = new Hashtable<>();
        this.currDestinationSet = null;
    }

    public boolean addTransition(char onSymb, int destStateName, char writeSymb, boolean direction) {
        if (this.transTable.contains(onSymb)) {
            return false;
        }
        // System.out.println("State: " + this.name);
        // System.out.println("onSymb: " + onSymb);
        // System.out.println("destStateName: " + destStateName);
        // System.out.println("writeSymb: " + writeSymb);
        // System.out.println("direction: " + direction);
        // System.out.println();
        this.transTable.put(onSymb, new DestinationSet(writeSymb, direction, destStateName));

        return true;
    }

    public boolean updateDestInfo(char onSymb) {
        this.currDestinationSet = this.transTable.get(onSymb);
        return this.currDestinationSet != null;
    }

    public int getDest() {
        return this.currDestinationSet.destStateName;
    }

    public char getWriteSymb() {
        return this.currDestinationSet.writeSymb;
    }

    public boolean getDirection() {
        return this.currDestinationSet.direction;
    }

    public int getName() {
        return this.name;
    }

}
