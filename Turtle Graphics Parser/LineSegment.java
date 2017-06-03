/**
 * Jarred Price
 *
 * This is a turtle program that is used with Parser.java
 * to draw the Turtle commands to the Turtle Canvas.
 *
 */

/**
 * A line segment in pixel space.
 */
public class LineSegment {

    public final Point start;
    public final Point end;
    public final PenColor color;

    /**
     * Construct a line segment from coordinate pairs.
     * 
     * @param startx x-coordinate of start point
     * @param starty y-coordinate of start point
     * @param endx x-coordinate of end point
     * @param endy y-coordinate of end point
     * @param color line segment color
     */
    public LineSegment(double startx, double starty, double endx, double endy, PenColor color) {
        this.start = new Point(startx, starty);
        this.end = new Point(endx, endy);
        this.color = color;
    }

    /**
     * Construct a line segment from start and end points.
     * 
     * @param start one end of the line segment
     * @param end the other end of the line segment
     * @param color line segment color
     */
    public LineSegment(Point start, Point end, PenColor color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    /**
     * Compute the length of this segment.
     * 
     * @return the length of the line segment
     */
    public double length() {
        return Math.sqrt(Math.pow(this.start.x - this.end.x, 2.0)
                + Math.pow(this.start.y - this.end.y, 2.0));
    }
}
