package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import items.Stash;
import structures.MainFrame;
import structures.StashScreen;
import wolrdbuilding.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NOptionsScreen extends UIScreen
{
    private Stash s;
    private List<String> options;
    private List<Item> items;
    private int iIndex;
    private StashScreen ss;

    public NOptionsScreen(World w, PlayScreen ps, JFrame main, Stash s, int iIndex, StashScreen ss)
    {
        super(w.getPlayer(), ps, main);
        this.s = s;
        this.ss = ss;

        this.items = s.getStash();
        this.iIndex = iIndex;

        options = new ArrayList<>();
        options.add("Inspect");
        options.add("Loot");
        options.add("Loot all");

        setList((ArrayList) options);

        this.bw = 30;
        this.bx = 31 + 30;
        this.bh = options.size() + 2;
        this.by = ((MainFrame) main).getDisplayHeight() - bh;

        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by + 1);

    }
    @Override
    public void render(AsciiPanel terminal)
    {
        int x = bx + 1;
        int y = by + 1;


        for (String i : itemList)
            terminal.write(i, x, y++);

        if(subScreen instanceof InspectScreen)
            ((InspectScreen)subScreen).displayOutput(terminal);


    }
    @Override
    public void select()
    {
        if(options.get(index).equals("Inspect"))
        {
            subScreen = new InspectScreen(items.get(iIndex));
            ss.update();
            setNull();
        }
        else if(options.get(index).equals("Loot"))
        {
            Item i = items.get(iIndex);
            player.inventory().add(i);
            s.getStash().remove(i);
            ss.update();
            setNull();
        }
        else if(options.get(index).equals("Loot all"))
        {
            int size = items.size();

            System.out.println(player.inventory().getRoom() + " spots left in bag");

            if(items.size() > player.inventory().getRoom())
            {
                player.notify("Make room in your bag !");
                return;
            }
            for(Item i : items)
            {
                System.out.println(" looting all " + items.size());
                if(!player.inventory().isFull())
                    player.inventory().add(i);

                System.out.println(i.name() + " added");
            }

            s.getStash().clear();
            ss.update();
            setNull();
        }
    }
    @Override
    public void setNull()
    {
        exitSubScreen =  true;
    }

}

