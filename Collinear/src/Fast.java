import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Fast {
   public static void main(String[] args){

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

			new Fast().foo(pp);

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
	private void print(Point p, Collection<Point> c){
		String str  = p.toString();
		for(Point pp : c){
			str += (foo + pp);
		}
		System.out.println(str);
	}
	
	private void print(Collection<Point> c){
		Iterator<Point> i = c.iterator();
		String str = i.next().toString();
		while(i.hasNext()){
			str += (foo + i.next());
		}
		System.out.println(str);
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
			for (int j = i+1; j < pp.length; j++) {
				slop = p.slopeTo(pp[j]);
				if(map.get(slop) == null){
					set = new TreeSet<Point>();
					map.put(slop, set);
				}
				map.get(slop).add(pp[j]);
			}
			
			for(Map.Entry<Double,Set<Point>> e: map.entrySet()){
				if(e.getValue().size() >= 3){
					if(!processed.contains(e.getValue().iterator().next().toString() + e.getKey())){
						
						e.getValue().add(p);
						
						print(e.getValue());
						
//						processed.add(p.toString() + e.getKey());
						
						for(Point q : e.getValue()){
							processed.add(q.toString() + e.getKey());
						}
					}
				}
			}
		}
		
	}
}