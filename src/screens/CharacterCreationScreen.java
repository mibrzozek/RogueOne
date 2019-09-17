package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.EntityFactory;
import entities.FieldOfView;
import entities.Statistics;
import items.ItemFactory;
import structures.RexReader;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

public class CharacterCreationScreen implements Screen
{
	private static final String DEFAULT_NAME = "Killing Smokes";
	private static final String DEFAULT_ROLE = "Naysayer";
	private transient AsciiPanel terminal;
	private Screen subScreen;
	private boolean exitGame = false;
	
	ArrayList<String> fields;
	
	private Statistics stats;
	private String customString = "Press enter to type...";
	
	private int scrollX = 19;
	private int scrollY = 8;
	private int index = 0;
	
	ArrayList<TilePoint> m1;
	ArrayList<TilePoint> m2;
	ArrayList<TilePoint> m3;

	ArrayList<TilePoint> background;

	private boolean rendered =  false;
	
	public CharacterCreationScreen()
	{
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
		
		stats = new Statistics();
		RexReader rex = new RexReader();
		background = new ArrayList<>();
		background = (ArrayList<TilePoint>) rex.getStructures().get("ascciWorld.csv");
	}
	@Override
	public void displayOutput(AsciiPanel terminal)
	{

		if(!rendered)
		{
			m1 = TileEngine.renderBox(terminal, 85,  62, 0, 0, TileSet.SIMPLE);
			m2 = TileEngine.renderBox(terminal, 70, 50, 7, 6, TileSet.SIMPLE);
			m3 = TileEngine.renderBox(terminal, 20, 3, 10, 5, TileSet.SIMPLE);
			rendered = true;
		}
		
		if(rendered)
		{
			TileEngine.sparkleAnime(m1);
			TileEngine.renderBox(terminal, 50, 53, 18, 4, TileSet.SIMPLE);
			TileEngine.displayTilesWithTransparentBox(terminal, m1, 50, 53, 18, 5, Palette.darkestGray);
			
			//TileEngine.renderBox(terminal, 20, 20, 10, 6, null);
			//TileEngine.renderBox(terminal, 70, 50, 7, 6, null);
			//TileEngine.renderBox(terminal, 20, 3, 10, 5, null);
		}
		terminal.write("Character Creation", 34, 6, Palette.lightRed);
		
		ArrayList display = stats.displayStats();
		
		terminal.write(stats.getPoints() + "", 20, 6);
		for(int i = 0; i < fields.size(); i++) // Displays all the fields
		{
			int y = 8+i;
			
			terminal.write(fields.get(i), 20, y);
			terminal.write("", 32,  y);
			
			if(display.get(i) != null)
				terminal.write(display.get(i).toString(), 34, y);
			
			if(y == 11)
				y++;
		}

		// Story synopsis

		String story = "\tThe year is unknown. Your origin is unknown. Your earliest memories reach back to the day you were" +
				" given your mission; get of the uninhabitable Earth. What made the Earth uninhabitable is vague, at the most." +
				"The logs show this facility was constructed long after the events which damaged Earths magnetic field. " +
				"One thing is certain, the motion of the Earth has slowed and the gravity has decreased. The logs always mention" +
				" the 'Long War' as something partially responsible for the near extinction of humanity.\n" +
				"\t'Near extinciton', you always remember. You're still here. Sector 2994, operated by Laura, the only companion you've" +
				" had your entire life. You always thought there was another person on the other side of the screen when you talked" +
				" to her. ";

		Message m = new Message(story, 47);
		ArrayList<String> print = m.getLines();

		int ys = 20;
		int xs = 20;

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
    		subScreen = new KeyInputScreen(terminal, this, 30, 34, scrollY, null);
    	}
    	else if(index == 2 || index == 3 || index == 4 || index == 5)
    	{
    		subScreen = new KeyInputScreen(terminal, this, 34, scrollY);
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
			
				case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(terminal, this); break;
	      		case KeyEvent.VK_ENTER: selectItem(); break;
	      		case KeyEvent.VK_BACK_SPACE:
	      		{
	      			if(stats.getName() == null || stats.getRole() == null)
	      			{
	      				stats.setName(DEFAULT_NAME);
	      				stats.setRole(DEFAULT_ROLE);
	      			}
	      			return new PlayScreen(stats);
	      		}
	      	}
		}
		if(exitGame)
		{
			exitGame = false;
			return new StartScreen(terminal);
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

}
