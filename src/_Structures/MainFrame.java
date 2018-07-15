package _Structures;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Screens.InventoryScreen;
import Screens.Screen;
import Screens.StartScreen;
import WorldBuilding.Tile;
import WorldBuilding.World;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;


public class MainFrame extends JFrame implements KeyListener
{
	protected static final String Filepath = "D:\\06 SOURCE\\01 JAVA PROJECTS\\Rogue One\\resources\\icon.png";
	private AsciiPanel terminal;
	private AsciiPanel terminal2;
	
	private JPanel ControlPanel;
	
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
	 			ImageIcon img = new ImageIcon(Filepath);;
	 			KillingSmokesFrame.setIconImage((Image)img.getImage());
	 		}
	 	
       	});
	}
	public MainFrame()
	{
		super(" SDU");
		this.setSize(1036, 787);
		this.setResizable(true);
		// Starts app in center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		// Content Init
		contentPane = getContentPane();	
		//contentPane.add(ControlPanel, BorderLayout.SOUTH);
		contentPane.setSize(800, 825);
		contentPane.setVisible(true);
		
		
		terminal = new AsciiPanel(85, 75, AsciiFont.CP437_12x12);
		screen = new StartScreen(terminal);
		//invScreen = new InventoryScreen();
		
		terminal2 = new AsciiPanel();
		
		
		
		//invScreen = new InventoryScreen(terminal2);
		
		
		
		ControlPanel = new JPanel();
		ControlPanel.setSize(this.getPreferredSize());
		ControlPanel.setVisible(true);
		// Content Addition
		this.addKeyListener(this);
		this.add(terminal, BorderLayout.NORTH);
		this.add(terminal2, BorderLayout.CENTER);
		
		this.repaint();	
	}

    public void repaint()
    {
    	// Clear
        terminal.clear();
        terminal2.clear();
        
        screen.displayOutput(terminal);
        //invScreen.displayOutput(terminal2);

        super.repaint();
    }
	@Override


	public void keyPressed(KeyEvent e) 
	{
		// Sends key event to screen to assess event and 
		// returns and assigns a screen type 
		// to the mainframe screen and repaints
		//invScreen = invScreen.respondToUserInput(e);
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
