
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Percolation p = new Percolation(3);
		p.open(1,  1);
		System.out.println(p.percolates());
		p.open(2, 1);
		System.out.println(p.percolates());
		p.open(3, 1);
		System.out.println(p.percolates());
	}

}
