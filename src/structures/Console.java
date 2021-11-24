package structures;

import entities.Entity;
import items.ItemFactory;
import screens.PlayScreen;
import screens.Screen;
import wolrdbuilding.Point;
import wolrdbuilding.World;

import java.util.ArrayList;
import java.util.List;

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
                        iF.newBlueVisor(), iF.newTerrainMapper(), iF.newClearanceRed());
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
        else if(s.startsWith("tel")) // 'tel 0' teleports you to level 0
        {
            String[] args = s.split(" ");

            if(Integer.parseInt(args[1]) < world.depth())
            {
                player.modifyZLevel(Integer.parseInt(args[1]));
                response = "Teleported to level " + Integer.parseInt(args[1]);
            }
            else
            {
                response = "Failed to teleport";
            }
        }
        else if(s.startsWith("pos")) //  pos x y z
        {
            String args[] = s.split(" ");
            Point p = new Point(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));

            if(p.x < world.width() && p.y < world.height() && p.z < world.depth())
            {
                player.modifyCoordinates(p);
                response = "New player coordinates are " + p.toString();
            }
            else
            {
                response = "Invalid location";
            }
        }
        else if(s.startsWith("showPoints")) //  pos x y z
        {
            List<Point> sPoints = world.getStairPoints();
            for(Point p : sPoints)
            {
                response += p.x + " " + p.y + " " + p.z + " \n";
            }
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
