package wolrdbuilding;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.Effect;
import entities.Entity;
import entities.Mech;
import items.Item;
import items.Type;
import structures.Air;
import structures.Dungeon;

public class World implements Serializable
{
	private static final int MINI_CELL = 3;

	private Point playerCell;

	Random random;
	private Entity player;

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
    
    // Constructor
	public World(TileV[][][] tiles, ArrayList<Point> spawns, ArrayList<Point> startingPoints, Entity player, Dungeon d)
	{
		this.tiles = tiles;
		this.player = player;
		
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;

		this.turns = 0;
		
		this.entities = new ArrayList<Entity>();
		this.projectiles = new ArrayList<Projectile>();
		this.firePoints = new ArrayList<Point>();
		this.doorPoints  = d.getDoorPoints();

		this.fireCenters = new ArrayList<>();
		
		this.itemMap = new Item[width][height][depth];
		this.projectileMap = new Projectile[width][height][depth];

		this.miniMap = new TileV[width/MINI_CELL][height/MINI_CELL][depth];
		this.mini = new MiniMap(this);
		
		this.insideSpawns = spawns;
		this.startingPoints = startingPoints;

		this.air = new Air(this);
		//System.out.println(startingPoints.size());
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
		return mini;
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
				if(newFire || newFire) // gets newest fires points and sees if new poiint is already ther
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
			return tiles[x][y][z];
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
			System.out.println(tiles[player.x +mx][player.y + my][player.z].getTile().toString());

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
			System.out.println("x : " + x + "y : " + y +"z : " + z);
		}
		else
		{
			tiles[x][y][z].setTile(Tile.BLASTED_TERRAIN);
		}
	}
    // Removes specified entity from EntityList 
    public void remove(Entity other) 
    {
        entities.remove(other);
    }
    
    public char glyph(int x, int y, int z)
    {
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

		// Logic for giving out EFFECTS over time spent in the world
		if(turns % 100 == 0)
		{
			player.stats.addEffect(new Effect(Effect.Effects.TRAUMA, "Tired", Palette.purple));
		}
		// World changes over time
		if(turns % 5 == 0)
		{
			air.modifyAir(-100);
		}

		if(air.getOxygen() < 0 && player.inventory().getTypeDuration(Type.OXYGEN) < 1 )
		{
			player.stats.setBreathing(false);
			player.stats.addEffect(new Effect(Effect.Effects.SUFFOCATION, "Suffocating", Palette.red));
			player.setAlert(true);

			player.notify("It's hard to breathe.");
		}

	}
    public Tile douseFire()
    {
    	
    	return null;
    }
    public Tile processCollision(Entity e, Projectile p)
    {
    	Point pp = p.point();
    	
    	System.out.println("Prcessing collision");
    	
    	if(e == null && p.glyph() != Tile.WATER.glyph())
    	{
    		return p.tile();
    	}
    	else if(e == null && p.glyph() == Tile.WATER.glyph() && tile(pp.x, pp.y, pp.z).getTile().isFire())
    	{
    		System.out.println("Steaming!");
    		
    		changeTile(pp, Tile.STEAM, false);
    		
    		return Tile.STEAM;
    	}

    	
    	if(e != null && !e.stats.getName().equals("Trader")
    			&& !e.stats.getName().equals(e.stats.getName())) // if collision doesn't hit trader or self, apply damage
    	{
    		int dmg = 0;
    		if(p.glyph() == Tile.Y_SMALL.glyph())
    			dmg = 100;
    		player.setTarget(e); 
    		e.modifyHp(-99);
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
		System.out.println(startingPoints.size() + " world s pints spawn");
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
    public void addMech(Mech mech)
    {
    	int x;
    	int y;
    	int z;
		do 
		{
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
			z = (int)(Math.random() * height);
		} while (!tile(x,y,z).isGround() && !tile(x+1,y,z).isGround() && !tile(x,y+1,z).isGround()
				&& !tile(x+1,y+1,z).isGround()
				|| entity(x,y,z) != null);
		
		mech.x = player.x + 2;
		mech.y = player.y;
		mech.z = player.z;
		entities.add(mech);
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

    public void showDoorUi() {
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
				System.out.print(p.toString());
		}
	}

	public Tile getTile(int x, int y, int z)
	{
		return tiles[x][y][z].getTile();
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
}