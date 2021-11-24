package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.ItemFactory;
import items.Type;
import structures.MainFrame;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EntityInteractScreen extends UIScreen
{
    private Entity e;
    private World w;
    private JFrame main;


    public EntityInteractScreen(World w, PlayScreen ps, JFrame main, Entity e)
    {
        super(w.getPlayer(), ps, main);
        this.main = main;
        this.w = w;
        this. e = e;
        List<String> options = new ArrayList<>();

        options.add("Fix");
        options.add("Destroy");
        options.add("Leave");
        setList((ArrayList<String>) options);

        this.bw = 10;
        this.bx = 21;
        this.bh = itemList.size() + 2;
        this.by = ((MainFrame)main).getDisplayHeight() - bh;

        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by +1 );
        setCursor(true);

    }
    @Override
    public void select()
    {
        if(itemList.get(index).equals("Fix"))
        {
            if(player.inventory().containsInInventory(new ItemFactory().newBasicAiUnit()))
            {
                player.notify("You place the missing AI unit into the AI assembly. It makes a satisfying click as it settles in place" +
                        ". You place the assembly into the droids head and hit the power button.");

                e.inventory().add(new ItemFactory().newBasicAiUnit());

                player.inventory().remove(new ItemFactory().newBasicAiUnit());
            }
        }
        else if(itemList.get(index).equals("Destroy"))
        {
            player.notify("You take your fists and give the broken droid a real beating. Ouch.");
            w.remove(e);
        }
        else if(itemList.get(index).equals("Leave"))
        {
            player.notify("The skeleton continues to collect dust.");
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

        Message m = new Message("You see a broken a beaten droid skeleton hunched over on the ground next to you." +
                "It's covered in dust and has it's AI assembly latch open. You identify it as a BRT model Mk5 NewMan Droid. It's one of the new ones", 30);

        int mx = bx + 10;
        int my = ((MainFrame)main).getDisplayHeight() - (int)m.numLines() -1;

        TileEngine.renderBox(terminal, 40, (int)m.numLines() + 1, mx, my, TileSet.SIMPLE, true);

        for(String s : m.lineList)
        {
            terminal.write(s, mx + 1, ++my, Palette.morePaleWhite);
        }
    }
}
