import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Node prior;
        private Node next;
        private Item item;

        public Node(Item i, Node prior, Node next) {
            this.item = i;
            this.next = next;
            this.prior = prior;
        }

    }

    private class DequeIterator2 implements Iterator<Item> {
        private Node current;

        public DequeIterator2() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item i = current.item;
            current = current.next;
            return i;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == 0) {
            firstAdd(item);
        } else {
            Node n = new Node(item, null, this.head);
            this.head.prior = n;
            this.head = n;
            size++;
        }
    }

    private void firstAdd(Item item) {
        Node n = new Node(item, null, null);
        head = n;
        tail = n;
        size = 1;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == 0) {
            firstAdd(item);
        } else {
            Node n = new Node(item, this.tail, null);
            this.tail.next = n;
            this.tail = n;
            size++;
        }
    }

    // delete and return the item at the front
    public Item removeFirst() {

        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        size--;
        Item i = this.head.item;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        } else {
            this.head.prior = null;
        }
        return i;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        size--;
        Item i = this.tail.item;
        this.tail = this.tail.prior;
        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.next = null;
        }
        return i;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator2();
    }
}
