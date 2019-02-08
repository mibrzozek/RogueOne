package screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.Tile;
import wolrdbuilding.TileSet;

public class InventoryScreen extends ScrollingBasedScreen
{
	AsciiPanel terminal;
	private Random r = new Random();
	
	private boolean selectingFromInventory = true;
	private boolean moreToDisplay = false;
	
	private int armXMax = 10;
	private int armYMax = 10;
	
	private int armXMin = 5;
	private int armYMin = 5;
	
	private int boxWidth = 45;
	private int boxHeight = 20;
	
	private int rx;
	private int ry;
	
	// equipement offset
	private int eo = 24;
	
	private boolean reRoll = true;
	
	
	public InventoryScreen(Entity player, AsciiPanel terminal, boolean selectingFromInventory)
	{	
		super(player, terminal, selectingFromInventory);
	}
    public void write(AsciiPanel terminal) 
    {	

    	renderArmAndBox(terminal);
    	//renderGrayBackground(terminal);
		TileEngine.renderBox(terminal, 45, 3, rx, ry-2, TileSet.DOUBLE, Color.pink);
        renderItemList(terminal);
        renderEquipementList(terminal);
         
        System.out.println(
        		Arrays.asList(player.inventory().getEquipped())
        		);
        
        
        terminal.write(""+ (char) 16, scrollX, scrollY, Palette.red);

        if(OptionScreen != null)
        	OptionScreen.displayOutput(terminal);

  
        terminal.repaint();
    }
    @Override
    public void selectItem()
    {
    	System.out.println("sccrollY" + (scrollY-2));
     	if(index >= 0 )
    	{
    		if(selectingFromInventory)
    		{
    			if(player.inventory().get(index) != null)
    			{
    				isSelected = true;
    				OptionScreen = new  OptionsScreen(player, rx+boxWidth, ry, index, true, terminal);
    				isSelected = false;
    			}
    		}
    		else
    		{
    			if(player.inventory().getEquipped(index) != null)
    			{
    				isSelected = true;
    				OptionScreen = new  OptionsScreen(player, rx+boxWidth, ry, index, false, terminal);
    				isSelected = false;
    			}
    		}
    	}
     	else if(index < 0)
     	{
     		selectingFromInventory = false;
     		scrollX = rx +eo-1;
     	}
    }
    public void scrollLeft()
    {
    	scrollX = rx;
    	scrollY = ry;
    	index = -1;
    	isSelected = false;
    	selectingFromInventory = true;
    
    }
    public void scrollDown()
    {	
    	if(index >= 17 && index <= player.inventory().getCapacity() && selectingFromInventory)
    	{
    		moreToDisplay = true;
    		if (index+1 < player.inventory().getCapacity()) 
    			index++;
    	}
    	else
    	{
    		moreToDisplay = false;
    		
    		if(selectingFromInventory)
    		{
    			if(ry - scrollY  < 20)
    				scrollY++;
    			index = scrollY-ry-1;
    		}
    		else
    		{
    			if(index < 6)
    				scrollY++;
    			index = scrollY-ry-1;
    		}
    		
    	}
    }
    public void scrollUp()
    {	
    	if(index >= 17)
    	{
    		index--;
    	}
    	else
    	{
    		moreToDisplay = false;
    		
    		if(ry-scrollY != 0)
    			scrollY--;
    		index = scrollY-ry-1;

    	}
    }
    // Render
    
	public void renderArmAndBox(AsciiPanel terminal)
	{	
		if(reRoll)
		{
			rx = r.nextInt(armXMax) + armXMin;
			ry = r.nextInt(armYMax) + armYMin;
			scrollX  = rx;
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
    	
    	//terminal.write("Craft", rx+1, ry-1);
	}
	
    public void renderEquipementList(AsciiPanel terminal)
    {
    	ArrayList<String> lines = getList(player.inventory().getEquipped());
    	List<Item> equippedItems = Arrays.asList(player.inventory().getEquipped());
    	
        int x = rx+ eo;
        int y = ry+ 1;
        
        System.out.println(x + " " + y );
        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());
    
        System.out.println("Arrays as list size : " + equippedItems.size());
        for(Item i : equippedItems)
        {
        	if(i != null)
        	{
        		terminal.write("+" + Tile.INVENTORY_TYPE_ICON.glyph(), x, y, i.type().setColor(), Palette.darkestGray);
        		terminal.write(i.name());
        		y++;
        	}
        	else
        	{
        		terminal.write("+", x, y++, Palette.white, Palette.darkestGray);
        	}
        }
        
        
        terminal.write("  Equipment", rx + eo, ry-1);
    }
    public void renderItemList(AsciiPanel terminal)
    {
    	ArrayList<String> lines = getList(player.inventory().getItems());
    	List<Item> iL = player.inventory().getItemList();
    	
    	
    	int limit = 18;
        int x = rx+1;
        int y = ry+1;

    	if(moreToDisplay && index-17 >= 0)
    	{
    		lines = updateList(index-17);
    	}
    	
        for (int i = 0; i < limit; i++)	
        {
			terminal.write("+"+ Tile.INVENTORY_TYPE_ICON.glyph(), x, y);
            terminal.write(lines.get(i), Color.white);
            y++;
        }
        terminal.write("  Inventory", rx+1, ry-1);
        terminal.write(player.inventory().getItemCount() + "/" + player.inventory().getCapacity(), rx +boxWidth-5 , ry+boxHeight-1);

        //TileEngine.renderItemList(terminal, new ArrayList<Item>(player.inventory().getItemList()), x, y);
    }
    private ArrayList<String> updateList(int startingIndex)
    {
    	Item[] items = player.inventory().getItems();
    	ArrayList<String> toDisplay = new ArrayList<>();
    	
    	int counter =  startingIndex +1;
    	
    	for(int i = 0; i <= 17; i++)
    	{
    		if(items[startingIndex] != null)
    			toDisplay.add(items[startingIndex].name());
    		else
    			toDisplay.add("");
    		startingIndex++;
    		
    	}
    	return toDisplay;
    }
    private ArrayList<String> getList(Item[] list) 
    {
        ArrayList<String> lines = new ArrayList<String>();
        
    
        for (int i = 0; i < list.length; i++)
        {
            Item item = list[i];
            String line = "";
            /*
            if (item == null || !isAcceptable(item))
                continue;
            */
        	if(item != null)
        		line = item.name();
        	
            lines.add(line);
        }
        return lines;
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
