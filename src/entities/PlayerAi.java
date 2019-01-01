package entities;

import java.util.ArrayList;
import java.util.List;

import items.Item;
import items.ItemFactory;
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
	public boolean canSee(int wx, int wy, int wz) 
	{
		ItemFactory iF = new ItemFactory();
		
		if(entity.inventory().isItemEquiped(iF.newDevSword()))
			return true;
		else
			return fov.isVisible(wx, wy, wz);
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
