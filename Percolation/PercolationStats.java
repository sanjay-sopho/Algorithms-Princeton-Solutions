/* PercolationStats.java
 * run with an input like java PercolationStats 200 100
 * it gives statistics on the monte carlo simulations 
 */
 
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    
    private double[]    threshold;

    // perform t independent computational experiments on an n-by-n grid
    public PercolationStats(int n, int t) {

        int openCount, row, column;

        if (n <= 0 || t <= 0)
            throw new IllegalArgumentException("Arguments out of bound");

        threshold = new double[t];
        
        for (int i = 0; i < t; i++) {
            Percolation pl = new Percolation(n);
            do {
                row     = StdRandom.uniform(1, n+1);
                column  = StdRandom.uniform(1, n+1);
                if (pl.isOpen(row, column))
                    continue;
                pl.open(row, column);
            } while (!pl.percolates());

            threshold[i] = (double) pl.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    private double halfInterval() {
        return 1.96 * stddev() / Math.sqrt(threshold.length);
    }
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - halfInterval();
    }
    
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + halfInterval();
    }
    
    // test client, described below
    public static void main(String[] args) {
        PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));

        System.out.printf("mean                     = %f\n", pls.mean());
        System.out.printf("stddev                   = %f\n", pls.stddev());
        System.out.printf("95%% confidence Interval  = [%f, %f]\n",
                pls.confidenceLo(), pls.confidenceHi());
    }
}
