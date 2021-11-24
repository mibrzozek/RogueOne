package structures;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screens.Screen;
import screens.StartScreen;
import wolrdbuilding.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MainFrame extends JFrame implements KeyListener
{
	protected static final String FILE_PATH = "D:\\06 SOURCE\\01 JAVA PROJECTS\\Rogue One\\resources\\icon.png";

	private static final Color DEFAULT_BACK = Palette.theNewBlue;
	private static final Color DEFAULT_FORE = Palette.paleWhite;

	private Theme t = Theme.PASTEL;
	private AsciiPanel terminal;
	private Container contentPane;

	private int screenWidth = 130, screenHeight = 75,
			displayWidth = 130, displayHeight = 55;

	private Screen screen;

	private Resolution r;


	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				LaunchFrame launch = new LaunchFrame();
				launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				launch.setVisible(true);

				/*
				MainFrame main = new MainFrame();
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setVisible(true);
				ImageIcon img = new ImageIcon(FILE_PATH);
				main.setIconImage((Image) img.getImage());
				*/
			}
		});
	}

	public MainFrame(Resolution r)
	{
		super(" Working Title");
		this.setResizable(false);
		this.setSize(1366, 1022);

		// Starts app in center of screen
		setResolution(r);

		t = Theme.MIDNIGHT_PURPLE;

		terminal = new AsciiPanel(screenWidth, screenHeight, AsciiFont.CP437_16x16);
		screen = new StartScreen(terminal, this);

		terminal.setDefaultBackgroundColor(t.b);
		terminal.setDefaultForegroundColor(t.f);

		this.setVisible(true);
		this.addKeyListener(this);
		this.add(terminal);
		this.repaint();
	}
	public void setTheme(Theme t)
	{
		this.t = t;
	}
	public Theme getTheme()
	{
		return this.t;
	}
	public void repaint()
	{
		terminal.clear();
		screen.displayOutput(terminal);

		terminal.setDefaultForegroundColor(t.getFore());
		terminal.setDefaultBackgroundColor(t.getBack());

		super.repaint();
	}

	public Screen getScreen() {
		return screen;
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		screen = screen.respondToUserInput(e);
		repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {

	}
	@Override
	public void keyTyped(KeyEvent e) {

	}
	public int getScreenWidth()
	{
		return screenWidth;
	}
	public int getScreenHeight()
	{
		return screenHeight;
	}
	public int getDisplayWidth()
	{
		return displayWidth;
	}
	public int getDisplayHeight()
	{
		return displayHeight;
	}

	public void setFullScreen()
	{
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.screenWidth = 120;
		this.screenHeight = 68;

		this.displayWidth = 89;
		this.displayHeight = 55;

	}
	public void setResolution(Resolution res)
	{
		if(res.equals(Resolution.R_1080))
		{
			setFullScreen();
		}
		else if(res.equals(Resolution.R_720))
		{
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(0, 0);
			this.setSize(1280,720 );


			this.screenWidth = 80;
			this.screenHeight = 43;

			this.displayWidth = 80;
			this.displayHeight = 31;
		}
		else if(res.equals(Resolution.R_SQUARE))
		{
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - (this.getSize().width / 2), 0);
			this.setSize(1285,1038 );


			this.screenWidth = 80;
			this.screenHeight = 63;

			this.displayWidth = 80;
			this.displayHeight = screenHeight - 12;
		}

	}
}