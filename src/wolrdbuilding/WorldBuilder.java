package wolrdbuilding;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entities.Entity;

public class WorldBuilder implements Serializable
{
	private Entity player;
	private int width;
	private int height;
	private int depth;
	private int nextRegion;
	
	private int[][][] regions;
    private Tile[][][] tiles;
    
    private ArrayList<Point> spawnPoints;
    private static Random random;

    public WorldBuilder(int width, int height, int depth, Entity player) 
    {
    	random = new Random();
    	
    	this.player = player;
        
    	this.width = width;
        this.height = height;
        this.depth = depth;
        this.tiles = new Tile[width][height][depth];
        this.regions = new int[width][height][depth];
        this.nextRegion = 1;
        this.spawnPoints = new ArrayList<>();
    }
    
    public World build() 
    {
        return new World(tiles, spawnPoints, player);
    }
    // Makes random rooms twice, once in makeCaves, and called again 
    // in playScreen.createWorld
    public WorldBuilder makeCaves() 
    {
        return randomizeTiles()
        		.smooth(12)
        		.createRegions()
        		.connectRegions()
        		.makeRandoRooms()
        		.addExitStairs()
        		.addExitStairs();
    }
   
    // WORLD BUILDING TOOLS // SMOOTH
	private WorldBuilder randomizeTiles() 
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++) 
				{
					tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
				}
			}
		}
		return this;
	}
	private WorldBuilder smooth(int times) 
	{
		Tile[][][] tiles2 = new Tile[width][height][depth];
		
		for (int time = 0; time < times; time++) 
		{
			for (int x = 0; x < width; x++) 
			{
				for (int y = 0; y < height; y++) 
				{
					for (int z = 0; z < depth; z++) 
					{
						int floors = 0;
						int rocks = 0;
	
						for (int ox = -1; ox < 2; ox++) 
						{
							for (int oy = -1; oy < 2; oy++) 
							{
								if (x + ox < 0 || x + ox >= width || y + oy < 0
										|| y + oy >= height)
									continue;
	
								if (tiles[x + ox][y + oy][z] == Tile.FLOOR)
									floors++;
								else
									rocks++;
							}
						}
						tiles2[x][y][z] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
					}
				}
			}
			tiles = tiles2;
		}
		return this;
	}
   
	public WorldBuilder makeRandoRooms()
	{
		Point largestRegion = findLargestRegion();
		int X = largestRegion.x;
		int Y = largestRegion.y;
		int w;
		int h;
		int x;
		int y;
		int z;
		Point p;
		
		for(int i = 0; i < 80; i++)
		{
			do
			{
				w = random.nextInt(25)+5;
				h = random.nextInt(25)+5;
				x = random.nextInt(width-w); 
				y = random.nextInt(height-h); 
				z = random.nextInt(5); 
				p = new Point(x, y, z);
			} while (!isValidPoint(p, w, h));
			buildRoom(p, w, h);
		}	
		return buildRoom(new Point(20, 20, 0), 20, 20);
	}
	public WorldBuilder buildRoom(Point p, int w, int h)
	{
		if(isValidPoint(p, w, h))
		{
		for (int x = p.x; x < p.x+w ; x++)
	    {
			for (int y = p.y; y < p.y+h ; y++)
	        {	 
				if(x == p.x || x == p.x+w-1)
					tiles[x][y][p.z] = Tile.lrWall;
				else if (y == p.y || y == p.y+h-1)
					tiles[x][y][p.z] = Tile.tbWall;
				else
				{
					tiles[x][y][p.z] = Tile.INSIDE_FLOOR;
					spawnPoints.add(new Point(x, y, p.z));
				}
	        }
	    }
		}

		tiles[p.x+random.nextInt(w-1)+ 1][p.y][p.z] = Tile.DOOR;
		tiles[p.x][p.y+random.nextInt(h-1)+ 1][p.z] = Tile.DOOR;
		tiles[p.x + w - 1][p.y+random.nextInt(h-1)+ 1][p.z] = Tile.DOOR;
		tiles[p.x+random.nextInt(w-1)+ 1 ][p.y+h-1][p.z] = Tile.DOOR;
	
		tiles[p.x][p.y][p.z] = Tile.tlCorner;
		tiles[p.x+w][p.y][p.z] = Tile.trCorner;
		tiles[p.x][p.y+h-1][p.z] = Tile.blCorner;
		tiles[p.x+w-1][p.y+h-1][p.z] = Tile.brCorner;
		
		return this;	 
	}
	private boolean isValidPoint(Point p, int w, int h)
	{
		if(p.x+w >= width || p.y+h >= height || p.x == width || p.y == height
				&& !spawnPoints.contains(p))
			return false;
		else
			return true;
	}
	
	
	private Point findLargestRegion()
	{
		int[] regionCounter = new int[500];
		int largest  = 0;
		int lx =0;
		int ly =0;
		for(int i  = 0; i < 50; i++)
			regionCounter[i] = 0;
		
        for (int x = 0; x < width; x++)
        {
           for (int y = 0; y < height; y++)
            {
            	//regionCounter[regions[x][y][0]] += 1;
            	if(regions[x][y][0] > largest)
				{
            		largest = regions[x][y][0];
            		lx= x;
            		ly= y;
            		
				}
            }
        }
        System.out.println(largest + " " + lx + " " + ly + " " +  regionCounter[regions[lx][ly][0]]);
		
        return new Point(lx, ly, 0);
	}
	
	private WorldBuilder createRegions()
	{
        regions = new int[width][height][depth];
    
        for (int z = 0; z < depth; z++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                   if (tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0)
                   {
                       int size = fillRegion(nextRegion++, x, y, z);
              
                       if (size < 25)
                           removeRegion(nextRegion - 1, z);
                   }
                }
            }
        }
        return this;
    }
	private void removeRegion(int region, int z)
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (regions[x][y][z] == region)
				{
					regions[x][y][z] = 0;
					tiles[x][y][z] = Tile.WALL;
				}
			}
		}
	}
	private int fillRegion(int region, int x, int y, int z) 
	{
		int size = 1;
		ArrayList<Point> open = new ArrayList<Point>();
		open.add(new Point(x,y,z));
		regions[x][y][z] = region;
		
		while (!open.isEmpty())
		{
			Point p = open.remove(0);

			for (Point neighbor : p.neighbors8())
			{
				if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height)
					continue;
				
				if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
						|| tiles[neighbor.x][neighbor.y][neighbor.z] == Tile.WALL)
					continue;

				size++;
				regions[neighbor.x][neighbor.y][neighbor.z] = region;
				open.add(neighbor);
			}
		}
		return size;
	}
	public WorldBuilder connectRegions()
	{
        for (int z = 0; z < depth-1; z++)
        {
            connectRegionsDown(z);
        }
        return this;
    
	}
	private void connectRegionsDown(int z)
	{
	    List<String> connected = new ArrayList<String>();
	  
	    for (int x = 0; x < width; x++)
	    {
	        for (int y = 0; y < height; y++)
	        {
	            String region = regions[x][y][z] + "," + regions[x][y][z+1];
	            if (tiles[x][y][z] == Tile.FLOOR
	              && tiles[x][y][z+1] == Tile.FLOOR
	              && !connected.contains(region))
	            {
	                connected.add(region);
	                connectRegionsDown(z, regions[x][y][z], regions[x][y][z+1]);
	            }
	        }
	    }
	}
	private void connectRegionsDown(int z, int r1, int r2)
	{
        List<Point> candidates = findRegionOverlaps(z, r1, r2);
    
        int stairs = 0;
        do
        {
            Point p = candidates.remove(0);
            tiles[p.x][p.y][z] = Tile.STAIRS_DOWN;
            tiles[p.x][p.y][z+1] = Tile.STAIRS_UP;
            stairs++;
        }
        while (candidates.size() / stairs > 250);
    }
	public List<Point> findRegionOverlaps(int z, int r1, int r2) 
	{
        ArrayList<Point> candidates = new ArrayList<Point>();
    
        for (int x = 0; x < width; x++)
        {
         for (int y = 0; y < height; y++)
         {
             if (tiles[x][y][z] == Tile.FLOOR
                  && tiles[x][y][z+1] == Tile.FLOOR
                  && regions[x][y][z] == r1
                  && regions[x][y][z+1] == r2)
             {
              candidates.add(new Point(x,y,z));
             }
         }
        }
    
        Collections.shuffle(candidates);
        return candidates;
    }
	private WorldBuilder addExitStairs() 
	{
        int x = -1;
        int y = -1;
    
        do 
        {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (tiles[x][y][0] != Tile.FLOOR);
    
        tiles[x][y][0] = Tile.STAIRS_EXIT;
        return this;
    }
	 // Average getters//setters
    public int getWidth()		{	return width;	}
	public void setWidth(int width){	this.width = width;		}
	public int getHeight() 	{	return height;	}
	public void setHeight(int height) {	this.height = height;}
}
