package entities;

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
