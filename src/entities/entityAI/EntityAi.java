package entities.entityAI;

import entities.Effect;
import entities.Entity;
import entities.Line;
import entities.Path;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class EntityAi implements Serializable
{
	protected Entity entity;
    private Entity player;

    public EntityAi(Entity entity)
    {
        this.entity = entity;
        this.entity.setEntityAi(this);

        this.entity.setVisionRadius(5);
    }
    public void wander()
    {
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;
        
        Entity other = entity.entity(entity.x + mx, entity.y + my, entity.z);
        
        if(Math.random() > .8)
        	return;
        
        if(other != null && other.tile().glyph() == entity.tile().glyph())
        	return;
        else
        	entity.moveBy(mx, my, 0);
    }
    public void follow(Entity leader)
    {
        if(leader != null && entity != null)
        {
            List<Point> points = new Path(entity, leader.x, leader.y).points();
            if(points != null)
            {
                if(Math.abs(this.entity.x - leader.x) <5 && Math.abs(this.entity.y - leader.y) <5)
                    return;
                int mx = points.get(0).x - entity.x;
                int my = points.get(0).y - entity.y;
                entity.moveBy(mx, my, 0);
            }
        }
    }
    public void hunt(Entity target)
    {
        if(target != null && entity != null)
        {
            List<Point> points = new Path(entity, target.x, target.y).points();

            if(points != null)
            {
                int mx = points.get(0).x - entity.x;
                int my = points.get(0).y - entity.y;
                entity.moveBy(mx, my, 0);
            }
        }
    }
    public void onEnter(int x, int y, int z, Tile tile) 
    { 
    	if (tile != null && tile.isGround())
        {
        	entity.x = x;
        	entity.y = y;
        	entity.z = z;
        }
        else if(tile.isEntity())
        {
            System.out.println("Melee damage code here");
        }
    	else 
        {
        	entity.doAction("bump into a wall");
        }
    }
	public boolean canSee(int wx, int wy, int wz) 
	{
        if (entity.z != wz) // if this entity and player are not on the same level
            return false;
        if ((entity.x-wx)*(entity.x-wx) + (entity.y-wy)*(entity.y-wy) > entity.visionRadius()*entity.visionRadius())
        {
            return false;
        }
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
        ArrayList<Effect> l = entity.stats.getEffects();
        List<Effect> indexToRemove = new ArrayList();


        if(entity.inventory().getPrimaryWeapon() != null)
        {
            if(entity.inventory().getPrimaryWeapon().isReloading())
            {
                this.entity.inventory().getPrimaryWeapon().reload(entity);
                entity.notify(this.entity.inventory().getPrimaryWeapon().getTurnsUntilReloaded() + "");
                //System.out.println("Is this 3?");
            }
        }

        for(Effect e : l) // update and queue done effects
        {
            e.update();

            if(e.getEffectLength() == 0) // zero is the condition in which effects end
            {

                if(e.getEffectTag().equals("Suffocating"))
                {
                    //System.out.println(e.getEffectLength() + "  tis is the effect length");
                    entity.setDead(true);
                }
                indexToRemove.add(e);
            }
        }
        if(!indexToRemove.isEmpty()) // remove done effects
        {
            for(Effect es : indexToRemove)
            {
                entity.stats.getEffects().remove(es);
            }
        }
        entity.processStates();
	}
	public void onNotify(String message)
	{
		
	}
}
