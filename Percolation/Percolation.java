import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    
    private WeightedQuickUnionUF grid, auxGrid;
    private boolean[]   state;
    private int numberOfColumns;

    // creates numberOfColumns  X numberOfColumns  grid, with all sites blocked intially
    public Percolation(int numberOfColumns) {

        int numberOfSites = numberOfColumns  * numberOfColumns;
        this.numberOfColumns  = numberOfColumns;

        // virtual top and bottom sites
        grid    = new WeightedQuickUnionUF(numberOfSites + 2);
        auxGrid = new WeightedQuickUnionUF(numberOfSites + 1);
        state   = new boolean[numberOfSites + 2];

        // Blocking all sites initially
        for (int i = 1; i <= numberOfSites; i++)
            state[i] = false;
        // Initialize virtual top and bottom site with open state
        state[0] = true;
        state[numberOfSites+1] = true;
    }

    // return array index of given row i and column j
    private int xyToIndex(int i, int j) {
        if (i <= 0 || i > numberOfColumns) 
            throw new IndexOutOfBoundsException("row i out of bound");
        if (j <= 0 || j > numberOfColumns) 
            throw new IndexOutOfBoundsException("column j out of bound");

        return (i - 1) * numberOfColumns  + j;
    }
    
    private boolean isTopSite(int index) {
        return index <=  numberOfColumns;
    }

    private boolean isBottomSite(int index) {
        return index >= (numberOfColumns - 1) * numberOfColumns + 1;
    }

    // return number of open sites
    public int numberOfOpenSites() {
        int openSites = 0;
        for (int i = 1; i < numberOfColumns*numberOfColumns+1; i++) {
            if (state[i])
                openSites++;
        } 
        return openSites;
    }

    // open site i,j
    public void open(int i, int j) {
        int idx = xyToIndex(i, j);
        state[idx] = true;

        // Traverse surrounding sites, connect all open ones. 
        // Make sure we do not index sites out of bounds.
        if (i != 1 && isOpen(i-1, j)) {
            grid.union(idx, xyToIndex(i-1, j));
            auxGrid.union(idx, xyToIndex(i-1, j));
        }
        if (i != numberOfColumns  && isOpen(i+1, j)) {
            grid.union(idx, xyToIndex(i+1, j));
            auxGrid.union(idx, xyToIndex(i+1, j));
        }
        if (j != 1 && isOpen(i, j-1)) {
            grid.union(idx, xyToIndex(i, j-1));
            auxGrid.union(idx, xyToIndex(i, j-1));
        }
        if (j != numberOfColumns  && isOpen(i, j+1)) {
            grid.union(idx, xyToIndex(i, j+1));
            auxGrid.union(idx, xyToIndex(i, j+1));
        }
        // if site is on top or bottom, connect to corresponding virtual site.
        if (isTopSite(idx)) {
            grid.union(0, idx);
            auxGrid.union(0, idx);
        }
        if (isBottomSite(idx))  grid.union(state.length-1, idx);
    }

    // check if site is open
    public boolean isOpen(int i, int j) {
        int idx = xyToIndex(i, j);
        return state[idx];
    }

    // check if site is connected to virtual top
    public boolean isFull(int i, int j) {
        int idx = xyToIndex(i, j);
        return grid.connected(0, idx) && auxGrid.connected(0, idx);
    }

    // if percolation occurs
    public boolean percolates() {
        // Check whether virtual top and bottom sites are connected
        return grid.connected(0, state.length-1);
    }
}
