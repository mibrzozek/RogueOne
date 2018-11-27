package allscreen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import items.Item;
import wolrdbuilding.Tile;



public class InspectScreen implements Screen
{
	private Item item;
	private int rx;
	private int ry;
	private int X = 4;
	private int Y = 2;
	
	Item[] items;

	
	int index;
	
	private String title;
	private String description;
	
	public InspectScreen(Item item)
	{
		this.item = item;
		this.items = null;
		rx = 4;
		ry = 2;
	}
	public InspectScreen(Item[] items, int index, int rx, int ry)
	{
		this.items = items;
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
		renderGrayBackground(terminal);
		
		if(items != null)
			item = items[index];
			
		renderTitleDescription(terminal, item);

		terminal.repaint();
	}
	public void renderTitleDescription(AsciiPanel terminal, Item item)
	{
		if(item != null)
		{	
			title = item.name();
			description = item.description();
			
			terminal.write(title, rx+1, ry+1, AsciiPanel.brightGreen);
		
		
			String[] split = description.split(" ");
			int count = 0;
		
			for (int y = ry+2; y < ry+22; y++)
			{	
				int charCount= 0;
				int cx = rx+2;
			
				while(charCount < 38  && count < split.length)
				{
					String word = split[count++];
					word += " ";
					charCount += word.length();
					terminal.write(word, cx, y);
				
					cx += word.length();
				}
			}	
		}
	}
	public void renderGrayBackground(AsciiPanel terminal)
	{
		for(int y = ry+1; y < ry+21; y++)
		{
			for(int x = rx+1; x < rx+47; x++)
			{
				if(x == rx+1 || x == rx + 47-1)
    				terminal.write((char) 179, x, y, Color.green);
				else if (y == ry+1 || y == ry+21-1)
					terminal.write((char) 196, x, y,  Color.green);
				else
					terminal.write(' ', x, y,  Color.WHITE);
			}
		}
	}
    public void scrollDown()
    {	
    	if(items !=null)
    	{
    		if(index + 1 == items.length)
    			index = 0;
    		else
    			index++;
    	}
    }
    public void scrollUp()
    {	
    	if(items !=null)
    	{
    		if(index - 1 == -1)
    			index = items.length - 1;
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
}
