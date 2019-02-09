package entities;

import items.ItemFactory;

public class JunkieAi extends EntityAi
{
    private ItemFactory iF;
    public JunkieAi(Entity entity)
    {
        super(entity);

        iF = new ItemFactory();

        entity.inventory().add(iF.newMacroUzi());
        entity.inventory().moveToEquiped(0);

        entity.inventory().add(iF.newPlasmaPack());
        entity.inventory().add(iF.newPlasmaPod());
        entity.inventory().add(iF.newPlasmaJuice());
    }
}
