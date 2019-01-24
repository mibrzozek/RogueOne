package entities;

import java.util.ArrayList;
import java.util.List;

import items.Item;
import items.ItemFactory;
import structures.Script;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;

public class PlayerAi extends EntityAi
{
	private List<String> messages;
	private FieldOfView fov;
	private boolean canMine = false;
	
	private List<String> attacks;
	 
    public PlayerAi(Entity entity, List<String> messages, FieldOfView fov) 
    {
    	super(entity);
    	this.entity.setVisionRadius(12);
    	this.messages = messages;
    	this.fov = fov;
    	attacks = new ArrayList<String>();
    	
    	attacks.add("Taunt");
    	attacks.add("Shoot");
    	attacks.add("Melee");
    	
    	this.entity.tagged = Tile.TAGGED_PLAYER;
    	this.entity.setScript(new Script());
    }
    public ArrayList<String> getAttacks()
    {
    	return (ArrayList<String>) attacks;
    }
    
    public void setFOV(FieldOfView fov) 	{	this.fov = fov;	}
    public FieldOfView getFOV()				{	return fov;     }
    
    public void onEnter(int x, int y, int z, Tile tile)
    {
    	entity.setTradeMode(false);
    	
    	if (tile.isGround())
    	{
            entity.x = x;
            entity.y = y;
            entity.z = z;
        } 
    	else if (tile.isDiggable() && canMine()) 
        {
    		if(entity.plasma() > 10)
    		{
    			entity.dig(x, y, z);
    			entity.modifyPlasma(-10);
    		}
    		else
    			entity.notify("Aww shit, all out of Plasma!");
    	}
        else if(tile.isGround())
        {
        	return;
        }
    }
    public void onNotify(String message)
    {
    	messages.add(message);
    }
    @Override
	public boolean canSee(int wx, int wy, int wz)
	{
		ItemFactory iF = new ItemFactory();

		if(entity.inventory().isItemEquiped(iF.newDevSword()))
			return true;
		if (entity.z != wz)
			return false;
		if ((entity.x-wx)*(entity.x-wx) + (entity.y-wy)*(entity.y-wy) > entity.visionRadius()*entity.visionRadius())
			return false;
		for (Point p : new Line(entity.x, entity.y, wx, wy))
		{
			if (entity.tile(p.x, p.y, wz).isGround() || entity.tile(p.x, p.y, wz).isStructure() || p.x == wx && p.y == wy)
				continue;

			return false;
		}
		return true;
	}
	public boolean canMine()
	{
		Item item = new ItemFactory().newMiningBeam();
		
		if(entity.inventory().isItemEquiped(item))
			return true;
		else 
			return false;
	}
}
