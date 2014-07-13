public class PointSET
{
    private final SET<Point2D> pointSet;
    
    // construct an empty set of points
    public PointSET()
    {
        pointSet = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty()
    {
        return pointSet.isEmpty();
    }
    
    // number of points in the set
    public int size()
    {
        return pointSet.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p != null)
        {
            pointSet.add(p);
        }
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p)
    {
        return pointSet.contains(p);
    }
    
    // draw all of the points to standard draw
    public void draw()
    {
        //StdDraw.setPenColor(StdDraw.BLACK);
        //StdDraw.setPenRadius(.01);
        for (Point2D point : pointSet)
        {
            point.draw();
        }
        //StdDraw.show(0);
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> inRect = new Stack<Point2D>();
        for (Point2D point : pointSet)
        {
            if (rect.contains(point))
            {
                inRect.push(point);
            }
        }
        return inRect;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p)
    {
        if (!pointSet.isEmpty())
        {
            Point2D nearest = pointSet.min();
            for (Point2D point : pointSet)
            {
                if (p.distanceSquaredTo(point) < p.distanceSquaredTo(nearest))
                {
                    nearest = point;
                }
            }
            return nearest;
        }
        return null;
    }
}