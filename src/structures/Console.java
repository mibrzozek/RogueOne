package structures;

import entities.Entity;
import items.ItemFactory;
import screens.PlayScreen;
import screens.Screen;
import wolrdbuilding.World;

import java.util.ArrayList;

public class Console
{
    private World world;
    private Entity player;

    private Screen playScreen;

    ArrayList<String> log;

    public Console(World world, Screen playsScreen)
    {
        this.playScreen =playsScreen;
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
            player.inventory().emptyBag();
            player.inventory().equipAll(iF.newDevSword(), iF.newRifle(),
                    iF.newWallBomb(), iF.newWaterCannon(), iF.newOxygenMask());
            response = "God mode";
        }
        else if(s.equals("2"))
        {
            player.inventory().emptyBag();
            player.inventory().equipAll(iF.newRifle(), iF.newWallBomb(),
                    iF.newWaterCannon(), iF.newOxygenMask(), iF.newClearanceGold(),
                        iF.newBlueVisor());
            response = "Comfortable mode";
        }
        else if(s.equals("3"))
        {
            player.inventory().equipAll(iF.newRifle(), iF.newWallBomb(),
                    iF.newWaterCannon(), iF.newOxygenMask(), iF.newClearanceGold(),
                    iF.newBlueVisor(), iF.newHelmet3(), iF.newGR7());
            response = "Fill mode";
        }
        else if(s.equals("empty"))
        {
            player.inventory().emptyBag();
            response = "Destroyed";
        }
        else if(s.equals("t1"))
        {
            Theme t = Theme.MIDNIGHT_PURPLE;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t2"))
        {
            Theme t = Theme.MONO_BLUE;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t3"))
        {
            Theme t = Theme.WHITE_NOT;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t4"))
        {
            Theme t = Theme.GRAY_NOT;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t5"))
        {
            Theme t = Theme.PAPER_PINK;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t6"))
        {
            Theme t = Theme.CLASSIC;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t7"))
        {
            Theme t = Theme.ALIEN;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t8"))
        {
            Theme t = Theme.ALIEN_2;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t9"))
        {
            Theme t = Theme.PASTEL;
            ((PlayScreen)playScreen).changeTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("t0"))
        {
            Theme t = Theme.MURDER;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u1"))
        {
            Theme t = Theme.MIDNIGHT_PURPLE;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u2"))
        {
            Theme t = Theme.MONO_BLUE;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u3"))
        {
            Theme t = Theme.WHITE_NOT;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u4"))
        {
            Theme t = Theme.GRAY_NOT;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u5"))
        {
            Theme t = Theme.PAPER_PINK;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u6"))
        {
            Theme t = Theme.CLASSIC;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u7"))
        {
            Theme t = Theme.ALIEN;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u8"))
        {
            Theme t = Theme.ALIEN_2;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u9"))
        {
            Theme t = Theme.PASTEL;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
        }
        else if(s.equals("u0"))
        {
            Theme t = Theme.MURDER;
            ((PlayScreen)playScreen).changeUiTheme(t);
            response = t.toString().toLowerCase().replaceAll("_", " ");
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
