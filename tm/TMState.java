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

        @Override
        public int hashCode() {
            return Integer.valueOf(writeSymb) + Integer.valueOf(direction) + Integer.valueOf(destStateName);
        }
    }

    private Hashtable<String, DestinationSet> transTable;
    private String name;
    public TMState(String name) {
        this.name = name;
        this.transTable = new Hashtable<>();
    }

    public boolean addTransition(String onSymb, String destStateName, String writeSymb, String direction) {
        if (this.transTable.contains(onSymb)) {
            return false;
        }

        this.transTable.put(onSymb, new DestinationSet(writeSymb, direction, destStateName));

        return true;
    }

    public DestinationSet getDestInfo(String onSymb) {
        return this.transTable.get(onSymb);
    }

    public String getName() {
        return this.name;
    }

}
