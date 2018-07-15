package WorldBuilding;


import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Character.Entity;
import Character.Mech;
import items.Item;

public class World implements Serializable
{
	Random random;
	private Entity player;
	
	private List<Entity> entities;
	private ArrayList<Projectile> projectiles;
	
	private Tile[][][] tiles;
	private Item[][][] itemMap;
	private Projectile[][][] projectileMap;
	
	private ArrayList<Point> insideSpawns;
    
    private int width;
    public int width() { return width; }
    
    private int height;
    public int height() { return height; }
	
    private int depth;
	public int depth() { return depth; }
    
    // Constructor
	public World(Tile[][][] tiles, ArrayList<Point> spawns, Entity player)
	{
		this.tiles = tiles;
		this.player = player;
		
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		
		this.entities = new ArrayList<Entity>();
		this.projectiles = new ArrayList<Projectile>();
		
		this.itemMap = new Item[width][height][depth];
		this.projectileMap = new Projectile[width][height][depth];
		
		this.insideSpawns = spawns;
	}
    // Returns the tiles at give point; Used for checking type of tile
	public Tile tile(int x, int y, int z)
	{
		if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth)
			return Tile.BOUNDS;
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
			tiles[x][y][z] = Tile.FLOOR;
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
    	
    	if(entity!= null && p != null)
    		return processCollision(entity, p).glyph();
    	
   
        if(p != null)
        	return p.glyph();
        if (entity != null)
            return entity.glyph();
        if (item(x,y,z) != null)
            return item(x,y,z).glyph();
        
        return tile(x, y, z).glyph();
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
            return entity.color();
        if (item(x,y,z) != null)
            return item(x,y,z).color();
        
        return tile(x, y, z).color();
    }
    public void addPlayer(Entity player)
    {
    	this.player= player;
    }
    // Updates entity list; Used for when things are killed
    public void update()
    {
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
    	

    	
    }
    public Tile processCollision(Entity e, Projectile p)
    {
    	if(e != null && !e.name().equals("Trader")
    			&& !e.name().equals("Killing Smokes"))
    		player.setTarget(e);
    	e.modifyHp(-100);
    	e.doAction("suffers from death by bullet");
    	
    	return Tile.TAGGED;
    }
    public void queueProjectile(Projectile p)
    {
    	// projectileMap[p.point().x][p.point().y][p.point().z] = p;

    	projectiles.add(p);
    }
    
    // Adds entities
    public void spawnInside(Object ...args)
    {
    	Random r = new Random();
    	Point p;
		if(args[0] instanceof Entity)
		{
			Entity entity = (Entity) args[0];
			do 
			{
				p = insideSpawns.get(r.nextInt(insideSpawns.size()));
			} while (!tile(p.x,p.y,p.z).isGround() || entity(p.x,p.y,p.z) != null);
			
			entity.x = p.x;
			entity.y = p.y;
			entity.z = p.z;
			entities.add(entity);
		}
		else if(args[0] instanceof Item)
		{
			Item item = (Item) args[0];
			do 
			{
				p = insideSpawns.get(r.nextInt(insideSpawns.size()));
			} while (!tile(p.x,p.y,p.z).isGround() || entity(p.x,p.y,p.z) != null);
			
			itemMap[p.x][p.y][p.z] = item;
			
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
}