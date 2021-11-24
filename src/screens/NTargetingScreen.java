package screens;

import entities.Entity;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NTargetingScreen extends UIScreen
{
    private List<Entity> inView;

    public NTargetingScreen(Entity player, PlayScreen ps, JFrame main)
    {
        super(player, ps, main);

        this.bw = bw;
        this.bx = bx;
        this.bh = bh;
        this.by = by;

        inView = player.fov().getEntities();

        inView.stream().map(entity -> entity.name()).collect(Collectors.toList());

        List<String> options = inView.stream().map(entity -> entity.name()).collect(Collectors.toList());

        setList((ArrayList<String>) options);
        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by +1 );
        setCursor(true);
    }
}
