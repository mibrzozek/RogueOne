package Managers;

import entities.Entity;
import items.Item;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;
import wolrdbuilding.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExplosionManager implements Serializable
{
    public static void resolveExplosion(World w, Item throwable)
    {
        Point location = throwable.getLocation();
        List<Point> neighbors = location.neighbors8();
        neighbors.add(location);
        List<Point> toRemove = new ArrayList<>();

        for(Point neighbor : neighbors)
        {
            if(!w.getTile(neighbor.x, neighbor.y, neighbor.z).isFloor())
            {
                toRemove.add(neighbor);
            }
        }
        neighbors.removeAll(toRemove);
        // add blast points
        // look at neighbors neighbors and see if explosion can spread somewhere
        List<Point> toAdd = new ArrayList<>();

        for(Point p : neighbors)
        {
            for(Point np :  p.neighbors8())
            {
                if(!neighbors.contains(np) && neighbors.size() + toAdd.size() < 9)
                {
                    toAdd.add(np);
                }
            }
        }
        neighbors.addAll(toAdd);


        for(Point floorPointsImpacted : neighbors)
        {
            w.changeTile(floorPointsImpacted, Tile.BLASTED_TERRAIN, false);
            Entity inDamageZone = w.entity(floorPointsImpacted.x, floorPointsImpacted.y, floorPointsImpacted.z);
            if(inDamageZone != null)
            {
                inDamageZone.dropAll();
                inDamageZone.doAction("Grenades kill");
                inDamageZone.stats.setDead(true);
            }
        }
        // Paint damage zone
    }

}
