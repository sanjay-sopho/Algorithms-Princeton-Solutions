import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int moves;
        
        
        public Node(Board board, Node prev, int moves) {
            this.moves = moves;
            this.board = board;
            this.prev  = prev;
        }
        public int compareTo(Node that) {
            return this.board.manhattan() + this.moves - that.board.manhattan()-that.moves;
        }
    }
    private MinPQ<Node> pq;
    private MinPQ<Node> twin;
    private int moves = 0;
    private ArrayList<Board> solution;
    private boolean isSolvable = true;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        pq = new MinPQ<>();
        twin = new MinPQ<>();
        pq.insert(new Node(initial, null, 0));
        twin.insert(new Node(initial.twin(), null, 0));
        while (true) {
            Node min =  pq.delMin();
            if (min.board.isGoal()) {
                isSolvable = true;
                moves = min.moves;
                solution = new ArrayList<>();
                while (min != null) {
                    solution.add(0, min.board);
                    min = min.prev;
                }
                break;
            }
            for (Board bo: min.board.neighbors()) {
                if (min.prev == null || !bo.equals(min.prev.board))
                    pq.insert(new Node(bo, min, min.moves + 1));
            }
            
            min = twin.delMin();
            if (min.board.isGoal()) {
                isSolvable = false;
                moves = -1;
                solution = null;
                break;
            }
            for (Board bo: min.board.neighbors()) {
                if (min.prev == null || !bo.equals(min.prev.board))
                    twin.insert(new Node(bo, min, min.moves + 1));
            }
        }
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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
            StdOut.println(initial.manhattan());
        }
    }
}
