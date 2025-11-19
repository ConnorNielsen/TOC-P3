package tm;

/**
 * A turing machine state class that contains the current state name,
 * the transitions for this state, and the write/direction symbols for the tape.
 * @author Connor Nielsen & Phuc Le
 */
public class TMState {

    /**
     * Private internal class for defining the transitions, including the symbol
     * to write to the tape, the next state to go to, and the direction to move the 
     * read/write head.
     * @author Connor Nielsen & Phuc Le
     */
    private class DestinationSet {
        private char writeSymb, destStateName; // The symbol to write and the next state to transition to.
        private boolean direction; // The direction to move the tape head. true is right, false is left.
        /**
         * Constructor for the DestinationSet
         * @param writeSymb The symbol to write to the tape
         * @param destStateName The state to transition to.
         * @param direction The direction to move the read/write head. true is right, false is left.
         */
        public DestinationSet(char writeSymb, char destStateName, boolean direction) {
            this.writeSymb = writeSymb;
            this.direction = direction;
            this.destStateName = destStateName;
        }
    }

    public DestinationSet[] transArray; // Array holding the transitions for this state.
    private DestinationSet currDestinationSet; // The current destination to transition to.
    private char name; // The name of this state.

    /**
     * Constructor for the TMState
     * @param name The name of the current state.
     * @param numSymb The number of symbols to store transitions for.
     */
    public TMState(char name, int numSymb) {
        this.name = name;
        this.transArray = new DestinationSet[numSymb];
        this.currDestinationSet = null;
    }

    /**
     * Adds a transition to the transition array.
     * @param onSymb The character to transition on.
     * @param destStateName The state to transition.
     * @param writeSymb The character to write back to the tape.
     * @param direction The direction to move the head. true is right, false is left.
     */
    public void addTransition(int onSymb, char destStateName, char writeSymb, boolean direction) {
        this.transArray[onSymb] = new DestinationSet(writeSymb, destStateName, direction);
    }

    /**
     * Updates the current destination for this state, given a symbol read from the tape.
     * @param onSymb The symbol to update the destination set from.
     */
    public void updateCurrDestination(char onSymb) {
        this.currDestinationSet = this.transArray[(int)(onSymb-'0')];
    }

    /**
     * Getter method for the destination state.
     * @return The name of the state to transition to.
     */
    public char getDest() {
        return this.currDestinationSet.destStateName;
    }

    /**
     * Getter method for the write symbol.
     * @return The write symbol to write to the tape.
     */
    public char getWriteSymb() {
        return this.currDestinationSet.writeSymb;
    }

    /**
     * Getter method for the direction to move the head.
     * @return The direction to move the head. true is right, false is left
     */
    public boolean getDirection() {
        return this.currDestinationSet.direction;
    }

    /**
     * Getter method for the name of this state
     * @return This state's name.
     */
    public char getName() {
        return this.name;
    }

}
