package entities;

import items.ItemFactory;

public class DroidAI extends EntityAi
{
    private ItemFactory iF;
    private Entity player;

    public DroidAI(Entity entity, Entity player)
    {
        super(entity);

        this.player = player;
        iF = new ItemFactory();

        entity.inventory().add(iF.newPlasmaPack());
        entity.inventory().add(iF.newPlasmaPack());
    }
    public void onUpdate()
    {
        if(this.entity.inventory().containsInInventory(iF.newBasicAiUnit()))
            wander();
    }
}
