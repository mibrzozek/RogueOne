package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import items.Inventory;
import items.Inventory.EquipmentSlot;
import items.Item;
import items.ItemFactory;
import items.Type;
import structures.Script;
import wolrdbuilding.*;

/*
 *  Damage :
 *  	Look for dealDamage()
 * 		Randomly choose a limb
 * 		Filters  damage dealt by the armor thats on the limb
 * 		Modifies limb health
 * 	Attack :
 * 		Undecided
 * 	Inventory
 * 			
 */

public class Entity implements Serializable
{
	private static final int DEFAULT_VISION_RADIUS = 5;
	//public enum Direction()
	
	// Context Variables
	private World world;
    public int x;
    public int y;
    public int z;
    	
    public int direction = 0;
    public Direction cardinal = Direction.NORTH;
    
    // Describers   
    public Tile tagged = Tile.TAGGED;
    private String name;
    private Tile tile;
    private int visionRadius;
    // Abstract
    private EntityAi ai;
    private Inventory inventory;
    private boolean equiped = false;
    private boolean tradeMode = false;
    // Stats
    public Statistics stats;
    private int maxHP;
    private int hp;
    
    private int attackValue;
    private int defenseValue;
    private int plasmaValue = 0;
    private int shieldValue = 100;
    private int crypto;
    
    private Point tradersPosition;
    private Point enemyPosition;
    public Entity lastTargetedEnemy = null;

    public Script script;
    
    private ArrayList<Projectile> projectiles;
    
    private Random r = new Random();
    
    public Entity(Statistics stats, World world, Tile tile)
    {
    	this.stats = stats;
    	this.world = world;
    	this.tile = tile;
    	
        this.projectiles = null;
        this.inventory = new Inventory(20);
        this.crypto = 1000;
    }
    
    public Entity(String name, World world, Tile tile, int maxHP)
    {
    	this.name = name;
        this.world = world;
        this.tile = tile;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = 0;
        this.defenseValue = 500;
        this.visionRadius = 9;
        
        this.stats = new Statistics();
        stats.setName(name);
        
        this.projectiles = null;
        this.inventory = new Inventory(20);
        this.crypto = 1000;
    }

    public Entity(String name, World world, Tile tile, int maxHP, int attack, int defense)
    {
    	this.name = name;
        this.world = world;
        this.tile = tile;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = 9;
        
        this.stats = new Statistics();
        stats.setName(name);
        
        this.projectiles = null;
        this.inventory = new Inventory(20);
        this.crypto = 100;
    }

    public void setScript(Script script)		{	this.script = script;	}
    public void setTile(Tile t)					{ 	this.tile = tile;	}						
    public void addFOV(FieldOfView fov)			{   ((PlayerAi)ai).setFOV(fov); }
    public void addWorld(World world)			{   this.world = world; }
    public void setTarget(Entity other)			{   this.lastTargetedEnemy = other; }
    public void setTradeMode(boolean truth) 	{	this.tradeMode = truth; }
	public void setEntityAi(EntityAi ai) 		{	this.ai = ai; }		
	public void setVisionRadius(int radius) 	{	this.visionRadius  = radius; }
    public void modifyPlasma(int amount)		{	plasmaValue += amount; }
    public void modifyCrypto(int amount)		{	crypto += amount; }
    public void modifyAttack(int amount) 		{	attackValue += amount; }
    public void modifyDefense(int amount) 		{ 	defenseValue += amount;	}
    public void modidyShield(int amount) 		{	shieldValue += amount; }
    public Tile hitTile()						{   return Tile.TAGGED;			}
	
	public int hp() 				{ return hp; }
	
	public int attackValue() 		{ return attackValue; }
	public int defenseValue() 		{ return defenseValue; }
	public int plasma()				{ return plasmaValue; }
	public int shield()				{ return shieldValue; }
	
	public int crypto()				{ return crypto; }
    public int visionRadius() 		{ return visionRadius; }
    
    public Direction getCardinal()	{ return cardinal;	}
   
