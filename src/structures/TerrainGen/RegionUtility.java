package structures.TerrainGen;

import puzzlelike.PuzzleManager;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;
import wolrdbuilding.TileV;

import java.util.*;

/*
    A region is a walkable area of the map that is not connected to another walkable area of the map.
    To connect these regions we will identify each region on the region map with a unique Integer.

 */
public class RegionUtility
{
    public static  Map<Integer, List<Point>> getRegionMap(TileV[][][] tiles, int width, int height, int depth, int offx, int offy)
    {
        int nextRegion = 1;

        List<Point> regionPoints = new ArrayList<>();
        Map<Integer, List<Point>> regionMap = new HashMap<>();



        int[][][] regions = new int[width+offx][height+offy][depth];

        // Set whole region map to zero to represent a map full of walls
        for(int x1 = 0; x1 < width; x1++)
        {
            for(int y1 = 0; y1 < height; y1++)
            {
                for(int z1 = 0; z1 < depth; z1++)
                {
                    regions[x1][y1][z1] = 0;
                }
            }
        }

        int regionCount=  0;
        int largestRegion = 0;
        for (int z = 0; z < depth; z++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    if (!tiles[x+offx][y+offy][z].getTile().isWall() && regions[x][y][z] == 0)
                    {
                         regionPoints = fillRegion(nextRegion++, x, y, z, regions, width, height, tiles, offx, offy);
                        //if (size < 60)
                            //removeRegion(nextRegion - 1, z);
                        regionMap.put(nextRegion-1, regionPoints);

                        if(regions[x][y][z] > regionCount)
                            regionCount = regions[x][y][z];
                    }
                }
            }
        }
        System.out.println("Regions : " + regionCount + " \nKey set" + regionMap.keySet().toString());

        return regionMap;
    }

    private static void removeRegion(List<Point> points, TileV[][][] tiles)
    {
        for(Point p  : points)
        {
            tiles[p.x][p.y][p.z].setTile(Tile.WALL);
        }
    }

    private static List<Point> fillRegion(int region, int x, int y, int z, int[][][] regions, int width, int height, TileV[][][] tiles, int offx, int offy)
    {
        int size = 1;
        ArrayList<Point> open = new ArrayList<Point>();
        List<Point> regionPoints = new ArrayList<>();

        open.add(new Point(x ,y ,z));
        //regionPoints.add(new Point(x,y,z));
        regions[x][y][z] = region;
        System.out.println("filling regions   ************************");
        System.out.println(region + " "  + "  ************************");
        while (!open.isEmpty())
        {
            Point p = open.remove(0);
            //regionPoints.add(p);
            //System.out.println(" Point should be clean " +  p.toString());
            for (Point neighbor : p.neighbors8())
            {
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height)
                    continue;

                if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
                        || tiles[neighbor.x + offx][neighbor.y + offy][neighbor.z].getTile().isWall())
                    continue;
                //System.out.println("\tChecking neighbors" + "");
                size++;
                regions[neighbor.x][neighbor.y][neighbor.z] = region;
                regionPoints.add(neighbor);
                open.add(neighbor);
                //regionMap.put(region, regionPoints);
            }
            if(tiles[p.x + offx][p.y+ offy][p.z].getTile().isFloor())
            {
                //regionPoints.add(p);
            }
        }
        //regionMap.put(region, );
        //regionList.add(regionPoints);

        // Calculations are being done on local width x height,
        // so then they need to be transposed to specified area
        List<Point> transposedRegions = new ArrayList<>();

        for(Point p  : regionPoints)
        {
            p = new Point(p.x + offx, p.y + offy, p.z);
            transposedRegions.add(p);
        }

        return transposedRegions;
    }

    public static Point findClosestPointFromNextRegion(TileV[][][] tiles, List<Point> region1, List<Point> region2)
    {
        // r1 = region r2 = candidates

        int counter  = 0;
        int max  = 100;

        Random random = new Random();
        Point r1 = region1.get(random.nextInt(region1.size()));
        Point r2 = region2.get(random.nextInt(region2.size()));

        Point closest = r2;
        int closestDistanceX = Math.abs(r1.x - r2.x);
        int closestDistanceY = Math.abs(r1.y - r2.y);

        System.out.println("For current point the distance between is   X : " + closestDistanceX + " Y : " +  closestDistanceY);

        do
        {
            // if it's withing 10 return
            if(closestDistanceX < 10 && closestDistanceY < 10)
            {
                counter = 100;
            }
            else // look for new closest
            {
                r1 = region1.get(random.nextInt(region1.size()));
                r2 = region2.get(random.nextInt(region2.size()));

                if(Math.abs(r1.x - r2.x) < closestDistanceX && Math.abs(r1.y - r2.y) < closestDistanceY)
                {
                    closestDistanceX = Math.abs(r1.x - r2.x);
                    closestDistanceY = Math.abs(r1.y - r2.y);
                    closest = r2;
                }
            }
            counter++;
        }while(counter < max);

        return closest;
    }
    public static TileV[][][] mergeRegions(TileV[][][] tiles, int width, int height, int depth, PuzzleManager puzzMan)
    {
        int offx = puzzMan.getNextPuzzleTerrainOffset().x;
        int offy = puzzMan.getNextPuzzleTerrainOffset().y;

        Map<Integer, List<Point>> regionMap = getRegionMap(tiles, width, height, depth, puzzMan.getNextPuzzleTerrainOffset().x, puzzMan.getNextPuzzleTerrainOffset().y);
        Random random = new Random();
        //System.out.println("Merge regions" + regionMap.get(1).toString());

        if(regionMap.keySet().size() == 1)
        {
            //System.out.println("Their is only one region");
            return tiles;
        }
        else
        {
            for(Integer region : regionMap.keySet())
            {
                // Checks if out of bounds
                if( region + 1 > regionMap.keySet().size())
                {
                    continue;
                }
                int counter  = 0;
                int max  = 100;
                Point r1 = regionMap.get(region).get(random.nextInt(regionMap.get(region).size()));
                Point r2 = regionMap.get(region + 1).get(random.nextInt(regionMap.get(region + 1).size()));
                do
                {
                    if(Math.abs(r1.x - r2.x) < 10 && Math.abs(r1.y - r2.y) < 10)
                    {
                        counter = 100;
                    }
                    else
                    {
                        r1 = regionMap.get(region).get(random.nextInt(regionMap.get(region).size()));
                        r2 = regionMap.get(region + 1).get(random.nextInt(regionMap.get(region +1).size()));
                    }
                    counter++;
                }while(counter < max);

                tiles[r1.x][r1.y][r1.z] = new TileV(Tile.GRASS_0);
                tiles[r2.x][r2.y][r2.z] = new TileV(Tile.FIRE_ONE);
                tiles = ConnectRegions(tiles, width, height, depth, puzzMan, r1, r2);
            }
            // Choose a point from region A
            // Choose a point from region B
            // Calculate x y distance between two points
            // Set as closest
                // See if distance is acceptable and end search

        }
        //System.out.println("Region count : " + regionMap.keySet().size());
        return tiles;
    }

    public static TileV[][][] ConnectRegions(TileV[][][] tiles, int width, int height, int depth, PuzzleManager puzzMan, Point r1, Point r2)
    {
        int count = 0;
        int max = 100;

        int xDistanceBetween = Math.abs(r1.x - r2.x);
        int yDistanceBetween = Math.abs(r1.y - r2.y);

        // Used in y transposition
        int newX = 0;

        System.out.println("Should be absolute " + xDistanceBetween + " and Y " + yDistanceBetween);

        if(r1.x < r2.x)
        {
            tiles[r1.x+1][r1.y][r1.z].setTile(Tile.INSIDE_FLOOR);
            for(int x = 1; x <= xDistanceBetween; x++)
            {
                tiles[r1.x+x][r1.y][r1.z].setTile(Tile.INSIDE_FLOOR);
            }
            newX = r1.x + xDistanceBetween;
        }
        else if(r1.x > r2.x)
        {
            tiles[r1.x-1][r1.y][r1.z].setTile(Tile.INSIDE_FLOOR);
            xDistanceBetween = 0 - xDistanceBetween;
            for(int x = -1; x >= xDistanceBetween; x--)
            {
                tiles[r1.x+x][r1.y][r1.z].setTile(Tile.INSIDE_FLOOR);
            }
            newX = r1.x + xDistanceBetween;
        }

        if(r1.y < r2.y)
        {
            tiles[r1.x][r1.y+1][r1.z].setTile(Tile.INSIDE_FLOOR);
            for(int y = 1; y <= yDistanceBetween; y++)
            {
                tiles[newX][r1.y+y][r1.z].setTile(Tile.INSIDE_FLOOR);
            }
        }
        else if(r1.y > r2.y)
        {
            tiles[newX][r1.y-1][r1.z].setTile(Tile.INSIDE_FLOOR);
            yDistanceBetween = 0 - yDistanceBetween;
            for(int y = -1; y >= yDistanceBetween; y--)
            {
                tiles[newX][r1.y+y][r1.z].setTile(Tile.INSIDE_FLOOR);
            }
        }

        System.out.println("X distance between regions :" + xDistanceBetween);
        System.out.println("Y distance between regions :" + yDistanceBetween);

        return tiles;
    }
}
