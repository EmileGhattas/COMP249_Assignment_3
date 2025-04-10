import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy, Cloneable {

    // (a) Inner class TariffNode.
    public class TariffNode implements Cloneable {
        private Show tariff;       // i. Tariff object (Show instance)
        private TariffNode next;   // i. Pointer to next node

        // ii. Default constructor.
        public TariffNode() {
            this.tariff = null;
            this.next = null;
        }

        // iii. Parameterized constructor.
        public TariffNode(Show tariff, TariffNode next) {
            this.tariff = (tariff != null) ? tariff.clone() : null;
            this.next = next;
        }

        // iv. Copy constructor.
        public TariffNode(TariffNode other) {
            if(other == null) {
                this.tariff = null;
                this.next = null;
            } else {
                this.tariff = (other.tariff != null) ? other.tariff.clone() : null;
                this.next = (other.next != null) ? new TariffNode(other.next) : null;
            }
        }

        // v. clone() method.
        @Override
        public TariffNode clone() {
            return new TariffNode(this);
        }

        // vi. equals() method.
        @Override
        public boolean equals(Object obj) {
            if(this == obj)
                return true;
            if(obj == null || getClass() != obj.getClass())
                return false;
            TariffNode other = (TariffNode) obj;
            if(this.tariff == null) {
                if(other.tariff != null)
                    return false;
            } else if(!this.tariff.equals(other.tariff))
                return false;
            if(this.next == null && other.next == null)
                return true;
            if(this.next == null || other.next == null)
                return false;
            return this.next.equals(other.next);
        }

        // vii. Accessor and mutator methods.
        public Show getTariff() {
            return tariff;
        }
        public void setTariff(Show tariff) {
            this.tariff = (tariff != null) ? tariff.clone() : null;
        }
        public TariffNode getNext() {
            return next;
        }
        public void setNext(TariffNode next) {
            this.next = next;
        }
    }

    // (b) Private attribute called head.
    private TariffNode head;
    // (c) Private attribute called size.
    private int size;

    // (d) Default constructor.
    public TariffList() {
        head = null;
        size = 0;
    }

    // (e) Copy constructor.
    public TariffList(TariffList other) {
        if(other == null) {
            head = null;
            size = 0;
        } else {
            head = (other.head != null) ? other.head.clone() : null;
            size = other.size;
        }
    }

    // (f) addToStart().
    public void addToStart(Show tariff) {
        TariffNode newNode = new TariffNode(tariff, head);
        head = newNode;
        size++;
    }

    // (g) insertAtIndex().
    public void insertAtIndex(Show tariff, int index) {
        if(index < 0 || index > size - 1)
            throw new NoSuchElementException("Invalid index for insertion: " + index);
        if(index == 0) {
            addToStart(tariff);
            return;
        }
        TariffNode current = head;
        for(int i = 0; i < index - 1; i++){
            current = current.getNext();
        }
        TariffNode newNode = new TariffNode(tariff, current.getNext());
        current.setNext(newNode);
        size++;
    }

    // (h) deleteFromIndex().
    public void deleteFromIndex(int index) {
        if(index < 0 || index > size - 1)
            throw new NoSuchElementException("Invalid index for deletion: " + index);
        if(index == 0) {
            deleteFromStart();
            return;
        }
        TariffNode current = head;
        for(int i = 0; i < index - 1; i++){
            current = current.getNext();
        }
        current.setNext(current.getNext().getNext());
        size--;
    }

    // (i) deleteFromStart().
    public void deleteFromStart() {
        if(head == null)
            throw new NoSuchElementException("List is empty, cannot delete.");
        head = head.getNext();
        size--;
    }

    // (j) replaceAtIndex().
    public void replaceAtIndex(Show tariff, int index) {
        if(index < 0 || index > size - 1)
            return;
        TariffNode current = head;
        for(int i = 0; i < index; i++){
            current = current.getNext();
        }
        current.setTariff(tariff);
    }

    // (k) find(): searches for a TariffNode matching the given origin, destination, and category.
    // It prints the number of iterations taken before returning the node, or 0 if not found.
    public TariffNode find(String origin, String destination, String category) {
        int iterations = 0;
        TariffNode current = head;
        while(current != null) {
            iterations++;
            Show t = current.getTariff();
            if(t.getOriginCountry().equalsIgnoreCase(origin) &&
                    t.getDestinationCountry().equalsIgnoreCase(destination) &&
                    t.getProductCategory().equalsIgnoreCase(category)) {
                System.out.println("Iterations in find(): " + iterations);
                return current;
            }
            current = current.getNext();
        }
        System.out.println("Iterations in find(): " + iterations);
        return null;
    }

    // (l) contains(): returns true if a matching TariffNode is found.
    public boolean contains(String origin, String destination, String category) {
        return (find(origin, destination, category) != null);
    }

    // (m) equals(): returns true if the two TariffList objects contain similar TariffNodes.
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        TariffList other = (TariffList) obj;
        if(this.size != other.size)
            return false;
        TariffNode curr1 = this.head;
        TariffNode curr2 = other.head;
        while(curr1 != null && curr2 != null) {
            if(!curr1.equals(curr2))
                return false;
            curr1 = curr1.getNext();
            curr2 = curr2.getNext();
        }
        return true;
    }

    // (n) Implement evaluateTrade() from TariffPolicy.
    @Override
    public String evaluateTrade(double proposedTariff, double minimumTariff) {
        if(proposedTariff >= minimumTariff)
            return "Accepted";
        else if(proposedTariff >= minimumTariff * 0.8)
            return "Conditionally Accepted";
        else
            return "Rejected";
    }

    // Getter for size.
    public int getSize() {
        return size;
    }

    // toString() method: returns a string representation of the list.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        TariffNode current = head;
        while(current != null) {
            sb.append(current.getTariff().toString()).append("\n");
            current = current.getNext();
        }
        return sb.toString();
    }
}