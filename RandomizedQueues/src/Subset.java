public class Subset {

    /**
     * /c/Program\ Files/Java/jdk1.7.0_25/bin/javac -classpath
     * ".;algs4.jar;stdlib.jar" Subset.java
     * 
     * 
     * echo "A B C D E F G H I" | /c/Program\ Files/Java/jdk1.7.0_25/bin/java
     * -classpath ".;algs4.jar;stdlib.jar" Subset 3
     * 
     * @param args
     */
    public static void main(String[] args) {
        int index = Integer.valueOf(args[0]);
        String s = null;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            rq.enqueue(s);
        }

        for (int i = 0; i < index; i++) {
            System.out.println(rq.dequeue());
        }
    }

}
