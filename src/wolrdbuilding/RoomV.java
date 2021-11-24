package wolrdbuilding;

import java.util.ArrayList;
import java.util.List;

public class RoomV
{
    private RoomPoint rp;
    private TileSet ts;

    private List<Door> doorPoints;
    private List<Point> floorPoints;
    private List<Point> wallPoints;

    private Door.Clearance clearance;

    public RoomV(RoomPoint rp, TileSet ts)
    {
        this.rp = rp;
        this.ts = ts;

        doorPoints = new ArrayList<>();
        floorPoints = new ArrayList<>();
        wallPoints = new ArrayList<>();

        List<Point> floors = rp.gridXbyX(new Point(rp.x, rp.y, rp.z), rp.w, rp.h);
        floorPoints.addAll(floors);
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

    public void addRoomPoints(ArrayList<Point> doorCandidates)
    {
        this.wallPoints = doorCandidates;
    }

    public void calculateClearance(TileV[][][] tiles)
    {
        doorPoints.clear();
        int doors = 0;
        System.out.println("Wall points " + wallPoints.size());

        for(Point p : wallPoints)
        {
            System.out.println("\tWall point " + p.toString());
            if(tiles[p.x][p.y][p.z].getTile().isDoor())
            {
                doors++;
                doorPoints.add(new Door(p, null, Door.Clearance.GOLD));

            }
            tiles[p.x][p.y][p.z] = new TileV(Tile.GRASS_1);
        }
        System.out.println("counted door " + doors + "doorPoints : " + doorPoints.size());

        if(doors == 0)
            this.clearance = Door.Clearance.PURPLE;
        else if(doors == 1)
            this.clearance = Door.Clearance.RED;
        else
            this.clearance = Door.Clearance.GOLD;

        for(Door d : doorPoints)
        {
            d.setClearance(this.clearance);
        }
    }

    public void setWallPoints(ArrayList<Point> doorCandidates)
    {
        this.wallPoints = doorCandidates;
    }
}
