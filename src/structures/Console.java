package structures;

import entities.Entity;
import items.ItemFactory;
import wolrdbuilding.World;

import java.util.ArrayList;

public class Console
{
    private World world;
    private Entity player;

    ArrayList<String> log;

    public Console(World world)
    {
        this.world = world;
        this.player = world.getPlayer();
        this.log = new ArrayList<>();
        log.add("....");
    }

    public String writeAndRespond(String s)
    {
        ItemFactory iF = new ItemFactory();
        String response = "";


        if(s.equals("1"))
        {
            player.inventory().equipAll(iF.newDevSword(), iF.newRifle(), iF.newWallBomb());
            response = "Added";
        }
        else if(s.equals("empty"))
        {
            player.inventory().emptyBag();
            response = "Destroyed";
        }

        if(response != "")
            log.add(response);

        return response;
    }
    public ArrayList<String> getLog()
    {
        return log;
    }
}
