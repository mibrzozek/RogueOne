package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import javax.swing.*;

public class UIScreen implements Screen
{
	private Screen subScreen;
	protected PlayScreen ps;
	private boolean exitGame;
	private boolean exitSubScreen;
	protected boolean cursorOn;
	private AsciiPanel terminal;
	
	protected Entity player;
	protected List<String> itemList;
	
	protected int index;
	private int scrollY;
	private int scrollX;

	protected int topBound;
	protected int bottomBound;
	protected int bw;
	protected int bh;
	protected int bx;
	protected int by;
	
	private TileSet ts;
	private JFrame main;
	
	public UIScreen(Entity player, PlayScreen ps, JFrame main)
	{
		this.main = main;
		this.ps = ps;
		this.player = player;
		this.index = 0;
		this.scrollX = 0;
		this.scrollY = 0;
		this.cursorOn = true;
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
	public void setTopBottomBounds(int top, int bottom)
	{
		this.topBound = top;
		this.bottomBound = bottom;
	}
	public void setTileSet(TileSet ts)
	{
		this.ts = ts;
	}
	public void setCursor(boolean onOff)
	{
		this.cursorOn = onOff;
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
		if(exitSubScreen ==  true)
			return;

		TileEngine.renderBox(terminal, bw, bh, bx, by, ts, true);
		render(terminal);
		terminal.write((char) 16, scrollX, scrollY);
	}
	public void render(AsciiPanel terminal)
	{
		
	}
	public void select()
	{
		
	}
	public void scrollUp()
	{
		if(cursorOn == true)
		{
			if(scrollY == topBound)
				scrollY = scrollY;
			else
			{
				scrollY--;
				index--;
			}
		}
		System.out.print("Scrolling");
	}
	public void scrollDown()
	{
		if(cursorOn == true)
		{
			if(scrollY == bottomBound)
				scrollY = scrollY;
			else
			{
				scrollY++;
				index++;
			}	
		}
	}
	public void setNull()
	{
		exitSubScreen =  true;
		ps.updateWorld();

	}
	@Override
	public Screen respondToUserInput(KeyEvent key)
	{
		if(this instanceof DoorScreen)
			System.out.println("This is a door screen bruh");

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
				case KeyEvent.VK_RIGHT:
							select();
							if(this instanceof DoorScreen)
								return null;
							break;
				
				case KeyEvent.VK_T:
				case KeyEvent.VK_LEFT:
					if(this instanceof DoorScreen)
						player.setShowUI(false);
					return null;
			
				case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(terminal, this); break;
	      		case KeyEvent.VK_ENTER:  break;
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
	private Color fore = Palette.paleWhite;
	private Color back = Palette.theNewBlue;
	@Override
	public Color getForeColor() {
		return fore;
	}

	@Override
	public Color getBackColor() {
		return back;
	}
}
