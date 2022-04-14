package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class JunkieAi extends EntityAi
{
    private ItemFactory iF;
    private Entity player;
    public JunkieAi(Entity entity, Entity player)
    {
        super(entity);
        this.player = player;
        
        iF = new ItemFactory();
        
        entity.inventory().add(iF.newPlasmaPack());
        entity.inventory().add(iF.newPlasmaPack());
    }
    public void onUpdate()
    {
    	if(entity.stats.vitals.getVitals() < entity.stats.vitals.getFullVitals())
    		hunt(player);
    	else
    		wander();
    }
    public void shootUp()
    {
    	
    }
    
}
