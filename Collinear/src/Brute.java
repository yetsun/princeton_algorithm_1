import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Brute {
	public static void main(String[] args) {
		String name = args[0];
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(name));
			br = new BufferedReader(fr);

			String line = br.readLine();
			int count = Integer.valueOf(line);
			String[] a = null;
			Point[] pp = new Point[count];
			int i = 0;
			while ((line = br.readLine()) != null) {
				a = line.split(" ");
				pp[i++] = new Point(Integer.valueOf(a[0]),
						Integer.valueOf(a[1]));
			}

			new Brute().foo(pp);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
	
	private static final String foo = " -> ";
	private void print(Point p, Point q, Point r, Point s){
		System.out.println(p + foo + q + foo + r + foo + s);
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
