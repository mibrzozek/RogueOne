package wolrdbuilding;

import structures.Dungeon;

import java.util.ArrayList;
import java.util.List;

public class RoomV
{
    private RoomPoint rp;
    private TileSet ts;

    private List<Door> doorPoints;
    private List<Point> floorPoints;
    private List<Point> wallPoints;
    private Boolean identified;

    private Door.Clearance clearance;

    public RoomV(RoomPoint rp, TileSet ts)
    {
        this.rp = rp;
        this.ts = ts;
        this.identified = false;

        doorPoints = new ArrayList<>();
        floorPoints = new ArrayList<>();
        wallPoints = new ArrayList<>();

        List<Point> floors = rp.gridXbyX(new Point(rp.x, rp.y, rp.z), rp.w, rp.h);
        floorPoints.addAll(floors);
        List<Point> walls = new ArrayList<>();
        for(Point p : floors)
        {
            if(p.x == rp.x || p.y == rp.y || p.x == rp.x + rp.w -1 || p.y == rp.y + rp.h -1)
            {
                walls.add(p);
            }
        }
        wallPoints = walls;
    }
    public Door.Clearance getClearance()
    {
        return clearance;
    }
    public void addDoor(Door d)
    {
        doorPoints.add(d);
    }
    public RoomPoint getRoomPoint()
    {
        return rp;
    }
    public TileSet getTileSet()
    {
        return ts;
    }
    public List<Door> getDoorPoints()
    {
        return doorPoints;
    }

    public List<Point> getFloorPoints()
    {
        return floorPoints;
    }
    public List<Point> getWallPoints()
    {
        return wallPoints;
    }

    public void addWallPoints(ArrayList<Point> doorCandidates)
    {
        this.wallPoints = doorCandidates;
    }

    public void calculateClearance(Dungeon dungeon)
    {
        TileV[][][] tiles = dungeon.getTiles();
        int doors = 0;
        System.out.println("Wall points size: " + wallPoints.size() + " rp point " + rp.point().toString());

        for(Point p : this.getWallPoints())
        {
            //tiles[p.x][p.y][p.z] = new TileV(Tile.WOOD_WALL);

            //System.out.println("\tWall point " + p.toString());
            if(tiles[p.x][p.y][p.z].getTile().isDoor())
            {
                doors++;
                Door d = new Door(p, null, Door.Clearance.GOLD);
                d.setRoom(this);
                doorPoints.add(d);
            }
            //tiles[p.x][p.y][p.z] = new TileV(Tile.GRASS_1);
        }
        if(doors == 0)
        {
            this.clearance = Door.Clearance.PURPLE;
        }
        else if(doors == 1)
        {
            this.clearance = Door.Clearance.RED;
            dungeon.getRedRooms().add(this);
        }
        else if(doors == 2)
        {
            this.clearance = Door.Clearance.GREEN;
            dungeon.getGreenRooms().add(this);

        }
        else
        {
            this.clearance = Door.Clearance.GOLD;
            dungeon.getGoldRooms().add(this);
        }
        for(Door d : doorPoints)
        {
            d.setClearance(this.clearance);
        }
        //System.out.println("counted door " + doors + "doorPoints : " + doorPoints.size() + clearance + " " + "clearcalc");\
    }
    public void setIdentified(Boolean id){this.identified = id;}
    public boolean isIdentified() { return identified;}
}
