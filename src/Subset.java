public class Subset {

    public static void main(String[] args) {
        int index = Integer.valueOf(args[0]);
        String s = null;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while ((s = StdIn.readString()) != null) {
            rq.enqueue(s);
        }

        for (int i = 0; i < index; i++) {
            System.out.println(rq.dequeue());
        }
    }

}
