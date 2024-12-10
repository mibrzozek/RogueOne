package structures;

import items.Stash;
import puzzlelike.Problem;
import puzzlelike.Puzzle;
import puzzlelike.PuzzleManager;
import structures.TerrainGen.CavernGen;
import structures.TerrainGen.MapUtility;
import structures.TerrainGen.RegionUtility;
import wolrdbuilding.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class Dungeon implements Serializable
{

	public List<Point> getStairPoints()
	{
		return stairPoints;
	}

	public TileV[][][] getTiles()
	{
		return tiles;
	}
	public List<RoomV> getRedRooms() { return redRooms;}
	public List<RoomV> getGreenRooms() { return greenRooms;}
	public List<RoomV> getGoldRooms()
	{
		return goldRooms;
	}
	public ArrayList<Point> getMainRegionPointS()
	{
		return mainRegionPoints;
	}
	public Puzzle getPuzzle()
	{
		return puzzle;
	}

	public TileV[][][] generateNextTerrain(PuzzleManager puzzMan, TileV[][][] tiles, Point nextTerrainOffset)
	{
		Puzzle nextPuzzle = puzzMan.getCurrent();
		Point continuationPoint = new Point(0, 0, 0);

		if(puzzMan.getPuzzCount() > 1)
		{
			continuationPoint = puzzMan.getPreviousPuzzle().getDoors().get(0).getPoint();
		}

		System.out.println("Generating next puzzle");
		int width = 50;
		int height = 50;
		int depth = 1;

		int offsetX = nextTerrainOffset.x;
		int offsetY = nextTerrainOffset.y;

		System.out.println("xoff : " + offsetX + " yoff : " + offsetY);

		tiles = CavernGen.makeCavern(7, width, height, depth, tiles, offsetX, offsetY, puzzMan);
		tiles = RegionUtility.mergeRegions(tiles, width, height, depth, puzzMan);


		// Connect current region w/ previous puzzle door
		regionMap = RegionUtility.getRegionMap(tiles, width, height, depth, offsetX, offsetY);
		Point r1 = regionMap.get(1).get(new Random().nextInt(regionMap.get(1).size()));
		Point r2 = puzzMan.getPreviousPuzzle().getDoors().get(0).getPoint();

		tiles = RegionUtility.ConnectRegions(tiles, width, height, depth, puzzMan, r2, r1);


		tiles = MapUtility.addPuzzleToTerrain(tiles, puzzMan, width, height, depth, offsetX, offsetY, regionMap);
		nextPuzzle.addItemSpawnPoints(MapUtility.getAllPointsThatAreThisTile(tiles, width, height, depth, Tile.INSIDE_FLOOR, offsetX, offsetY));
		doors.addAll(nextPuzzle.getDoors());
		System.out.println("Doors stored in puzzle" + nextPuzzle.getDoors().size());
		//System.out.println(puzzle.getDoors().get(0).getPoint().toString());
		System.out.println("Doors stored dungeonDoors" + doors.size());

		return tiles;
	}
	public enum Terrain
	{
		WATER(.15),
		MOUNTAIN(.35),
		DESERT(.10),
		PLAINS(.10),
		WOODS(.30);

		private double percent;
		Terrain(double d)
		{
			this.percent = d;
		}
		public double getPercent()
		{
			return this.percent;
		}
	}
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
	private ArrayList<Stash> stashes;
	private ArrayList<Point> doorCandidates;
	private ArrayList<Point> stairPoints;
	private ArrayList<Point> wallPoints;
	private ArrayList<Point> rockPoints;

	private ArrayList<Point> mainRegionPoints;
	private ArrayList<Point> lockedRoomPoints;

	private List<List> regionList;
	private List<List> regionMapOfFloor;


	//private Map<Integer, List<Point>> regionMap;

	private List<RoomV> allRooms;

	private Puzzle puzzle;

	List<RoomV> redRooms;
	List<RoomV> goldRooms;
	List<RoomV> greenRooms;

	private Map<Integer, List<Point>> regionMap;

	private HashMap<String, ArrayList<TilePoint>> structureMap;
	private HashMap<Integer, ArrayList<TilePoint>> partsMap;
	
	private Random r =  new Random();
	
	static final int MAX_TUNNEL_LENGTH = 3;
	static final int MAX_TUNNEL_WIDTH = 3;
	
	static final int MAX_ROOM_LENGTH = 30;
	static final int MIN_ROOM_LENGTH = 5;

	static final int MAX_ROOM_WIDTH = 20;
	static final int MAX_ROOM_HEIGHT = 20;
	static final int LOCKED_ROOM_LOOP = 600;
	
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
		this.stashes = new ArrayList<>();
		this.startingPoints = new ArrayList<>();
		this.rockPoints = new ArrayList<>();
		this.stairPoints = new ArrayList<>();

		this.mainRegionPoints = new ArrayList<>();
		this.lockedRoomPoints = new ArrayList<>();

		this.allRooms = new ArrayList<>();

		this.redRooms = new ArrayList<>();
		this.goldRooms = new ArrayList<>();
		this.greenRooms = new ArrayList<>();

		this.regionMapOfFloor = new ArrayList<>();
		this.regionMap = new HashMap<>();
		this.regionList = new ArrayList<>();
		this.nextRegion = 1;

		RexReader rex = new RexReader();
		this.structureMap = rex.getStructures();
		this.partsMap = rex.getDecorations();

	}
	public TileV[][][] getNewDungeon(World.Map m)
	{
		randomizeFloor();

		if(m.equals(World.Map.DUNGEON))
			randomApproachToDungeons();
		else if(m.equals(World.Map.TURKEY))
			anotherDungeon();
		else if(m.equals(World.Map.PUZZLE))
		{
			//randomApproachToDungeons();     ;]
			makePuzzleInstance();
		}

		return tiles;
	}
	public void makePuzzleInstance()
	{
		System.out.println("Doors before" + doors.size());
		Puzzle p = new Puzzle(Problem.LOCK_AND_KEY, puzzlelike.Terrain.CAVERN);
		System.out.println("Puzzle : " + p.problem().toString());
		System.out.println("Solution : " + p.solution().toString());
		randomizeFloorCorrectly();
		PuzzleManager puzzMan = new PuzzleManager(p);

		int width = 50;
		int height = 50;
		int depth = 1;

		tiles = CavernGen.makeCavern(7, width, height, depth, tiles, 0, 0, puzzMan);
		tiles = RegionUtility.mergeRegions(tiles, width, height, depth, puzzMan);

		// Take region snapshot after merging, should be one
		this.regionMap = RegionUtility.getRegionMap(tiles, width, height, depth, puzzMan.getNextPuzzleTerrainOffset().x, puzzMan.getNextPuzzleTerrainOffset().y);
		if(regionMap.keySet().size() > 1)
		{
			System.err.println("Region size after merge is greater than 1 :" + regionMap.keySet().size());
		}
		System.out.println("Dungeon regions : " + regionMap.keySet().size());

		tiles = MapUtility.addPuzzleToTerrain(tiles, puzzMan, width, height, depth, 0, 0, regionMap);

		puzzle = p;
		puzzle.addItemSpawnPoints(MapUtility.getAllPointsThatAreThisTile(tiles, width, height, depth, Tile.INSIDE_FLOOR, 0, 0));
		doors.addAll(puzzle.getDoors());
		System.out.println("Doors stored in puzzle" + puzzle.getDoors().size());
		//System.out.println(puzzle.getDoors().get(0).getPoint().toString());
		System.out.println("Doors stored dungeonDoors" + doors.size());

		this.spawnPoints.addAll(MapUtility.getAllPointsThatAreThisTile(tiles, width, height, depth, Tile.INSIDE_FLOOR, 0, 0));
		this.startingPoints.addAll(MapUtility.getAllPointsThatAreThisTile(tiles, width, height, depth, Tile.INSIDE_FLOOR, 0, 0));
	}
	public void anotherDungeon()
	{
		RoomPoint first = null;
		Point p;

		double mountain = 0.35;
		double water = 0.15;
		double woods = 0.30;
		double plains = 0.10;
		double desert = 0.10;

		List<Point> northBorder = new ArrayList<>();
		List<Point> southBorder = new ArrayList<>();
		List<Point> westBorder = new ArrayList<>();
		List<Point> eastBorder = new ArrayList<>();

		for(int nx = 0; nx < width; nx++)
		{
			northBorder.add(new Point(nx, 0, 0));
			southBorder.add(new Point(nx, height-1, 0));
		}
		for(int ny = 0; ny < width; ny++)
		{
			westBorder.add(new Point(0, ny, 0));
			eastBorder.add(new Point(width-1, ny, 0));
		}
		List<Point>  border = new ArrayList<Point>(northBorder);
		border.addAll(southBorder);
		border.addAll(eastBorder);
		border.addAll(westBorder);

		for(int x =  0 ; x < width;x++)
		{
			for(int y = 0; y < height; y++)
			{
				tiles[x][y][0] = new TileV(Tile.GRASS_1);
				spawnPoints.add(new Point(x, y, 0));
			}
		}

		int midWidth = width/2;
		int midHeight = height/2;
		//makeRocks(new Point(0, 0, 0), width, height);
		makeRandomBushes(100);
		makeRandomTrees();

		List<Terrain> terrains = Arrays.asList(Terrain.values());
		List<List<Point>> bordersList =  new ArrayList<>();
		bordersList.add(northBorder);
		bordersList.add(southBorder);
		bordersList.add(eastBorder);
		bordersList.add(westBorder);

		Collections.shuffle(terrains);
		Collections.shuffle(bordersList);
		for(Terrain t : terrains)
		{
			if(t.equals(Terrain.DESERT))
			{

			}
			else if(t.equals(Terrain.WOODS))
			{

			}
			else if(t.equals(Terrain.MOUNTAIN))
			{

			}
			else if(t.equals(Terrain.WATER))
			{

			}
			else if(t.equals(Terrain.PLAINS))
			{

			}
		}
		// make everything rock  and grass
		fillAreaWithTile(new Point(0, 0, 0), width, height, Tile.ROCK_0, Tile.GRASS_0, null);
		smoothAreaForTile(new Point(0, 0, 0), width, height, Tile.ROCK_0);

		// make water
		int nx=width, ny=height, nw, nh;
		Point np = new Point(nx, ny, 0);
		do
		{
			nx = r.nextInt(width);
			ny = r.nextInt(height);
			nw = r.nextInt(50) + 75;
			nh = r.nextInt(50) + 75;
			np = new Point(nx, ny, 0);
		}while(!isValidPoint(np, nw, nh));
		fillAreaWithTile(np, nw, nh, Tile.WATER, Tile.GRASS_0, null);
		fillAreaWithTile(new Point(np.x+10, np.y+10, 0), nw-20, nh-20, Tile.WATER, null, null);
		smoothAreaForTile(np, nw, nh, Tile.WATER);

		// gather border points
		for(Point ep : border)
		{
			tiles[ep.x][ep.y][ep.z] = new TileV(Tile.ROCK_0);
		}
		System.out.println(border.size() + " border length");
	}
	/*
			If t2 is null it will fill the area with one tile otherwise it will
			fill it randomly with the two tiles
	 */
	public void fillAreaWithTile(Point p, int areaWidth, int areaHeight, Tile t, Tile t2, Double percent)
	{
		double per = 0.5;

		if(percent != null)
			per = percent;

		for(int x = p.x; x < p.x+areaWidth; x++)
		{
			for(int y = p.y; y < p.y+areaHeight; y++)
			{
				//System.out.println("Filling area " + x + "  " + y);
				//System.out.println(p.toString() + " width  " + areaWidth +" height " + areaHeight);
				if(!isValidPoint(new Point(x, y, 0),0, 0))
					break;

				//System.out.println(p.toString() + " width  " + areaWidth +" height " + areaHeight);
				if(t2==null) // fill whole area with one tile
					tiles[x][y][p.z] = new TileV(t);
				else // randomize area between two tiles
				{
					if(Math.random() > per)
						tiles[x][y][p.z] = new TileV(t);
					else
						tiles[x][y][p.z] = new TileV(t2);
				}
			}
		}
	}
	public void smoothAreaForTile(Point p, int areaWidth, int areaHeight, Tile t)
	{
		List<Point> designatedSpot = p.gridXbyX(p, areaWidth, areaHeight);

		for(Point dpr : designatedSpot) // look at 3x3 regions and smooth
		{
			int designated = 0, floors = 0;

			for(Point gp : dpr.gridXbyX(dpr, 3, 3)) // count cells majority tile
			{
				if(!isValidPoint(gp, 1, 1))
					continue;

				if(tiles[gp.x][gp.y][gp.z].getTile() != t)
					floors++;
				else
					designated++;
			}
			for(Point gp : dpr.gridXbyX(dpr, 3, 3)) // fill cell based on majority
			{

				if(!isValidPoint(gp, 1, 1))
					continue;

				if(designated >= 5)
				{
					tiles[gp.x][gp.y][gp.z] = new TileV(t);
					//rockPoints.add(new Point(gp.x, gp.y, gp.z));
				}
				else
				{
					tiles[gp.x][gp.y][gp.z] = new TileV(Tile.GRASS_1);
					//spawnPoints.add(new Point(gp.x, gp.y, gp.z));
				}
			}
		}
	}
	public void makeRocks(Point p, int areaWidth, int areaHeight)
	{
		int numFormations = 8;
		int formationWidthHeightMAX = 50;
		int formationWidthHeightMIN = formationWidthHeightMAX/2;
		//Point p = new Point(0,0,0);


		for(int x = p.x; x < p.x + areaWidth;x++) // fill randomly with grass and rock
		{
			for(int y = p.y; y < p.y + areaHeight; y++)
			{
				if(!isValidPoint(p, 1, 1))
					continue;

				if(Math.random() < 0.5) {
					tiles[x][y][0] = new TileV(Tile.ROCK_0);
				}
				else
				{
					tiles[x][y][0] = new TileV(Tile.GRASS_1);
					spawnPoints.add(new Point(x, y, 0));
				}
			}
		}
		List<Point> designatedSpot = p.gridXbyX(p, areaWidth, areaHeight); // list of all point in newly filled region

		for(Point dpr : designatedSpot) // look at 3x3 regions and smooth
		{
			int rocks = 0, floors = 0;

			for(Point gp : dpr.gridXbyX(dpr, 3, 3)) // count cells majority tile
			{
				if(!isValidPoint(gp, 1, 1))
					continue;

				if(tiles[gp.x][gp.y][gp.z].isGround())
					floors++;
				else
					rocks++;
			}
			for(Point gp : dpr.gridXbyX(dpr, 3, 3)) // fill cell based on majority
			{

				if(!isValidPoint(gp, 1, 1))
					continue;

				//System.out.println(" number of rocks  "  + rocks);
				if(rocks >= 5)
				{
					tiles[gp.x][gp.y][gp.z] = new TileV(Tile.ROCK_0);
					rockPoints.add(new Point(gp.x, gp.y, gp.z));
				}
				else
				{
					tiles[gp.x][gp.y][gp.z] = new TileV(Tile.GRASS_1);
				}
			}
		}
	}
	public void makeRandomTrees()
	{
		int amount = 30;
		Point p = new Point(0, 0, 0);

		int w = 10;

		for(int i = 0; i < amount; i++)
		{
			do
			{
				p = new Point(r.nextInt(width - w), r.nextInt(height - w), 0);
			}while(!tiles[p.x][p.y][p.z].getTile().isGrassy());

			List<Point> trees = p.gridXbyX(p, w, w);
			for(Point bp : trees)
			{
				if(!tiles[bp.x][bp.y][bp.z].getTile().isGrassy())
					continue;

				if(Math.random() > 0.9)
				{
					if(Math.random() > 0.5)
						tiles[bp.x][bp.y][bp.z] = new TileV(Tile.TREE_0);
					else
						tiles[bp.x][bp.y][bp.z] = new TileV(Tile.TREE_0);
				}
			}
		}
	}
	public void makeRandomBushes(int amount)
	{
		Point p = new Point(0, 0, 0);
		for(int i = 0; i < amount; i++)
		{
			do
			{
				p = new Point(r.nextInt(width -5 ), r.nextInt(height -5), 0);
			}while(!tiles[p.x][p.y][p.z].getTile().isGrassy());

			List<Point> bushes = p.gridXbyX(p, 5, 5);
			for(Point bp : bushes)
			{
				if(!tiles[bp.x][bp.y][bp.z].getTile().isGround())
					continue;

				if(Math.random() > 0.7)
				{
					if(Math.random() > 0.5)
						tiles[bp.x][bp.y][bp.z] = new TileV(Tile.BUSH_0);
					else
						tiles[bp.x][bp.y][bp.z] = new TileV(Tile.BUSH_1);

					spawnPoints.add(bp);
					//startingPoints.add(bp);
				}
			}
		}
	}
	public void makePlanetSurface()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				Point p0 = new Point(x, y, 0);
				tiles[x][y][0] = new TileV(Tile.INSIDE_FLOOR);

				spawnPoints.add(new Point(x, y, p0.z));
				//startingPoints.add(new Point(x, y, p0.z));
			}
		}
		stampStructureOntoFloor(new Point(20, 20, 0), structureMap.get("PT_9.csv"));
	}
	public void stampStructureOntoFloor(Point p, ArrayList<TilePoint> structure)
	{
		ArrayList<TilePoint> copy = structure;

		while(!structure.isEmpty())
		{
			TilePoint t = structure.remove(0);
			if(t.ascii() == 250)
				continue;

			tiles[p.x + t.x()][p.y + t.y()][p.z].setGlyph((char)t.ascii());

		}
		structure = copy;
	}
	public void randomStamping()
	{
		Point p =  null;
		for(int i = 0; i < 40; i++) // random all ground room stamping
		{
			for(int j = 0; j < depth; j++)
			{
				p = getFirstRoomPoint(j);
				RoomPoint rp = new RoomPoint(p, p.w, p.h, null);
				if(rp!= null)
				{
					buildRoom(rp, TileSet.ALL_GROUND_ROOM);
					mainRegionPoints.addAll(p.gridXbyX(rp.point(), rp.w, rp.h));
				}
			}
		}
	}
	public void randomApproachToDungeons()
	{
		randomizeFloor();

		randomStamping(); // main region created?
		smooth();
		createRegions();

		connectRegions();

		makeStartingRoom();
		//addStructures();
		fillDungeonWithWall(TileSet.SIMPLE);

		makeLockedRooms();
		calculateRoomClearance();

		makeStairsDown();
		makeStairsDown();
		addExitStairs();
		//makeLaserTraps();
		addRoomDecor();
		//spawnMethane();
	}
	private void calculateRoomClearance()
	{
		System.out.println("Room count " + allRooms.size());
		for(RoomV rooms : allRooms)
		{
			rooms.calculateClearance(this);
			System.out.println("\tFirst wall point " + rooms.getWallPoints().get(0));
			System.out.println("\tRoom point : " + rooms.getRoomPoint());
		}
		System.out.println("Red room " + redRooms.size());
		System.out.println("Green room " + greenRooms.size());
		System.out.println("Gold room " + goldRooms.size());
	}
	private void connectRegions()
	{
		int tunnelWidth = 3;

		for(List<Point> list : regionList)
		{
			if(list.size() < 150)
			{
				//removeRegion(list);
				continue;
			}
			Stream<Point> s = list.stream();

			//s.mapToInt(point -> point.x).forEach(System.out::println);
			int maxX = s.mapToInt(point -> point.x).max().getAsInt();
			s = list.stream();
			int maxY = s.mapToInt(point -> point.y).max().getAsInt();
			s = list.stream();
			int minX = s.mapToInt(point -> point.x).min().getAsInt();
			s = list.stream();
			int minY = s.mapToInt(point -> point.y).min().getAsInt();

			List<Point> eastBorderLookingEast = new ArrayList<>();
			List<Point> westBorderCandidates = new ArrayList<>();

			List<Point> southBorderLookingSouth = new ArrayList<>();
			List<Point> southBorderCandidates = new ArrayList<>();

			List<Point> westBorderLookingWest = new ArrayList<>();
			List<Point> eastBorderCandidates = new ArrayList<>();

			List<Point> northBorderLookingNorth = new ArrayList<>();
			List<Point> northBorderCandidates = new ArrayList<>();

			Point np = null;
			for(Point p : list) // for each point in region
			{
				if(p.x + 1 > width || p.x - 1 < 0 || p.y + 1 > height || p.y - 1 < 0)
					continue;

				if(p.x == maxX && tiles[p.x][p.y+1][p.z].getTile().isFloor()
						&& tiles[p.x][p.y+2][p.z].getTile().isFloor()) // if furthest facing EASTERN wall that isn't on the edge
				{
					np = lookForInDirectionFrom(Tile.INSIDE_FLOOR, Direction.EAST, p, true, 1); // then look for candidate to the east
					//tiles[p.x][p.y][p.z] = new TileV(Tile.GRASS_1);

					if(np !=null) // and if you found a candidate
					{
						//System.out.println("Tunnel results \n" +  p.toString() + "\n" + np.toString());
						//tiles[p.x][p.y][p.z] = new TileV(Tile.GRASS_1);
						//tiles[np.x][np.y][np.z] = new TileV(Tile.GRASS_1); // mark candidates
						eastBorderLookingEast.add(p);
						westBorderCandidates.add(np);
						//System.out.println("Border and new eastBorderLookingEast size\n\t\tBorder :" + eastBorderLookingEast.size()
						//	+ "\n\t\tNew Border : " + westBorderCandidates.size());

					}
				}
				if(p.y == maxY && tiles[p.x + 1][p.y][p.z].getTile().isFloor()
						&& tiles[p.x + 2][p.y][p.z].getTile().isFloor()) // // if we're looking at ** MAXIMUM Y SOUTH **  value
				{
					//System.out.println("We're looking at maxY");
					np = lookForInDirectionFrom(Tile.INSIDE_FLOOR, Direction.SOUTH, p, true, 1);

					if(np != null) // and if you found a candidate
					{
						southBorderLookingSouth.add(p);
						southBorderCandidates.add(np);
						//System.out.println("Tunnel results souths \n" +  p.toString() + "\n" + np.toString());
						//tiles[p.x][p.y][p.z] = new TileV(Tile.GRASS_1);
						//tiles[np.x][np.y][np.z] = new TileV(Tile.GRASS_1); // mark candidates
					}
				}
				if(p.x == minX && tiles[p.x][p.y+1][p.z].getTile().isFloor()	// if we're looking at ** MINIMUM X **  value
						&& tiles[p.x][p.y +2][p.z].getTile().isFloor())
				{
					np = lookForInDirectionFrom(Tile.INSIDE_FLOOR, Direction.WEST, p, true, 1);

					if(np != null)
					{
						westBorderLookingWest.add(p);
						eastBorderCandidates.add(np);
						//tiles[p.x][p.y][p.z] = new TileV(Tile.CLOSED_DOOR);
						//tiles[np.x][np.y][np.z] = new TileV(Tile.CLOSED_DOOR); // mark candidates
					}
				}
				if(p.y == minY && tiles[p.x + 1][p.y][p.z].getTile().isFloor() // if we're looking at ** MINIMUM Y **  value
						&& tiles[p.x+2][p.y][p.z].getTile().isFloor())
				{
					np = lookForInDirectionFrom(Tile.INSIDE_FLOOR, Direction.NORTH, p, true, 1);

					if(np != null)
					{
						northBorderLookingNorth.add(p);
						northBorderCandidates.add(np);
						//tiles[p.x][p.y][p.z] = new TileV(Tile.CLOSED_DOOR);
						//tiles[np.x][np.y][np.z] = new TileV(Tile.CLOSED_DOOR); // mark candidates
					}
				}
			}
			if(!eastBorderLookingEast.isEmpty() && !westBorderCandidates.isEmpty())
			{
				RoomPoint nrp = new RoomPoint(eastBorderLookingEast.get(r.nextInt(eastBorderLookingEast.size())),
						westBorderCandidates.get(0).x - eastBorderLookingEast.get(0).x + 2, tunnelWidth);
				buildRoom(nrp, TileSet.INSIDE_TILE);
			}
			if(!eastBorderCandidates.isEmpty() && !westBorderLookingWest.isEmpty())
			{
				RoomPoint nrp = new RoomPoint(westBorderLookingWest.get(r.nextInt(westBorderLookingWest.size())),
						westBorderLookingWest.get(0).x - eastBorderCandidates.get(0).x +2, tunnelWidth);
				buildRoom(nrp, TileSet.INSIDE_TILE);
			}
			if(!southBorderLookingSouth.isEmpty() && !southBorderCandidates.isEmpty())
			{
				RoomPoint nrp = new RoomPoint(southBorderLookingSouth.get(r.nextInt(southBorderLookingSouth.size())),
						tunnelWidth, southBorderCandidates.get(0).y - southBorderLookingSouth.get(0).y + 2);
				buildRoom(nrp, TileSet.INSIDE_TILE);
			}
			if(!northBorderCandidates.isEmpty() && !northBorderLookingNorth.isEmpty())
			{
				RoomPoint nrp = new RoomPoint(northBorderLookingNorth.get(r.nextInt(northBorderLookingNorth.size())),
						tunnelWidth, northBorderLookingNorth.get(0).y - northBorderCandidates.get(0).y + 2);
				buildRoom(nrp, TileSet.INSIDE_TILE);
			}
		}
	}
	private Point lookForInDirectionFrom(Tile tile, Direction d, Point p, Boolean branch, int depth)
	{
		//System.out.println("Looking in direction ");
		int search = 70;
		Point rp = null;

		if(d.equals(Direction.EAST))
		{
			for(int x = 1; x < search; x++)
			{
				if(p.x + x >= width)
					continue;

				if(branch) // What is this? Seems to be TRUE for previous times it was called
				{
					if(tiles[p.x + x][p.y][p.z].getTile().equals(tile)
							&& tiles[p.x + x][p.y +1][p.z].getTile().equals(tile)
							&& tiles[p.x + x][p.y +2][p.z].getTile().equals(tile))
					{
						rp = new Point(p.x + x, p.y, p.z);
						x = search;
					}
				}
				else if(tiles[p.x + x][p.y][p.z].getTile().equals(tile))
				{

					rp = new Point(p.x + x, p.y, p.z);
					x = search;
				}
			}
		}
		if(d.equals(Direction.WEST))
		{
			for(int x = 1; x < search; x++)
			{
				if(p.x - x < 0)
					continue;
				if(branch)
				{
					if(tiles[p.x - x][p.y][p.z].getTile().equals(tile)
							&& tiles[p.x - x][p.y +1][p.z].getTile().equals(tile)
							&& tiles[p.x - x][p.y +2][p.z].getTile().equals(tile))
					{
						rp = new Point(p.x - x, p.y, p.z);
						x = search;
					}
				}
				else if(tiles[p.x - x][p.y][p.z].getTile().equals(tile))
				{
					rp = new Point(p.x - x, p.y, p.z);
					x = search;
				}
			}
		}
		if(d.equals(Direction.SOUTH))
		{
			for(int y = 1; y < search; y++)
			{
				if(p.y + y >= height)
					continue;

				if(branch)
				{
					if(tiles[p.x][p.y + y][p.z].getTile().equals(tile)
							&& tiles[p.x + 1][p.y + y][p.z].getTile().equals(tile)
							&& tiles[p.x + 2][p.y + y][p.z].getTile().equals(tile))
					{
						rp = new Point(p.x, p.y + y, p.z);
						y = search;
					}
				}
				else if(tiles[p.x][p.y + y][p.z].getTile().equals(tile))
				{
					rp = new Point(p.x, p.y + y, p.z);
					y = search;
				}
			}
		}
		if(d.equals(Direction.NORTH))
		{
			for(int y = 1; y < search; y++)
			{
				if(p.y - y < 0)
					continue;
				if(branch)
				{
					if(tiles[p.x][p.y - y][p.z].getTile().equals(tile)
							&& tiles[p.x + 1][p.y - y][p.z].getTile().equals(tile)
							&& tiles[p.x + 2][p.y - y][p.z].getTile().equals(tile))
					{
						rp = new Point(p.x, p.y - y, p.z);
						y = search;
					}
				}
				else if(tiles[p.x][p.y - y][p.z].getTile().equals(tile))
				{
					rp = new Point(p.x, p.y - y, p.z);
					y = search;
				}
			}
		}

		return rp;
	}
	private void removeRegion(List<Point> list)
	{
		for(Point p : list)
		{
			tiles[p.x][p.y][p.z] = new TileV(Tile.WALL);
		}
	}
	public void addStash(Point p, Stash s, Tile tile)
	{
		stashes.add(s);
		tiles[p.x][p.y][p.z] = new TileV(tile);
	}
	public void spawnStashes()
	{
		for(int i = 0; i < 30; i++)
		{
			Point  p = spawnPoints.get(r.nextInt(allWalls.size()));

			do
			{
				p = spawnPoints.get(r.nextInt(spawnPoints.size()));
			}while(tiles[p.x][p.y][p.z].isGround());

			tiles[p.x][p.y][p.z] = new TileV(Tile.STASH);
			stashes.add(new Stash(p));
		}
	}
	private void moarDungeon()
	{
		System.out.println("moar dungeon");

		List<RoomPoint> hallList = new ArrayList<>();

		Point p = new Point(0,0,0);
		RoomPoint rp = new RoomPoint(p, 0, 0);
		int w =0, h =0, x = 0, y = 0;

		do
		{
			if(Math.random() > 0.5) //vertical
			{
				x = r.nextInt(width);
				y = r.nextInt(10) + 5;
				w = 5;
				h = height - y;

				p = new Point(x, y, 0);
				rp = new RoomPoint(p, w, h);
			}
			else // horizontal
			{
				x = r.nextInt(10) + 5;
				y = r.nextInt(height);
				w = width - x;
				h = 5;
				p = new Point(x, y, 0);
				rp = new RoomPoint(p, w, h);
			}

			hallList.add(rp);
		}while(hallList.size() < 6);

		for(RoomPoint r : hallList)
		{
			buildRoom(r, TileSet.ALL_GROUND_ROOM);
		}

		fillDungeonWithWall(TileSet.SIMPLE);
		makeLockedRooms();
	}
	private void addStructures()
	{
		makeStructure("pt_s1_01");
		makeStructure("pt_s1_02");
		makeStructure("pt_s1_03");
		makeStructure("pt_s1_04");
	}
	private void makeStructure(String fileName)
	{
		int w = 25, h = 25;
		Point p;
		do {
			p = getSpawnPointFromLevel(0);
		}while(!isValidPoint(p, w, h));

		RoomPoint rp = new RoomPoint(p, w, h);
		buildRoom(rp, TileSet.INSIDE_TILE);

		//buildPlasmaBlock(rp, 7);
		buildStructure(new ArrayList<TilePoint>(structureMap.get(fileName +
				".csv")), new Point(rp.x + 4, rp.y + 4, 0));

		startingPoints = getOpenPointFromRegion(rp.point(), 15, 15);
	}

	public void makeLockedRooms()
	{
		Point p = new Point(0, 0, 0);
		RoomPoint roomPoint = new RoomPoint(p, 0, 0);

		List<RoomPoint> toBuild=  new ArrayList<>();

		boolean found = false;
		int bw = 15;
		int count = 0;
		int newRoom = 0;

		//System.out.println(p.toString() + " before search");
		for(int i = 0; i < LOCKED_ROOM_LOOP; i++)
		{
			found  = false;
			do // look for a point with a room to the right or below it
			{
				do
				{
					p = allWalls.get(r.nextInt(allWalls.size())); // get a wall point
				} while (p.z < 0 || p.x < 1 || p.y < 1 || p.x > width - 2 || p.y > height - 2); // from zero floor for testing, and a little buffer to make sure everything stays positive

				Direction d = null;
				//System.out.println(tiles[p.x][p.y][p.z].getTile().toString() + " is the tile a wall?    ***********************************");
				//System.out.println(allWalls.size() + " all walls size" + wallPoints.size() + " wallPoints size");
				//System.out.println(p.toString() + "       in search");

				if (tiles[p.x + 1][p.y][p.z].getTile().isWall()) // set direction of where the wall is
				{
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

				if (d == Direction.EAST || d == Direction.SOUTH)
				{
					boolean isGood = false;

					for (int y = 23; y > 8; y--) // height
					{
						for (int x = 23; x > 8; x--) // width
						{
							//System.out.println(x + " " + y + " " + "loop points " + count++ + " count");
							if (p.x + x >= width || p.y + y >= height) // what is 200????
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
									//System.out.println("			This is not a wall!");
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
				//System.out.println("We might be stuck in finding point " +  count);
			} while (!found && count++ < LOCKED_ROOM_LOOP);
			//System.out.println(i + " this is I");

			if(found)
			{
				//System.out.println("Something has been found!\n\t\t" +
				//				 roomPoint.toString() + "\n");
				buildLockedRoom(roomPoint, TileSet.DOUBLE);
			}
		}
		System.out.println(newRoom + " new rooms built!");
	}
	public void buildLockedRoom(RoomPoint rp, TileSet t)
	{
		//System.out.println("**************************** We're building a locked room *********************************");
		RoomV room = buildRoom(rp, t);

		if(room == null)
		{
			return;
		}

		boolean south = false;
		boolean north = false;
		boolean east = false;
		boolean west = false;

		Direction d  = null;
		Door door = null;

		Point np = new Point(0, 0, 0);
		Point sp = new Point(0, 0, 0);
		Point ep = new Point(0, 0, 0);
		Point wp = new Point(0, 0, 0);

		Collections.shuffle(doorCandidates);

		for(Point p : doorCandidates) // doorCandidates is created in buildRoom()
		{
			//System.out.println("looking for candiodates" + p.toString()
			// + tiles[p.x][p.y+1][p.z].getTile() + "  " + tiles[p.x][p.y][p.z].getTile());

			if(tiles[p.x][p.y][p.z].getTile().equals(Tile.dblTBW))
			{
				if((tiles[p.x][p.y+1][p.z].getTile().equals(Tile.dblTBW) || tiles[p.x][p.y+1][p.z].getTile().equals(Tile.simpleTBW))
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
		//System.out.println(np.toString() + sp.toString() + ep.toString() + wp.toString());
		int w = 4, h = 4;

		if(!np.equals(new Point(0, 0, 0)) && north)
		{
			//System.out.println("building north **************** !!!!!!!!!!!!!!!!!!!!");
			tiles[np.x][np.y][np.z].setTile(Tile.CLOSED_DOOR);
			tiles[np.x][np.y-1][np.z].setTile(Tile.CLOSED_DOOR);
			door = new Door(np, new Point(np.x, np.y-1,np.z), Door.Clearance.GOLD);
			doors.add(door);
			room.addDoor(door);
			door.setRoom(room);
		}
		if(!sp.equals(new Point(0, 0, 0)) && south)
		{
			//System.out.println("building south **************** !!!!!!!!!!!!!!!!!!!!");
			tiles[sp.x][sp.y][sp.z].setTile(Tile.CLOSED_DOOR);
			tiles[sp.x][sp.y+1][sp.z].setTile(Tile.CLOSED_DOOR);
			door = new Door(sp, new Point(sp.x, sp.y+1,sp.z), Door.Clearance.GOLD);
			doors.add(door);
			room.addDoor(door);
			door.setRoom(room);
		}
		if(!ep.equals(new Point(0, 0, 0)) && east)
		{
			//System.out.println("building east **************** !!!!!!!!!!!!!!!!!!!!");
			tiles[ep.x][ep.y][ep.z].setTile(Tile.CLOSED_DOOR);
			tiles[ep.x+1][ep.y][ep.z].setTile(Tile.CLOSED_DOOR);
			door = new Door(ep, new Point(ep.x+1, ep.y,ep.z), Door.Clearance.GOLD);
			doors.add(door);
			room.addDoor(door);
			door.setRoom(room);
		}
		if(!wp.equals(new Point(0, 0, 0)) && west)
		{
			//System.out.println("building west **************** !!!!!!!!!!!!!!!!!!!!");
			tiles[wp.x][wp.y][wp.z].setTile(Tile.CLOSED_DOOR);
			tiles[wp.x-1][wp.y][wp.z].setTile(Tile.CLOSED_DOOR);
			door = new Door(wp, new Point(wp.x-1, wp.y,wp.z), Door.Clearance.GOLD);
			doors.add(door);
			room.addDoor(door);
			door.setRoom(room);
		}
		north = false;
		south = false;
		east = false;
		west = false;
		// here we are done building a locked, therefore the number of doors have been set
		// and the clearance type is based on the number of door, so we calculate
		//room.setWallPoints(doorCandidates);
		allRooms.add(room);
		for(Point p : room.getWallPoints())
		{
			//tiles[p.x][p.y][p.z] = new TileV(Tile.GRASS_1);
		}
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
				if(ps.x < width && ps.y < height && ps.x > 0 && ps.y > 0)
				{
					if (tiles[ps.x][ps.y][ps.z].getTile() == Tile.INSIDE_FLOOR)
					{
						tiles[ps.x][ps.y][ps.z] = new TileV(Tile.METHANE);
					}
				}
			}
		}
	}


	// Smooths by regenerating main map from mini map.
	public void smooth()
	{
		int cellSize = 3;
		TileV[][][] miniature = MiniMap.getNewMiniMap(tiles, width, height, depth, cellSize);

		//System.out.println(miniature.length + " " + miniature[0].length + " " + miniature[0][0].length);
		//TileV[][][] newTiles = new TileV[width][height][depth];
		//newTiles = tiles;

		int miniX = 0, miniY = 0;

		for(int z2 = 0; z2 < depth; z2++)  // fill up newTiles with all walls
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

					for(Point  p : cell) // check every point in call and count floors
					{
						if(p.x < width && p.y < height && p.z < depth)
						{
							if (tiles[p.x][p.y][p.z].getTile().isFloor())
							{
								floors++;
							}
						}
					}
					if(floors > 3) // if floor count  higher then 3 stamp cell with all floor
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
		//buildStructure(new ArrayList<TilePoint>(structureMap.get("PT_9" +
		//		".csv")), new Point(rp.x + 4, rp.y + 4, 0));

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
			int count = 0;
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
				if(count > 100)
					System.out.println("Sticks?? in room decor");
				count++;
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
		} }
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
		for(int i = 0; i < depth; i++)
		{
			int count = 0;
			boolean found = false;
			do
			{
				Point p;
				do {
					p = mainRegionPoints.get(r.nextInt(mainRegionPoints.size()));
				}while (p.z != i);

				if(tiles[p.x][p.y][p.z].isGround()
						&& p.z + 1 < depth // not bottom floor
						&& mainRegionPoints.contains(new Point(p.x, p.y, p.z +1))
						&& tiles[p.x][p.y][p.z+1].isGround()) // point below is in main region
				{
					tiles[p.x][p.y][p.z].setTile(Tile.STAIRS_DOWN);
					tiles[p.x][p.y][p.z + 1].setTile(Tile.STAIRS_UP);

					stairPoints.add(p);
					stairPoints.add(new Point(p.x, p.y,p.z + 1));

					found = true;
					System.out.println(p.toString() +  " this is where the stairs are!");
				}
				count++;

			} while (found == false && count < 100);
		}
		/*
		for(int i = 0; i < depth; i++)
		{
			//System.out.println("This is the depth " +  depth);

			int count = 0;
			boolean found = false;

			do
			{
				Point p = new Point(r.nextInt(width), r.nextInt(height), i);

				if(tiles[p.x][p.y][p.z].isGround()
						&& p.z + 1 < depth
						&& tiles[p.x][p.y][p.z + 1].isGround())
				{
					tiles[p.x][p.y][p.z].setTile(Tile.STAIRS_DOWN);
					tiles[p.x][p.y][p.z + 1].setTile(Tile.STAIRS_UP);

					stairPoints.add(p);
					stairPoints.add(new Point(p.x, p.y,p.z + 1));

					found = true;
					System.out.println(p.toString() +  " this is where the door is!");
				}
				count++;
			} while (found == false && count < 100);
		}

		 */
	}
	private void createRegions()
	{
		//System.out.println("Creating Regions");
        regions = new int[width][height][depth];
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
        // System.out.println("regions : " +  nextRegion + " Largest Region :" + largestRegion);
		//System.out.println(regionList.size() + " new regions!");
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

		//System.out.println("Regions list info: \n\t\t Size :" + regionList.size());
		//System.out.println(regionList.get(0).toString());
		List<Point> testRegion = regionList.get(0);
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
	private void addExitStairs() 
	{
        int x = -1;
        int y = -1;
    
        do 
        {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (!tiles[x][y][depth-1].getTile().isGround());
    
        tiles[x][y][depth-1].setTile(Tile.STAIRS_EXIT);

        System.out.println("Added exit " + x + " " +  y + " " + (depth -1) + " ");

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
								//wallPoints.add(new Point(x, y, z));
							}
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
			int w = r.nextInt(MAX_ROOM_WIDTH)+5;
			int h = r.nextInt(MAX_ROOM_HEIGHT)+5;
			
			int x = r.nextInt(width-w); 
			int y = r.nextInt(height-h); 
			
			p = new Point(x, y, depth);
			p.addWidth(w);
			p.addHeight(h);
		} while (!isValidPoint(p, p.w, p.h));
		
		return p;
	}
	public RoomV buildRoom(RoomPoint rp, TileSet t)
	{
		doorCandidates.clear();
		RoomV room =  new RoomV(rp, t);

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
						tiles[x][y][rp.z].setTile(t.floor);
						spawnPoints.add(new Point(x, y, rp.z));
						//startingPoints.add(new Point(x, y, rp.z));
						}
					}
					occupiedPoints.add(new Point(x, y, rp.z));
		        }
		    }	
			tiles[rp.x][rp.y][rp.z].setTile(t.tlc);
			tiles[rp.x+rp.w-1][rp.y][rp.z].setTile(t.trc);
			tiles[rp.x][rp.y+rp.h-1][rp.z].setTile(t.blc);
			tiles[rp.x+rp.w-1][rp.y+rp.h-1][rp.z].setTile(t.brc);

			// we collect door candidates to draw doors and discard them everytime a new room is built
			// but we can track them as all walls for building locked rooms
			// door candidates is just a fancy term for walls
			allWalls.addAll(doorCandidates);
			System.out.println("door candidates list INSIDE BUILDROOM " + doorCandidates.toString() + " rp point : "  + rp.point().toString());
			//room.addWallPoints(doorCandidates);

			return room;
		}
		else
		{
			System.out.println("The room was NOT built");
			return null;
		}
		//we collect door candidates to draw doors and discard them everytime a new room is built
		//but we can track them as all walls for building locked rooms
	}
	public void randomizeFloor()
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				for (int z = 0; z < depth; z++)

				{
					tiles[x][y][z] = new TileV( (Math.random() < 0.5 ? Tile.WALL : Tile.WALL) );
				}
			}
		}
	}
	public void randomizeFloorCorrectly()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				for (int z = 0; z < depth; z++)

				{
					tiles[x][y][z] = new TileV( (Math.random() < 0.5 ? Tile.WALL : Tile.INSIDE_FLOOR) );
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
	public ArrayList<Stash> getStashes()
	{
		return stashes;
	}
}
