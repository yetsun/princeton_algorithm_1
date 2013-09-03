public class PercolationStats {
    private Percolation p;
    private int N;
    private int T;
    private double[] x;
    private double mean = -100;
    private double stddev = -100;


    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {

        if (N < 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.T = T;
        this.x = new double[T];
    }

    // [1, N)
    // [0, 1) * n [0, N)
    // [1, N+1)

    // sample mean of percolation threshold
    public double mean() {

        if (needCalculation(mean)) {
            long all = 0;
            for (int c = 0; c < T; c++) {
                p = new Percolation(N);

                int t = 0;
                int nn = N * N;
                while (!p.percolates() && t < nn) {
                    int i, j;
                    do {
                        int rand = StdRandom.uniform(0, nn);
                        i = rand / N + 1;
                        j = rand % N + 1;
                    } while (p.isOpen(i, j));

                    t++;
                    p.open(i, j);
                }

                x[c] = ((double) t / (double) (N * N));

                all += t;
            }
            mean = (double) all / (double) (T * N * N);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        if (needCalculation(stddev)) {
            mean();
            double sum = 0;
            for (double d : x) {
                sum += (d - mean()) * (d - mean());
            }
            stddev = Math.sqrt(sum / (T - 1));
        }
        return stddev;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

    /**
     * check if the double is calculated return true if double is still in its
     * initialization value and need to be calculated
     * 
     * @param d
     * @return
     */
    private boolean needCalculation(double d) {
        // not accurately equivalent to d == Double.NEGATIVE_INFINITY
        return Math.abs(d - (-100)) < .0000001;
    }

    
    // test client, described below
    public static void main(String[] args) {

        int n = StdIn.readInt();
        int t = StdIn.readInt();

        if (n < 0 || t < 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats ps = new PercolationStats(n, t);
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