package structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wolrdbuilding.Direction;
import wolrdbuilding.Point;
import wolrdbuilding.RoomPoint;
import wolrdbuilding.Tile;
import wolrdbuilding.TileSet;

public class Dungeon
{
	private static int width, height, depth, nextRegion;
	private static Tile[][][] tiles;
	private static int[][][] regions;
	
	private static ArrayList<Point> spawnPoints;
	private static ArrayList<Point> startingPoints;
	private ArrayList<Point> occupiedPoints;
	private List<List> regionList;
	
	private Random r =  new Random();
	
	static final int MAX_TUNNEL_LENGTH = 3;
	static final int MAX_TUNNEL_WIDTH = 3;
	
	static final int MAX_ROOM_LENGTH = 30;
	static final int MIN_ROOM_LENGTH = 5;
	
	public Dungeon(int width, int height, int depth)
	{
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		this.tiles = new Tile[width][height][depth];
		
		this.spawnPoints = new ArrayList<>();
		this.occupiedPoints = new ArrayList<>();
		this.regionList = new ArrayList<>();
		this.nextRegion = 1;
	}
	
	public Tile[][][] getNewDungeon()
	{
		randomizeFloor();
		randomApproachToDungeons();
		return tiles;
	}
	public void randomApproachToDungeons()
	{
		Point p =  null;
		
		for(int i = 0; i < 120; i++)
		{	
			for(int j = 0; j < depth; j++)
			{
				p = getFirstRoomPoint(j);
				Direction d = chooseDirectionClosestToBorder(p);
				RoomPoint rp = new RoomPoint(p, p.w, p.h, d);
				buildRoom(rp, TileSet.ALL_GROUND_ROOM);
			}
		}
		
		createRegions();
		fillDungeonWithWall(TileSet.SIMPLE);
		//addExitStairs();
		makeStairsDown();
		makeLaserTraps();
		makeStartingRoom();
		
	}
	public void makeStartingRoom()
	{
		int w = 25, h = 25;
		Point p;
		do {
			p = getSpawnPointFromLevel(0);
		}while(!isValidPoint(p, w, h));

		RoomPoint rp = new RoomPoint(p, w, h);
		buildRoom(rp, TileSet.DOUBLE);

		buildPlasmaBlock(rp, 7);

		startingPoints = getOpenPointFromRegion(rp.point(), 15, 15);
	}
	public void buildPlasmaBlock(RoomPoint rp, int oddWH)
	{
		// Plasma blocks should always be in the center of the room
		// This checks that the room is square and has a center
		int nw, nh, center;
		if(rp.w != rp.h && rp.w % 2 == 0)
			return;
		else
		{
			center = ((rp.w - 1)/2) - ((oddWH-1)/2);
		}

		rp = new RoomPoint(new Point(rp.x + center, rp.y + center, rp.z), oddWH, oddWH);
		buildRoom(rp, TileSet.SIMPLE_S);
		rp = new RoomPoint(new Point(rp.x + 1, rp.y + 1, rp.z), oddWH-2, oddWH-2);
		buildRoom(rp, TileSet.CANISTERS);
		tiles[rp.x+ 2][rp.y -1][rp.z] = Tile.INTERFACE;
	}

