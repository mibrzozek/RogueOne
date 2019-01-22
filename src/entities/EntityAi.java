package entities;

import items.Item;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;

import java.io.Serializable;


public class EntityAi implements Serializable
{
	protected Entity entity;

    public EntityAi(Entity entity)
    {
        this.entity = entity;
        this.entity.setEntityAi(this);
    }
    public void wander()
    {
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;
        
        Entity other = entity.entity(entity.x + mx, entity.y + my, entity.z);
        
        if(other != null && other.tile().glyph() == entity.tile().glyph())
        	return;
        else
        	entity.moveBy(mx, my, 0);
    }
    public void onEnter(int x, int y, int z, Tile tile) 
    { 
    	if (tile.isGround())
        {
    		
        	entity.x = x;
        	entity.y = y;
        	entity.z = z;
        } 
    	else 
        {
        	entity.doAction("bump into a wall");
        }
    }

	public boolean canSee(int wx, int wy, int wz) 
	{
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
		return false;
	}
	public void onUpdate() 
	{
		
	}
	public void onNotify(String message)
	{
		
	}
	
}
