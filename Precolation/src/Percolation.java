/**
 * 
 * memory size
 * 16 object overhead
 * 8*3 references
 * 4*3 int
 * n+24 boolean array
 * 4 padding
 * 
 * totally n+80 ~n
 * 
 * 
 * 
 * @author Jun Ye
 *
 */
public class Percolation {
    private WeightedQuickUnionUF uf;
    /**
     * second and uf only for fullness check. The idea is to have only the
     * virtual top site, but no virtual bottom site. so the backwash case can be
     * resolved.
     * 
     */
    private WeightedQuickUnionUF ufForFullness;
    /**
     * matrix horizontal/vertical size
     */
    private int n;
    /**
     * converted array index for virtual top site
     */
    private int headIndex;
    /**
     * converted array index for virtual bottom site
     */
    private int tailIndex;
    private boolean[] open;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        n = N;
        int nn = n * n;
        headIndex = nn;
        tailIndex = nn + 1;
        uf = new WeightedQuickUnionUF(nn + 2);
        open = new boolean[nn];

        for (int i = 0; i < n; i++) {
            uf.union(i, headIndex);
        }

        for (int i = n * (n - 1); i < nn; i++) {
            uf.union(i, tailIndex);
        }

        ufForFullness = new WeightedQuickUnionUF(nn + 1);
        for (int i = 0; i < n; i++) {
            ufForFullness.union(i, headIndex);
        }
    }

    private void unionNeighbor(int ij, int i2, int j2) {
        if (softValidate(i2) && softValidate(j2)) {
            int ij2 = getArrayIndex(i2, j2);
            if (open[ij2]) {
                uf.union(ij, ij2);
                ufForFullness.union(ij, ij2);
            }
        }
    }

    // open site (row i, column j) if it is not already
    // i and j starts from 1
    public void open(int i, int j) {
        hardValidate(i);
        hardValidate(j);
        int ij = getArrayIndex(i, j);
        open[ij] = true;
        // up
        unionNeighbor(ij, i - 1, j);
        // right
        unionNeighbor(ij, i, j + 1);
        // down
        unionNeighbor(ij, i + 1, j);
        // left
        unionNeighbor(ij, i, j - 1);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        hardValidate(i);
        hardValidate(j);
        int ij = getArrayIndex(i, j);
        return open[ij];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return (isOpen(i, j) && ufForFullness.connected(getArrayIndex(i, j),
                headIndex));
    }

    // does the system percolate?
    public boolean percolates() {
        // corner case that size is only 1 and no open
        if (n == 1) {
            return isOpen(1, 1);
        }
        return uf.connected(headIndex, tailIndex);
    }

    private int getArrayIndex(int i, int j) {
        hardValidate(i);
        hardValidate(j);
        return (i - 1) * n + j - 1;
    }

    private void hardValidate(int i) {
        if (i <= 0 || i > n) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean softValidate(int i) {
        boolean good = false;
        if (i <= 0 || i > n) {
            good = false;
        } else {
            good = true;
        }
        return good;
    }
}