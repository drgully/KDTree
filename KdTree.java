public class KdTree
{
    private Node root;
    private int size;
    
    private class Node
    {
        private final Point2D point;
        private final boolean vert;
        private Node left, right;
        
        public Node(Point2D p, boolean orientation)
        {
            point = p;
            vert = orientation;
        }
        
        public Point2D getPoint()
        {
            return point;
        }
        
        public boolean isVertical()
        {
            return vert;
        }
        
        public void setLeft(Node n)
        {
            left = n;
        }
        
        public void setRight(Node n)
        {
            right = n;
        }
        
        public Node getLeft()
        {
            return left;
        }
        
        public Node getRight()
        {
            return right;
        }
    }
    
    // construct an empty tree of point nodes
    public KdTree()
    {
        root = null;
        size = 0;
    }
    
    // is the tree empty?
    public boolean isEmpty()
    {
        return root == null;
    }
    
    // number of nodes in the tree
    public int size()
    {
        return size;
    }
    
    // add the point p to the tree (if it is not already in the tree)
    public void insert(Point2D p)
    {
        if (isEmpty())
        {
            Node n = new Node(p, true);
            root = n;
            return;
        }
        if (!contains(p))
        {
            Node current = root;
            Node prev = null;
            boolean left = false;
            while (current != null)
            {
                prev = current;
                left = false;
                if (current.isVertical()) // compare by x
                {
                    if (p.x() < current.getPoint().x())
                    {
                        current = current.getLeft();
                        left = true;
                    }
                    else
                    {
                        current = current.getRight();
                    }
                }
                else // compare by y
                {
                    if (p.y() < current.getPoint().y())
                    {
                        current = current.getLeft();
                        left = true;
                    }
                    else
                    {
                        current = current.getRight();
                    }
                }
            }
            
            Node toInsert = new Node(p, !prev.isVertical());
            if (left)
            {
                prev.setLeft(toInsert);
            }
            else
            {
                prev.setRight(toInsert);
            }
            size++;
        }
    }
    
    // does the tree contain the point p?
    public boolean contains(Point2D p)
    {
        if (p != null && !isEmpty())
        {
            Node current = root;
            while (current != null)
            {
                if (current.isVertical()) // compare by x
                {
                    if (p.x() < current.getPoint().x())
                    {
                        current = current.getLeft();
                    }
                    else if (p.x() > current.getPoint().x())
                    {
                        current = current.getRight();
                    }
                    else // equal x, now compare y
                    {
                        if (p.y() == current.getPoint().y())
                        {
                            return true;
                        }
                    }
                }
                else // compare by y
                {
                    if (p.y() < current.getPoint().y())
                    {
                        current = current.getLeft();
                    }
                    else if (p.y() > current.getPoint().y())
                    {
                        current = current.getRight();
                    }
                    else // equal y, now compare x
                    {
                        if (p.x() == current.getPoint().x())
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    // draw all the points to standard draw
    public void draw()
    {
        inorder(root);
    }
    
    private void inorder(Node at)
    {
        if (at == null)
        {
            return;
        }
        else
        {
            inorder(at.getLeft());
            at.getPoint().draw();
            inorder(at.getRight());
        }
    }
    
    // all the points in the tree that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> pointsInRect = new Stack<Point2D>();
        range(root, rect, pointsInRect);
        return pointsInRect;
    }
    
    private void range(Node at, RectHV rect, Stack<Point2D> pointsInRect)
    {
        if (at == null)
        {
            return;
        }
        if (rect.contains(at.getPoint()))
        {
            pointsInRect.push(at.getPoint());
        }
        if (at.isVertical())
        {
            if (at.getPoint().x() < rect.xmin()) // go left
            {
                range(at.getLeft(), rect, pointsInRect);
            }
            if (at.getPoint().x() > rect.xmax()) // go right
            {
                range(at.getRight(), rect, pointsInRect);
            }
        }
        else
        {
            if (at.getPoint().y() < rect.ymin()) // go left
            {
                range(at.getLeft(), rect, pointsInRect);
            }
            if (at.getPoint().y() > rect.ymax()) // go right
            {
                range(at.getRight(), rect, pointsInRect);
            }
        }
    }
    
    // a nearest neighboe in the tree to p; null if tree is empty
    public Point2D nearest(Point2D p)
    {
        if (p != null && !isEmpty())
        {
            Node current = root;
            Node nearest = null;
            while (current != null)
            {
                nearest = current;
                if (current.isVertical()) // compare by x
                {
                    if (p.x() < current.getPoint().x())
                    {
                        current = current.getLeft();
                    }
                    else
                    {
                        current = current.getRight();
                    }
                }
                else // compare by y
                {
                    if (p.y() < current.getPoint().y())
                    {
                        current = current.getLeft();
                    }
                    else
                    {
                        current = current.getRight();
                    }
                }
            }
            return nearest.getPoint();
        }
        return null;
    }
}