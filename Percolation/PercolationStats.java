/******************************************************************************
  *  Name:    Sanjay Khadda
 *
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data
 *                structures to determine the threshold.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] thress;
    private int size;
    private int noOfTrials;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n < 1 || trials < 1) {
            throw new java.lang.IllegalArgumentException("N or T is out of bounds");
        }
        noOfTrials = trials;                       // making global variables to use in other functions
        size = n;
        thress = new double[trials];
        for (int i = 0; i < trials; i++) {
            thress[i] = findPercThres();
        }
    }
    private double findPercThres() {               // find percolation threshold
        Percolation percol = new Percolation(size);
        int a, b;
        int count = 0;
        do {
            a = StdRandom.uniform(size) + 1;
            b = StdRandom.uniform(size) + 1;
            percol.open(a, b);
        } while (!percol.percolates());

        return percol.numberOfOpenSites()/(Math.pow(size, 2));
    }
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(thress);
    }
    public double stddev() {                     // sample standard deviation of percolation threshold
        if (noOfTrials == 1) return Double.NaN;
        return StdStats.stddev(thress);
    }
    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        return mean() - 1.96*stddev()/Math.sqrt(noOfTrials);
    }
    public double confidenceHi() {               // high endpoint of 95% confidence interval
        return mean() + 1.96*stddev()/Math.sqrt(noOfTrials);
    }
    public static void main(String[] args) {        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
