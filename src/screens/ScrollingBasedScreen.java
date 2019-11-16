package screens;

import java.awt.event.KeyEvent;
import java.util.Random;

import asciiPanel.AsciiPanel;
import entities.Entity;

public abstract class ScrollingBasedScreen implements Screen
{
	protected AsciiPanel terminal;
    protected Entity player;
    protected Entity other;
    
    protected Screen OptionScreen = null;
    private Random r = new Random();

	protected int invIndex;
    protected int equipIndex;
    
    protected int scrollX;
    protected int scrollY;
    
	private int armXMax = 10;
	private int armYMax = 10;
	
	private int armXMin = 5;
	private int armYMin = 5;
	
	private int boxWidth = 45;
	private int boxHeight = 20;
	
	protected int rx;
	protected int ry;
	
	private boolean reRoll = true;
	
    
    protected boolean isSelected = false;
    protected boolean selectingFromLeft = true;
    
    public ScrollingBasedScreen(Entity player, AsciiPanel terminal, boolean selectingFromLeft)
	{
    	this.player = player;
    	this.terminal = terminal;
    	this.selectingFromLeft = selectingFromLeft;	
	}
    
    public ScrollingBasedScreen(Entity player, Entity other)
    {
    	this.player = player;
    	this.other = other;
    }

    public void displayOutput(AsciiPanel terminal) 
    {
    	// renderArmAndBox(terminal);
    	
        // terminal.write(""+(char)254+(char)254 , scrollX, scrollY, AsciiPanel.brightGreen);
        
        if(OptionScreen != null)
        	OptionScreen.displayOutput(terminal);
        
        terminal.repaint();
    }
    /*
	public void renderArmAndBox(AsciiPanel terminal)
	{	
		if(reRoll)
		{
			rx = r.nextInt(armXMax) + armXMin;
			ry = r.nextInt(armYMax) + armYMin;
			scrollX  = rx-3;
			scrollY  = ry+1;
			reRoll = false;

	        
	        equipIndex = 0;
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
    				terminal.write((char) 179, x, y, Color.PINK);
				else if (y == ry || y == ry+boxHeight-1)
					terminal.write((char) 196, x, y,  Color.PINK);
				else
					terminal.write(' ', x, y,  Color.WHITE);
    		}
    	}
    	terminal.write((char) 181, rx, ry);
    	terminal.write(Tile.trCorner.glyph(), rx+boxWidth-1, ry);
    	terminal.write(Tile.brCorner.glyph(), rx+boxWidth-1, ry+boxHeight-1);
    	terminal.write(Tile.blCorner.glyph(), rx, ry+boxHeight-1);

	}
	*/
    public void scrollLeft()
    {
    	scrollX = rx;
    	scrollY = ry+1;
    	isSelected = false;
    	selectingFromLeft = true;
    
    }
    public void scrollDown()
    {	

    	if(ry - scrollY + 1 == 20)
    		scrollY = ry;
    	else
    		scrollY++;
    	equipIndex = scrollY-ry;
    }
    public void scrollUp()
    {	
   
    	if(scrollY - 1 == -1)
    		scrollY = ry+19;
    	else
    		scrollY--;
    	equipIndex = scrollY-ry;
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
        		if(scrollY -3 >= -1 && selectingFromLeft)
        		{
        			scrollLeft();
        			return this;
        		}
        		else if(scrollY - -1 >= -1 && !selectingFromLeft)
        		{
        			scrollLeft();
        			selectingFromLeft = true;
        			return this;
        		}
        		else
        			selectingFromLeft = true;
        	}
        	else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
        	{
        		if(!isSelected && equipIndex >= 0)
        		{
        			selectItem();
        			return this;
        		}
        		if(equipIndex < 0)
        		{
        			selectItem();
        			selectingFromLeft = false;
        		}
        	}
        	return this;
        }
        return this;
    }   
}