    public Tile tile()				{ return tile;	}
    public int maxHP() 				{ return maxHP; }
    public String name() 			{ return name; }
    public Inventory inventory()	{ return inventory; }
    public EntityAi getEntityAi() 	{ return ai; }
    public boolean tradeMode()		{ return tradeMode; }
    public Point tradersPosition()  { return tradersPosition; }
    public Entity lastTargeted()	{ return lastTargetedEnemy;}
    public double getStealth()		{ return inventory.getStealthNumber();}
    public FieldOfView fov()		{ if( ai instanceof PlayerAi)
    										return ((PlayerAi) ai).getFOV();
    									else return null;
    								}
	public Script getScript() 		{ return script; }
   
    
	public Tile tile(int wx, int wy, int wz) 
	{
		return world.tile(wx, wy, wz).getTile();
	}
	public boolean isLookingAtStructure(int wx, int wy, int wz)
	{
		return world.tile(wx, wy, wz).isStructure();
	}
    public Entity entity(int wx, int wy, int wz) 
    {
        return world.entity(wx, wy, wz);
    }
    public void equipItem(Item item)
    {
    	if(item != null && !inventory.isFullyEquiped())
    	{	
    		modifyAttack((int)item.attack());
    		modifyDefense((int)item.defense());
    	}
    }
    public void uniequipItem(Item item)
    {
    	if(item != null && !inventory.isFullyEquiped())
    	{	
    		modifyAttack(-(int)item.attack());
    		modifyDefense(-(int)item.defense());
    	}
    }
    public ArrayList<Projectile> getProjectiles()
    {
    	if(projectiles != null)
    		return projectiles;
    	else
    		return null;
    }

    public void setSpark()
	{
		if(Math.random() > 0.9)
			world.changeTile(new Point(x, y, z), Tile.getFire(), true);
	}
    public void useDevice()
    {
    	notify("Device out!");

    	if(inventory.getDevice() != null 
    			&& inventory.getDevice().type().equals(Type.DEVICE)
				)
    	{
    		//inventory.removeEquiped(inventory.getDevice())


    		if(plasmaValue > 50
					&& world.tunnelExplosion(this.direction, inventory.getDevice().value()))
    			plasmaValue -= 50;
    		else 
    			return;
    	}
    }
    public boolean isNextToWall()
	{

		if(!world.tile(x, y-1, z).getTile().isFloor()) // north
			return false;
		else if(!world.tile(x -1, y-1, z).getTile().isFloor()) // north west
			return false;
		else if(!world.tile(x, y+1, z).getTile().isFloor()) // south
			return false;
		else if(!world.tile(x+1, y+1, z).getTile().isFloor()) // south east
			return false;
		else if(!world.tile(x + 1, y, z).getTile().isFloor()) // west
			return false;
		else if(!world.tile(x - 1, y + 1, z).getTile().isFloor()) // south west
			return false;
		else if(!world.tile(x + 1, y - 1, z).getTile().isFloor()) // north east
			return false;
		else if(!world.tile(x + 1, y, z).getTile().isFloor()) // east
			return false;
		else
			return true;
	}
    public void useWeapon()
    {
    		Projectile p;
    		if((inventory.isItemEquiped(new ItemFactory().newDevSword()) 
    				&& inventory.isItemEquiped(new ItemFactory().newMacroUzi()))
    				&& inventory.isItemEquiped(new ItemFactory().newScopedRifle()))
    		{
    			p = new Projectile(direction, new Point(x, y, z), Tile.R_SNIPER);
    			world.queueProjectile(p);
    			
    			p = new Projectile(direction, new Point(x, y, z), Tile.G_SMALL);
    			world.queueProjectile(p);
    			
    			p = new Projectile(direction, new Point(x, y, z), Tile.Y_SMALL);
    			world.queueProjectile(p);
    			
    			notify("Hahahaha, die suckers!");
    		}
    		else if(inventory.isItemEquiped(new ItemFactory().newScopedRifle()))
    		{
    			p = new Projectile(direction, new Point(x, y, z), Tile.R_SNIPER);
    			world.queueProjectile(p);
    			notify("Die suckers!");
    		}
    		else if(inventory.isItemEquiped(new ItemFactory().newDevSword()))
    		{
    			p = new Projectile(direction, new Point(x, y, z), Tile.G_SMALL);
    			world.queueProjectile(p);
    			notify("Die suckers!");
    		}
    		else if(inventory.isItemEquiped(new ItemFactory().newMacroUzi()))
    		{
    			p = new Projectile(direction, new Point(x, y, z), Tile.Y_SMALL);
    			world.queueProjectile(p);
    			notify("Die suckers!");
    		}
    }
    // Attacking, modifying HP, messages
    public void attack(Entity other)
    {
    	tradeMode=false;
        int amount = Math.max(0, attackValue());
    
        amount = (int)(Math.random() * amount) + 1;
        
        other.notify("The '%s' attacks you for %d damage.", name, amount);
        
        notify("You deal '%s' damage to the enemy", amount);
        
        if(other.ai instanceof PlayerAi)
        	other.dealDamage(-100);
        else
        	other.modifyHp(-amount);
        	
    }
    public void addEffect(Effect e)
	{

	}
    public void testMethod()
    {
    	System.out.println("testing");
    }
    
