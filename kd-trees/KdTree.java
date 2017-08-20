import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
  private static final boolean HORIZONTAL = true;
private static final boolean VERTICAL = false;
    private static class Node {
        private Point2D point;
        private boolean HV;
        private RectHV rect;
        private Node left;
        private Node right;
        private int size;
        private Node(Point2D point, boolean HV) {
            this.left = null;
            this.HV = HV;
            this.right = null;
            this.point = point;
            this.size = 1;
        }
    }
    private Node root;
    private Point2D closest;
    private double cDist;

    public KdTree() {
        // construct an empty set of points
        root = null;
    }
    // number of points in the set
    public int size() {
        return size(root)-1;
    }
    private int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }
    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(!contains(p))
        root = insert(root, p, VERTICAL);
    }
    private Node insert(Node root, Point2D p, boolean HV) {
        if (root == null) return new Node(p, HV);
        if (p.compareTo(root.point) == 0) return root;

        double cmp;
        if (root.HV == VERTICAL)
            cmp = p.x() - root.point.x();
        else
            cmp = p.y() - root.point.y();

        if (cmp < 0)
            root.left = insert(root.left, p, !HV);
        else
            root.right = insert(root.right,p,  !HV);

        root.size = 1 + size(root.left) + size(root.right);
        return root;
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        Node x = contains(root, p);
        return x != null;
    }

    private Node contains(Node x, Point2D p) {
        if (x == null) return null;

        if (p.compareTo(x.point) == 0) return x;

        double cmp;
        if (x.HV == VERTICAL)
            cmp = p.x() - x.point.x();
        else
            cmp = p.y() - x.point.y();

        if (cmp < 0)
            return contains(x.left, p);
        else
            return contains(x.right, p);
    }
    // draw all points to standard draw
    public              void draw() {
        draw(root);
    }
    private void draw(Node x) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        x.point.draw();
        if (x.HV == VERTICAL) {
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.line(x.point.x(), 0, x.point.x(), 1);
        } else {
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.line(0, x.point.y(), 1, x.point.y());
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        ArrayList<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }
    private void range(Node x, RectHV rect, ArrayList<Point2D> points) {
        if (x == null) return;
        if (rect.contains(x.point))
            points.add(x.point);

        if (x.HV == VERTICAL) {
            if (rect.xmax() < x.point.x())
                range(x.left, rect, points);
            else if (rect.xmin() >= x.point.x())
                range(x.right, rect, points);
            else {
                range(x.left, rect, points);
                range(x.right, rect, points);
            }
        } else {
            if (rect.ymax() < x.point.y())
                range(x.left, rect, points);
            else if (rect.ymin() >= x.point.y())
                range(x.right, rect, points);
            else {
                range(x.left, rect, points);
                range(x.right, rect, points);
            }
        }
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p) {
        if (p == null){
            throw new java.lang.NullPointerException();
        }

        closest = null;
        cDist = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return closest;
    }

    private void nearest(Node x, Point2D p) {
        if (x == null) return;

        double distance = p.distanceTo(x.point);
        if (distance < cDist) {
            cDist = distance;
            closest = x.point;
        }

        if (x.HV == VERTICAL) {
            if (p.x() < x.point.x()) {
                nearest(x.left, p);
                if (cDist >= x.point.x() - p.x())
                    nearest(x.right, p);
            } else {
                nearest(x.right, p);
                if (cDist >= p.x() - x.point.x())
                    nearest(x.left, p);
            }
        } else {
            if (p.y() < x.point.y()) {
                nearest(x.left, p);
                if (cDist >= x.point.y() - p.y())
                    nearest(x.right, p);
            } else {
                nearest(x.right, p);
                if (cDist >= p.y() - x.point.y())
                    nearest(x.left, p);
            }
        }
    }
}
