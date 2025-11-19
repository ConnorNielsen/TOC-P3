package tm;

import java.util.Hashtable;
import java.util.Set;

public class TMState {
    private class DestinationSet {
        private char writeSymb, destStateName;
        private boolean direction;
        /**
         * 
         * @param writeSymb
         * @param destStateName
         * @param direction Right is true, Left is false
         */
        public DestinationSet(char writeSymb, char destStateName, boolean direction) {
            this.writeSymb = writeSymb;
            this.direction = direction;
            this.destStateName = destStateName;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(writeSymb) + ((direction)?1:0) + Integer.valueOf(destStateName);
        }
    }

    private Hashtable<Character, DestinationSet> transTable;
    private DestinationSet currDestinationSet;
    private char name;
    public TMState(char name) {
        this.name = name;
        this.transTable = new Hashtable<>();
        this.currDestinationSet = null;
    }

    public boolean addTransition(char onSymb, char destStateName, char writeSymb, boolean direction) {
        if (this.transTable.contains(onSymb)) {
            return false;
        }

        this.transTable.put(onSymb, new DestinationSet(writeSymb, destStateName, direction));
        return true;
    }

    // public DestinationSet getDestInfo(char onSymb) {
    //     return this.transTable.get(onSymb);
    // }

    public boolean updateCurrDestination(char onSymb) {
        if (!this.transTable.contains(onSymb)) {
            return false;
        }
        this.currDestinationSet = this.transTable.get(onSymb);
        return true;
    }

    public char getDest() {
        return this.currDestinationSet.destStateName;
    }

    public char getWriteSymb() {
        return this.currDestinationSet.writeSymb;
    }

    public boolean getDirection() {
        return this.currDestinationSet.direction;
    }

    public char getName() {
        return this.name;
    }

}
