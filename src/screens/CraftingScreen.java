package screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import wolrdbuilding.Tile;

public class CraftingScreen extends ScrollingBasedScreen
{
	
	private int armXMax = 10;
	private int armYMax = 10;
	
	private int armXMin = 5;
	private int armYMin = 5;
	
	private int boxWidth = 45;
	private int boxHeight = 20;
	
	private int rx;
	private int ry;
	
	private boolean reRoll = true;
	private boolean moreToDisplay = false;
	
	private Random r = new Random();
	
	

	public CraftingScreen(Entity player, AsciiPanel terminal, boolean selectingFromLeft)
	{
		super(player, terminal, selectingFromLeft);	

		// TODO Auto-generated constructor stub
	
	}
	public void write(AsciiPanel terminal)
	{
		if(reRoll)
		{
			rx = r.nextInt(armXMax) + armXMin;
			ry = r.nextInt(armYMax) + armYMin;
			scrollX  = rx-3;
			scrollY  = ry+1;
			reRoll = false;
		}
		terminal.write("-->", scrollX, scrollY);
		
		renderArmAndBox(terminal);
		renderItemList(terminal);
	
	}
    public void renderItemList(AsciiPanel terminal)
    {
    	ArrayList<String> lines = getList(player.inventory().getItems());
    	
    	
    	int limit = 18;
        int x = rx+1;
        int y = ry+1;
    	
    	if(moreToDisplay && index-17 >= 0)
    	{
    		lines = updateList(index-17);
    	}
    	
        for (int i = 0; i < limit; i++)	
        {
            terminal.write(lines.get(i), x, y++, Color.white);
        }
    }
    private ArrayList<String> getList(Item[] list) 
    {
        ArrayList<String> lines = new ArrayList<String>();
        
    
        for (int i = 0; i < list.length; i++)
        {
            Item item = list[i];
            String line;

        	if(item != null)
        		line = " " +(char)175 +  item.name();
        	else 
        		line = "+";
        	
            lines.add(line);
        }
        return lines;
    }
    private ArrayList<String> updateList(int startingIndex)
    {
    	Item[] items = player.inventory().getItems();
    	ArrayList<String> toDisplay = new ArrayList<>();
    	
    	int counter =  startingIndex +1;
    	
    	for(int i = 0; i <= 17; i++)
    	{
    		if(items[startingIndex] != null)
    			toDisplay.add(" " + (char)175 + items[startingIndex].name());
    		else
    			toDisplay.add("+"); 
    		startingIndex++;
    		
    	}
    	return toDisplay;
    }
	public void renderArmAndBox(AsciiPanel terminal)
	{	

		for(int i = 0; i < ry; i++)
		{
			terminal.write((char) 179, rx, i, Color.DARK_GRAY);
		}
    	for(int x = rx; x < rx+boxWidth; x++)
    	{
    		for(int y = ry; y < ry+boxHeight; y++)
    		{
    			
    			if(x == rx || x == rx + boxWidth-1)
    				terminal.write((char) 179, x, y, Color.CYAN);
				else if (y == ry || y == ry+boxHeight-1)
					terminal.write((char) 196, x, y,  Color.CYAN);
				else
					terminal.write(' ', x, y,  Color.WHITE);
    		}
    	}
    	terminal.write((char) 181, rx, ry);
    	terminal.write(Tile.trCorner.glyph(), rx+boxWidth-1, ry);
    	terminal.write(Tile.brCorner.glyph(), rx+boxWidth-1, ry+boxHeight-1);
    	terminal.write(Tile.blCorner.glyph(), rx, ry+boxHeight-1);
    	
    	terminal.write("Craft", rx+1, ry-1);
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
