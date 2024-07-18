package screens;

import asciiPanel.AsciiPanel;
import items.Item;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.Tile;
import wolrdbuilding.TileSet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;


public class InspectScreen implements Screen
{
	private Item item;
	private int rx;
	private int ry;
	private int X = 4;
	private int Y = 2;
	private int bw = 31;
	private int bh = 19;
	
	//Item[] items;

	List<Item> inventory;

	
	int index;
	
	private String title;
	private String description;
	
	public InspectScreen(Item item)
	{
		this.item = item;
		rx = 4;
		ry = 2;
	}
	public InspectScreen(List<Item> inv, int index, int rx, int ry)
	{
		this.inventory = inv;
		this.index = index;
		this.rx = rx;
		this.ry = ry;
		
	}
	public InspectScreen(String title, String description)
	{
		this.title = title;
		this.description = description;
		rx = 4;
		ry = 2;
	}
	public InspectScreen()
	{
		this.title = "Inspect";
		rx = 15;
		ry = 5;
	}
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		//renderGrayBackground(terminal);
		TileEngine.renderBox(terminal, bw, bh, rx, ry+1, TileSet.SIMPLE, true);

		if(inventory != null)
			item = inventory.get(index);
			
		renderTitleDescription(terminal, item);

		terminal.repaint();
	}
	public void renderTitleDescription(AsciiPanel terminal, Item item)
	{
		if(item != null)
		{
			TileEngine.renderBox(terminal, bw, 5, rx, ry, TileSet.SIMPLE, true);

			title = item.name();
			description = item.description();
			int bx = rx + 1;
			int by = ry + 1;
			//terminal.write(title, bx, by, Palette.white);
			//TileEngine.renderItemPlate(terminal, bx, by, item, 43);
			TileEngine.renderDisplayPlate(terminal, bx, by++, bw-2, item.name(), false, Palette.darkestGray, item.color());
			TileEngine.renderDisplayPlate(terminal, bx, by++, bw-2, "" + item.value(), false, Palette.darkestGray, item.color());
			TileEngine.renderDisplayPlate(terminal, bx, by, bw-2, "" + item.type(), false, Palette.darkestGray, item.color());
			//terminal.write(item.type().toString() + " : " + item.value(), bx + 23, by, item.type().setColor());
		
			String[] split = description.split(" ");
			int count = 0;

			TileEngine.renderBox(terminal, bw, 15, rx, ry+ 5, TileSet.SIMPLE, true);

			for (int y = ry+6; y < ry+22; y++)
			{	
				int charCount= 0;
				int cx = rx+1;

				String word = "";

				if(count <  split.length)
					word = split[count];
		
				while(charCount + word.length() < bw -2  && count < split.length)
				{
					word = split[count++];
					word += " ";
					charCount += word.length();
					terminal.write(word, cx, y, Palette.paleWhite);
					
					cx += word.length();
				}
			}	
		}
	}
	public void renderGrayBackground(AsciiPanel terminal)
	{
		Message msg;
		int gx = 0 , gy = 0;
		if(item != null)
		{
			msg = new Message(item.description(), 39);
			gx = 0;
			gy = msg.getMsgLineCount() + 3;
		
			for(int y = ry+1; y < ry+gy; y++)
			{
				for(int x = rx+1; x < rx+47; x++)
				{
					if(x == rx+1)
	    				terminal.write((char) 179, x, y, Palette.white, Palette.darkGray);
					else if (y == ry+1 || y == ry+21-1)
						terminal.write((char) 196, x, y, Palette.white, Palette.darkGray);
					else
						terminal.write(Tile.WHITE_TERRAIN.glyph(), x, y,  Color.WHITE, Color.WHITE);
					/*
					 
					 */
				}
			}
		}
	}
    public void scrollDown()
    {	
    	if(!inventory.isEmpty())
    	{
    		if(index + 1 == inventory.size())
    			index = 0;
    		else
    			index++;
    	}
    }
    public void scrollUp()
    {	
    	if(!inventory.isEmpty())
    	{
    		if(index - 1 == -1)
    			index = inventory.size() - 1;
    		else
    			index--;
    	}
    }

	@Override
	public Screen respondToUserInput(KeyEvent key)
	{
		switch (key.getKeyCode())
		{
			// Special Keys
    		case KeyEvent.VK_LEFT: 
    		case KeyEvent.VK_C: 
    			return null; 
    		case KeyEvent.VK_DOWN: scrollDown(); break;
    		case KeyEvent.VK_UP: scrollUp(); break;
    		
    		
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
