package screens;

import asciiPanel.AsciiPanel;
import entities.Statistics;
import structures.MainFrame;
import structures.NameGenerator;
import structures.RexReader;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CharacterCreationScreen implements Screen
{
	private Color fore = Palette.paleWhite;
	private Color back = Palette.theNewBlue;

	private static final String DEFAULT_NAME = "Killing Smokes";
	private static final String DEFAULT_ROLE = "Naysayer";
	private transient AsciiPanel terminal;
	private Screen subScreen;
	private boolean exitGame = false;
	
	ArrayList<String> fields;
	
	private Statistics stats;
	private String customString = "";
	
	private int scrollX = 19;
	private int scrollY = 8;
	private int index = 0, sw, sh, center, bx, by, bw, bh, cx, cx2;
	
	ArrayList<TilePoint> m1;
	ArrayList<TilePoint> m2;
	ArrayList<TilePoint> m3;

	ArrayList<TilePoint> background;

	private JFrame main;

	private boolean rendered =  false;
	private NameGenerator nameGen;
	private static final String NAME_PATH = "C:\\006 SOURCE\\01 JAVA PROJECTS\\02 JAVA PROJECTS\\RogueOne\\resources\\lsv\\first_names.txt";
	private static final String NAME_PATH_1 = "C:\\006 SOURCE\\01 JAVA PROJECTS\\02 JAVA PROJECTS\\RogueOne\\resources\\lsv\\short_names.txt";
	
	public CharacterCreationScreen(MainFrame main)
	{
		this.main = main;

		fields = new ArrayList<String>();
		fields.add("Name");
		fields.add("Role");
		fields.add("Strength");
		fields.add("Dexterity");
		fields.add("Inteligence");
		fields.add("Charisma");
		fields.add("Traits");
		fields.add("Effects");
		fields.add("Skills");

		this.sw = main.getScreenWidth();
		this.sh = main.getScreenHeight();

		int paddingX = 20;
		int paddingY = 4;

		if(sw > 100)
		{
			paddingX = 60;
		}
		this.center = sw / 2;
		this.bx = center - ((sw - paddingX)/2);
		this.by = paddingY;
		this.bw = sw - paddingX;
		this.bh = sh - (paddingY*2);

		this.cx = 0;
		this.cx2 = bx + 2;
		this.scrollX = cx2-1;

		nameGen = new NameGenerator();
		nameGen.setFileToUse(NAME_PATH_1);
		
		stats = new Statistics();
		stats.setName(nameGen.getRandomName());
		customString = stats.getName();

		RexReader rex = new RexReader();
		background = new ArrayList<>();
		background = (ArrayList<TilePoint>) rex.getStructures().get("ascciWorld.csv");
	}
	@Override
	public void displayOutput(AsciiPanel terminal)
	{

		if(!rendered)
		{
			((MainFrame)main).getScreenWidth();
			m1 = TileEngine.renderBox(terminal, ((MainFrame)main).getScreenWidth(),  ((MainFrame)main).getScreenHeight(), 0, 0, TileSet.SIMPLE);
			rendered = true;
		}
		
		if(rendered)
		{
			TileEngine.sparkleAnime(m1);
			TileEngine.renderBox(terminal, bw, bh, bx, by, TileSet.SIMPLE);
			//TileEngine.displayTilesWithTransparentBox(terminal, m1, 40, ((MainFrame)main).getScreenHeight() - 15, 8, 5, Palette.darkestGray);
			
			//TileEngine.renderBox(terminal, 20, 20, 10, 6, null);
			//TileEngine.renderBox(terminal, 70, 50, 7, 6, null);
			//TileEngine.renderBox(terminal, 20, 3, 10, 5, null);
		}
		String title = "Character Creation";
		cx = center - (title.length() / 2);

		terminal.write(title, cx, 6, Palette.lightRed);
		
		ArrayList display = stats.displayStats();
		
		terminal.write(stats.getPoints() + "", cx2, 6);
		for(int i = 0; i < fields.size(); i++) // Displays all the fields
		{
			int y = 8+i;
			
			terminal.write(fields.get(i), cx2, y);
			terminal.write("", cx,  y);
			
			if(display.get(i) != null)
				terminal.write(display.get(i).toString(), cx, y);
			
			if(y == 11)
				y++;
		}

		// Story synopsis

		String story = "Here goes the story. It is very interesting. Enjoy it";

		Message m = new Message(story, bw - 6);
		ArrayList<String> print = m.getLines();

		int ys = 20;
		int xs = cx2;

		for(String s : print)
		{
			terminal.write(s, xs, ys++);
		}
			
		terminal.write((char)175 + "", scrollX, scrollY, Palette.lightRed); // Cursor

		if(customString != null)
		{
			stats.setAttribute(index, customString);
			customString = null;
		}
		if(subScreen instanceof KeyInputScreen)
	    	 ((KeyInputScreen) subScreen).displayOutput(terminal);
		
		if(subScreen instanceof EscapeScreen)
	    {
			((EscapeScreen) subScreen).displayOutput(terminal);
	    	((EscapeScreen) subScreen).write(terminal); 
	    }
		if(subScreen instanceof AnimationScreen) {
			((AnimationScreen) subScreen).displayOutput(terminal);
		}
		
	}
	public void setCustomString(String s)
	{
		this.customString = s;
	}
	public void setName(String s)
	{
		this.stats.setName(s);
	}
    public void returnStartScreen()
    {
    	exitGame = true;
    }
    
    public void scrollUp()
    {
    	if(scrollY -1  > 7)
    	{
    		scrollY -=1;
    		index--;
    	}
    }
    public void scrollDown()
    {
    	if(scrollY + 1 < 9 + fields.size())
    	{
    		scrollY += 1;
    		index++;
    	}
    }
    public void selectItem()
    {
    	if(index == 0 || index == 1)
    	{
    		subScreen = new KeyInputScreen(terminal, this, 30, cx, scrollY, null);
    	}
    	else if(index == 2 || index == 3 || index == 4 || index == 5)
    	{
    		subScreen = new KeyInputScreen(terminal, this, cx, scrollY);
    	}
    	else if(index == 6)
    	{
    		
    	}
    	else if(index == 7)
    	{
    		
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
				case KeyEvent.VK_UP: scrollUp(); break;
				case KeyEvent.VK_DOWN: scrollDown(); break;
				case KeyEvent.VK_RIGHT: selectItem(); break;
				case KeyEvent.VK_LEFT: break;

				case KeyEvent.VK_CAPS_LOCK: getNewRandomName(); break;
				case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(terminal, this); break;
	      		case KeyEvent.VK_ENTER: selectItem(); break;
	      		case KeyEvent.VK_BACK_SPACE:
	      		{
	      			if(stats.getName() == null || stats.getRole() == null)
	      			{
	      				stats.setName(DEFAULT_NAME);
	      				stats.setRole(DEFAULT_ROLE);
	      			}
	      			return new PlayScreen(stats, (MainFrame) main, World.Map.DUNGEON);
	      		}
	      	}
		}
		if(exitGame)
		{
			exitGame = false;
			return new StartScreen(terminal, main);
		}
		return this;
	}
	@Override
	public Screen returnScreen(Screen screen)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Color getForeColor() {
		return fore;
	}

	@Override
	public Color getBackColor() {
		return back;
	}

	public void getNewRandomName() {
		this.stats.setName(nameGen.getRandomName());
	}
}
