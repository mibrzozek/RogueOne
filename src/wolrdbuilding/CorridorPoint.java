package wolrdbuilding;

public class CorridorPoint extends Point
{
    private Direction dOfWall, dOfHall;
    Point p;
    private int hallLength;

    public CorridorPoint(Point p)
    {
        super(p.x, p.y, p.z);
        this.p = p;
    }

    public void checkHallLength(int moveX, int moveY, Tile[][][] tiles) {
        int mx = x;
        int my = y;
        System.out.println("Starting Loop in " + dOfWall + " " + moveX + " " + moveY);
        System.out.println(" " + tiles[mx + moveX][my + moveY][z]);
    }
    public boolean isCorridor(TileV[][][] tiles)
    {
        boolean corridor = false;

        if(tiles[x + 1][y][z].getTile().isRoom())
        {
            dOfWall = Direction.WEST;
            dOfHall = Direction.WEST;
            //System.out.println(dOfHall);
        }
        else if(tiles[x - 1][y][z].getTile().isRoom())
        {
            dOfWall = Direction.EAST;
            dOfHall = Direction.EAST;
            //System.out.println(dOfHall);
        }
        else if(tiles[x][y + 1][z].getTile().isRoom())
        {
            dOfWall = Direction.NORTH;
            dOfHall = Direction.NORTH;
            //System.out.println(dOfHall);
        }
        else if(tiles[x][y - 1][z].getTile().isRoom())
        {
            dOfWall = Direction.SOUTH;
            dOfHall = Direction.SOUTH;
            //System.out.println(dOfHall);
        }

        //System.out.println(checkSpaceInDirection(tiles));

        if(checkSpaceInDirection(tiles) > 1 && checkSpaceInDirection(tiles) < 16)
            corridor = true;
        //System.out.println(hallLength + " length and " + corridor);

        return corridor;
    }
    public void checkHallLength(int moveX, int moveY, TileV[][][] tiles)
    {
        int mx = x;
        int my = y;
        //System.out.println("Starting Loop in " + dOfWall + " " + moveX + " " +  moveY);
        //System.out.println(" " + tiles[mx + moveX][my + moveY][z]);
        while(tiles[mx + moveX][my + moveY][z].isGround() && mx < 199 && mx > 1 && my < 199 && my > 1)
        {
            hallLength++;
            mx += moveX;
            my += moveY;
            System.out.println("From check hall length " +  tiles[mx + moveX][my + moveY][z].isGround());
            //System.out.println("From check hall length " +  tiles[mx + moveX][my + moveY][z].isGround());
        }


    }
    public int checkSpaceInDirection(TileV[][][] tiles)
    {
        if(dOfHall == null)
            return 0;

        if(dOfHall.equals(Direction.NORTH))
        {
            checkHallLength(0, -1, tiles);
        }
        else if(dOfHall.equals(Direction.SOUTH))
        {
            checkHallLength(0, 1, tiles);
        }
        else if(dOfHall.equals(Direction.EAST))
        {
            checkHallLength(1, 0, tiles);
        }
        else if(dOfHall.equals(Direction.WEST))
        {
            checkHallLength(-1, 0, tiles);
        }
        System.out.print("Check space ");
        //System.out.print("Check space ");
        return hallLength;
    }
    public static boolean isCorridor(Point p, Tile[][][] tiles)
    {


        return false;
    }
    public Point getPoint()
    {
        return p;
    }
    public int getHallLength()
    {
        return hallLength;
    }
    public Direction getdOfHall() {
        return dOfHall;
    }

    public Direction getdOfWall() {
        return dOfWall;
    }

    public void setHallLength(int l)
    {
        this.hallLength = l;
    }
    public void setDofWall(Direction d)
    {
        this.dOfWall = d;
    }
    public void setDofHall(Direction d)
    {
        this.dOfHall = d;
    }
}
