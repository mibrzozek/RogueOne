package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import Character.Entity;
import WorldBuilding.Tile;
import asciiPanel.AsciiPanel;

public abstract class singleListScrollingBasedScreen implements Screen
{
    protected Entity player;
    protected Entity other;
    
    protected AsciiPanel terminal;
    protected Screen OptionScreen = null;
    private Random r = new Random();
    
    protected int index;
    
    protected int scrollX;
    protected int scrollY;
    
	private int armXMax = 10;
	private int armYMax = 10;
	private int armXMin = 5;
	private int armYMin = 5;
	protected int boxWidth = 45;
	protected int boxHeight = 20;
	
	protected int rx;
	protected int ry;
	
	private boolean reRoll = true;
    protected boolean isSelected = false;
	public singleListScrollingBasedScreen(Entity player, Entity other)
	{
    	this.player = player;
    	this.other = other;
    	this.boxWidth = 45;
	}
	public singleListScrollingBasedScreen(Entity player, Entity other, AsciiPanel terminal)
	{
    	this.player = player;
    	this.other = other;
    	this.terminal = terminal;
	}
	public void displayOutput(AsciiPanel terminal)
	{
		renderArmAndBox(terminal);

	}
	public void renderArmAndBox(AsciiPanel terminal)
	{	
		if(reRoll)
		{
			rx = r.nextInt(armXMax) + armXMin;
			ry = r.nextInt(armYMax) + armYMin;
			scrollX  = rx+1;
			scrollY  = ry+1;
			reRoll = false;

	        
	        index = 0;
		}

		for(int i = 0; i < ry; i++)
		{
			terminal.write((char) 179, rx, i, Color.DARK_GRAY);
		}
    	for(int x = rx; x < rx+boxWidth; x++)
    	{
    		for(int y = ry; y < ry+boxHeight; y++)
    		{
    			
    			if(x == rx || x == rx + boxWidth-1)
    				terminal.write((char) 179, x, y, Color.WHITE);
				else if (y == ry || y == ry+boxHeight-1)
					terminal.write((char) 196, x, y,  Color.WHITE);
				else
					terminal.write(' ', x, y,  Color.WHITE);
    		}
    	}
    	terminal.write((char) 181, rx, ry);
    	terminal.write(Tile.trCorner.glyph(), rx+boxWidth-1, ry);
    	terminal.write(Tile.brCorner.glyph(), rx+boxWidth-1, ry+boxHeight-1);
    	terminal.write(Tile.blCorner.glyph(), rx, ry+boxHeight-1);

	}
    public void scrollLeft()
    {
    	scrollX = rx+1;
    	scrollY = ry+1;
    	index = 0;
    	isSelected = false;
    }
    public void scrollDown()
    {	

    	if(ry - scrollY + 1 == 20)
    		scrollY = ry;
    	else
    		scrollY++;
    	index = scrollY-ry- 1;
    }
    public void scrollUp()
    {	
   
    	if(scrollY - 1 == -1)
    		scrollY = ry+19;
    	else
    		scrollY--;
    	index = scrollY-ry -1;
    }
    public void selectItem()
    {

    }
    public Screen respondToUserInput(KeyEvent key) 
    {
        char c = key.getKeyChar();
        
        if(OptionScreen != null)
        {
        	OptionScreen = OptionScreen.respondToUserInput(key);
        }
        else
        {
        
        	if (key.getKeyCode() == KeyEvent.VK_R)
        		return null;
        	else if(key.getKeyCode() == KeyEvent.VK_DOWN)
        	{	
        		scrollDown();
        		return this;
        	}
        	else if(key.getKeyCode() == KeyEvent.VK_UP)
        	{	
        		scrollUp();
        		return this;
        	}
        	else if(key.getKeyCode() == KeyEvent.VK_LEFT)
        	{
        		if(scrollY -3 >= -1)
        		{
        			scrollLeft();
        			return this;
        		}
        		else if(scrollY - -1 >= -1)
        		{
        			scrollLeft();
        		
        			return this;
        		}
       
        	}
        	else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
        	{
        		if(!isSelected && index >= 0)
        		{
        			selectItem();
        			return this;
        		}
        		if(index < 0)
        		{
        			selectItem();
        
        		}
        	}
        	return this;
        }
        return this;
    }   
}
