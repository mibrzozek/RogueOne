package Managers;

import entities.Statistics;
import wolrdbuilding.PlanetPrinter;
import wolrdbuilding.World;

import java.io.Serializable;

public class MapMan implements Serializable
{
    World world;

    public MapMan(Statistics stats, World.Map map)
    {
        createWorld(map);
    }
    private void createWorld(World.Map m)
    {
        // Sets World size
        // Makes caves, castles, loot and enemies
        world = new PlanetPrinter(200 ,200 , 5
                , null)
                .makeDungeons(m)
                .build();

    }

}
