package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class BossAi extends EntityAi
{
    public BossAi(Entity entity, Entity player)
    {
        super(entity);
        ItemFactory iF = new ItemFactory();
        entity.inventory().equipAll(iF.newDiscoBall());
        //ItemManager equipBoss
        //BuffManager buffBoss
        //
    }
    public void onUpdate()
    {
        wander();
    }

}
