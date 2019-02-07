package zunused;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import screens.OptionsScreen;
import screens.Screen;

public abstract class InventoryBasedScreen implements Screen 
{
	protected AsciiPanel terminal;
    protected Entity player;
    private static String letters;
    
    int invX = 27;
    int invY = 2;
    
    int statsX = 4;
    private Color[] colors;
    
    private int scrollY = invY;
    private int scrollX = invX-3;
    
    private int previousScollX;
    private int previousScrollY;
    
    private int index;
    private Screen OptionScreen = null;
   
    private boolean isSelected = false;
    private boolean selectingFromInventory = true;
    
    protected abstract String getVerb();
    protected abstract boolean isAcceptable(Item item);
    protected abstract Screen use(Item item);

    public InventoryBasedScreen(Entity player)
    {
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        
        colors = new Color[10];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.DARK_GRAY;
        colors[4] = Color.PINK;
        
    }
    public void displayOutput(AsciiPanel terminal) 
    {
        renderStats(terminal);
        renderItemList(terminal);
        renderEquipementList(terminal);
        
        if(OptionScreen != null)
        	OptionScreen.displayOutput(terminal);
  
        terminal.repaint();
    }
    public void renderEquipementList(AsciiPanel terminal)
    {
    	ArrayList<String> lines = getList(player.inventory().getEquipped());
    	
        int y = 3;
        int x = 50;
        
        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());
    
        for (String line : lines)
        {
            terminal.write(line, x, y++);
        }
        terminal.write("Equipment: ", 50, 2, Color.ORANGE);
    }
    public void renderItemList(AsciiPanel terminal)
    {
    	ArrayList<String> lines = getList(player.inventory().getItems());
    	
        int x = invX;
        int y = invY + 1;
        
        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());
    
        for (String line : lines)
        {
            terminal.write(line, x, y++, Color.white);
        }
        terminal.write("Inventory :", invX, invY, AsciiPanel.brightGreen);
        terminal.write(player.inventory().getItemCount() + "/" + player.inventory().getCapacity(), invX +12 , invY);
    }
    public void renderStats(AsciiPanel terminal)
    {	
    	// Stats
    	terminal.write("Attack: ", statsX, 3, AsciiPanel.white);
    	terminal.write(""+ player.attackValue(), statsX + 15, 3, Color.pink);
    	
    	terminal.write("Defense: ", statsX, 4, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 4, Color.pink );
    	
    	terminal.write("Vogue: ", statsX, 5, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 5, Color.pink );
    	
    	terminal.write("Complacency: ", statsX, 6, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 6, Color.pink );
    	
    	terminal.write("Attitude: ", statsX, 7, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 7, Color.pink );
    	
    	terminal.write("Pathology: ", statsX, 8, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 8 , Color.pink);
    	
    	terminal.write("Chill Level: ", statsX, 9, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 9, Color.pink );
    	
    	terminal.write("Bro Level: ", statsX, 10, AsciiPanel.white);
    	terminal.write(""+ player.defenseValue(), statsX + 15, 10 , Color.pink);
    	
    	//Inventory

        // Cursor
        terminal.write("-->" , scrollX, scrollY, AsciiPanel.brightGreen);
    }
    
    private ArrayList<String> getList(Item[] list) 
    {
        ArrayList<String> lines = new ArrayList<String>();
        
    
        for (int i = 0; i < list.length; i++)
        {
            Item item = list[i];
            String line;
            /*
            if (item == null || !isAcceptable(item))
                continue;
            */
        	if(item != null)
        		line = item.glyph() + " - " + item.name();
        	else 
        		line = "...";
        	
            lines.add(line);
        }
        return lines;
    }

    public void scrollLeft()
    {
    	scrollX = invX-3;
    	isSelected = false;
    
    }
    public void scrollDown()
    {	

    	if(scrollY + 1 == 24)
    		scrollY = 0;
    	else
    		scrollY++;
    	index = scrollY-3;
    }
    public void scrollUp()
    {	
   
    	if(scrollY - 1 == -1)
    		scrollY = 23;
    	else
    		scrollY--;
    	index = scrollY-3;
    }
    public void selectItem()
    {
    	if(scrollY - 3 >= 0)
    	{
    		if(selectingFromInventory)
    		{
    			if(player.inventory().get(scrollY - 3) != null)
    			{
    				isSelected = true;
    				OptionScreen = new  OptionsScreen(player, 63, 3, index, selectingFromInventory, terminal);
    				isSelected = false;
    			}
    		}
    		else
    		{
    			if(player.inventory().getEquipped(index) != null)
    			{
    				isSelected = true;
    				OptionScreen = new  OptionsScreen(player, 63, 3, index, selectingFromInventory, terminal);
    				isSelected = false;
    			}
    		}
    	}
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
        		if(scrollY -3 >= 0 && selectingFromInventory)
        		{
        			scrollLeft();
        			return this;
        		}
        		else if(scrollY - 3 >= -1 && !selectingFromInventory)
        		{
        			scrollLeft();
        			selectingFromInventory = true;
        			return this;
        		}
        		else
        			selectingFromInventory = true;
        	}
        	else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
        	{
        		if(!isSelected && scrollY - 3 >= 0)
        		{
        			selectItem();
        			return this;
        		}
        		if(scrollY - 3 < 0)
        		{
        			scrollX = 47;
        			selectingFromInventory = false;
        		}
        	}
        	return this;
        }
        return this;
    }
}