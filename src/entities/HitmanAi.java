package entities;

import java.util.List;

import items.ItemFactory;
import wolrdbuilding.Point;

public class HitmanAi extends EntityAi 
{
  private Entity player;

  public HitmanAi(Entity entity, Entity player) 
  {
    super(entity);
    entity.inventory().add(new ItemFactory().newMacroUzi());
    entity.inventory().moveToEquiped(0);
    this.player = player;
  }
  
  public void onUpdate()
  {
      if (Math.random() < 0.2)
          return;
  
      if (entity.canSee(player.x, player.y, player.z))
          hunt(player);
      else
          wander();
  }
  
  public void hunt(Entity target)
  {
      List<Point> points = new Path(entity, target.x, target.y).points();
  
      int mx = points.get(0).x - entity.x;
      int my = points.get(0).y - entity.y;
      
      if(mx > 0 || my > 0)
    	  entity.setDirection(5);
      else if(mx < 0 || my < 0)
    	  entity.setDirection(7);
      
      else if(mx == 0 || my > 0)
    	  entity.setDirection(4);
      else if(mx > 0 || my == 0)
    	  entity.setDirection(2);

      else if(mx < 0 || my == 0)
    	  entity.setDirection(6);
      else if(mx == 0 || my < 0)
    	  entity.setDirection(0);
      
      else if(mx < 0 || my > 0)
    	  entity.setDirection(5);
      else if(mx > 0 || my < 0)
    	  entity.setDirection(1);
      
      
      if(target.getStealth() < 99)
      {
    	  entity.useWeapon();
    	  entity.moveBy(mx, my, 0);
      }
     
      System.out.println("mx: " +  mx + " my: " + my);
  }
}