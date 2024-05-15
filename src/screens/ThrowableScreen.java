package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.Line;
import items.Item;
import items.Type;
import structures.MainFrame;
import wolrdbuilding.Palette;
import wolrdbuilding.Point;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThrowableScreen extends UIScreen
{
    private Screen subScreen;
    private int cursorX, cursorY, sh, sw, psx, psy;
    private List<Item> throwItems;

    public ThrowableScreen(Entity player, PlayScreen ps, JFrame main)
    {
        super(player, ps, main);

        throwItems = player.inventory().getEquipped().stream().filter(items -> items.type() == Type.UTILITY).collect(Collectors.toList());
        List<String> options = throwItems.stream().map(item -> item.name()).collect(Collectors.toList());
        setList((ArrayList) options);

        this.bw = 15;
        this.bx = 31;
        this.bh = options.size() + 2;
        this.by = ((MainFrame) main).getDisplayHeight() - bh;

        sw = ((MainFrame)main).getScreenWidth();
        sh = ((MainFrame)main).getScreenHeight();

        this.cursorX = (player.x - ps.getLeftOffset());
        this.cursorY = (player.y - ps.getTopOffset()) -3;

        this.psx = (player.x - ps.getLeftOffset());
        this.psy = (player.y - ps.getTopOffset());

        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by + 1);
    }

    @Override
    public void render(AsciiPanel terminal)
    {
        int x = bx + 1 ;
        int y = by + 1;

        for (String i : itemList)
            terminal.write(i, x, y++);

        terminal.write("X", cursorX + ps.getPlayAreaOffset(), cursorY, Palette.red);

        //List<Point> points = new Path(entity, target.x, target.y).points();

        Line l  = new Line(cursorX + ps.getPlayAreaOffset(), cursorY, psx + ps.getPlayAreaOffset(), psy);

        for(Point p : l.getPoints())
        {
            Point np = new Point(psx, psy, player.z);
            Point cp = new Point(cursorX, cursorY, player.z);

            if(!np.equals(p) && !cp.equals(p))
                terminal.write((char) 46, p.x, p.y, Palette.red);
        }
        terminal.write("X", cursorX + ps.getPlayAreaOffset(), cursorY, Palette.red);
    }
    @Override
    public void scrollUp()
    {
        if(cursorY - 1 > 0)
            cursorY--;
    }
    @Override
    public void scrollDown()
    {
        if(cursorY + 1 < sh)
            cursorY++;
    }
    public void scrollLeft()
    {
        if(cursorX - 1 > 0)
            cursorX--;
    }
    public void scrollRight()
    {
        if(cursorX + 1 < sw)
            cursorX++;
    }

    private void throwThrowable()
    {
        Item i = throwItems.get(0);
        i.setLocation(new Point(cursorX +ps.getLeftOffset(), cursorY + ps.getTopOffset(), 0));
        ps.getWorld().addThrowableItem(i);
        if(ps.getWorld().getPlayer().inventory().isItemEquiped(throwItems.get(0)))
        {
            ps.getWorld().getPlayer().inventory().removeEquiped(throwItems.get(0));
        }
    }
    @Override
    public Screen respondToUserInput(KeyEvent key)
    {

        if (subScreen != null)
        {
            subScreen = subScreen.respondToUserInput(key);
            if (subScreen instanceof EscapeScreen) {
                if (((EscapeScreen) subScreen).exit == true) {
                    subScreen = null;
                    ((EscapeScreen) subScreen).exit = false;
                }
            }
        } else
        {
            switch (key.getKeyCode())
            {
                case KeyEvent.VK_ENTER:
                    throwThrowable();
                    return null;
                case KeyEvent.VK_ESCAPE:
                    return null;
                case KeyEvent.VK_UP:
                    scrollUp();
                    break;
                case KeyEvent.VK_DOWN:
                    scrollDown();
                    break;
                case KeyEvent.VK_RIGHT:
                    scrollRight();
                    break;
                case KeyEvent.VK_LEFT:
                    scrollLeft();
                    break;

                case KeyEvent.VK_1:
                case KeyEvent.VK_2:
                case KeyEvent.VK_3:
                case KeyEvent.VK_4:
                case KeyEvent.VK_5:
                case KeyEvent.VK_6:
                case KeyEvent.VK_7:
                case KeyEvent.VK_8:
                case KeyEvent.VK_9:
                    char ascii = key.getKeyChar();
                    String as = new String(String.valueOf(ascii));
                    inputNumber = Integer.parseInt(as) - 1;
                    update();

            }
        }
        return this;
    }


}
