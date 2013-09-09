import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int initLenght = 10;
    private Item[] a = (Item[]) new Object[initLenght];
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    private class RQIterator implements Iterator<Item> {
        private int currentIndex = 0;
        private int[] indices;

        public RQIterator() {
            indices = new int[size];
            for (int j = 0; j < size; j++) {
                indices[j] = j;
            }
            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = a[indices[currentIndex]];
            currentIndex++;
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
        if (size <= (a.length * 0.25) && size > initLenght) {
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
        a[size - 1] = null;
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
