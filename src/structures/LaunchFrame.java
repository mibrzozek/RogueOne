package structures;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screens.LaunchScreen;
import screens.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LaunchFrame extends JFrame implements KeyListener
{
    protected static final String FILE_PATH = "D:\\06 SOURCE\\01 JAVA PROJECTS\\Rogue One\\resources\\icon.png";

    private AsciiPanel terminal;
    private Screen screen;

    public LaunchFrame()
    {
        super("Pick resolution");

        this.setResizable(false);
        this.setSize(300, 300);

        JPanel j = new JPanel();
        Button b = new Button("Click me");
        j.add(b);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.terminal = new AsciiPanel(30, 30, AsciiFont.CP437_16x16);
        screen = new LaunchScreen(terminal, this);

        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);
        this.add(terminal);
        this.addKeyListener(this);

        screen = new LaunchScreen(terminal, this);

        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent key)
    {

    }

    @Override
    public void keyPressed(KeyEvent key)
    {
        screen = screen.respondToUserInput(key);
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e)
    {

    }
    public void repaint()
    {
        terminal.clear();

        if(screen == null)
            screen = new LaunchScreen(terminal, this);
        screen.displayOutput(terminal);

        super.repaint();
    }
}
