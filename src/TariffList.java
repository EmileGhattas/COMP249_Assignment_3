// TariffList.java
// III) The TariffList class must implement the TariffPolicy interface, and it has the following:
//
//      (a) An inner class named TariffNode. This class has the following:
//              i. Two private attributes: an object of Tariff and a pointer to a TariffNode object.
//              ii. A default constructor, which assigns both attributes to null.
//              iii. A parameterized constructor that accepts two parameters, a Tariff object and a TariffNode object, then initializes the attributes accordingly.
//              iv. A copy constructor that creates deep copy of a TariffNode.
//              v. A clone() method that creates deep copy of the node.
//              vi. An equals() method that compares passed TariffNode with the current one.
//              vii. Other mutator and accessor methods.
//      (b) A private attribute called head, which should point to the first TariffNode in this list.
//      (c) A private attribute called size, which always indicates the current size of the list.
//      (d) A default constructor, which creates an empty list.
//      (e) A copy constructor, which accepts a TariffList object and creates a copy of it.
//      (f) A method called addToStart(), which accepts one parameter, a Tariff object and then creates a TariffNode with that passed object and inserts it at the head of the list.
//      (g) A method called insertAtIndex(), which accepts two parameters, a Tariff object, and an integer representing an index. If the index is invalid (a valid index is between 0 and size-1), the method must throw a NoSuchElementException and terminate the program. If the index is valid, then the method creates a TariffNode with the passed Tariff object and inserts it at the given index.
//      (h) A method called deleteFromIndex(), which accepts one integer parameter representing an index. Again, if the index is invalid, the method must throw NoSuchElementException and terminate the program. Otherwise, the TariffNode pointed to by that index is deleted from the list.
//      (i) A method called deleteFromStart(), which deletes first TariffNode in the list (i.e. one pointed by the head). All special cases must be properly handled.
//      (j) A method called replaceAtIndex(), which accepts two parameters, a Tariff object and an integer representing an index. If the index is invalid, the method simply returns; otherwise, the object in the TariffNode at the passed index is to be replaced by the node created from the passed object.
//      (k) A method called find(), which accepts three parameters String origin, String destination and String category. The method then searches the list for a TariffNode with that Tariff. If such an object is found, then the method returns a pointer to that TariffNode; otherwise, method returns null. The method must keep track of how many iterations were made before the search finally finds TariffNode or concludes that it is not in the list.
//      (l) A method called contains(), which accepts three parameters String origin, String destination and String category. It returns true if a TariffNode with matching info is in the list; otherwise, the method returns false.
//      (m) A method called equals(), which accepts one parameter of type TariffList. The method returns true if the two lists contain similar TariffNodes; otherwise, the method returns false.
//
// Finally, here are some general rules that you must consider when implementing the above methods:
//      - Whenever a node is added or deleted, the list size must be adjusted accordingly.
//      - All special cases must be handled, whether the method description explicitly states that or not.
//      - All clone() and copy constructors must perform a deep copy; no shallow copies are allowed.
//      - If any of your methods allows a privacy leak, you must clearly place a comment at the beginning of the method 
//        1) indicating, this method may result in a privacy leak 
//        2) explaining, reason behind the privacy leak.
//        Please keep in mind that you are not required to implement these proposals.

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy, Cloneable {

    // (a) Inner class TariffNode.
    public class TariffNode implements Cloneable {
        // i. Two private attributes: an object of Tariff and a pointer to a TariffNode object.
        private Show tariff;  // Using Show as the tariff object
        private TariffNode next;

        // ii. Default constructor, which assigns both attributes to null.
        public TariffNode() {
            this.tariff = null;
            this.next = null;
        }

        // iii. Parameterized constructor that accepts a Tariff object and a TariffNode object.
        public TariffNode(Show tariff, TariffNode next) {
            // Deep copy tariff.
            this.tariff = (tariff != null) ? tariff.clone() : null;
            this.next = next;
        }

        // iv. Copy constructor: creates deep copy of a TariffNode.
        public TariffNode(TariffNode other) {
            if(other == null) {
                this.tariff = null;
                this.next = null;
            } else {
                this.tariff = (other.tariff != null) ? other.tariff.clone() : null;
                this.next = (other.next != null) ? new TariffNode(other.next) : null;
            }
        }

        // v. clone() method that creates deep copy of the node.
        @Override
        public TariffNode clone() {
            return new TariffNode(this);
        }

        // vi. equals() method that compares passed TariffNode with the current one.
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
            // Compare next: if both null then equal; otherwise, compare recursively.
            if(this.next == null && other.next == null)
                return true;
            if(this.next == null || other.next == null)
                return false;
            return this.next.equals(other.next);
        }

        // vii. Other mutator and accessor methods.
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

    // (d) Default constructor, which creates an empty list.
    public TariffList() {
        head = null;
        size = 0;
    }

    // (e) Copy constructor, which accepts a TariffList object and creates a copy of it.
    public TariffList(TariffList other) {
        if(other == null) {
            head = null;
            size = 0;
        } else {
            head = (other.head != null) ? other.head.clone() : null;
            size = other.size;
        }
    }

    // (f) addToStart(): accepts one parameter, a Tariff object and then creates a TariffNode and inserts it at the head.
    public void addToStart(Show tariff) {
        TariffNode newNode = new TariffNode(tariff, head);
        head = newNode;
        size++;
    }

    // (g) insertAtIndex(): accepts a Tariff object and an integer representing an index.
    // If the index is invalid (a valid index is between 0 and size-1), throw NoSuchElementException.
    public void insertAtIndex(Show tariff, int index) {
        if(index < 0 || index > size - 1) {
            throw new NoSuchElementException("Invalid index for insertion: " + index);
        }
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

    // (h) deleteFromIndex(): accepts an integer parameter representing an index.
    // If index is invalid, throw NoSuchElementException; else, delete the node.
    public void deleteFromIndex(int index) {
        if(index < 0 || index > size - 1) {
            throw new NoSuchElementException("Invalid index for deletion: " + index);
        }
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

    // (i) deleteFromStart(): deletes first TariffNode in the list.
    public void deleteFromStart() {
        if(head == null) {
            throw new NoSuchElementException("List is empty, cannot delete.");
        }
        head = head.getNext();
        size--;
    }

    // (j) replaceAtIndex(): accepts a Tariff object and an integer representing an index.
    // If the index is invalid, simply return; otherwise, replace the Tariff in that node.
    public void replaceAtIndex(Show tariff, int index) {
        if(index < 0 || index > size - 1) {
            return;
        }
        TariffNode current = head;
        for (int i = 0; i < index; i++){
            current = current.getNext();
        }
        current.setTariff(tariff);
    }

    // (k) find(): accepts three parameters String origin, String destination and String category.
    // Searches the list for a TariffNode with that Tariff; prints the number of iterations; returns node if found, else null.
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

    // (l) contains(): accepts three parameters String origin, String destination and String category.
    // Returns true if a TariffNode with matching info is in the list.
    public boolean contains(String origin, String destination, String category) {
        return (find(origin, destination, category) != null);
    }

    // (m) equals(): accepts one parameter of type TariffList.
    // Returns true if the two lists contain similar TariffNodes.
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

    // (n) Implement the evaluateTrade(double proposedTariff, double minimumTariff) method from TariffPolicy.
    @Override
    public String evaluateTrade(double proposedTariff, double minimumTariff) {
        if(proposedTariff >= minimumTariff) {
            return "Accepted";
        } else if(proposedTariff >= minimumTariff * 0.8) {
            return "Conditionally Accepted";
        } else {
            return "Rejected";
        }
    }

    // General rules: Whenever a node is added or deleted, adjust size (already done).
    // All clone() and copy constructors perform deep copies.

    // Getter for size.
    public int getSize() {
        return size;
    }

    // (m) toString(): Returns a string representation of the list.
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