	public ArrayList<Point> getOpenPointFromRegion(Point p, int w, int h)
	{
		ArrayList<Point> available = new ArrayList<>();
		for(int i = p.x; i < p.x + w; i++)
		{
			for(int j = p.y; j < p.y+h; j++)
			{
				if(tiles[i][j][p.z].isGround())
					available.add(new Point(i, j, p.z));
			}
		}


		return available;
	}
	public Point getSpawnPointFromLevel(int level)
	{
		Point p = null;
		do
		{
			p = spawnPoints.get(r.nextInt(spawnPoints.size()));
		} while(p.z != level);

		return p;
	}
	private void makeLaserTraps()
	{
		//find random inside point nextr to a wall
		// see if corridor
		// connect
		boolean isCorridor = false;
		int nx, ny, nz;
		for(int i = 0; i < 7; i++)
		{
			Point p = getPointFromLevel(0);
			System.out.println(tiles[p.x-1][p.y][p.z].toString() + " " +
					tiles[p.x][p.y][p.z].toString() + " " +
					tiles[p.x+1][p.y][p.z].toString() + " ");
			
			Direction direction = Direction.NORTH;
			
			if(tiles[p.x+1][p.y][p.z].isRoom())
				direction = Direction.WEST;
			else if(tiles[p.x-1][p.y][p.z].isRoom())
				direction = direction.EAST;
			else if(tiles[p.x][p.y-1][p.z].isRoom())
				direction = direction.SOUTH;
			
			System.out.println(direction);
			
			makeLine(p, direction);
		}
		System.out.println("checking");
	}
	public Point getPointFromLevel(int z)
	{
		Point p = null;
		
		do
		{
			p = spawnPoints.get(r.nextInt(spawnPoints.size()));
			
			System.out.println("checking " + " " + p.toString() + "\n"
					+ tiles[p.x + 1][p.y][p.z].isRoom() + "\n"
					+ isCorridor(p));						
		}while(p.z != 0 || !bound(p) || !isCorridor(p));
		return p;
	}
	public boolean bound(Point p)
	{
		int bound = 30;
		
		if(p.x < bound || p.x > width - bound || p.y < bound || p.y > width - bound)
			return false;
		else
			return true;
	}
	public boolean isNextToWall(Point p)
	{
		if(tiles[p.x + 1][p.y][p.z].isRoom() 
			|| tiles[p.x - 1][p.y][p.z].isRoom()
			|| tiles[p.x][p.y + 1][p.z].isRoom()
			|| tiles[p.x][p.y - 1][p.z].isRoom())
			return true;
		else
			return false;
	}
	private void makeLine(Point p, Direction d)
	{
		int movement = 1, corridor = 7;
		
		if(d.equals(Direction.WEST) || d.equals(Direction.NORTH))
			movement = -1;
		
		for(int i = 0; i < 10; i++)
		{
			if(d.equals(Direction.WEST) || d.equals(Direction.EAST))
			{	
				if(tiles[p.x][p.y][p.z].isFloor())
					tiles[p.x][p.y][p.z] = Tile.LEFT_RIGHT_LASER;
				p.x += movement;
			}
			else 
			{
				if(tiles[p.x][p.y][p.z].isFloor())
					tiles[p.x][p.y][p.z] = Tile.UP_DOWN_LASER;
				p.y += movement;
			}
		}
	}
	private boolean isCorridor(Point p)
	{
		int movement = 1;
		boolean corridor = false;
		
		if(isNextToWall(p))
		{	
			if(tiles[p.x + 1][p.y][p.z].isRoom())
				movement = -1;
			System.out.println(tiles[p.x + 1][p.y][p.z] + " ************ movement : " + movement);
			int i, nx = p.x;
			for(i = 0; i < 7; i++)
			{
				if(i == 0 && tiles[nx][p.y][p.z].isFloor())	
					nx += movement;
				else if(i > 0 && tiles[nx][p.y][p.z].isRoom())
				{	
					corridor = true;
					break;
				}
				if(nx + movement > 0 && nx + movement < 198)
					nx += movement;
			
				System.out.println("x : " + p.x + "movement : " + movement
						+"\n" + tiles[p.x][p.y][p.z]);
			}
		}
		else
		{
			System.out.println("Not next to wall");
		}
		/*
		if(tiles[p.x + 1][p.y][p.z].isRoom())
			movement = -1;
		
		System.out.println("x : " + p.x + "movement : " + movement);
		
		p.x += movement;
		for(int i = 1; i < 10; i++);
		{
			
			if(tiles[p.x][p.y][p.z].isRoom())
				corridor = true;
			
			p.x += movement;
		}
		
		*/
		System.out.println("Is it a corridor? " + corridor);
		return corridor;
	}
	
