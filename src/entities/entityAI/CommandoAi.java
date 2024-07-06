package entities.entityAI;

import entities.Entity;
import items.ItemFactory;

public class CommandoAi extends EntityAi
{
        private ItemFactory iF;
        private Entity player;
        private Entity leader;

        public CommandoAi(Entity entity, Entity player, Entity leader)
        {
            super(entity);

            this.player = player;
            iF = new ItemFactory();

            entity.inventory().add(iF.newPlasmaPack());
            entity.inventory().add(iF.newPlasmaPack());
            this.leader = leader;
        }
        public void onUpdate()
        {
            follow(leader);
        }
}
