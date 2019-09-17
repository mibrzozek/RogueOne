package wolrdbuilding;

import java.util.ArrayList;
import java.util.Random;

public class Fire
{
    // Passed in through constructor
    private Point p, newestPoint;
    private int ticks = 0; // tracks turns taken since fire started
    private int divisor = 8;
    private int incrementor  = divisor;
    private int inTick = 0;

    // Set up randomly
    private Point center;
    private ArrayList<Point> firePoints;
    private Random r = new Random();

    private Direction d;
    private World w;

    public Fire(Point p, World w)
    {
        this.w = w;
        this.p = p;
        this.firePoints = new ArrayList<>();
        this.center = p;
        this.newestPoint = p;

        this.d = Direction.getRandomDirection();

        firePoints.add(p);
    }
    public Point getPoint()
    {
        return this.p;
    }

    public ArrayList<Point> getFirePoints() {
        return firePoints;
    }

    public boolean isLit()
    {
        if(ticks < 600)
            return true;
        else
            return false;
    }
    public void blaze()
    {
        ticks++;
        Point np = new Point(0, 0, 0);

        // Change direction every time new box formed?
        if(isLit())
        {
            if (ticks % divisor == incrementor)
            {
                incrementor *= 2;
                d = Direction.getRandomDirection();
                if (incrementor > 200)
                    incrementor = 8;
            }
            if (ticks % 6 == 0) // spreads every 5 ticks
            {
                d = Direction.getRandomDirection();

                np = newestPoint;
                boolean found = false;
                int tickss = 0;
                do
                {
                    if (d == Direction.NORTH || d == Direction.SOUTH) // Increment x or y
                    {
                        np = new Point(np.x, np.y + d.getMovement(d), p.z);
                    }
                    else
                    {
                        np = new Point(np.x + d.getMovement(d), np.y, p.z);
                    }
                    if(w.tile(np.x, np.y, np.z).getTile().isGround() && !w.tile(np.x, np.y, np.z).getTile().isFire())
                    {
                        found = true;
                    }

                    if(tickss > 100)
                    {

                        np =  null;
                        found = true;
                    }
                    tickss++;
                    System.out.println("stuck maybe?");
                } while (!found);
                if(tickss < 100)
                {
                    firePoints.add(np);
                    w.changeTile(np, Tile.getFire(), false);
                }
                    newestPoint = np;


            }
        }
        else // fire is going out
        {
            if(!firePoints.isEmpty()) {
                np = center;
                w.changeTile(np, Tile.BLASTED_TERRAIN, false);

                np = firePoints.get(firePoints.size() - 1);

                d = Direction.getRandomDirection();
                // start at center and put fire out
                //
                if (d == Direction.NORTH || d == Direction.SOUTH) // Increment x or y
                {
                    np = new Point(np.x, np.y, p.z);
                } else {
                    np = new Point(np.x, np.y, p.z);
                }
                firePoints.remove(firePoints.size() - 1);
                newestPoint = np;

                w.changeTile(np, Tile.getBurnedTile(), false);
            }
        }

        System.out.println(ticks +  " duration of fire!");

        System.out.println(firePoints.size() + " we're blazing!");
    }
    @Override
    public boolean equals(Object o)
    {
        Fire f;
        if(o instanceof Fire)
            f = (Fire)o;
        else return false;

        if(f.center.equals(this.center))
            return true;
        else return false;


    }


}
