package structures;

import java.util.*;
import java.util.List;

import wolrdbuilding.*;
import wolrdbuilding.Point;

public class Dungeon
{
	private static int width, height, depth, nextRegion;
	private static TileV[][][] tiles;
	private static TileV[][][] newTiles;
	private static int[][][] regions;
	
	private static ArrayList<Point> spawnPoints;
	private static ArrayList<Point> startingPoints;
	private ArrayList<Point> occupiedPoints;
	private ArrayList<Point> roomPoints;
	private ArrayList<Point> allWalls;
	private ArrayList<Door> doors;

	private ArrayList<Point> doorCandidates;

	private ArrayList<Point> wallPoints;


	private List<List> regionList;
	private List<List> regionMapOfFloor;

	private Map<Integer, List<List>> regionMap;

	private HashMap<String, ArrayList<TilePoint>> structureMap;
	private HashMap<Integer, ArrayList<TilePoint>> partsMap;
	
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
		
		this.tiles = new TileV[width][height][depth];
		this.newTiles = new TileV[width][height][depth];

		
		this.spawnPoints = new ArrayList<>();
		this.occupiedPoints = new ArrayList<>();
		this.wallPoints = new ArrayList<>();
		this.allWalls = new ArrayList<>();
		this.doorCandidates = new ArrayList<>();
		this.doors = new ArrayList<>();


		this.regionMapOfFloor = new ArrayList<>();
		this.regionMap = new HashMap<>();
		this.regionList = new ArrayList<>();
		this.nextRegion = 1;

