import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;
//    private int numberOfMove;
    private int dimension;

//    protected int getMoves(){
//    	return numberOfMove;
//    }
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = copyArray(blocks);
        this.dimension = this.blocks[0].length;
    }

    // board dimension N
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        int goal = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (i != (dimension - 1) || j != (dimension - 1)) {
                    goal++;
                    if (blocks[i][j] != goal) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        int value = 0;
        int goalI = 0;
        int goalJ = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                value = blocks[i][j];
                if (value != 0) {
                    goalI = (value - 1) / dimension;
                    goalJ = (value - 1) % dimension;
                    result += i > goalI ? (i - goalI) : (goalI - i);
                    result += j > goalJ ? (j - goalJ) : (goalJ - j);
                }
            }
        }

        return result;

    }

    // is this board the goal board?
    public boolean isGoal() {
        int goal = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (i == (dimension - 1) && j == (dimension - 1)) {
                    if (0 != blocks[i][j]) {
                        return false;
                    }
                } else {
                    goal++;
                    if (goal != blocks[i][j]) {
                        return false;
                    }
                }
            }
        }
        return true;

    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    int[][] copy = copyArray(blocks);
                    swap(copy, i, j, i, j + 1);
                    return new Board(copy);
                }
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == null) {
            return false;
        }

        if (y == this) {
            return true;
        }

        if (y.getClass() == this.getClass()) {
            Board b = (Board) y;
            if (b.dimension() != this.dimension) {
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
        } else {
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
//            board.numberOfMove = this.numberOfMove + 1;
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