
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
		
		testcase_outofbound(10, 0, 6);
		testcase_outofbound(10, 12, 6);
		testcase_outofbound(10, 11, 6);
		testcase_outofbound(10, 6, 0);
		testcase_outofbound(10, 6, 12);
		testcase_outofbound(10, 6, 11);
	}
	
	public static void testcase_outofbound(int n, int i, int j){
		
		try{
			testcase_open(n, i, j);
		}catch (IndexOutOfBoundsException  e){}
		
		try{
			testcase_isopen(n, i, j);
		}catch (IndexOutOfBoundsException  e){}
		
		try{
			testcase_isfull(n, i, j);
		}catch (IndexOutOfBoundsException  e){}
		
	}
	
	public static void testcase_open(int n, int i, int j){
		Percolation p = new Percolation(n);
		p.open(i, j);
	}
	
	public static void testcase_isopen(int n, int i, int j){
		Percolation p = new Percolation(n);
		p.isOpen(i, j);
	}
	
	public static void testcase_isfull(int n, int i, int j){
		Percolation p = new Percolation(n);
		p.isFull(i, j);
	}

}
