package structures.TerrainGen;

import puzzlelike.Puzzle;
import wolrdbuilding.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapUtility
{
    public static List<Point> returnPointsBetween(Point p, Point op)
    {
        return null;
    }

    //
    private static boolean allTilesAreThis(Tile wall, List<Point> shouldBeWall, TileV[][][] tiles)
    {
        Boolean truth = true;

        //System.out.println(shouldBeWall.size()  + "grorp");
        for(Point p : shouldBeWall)
        {
            if(!tiles[p.x][p.y][p.z].getTile().equals(wall))
            {
                //System.out.println(tiles[p.x][p.y][p.z].getTile() + " Should be a wall");
                truth = false;
            }
        }
        return truth;
    }
    public static List<Point> tunnelInDirection(TileV[][][] tiles, Point p, Direction d, int width, int height, int depth, int offsetX, int offsetY)
    {
        Point np = p.getPointInDirection(d);

        if(!isInBounds(np, width  + offsetX, height + offsetY, depth, offsetX, offsetY))
        {
            return null;
        }
        //tiles[np.x][np.y][np.z] = new TileV(Tile.DIRT_FLOOR);

        List<Point> excavated = new ArrayList<Point>();
        excavated.add(np);
        do
        {
            np = np.getPointInDirection(d);
            if(isInBounds(np, width+ offsetX, height + offsetY, depth, offsetX, offsetY))
            {
                //tiles[np.x][np.y][np.z] = new TileV(Tile.DIRT_FLOOR);
                excavated.add(np);
            }
        }while(isInBounds(np.getPointInDirection(d), width + offsetX, height + offsetY, depth, offsetX, offsetY));

        return excavated;
        //return tiles;
    }
    public static Boolean isInBounds(Point p, int width, int height, int depth, int offsetX, int offsetY)
    {
        if(p.x < 0 + offsetX || p.y < 0 + offsetX || p.x > width + offsetX|| p.y > height + offsetX)
            return false;
        else
            return true;
    }
    public static List<Point> getClosestBorderPoint(int width, int height, int depth, TileV[][][] tiles, Point p, int offsetX, int offsetY)
    {
        Direction closestBorder = null;

        //Padding for the border
        int padding  = 10;

        if(p.x > width - 1 + offsetX - padding)
        {
            closestBorder = Direction.EAST;
        }
        else if(p.x < padding)
        {
            closestBorder = Direction.WEST;
        }
        else if(p.y > height - 1 + offsetY - padding)
        {
            closestBorder = Direction.SOUTH;
        }
        else if(p.y < padding)
        {
            closestBorder = Direction.NORTH;
        }

        if(closestBorder != null)
        {
            return tunnelInDirection(tiles, p, closestBorder, width, height, depth, offsetX, offsetY);
        }
        else
        {
            return null;
        }
    }
    public static void lookForInDirectionFrom(Tile t, Direction d, Point p,Boolean branch, int depth)
    {

    }
    public static ArrayList<Point> getBorderPoints(int width, int height, int depth, TileV[][][] tiles, int padding, int offsetX, int offsetY)
    {
        // When padding is one, list will contain only points directly next to the border
        // With padding at 2, list will contain the outer most layers of the border
        List<Point> borderPoints = new ArrayList<Point>();

        for(int x = 0 + offsetX; x < width + offsetX; x++)
        {
            for(int y = 0 + offsetY; y < height + offsetY; y++)
            {
                for(int z = 0; z < depth; z++)
                {
                    if(x == 0 + offsetX || x == width + offsetX || x  == width + offsetX-1)
                    {
                        borderPoints.add(new Point(x , y, z));
                    }
                    if(y == 0 + offsetY || y  == height + offsetY || y  == height + offsetY-1)
                    {
                        borderPoints.add(new Point(x , y , z));
                    }
                }
            }
        }
        System.out.println(borderPoints);
        return (ArrayList<Point>) borderPoints;
    }
    public static TileV[][][] fillPointsWithTile(TileV[][][] tiles, List<Point> points, Tile t, int offsetX, int offsetY)
    {
        for(Point p: points)
        {
            tiles[p.x + offsetX][p.y + offsetY][p.z] = new TileV(t);
        }
        return tiles;
    }
    public static TileV[][][] addPuzzleToTerrain(TileV[][][] tiles, Puzzle puzzle, int width, int height, int depth, int offsetX, int offsetY)
    {
        /*
            1. Populate candidates w/ all the border points of the new Puzzle area
            2. Iterate through candidates to find a point that's a wall
                a. If point is a wall, check neighbors and look for floor
                b. If candidate has floorPoint next to it, save first find in P variable
                c. Add all candidates that have floor next to them to narrowedCandidates
            3. Pass narrowedCandidates to placeEscapeDoor()
         */
        System.out.println(puzzle.problem().toString());
        if(puzzle.problem().toString() == "LOCK_AND_KEY")
        {
            Point p = new Point(0, 0, 0);

            Boolean found  = false;
            ArrayList<Point> candidates = getBorderPoints(width, height, depth, tiles, 5, offsetX, offsetY);
            ArrayList<Point> narrowedCandidates = new ArrayList<>();

            Collections.shuffle(candidates);
            for(Point cp : candidates) // Find wall point with floor next to it
            {
                System.out.println(candidates.size());
                System.out.println("Sire:" + cp.toString());
                if(tiles[cp.x][cp.y][cp.z].getTile().isWall())
                {
                    for(Point np : cp.neighbors8())
                    {
                        if(np.x + offsetX < 0 + offsetX || np.y + offsetY < 0 + offsetY || np.x + offsetX > width + offsetX -1 || np.y + offsetY > height  + offsetY -1)
                        {
                            // if we make neighbors8 return only positive points this will increase efficiency
                            // skipping potentially out of bounds points
                            continue;
                        }
                        //System.out.println(np.toString());
                        if(tiles[np.x + offsetX][np.y + offsetY][np.z].getTile().isFloor())
                        {
                            if(found == false)
                            {
                                p = cp;
                                found = true;
                                narrowedCandidates.add(cp);
                            }
                            else
                            {
                                narrowedCandidates.add(cp);
                            }
                        }
                    }
                }
            }
            System.out.println(narrowedCandidates.size() + "narrowed candidates");
            placeEscapeDoor(tiles, narrowedCandidates, width, height, depth, puzzle, offsetX, offsetY);
            //fillPointsWithTile(tiles, narrowedCandidates, Tile.ROCK_0, offsetX, offsetY);
        }
        return tiles;
    }
    public static void placeEscapeDoor(TileV[][][] tiles, List<Point> siteCandidates, int width, int height, int depth, Puzzle puzzle, int offsetX, int offsetY)
    {
        /*
            1. Shuffle and iterate through candidates
         */
        boolean found = false;
        if(siteCandidates.isEmpty())
        {
            System.out.println("Site candidates empty");
            // search again i tile closer to the inside then the previous search
        }
        else
        {
            Collections.shuffle(siteCandidates);

            Point p = siteCandidates.get(new Random().nextInt(siteCandidates.size()));

            Direction d = null;

            if(p.x == 0 +offsetX )
            {
                d = Direction.EAST;
            }
            if( p.x == width - 1 + offsetX)
            {
                d = Direction.WEST;
            }
            if(p.y == 0 + offsetY )
            {
                d = Direction.NORTH;
            }
            if(p.y == height-1 + offsetY)
            {
                d = Direction.SOUTH;
            }

            tiles[p.x][p.y][p.z] = new TileV(Tile.CLOSED_DOOR);
            puzzle.addLockedDoor(new Door(p, p, Door.Clearance.UNIQUE), d);
            System.out.println("This is the one true door:" + p.toString());

            found = true;
        }
    }
        /*
        for(Point p :  siteCandidates)
        {
            System.out.println("Site! :" + p.toString());
            if (found) {
                continue;
            }
            List<Point> shouldBeWall = new ArrayList<>();
            shouldBeWall = getClosestBorderPoint(width, height, depth, tiles, p, offsetX, offsetY);

            if(shouldBeWall == null)
                continue;

            System.out.println(shouldBeWall.size() + "Should be wall size:p");

            //if(allTilesAreThis(Tile.WALL, shouldBeWall, tiles))
            {
                //Point theOneDoorSpot = shouldBeWall.get(0);
                //shouldBeWall.clear();
                //shouldBeWall.add(theOneDoorSpot);
                //tiles[theOneDoorSpot.x][theOneDoorSpot.y][theOneDoorSpot.z] = new TileV(Tile.CLOSED_DOOR);
                Collections.shuffle(shouldBeWall);
                for(Point wp : shouldBeWall)
                {
                    //System.out.println("Should be carving hallway");
                    // || wp.x == 0 + offsetX || wp.x == width - 1 +offsetY || wp.y == 0 + offsetY

                    if( wp.x == width - 1 + offsetX || wp.y == height - 1 + offsetY )
                    {
                        Direction d = null;
                        if (found) {
                            continue;}
                        if(wp.x == 0 +offsetX )
                        {
                            d = Direction.EAST;
                        }
                        if( wp.x == width - 1 + offsetX)
                        {
                            d = Direction.WEST;
                        }
                        if(wp.y == 0 + offsetY )
                        {
                            d = Direction.NORTH;
                        }
                        if(wp.y == height-1 + offsetY)
                        {
                            d = Direction.SOUTH;
                        }

                        tiles[wp.x][wp.y][wp.z] = new TileV(Tile.CLOSED_DOOR);
                        puzzle.addLockedDoor(new Door(wp, wp, Door.Clearance.UNIQUE), d);
                        System.out.println("This is the one true door:" + wp.toString());

                        found = true;
                    }
                    else
                    {
                        tiles[wp.x][wp.y][wp.z] = new TileV(Tile.BLASTED_TERRAIN);
                    }
                }
            }
        }
    }
    */
    public static List<Point> getAllPointsThatAreThisTile(TileV[][][] tiles, int width, int height, int depth, Tile tile, int offsetX, int offsetY)
    {
        List<Point> points = new ArrayList();

        for(int x = 0 + offsetX; x < width  + offsetX; x++)
        {
            for(int y = 0 + offsetY; y < height + offsetY; y++)
            {
                for(int z = 0; z < depth; z++)
                {
                    if(tiles[x+offsetX][y+offsetY][z].getTile() == tile)
                    {
                        points.add(new Point(x+offsetX, y+offsetY, z));
                    }
                }
            }
        }
        return points;
    }
}