package screens;

import asciiPanel.AsciiPanel;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyGuideScreen implements Screen
{
    public KeyGuideScreen()
    {

    }
    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        TileEngine.renderBox(terminal, 31, 17, 0, 9, TileSet.SIMPLE, Palette.blue);

        terminal.write("Movement Keys", 1, 10, Palette.blue);
        terminal.write("     arrow keys, wasd, numpad 78946123", 1, 11, Palette.blue);
        terminal.write("Rotate character clockwise : e", 1, 12, Palette.blue);
        terminal.write("Rotate character counter clockwise :  q", 1, 13, Palette.blue);
        terminal.write("Open inventory :  r", 1, 14, Palette.blue);
        terminal.write("Use device :  g", 1, 15, Palette.blue);
        terminal.write("Inspect item while standing on top of it :  c", 1, 16, Palette.blue);
        terminal.write("Interact :  f", 1, 17, Palette.blue);
        terminal.write("Show character sheet : SHIFT", 1, 18, Palette.blue);
        terminal.write("Shoot projectile weapon :  SPACE", 1, 19, Palette.blue);
        terminal.write("Choose target : 1-9", 1, 20, Palette.blue);

    }
    @Override
    public Screen respondToUserInput(KeyEvent key)
    {
        if(key.getKeyCode() == KeyEvent.VK_ESCAPE
            || key.getKeyCode() == KeyEvent.VK_LEFT)
            return null;
        else
            return this;
    }
    @Override
    public Screen returnScreen(Screen screen) {return null;}
    @Override
    public void animate() {}
    @Override
    public Color getForeColor() {
        return null;
    }

    @Override
    public Color getBackColor() {
        return null;
    }
}
