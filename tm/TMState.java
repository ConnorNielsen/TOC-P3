package tm;

import java.util.Hashtable;
import java.util.Set;

public class TMState {
    private class DestinationSet {
        private String writeSymb, direction, destStateName;
        public DestinationSet(String writeSymb, String direction, String destStateName) {
            this.writeSymb = writeSymb;
            this.direction = direction;
            this.destStateName = destStateName;
        }
    }

    private Hashtable<String, DestinationSet> transTable;
    private String name;
    public TMState(String name) {
        this.name = name;
        this.transTable = new Hashtable<>();
    }

    public boolean addTransition(String onSymb, String writeSymb, String direction, String stateName) {
        if (this.transTable.contains(onSymb)) {
            return false;
        }
        
        this.transTable.put(onSymb, new DestinationSet(writeSymb, direction, stateName));

        return true;
    }

    public String getName() {
        return this.name;
    }
}
