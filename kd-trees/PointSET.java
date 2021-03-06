import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.ArrayList;

public class PointSET {

    private TreeSet<Point2D> set;
    public         PointSET() {
        set =  new TreeSet<>();
    }                               // construct an empty set
    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }
    // number of points in the set
    public int size() {
        return set.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
            if (p == null) {
                throw new java.lang.NullPointerException();
            }
            set.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        return set.contains(p);
    }
    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D point  : set) {
            if (rect.contains(point)) {
                points.add(point);
            }
        }
        return points;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null){
            throw new java.lang.NullPointerException();
        }
        Point2D closest = null;
        double cDist = Double.POSITIVE_INFINITY;
        for (Point2D point: set) {
            double dist = point.distanceSquaredTo(p);
            if (dist < cDist) {
                closest = point;
                cDist = dist;
            }
        }
        return closest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