		RexReader rex = new RexReader();
		this.structureMap = rex.getStructures();
		this.partsMap = rex.getDecorations();
	}
	public TileV[][][] getNewDungeon()
	{
		randomizeFloor();
		randomApproachToDungeons();
		return tiles;
	}
	public void randomApproachToDungeons()
	{
		Point p =  null;
		
		for(int i = 0; i < 50; i++)
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
		smooth();
		//narrow(4);

		makeStartingRoom();

		fillDungeonWithWall(TileSet.SIMPLE);

		makeOtherRooms();
		//addExitStairs();
		makeStairsDown();
		makeLaserTraps();
		addRoomDecor();
		//spawnMethane();
		
	}
	public void makeOtherRooms()
	{
		Point p = new Point(0, 0, 0);
		RoomPoint roomPoint = new RoomPoint(p, 0, 0);

		List<RoomPoint> toBuild=  new ArrayList<>();

		boolean found = false;
		int bw = 15;
		int count = 0;
		int newRoom = 0;

		//System.out.println(p.toString() + " before search");
		for(int i = 0; i < 40; i++)
		{
			found  = false;

			do // look for a point with a room to the right or below it
			{


				do {
					p = allWalls.get(r.nextInt(allWalls.size())); // get a wall point
				} while (p.z != 0); // from zero floor for testing

				Direction d = null;

				//System.out.println(tiles[p.x][p.y][p.z].getTile().toString() + " is the tile a wall?    ***********************************");
				//System.out.println(allWalls.size() + " all walls size" + wallPoints.size() + " wallPoints size");


				//System.out.println(p.toString() + "       in search");

				if (tiles[p.x + 1][p.y][p.z].getTile().isWall()) {
					d = Direction.EAST;
					p = new Point(p.x + 1, p.y, p.z);
				} else if (tiles[p.x - 1][p.y][p.z].getTile().isWall()) {
					d = Direction.WEST;
					p = new Point(p.x - 1, p.y, p.z);
				} else if (tiles[p.x][p.y + 1][p.z].getTile().isWall()) {
					d = Direction.SOUTH;
					p = new Point(p.x, p.y + 1, p.z);
				} else if (tiles[p.x][p.y - 1][p.z].getTile().isWall()) {
					d = Direction.NORTH;
					p = new Point(p.x, p.y - 1, p.z);
				}
				int roomX = 0;
				int roomY = 0;

				if (d == Direction.EAST || d == Direction.SOUTH) {
					boolean isGood = false;

					for (int y = 23; y > 8; y--) // height
					{
						for (int x = 23; x > 8; x--) // width
						{
							//System.out.println(x + " " + y + " " + "loop points " + count++ + " count");

							if (p.x + x >= 200 || p.y + y >= 200)
							{
								//System.out.println("Out of bounds, lets try next one");
								//System.out.println(p.toString());
								continue;
							}


							List<Point> room = p.gridXbyX(p, x, y); // each iteration x gets smaller

							int other = 0;

							for (Point rp : room)
							{
								if (!tiles[rp.x][rp.y][rp.z].getTile().isWall())
								{
									other++;
									//ystem.out.println("			This is not a wall!");
									continue;
								}

								//System.out.println(tiles[p.x + x][p.y + y][p.z].getTile().toString() + rp.toString());
							}
							if (other == 0)
							{
								found = true;
								roomPoint = new RoomPoint(p, x, y);

								//System.out.println(x + " width " + y + " height " + p.z + " level");
								x = 0;
								y = 0;

								//System.out.println("We found something!");

								newRoom++;
							}
						}
					}
				}
			} while (!found && count < 500);
			System.out.println(i + " this is I");

			if(found)
				buildLockedRoom(roomPoint, TileSet.DOUBLE);
		}


		System.out.println(newRoom + " new rooms built!");
	}
	public void buildLockedRoom(RoomPoint rp, TileSet t)
	{
		buildRoom(rp, t);

		boolean south = false;
		boolean north = false;
		boolean east = false;
		boolean west = false;

		Direction d  = null;
		Point np = new Point(0, 0, 0);
		Point sp = new Point(0, 0, 0);
		Point ep = new Point(0, 0, 0);
		Point wp = new Point(0, 0, 0);

		Collections.shuffle(doorCandidates);

		for(Point p : doorCandidates) // doorCandidates is created in buildRoom()
		{
			System.out.println("looking for candiodates" + p.toString()
			+ tiles[p.x][p.y+1][p.z].getTile() + "  " + tiles[p.x][p.y][p.z].getTile());


			if(tiles[p.x][p.y][p.z].getTile().equals(Tile.dblTBW))
			{
				if((tiles[p.x][p.y+1][p.z].getTile().equals(Tile.dblTBW) ||tiles[p.x][p.y+1][p.z].getTile().equals(Tile.simpleTBW) )
						&& !south)
				{
					d = Direction.SOUTH;
					sp = new Point(p.x, p.y, p.z);
					south = true;
				}
				else if((tiles[p.x][p.y-1][p.z].getTile().equals(Tile.dblTBW) ||tiles[p.x][p.y-1][p.z].getTile().equals(Tile.simpleTBW) )
						&& !north)
				{
					d = Direction.NORTH;
					np = new Point(p.x, p.y, p.z);
					north = true;
				}
			}
			else if(tiles[p.x][p.y][p.z].getTile().equals(Tile.dblLRW))
			{
				if((tiles[p.x+1][p.y][p.z].getTile().equals(Tile.dblLRW) || tiles[p.x+1][p.y][p.z].getTile().equals(Tile.simpleLRW))
						&& !east)
				{
					d = Direction.EAST;
					ep = new Point(p.x, p.y, p.z);
					east = true;
				}
				else if((tiles[p.x-1][p.y][p.z].getTile().equals(Tile.dblLRW) || tiles[p.x-1][p.y][p.z].getTile().equals(Tile.simpleLRW))
				&& !west)
				{
					d = Direction.WEST;
					wp = new Point(p.x, p.y, p.z);
					west = true;
				}
			}
		}

		System.out.println(np.toString() + sp.toString() + ep.toString() + wp.toString());

		int w = 4, h = 4;

		if(!np.equals(new Point(0, 0, 0)) && north)
		{
			tiles[np.x][np.y][np.z].setTile(Tile.CLOSED_DOOR);
			tiles[np.x][np.y-1][np.z].setTile(Tile.CLOSED_DOOR);
			doors.add(new Door(np, new Point(np.x, np.y-1,np.z), Door.getRandomClearance()));
			//buildRoom(new RoomPoint(new Point(np.x, np.y-1, np.z), w, h), TileSet.UP_DOWN_TUNNEL_S);
		}
		if(!sp.equals(new Point(0, 0, 0)) && south)
		{
			tiles[sp.x][sp.y][sp.z].setTile(Tile.CLOSED_DOOR);
			tiles[sp.x][sp.y+1][sp.z].setTile(Tile.CLOSED_DOOR);
			doors.add(new Door(sp, new Point(sp.x, sp.y+1,sp.z), Door.getRandomClearance()));
			//buildRoom(new RoomPoint(new Point(sp.x, sp.y, sp.z), w, h), TileSet.UP_DOWN_TUNNEL_S);
		}
		if(!ep.equals(new Point(0, 0, 0)) && east)
		{
			tiles[ep.x][ep.y][ep.z].setTile(Tile.CLOSED_DOOR);
			tiles[ep.x+1][ep.y][ep.z].setTile(Tile.CLOSED_DOOR);
			doors.add(new Door(ep, new Point(ep.x+1, ep.y,ep.z), Door.getRandomClearance()));
			//buildRoom(new RoomPoint(new Point(ep.x, ep.y, ep.z), w, h), TileSet.LEFT_RIGHT_TUNNEL_S);
		}
		if(!wp.equals(new Point(0, 0, 0)) && west)
		{
			tiles[wp.x][wp.y][wp.z].setTile(Tile.CLOSED_DOOR);
			tiles[wp.x-1][wp.y][wp.z].setTile(Tile.CLOSED_DOOR);
			doors.add(new Door(wp, new Point(wp.x-1, wp.y,wp.z), Door.getRandomClearance()));
			//buildRoom(new RoomPoint(new Point(wp.x-1, wp.y, wp.z), w, h), TileSet.LEFT_RIGHT_TUNNEL_S);
		}
		north = false;
		south = false;
		east = false;
		west = false;

	}
	public void narrow(int factor)
	{
		int offset = 5;

		for(int ix = 0; ix < factor; ix++)
		{
			fillDungeonWithWall(TileSet.SIMPLE);

			for (int z2 = 0; z2 < depth; z2++)
			{
				for (int y2 = 0; y2 < height; y2++)
				{
					for (int x2 = 0; x2 < width; x2++)
					{

						if (tiles[x2][y2][z2].getTile().isRoom()) // if room
						{
							if (x2 + offset < width && x2 - offset > 0 && y2 + offset < height && y2 - offset > 0) // and within bound
							{
								if (tiles[x2 + offset][y2][z2].getTile().isFloor() || tiles[x2 - offset][y2][z2].isFloor())
										//|| tiles[x2][y2 + offset][z2].isFloor() || tiles[x2][y2 - offset][z2].isFloor())// and has room to left right up or down
								{
									tiles[x2][y2][z2] = new TileV(Tile.RED_WALL);                        // then change to wall
									System.out.println("Do i ever show up?");
								}
							}
						}
					}
				}
			}
		}
	}
	public void spawnMethane()
	{
		for(int i = 0; i < 30; i++)
		{
			Point p = new Point(0, 0, 0);
			Boolean found = false;

			do
			{
				int x = r.nextInt(width);
				int y = r.nextInt(height);

				TileV tv = tiles[x][y][0];
				if (tv.getTile() == Tile.INSIDE_FLOOR)
				{
					found = true;
					p = new Point(x, y, 0);
				}
			} while (!found);

			//System.out.println("FIRST CLOUD : " + p.toString());

			List<Point> cloud = p.neighbors8();
			List<Point> cloud1 = cloud.get(r.nextInt(cloud.size())).neighbors8();
			List<Point> cloud2 = cloud.get(r.nextInt(cloud.size())).neighbors8();

			cloud.addAll(cloud1);
			cloud.addAll(cloud2);

			for (Point ps : cloud)
			{
				if (tiles[ps.x][ps.y][ps.z].getTile() == Tile.INSIDE_FLOOR)
				{
					tiles[ps.x][ps.y][ps.z] = new TileV(Tile.METHANE);
				}
			}
		}
	}
	public void smooth()
	{
		int cellSize = 3;
		TileV[][][] miniature = MiniMap.getNewMiniMap(tiles, width, height, depth, cellSize);

		System.out.println(miniature.length + " " + miniature[0].length + " " + miniature[0][0].length);
		//TileV[][][] newTiles = new TileV[width][height][depth];

		//newTiles = tiles;

		int miniX = 0, miniY = 0;

		for(int z2 = 0; z2 <depth; z2++)
		{
			for (int y2 = 0; y2 < height; y2++)
			{
				for (int x2 = 0; x2 < width; x2++)
				{
					newTiles[x2][y2][z2] = new TileV(Tile.RED_WALL);
				}
			}
		}

		for(int z1 = 0; z1 <depth; z1++)
		{
			miniX = 0;
			miniY = 0;
			for (int y1 = 0; y1 < height; y1 += cellSize)
			{
				for (int x1 = 0; x1 < width; x1 += cellSize)
				{
					//System.out.println("mx : " + miniX + " my : " + miniY);
					//System.out.println("x : " + x1 + " y : " + y1);
					//System.out.println(miniature[miniX][miniY][z1].getTile().isGround() + " <- is ground");


					//newTiles[x1][y1][z1] = tiles[x1][y1][z1];
					Point np = new Point(x1, y1, z1);

					List<Point> cell = np.gridXbyX(np, cellSize, cellSize);

					int floors = 0;

					for(Point  p : cell)
					{
						if(p.x < width && p.y < height && p.z < depth)
						{
							if (tiles[p.x][p.y][p.z].getTile().isFloor())
							{
								floors++;
							}
						}
					}
					if(floors > 3)
					{

						for(Point  p : cell)
						{
							if(p.x < width && p.y < height && p.z < depth)
							{
								newTiles[p.x][p.y][p.z] = new TileV(Tile.INSIDE_FLOOR);
								//System.out.println("Stamping floor!");
							}
						}
					}
					//check cell
					if(miniX + 1 < 67)
					{
						miniX++;
					}
					else
						miniX = 0;
				}
				if(miniY + 1 < 67)
					miniY++;

			}
		}
		tiles = newTiles;
		/*
		System.out.println(newTiles.length + " mini length in smooooooth");

		int miniX = 0, miniY = 0;

		for(int z1 = 0; z1 <depth; z1++) {
			for (int y1 = 0; y1 < height; y1++) {
				for (int x1 = 0; x1 < width; x1++) {
					newTiles[x1][y1][z1] = new TileV(Tile.RED_WALL);
				}
			}
		}
		for(int z = 0; z <depth; z++)
		{
			miniX = 0;
			miniY = 0;

			for(int y = 0; y < height; y+=3)
			{
				for (int x = 0; x < width; x+=3)
				{
					Point p = new Point(x, y, z);
					if(z  ==0 )
						System.out.println("In cell " + p.toString());

					List<Point> cell = p.gridXbyX(p, 3);

					for(Point cp : cell)
					{
						if(cp.x < width && cp.y < height)
						{
							if(miniX < 66 && miniY < 66)
							{
								if (miniature[miniX][miniY][z].getTile().isFloor())
									newTiles[cp.x][cp.y][cp.z] = new TileV(Tile.INSIDE_FLOOR);
								else
									newTiles[cp.x][cp.y][cp.z] = new TileV(Tile.RED_WALL);

								if(z  ==0 )
								System.out.println("x " + cp.x + " y " + cp.y);
							}
						}
					}
					if(z  ==0 )
						System.out.println("mini x " + miniX + " miniY " + miniY);

					if (miniX < 66)
						miniX++;
					else
						miniX = 0;

					if (miniX == 66 && miniY < 66) {
						miniY++;
					}
				}
			}
		}
		tiles = newTiles;

		*/
		//System.out.println(tiles.length + " new tiles as TILES length in smooooooth");
	}
	public RoomPoint makeStartingRoom()
	{
		int w = 25, h = 25;
		Point p;
		do {
			p = getSpawnPointFromLevel(0);
		}while(!isValidPoint(p, w, h));

		RoomPoint rp = new RoomPoint(p, w, h);
		buildRoom(rp, TileSet.INSIDE_TILE);

		//buildPlasmaBlock(rp, 7);
		buildStructure(new ArrayList<TilePoint>(structureMap.get("PT_9" +
				".csv")), new Point(rp.x + 4, rp.y + 4, 0));

		startingPoints = getOpenPointFromRegion(rp.point(), 15, 15);
		// Removes spawn points for enemies form starting area
		for(Point po : startingPoints)
		{
			if(spawnPoints.contains(po))
				spawnPoints.remove(po);
		}
		return rp;
	}
	public void addRoomDecor()
	{
		int numDecor = partsMap.keySet().size();
		ArrayList<TilePoint> list;
		ArrayList<Point> builtP = new ArrayList<>();
		Point p= new Point(0, 0, 0);

		for(int j = 0; j < 200; j++)
		{
			list = partsMap.get(r.nextInt(numDecor));

			//System.out.println(partsMap.size() + " map size");
			boolean found = false;
			do
			{
				p = new Point(r.nextInt(width -13), r.nextInt(height-13), 0);
				TileV tile = tiles[p.x][p.y][0];

				if (tile.getTile() == Tile.simpleTBW)
				{
					boolean clear = true;
					for (int i = 0; i < list.size(); i++)
					{
						if(i + p.x > width)
							i = 13;
						//.out.println(list.size());
						if (tiles[p.x + i][p.y][0].getTile() != Tile.simpleTBW)
						{
							clear = false;
						}
						//System.out.println(tiles[p.x + i][p.y][0].getTile() );
					}
					if (clear)
						found = true;
				}
			} while (!found);

			for(TilePoint tp : list)
			{
				tp.setY(0);
			}
			buildStructure(list, p);
			builtP.add(p);
		}

		//System.out.println("\t\t All point where decor goes" + "\n" + builtP.toString());
		//System.out.println(p.toString() + " " + tiles[p.x][p.y][0].getTile());

		//System.out.println(partsMap.size() + " map size");
	}
	public void buildStructure(ArrayList<TilePoint> structure, Point p)
	{
		//System.out.println(structure.size() + " from the new method");
		ArrayList<TilePoint> copy = structure;

		while(!structure.isEmpty())
		{
			TilePoint t = structure.remove(0);
			if(t.ascii() == 250)
				continue;
			//System.out.println("In dungeon " + t.toString());

			tiles[p.x + t.x()][p.y + t.y()][p.z].setTile((char)t.ascii(), t.foreground(), t.background(), true);
		}
		structure = copy;
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
	private CorridorPoint getCorridorPoint()
	{
		Point p = null;
		CorridorPoint cp;
		do
		{
			p = getSpawnPointFromLevel(0);
			//System.out.println("From dungeon " + p.toString());
			cp = new CorridorPoint(p);

		} while(!cp.isCorridor(tiles));

		return cp;
	}
	public boolean bound(Point p)
	{
		int bound = 30;

		if (p.x < bound || p.x > width - bound || p.y < bound || p.y > width - bound)
			return false;
		else
			return true;
	}
	/*
	{
		Point p = null;
		CorridorPoint cp;
		do
		{
			p = getSpawnPointFromLevel(0);
			//System.out.println("From dungeon " + p.toString());
			cp = new CorridorPoint(p);

		} while(!cp.isCorridor(tiles));

		return cp;
	}
	*/
	private void makeLaserTraps()
	{
		//find random inside point next to a wall
		// see if corridor
		// connect

		for(int i = 0; i < 15; i++)
		{
			CorridorPoint cp = getCorridorPoint();
			//tiles[cp.x][cp.y][cp.z] = Tile.PLASMA_CANISTER_2;

			makeLine(cp.point(), cp.getdOfHall(), cp.getHallLength());
		}
	}
	private void makeLine(Point p, Direction d, int length)
	{
		int movement = 1, corridor = 7;

		if(d.equals(Direction.WEST) || d.equals(Direction.NORTH))
			movement = -1;

		for(int i = 0; i < length; i++)
		{
			if(p.x > 199 || p.x < 1 || p.y > 199 || p.y < 1 || tiles[p.x][p.y][p.z].getTile().isRoom()) {
				i = length;
				return;
			}
			if(d.equals(Direction.WEST) || d.equals(Direction.EAST))
			{
				if(tiles[p.x][p.y][p.z].isFloor())
					tiles[p.x][p.y][p.z].setTile(Tile.LEFT_RIGHT_SINGLE_LASER);
				p.x += movement;
			}
			else
			{
				if(tiles[p.x][p.y][p.z].isFloor())
					tiles[p.x][p.y][p.z].setTile(Tile.UP_DOWN_SINGLE_LASER);
				p.y += movement;
			}
		}
	}
	/*
	public boolean bound(Point p)
	{
		int bound = 30;
		
		if(p.x < bound || p.x > width - bound || p.y < bound || p.y > width - bound)
			return false;
		else
			return true;
	}
	*/
	public boolean isNextToWall(Point p)
	{
		if(tiles[p.x + 1][p.y][p.z].getTile().isRoom()
			|| tiles[p.x - 1][p.y][p.z].getTile().isRoom()
			|| tiles[p.x][p.y + 1][p.z].getTile().isRoom()
			|| tiles[p.x][p.y - 1][p.z].getTile().isRoom())
			return true;
		else
			return false;
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
					tiles[p.x][p.y][p.z].setTile(Tile.STAIRS_DOWN);
					tiles[p.x][p.y][p.z + 1].setTile(Tile.STAIRS_UP);
				
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
        System.out.println("regions : " +  nextRegion + " Largest Region :" + largestRegion);
		System.out.println(regionList.size() + " new regions!");

		Point p = new Point(1, 1, 1);
		List<Point> fromZero = new ArrayList<Point>();

		/*
		for(int i =0; i <  3; i++)
		{
			do
			{
				fromZero = regionList.get(r.nextInt(regionList.size()));
				p = fromZero.get(r.nextInt(fromZero.size()));

			} while (p.z != 0);

			System.out.println(p.toString() + " is a region on floor o");

			for (Point ps : fromZero)
			{
				TileV tv = new TileV(Tile.INSIDE_FLOOR.glyph(), Palette.purple, Palette.white);
				tv.setTile(Tile.getBurnedTile());

				tiles[ps.x][ps.y][ps.z] = tv;
			}
		}
		*/
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
					tiles[x][y][z].setTile(Tile.WALL);
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
	private void addExitStairs() 
	{
        int x = -1;
        int y = -1;
    
        do 
        {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (tiles[x][y][0].getTile() != Tile.FLOOR);
    
        tiles[x][y][0].setTile(Tile.STAIRS_EXIT);
    }
	public void fillDungeonWithWall(TileSet set)
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++) 
				{
					if(tiles[x][y][z].getTile() == Tile.INSIDE_FLOOR)
					{	
						if(x-1 > 0 && x + 1 < width && y-1 > 0 && y + 1 < height)
						{
							boolean isWall = true;

							if(tiles[x+1][y][z].getTile() == Tile.INSIDE_FLOOR
									&& tiles[x-1][y][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.lrw);
								allWalls.add(new Point(x, y, z));
							}
							else if(tiles[x][y+1][z].getTile() == Tile.INSIDE_FLOOR
									&& tiles[x][y-1][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.tbw);
							}
							else if(tiles[x-1][y][z].getTile() == Tile.INSIDE_FLOOR
									&& tiles[x+1][y][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.lrw);
								allWalls.add(new Point(x, y, z));
							}
							else if(tiles[x][y-1][z].getTile() == Tile.INSIDE_FLOOR
									&& tiles[x][y+1][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.tbw);
								allWalls.add(new Point(x, y, z));
							}
							// Fills corners with floor tiles INSIDE the corner
							if(tiles[x][y+1][z].getTile().isWall()
									&& tiles[x+1][y][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.brc);
								allWalls.add(new Point(x, y, z));
							}
							else if(tiles[x][y-1][z].getTile().isWall()
									&& tiles[x+1][y][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.trc);
								allWalls.add(new Point(x, y, z));
							}
							else if(tiles[x][y+1][z].getTile().isWall()
									&& tiles[x-1][y][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.blc);
								allWalls.add(new Point(x, y, z));
							}
							else if(tiles[x][y-1][z].getTile().isWall()
									&& tiles[x-1][y][z].getTile().isWall())
							{
								tiles[x][y][z].setTile(set.tlc);
								allWalls.add(new Point(x, y, z));
							}
							else
								isWall = false;

							if(isWall)
							{
								wallPoints.add(new Point(x, y, z));
							}
						}
					}
				}
			}
		}
		fillLeftOverCorners(set);
		System.out.println(wallPoints.size());

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
						if((tiles[x][y-1][z].getTile() == set.lrw || tiles[x][y-1][z].getTile() == set.tlc ) //done
								&& (tiles[x-1][y][z].getTile() == set.tbw || tiles[x-1][y][z].getTile() == set.tlc ))
								tiles[x][y][z].setTile(set.brc);
						if((tiles[x][y-1][z].getTile() == set.lrw || tiles[x][y-1][z].getTile() == set.trc )
								&& (tiles[x+1][y][z].getTile() == set.tbw || tiles[x+1][y][z].getTile() == set.trc ))
								tiles[x][y][z].setTile(set.blc);
						if((tiles[x][y+1][z].getTile() == set.lrw || tiles[x][y+1][z].getTile() == set.blc ) //done
								&& (tiles[x-1][y][z].getTile() == set.tbw || tiles[x-1][y][z].getTile() == set.blc ))
								tiles[x][y][z].setTile(set.trc);
						if((tiles[x][y+1][z].getTile() == set.lrw || tiles[x][y+1][z].getTile() == set.brc) //done
								&& (tiles[x+1][y][z].getTile() == set.tbw || tiles[x+1][y][z].getTile() == set.brc))
								tiles[x][y][z].setTile(set.tlc);
						
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
		doorCandidates.clear();

		if(isValidPoint(rp.point(), rp.w, rp.h))
		{
			for (int x = rp.x; x < rp.x+rp.w ; x++)
		    {
				for (int y = rp.y; y < rp.y+rp.h ; y++)
		        {	 
					if(x == rp.x || x == rp.x+rp.w-1)
					{
						tiles[x][y][rp.z].setTile(t.lrw);
						doorCandidates.add(new Point(x, y, rp.z));
					}
					else if (y == rp.y || y == rp.y+rp.h-1)
					{
						tiles[x][y][rp.z].setTile(t.tbw);
						doorCandidates.add(new Point(x, y, rp.z));
					}
					else
					{
						if(t.equals(TileSet.CANISTERS)) // for filling whole room with same tile
							tiles[x][y][rp.z].setTile(t.tlc);
						else // fills everything inside of the walls with ground
						{
						tiles[x][y][rp.z].setTile(Tile.INSIDE_FLOOR);
						spawnPoints.add(new Point(x, y, rp.z));
						}
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
					tiles[x][y][z] = new TileV( (Math.random() < 0.5 ? Tile.WALL : Tile.RED_WALL) );
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

	public ArrayList<Door> getDoorPoints() {
		return doors;
	}
}
