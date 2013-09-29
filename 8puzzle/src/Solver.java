import java.util.Comparator;

public class Solver {

//    private List<Board> solution;
	private SearchNode result;

    private static class SearchNode {
        private Board board;
        private SearchNode previousSearchNode;
        private int move = 0;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
//        solution = new ArrayList<Board>();
        bar(initial);

    }

    private void bar(Board initial) {
        SearchNode node = new SearchNode();
        node.board = initial;

        SearchNode twin = new SearchNode();
        twin.board = initial.twin();

        MinPQ<SearchNode> queue = new MinPQ<SearchNode>(mcomparator1);
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(mcomparator1);

        queue.insert(node);
        twinQueue.insert(twin);

        Iterable<Board> neighbors = null;
        while (!queue.isEmpty()) {
            node = queue.delMin();

//            solution.add(node.board);
            result = node;

            if (node.board.isGoal()) {
                return;
            }

            neighbors = node.board.neighbors();
            for (Board neighor : neighbors) {
                if (node.previousSearchNode == null || !neighor.equals(node.previousSearchNode.board)) {
                    SearchNode newNode = new SearchNode();
                    newNode.board = neighor;
                    newNode.previousSearchNode = node;
                    newNode.move = node.move + 1;
                    queue.insert(newNode);
                }
            }

            // twin
            twin = twinQueue.delMin();
            if (twin.board.isGoal()) {
//                solution = null;
                result = null;
                return;
            }

            neighbors = twin.board.neighbors();
            for (Board neighor : neighbors) {
                if (twin.previousSearchNode == null || !neighor.equals(twin.previousSearchNode.board)) {
                    SearchNode newNode = new SearchNode();
                    newNode.board = neighor;
                    newNode.previousSearchNode = twin;
                    newNode.move = twin.move + 1;
                    twinQueue.insert(newNode);
                }
            }

        }

    }

    private Comparator<SearchNode> mcomparator1 = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode arg0, SearchNode arg1) {
            return arg0.board.manhattan() + arg0.move - arg1.board.manhattan() - arg1.move;
        }
    };

    // is the initial board solvable?
    public boolean isSolvable() {
        return result != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
    	if(isSolvable()){
    		return result.move;
    	}else{
    		return -1;
    	}
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
    	Stack<Board> s = new Stack<Board>();
    	SearchNode sn = result;
    	
    	s.push(sn.board);
    	while(sn.previousSearchNode != null){
    		s.push(sn.previousSearchNode.board);
    		sn = sn.previousSearchNode;
    	}
        return s;
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

        // System.out.println(initial.hamming());
        // initial.twin();

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