package structures;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import asciiPanel.TileTransformer;
import screens.InventoryScreen;
import screens.Screen;
import screens.StartScreen;
import wolrdbuilding.Palette;
import wolrdbuilding.Tile;
import wolrdbuilding.World;


public class MainFrame extends JFrame implements KeyListener 
{	
	protected static final String FILE_PATH = "D:\\06 SOURCE\\01 JAVA PROJECTS\\Rogue One\\resources\\icon.png";
	
	private static final Color DEFAULT_BACK = Palette.darkestGray;
	private static final Color DEFAULT_FORE = Palette.paleWhite;
	
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
		this.setSize(1036, 787);
		this.setResizable(true);
		// Starts app in center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		terminal = new AsciiPanel(85, 75, AsciiFont.CP437_12x12);
		screen = new StartScreen();
		
		terminal.setDefaultBackgroundColor(DEFAULT_BACK);
		terminal.setDefaultForegroundColor(DEFAULT_FORE);

		this.addKeyListener(this);
		this.add(terminal);
		this.repaint();
	}

    public void repaint()
    {
        terminal.clear();
        screen.displayOutput(terminal);
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
