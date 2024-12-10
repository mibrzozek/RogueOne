package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.MainFrame;
import structures.Theme;

import javax.swing.*;
import java.util.ArrayList;

public class NEscapeScreen extends UIScreen
{
    ArrayList<String> options = new ArrayList<>();
    private transient JFrame main;

    public NEscapeScreen(Entity player, PlayScreen ps, JFrame main)
    {
        super(player, ps, main);

        Screen ns = ps;
        if(ns instanceof PlayScreen)
            this.ps = (PlayScreen)ns;

        options = new ArrayList<>();
        options.add("Resume");
        options.add("Save");
        options.add("Main menu");
        options.add("New theme");
        options.add("New dungeon");
        options.add("Set fullscreen");
        options.add("Show key guide");

        this.main = main;
        this.bw = 31;
        this.bx = 0;
        this.bh = options.size() + 2;
        this.by = 0;

        setList(options);
        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by +1 );
    }
    @Override
    public void select()
    {
            if(itemList.get(index).equals("Resume"))
            {
                this.exitSubScreen();
            }
            else if(itemList.get(index).equals("Save"))
            {
                if(ps != null)
                    ps.saveGame();
            }
            else if(itemList.get(index).equals("Main menu"))
            {
                if(ps != null)
                    ps.returnStartScreen();
            }
            else if(itemList.get(index).equals("New theme"))
            {
                if(ps != null)
                    ps.changeUiTheme(Theme.getRandom());
                    ps.changeTheme(Theme.getRandom());
            }
            else if(itemList.get(index).equals("New dungeon"))
            {
                ps.newDungeon();
            }
            else if(itemList.get(index).equals("Set fullscreen"))
            {
                ((MainFrame)main).setFullScreen();
            }
            else if(itemList.get(index).equals("Show key guide"))
            {
                this.subScreen = new KeyGuideScreen();
            }
    }
    @Override
    public void render(AsciiPanel terminal)
    {
        int x = bx + 1;
        int y = by + 1;

        for(String i : itemList)
            terminal.write(i,x, y++ );

        if(subScreen instanceof KeyGuideScreen)
            ((KeyGuideScreen)subScreen).displayOutput(terminal);
    }
}
