package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class HeavyAi extends EntityAi
{
    private Entity player;
    public HeavyAi(Entity entity, Entity player) {
        super(entity);
        this.player = player;
        rollLoot();

    }
    private void rollLoot()
    {
        ItemFactory itemFactory = new ItemFactory();

        entity.inventory().equipAll(itemFactory.newHybridTorso(), itemFactory.newScopedRifle(), itemFactory.newHeavyHelmet());
    }
    @Override
    public void onUpdate()
    {
        if (Math.random() < 0.2)
            return;

        if (entity.canSee(player.x, player.y, player.z) && player.getStealth() < 100)
        {
            hunt(player);
        }
        else
        {
            wander();
        }
    }
}
