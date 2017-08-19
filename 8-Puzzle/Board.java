import java.util.ArrayList;

public class Board {
    private int dim;
    private int [][] blocks;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if(blocks == null)
          throw new java.lang.IllegalArgumentException();
        dim = blocks.length;
        this.blocks = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
            this.blocks[i][j] = blocks[i][j];
    }
    // board dimension n
    public int dimension() {
        return dim;
    }
    // number of blocks out of place
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != (i)*dim + j + 1 && blocks[i][j] != 0)
                    ham++;
            }
        }
        return ham;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int man = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != 0)
                    man += Math.abs(i-(blocks[i][j]-1)/dim) + Math.abs(j-(blocks[i][j]-1) % dim);

            }
        }
        return  man;
    }
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
            twinBlocks[i][j] = blocks[i][j];

        Board twin = new Board(twinBlocks);
        if (twinBlocks[0][0] != 0 && twinBlocks[0][1] != 0)
            twin.swap(0, 0, 0, 1);
        else
            twin.swap(1, 0, 1, 1);
        return twin;
    }
    // does this board equal y?
    public boolean equals(Object ob) {
        if (ob == this)
            return true;

        if (ob == null || ob.getClass() != this.getClass())
            return false;
        Board board = (Board) ob;
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks.length; col++) {
                if (board.blocks[row][col] != blocks[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blanki = 0, blankj = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    blanki = i;
                    blankj = j;
                    break;
                }
            }
        }
        ArrayList<Board> neighbors = new ArrayList<>();
        if (blanki > 0) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blanki, blankj, blanki-1, blankj);
            neighbors.add(neighbor);
        }
        if (blanki < dim-1) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blanki, blankj, blanki+1, blankj);
            neighbors.add(neighbor);
        }
        if (blankj > 0) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blanki, blankj, blanki, blankj-1);
            neighbors.add(neighbor);
        }
        if (blankj < dim-1) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blanki, blankj, blanki, blankj+1);
            neighbors.add(neighbor);
        }
        return neighbors;
    }
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dim).append('\n');
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
                sb.append(' ').append(blocks[i][j]).append(' ');
            sb.append('\n');
        }
        return sb.toString();
    }
    // swaps two boxes
    private void swap(int i, int j, int x, int y) {
        int temp = blocks[i][j];
        blocks[i][j] = blocks[x][y];
        blocks[x][y] = temp;
    }
    public static void main(String[] args) {

    } // unit tests (not graded)
}
