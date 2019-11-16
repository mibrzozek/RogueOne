package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Type;
import wolrdbuilding.Door;
import wolrdbuilding.Point;
import wolrdbuilding.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DoorScreen extends UIScreen
{
    World w;
    Point p;
    Door d;
    private JFrame main;

    public DoorScreen(World w, int bw, int bh, int bx, int by, PlayScreen ps, JFrame main)
    {
        super(w.getPlayer(), ps, main);
        this.w = w;
        this.p = w.getPlayer().getDoorPoint();
        this.d = w.getDoor(p);

        this.bw = bw;
        this.bx = bx;
        this.bh = bh;
        this.by = by;

        List<String> options = new ArrayList<>();

        options.add("Open");
        options.add("Close");

        setList((ArrayList<String>) options);
        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by +1 );
        setCursor(true);
    }
    @Override
    public void select()
    {
        if(itemList.get(index).equals("Open"))
        {
            if (player.inventory().getTypeDuration(Type.GOLD) > 0)
            {
                w.openDoor(p);
                player.notify("Looks like we have clearance Clarance.");
            }
            else
            {
                player.notify("You don't have the right clearance.");
            }
        }
        else if(itemList.get(index).equals("Close"))
        {
            w.closeDoor(p);
        }

    }
    @Override
    public void render(AsciiPanel terminal)
    {
        int x = bx + 1;
        int y = by + 1;
        //terminal.write(d.getClearance()+"", x, y-1);

        for(String i : itemList)
            terminal.write(i, x, y++ );
    }

}
