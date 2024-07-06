package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class DroidAi extends EntityAi
{
    private ItemFactory iF;
    private Entity player;

    public DroidAi(Entity entity, Entity player)
    {
        super(entity);

        this.player = player;
        iF = new ItemFactory();

        entity.inventory().add(iF.newPlasmaPack());
        entity.inventory().add(iF.newPlasmaPack());
    }
    public void onUpdate()
    {
        follow(player);
        //if(this.entity.inventory().containsInInventory(iF.newBasicAiUnit()))
            //wander();
    }
}