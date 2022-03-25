package entities.entityAI;

import entities.Entity;
import entities.EntityFactory;

public class FungusAi extends EntityAi
{
	private EntityFactory factory;
    private int spreadcount; 
	
    public FungusAi(Entity entity, EntityFactory factory)
	{
		super(entity);
		this.factory = factory;
	}
	public void onUpdate()
	{
		wander();
		/*
        if (spreadcount < 5 && Math.random() < 0.01)
            spread();
        */
	}
	// Original wander made by me ;]
	private void animate()
	{
        int x = entity.x + (int)(Math.random() * 3 - 2);
        int y = entity.y + (int)(Math.random() *  3 + 2);
        int z = entity.z;
  
        if (!entity.canEnter(x, y, z))
            return;	
        
        entity.x = x;
        entity.y = y;
        
	}
 
    private void spread()
    {
        int x = entity.x + (int)(Math.random() * 11) - 5;
        int y = entity.y + (int)(Math.random() * 11) - 5;
        int z = entity.z;
  
        if (!entity.canEnter(x, y, z))
            return;
        
        entity.doAction("spawn a child");
  
        //Entity child = factory.newFungus(entity.z);
        //child.x = x;
        //child.y = y;
        //child.z = z;
        spreadcount++;
    }
}
