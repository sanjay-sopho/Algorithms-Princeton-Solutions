/******************************************************************************
 *  Name:    Sanjay Khadda
 *
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data
 *                structures to determine the threshold.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private WeightedQuickUnionUF wqu;

    private boolean[] openSites;

    private int num;

    private int noOfElements;

    private int noOfOpenSites = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        noOfOpenSites = 0;
        num = n;                            // for use in later functions
        noOfElements = n*n+2;          // calculating it once and for all to save time calculating again and again
        wqu = new WeightedQuickUnionUF(noOfElements);
        openSites = new boolean[noOfElements];
        for (int i = 1; i < noOfElements-1; i++) {
            openSites[i] = false;
        }
        openSites[0] = true;                  // virtual top site opened
        openSites[noOfElements-1] = true;     // virtual bottom site opened

    }
    // open site (row, col) if it is not open already
    public  void open(int row, int col) {
        if (row <= 0 || row > num)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > num)
            throw new IndexOutOfBoundsException("col index j out of bounds");


        if (!isOpen(row, col)) {
            int index = ((row-1) * num) + col;

            openSites[index] = true;   // opening the site

            noOfOpenSites++;

            if (row == 1) {                             // connecting to virtual top if  in first row
                wqu.union(0, index);
            }
            if (row == num) {
                wqu.union(index, noOfElements-1);     // connecting to virtual bottom
            }
            if (row != 1 && isOpen(row-1, col)) {         // connecting to upper element if  open
                wqu.union(index, ((row-2)*num)+col);
            }
            if (row != num && isOpen(row+1, col)) {         // connecting to lower element if  open
                wqu.union(index, ((row)*num)+col);
            }
            if (col != 1 && isOpen(row, col-1)) {         // connecting to left element if   open
                wqu.union(index, ((row-1)*num)+col-1);
            }
            if (col != num && isOpen(row, col+1)) {         // connecting to right element if  open
                wqu.union(index, ((row-1)*num)+col+1);
            }
        }
    }
    public boolean isOpen(int row, int col) {     // is site (row, col) open?
        if (row <= 0 || row > num)
            throw new IndexOutOfBoundsException("row  out of bounds");
        if (col <= 0 || col > num)
            throw new IndexOutOfBoundsException("col  out of bounds");

        return openSites[(row-1)*num + col];
    }
    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row <= 0 || row > num)
            throw new IndexOutOfBoundsException("row  out of bounds");
        if (col <= 0 || col > num)
            throw new IndexOutOfBoundsException("col out of bounds");

        return wqu.connected(0, ((row-1)*num) + col);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return noOfOpenSites;
    }
    public boolean percolates() {  // does the system percolate?
        return wqu.connected(0, noOfElements-1);
    }
}
