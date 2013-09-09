import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a = (Item[]) new Object[10];
    private int size = 0;
    // construct an empty randomized queue
    public RandomizedQueue() {

    }
    
    private class RQIterator implements Iterator<Item> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = a[i];
            i++;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

    private void extendArray() {
        if (a.length == size) {
            Item[] b = (Item[]) new Object[(int) (a.length * 2)];
            System.arraycopy(a, 0, b, 0, size);
            a = b;
        }
    }

    private void shrinkArray() {
        if (size <= (a.length * 0.25)) {
            Item[] b = (Item[]) new Object[(int) (a.length * 0.5)];
            System.arraycopy(a, 0, b, 0, size);
            a = b;
        }
    }

  

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        extendArray();

        a[size] = item;
        size++;

    }

    // delete and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int index = (int) (StdRandom.random() * size);
        Item i = a[index];
        a[index] = a[size - 1];
        size--;
        shrinkArray();
        return i;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int index = (int) (StdRandom.random() * size);
        Item i = a[index];
        return i;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }
}