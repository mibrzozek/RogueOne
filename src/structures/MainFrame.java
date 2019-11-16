package structures;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screens.Screen;
import screens.StartScreen;
import wolrdbuilding.Palette;
import wolrdbuilding.World;


public class MainFrame extends JFrame implements KeyListener 
{	
	protected static final String FILE_PATH = "D:\\06 SOURCE\\01 JAVA PROJECTS\\Rogue One\\resources\\icon.png";

	private static final Color DEFAULT_BACK = Palette.theNewBlue;
	private static final Color DEFAULT_FORE = Palette.paleWhite;

	private Theme t = Theme.PASTEL;
	
	private AsciiPanel terminal;
	
	private Container contentPane;
	
	private Screen screen;
	private static Screen invScreen;
	
	private World world;
	
	public static void main(String args[])
	{
	 	EventQueue.invokeLater(new Runnable()
       	{
	 		public void run()
	 		{
	 			MainFrame KillingSmokesFrame = new MainFrame();
	 			KillingSmokesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 			KillingSmokesFrame.setVisible(true);
	 			ImageIcon img = new ImageIcon(FILE_PATH);
	 			KillingSmokesFrame.setIconImage((Image)img.getImage());
	 		}
       	});
	}
	public MainFrame()
	{
		super(" Abandoned");
		this.setResizable(false);
		this.setSize(1366, 1022);

		// Starts app in center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//this.setLocation(0, 0);
		this.setLocation(dim.width/2-this.getSize().width/2, 0);

		t = Theme.MIDNIGHT_PURPLE;

		terminal = new AsciiPanel(85, 75, AsciiFont.CP437_16x16);
		screen = new StartScreen(terminal, this);
		
		terminal.setDefaultBackgroundColor(t.b);
		terminal.setDefaultForegroundColor(t.f);

		this.addKeyListener(this);
		this.add(terminal);
		this.repaint();
	}
	public void setTheme(Theme t)
	{
		this.t = t;
	}
    public void repaint()
    {
        terminal.clear();
        screen.displayOutput(terminal);
        terminal.setDefaultForegroundColor(t.getFore());
		terminal.setDefaultBackgroundColor(t.getBack());

        super.repaint();
    }
	
	public Screen getScreen()
	{
		return screen;
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
        screen = screen.respondToUserInput(e);
        repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
	}
}
