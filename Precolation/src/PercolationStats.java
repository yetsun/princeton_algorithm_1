public class PercolationStats {
	Percolation p;
	int N;
	int T;
	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		this.N = N;
		this.T = T;
	}
	
	//[1, N)
	//[0, 1) * n [0, N)
	//[1, N+1)

	// sample mean of percolation threshold
	public double mean() {
		long all = 0;
		for(int c = 0; c < T; c++){
			p = new Percolation(N);
			
			int t = 0;
			int nn = N*N;
			while(!p.percolates() && t < nn){
				int i,j;
				do{
//					i = getRandom();
//					j = getRandom();
					 int rand = StdRandom.uniform(0, nn);
		             i = rand / N + 1;
		             j = rand % N + 1;
				} while(p.isOpen(i, j));
				
				t++;
				p.open(i, j);
			}
			
			all += t;
		}
		
		return  (double)all / (double)(T * N * N);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return 0;
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return 0;
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return 0;
	}

	// test client, described below
	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(200, 100);
		System.out.println(ps.mean());
	}
}