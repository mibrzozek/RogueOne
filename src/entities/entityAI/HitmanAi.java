package entities.entityAI;

import entities.Entity;
import entities.Path;
import items.ItemFactory;
import wolrdbuilding.Point;

import java.util.List;

public class HitmanAi extends EntityAi 
{
  private Entity player;

  public HitmanAi(Entity entity, Entity player) 
  {
    super(entity);
    initializeLoot();

    System.out.println(entity.inventory().getEquipped(0).toString());
    this.player = player;
  }
  public void initializeLoot()
  {
      entity.inventory().add(new ItemFactory().newMacroUzi());
      entity.inventory().moveToEquiped(0);
      entity.inventory().setPrimaryWeapon(entity.inventory().getEquipped(0));
  }
  public void onUpdate()
  {
      if (Math.random() < 0.2)
          return;
  
      if (entity.canSee(player.x, player.y, player.z) && player.getStealth() < 100)
          hunt(player);
      else
          wander();
  }
  public void hunt(Entity target)
  {
      List<Point> points = new Path(entity, target.x, target.y).points();

      if(points ==  null)
          return;
      if(points.isEmpty())
          return;

      int mx = points.get(0).x - entity.x;
      int my = points.get(0).y - entity.y;
      
      if(mx > 0 || my > 0)
    	  entity.setDirection(5);
      else if(mx < 0 || my < 0)
    	  entity.setDirection(3);
      
      else if(mx == 0 || my > 0)
    	  entity.setDirection(4);
      else if(mx > 0 || my == 0)
    	  entity.setDirection(2);

      else if(mx < 0 || my == 0)
    	  entity.setDirection(6);
      else if(mx == 0 || my < 0)
    	  entity.setDirection(0);
   
      else if(mx < 0 || my > 0)
    	  entity.setDirection(1);
      else if(mx > 0 || my < 0)
    	  entity.setDirection(5);

      if(entity.inventory().hasProjectileWeapon())
          entity.useWeapon(target);

      entity.moveBy(mx, my, 0);
  }
}