package tm;

public class LinkedList {
    private class Node {
        private Node next,prev;
        private Character data;
        private boolean isSent;

        public Node(Character data, Node prev, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
            isSent = false;
            if (prev==null && next==null) {
                this.prev = this;
                this.next = this;
                isSent = true;
            }
        }

        @Override
        public String toString() {
            return "data: " + this.data +
                    "\nprev: " + (this.prev.getClass().getName() + '@' + Integer.toHexString(hashCode())) +
                    "\nnext: " + (this.next.getClass().getName() + '@' + Integer.toHexString(hashCode()));
        }
    }

    private int size;
    private Node sentinel;
    private Node currNode;

    public LinkedList() {
        sentinel = new Node(null, null, null);
        size = 0;
        append('0');
    }

    public LinkedList(char[] array) {
        sentinel = new Node(null, sentinel, sentinel);
        size = 0;
        for (int i = 0; i < array.length; i++) {
            append(array[i]);
        }
    }

    public Character getCurrNodeData() {
        return this.currNode.data;
    }

    public void append(Character data) {
        this.currNode = new Node(data, sentinel.prev, sentinel);
        sentinel.prev.next = this.currNode;
        sentinel.prev = this.currNode;
        this.size++;
    }

    public void append_after_sent(Character data) {
        this.currNode = new Node(data, sentinel, sentinel.next);
        sentinel.next.prev = this.currNode;
        sentinel.next = this.currNode;
        this.size++;
    }

    public void updateCurrNode(Character data, boolean moveRight) {
        //System.out.println("Before: "+this.currNode);
        this.currNode.data = data;
        //System.out.println("After|update: "+this.currNode);
        if (moveRight) {
            if (this.currNode.next.isSent) {
                append('0');
            } else {
                this.currNode = currNode.next;
            }
        } else {
            if (this.currNode.prev.isSent) {
                append_after_sent('0');
            } else {
                this.currNode = currNode.prev;
            }
        }
    }

    public int size() {
        return this.size;
    }

    public int sum() {
        Node curr = sentinel.next;
        int result = 0;
        while (!curr.isSent) {
            result += (int)(curr.data-'0');
            curr = curr.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void print() {
        Node curr = sentinel.next;
        while (!curr.isSent) {
            System.out.print(curr.data);
            curr = curr.next;
        }
        System.out.println();
    }

    @Override
    public String toString() {
        //System.out.println(sentinel);
        Node curr = sentinel.next;
        StringBuilder sb = new StringBuilder();
        while (!curr.isSent) {
            //System.out.println(curr);
            sb.append(curr.data);
            curr = curr.next;
        }
        return sb.toString();
    }
}