    public void dealDamage(int amount)
    {
    	/*
    	if (shieldValue > 10)
    		shieldValue += amount /2.0;
    	else
    		modifyHp(amount);
		*/
    	double damage = amount;
    	EquipmentSlot slot = null;
    	
    	while(slot == null)
    	{
    		int bodyPart = r.nextInt(4)+ 1;
    		
    		if(bodyPart == 1)
    		{
    			if(stats.getHead() > 0)
    				slot = EquipmentSlot.HEAD;
    		}
    		if(bodyPart == 2)
    		{
    			if(stats.getTorso() > 0)
    				slot = EquipmentSlot.TORSO;
    		}
    		if(bodyPart == 3)
    		{
    			if(stats.getlHand() + stats.getrHand() > 0)
    				slot = EquipmentSlot.ARMS;
    		}
    		if(bodyPart == 4)
    		{
    			if(stats.getlLeg() + stats.getrLeg() > 0)
    				slot = EquipmentSlot.LEGS;
    		}
    		if(stats.getVitals() <= 0)
    		{
    			slot = EquipmentSlot.HEAD;
    		}
    	}
    	
    	System.out.println(EquipmentSlot.HEAD + " " + Type.HEAD);
    	
    	
    	System.out.println(" "+ stats.getHead() 
    			+ " "+ stats.getTorso() + " " 
    			+ (stats.getlHand() + stats.getrHand()) + " "     					
    			+ " "+ (stats.getlLeg() + stats.getrLeg()));
    	System.out.println("This is the slot "+ slot.toString());
    	// The following modify damage dealt based on armor and attributes
    	damage = inventory.getArmorNumber(slot, damage);
    	// check attributes, negate or cause more damage
    	modifyLimbHealth(slot, damage);
    	
    }
    public void modifyLimbHealth(EquipmentSlot slot, double damage)
    {
    	System.out.println(damage + " initial damage.");
    	
    	if(slot == EquipmentSlot.HEAD)
    		stats.setHead(stats.getHead() + damage);
    	else if(slot == EquipmentSlot.TORSO)
    		stats.setTorso(stats.getTorso() + damage);
    	else if(slot == EquipmentSlot.ARMS)
    	{
    		if((r.nextInt(1) +1) == 1 && stats.getlHand() > 0)
    			stats.setlHand(stats.getlHand() + damage);
    		else
    			stats.setrHand(stats.getrHand() + damage);
    	}
    	else if(slot == EquipmentSlot.LEGS)
    	{
    		if((r.nextInt(1) +1) == 1 && stats.getlLeg() > 0)
    			stats.setlLeg(stats.getlLeg() + damage);
    		else
    			stats.setrLeg(stats.getrLeg() + damage);
    	}
    	
    	System.out.println(damage + " damage is dealt to the " + slot.toString());
    	
    }
    public void modifyHp(double amount)
    {
        hp += amount;
    
        if (hp < 1)
        {
        	dropAll();
        	world.remove(this);
        	doAction("Die");
        }
    }
    public void notify(String message, Object ... params)
    {
        ai.onNotify(String.format(message, params));
    }
    
