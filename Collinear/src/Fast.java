import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Fast {
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		String name = args[0];
		Point p = null;

		In in = new In(name);
		int count = in.readInt();
		Point[] pp = new Point[count];

		for (int i = 0; i < count; i++) {
			int x = in.readInt();
			int y = in.readInt();
			p = new Point(x, y);
			pp[i++] = p;
			p.draw();
		}

		new Fast().foo(pp);

	}

	private static final String foo = " -> ";

	/*
	 * private void print(Point p, Collection<Point> c){ String str =
	 * p.toString(); for(Point pp : c){ str += (foo + pp); }
	 * System.out.println(str); }
	 */

	private void print(Collection<Point> c) {
		Point first = null, last = null;
		Iterator<Point> i = c.iterator();
		first = i.next();
		String str = first.toString();
		while (i.hasNext()) {
			last = i.next();
			str += (foo + last);
		}
		System.out.println(str);
		first.drawTo(last);
	}

	private void foo(Point[] pp) {

		List<String> processed = new ArrayList<String>();
		Set<Point> set = null;
		Point p = null;
		double slop = 0;
		HashMap<Double, Set<Point>> map = null;
		for (int i = 0; i < pp.length; i++) {
			p = pp[i];
			map = new HashMap<Double, Set<Point>>();
			for (int j = i + 1; j < pp.length; j++) {
				slop = p.slopeTo(pp[j]);
				if (map.get(slop) == null) {
					set = new TreeSet<Point>();
					map.put(slop, set);
				}
				map.get(slop).add(pp[j]);
			}

			for (Map.Entry<Double, Set<Point>> e : map.entrySet()) {
				if (e.getValue().size() >= 3) {
					if (!processed.contains(e.getValue().iterator().next()
							.toString()
							+ e.getKey())) {

						e.getValue().add(p);

						print(e.getValue());

						// processed.add(p.toString() + e.getKey());

						for (Point q : e.getValue()) {
							processed.add(q.toString() + e.getKey());
						}
					}
				}
			}
		}

	}
}