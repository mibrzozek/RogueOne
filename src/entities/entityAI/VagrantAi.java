package entities.entityAI;

import entities.Entity;
import items.Item;
import items.ItemFactory;
import items.LootTable;

public class VagrantAi extends HitmanAi
{
    Entity player;

    public VagrantAi(Entity entity, Entity player)
    {
        super(entity, player);
        this.entity = entity;
        this.player = player;
        this.entity.setVisionRadius(10);

        ItemFactory iF = new ItemFactory(null);
        LootTable loot = new LootTable();
        rollLoot(iF, loot);

    }
    private void rollLoot(ItemFactory iF, LootTable loot)
    {
        entity.inventory().clear();

        if(Math.random() < 0.2)
            entity.inventory().equipAll(iF.newSerratedKnife());
        else if(Math.random() < 0.1)
            entity.inventory().equipAll(iF.newFoldedSteelKnife());
        else
            entity.inventory().equipAll(iF.newRustyKnife());

        Item armor = loot.getArmorItem();

        if(armor.rarity().equals(Item.Rarity.RARE)) // if rare item
        {
            if(Math.random() < 0.5)                 //don't add 50% of the time
                entity.inventory().equipAll(armor);
        }
        else
        {
            entity.inventory().equipAll(armor);
        }
        if(Math.random() < 0.1) // random extra COMMON armor
        {
            Item extra = loot.getArmorItem();
            if(extra.rarity().equals(Item.Rarity.COMMON))
                entity.inventory().equipAll(loot.getArmorItem());
        }

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
