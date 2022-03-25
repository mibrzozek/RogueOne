package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class TurkeyAI extends EntityAi
{
    private ItemFactory iF;
    private Entity player;

    public TurkeyAI(Entity entity, Entity player)
    {
        super(entity);
        this.player = player;

        iF = new ItemFactory();

        entity.inventory().add(iF.newTurkey());
        entity.inventory().add(iF.newTurkey());
        entity.inventory().add(iF.newTurkey());
    }
    public void onUpdate()
    {
        wander();
    }
}
