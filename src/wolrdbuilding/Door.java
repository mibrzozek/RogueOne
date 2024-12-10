package wolrdbuilding;

import items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static wolrdbuilding.Palette.r;

public class Door implements Serializable
{
    public List<Point> getPoints() {
        return points;
    }
    public Clearance getClearance()
    {
        return c;
    }
    public void setClearance(Clearance clearance) {
        this.c = clearance;
    }

    public RoomV getRoom() {
        return room;
    }

    public enum Clearance{BLUE, RED, PURPLE, GREEN, GOLD, UNIQUE};

    Point p, dp;
    Direction d;
    Clearance c;
    RoomV room;
    Item uniqueKey;
    
    List<Point> points;
    Direction cardinalBorderDirection =  null;
    
    boolean open;
    // What's the difference between p and dp here???
    public Door(Point p, Point dp, Clearance c)
    {
        points = new ArrayList<>();

        this.cardinalBorderDirection = d;

        this.p = p;
        this.dp = dp;
        this.c = c;
        this.open = false;
        this.uniqueKey = null;

        points.add(p);
        points.add(dp);
    }
    public void setUniqueKey(Item i)
    {
        this.uniqueKey = i;
    }
    public void open()
    {
        open = true;
    }
    public void close()
    {
        open = false;
    }
    public boolean isOpen() { return open; }
    public Point getPoint()
    {
        return p;   
    }
    public void setRoom(RoomV room){this.room = room;}

    public static Clearance getRandomClearance()
    {
        Clearance[] clr = Clearance.values();

        return clr[r.nextInt(clr.length-1)];
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Point)
        {
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
