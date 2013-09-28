import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private List<Board> solution;
    private Board initial;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        foo(initial);
    }

    private void foo(Board initial) {
        this.initial = initial;
        solution = new ArrayList<Board>();

        Comparator<Board> mcomparator = new Comparator<Board>() {
            @Override
            public int compare(Board arg0, Board arg1) {
                return arg0.manhattan() - arg1.manhattan();
            }
        };
        
        Comparator<Board> hcomparator = new Comparator<Board>() {
            @Override
            public int compare(Board arg0, Board arg1) {
                return arg0.hamming() - arg1.hamming();
            }
        };

        MinPQ<Board> queue = new MinPQ<Board>(mcomparator);

        queue.insert(initial);

        Board board = null;
        Board previous = null;
        while (!queue.isEmpty()) {
            board = queue.delMin();

            solution.add(board);

            if (board.isGoal()) {
                return;
            }

            Iterable<Board> neighbors = board.neighbors();
            for (Board neighor : neighbors) {
                if (previous == null || !neighor.equals(previous)) {
                    queue.insert(neighor);
                }
            }
            previous = board;

        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}