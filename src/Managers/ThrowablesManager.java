package Managers;

import items.Item;
import wolrdbuilding.World;

import java.util.HashMap;
import java.util.Map;

public class ThrowablesManager
{
    private Map<Item, Integer> fuseManager;

    public ThrowablesManager()
    {
        this.fuseManager = new HashMap<>();
    }
    public void addThrowable(Item throwable, Integer fuseTime)
    {
        fuseManager.put(throwable, fuseTime);
    }
    public void manageItem(World world)
    {
        for(Item i : fuseManager.keySet())
        {
            int fuseTime = fuseManager.get(i);
            if(fuseTime > 1)
            {
                fuseManager.put(i, fuseTime - 1);
            }
            else
            {
                // Remove throwable
                // Boom
                ExplosionManager.resolveExplosion(world, i);
                fuseManager.remove(i);
                //System.out.println("There has been a " + i.name() + " explosion");
                world.remove(i.getLocation().x, i.getLocation().y, i.getLocation().z);
            }
        }
    }

    public boolean isEmpty()
    {
        return fuseManager.isEmpty();
    }
}
