package Character;

import java.util.List;

import WorldBuilding.Tile;
import items.Item;
import items.ItemFactory;

public class PlayerAi extends EntityAi
{
	private List<String> messages;
	private FieldOfView fov;
	private boolean canMine = false;
	 
    public PlayerAi(Entity entity, List<String> messages, FieldOfView fov) 
    {
    	super(entity);
    	this.entity.setVisionRadius(12);
    	this.messages = messages;
    	this.fov = fov;
    	
    	this.entity.tagged = Tile.TAGGED_PLAYER;
    }
    
    public void setFOV(FieldOfView fov) 	{	this.fov = fov;	};
    
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
