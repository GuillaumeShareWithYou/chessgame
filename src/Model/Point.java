
package Model;

public class Point {
   public int x,y;
    public Point(int _x, int _y)
    {
        this.x = _x;
        this.y = _y;
    }
    public Point(Point p)
    {
        this.x = p.x;
        this.y = p.y;
    }
    public String toString()
    {
        return "("+this.x+" , "+this.y+")";
    }
    public boolean equals(Point p)
    {
        return (this.x == p.x) && (this.y == p.y);
    }
}