	private void makeStairsDown()
	{
		ArrayList<Point> allRegionPoints = new ArrayList<>();
		for(List list : regionList)
		{
			for(Object o : list)
			{
				allRegionPoints.add((Point) o);
			}
		}
		for(List list : regionList)
		{
			boolean truth = false;
			int count  =  0;
			do
			{
				
				Point p = (Point)list.get(r.nextInt(list.size()));
				
				if(tiles[p.x][p.y][p.z].isGround() 
						&& p.z +1 < depth && tiles[p.x][p.y][p.z + 1].isGround())
				{
					tiles[p.x][p.y][p.z] = Tile.STAIRS_DOWN;
					tiles[p.x][p.y][p.z + 1] = Tile.STAIRS_UP;
				
					truth = true;
				}
				count++;
			} while (truth == false && count < 100);
		}
	}
	private void createRegions()
	{
        regions = new int[width][height][depth];
        int regionCount=  0;
        int largestRegion = 0;
        for (int z = 0; z < depth; z++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                   if (!tiles[x][y][z].isWall() && regions[x][y][z] == 0)
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
        System.out.println("regions : " +  nextRegion + " Largest Region :" + largestRegion);
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
		List<Point> regionPoints = new ArrayList<>();
		
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
						|| tiles[neighbor.x][neighbor.y][neighbor.z].isWall())
					continue;

				size++;
				regions[neighbor.x][neighbor.y][neighbor.z] = region;
				regionPoints.add(neighbor);
				open.add(neighbor);
			}
		}
		regionList.add(regionPoints);
		return size;
	}
	private void addExitStairs() 
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
    }
	public void fillDungeonWithWall(TileSet set)
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++) 
				{
					if(tiles[x][y][z] == Tile.INSIDE_FLOOR)
					{	
						if(x-1 > 0 && x + 1 < width && y-1 > 0 && y + 1 < height)
						{	
							if(tiles[x+1][y][z] == Tile.INSIDE_FLOOR
									&& tiles[x-1][y][z].isWall())
									tiles[x][y][z] = set.lrw;
							else if(tiles[x][y+1][z] == Tile.INSIDE_FLOOR
									&& tiles[x][y-1][z].isWall())
									tiles[x][y][z] = set.tbw;
							else if(tiles[x-1][y][z] == Tile.INSIDE_FLOOR
									&& tiles[x+1][y][z].isWall())
									tiles[x][y][z] = set.lrw;
							else if(tiles[x][y-1][z] == Tile.INSIDE_FLOOR
									&& tiles[x][y+1][z].isWall())
									tiles[x][y][z] = set.tbw;
							// Fills corners with floor tiles INSIDE the corner
							if(tiles[x][y+1][z].isWall()
									&& tiles[x+1][y][z].isWall())
									tiles[x][y][z] = set.brc;
							else if(tiles[x][y-1][z].isWall()
									&& tiles[x+1][y][z].isWall())
									tiles[x][y][z] = set.trc;
							else if(tiles[x][y+1][z].isWall()
									&& tiles[x-1][y][z].isWall())
									tiles[x][y][z] = set.blc;
							else if(tiles[x][y-1][z].isWall()
									&& tiles[x-1][y][z].isWall())
									tiles[x][y][z] = set.tlc;
						}
					}
				}
			}
		}
		fillLeftOverCorners(set);
	}
	public void fillLeftOverCorners(TileSet set)
	{ 	// Fills corners with floor OUTSIDE the corner
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++) 
				{
					if(x-1 > 0 && x + 1 < width && y-1 > 0 && y + 1 < height) // transformation bound check
					{	
						if((tiles[x][y-1][z] == set.lrw || tiles[x][y-1][z] == set.tlc ) //done
								&& (tiles[x-1][y][z] == set.tbw || tiles[x-1][y][z] == set.tlc ))
								tiles[x][y][z] = set.brc;
						if((tiles[x][y-1][z] == set.lrw || tiles[x][y-1][z] == set.trc )
								&& (tiles[x+1][y][z] == set.tbw || tiles[x+1][y][z] == set.trc ))
								tiles[x][y][z] = set.blc;
						if((tiles[x][y+1][z] == set.lrw || tiles[x][y+1][z] == set.blc ) //done
								&& (tiles[x-1][y][z] == set.tbw || tiles[x-1][y][z] == set.blc ))
								tiles[x][y][z] = set.trc;
						if((tiles[x][y+1][z] == set.lrw || tiles[x][y+1][z] == set.brc) //done
								&& (tiles[x+1][y][z] == set.tbw || tiles[x+1][y][z] == set.brc))
								tiles[x][y][z] = set.tlc;
						
					}
				}
			}
		}
	}
	public static Direction chooseDirectionClosestToBorder(Point p)
	{
		Direction[] da = Direction.values();
		int z = 0;
		int distance = 1000;
		
		int nd = 1000;
		Direction d = null;
		
		for(int i = 0; i < da.length; i++) // chooses the shortest distance as long as its greater
		{								   // than max tunnel and room length
			if(da[i] == Direction.EAST)
			{
				nd = (width - (p.x + p.w - 1));
				if (nd < distance && nd > MAX_TUNNEL_LENGTH + MAX_ROOM_LENGTH)
				{
					distance = nd; z = i;
				}
			}
			else if(da[i] == Direction.WEST)
			{
				nd = ((p.x));
				if (nd < distance && nd > MAX_TUNNEL_LENGTH + MAX_ROOM_LENGTH)
				{
					distance = nd; z = i;
				}
			}
			else if(da[i] == Direction.NORTH)
			{
				nd = (p.y);
				if (nd < distance && nd > MAX_TUNNEL_LENGTH + MAX_ROOM_LENGTH)
				{
					distance = nd; z = i;
				}
			}
			else if(da[i] == Direction.SOUTH)
			{
				nd = height - (p.y + p.h -1);
				if (nd < distance && nd > MAX_TUNNEL_LENGTH + MAX_ROOM_LENGTH)
				{
					distance = nd; z = i;
				}
			}
		}
		da[z].setFurthest(distance);
		
		return da[z];
	}
	public Point getFirstRoomPoint(int depth)
	{
		Point p;
	
		do
		{
			int w = r.nextInt(25)+5;
			int h = r.nextInt(25)+5;
			
			int x = r.nextInt(width-w); 
			int y = r.nextInt(height-h); 
			
			p = new Point(x, y, depth);
			p.addWidth(w);
			p.addHeight(h);
		} while (!isValidPoint(p, p.w, p.h));
		
		return p;
	}
	public void buildRoom(RoomPoint rp, TileSet t)
	{
		if(isValidPoint(rp.point(), rp.w, rp.h))
		{
			for (int x = rp.x; x < rp.x+rp.w ; x++)
		    {
				for (int y = rp.y; y < rp.y+rp.h ; y++)
		        {	 
					if(x == rp.x || x == rp.x+rp.w-1)
						tiles[x][y][rp.z] = t.lrw ;
					else if (y == rp.y || y == rp.y+rp.h-1)
						tiles[x][y][rp.z] = t.tbw;
					else
					{
						if(t.equals(TileSet.CANISTERS)) // for filling whole room with same tile
							tiles[x][y][rp.z] = t.tlc;
						else // fills everything inside of the walls with ground
						{
						tiles[x][y][rp.z] = Tile.INSIDE_FLOOR;
						spawnPoints.add(new Point(x, y, rp.z));
						}
					}
					occupiedPoints.add(new Point(x, y, rp.z));
		        }
		    }	
			tiles[rp.x][rp.y][rp.z] = t.tlc;
			tiles[rp.x+rp.w-1][rp.y][rp.z] = t.trc;
			tiles[rp.x][rp.y+rp.h-1][rp.z] = t.blc;
			tiles[rp.x+rp.w-1][rp.y+rp.h-1][rp.z] = t.brc;
		}
		else
			System.out.println("The room was NOT built");
	}
	public void randomizeFloor()
	{
	
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++) 
				{
					tiles[x][y][z] = Math.random() < 0.5 ? Tile.WALL : Tile.RED_WALL;
				}
			}
		}
	}
	public static boolean isValidPoint(Point p, int w, int h)
	{
		if(p.x + w -1 >= width || p.y + h -1 >= height || p.x == width || p.y == height
				|| p.x < 0 || p.y < 0)
			return false;
		else
			return true;
	}
	public ArrayList<Point> getSpawnPoints()
	{
		return spawnPoints;
	}
	public ArrayList<Point> getOccupiedPoints()
	{
		return occupiedPoints;
	}


	public ArrayList<Point> getStartingPoints() {
		return startingPoints;
	}
}
