package entities.entityAI;

import entities.Entity;
import entities.EntityFactory;

public class RogueAi extends EntityAi
{
    public RogueAi(Entity entity, EntityFactory factory)
	{
		super(entity);
	
	}
	public void onUpdate()
	{
		wander();
	}
}
