import java.util.Comparator;

public class Point implements Comparable<Point> {
	// compare points by slope to this point
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			return Double.valueOf(slopeTo(o1)).compareTo(slopeTo(o2));
		}
	};

	private int x;
	private int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	// construct the point (x, y)
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// draw this point
	public void draw() {
		System.out.print("(" + getX() + ", " + getY() + ")");
	}

	// draw the line segment from this point to that point
	public void drawTo(Point that) {
	}

	// string representation
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}

	// is this point lexicographically smaller than that point?
	public int compareTo(Point that) {
		if(this.getY() == that.getY()){
			return Integer.valueOf(this.getX()).compareTo(that.getX());
		}else{
			return Integer.valueOf(this.getY()).compareTo(that.getY());
		}
		
	}

	// the slope between this point and that point
	public double slopeTo(Point that) {
		if (that == null) {
			return -1;
		}
		if (that.getY() == this.getY() && that.getX() == this.getX()) {
			return Integer.MIN_VALUE;
		}
		if (that.getY() == this.getY()) {
			return 0;
		}
		if (that.getX() == this.getX()) {
			return Integer.MAX_VALUE;
		}
		return (that.getY() - this.getY()) / (that.getX() - this.getX());
	}
	
}