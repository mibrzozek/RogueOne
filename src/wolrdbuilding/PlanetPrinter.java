package wolrdbuilding;

import entities.Entity;
import structures.Dungeon;

import java.util.ArrayList;
import java.util.Random;

public class PlanetPrinter
{
	private static final int MINI_CELL = 3;
	private static int width;
	private static int height;
	private int depth;

	private Dungeon dungeon;
	
	private Entity player;
	private TileV[][][] tiles;
	private TileV[][][] miniMap;

	private ArrayList<Point> spawnPoints;
	private static ArrayList<Point> occupiedPoints;
	private static ArrayList<Point> startingPoints;
	private static ArrayList<Point> stairPoints;
	private Random r;
	
	private Point lastPoint;
	private int lastW;
	private int lastH;
	
	static final int MAX_TUNNEL_LENGTH = 3;
	static final int MAX_TUNNEL_WIDTH = 3;
	
	static final int MAX_ROOM_LENGTH = 30;
	static final int MIN_ROOM_LENGTH = 5;
	
	
	public PlanetPrinter(int width, int height, int depth, Entity player)
	{
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		this.player = player;
		this.tiles = new TileV[width][height][depth];
		this.tiles = new TileV[width/MINI_CELL][height/MINI_CELL][depth];
		
		this.spawnPoints = new ArrayList<>();
		this.occupiedPoints = new ArrayList<>();
		this.stairPoints = new ArrayList<>();
		r = new Random();
	}
	public World build()
	{

		System.out.println("before retuning world");

		return new World(tiles, spawnPoints, startingPoints, player, dungeon);
	}
	static boolean isValidPoint(Point p, int w, int h)
	{
		if(p.x + w -1 >= width || p.y + h -1 >= height || p.x == width || p.y == height
				|| p.x < 0 || p.y < 0)
			return false;
		else
			return true;
	}
	public PlanetPrinter randomizeFloor()
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++) 
				{
					tiles[x][y][z].setTile(Math.random() < 0.5 ? Tile.WALL : Tile.INSIDE_FLOOR);
					
					if(tiles[x][y][z].getTile() == Tile.INSIDE_FLOOR)
						spawnPoints.add(new Point(x, y, z));
				}
			}
		}
		return this;
	}
	public PlanetPrinter buildRoom(RoomPoint rp, TileSet t)
	{
		if(isValidPoint(rp.point(), rp.w, rp.h))
		{
		for (int x = rp.x; x < rp.x+rp.w ; x++)
	    {
			for (int y = rp.y; y < rp.y+rp.h ; y++)
	        {	 
				if(x == rp.x || x == rp.x+rp.w-1)
					tiles[x][y][rp.z].setTile(t.lrw) ;
				else if (y == rp.y || y == rp.y+rp.h-1)
					tiles[x][y][rp.z].setTile(t.tbw);
				else
				{
					tiles[x][y][rp.z].setTile(Tile.INSIDE_FLOOR);
					spawnPoints.add(new Point(x, y, rp.z));
				}
				occupiedPoints.add(new Point(x, y, rp.z));
	        }
	    }
		tiles[rp.x][rp.y][rp.z].setTile(t.tlc);
		tiles[rp.x+rp.w-1][rp.y][rp.z].setTile(t.trc);
		tiles[rp.x][rp.y+rp.h-1][rp.z].setTile(t.blc);
		tiles[rp.x+rp.w-1][rp.y+rp.h-1][rp.z].setTile(t.brc);
		}
		else
			System.out.println("The room was NOT built");		/*
		tiles[p.x+r.nextInt(w-1)+ 1][p.y][p.z] = Tile.DOOR;
		tiles[p.x][p.y+r.nextInt(h-1)+ 1][p.z] = Tile.DOOR;
		tiles[p.x + w - 1][p.y+r.nextInt(h-1)+ 1][p.z] = Tile.DOOR;
		tiles[p.x+r.nextInt(w-1)+ 1 ][p.y+h-1][p.z] = Tile.DOOR;
		*/

	
		return this;	 
	}
	public PlanetPrinter buildTunnel(RoomPoint rp)
	{
		RoomPoint tp = rp.calculateTunnelPoint();
		//tp.setTileSet(TileSet.UP_DOWN_TUNNEL_S);
		buildRoom(tp, tp.tileSet());

		return this;
	}
	// 	DUNGEON GEN
	public PlanetPrinter makeDungeons(World.Map m)
	{
		dungeon = new Dungeon(width, height, depth);

		// Done after dungeon is done
		tiles = dungeon.getNewDungeon(m);
		spawnPoints = dungeon.getSpawnPoints();				//
		occupiedPoints = dungeon.getOccupiedPoints();		//
		startingPoints = dungeon.getStartingPoints();
		stairPoints = (ArrayList<Point>) dungeon.getStairPoints();

		/*
		Direction d = null;
		
		Point p = getFirstRoomPoint();
		d = chooseDirectionClosestToBorder(p);
		
		RoomPoint rp = new RoomPoint(p, p.w, p.h, d);
		buildRoom(rp, TileSet.SIMPLE);
		
		for(int i = 0; i < 8; i++)
		{
			rp.calculateTunnelPoint();
			RoomPoint np = getPointRelativeTo(rp.getTunnelPosition(), rp.direction());
			buildRoom(np, TileSet.SIMPLE);
			buildTunnel(rp);
			
			rp = np;
			rp.setDirection(chooseNextUnoccupiedDirection(rp.point(), rp.direction()));
			// d = chooseDirection(np);
		}	
			//rp = new RoomPoint(np
		//p = getNextPoint(d);
		//buildRoom(rp, TileSet.SIMPLE);
		
		
	
		System.out.println(d.toString() + d.getFurthest());
		*/
		return this;
	}

	// DUNGEON HELPERS
	public RoomPoint getPointRelativeTo(Point rp, Direction d)
	{
		int count = 0;
		RoomPoint newRoom;
		Point p = new Point(11, 11, 0);
		// decide calculation based on direction
		// getTwoPoint which represent  the area
		// pick random point in area
		// 
		if(d ==  Direction.WEST)
		{	
			do
			{
				p.w = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				p.h = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				
				p.x = rp.x - p.w  +1; // moves from rp towards 0
				p.y = rp.y - (p.h/4);
				count++;
			} while(!isValidPoint(p, p.w, p.h) && count < 20);
					
				
		}
		else if(d ==  Direction.EAST)
		{
			do
			{
				p.w = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				p.h = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				
				p.x = rp.x + (rp.w -1);
				p.y = rp.y - (p.h/4);			
				count++;
			} while(!isValidPoint(p, p.w, p.h) && count < 20);
		}
		else if(d ==  Direction.NORTH)
		{
			do
			{
				p.w = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				p.h = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				
				p.x = rp.x - (p.w/4);
				p.y = rp.y - (p.h-1);
				count++;
			} while(!isValidPoint(p, p.w, p.h) && count < 20);
		}
		else if(d ==  Direction.SOUTH)
		{
			do
			{
				p.w = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				p.h = r.nextInt(MAX_ROOM_LENGTH - MIN_ROOM_LENGTH) +MIN_ROOM_LENGTH;
				
				p.x = rp.x - (p.w/4);
				p.y = rp.y + (rp.h -1);			
				count++;
			} while(!isValidPoint(p, p.w, p.h) && count < 20);
		}
		
		//System.out.println(p.x + " this is x\n" + p.y + " this is y" );
		//System.out.println(rp.x + " this is rx\n" + rp.y + " this is ry" );
		
		if(!isValidPoint(p, p.w, p.h))
		{
			d = chooseNextUnoccupiedDirection(rp.point(), d);
			
			newRoom = getPointRelativeTo(rp, d);
		}
		else
			newRoom =  new RoomPoint(p, p.w, p.h, d);
	
		
		return newRoom;
	}
	
	public Point randomPointFromSpecifiedArea()
	{
		return null;
	}
	public Point getFirstRoomPoint()
	{
		Point p;
	
		do
		{
			int w = r.nextInt(25)+5;
			int h = r.nextInt(25)+5;
			
			int x = r.nextInt(width-w); 
			int y = r.nextInt(height-h); 
			
			p = new Point(x, y, 0);
			p.addWidth(w);
			p.addHeight(h);
		} while (!isValidPoint(p, p.w, p.h));
		
		return p;
	}
	public Point getNextPoint(Direction d)
	{
		Point p = null;
		int  i = 0;
		do
		{
			i++;
			int w = r.nextInt(25)+5;
			int h = r.nextInt(25)+5;
			
			if(d == Direction.NORTH)
				p = new Point(lastPoint.x - w/3, lastPoint.y -h +1, 0);
			else if(d == Direction.SOUTH)
				p = new Point(lastPoint.x - w/3, lastPoint.y + lastH +2, 0);
			else if(d == Direction.EAST)
				p = new Point(lastPoint.x + lastH + 2, lastPoint.y - h/3, 0);
			else if(d == Direction.WEST)
				p = new Point(lastPoint.x - w + 1, lastPoint.y - lastH/3, 0);
			

			p.addWidth(w);
			p.addHeight(h);
		} while (!isValidPoint(p, p.w, p.h) && i < 100);
		
		return p;
	}

	
	public PlanetPrinter smoothSpawns()
	{
		for(Point p: spawnPoints)
			tiles[p.x][p.y][p.z].setTile(Tile.INSIDE_FLOOR);
		
		return this;
		
	}
	public static Direction chooseNextUnoccupiedDirection(Point p, Direction lastD)
	{
		Direction[] da = Direction.values();
		int z = 0;
		Direction d =  null;
		
		for(int i = 0; i < da.length; i++) 
		{
			if(da[i] == Direction.EAST)
			{
				p.x = p.x + p.w - 1;
				for(int x = p.x; x < p.x + p.w + 4; x++)
				{
					for(int y = p.y; y < p.y +p.h + 4; y++)
					{
						if(!occupiedPoints.contains(new Point(x, y, p.z)))
							d = Direction.EAST;
							
					}
				}
			}
			else if(da[i] == Direction.WEST)
			{
				p.x = p.x - p.w + 1;
				for(int x = p.x; x < p.x + p.w -1 ; x++)
				{
					for(int y = p.y; y < p.y +p.h -1; y++)
					{
						if(!occupiedPoints.contains(new Point(x, y, p.z)))
							d = Direction.WEST;
							
					}
				}
			}
			else if(da[i] == Direction.NORTH)
			{
				p.y = p.y - p.h + 1;
				for(int x = p.x; x < p.x + p.w + 4; x++)
				{
					for(int y = p.y; y < p.y +p.h + -1; y++)
					{
						if(!occupiedPoints.contains(new Point(x, y, p.z)))
							d = Direction.NORTH;
							
					}
				}
			}
			else if(da[i] == Direction.SOUTH)
			{
				p.y = p.y + p.h - 1;
				for(int x = p.x; x < p.x + p.w + 4; x++)
				{
					for(int y = p.y; y < p.y +p.h + -1; y++)
					{
						if(!occupiedPoints.contains(new Point(x, y, p.z)))
							d = Direction.NORTH;
							
					}
				}
			}
		}
		
		return d;
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



}
