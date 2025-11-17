package tm;

import java.util.Hashtable;

public class TMStateLogic {
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

    public DestinationSet[][] array;
    public int currState, finalState;
    public DestinationSet currDestinationSet;
    //private boolean invalidState;

    public TMStateLogic(int numStates, int numSymb) {
        this.array = new DestinationSet[numStates][numSymb];
        this.currState = 0;
        this.finalState = numStates-1;
        //invalidState = false;
    }

    public boolean addTransition(int stateName, int onSymb, int destStateName, int writeSymb, boolean direction) {
        array[stateName][onSymb] = new DestinationSet(writeSymb, direction, destStateName);
        System.out.println(array[stateName][onSymb]);
        return array[stateName][onSymb] != null;
    }

    public void update(int onSymb) {
        this.currDestinationSet = this.array[this.currState][onSymb];
    }

    public boolean is_running() {
        if (this.currState==this.finalState) {
            return false;
        }
        // if (this.invalidState) {
        //     return false;
        // }
        return true;
    }

    // public boolean updateDestInfo(char onSymb) {
    //     this.currDestinationSet = this.transTable.get(onSymb);
    //     return this.currDestinationSet != null;
    // }

    // public int getDest() {
    //     return this.currDestinationSet.destStateName;
    // }

    public int getWriteSymb() {
        return this.currDestinationSet.writeSymb;
    }

    public boolean getDirection() {
        return this.currDestinationSet.direction;
    }

    // public int getName() {
    //     return this.name;
    // }
}
