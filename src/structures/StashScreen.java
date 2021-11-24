package structures;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import items.Stash;
import items.Type;
import screens.*;
import wolrdbuilding.Palette;
import wolrdbuilding.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StashScreen extends UIScreen {

    private Stash s;
    private List<Item> items;
    List<String> options;
    private World w;
    private JFrame main;

    public StashScreen(World w, PlayScreen ps, JFrame main, Stash s)
    {
        super(w.getPlayer(), ps, main);
        this.s = s;
        this.w = w;
        this.main = main;

        items = s.getStash();
        options = items.stream().map(item -> item.name()).collect(Collectors.toList());
        setList((ArrayList) options);

        System.out.println(options.size() + " size of stash!");

        this.bw = 30;
        this.bx = 31;
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
        TileEngine.renderDisplayPlate(terminal, 32, by, bw - 2, "Stash", true, main.getForeground(), main.getBackground());

        for(String i : itemList)
            terminal.write(i, x, y++);

        if(subScreen instanceof NOptionsScreen)
            ((NOptionsScreen)subScreen).displayOutput(terminal);

    }
    @Override
    public void select()
    {
        this.subScreen = new NOptionsScreen(w, ps, main, s, index, this);
    }
    @Override
    public void update()
    {
        items = s.getStash();
        System.out.println(items.size() +  " size of updates items in stash");
        options = items.stream().map(item -> item.name()).collect(Collectors.toList());
        setNull();
    }
}
