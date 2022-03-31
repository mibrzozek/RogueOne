package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class RogueAi extends EntityAi
{
	private Entity player;
    public RogueAi(Entity entity, Entity player)
	{
		super(entity);
		this.player = player;
	}
	private void rollLoot()
	{
		ItemFactory itemFactory = new ItemFactory();

		entity.inventory().equipAll(itemFactory.newHybridTorso(), itemFactory.newScopedRifle(), itemFactory.newHeavyHelmet());
	}
	public void onUpdate()
	{
		wander();
	}
}
