package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

public class UIScreen implements Screen
{
	private Screen subScreen;
	protected PlayScreen ps;
	private boolean exitGame;
	private boolean exitSubScreen;
	private AsciiPanel terminal;
	
	private Entity player;
	protected List<String> itemList;
	
	protected int index;
	private int scrollY;
	private int scrollX;
	protected int bw;
	protected int bh;
	protected int bx;
	protected int by;
	
	private TileSet ts;
	
	public UIScreen(Entity player, PlayScreen ps)
	{
		this.ps = ps;
		this.player = player;
		this.index = 0;
		this.scrollX = 0;
		this.scrollY = 0;
		
		this.ts = TileSet.SIMPLE;
	}
	public void setList(ArrayList<String> list)
	{
		this.itemList = list;
	}
	public void setBoxProperties(int bw, int bh, int bx, int by, TileSet ts)
	{
		this.bw = bw;
		this.bh = bh;
		this.bx = bx;
		this.by = by;
	}
	public void setScrollX(int x)
	{
		scrollX = x;
	}
	public void setScrollY(int y)
	{
		scrollY = y;
	}
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		TileEngine.renderBox(terminal, bw, bh, bx, by, ts);
		terminal.write((char) 16, scrollX, scrollY);
		
		render(terminal);
	}
	public void render(AsciiPanel terminal)
	{
		
	}
	public void select()
	{
		
	}
	public void scrollUp()
	{
		if(scrollY == (49 - itemList.size()))
			scrollY = scrollY;
		else
		{
			scrollY--;
			index--;
		}		
	}
	public void scrollDown()
	{
		if(scrollY == (48))
			scrollY = scrollY;
		else
		{
			scrollY++;
			index++;
		}	
	}
	public void setNull()
	{
		exitSubScreen =  true;
		ps.setSubScreenNull();

	}
	@Override
	public Screen respondToUserInput(KeyEvent key)
	{
		if(exitSubScreen)
		{
			return null;
		}
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
				case KeyEvent.VK_UP:  scrollUp(); break;
				case KeyEvent.VK_DOWN: scrollDown(); break;
				case KeyEvent.VK_RIGHT:	select(); break;
				
				case KeyEvent.VK_T:
				case KeyEvent.VK_LEFT: return null;
			
				case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(terminal, this); break;
	      		case KeyEvent.VK_ENTER:  break;
			}
		}
		if(exitGame)
		{
			exitGame = false;
			return new StartScreen();
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
