import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;
    private int numberOfMove;
    private int dimension;

    private int[][] goal = null;

    private int i = 0;
    private int j = 0;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	
        this.blocks = copyArray(blocks);
        this.dimension = this.blocks[0].length;
        goal = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                goal[i][j] = (i * dimension + j + 1);
            }
        }
        goal[dimension - 1][dimension - 1] = 0;

    }

    // board dimension N
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != goal[i][j] && goal[i][j] != 0) {
                    result++;
                }
            }
        }
        return result + numberOfMove;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {

                if (goal[i][j] != 0) {
                    finder: for (int m = 0; m < dimension(); m++) {
                        for (int n = 0; n < dimension(); n++) {
                            if (goal[i][j] == blocks[m][n]) {
                                result += ((i > m ? (i - m) : (m - i)) + (j > n ? (j - n)
                                        : (n - j)));
                                break finder;
                            }
                        }
                    }
                }
            }
        }

        return result + numberOfMove;

    }

    // is this board the goal board?
    public boolean isGoal() {

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (goal[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        if (i >= this.dimension()/* && j >= this.dimension() */) {
            return null;
        }


        if (j < dimension() - 1) {
        	if (this.blocks[i][j] == 0) {
        		j++;
        		return twin();
        	}
        	
            if (this.blocks[i][j + 1] == 0) {
                j++;
                j++;
                return twin();
            } else {
                Board twin = new Board(copyArray(blocks));
                swap(twin.blocks, i, j, i, j + 1);
                j++;
                return twin;
            }
        } else {
            i++;
            j = 0;
            return twin();

        }
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == null) {
            return false;
        }
        
        if(y instanceof Board){
            Board b = (Board) y;
            if(b.dimension() != this.dimension){
            	return false;
            }
            int[][] target = b.blocks;
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++) {
                    if (target[i][j] != blocks[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }else{
        	return false;
        }
    
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        int i = 0;
        int j = 0;
        // find the empty node
        outer: for (i = 0; i < dimension(); i++) {
            for (j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    break outer;
                }
            }
        }

        List<Board> list = new ArrayList<Board>(4);
        addNeighbor(list, i, j, i - 1, j);
        addNeighbor(list, i, j, i, j - 1);
        addNeighbor(list, i, j, i + 1, j);
        addNeighbor(list, i, j, i, j + 1);
        return list;
    }

    private void addNeighbor(List<Board> list, int i, int j, int m, int n) {
        if (m >= 0 && m < dimension() && n >= 0 && n < dimension()) {
            int[][] copy = copyArray(this.blocks);
            swap(copy, i, j, m, n);

            Board board = new Board(copy);
            board.numberOfMove = this.numberOfMove + 1;
            list.add(board);
        }
    }

    private int[][] copyArray(int[][] a) {
    	int dim = a[0].length;
        int[][] result = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                result[i][j] = a[i][j];
            }
        }
        return result;
    }

    private void swap(int[][] blocks, int i, int j, int m, int n) {
        if (m >= 0 && m < dimension() && n >= 0 && n < dimension()) {
            int x = blocks[i][j];
            blocks[i][j] = blocks[m][n];
            blocks[m][n] = x;
        }
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(" ");
                sb.append(blocks[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}