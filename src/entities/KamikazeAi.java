package entities;

import items.ItemFactory;

public class KamikazeAi extends HitmanAi
{
    Entity player;

    public KamikazeAi(Entity entity, Entity player)
    {
        super(entity, player);
        this.entity = entity;
        this.player = player;
        this.entity.setVisionRadius(10);

        ItemFactory iF = new ItemFactory(null);
        entity.inventory().clear();

        entity.inventory().add(iF.newOpticalMagnifier());
        entity.inventory().moveToEquiped(0);

        entity.inventory().add(iF.newRustyKnife());
        entity.inventory().add(iF.newClearanceRed());

        entity.inventory().equipAll(iF.newKevlarTorso(), iF.newBasicHelmet(), iF.newRustyKnife());
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
