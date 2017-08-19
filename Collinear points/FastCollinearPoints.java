import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segmentList = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException();
        Point[] aux = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {

            Point p = points[i];
            Arrays.sort(aux);
            Arrays.sort(aux, p.slopeOrder());

            int min = 0;
            while (min < aux.length && p.slopeTo(aux[min]) == Double.NEGATIVE_INFINITY) min++;
            if (min != 1) throw new IllegalArgumentException();// check duplicate points
            int max = min;
            while (min < aux.length) {
                while (max < aux.length && p.slopeTo(aux[max]) == p.slopeTo(aux[min])) max++;
                if (max - min >= 3) {
                    Point pMin = aux[min].compareTo(p) < 0 ? aux[min] : p;
                    Point pMax = aux[max - 1].compareTo(p) > 0 ? aux[max - 1] : p;
                    if (p == pMin)
                        segmentList.add(new LineSegment(pMin, pMax));
                }
                min = max;
            }
        }
    }
    public int numberOfSegments() {        // the number of line segments
        return segmentList.size();
    }
    public LineSegment[] segments() {               // the line segments
        LineSegment[] segments = new LineSegment[segmentList.size()];
        return segmentList.toArray(segments);
    }
}
