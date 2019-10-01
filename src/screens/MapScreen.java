package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.TileEngine;
import wolrdbuilding.*;

import java.awt.event.KeyEvent;

public class MapScreen implements Screen
{

    private int scrollX, scrollY, offsetX, offsetY;

    private World world;
    private Screen subScreen;
    private AsciiPanel terminal;

    private Entity player;
    private MiniMap mini;

    private TileV[][][] miniMap;

    public MapScreen(AsciiPanel t, World w)
    {
        this.mini = w.getMiniMap();
        this.terminal = t;
        this.world = w;
        this.miniMap = mini.getTileMiniMap();
        this.player = w.getPlayer();
        this.scrollX = 51/2;
        this.scrollY = 29/2;
        this.offsetX = mini.getPlayerPoint().x;
        this.offsetY = mini.getPlayerPoint().y;
    }


    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        int mw = 52;
        int mh = 29;
        int mx = 32;
        int my = 32;

        TileEngine.renderBox(terminal, mw + 2, mh + 2, mx-1, my-1, TileSet.SIMPLE, Palette.paleWhite);

        for(int x = 0; x < mw; x++)
        {
            for(int y = 0; y < mh; y++)
            {
                if(x+offsetX < mini.miniW() && y+offsetY < mini.miniH())
                    terminal.write(miniMap[x+offsetX][y+offsetY][0].getTile().glyph(), x + mx, y + my, Palette.darkerGray);
            }
        }

        terminal.write((char)179, mx+scrollX, my+scrollY, Palette.lightGreen);
        terminal.write((char)179, mx+scrollX, my+scrollY+2, Palette.lightGreen);
        terminal.write((char)196, mx+scrollX-1, my+scrollY+1, Palette.lightGreen);
        terminal.write((char)196, mx+scrollX+1, my+scrollY+1, Palette.lightGreen);
    }
    public void scroll(int x, int y)
    {
        //System.out.println("Scroll x : " +scrollX + " scroll Y : " + scrollY);
        //System.out.println("Ofsset x : " + offsetX + " offset y : " + offsetY);
        int sx = 25, sy = 15;


        //if(scrollX + x < 52 && scrollX + x > -1 && scrollY + y > -2 && scrollY + y < 28
              //  && offsetX == 0 && offsetY == 0)
        {
          //  scrollX += x;
          //  scrollY += y;
        }
        if(x > 0 && scrollX + x < sx && offsetX == 0)  // scroll x plus
        {
            scrollX += x;
        }
        else if(x < 0 && scrollX + x > -1 && offsetX == 0) // scroll x minus
        {
            scrollX += x;
        }
        else if(y > 0 && scrollY + y < sy && offsetY == 0) // scroll y plus
        {
            scrollY += y;
        }
        else if(y < 0 && scrollY + y > -2 && offsetY == 0) // scroll y minus
        {
            scrollY += y;
        }
        else if(x > 0 && offsetX + x < 65) // offset x plus
        {
            //System.out.println("\t Extra testing " + x + " and y " + y);
            offsetX += x;
        }
        else if(x < 0 && offsetX + x > -1) // offset x minus
        {
            //System.out.println("\t Extra testing " + x + " and y " + y);
            offsetX += x;
        }
        else if (y > 0 && offsetY + y < 65) // offset y plus
        {
            //System.out.println("\t Extra testing " + x + " and y " + y);
            offsetY += y;
        }
        else if(y < 0 && offsetY + y > -1) // offset y minus
        {
            //System.out.println("\t Extra testing " + x + " and y " + y);
            offsetY += y;
        }

    }
    @Override
    public Screen respondToUserInput(KeyEvent key)
    {
        if(subScreen != null)
        {
            subScreen = subScreen.respondToUserInput(key);
            if(subScreen instanceof EscapeScreen)
            {
                if(((EscapeScreen)subScreen).exit == true)
                {
                    subScreen = null;
                    ((EscapeScreen)subScreen).exit = false;
                }
            }
        }
        else
        {
            switch (key.getKeyCode())
            {
                case KeyEvent.VK_UP:    scroll(0, -1); break;
                case KeyEvent.VK_DOWN:  scroll(0, 1);break;
                case KeyEvent.VK_RIGHT: scroll(1, 0);break;
                case KeyEvent.VK_LEFT:  scroll(-1, 0); break;

                case KeyEvent.VK_SHIFT:
                case KeyEvent.VK_M: return null;

                case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(terminal, this); break;
                case KeyEvent.VK_ENTER:  break;
            }
        }
        return this;
    }

    @Override
    public Screen returnScreen(Screen screen) {
        return null;
    }

    @Override
    public void animate() {

    }
}
