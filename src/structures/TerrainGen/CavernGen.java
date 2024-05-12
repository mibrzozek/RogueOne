package structures.TerrainGen;

import puzzlelike.PuzzleManager;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;
import wolrdbuilding.TileV;

import java.util.List;

public class CavernGen
{
    public static TileV[][][] makeCavern(int times, int width, int height, int depth, TileV[][][] tiles, int offsetX, int offsetY, PuzzleManager puzzMan)
    {
        //tiles = randomizeFloorWithPercent(width, height, depth, tiles, Tile.WALL, Tile.INSIDE_FLOOR, .5, offsetX, offsetY);
        tiles = MapUtility.determineContinuationPoint(tiles, width, height, depth, offsetX, offsetY, puzzMan);

        tiles = MapUtility.fillPointsWithTile(tiles, MapUtility.getBorderPoints(width, height, depth, tiles, 5, offsetX, offsetY), Tile.WALL, 0, 0);
        tiles = smooth(times, width, height, depth, tiles, offsetX, offsetY); // smoothing last time to prevent caverns on border
        tiles = MapUtility.fillPointsWithTile(tiles, MapUtility.getBorderPoints(width, height, depth, tiles, 5, offsetX, offsetY), Tile.WALL, 0, 0); // ensures walls on edges

        // ID Floor points
        List<Point> allFloors = MapUtility.getAllPointsThatAreThisTile(tiles, width, height, depth, Tile.INSIDE_FLOOR, offsetX, offsetY);

        //Regen map
        tiles = randomizeFloorWithPercent(width, height, depth, tiles, Tile.WALL, Tile.INSIDE_FLOOR, .5, offsetX, offsetY);
        tiles = MapUtility.fillPointsWithTile(tiles, MapUtility.getBorderPoints(width, height, depth, tiles, 5, offsetX, offsetY), Tile.WALL, 0, 0);
        tiles = smooth(times, width, height, depth, tiles, offsetX, offsetY); // smoothing last time to prevent caverns on border
        tiles = MapUtility.fillPointsWithTile(tiles, MapUtility.getBorderPoints(width, height, depth, tiles, 5, offsetX, offsetY), Tile.WALL, 0, 0); // ensures walls on edges

        //Stamp Previous Cave (no smoothing necessary)
        tiles = MapUtility.fillPointsWithTile(tiles, allFloors, Tile.INSIDE_FLOOR, 0, 0);

        return tiles;
    }
    private static TileV[][][] randomizeFloorWithPercent(int width, int height, int depth, TileV[][][] tiles, Tile wall, Tile insideFloor, Double percent, int offsetX, int offsetY)
    {
        for(int x = 0 + offsetX ;x < width; x++)
        {
            for(int y = 0 + offsetY; y < height; y++)
            {
                for(int z = 0; z < depth; z++)
                {
                    if(Math.random() > percent)
                    {
                        tiles[x][y][z].setTile(wall);
                        System.out.println((x + offsetX) +  " painting at this offset locations  " + (y+ offsetY) + " " + z);
                    }
                    else
                    {
                        tiles[x][y][z].setTile(insideFloor);
                    }
                }
            }
        }
        return tiles;
    }
    public static TileV[][][] smooth(int times, int width, int height, int depth,  TileV[][][] tiles, int offsetX, int offsetY)
    {
        TileV[][][] tiles2 = tiles;
        for (int time = 0; time < times; time++)
        {
            for (int x = 0 + offsetX; x < width + offsetX; x++)
            {
                for (int y = 0 + offsetY; y < height + offsetY; y++)
                {
                    for (int z = 0; z < depth; z++)
                    {
                        int floors = 0;
                        int rocks = 0;

                        for (int ox = -1; ox < 2; ox++)
                        {
                            for (int oy = -1; oy < 2; oy++)
                            {
                                if (x + ox < 0 + offsetX || x + ox >= width + offsetX || y + oy < 0 +offsetY
                                        || y + oy >= height + offsetY)
                                    continue;
                                if (tiles[x + ox][y + oy][z].isFloor())
                                    floors++;
                                else
                                    rocks++;
                            }
                        }
                        tiles[x][y][z] = floors >= rocks ? new TileV(Tile.INSIDE_FLOOR) : new TileV(Tile.WALL);
                        //
                        // System.out.println("Just stamped x : " + x + "y:" +y + "z:" + z );
                    }
                }
            }
        }
        return tiles;
    }
    /*
    private static int[][][] createRegions(int[][][] regions, int width, int height, int depth, TileV[][][] tiles)
    {
        regions = new int[width][height][depth];

        for (int z = 0; z < depth; z++){
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    if (tiles[x][y][z] != new TileV(Tile.WALL) && regions[x][y][z] == 0)
                    {
                        int size = fillRegion(nextRegion++, x, y, z);

                        if (size < 25)
                            removeRegion(nextRegion
                           - 1, z);
                    }
                }
            }
        }
        return regions;
    }
    private static void removeRegion(int region, int z,int width,  int height, int[][][] regions, TileV[][][] tiles)
    {
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (regions[x][y][z] == region){
                    regions[x][y][z] = 0;
                    tiles[x][y][z] = new TileV(Tile.WALL);
                }
            }
        }
    }
    private int fillRegion(int region, int x, int y, int z, int[][][] regions, int width, int height, int depth, TileV[][][] tiles)
    {
        int size = 1;
        ArrayList<Point> open = new ArrayList<Point>();
        open.add(new Point(x,y,z));
        regions[x][y][z] = region;

        while (!open.isEmpty()){
            Point p = open.remove(0);

            for (Point neighbor : p.neighbors8()){
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height)
                    continue;

                if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
                        || tiles[neighbor.x][neighbor.y][neighbor.z] == new TileV(Tile.WALL))
                    continue;

                size++;
                regions[neighbor.x][neighbor.y][neighbor.z] = region;
                open.add(neighbor);
            }
        }
        return size;
    }

    public static TileV[][][] connectRegions(int width, int height, int depth)
    {
        for (int z = 0; z < depth-1; z++){
            connectRegionsDown(z, width, );
        }
        return this;
    }

    private void connectRegionsDown(int z, int width, int height, int depth, int[][][] regions, TileV[][][] tiles)
    {
        List<String> connected = new ArrayList<String>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                String region = regions[x][y][z] + "," + regions[x][y][z+1];
                if (tiles[x][y][z] == new TileV(Tile.FLOOR)
                        && tiles[x][y][z+1] == new TileV(Tile.FLOOR)
                        && !connected.contains(region)){
                    connected.add(region);
                    connectRegionsDown(z, regions[x][y][z], regions[x][y][z+1]);
                }
            }
        }
    }

    private void connectRegionsDown(int z, int r1, int r2, TileV[][][] tiles)
    {
        List<Point> candidates = findRegionOverlaps(z, r1, r2);

        int stairs = 0;
        do{
            Point p = candidates.remove(0);
            tiles[p.x][p.y][z] = new TileV(Tile.STAIRS_DOWN);
            tiles[p.x][p.y][z+1] = new TileTile.STAIRS_UP;
            stairs++;
        }
        while (candidates.size() / stairs > 250);
    }

    public List<Point> findRegionOverlaps(int z, int r1, int r2) {
        ArrayList<Point> candidates = new ArrayList<Point>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (tiles[x][y][z] == Tile.FLOOR
                        && tiles[x][y][z+1] == Tile.FLOOR
                        && regions[x][y][z] == r1
                        && regions[x][y][z+1] == r2){
                    candidates.add(new Point(x,y,z));
                }
            }
        }

        Collections.shuffle(candidates);
        return candidates;
    }
     */
}
