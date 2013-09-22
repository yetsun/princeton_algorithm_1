import java.util.Comparator;

public class Point implements Comparable<Point> {
    // construct the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            if (o1 == null || o2 == null) {
                throw new NullPointerException();
            }
            return Double.valueOf(slopeTo(o1)).compareTo(slopeTo(o2));
        }
    };

    private int x;
    private int y;

    private int getX() {
        return x;
    }

    /*
     * private void setX(int x) { this.x = x; }
     */
    private int getY() {
        return y;
    }

    /*
     * private void setY(int y) { this.y = y; }
     */


    // draw this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    // is this point lexicographically smaller than that point?
    public int compareTo(Point that) {
        if (this.getY() == that.getY()) {
            return Integer.valueOf(this.getX()).compareTo(that.getX());
        } else {
            return Integer.valueOf(this.getY()).compareTo(that.getY());
        }

    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) {
            throw new java.lang.NullPointerException();
        }
        if (that.getY() == this.getY() && that.getX() == this.getX()) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.getY() == this.getY()) {
            return 0;
        }
        if (that.getX() == this.getX()) {
            return Double.POSITIVE_INFINITY;
        }
        return (double) (that.getY() - this.getY())
                / (double) (that.getX() - this.getX());
    }

}