	public void doAction(String message, Object ... params)
	{
		int r = 6;
		for (int ox = -r; ox < r+1; ox++)
		{
			for (int oy = -r; oy < r+1; oy++)
			{
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Entity other = world.entity(x+ox, y+oy, z);
				
				if (other == null)
					continue;
				
				if (other == this)
					other.notify("You " + message + ".", params);
				else if (other.canSee(x, y, z))
					other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
		}
	}
    private String makeSecondPerson(String text)
    {
    	String[] words = text.split(" ");
    	words[0] = words[0] + "s";
            
        StringBuilder builder = new StringBuilder();
        for (String word : words)
        {
        	builder.append(" ");
        	builder.append(word);
        }
            
          return builder.toString().trim();
     }
    // Exploring
    public boolean canSee(int wx, int wy, int wz)
    {
        return ai.canSee(wx, wy, wz);
    }
    public void update()
    {   
        ai.onUpdate();  
    }
	public void dig(int wx, int wy, int wz) 
	{
		if(ai.canMine())
		{
			world.dig(wx, wy, wz);
			doAction("dig");
		}
	}
	public boolean canEnter(int wx, int wy, int wz) 
	{
		return world.tile(wx, wy, wz).isGround() && world.entity(wx, wy, wz) == null;
	}
	public void setDirection(int direction)
	{
		this.direction = direction;
		cardinal = Direction.fromIntToString(direction);
	}
	
	public void rotateCounterClockwise()
	{
		if(direction == 0)
			direction = 7;
		else if(direction == 7)
			direction = 6;
		else if(direction == 6)
			direction = 5;
		else if(direction == 5)
			direction = 4;
		else if(direction == 4)
			direction = 3;
		else if(direction == 3)
			direction = 2;
		else if(direction == 2)
			direction = 1;
		else if(direction ==1)
			direction = 0;
		
		cardinal = Direction.fromIntToString(direction);
	}
	public void rotateClockwise()
	{
		if(direction == 1)
			direction = 2;
		else if(direction == 2)
			direction = 3;
		else if(direction == 3)
			direction = 4;
		else if(direction == 4)
			direction = 5;
		else if(direction == 5)
			direction = 6;
		else if(direction == 6)
			direction = 7;
		else if(direction == 7)
			direction = 0;
		else if(direction == 0)
			direction = 1;
		
		cardinal = Direction.fromIntToString(direction);
	}
	
	public void moveBy(int mx, int my, int mz)
	{
		if (mx == 0 && my == 0 && mz == 0)
		    return;
		if(shieldValue + 5 < 80)
			shieldValue += 1;
		
		TileV tile = world.tile(x+mx, y+my, z+mz);
		
		if (mz == -1)
		{
			if (tile.getTile() == Tile.STAIRS_DOWN)
			{
				doAction("walk up to level %d", z+mz+1);
			} 
			else 
			{
				doAction("jump up but can't seem to really get anywhere");
				return;
			}
		} 
		else if (mz == 1)
		{
			if (tile.getTile() == Tile.STAIRS_UP)
			{
				doAction("walk down to level %d", z+mz+1);
			} 
			else 
			{
				doAction("look at the floor but can't go any lower");
				return;
			}
		}
		
		Entity other = world.entity(x+mx, y+my, z+mz);
		Item item = world.item(x+mx, y+my, z+mz);
		
		if (other == null)
		{	
			if(item != null
					&& item.type() == Type.APLASMA
					&& !item.usable())
			{	
				if(item.name().equals("Plasma Pod"))
					modifyPlasma(5);
				else if(item.name().equals("Plasma Pack"))
					modifyPlasma(100);
				
				notify("Mmmmm, tasty plasma");
				world.remove(x+mx, y+my, z+mz);
			}
			ai.onEnter(x+mx, y+my, z+mz, tile.getTile());
			
		}
		else if(!other.equals( new EntityFactory().newTrader()) && other != null)
		{
			lastTargetedEnemy = other;
			attack(other);
		}
		else if(other.equals( new EntityFactory().newTrader()) 
				&& other != null
				&& this.ai instanceof PlayerAi)
		{
			tradeMode = true;
			tradersPosition = new Point(x+mx, y+my,z+mz);
		}	
	}
	public void pickup()
	{
        Item item = world.item(x, y, z);
    
        if (inventory.isFull() || item == null)
        {
            doAction("You'll need a bigger book bag");
        } else 
        {
            doAction("pickup a %s", item.name());
            world.remove(x, y, z);
            inventory.add(item);
        }
    }
	public void dropAll()
	{
		for(int i = 0; i < inventory.getCapacity(); i++)
		{
			if(inventory.get(i) != null)
			{
				// doAction("drop a " + inventory.get(i).name());
				drop(i, true);
				inventory.remove(inventory.get(i));
			}

		}
    }
	public void drop(int i, boolean fromInventory)
	{
		if(fromInventory)
		{
			doAction("drop a " + inventory.get(i).name());
			world.addAtEmptySpace(inventory.get(i),x,y,z);
			inventory.remove(inventory.get(i));
			
		}
		else
		{
			doAction("drop a " + inventory.getEquipped(i).name());
			world.addAtEmptySpace(inventory.getEquipped(i),x,y,z);
			inventory.removeEquiped(inventory.getEquipped(i));
			
		}
    }
	public void setName(String name)
	{
		this.name = name;
	}
	public boolean equals(Object obj)
	{

		Entity entity = null;
		
		if(obj instanceof Entity)
			entity = (Entity) obj;
		
		// If this name and other name are Trader, return true
		// else if those names aren't Traders  but have the same hash code, return true
		if(entity != null && entity.stats.getName().equals(this.stats.getName())
				&& entity.stats.getName().equals("Trader"))
			return true;
		else if(entity != null && entity.stats.getName().toString().equals(this.stats.getName().toString())
				&& entity.hashCode() == this.hashCode())
			return true;
		else
			return false;
		
	}


	public void updateStats()
	{
		if(inventory.getVisionRadius() != null)
			this.setVisionRadius(inventory.getVisionRadius().value());
		else
		{
			this.setVisionRadius(DEFAULT_VISION_RADIUS);
		}
	}
}
