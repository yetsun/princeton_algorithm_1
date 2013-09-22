import java.util.Arrays;

public class Brute {
    private static final String BAR = " -> ";

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        String name = args[0];

        In in = new In(name);
        int count = in.readInt();
        Point[] pp = new Point[count];
        Point p = null;

        for (int i = 0; i < count; i++) {
            int x = in.readInt();
            int y = in.readInt();
            p = new Point(x, y);
            pp[i] = p;
            p.draw();
        }
        Arrays.sort(pp);
        new Brute().foo(pp);
    }

    private void sortAndPrint(Point p, Point q, Point r, Point s) {
        Point[] pp = { p, q, r, s };
        Arrays.sort(pp);
        System.out.println(pp[0] + BAR + pp[1] + BAR + pp[2] + BAR + pp[3]);
        pp[0].drawTo(pp[1]);
    }
    
    private void print(Point p, Point q, Point r, Point s) {
        System.out.println(p + BAR + q + BAR + r + BAR + s);
        p.drawTo(s);
    }

    private void foo(Point[] points) {
        Point p = null, q = null, r = null, s = null;
        double s1 = 0, s2 = 0, s3 = 0;

        for (int i = 0; i < points.length; i++) {
            p = points[i];

            for (int j = i + 1; j < points.length; j++) {
                q = points[j];
                s1 = p.slopeTo(q);

                for (int k = j + 1; k < points.length; k++) {
                    r = points[k];

                    s2 = p.slopeTo(r);

                    if (s1 == s2) {

                        for (int l = k + 1; l < points.length; l++) {
                            s = points[l];
                            s3 = p.slopeTo(s);

                            if (s1 == s3) {
                                print(p, q, r, s);
                            }
                        }

                    }
                }

            }
        }
    }
}
