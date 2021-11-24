package screens;

import asciiPanel.AsciiPanel;
import structures.LaunchFrame;
import structures.MainFrame;
import structures.Resolution;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class LaunchScreen implements Screen
{
    private JFrame frame;
    private AsciiPanel terminal;
    private Resolution res;

    private Resolution[] values = Resolution.values();
    private int  resIndex = 0, resMax = values.length;

    protected static final String FILE_PATH = "D:\\06 SOURCE\\01 JAVA PROJECTS\\Rogue One\\resources\\icon.png";

    public LaunchScreen(AsciiPanel t, JFrame frame)
    {
        this.terminal = t;
        this.frame = frame;
        this.res = Resolution.R_1080;
    }
    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        TileEngine.renderBox(terminal, 18, 17, 0, 0, TileSet.SIMPLE, true);

        terminal.write("Cycle resolution", 1, 1);
        terminal.write("[SPACE]", 5, 2);
        TileEngine.renderDisplayPlate(terminal, 1, 3, 16, res.toString(), true, Palette.lightRed, Palette.morePaleWhite);

        terminal.write("[ENTER]  to play", 1, 15);
    }
    @Override
    public Screen respondToUserInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_SPACE:
                cycleResolution();
                break;
            case KeyEvent.VK_ENTER:
                launchGame(res);
                break;
        }
        System.out.println("hello");
        return this;
    }

    private void cycleResolution()
    {
        if(resIndex + 1 < resMax)
        {
            resIndex++;
        }
        else
            resIndex = 0;

        System.out.println(res + " " +  resIndex);

        res = values[resIndex];
        displayOutput(terminal);

    }

    private void launchGame(Resolution res)
    {
        frame.setVisible(false);

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
				MainFrame main = new MainFrame(res);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setVisible(true);
				ImageIcon img = new ImageIcon(FILE_PATH);
				main.setIconImage((Image) img.getImage());
            }
        });
    }

    @Override
    public Screen returnScreen(Screen screen) {
        return null;
    }

    @Override
    public void animate() {

    }

    @Override
    public Color getForeColor() {
        return null;
    }

    @Override
    public Color getBackColor() {
        return null;
    }
}
