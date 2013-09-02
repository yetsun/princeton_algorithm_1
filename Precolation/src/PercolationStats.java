public class PercolationStats {
	Percolation p;
	int N;
	int T;
	double[] x;
	double mean;
	double stddev;
	
	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		this.N = N;
		this.T = T;
		this.x = new double[T];
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
					 int rand = StdRandom.uniform(0, nn);
		             i = rand / N + 1;
		             j = rand % N + 1;
				} while(p.isOpen(i, j));
				
				t++;
				p.open(i, j);
			}
			
			 x[c] = ((double)t / (double) (N * N));
			
			all += t;
		}
		mean = (double)all / (double)(T * N * N);
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if(mean == 0 ) mean();
		double sum = 0;
		for(double d : x){
			sum += (d- mean) * (d-mean);
		}
		stddev =  Math.sqrt(sum / (T-1));
		return stddev;
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return mean - (1.96 * stddev / Math.sqrt(T));
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return mean + (1.96 * stddev / Math.sqrt(T));
	}

	// test client, described below
	public static void main(String[] args) {
		
		int n = StdIn.readInt();
		int t = StdIn.readInt();
		
		if(n < 0 || t < 0){
			throw new IllegalArgumentException();
		}
		
		PercolationStats ps = new PercolationStats(n,t);
		StdOut.print("mean                    = ");
		StdOut.println(ps.mean());
		StdOut.print("stddev                  = ");
		StdOut.println(ps.stddev());
		StdOut.print("95% confidence interval = ");
		StdOut.print(ps.confidenceLo());
		StdOut.print(", ");
		StdOut.print(ps.confidenceHi());
	}
}