package wolrdbuilding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Point implements Serializable
{
	Random r = new Random();
	
    public int x;
    public int y;
    public int z;
    
    public int w = 0;
    public int h = 0;

    public Point(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void addWidth(int w)
    {
    	this.w = w;
    }
    public void addHeight(int h)
    {
    	this.h = h;
    }
    
    public Point point()
    {
    	return this;
    }
    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
        	return true;
        if (obj == null)
        	return false;
        if (!(obj instanceof Point))
        	return false;
        
        Point other = (Point) obj;
        
        if (x != other.x)
        	return false;
        if (y != other.y)
        	return false;
        if (z != other.z)
        	return false;
        return true;
    }
    public Point getPointInDirection(Direction d)
    {
        Point adjacentPoint = null;

        if(d.equals(Direction.NORTH))
        {
            adjacentPoint = new Point(this.x, this.y -1, this.z);
        }
        else if(d.equals(Direction.SOUTH))
        {
            adjacentPoint = new Point(this.x, this.y + 1, this.z);
        }
        else if(d.equals(Direction.EAST))
        {
            adjacentPoint = new Point(this.x+1, this.y, this.z);
        }
        else if(d.equals(Direction.WEST))
        {
            adjacentPoint = new Point(this.x-1, this.y -1, this.z);
        }

        return adjacentPoint;
    }
    public static Point getPointForCircle(int r, int w, int h)
    {
        Point p = new Point(0,0,0);

        Random re = new Random();
        do
        {
            p = new Point(re.nextInt((w-(r*2))) + r, re.nextInt((h-(r*2))) + r, 0);

        }while(p.x + r > w && p.x-r < 0
                && p.y > h && p.y < 0);

        System.out.println(p.toString() + " that was the new circle point");

        return p;
    }
    public List<Point> gridXbyX(Point p, int boxWidth, int boxHeight)
    {
        List<Point> points = new ArrayList<Point>();

        for (int ox = 0; ox < boxWidth; ox++)
        {
            for (int oy = 0; oy < boxHeight; oy++)
            {
                if (ox < 0 && oy < 0)
                    continue;

                points.add(new Point(x+ox, y+oy, z));
            }
        }

        Collections.shuffle(points);
        return points;
    }
    public List<Point> neighbors8()
    {
        List<Point> points = new ArrayList<Point>();
      
        for (int ox = -1; ox < 2; ox++)
        {
            for (int oy = -1; oy < 2; oy++)
            {
                if (ox == 0 && oy == 0)
                    continue;
        
                points.add(new Point(x+ox, y+oy, z));
            }
        }

        Collections.shuffle(points);
        return points;
    }
    @Override
    public String toString()
    {
    	return "x : " + x + " y : " + y + " z : " + z + "";
    }

    public static Point transform(Direction cardinal, Point np)
    {
        if(cardinal.equals(Direction.NORTH))
        {
            np = new Point(np.x, np.y-1, np.z);
        }
        else if(cardinal.equals(Direction.NORTH_EAST))
        {
            np = new Point(np.x+1, np.y-1, np.z);
        }
        else if(cardinal.equals(Direction.EAST))
        {
            np = new Point(np.x+1, np.y, np.z);
        }
        else if(cardinal.equals(Direction.SOUTH_EAST))
        {
            np = new Point(np.x+1, np.y+1, np.z);
        }
        else if(cardinal.equals(Direction.SOUTH))
        {
            np = new Point(np.x, np.y+1, np.z);
        }
        else if(cardinal.equals(Direction.SOUTH_WEST))
        {
            np = new Point(np.x-1, np.y+1, np.z);
        }
        else if(cardinal.equals(Direction.WEST))
        {
            np = new Point(np.x-1, np.y, np.z);
        }
        else if(cardinal.equals(Direction.NORTH_WEST))
        {
            np = new Point(np.x-1, np.y-1, np.z);
        }

        return np;
    }
}
