package entities.entityAI;

import entities.Entity;
import entities.EntityFactory;

public class TraderAi extends EntityAi
{
	private EntityFactory factory;
    private int spreadcount; 
	
    public TraderAi(Entity entity, EntityFactory factory)
	{
		super(entity);
		this.factory = factory;
	}
	public void onUpdate() 
	{
		entity.doAction("wants to trade and barter");
	}
	public void onNotify(String message)
	{
		
	}

}
