package wolrdbuilding;

import entities.Entity;

import java.util.ArrayList;
import java.util.List;

import static wolrdbuilding.Palette.r;

public class Door
{
    public List<Point> getPoints() {
        return points;
    }

    public Clearance getClearance()
    {
        return c;
    }

    public enum Clearance{BLUE, RED, PURPLE};

    Point p, dp;
    Direction d;
    Clearance c;
    
    List<Point> points;
    
    boolean open;

    public Door(Point p, Point dp, Clearance c)
    {
        points = new ArrayList<>();
        this.p = p;
        this.dp = dp;
        this.c = c;
        this.open = false;
        
        points.add(p);
        points.add(dp);
    }
    public void open()
    {
        open = true;
    }
    public void close()
    {
        open = false;
    }
    public Point getPoint()
    {
        return p;   
    }

    public static Clearance getRandomClearance()
    {
        Clearance[] clr = Clearance.values();

        return clr[r.nextInt(clr.length-1)];
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            if (points.contains((Point) o))
                return true;
            else
                return false;
        } else if (o instanceof Door) {
            if (points.contains(((Door) o).getPoint()))
                return true;
            else
                return false;
        } else
            return false;
    }
    
}
