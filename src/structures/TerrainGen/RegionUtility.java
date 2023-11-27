package structures.TerrainGen;

/*
    A region is a walkable area of the map that is not connected to another walkable area of the map.
    To connect these regions we will identify each region on the region map with a unique Integer.

 */
public class RegionUtility
{
    /*

    public static int[][][] getRegionMap(TileV[][][] tiles,int width, int height, int depth)
    {
        int nextRegion = 1;

        int[][][] regions = new int[width][height][depth];

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
                    if (!tiles[x][y][z].getTile().isWall() && regions[x][y][z] == 0)
                    {
                        int size = fillRegion(nextRegion++, x, y, z);
                        if (size < 60)
                            removeRegion(nextRegion - 1, z);

                        if(regions[x][y][z] > regionCount)
                            regionCount = regions[x][y][z];
                        if(largestRegion < size)
                            largestRegion = size;
                    }
                }
            }
        }

        return null;
    }
    private int fillRegion(int region, int x, int y, int z, int[][][] regions)
    {
        int size = 1;
        ArrayList<Point> open = new ArrayList<Point>();
        List<Point> regionPoints = new ArrayList<>();

        open.add(new Point(x,y,z));
        regions[x][y][z] = region;
        //System.out.println("filling regions   ************************");
        //System.out.println(region + " "  + "  ************************");
        while (!open.isEmpty())
        {
            Point p = open.remove(0);
            for (Point neighbor : p.neighbors8())
            {
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height)
                    continue;

                if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
                        || tiles[neighbor.x][neighbor.y][neighbor.z].getTile().isWall())
                    continue;

                size++;
                regions[neighbor.x][neighbor.y][neighbor.z] = region;
                regionPoints.add(neighbor);
                open.add(neighbor);
                //regionMap.put(region, regionPoints);
            }
        }
        //regionMap.put(region, );
        regionList.add(regionPoints);

        return size;
    }
    */
}
