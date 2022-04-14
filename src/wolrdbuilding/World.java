package wolrdbuilding;

import entities.Effect;
import entities.Entity;
import entities.EntityFactory;
import items.*;
import structures.Air;
import structures.Dungeon;
import structures.NameGenerator;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable
{
	public int getCircleRadius()
	{
		return radius;
	}
	public List<Point> getStairPoints()
	{
		return stairPoints;
	}

	public NameGenerator getNameGenerator() {
		return nameGenerator;
	}

	public void dealEnemiesLoot()
	{
		LootTable loot = new LootTable();
		int floorOneLoot = loot.floorOneLootList().size();

		for(int i = 0; i < floorOneLoot; i++) // for every piece of loot to distribute
		{
			Entity e =  null;
			do // find an entity on the first floor
			{
				e = entities.get(random.nextInt(entities.size()));
			} while(e.z !=  0);

			e.inventory().add(loot.removeRandomItemFromFloorOneTable()); // distribute loot and remove from list
		}
	}
	public enum Map
	{
		TURKEY(),
		DUNGEON();
	}

	private static final int MINI_CELL = 3;
	private NameGenerator nameGenerator;

	private Point playerCell;

	Random random;
	private Entity player;
	private Dungeon dungeon;

	private MiniMap mini;

	private List<Entity> entities;
	private ArrayList<Projectile> projectiles;
	
	private TileV[][][] tiles;
	private TileV[][][] miniMap;
	private Item[][][] itemMap;
	private Projectile[][][] projectileMap;
	
	private ArrayList<Point> insideSpawns;
	private ArrayList<Point> startingPoints;
	private ArrayList<Point> firePoints;
	private ArrayList<Door> doorPoints;
	private ArrayList<Stash> stashPoints;
	private ArrayList<Point> stairPoints;


	private Point circleCenter;
	private Point circleStartingPoint;

	private int radius;

	private Air air;

	private ArrayList<Fire> fireCenters;

	private int turns;
	public int turns() 	{ return turns; }

    private int width;
    public int width() 	{ return width; }
    
    private int height;
    public int height() { return height; }
	
    private int depth;
	public int depth() 	{ return depth; }

	public World(TileV[][][] tiles, ArrayList<Point> spawns, ArrayList<Point> startingPoints, Entity player, Dungeon d)
	{
		this.nameGenerator = new NameGenerator();
		this.nameGenerator.setFileToUse("C:\\006 SOURCE\\01 JAVA PROJECTS\\004 ROGUE ONE\\RogueOne\\resources\\lsv\\first_names.txt");

		this.tiles = tiles;
		this.player = player;
		this.dungeon = d;
		
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;

		this.turns = 0;
		
		this.entities = new ArrayList<Entity>();
		this.projectiles = new ArrayList<Projectile>();
		this.firePoints = new ArrayList<Point>();
		this.doorPoints  = d.getDoorPoints();
		this.stashPoints = d.getStashes();
		this.stairPoints = (ArrayList<Point>) d.getStairPoints();

		this.fireCenters = new ArrayList<>();
		
		this.itemMap = new Item[width][height][depth];
		this.projectileMap = new Projectile[width][height][depth];

		this.miniMap = new TileV[width/MINI_CELL][height/MINI_CELL][depth];
		this.circleStartingPoint = Point.getPointForCircle(25,width/MINI_CELL, height/MINI_CELL);
		
		this.insideSpawns = spawns;
		this.startingPoints = startingPoints;

		this.air = new Air(this);
		random  = new Random();
	}
	public Air getAir()
	{
		return air;
	}

	public TileV[][][] getTileMap()
	{
		return tiles;
	}
	public MiniMap getMiniMap()
	{
		return  new MiniMap(this);
	}


	public Entity getPlayer()
	{
		return player;
	}
	public void changeTile(Point p, Tile t, boolean newFire)
	{
		if(p.x >= width || p.x < 0 || p.y >= height || p.y < 0)
			return;

		tiles[p.x][p.y][p.z] = new TileV(t);

		if(t.isFire() ) //
		{
				if(newFire) // gets newest fires points and sees if new poiint is already ther
					fireCenters.add(new Fire(p, this));
		}
	}
    public void animate()
    {
    	for(int x = 0; x < width; x++)
    	{
    		for(int y = 0; y < height; y++)
    		{
    			//tiles[x][y][player.z].animate();
    		}
    	}
    }
    // Returns the tiles at give point; Used for checking type of tile
	public TileV tile(int x, int y, int z)
	{
		if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth)
			return new TileV(Tile.BOUNDS.glyph(), Tile.BOUNDS.color());
		else
		{
			return tiles[x][y][z];
		}
	}
    // Checks if there's an entity in given coordinate
	public Projectile projectile(int x, int y, int z)
	{
		Projectile p = null;
		for (Projectile pr : projectiles)
		{
			if (pr.point().x == x && pr.point().y == y && pr.point().z == z)
				p = pr;
		}
		return p;
	}
	public Entity entity(int x, int y, int z)
	{
		for (Entity e : entities)
		{
			if (e.x == x && e.y == y && e.z == z)
				return e;
		}
		return null;
	}
	public Item item(int x, int y, int z)
	{
		if(x >= 0 && y >= 0 && z >= 0
				&& x < width && y < height && z < depth)	
			return itemMap[x][y][z];
		else 
			return null;
	}
    // Digs through undigable terrain
	public void dig(int x, int y, int z) 
	{
		if (tile(x, y, z).isDiggable())
			tiles[x][y][z].setGlyph(Tile.FLOOR);
	}
	public boolean tunnelExplosion(int direction, int distance) // returns true is tiles were blasted
	{
		boolean blasted  = false;

		int mx = 0, my= 0;
		
		if(direction == 0)
		{
			my = -1;
		}
		else if(direction == 1)
		{
			my =-1;
			mx = 1;
		}
		else if(direction == 2)
		{
			mx = 1;
		}
		else if(direction == 3)
		{
			mx = 1;
			my = 1;
		}
		else if(direction == 4)
		{
			my =1;
		}
		else if(direction == 5)
		{
			mx =-1;
			my = 1;
		}
		else if(direction == 6)
		{
			mx =-1;
		}
		else if(direction == 7)
		{
			mx =-1;
			my =-1;
		}
		
		for(int i = 0; i < distance; i++)
		{
			//System.out.println(tiles[player.x +mx][player.y + my][player.z].getTile().toString());
			if(player.x + mx >= width || player.x + mx < 0 || player.y + my >= height || player.y + my < 0)
				continue;

			if(!tiles[player.x +mx][player.y + my][player.z].isGround() && !tiles[player.x +mx][player.y + my][player.z].isStructure())
			{
				blastTerrain(player.x + mx, player.y + my, player.z);
				if(my < 0)
					--my;
				else if(my > 0)
					++my;
				
				if(mx < 0)
					--mx;
				else if(mx > 0)
					++mx;

				blasted = true;
			}
			else
				i = distance;
		}
		return blasted;
	}
	public void blastTerrain(int x, int y, int z)
	{
		if(x < 0 || x >= width ||y < 0|| y >= height || z < 0 || z >= depth)
		{
			player.notify("We're digging through another dimmmension!");
			//System.out.println("x : " + x + "y : " + y +"z : " + z);
		}
		else
		{
			tiles[x][y][z].setTile(Tile.BLASTED_TERRAIN);
		}
	}
    // Removes specified entity from EntityList 
    public void remove(Entity other) 
    {
		System.out.println("\tEntity removed ?");
		entities.remove(other);
    }
    public char glyph(int x, int y, int z)
    {
    	//System.out.println(x +  " " + y + " in glyph");
    	if(x >= width || y >=width)
    		return 'X';

    	Entity entity = entity(x, y, z);
    	Projectile p = projectile(x, y, z);
    	
    	if(entity != null && p != null)
    		return processCollision(entity, p).glyph();
    	else if(entity == null && p != null && p.glyph() == Tile.WATER.glyph() && tile(x, y, z).getTile().isFire())
    	{
    		changeTile(new Point(x, y, z), Tile.STEAM, false);
    		p.terminate();
    		return Tile.STEAM.glyph();
    	}
   
        if(p != null)
        	return p.glyph();
        if (entity != null)
            return entity.tile().glyph();
        if (item(x,y,z) != null)
            return item(x,y,z).glyph();
        
        return tile(x, y, z).getGlyph();
    }
    public Color color(int x, int y, int z)
    {
        Entity entity = entity(x, y, z);
        Projectile p = projectile(x, y, z);
        
    	if(entity!= null && p != null)
    		return Tile.TAGGED.color();
        
    	if(p != null)
        	return p.color();
        if (entity != null)
            return entity.tile().color();
        if (item(x,y,z) != null)
            return item(x,y,z).color();
        
        return tile(x, y, z).getColorF();
    }
    public Color backColor(int x, int y, int z)
    {
        Entity entity = entity(x, y, z);
        Projectile p = projectile(x, y, z);
        
    	if(p != null)
        	return Palette.darkestGray;
        if (entity != null)
            return entity.tile().backColor();
        if (item(x,y,z) != null)
            return Palette.darkestGray;
        
        return tile(x, y, z).getBackColor();
    }
    public void addPlayer(Entity player)
    {
    	this.player = player;
    }
    // Updates entity list; Used for when things are killed
    public void update()
    {
    	if(mini == null)
    	{
			mini = getMiniMap();
			mini.update();
		}

    	turnManagement();

    	List<Projectile> projUpdate = new ArrayList<>(projectiles);
    	
    	for (Projectile p : projUpdate)
        {
    		if (p.point().x < 0 || p.point().x > width -1
    				|| p.point().y < 0 || p.point().y > height - 1
    				|| !tiles[p.point().x][p.point().y][p.point().z].isGround())
    			projectiles.remove(p);
    		else if(tiles[p.point().x][p.point().y][p.point().z].isGround())
    			p.update(this);
    		
        }
    	List<Entity> toUpdate = new ArrayList<Entity>(entities);
        
    	for (Entity entity : toUpdate)
        {
            entity.update();
        }
    	if(!fireCenters.isEmpty()) // Animates fire
		{
			for(int i = 0; i < fireCenters.size();i++)
			{
				fireCenters.get(i).blaze();
				if(fireCenters.get(i).getFirePoints().isEmpty())
				{
					fireCenters.remove(i);
				}
				else
					System.out.println(fireCenters.size() + " fires in world");
			}
		}
    }
    public void turnManagement()
	{
		turns++;

		/*
					PASSIVE HEALING
		 */
		if(player.stats.getEffects().contains(Effect.Effects.PASSIVE_HEALING))
		{
			//player.stats.healAllVitals(10);
		}
		/*
					EFFECTS
		 */
		if(turns % 100 == 0)
		{
			player.stats.addEffect(new Effect(Effect.Effects.TRAUMA, "Tired", Palette.purple));
		}

		/*
					BREATHING AIR
		 */
		if(turns % 5 == 0)
		{
			//air.modifyAir(-100);
		}
		if(air.getOxygen() < 0 && player.inventory().getTypeDuration(Type.OXYGEN) < 1 )
		{
			player.stats.setBreathing(false);
			player.stats.addEffect(new Effect(Effect.Effects.SUFFOCATION, "Suffocating", Palette.red));
			player.setAlert(true);

			player.notify("It's hard to breathe.");
		}

		/*
					PLAYER SAFE ZONE
		 */
		if(!mini.isPlayerInSafeZone())
		{
			if(!player.stats.isBreathing())
			{
				//player.stats.dealDamage(2.0);
			}
		}
		int f1 = 1;
		int f2 = 100;
		int f3 = 200;
		int f4 = 400;
		int f5 = 1000;

		if(turns == f5)
		{
			radius = 2;
			circleStartingPoint = mini.getPointInsideCircle();
			mini.update();
		}
		else if(turns == f4)
		{
			radius = 4;
			circleStartingPoint = mini.getPointInsideCircle();
			mini.update();
		}
		else if(turns == f3)
		{
			radius = 8;
			circleStartingPoint = mini.getPointInsideCircle();
			mini.update();
		}
		else if(turns == f2)
		{
			radius = 16;
			circleStartingPoint = mini.getPointInsideCircle();
			mini.update();

		}
		else if(turns == f1)
		{
			radius = 27;
			circleStartingPoint = mini.getPointInsideCircle();
			mini.update();

			System.out.println("we are iffing at phase 1");
		}

	}
    public Tile douseFire()
    {
    	
    	return null;
    }
    public Tile processCollision(Entity e, Projectile p)
    {
    	Point pp = p.point();

		System.out.println("Collision has occured!");
		System.out.println(e.name() + " ");
    	
    	//System.out.println("Processing collision");
    	

    	if(e == null && p.glyph() == Tile.WATER.glyph() && tile(pp.x, pp.y, pp.z).getTile().isFire()) // if water hitting fire
    	{
    		//System.out.println("Steaming!")
    		changeTile(pp, Tile.STEAM, false);
    		
    		return Tile.STEAM;
    	}
    	if(e != null && !e.stats.getName().equals("Trader")) // if collision doesn't hit trader or self, apply damage
    	{
    		System.out.println("We;re modifying hp!");

    		int dmg = 0;
    		if(p.glyph() == Tile.Y_SMALL.glyph())
    			dmg = 100;
    		player.setTarget(e);
			//e.dealDamage(-99);
    		//e.modifyHp(-99);
    	}

    	if(!e.stats.getName().equals(e.stats.getName()))
    		return Tile.TAGGED;
    	else
    		return e.tagged;
    }
    public void queueProjectile(Projectile p)
    {
    	// projectileMap[p.point().x][p.point().y][p.point().z] = p;

    	projectiles.add(p);
    }
    
    // Adds entities
    public void spawnPlayer(Entity player)
	{
		Random r = new Random();
		//System.out.println(startingPoints.size() + " spawn points size");
		Point p = startingPoints.get(r.nextInt(startingPoints.size()));

		player.x = p.x;
		player.y = p.y;
		player.z = 0;

		entities.add(player);
	}
    public void spawnInside(int depth, Object ...args)
    {
		Random r = new Random();
    	Point p;
		if(args[0] instanceof Entity)
		{
			Entity entity = (Entity) args[0];
			/*
			Integer depth = null;
			if(entity.getEntityAi() instanceof PlayerAi)
			{
				depth = 0; 
				System.out.println(entity.name());
			}
			else
				depth = null;
			*/

			//System.out.println(insideSpawns.size() + " spawn size");
			if(insideSpawns.size() > 0)
			{
				do 
				{
					p = insideSpawns.get(r.nextInt(insideSpawns.size()));
				} while (!tile(p.x,p.y,p.z).isGround() || entity(p.x,p.y,p.z) != null
						||  p.z != depth);
				
				entity.x = p.x;
				entity.y = p.y;
				entity.z = p.z;
				
				entities.add(entity);
			}
		}
		else if(args[0] instanceof Item)
		{
			Item item = (Item) args[0];
			if(insideSpawns.size() > 0)
			{
				do 
				{
					p = insideSpawns.get(r.nextInt(insideSpawns.size()));
				} while (!tile(p.x,p.y,p.z).isGround() || entity(p.x,p.y,p.z) != null
						|| p.z != depth);
				
				itemMap[p.x][p.y][p.z] = item;
			}
		}
    }
    public void addAtEmptyLocation(int z, Object ... args)
    {
		int x;
		int y;
		
		if(args[0] instanceof Entity)
		{
			Entity entity = (Entity) args[0];
			
			do 
			{
				x = (int)(Math.random() * width);
				y = (int)(Math.random() * height);
				if(z != 0)
					z = (int)(Math.random() * height);
			} while (!tile(x,y,z).isGround() || entity(x,y,z) != null);
			
			entity.x = x;
			entity.y = y;
			entity.z = z;
			entities.add(entity);
		}
		else if(args[0] instanceof Item)
		{
			Item item = (Item) args[0];
			
			do 
			{
				x = (int)(Math.random() * width);
				y = (int)(Math.random() * height);
				if(z != 0)
					z = (int)(Math.random() * height);
			} while (!tile(x,y,z).isGround() || entity(x,y,z) != null);
			
			itemMap[x][y][z] = item;
			
		}

	}
    public void remove(int x, int y, int z) 
    {
        itemMap[x][y][z] = null;
    }
    public void addAtEmptySpace(Item item, int x, int y, int z)
    {
        if (item == null)
            return;
        
        List<Point> points = new ArrayList<Point>();
        List<Point> checked = new ArrayList<Point>();
        
        points.add(new Point(x, y, z));
        
        while (!points.isEmpty())
        {
            Point p = points.remove(0);
            checked.add(p);
            
            if (!tile(p.x, p.y, p.z).isGround())
                continue;
             
            if (itemMap[p.x][p.y][p.z] == null)
            {
            	itemMap[p.x][p.y][p.z] = item;
                Entity e = this.entity(p.x, p.y, p.z);
                
                if (e != null)
                    e.notify("A %s lands between your feet.", item.name());
                return;
            } else 
            {
                List<Point> neighbors = p.neighbors8();
                neighbors.removeAll(checked);
                points.addAll(neighbors);
            }
        }
    }
	public int getTurns() {
		return turns;
	}
	public void openDoor(Point p)
	{
		for(Door d : doorPoints)
		{
			if(d.getPoints().contains(p))
			{
				for(Point ap : d.getPoints())
				{
					tiles[ap.x][ap.y][ap.z] = new TileV(Tile.OPEN_DOOR);
				}
				d.open();
			}
			else
			{
				//System.out.print(p.toString());
			}
		}
	}
	public Tile getTile(int x, int y, int z)
	{
		return tiles[x][y][z].getTile();
	}

	public TileV getTileV(int x, int y, int z)
{
	return tiles[x][y][z];
}

	public void closeDoor(Point p)
	{
		for(Door d : doorPoints)
		{
			if(d.getPoints().contains(p))
			{
				for(Point ap : d.getPoints())
				{
					tiles[ap.x][ap.y][ap.z] = new TileV(Tile.CLOSED_DOOR);
				}
				d.close();
			}
			else
				System.out.print(p.toString());
		}
	}

	public Door getDoor(Point p)
	{
		for(Door d : doorPoints)
		{
			if(d.getPoints().contains(p))
			{
				return d;
			}
		}
		return null;
	}

    public void addEntityAt(Point p, Entity e)
	{
		//System.out.println(p.toString() + " this is the droid point");

		e.x = p.x;
		e.y = p.y;
		e.z = p.z;

		entities.add(e);
    }
	public void addAtStartingPoint(Entity turkey)
	{
		//tem.out.println(insideSpawns.size());

		Point p = insideSpawns.get(random.nextInt(insideSpawns.size()));

		turkey.x = p.x;
		turkey.y = p.y;
		turkey.z = 0;

		entities.add(turkey);
	}
	public Point getCirclePoint() {
		return circleStartingPoint;
	}
	public List<Stash> getStashes()
	{
		return stashPoints;
	}
	public void generateLockedRoomLoot()
	{
		ItemFactory itemFactory = new ItemFactory();
		LootTable table =  new LootTable();

		for(RoomV r : dungeon.getGoldRooms()) // Gold room loot
		{
			int numItems = random.nextInt(1) + 5; // random number of items to gen

			Point p = r.getFloorPoints().get(random.nextInt(r.getFloorPoints().size()));
			Stash s = new Stash(p);

			for(int i = 0; i < numItems; i++)
			{
				s.addItem(table.getGoldRoomItem());
			}
			stashPoints.add(s);
			dungeon.addStash(p, s, Tile.STASH);
		}
		for(RoomV r : dungeon.getGreenRooms()) // Green room loot
		{
			int numItems = random.nextInt(3) + 2;

			Point p = r.getFloorPoints().get(random.nextInt(r.getFloorPoints().size()));
			Stash s = new Stash(p);

			for(int i = 0; i < numItems; i++)
			{
				s.addItem(table.getGreenRoomItem());
			}
			stashPoints.add(s);
			dungeon.addStash(p, s, Tile.MED_STASH);
		}
		for(RoomV r : dungeon.getRedRooms()) // Green room loot
		{
			int numItems = random.nextInt(3) + 2;

			Point p = r.getFloorPoints().get(random.nextInt(r.getFloorPoints().size()));
			Stash s = new Stash(p);

			for(int i = 0; i < numItems; i++)
			{
				s.addItem(table.getRedRoomItem());
			}
			stashPoints.add(s);
			dungeon.addStash(p, s, Tile.RED_STASH);
		}
	}
	public void spawnEnemies()
	{
		spawnMainRegionEnemies();
	}
	public void spawnMainRegionEnemies()
	{
		EntityFactory nullEntityFactory = new EntityFactory(this, null);
		entities.add(player);

		int enemiesInMain = 15;
		for (int z = 1; z < depth(); z++)
		{
			for (int i = 0; i < enemiesInMain; i++)
			{
				Entity ents = nullEntityFactory.newVagrant(z, player);
				Entity ent = nullEntityFactory.newHitman(z, player);

				spawnInMainRegion(ents);
				spawnInMainRegion(ent);
			}
		}
	}
	public void spawnInMainRegion(Entity e)
	{
		Point p = dungeon.getMainRegionPointS().get(random.nextInt(dungeon.getMainRegionPointS().size()));

		if(tiles[p.x][p.y][p.z].isGround())
		{
			e.x = p.x;
			e.y = p.y;
			e.z = p.z;

			entities.add(e);
		}
	}
	public void spawnRogues()
	{
		int rogueCount = 50;
		EntityFactory nullEntityFactory = new EntityFactory(this, null);

		for(int i = 0; i < rogueCount; i++)
		{
			spawnInMainRegion(nullEntityFactory.newRogue(0, player));
		}
	}
	public void spawnRedRoomEnemies()
	{
		EntityFactory nullEntityFactory = new EntityFactory(this, null);

		for(RoomV room : dungeon.getRedRooms()) // for each red room
		{
			int numEnemies = random.nextInt(3) + 1;

			for(int i = 0; i < numEnemies; i++) // spawn up to 3 enemies
			{
				Point p = room.getFloorPoints().get(random.nextInt(room.getFloorPoints().size()));
				Entity e = nullEntityFactory.newMarine(0, player);
				if(tiles[p.x][p.y][p.z].isFloor())
				{
					e.x = p.x;
					e.y = p.y;
					e.z = p.z;

					entities.add(e);
				}
			}
			if(Math.random() > .4) // 60% chance of spawning heavy
			{
				Entity e =  nullEntityFactory.newHeavy(0, player);
				Point p = room.getFloorPoints().get(random.nextInt(room.getFloorPoints().size()));

				if(tiles[p.x][p.y][p.z].isFloor())
				{
					e.x = p.x;
					e.y = p.y;
					e.z = p.z;

					entities.add(e);
				}
			}
		}
	}
}