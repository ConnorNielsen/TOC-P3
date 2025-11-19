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

    public DestinationSet[] transArray;
    private DestinationSet currDestinationSet;
    private char name;
    public TMState(char name, int numSymb) {
        this.name = name;
        this.transArray = new DestinationSet[numSymb];
        this.currDestinationSet = null;
    }

    public boolean addTransition(int onSymb, char destStateName, char writeSymb, boolean direction) {
        this.transArray[onSymb] = new DestinationSet(writeSymb, destStateName, direction);
        return true;
    }

    public boolean updateCurrDestination(char onSymb) {
        this.currDestinationSet = this.transArray[(int)(onSymb-'0')];
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
