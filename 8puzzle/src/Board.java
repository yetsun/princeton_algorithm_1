import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;
    private int numberOfMove;
    private int dimension;

//    private int[][] goal = null;

    private int twinI = 0;
    private int twinJ = 0;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	
        this.blocks = copyArray(blocks);
        this.dimension = this.blocks[0].length;
/*        goal = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                goal[i][j] = (i * dimension + j + 1);
            }
        }
        goal[dimension - 1][dimension - 1] = 0;*/

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
                if (blocks[i][j] != getGoalValue(i, j) && (i != (dimension - 1) || j != (dimension - 1))) {
                    result++;
                }
            }
        }
        return result + numberOfMove;
    }
    
    private int getGoalValue(int i, int j){
    	if(i == (dimension - 1) && j == (dimension - 1)) return 0;
    	return (i * dimension + j + 1) ;
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
            	if(value != 0){
            		goalI = (value - 1) / dimension;
            		goalJ = (value - 1) % dimension;
					result += i > goalI ? (i - goalI) : (goalI - i);
					result += j > goalJ ? (j - goalJ) : (goalJ - j);
            	}
            }
        }

        return result + numberOfMove;

    }

    // is this board the goal board?
    public boolean isGoal() {

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (getGoalValue(i, j) != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }
    
    

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
    	
    	if(twinI == 0 && twinJ == 0){
    		int diff = 0;
    		for (int i = 0; i < dimension(); i++) {
    			for (int j = 0; j < dimension(); j++) {
    				if (getGoalValue(i, j) != blocks[i][j] && blocks[i][j] != 0) {
    					diff++;
    					if(diff > 2){
    						return null;
    					}
    				}
    			}
    		}
    	}
    	
    	
    	
        if (twinI >= this.dimension()/* && j >= this.dimension() */) {
            return null;
        }


        if (twinJ < dimension() - 1) {
        	if (this.blocks[twinI][twinJ] == 0) {
        		twinJ++;
        		return twin();
        	}
        	
            if (this.blocks[twinI][twinJ + 1] == 0) {
                twinJ++;
                twinJ++;
                return twin();
            } else {
                Board twin = new Board(copyArray(blocks));
                swap(twin.blocks, twinI, twinJ, twinI, twinJ + 1);
                twinJ++;
                return twin;
            }
        } else {
            twinI++;
            twinJ = 0;
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