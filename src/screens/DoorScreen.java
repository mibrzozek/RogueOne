package screens;

import asciiPanel.AsciiPanel;
import items.Item;
import items.ItemFactory;
import items.Type;
import wolrdbuilding.Point;
import wolrdbuilding.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DoorScreen extends UIScreen
{
    World w;
    Point p;
    Door d;

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
        ItemFactory iF = new ItemFactory();
        boolean opened = false;
        Color c = Palette.paleWhite;


        if(itemList.get(index).equals("Open"))
        {
            if(d == null)
            {
                System.out.println("Door is null, i'm returning");
                return;
            }
            if (player.inventory().getTypeDuration(Type.GOLD) > 0
                && d.getClearance().equals(Door.Clearance.GOLD))
            {
                w.openDoor(p);
                player.notify("Looks like we have clearance Clarance.");
                opened = true;
                c = Palette.gray;
                Item i = player.inventory().getEquippedItem(iF.newClearanceGold());
                if(i != null)
                {
                    i.modifyValue(-1, player.inventory());
                }
            }
            else if(player.inventory().getTypeDuration(Type.RED) > 0
                    && d.getClearance().equals(Door.Clearance.RED))
            {
                w.openDoor(p);
                player.notify("Looks like we have clearance Clarance.");
                opened = true;
                c = Palette.red;
                Item i = player.inventory().getEquippedItem(iF.newClearanceRed());
                if(i != null)
                {
                    i.modifyValue(-1, player.inventory());
                }
            }
            else if(player.inventory().getTypeDuration(Type.GREEN) > 0
                    && d.getClearance().equals(Door.Clearance.GREEN))
            {
                w.openDoor(p);
                player.notify("Looks like we have clearance Clarance.");
                opened = true;
                c = Palette.lightGreen;
                Item i = player.inventory().getEquippedItem(iF.newClearanceGreen());
                if(i != null)
                {
                    i.modifyValue(-1, player.inventory());
                }
            }
            else if(player.inventory().getTypeDuration(Type.UNIQUE) > 0
                    && d.getClearance().equals(Door.Clearance.UNIQUE))
            {
                w.openDoor(p);
                player.notify("Looks like we have clearance Clarance.");
                opened = true;
                c = Palette.lightGreen;
                Item i = player.inventory().getEquippedItem(iF.newUniqueKey());
                if(i != null)
                {
                    i.modifyValue(-1, player.inventory());
                }
            }
            else
            {
                player.notify("You need " + d.getClearance().toString() + " clearance to get it.");
            }

            if(d.getRoom() != null && opened && !d.getRoom().isIdentified())
            {
                d.getRoom().setIdentified(true); // once the room is opened it is identified and therefore the
                                                 // wall color of the room does not have to get changed

                for(Point p : d.getRoom().getWallPoints())
                {
                    Tile t = w.getTile(p.x, p.y, p.z);
                    if(!t.isDoor())
                    {
                        w.getTileV(p.x, p.y, p.z).setTile(t.glyph(), c, t.backColor(), false);
                    }
                    else // open all doors if same clearance
                    {
                        if(d.getClearance().equals(w.getDoor(p).getClearance()))
                            w.openDoor(p);
                    }
                }
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
