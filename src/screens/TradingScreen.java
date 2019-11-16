package screens;
/*
import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import wolrdbuilding.Palette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TradingScreen extends ScrollingBasedScreen
{
	AsciiPanel terminal;

	public TradingScreen(Entity player, Entity other, AsciiPanel terminal)
	{
		super(player, other);
		this.terminal = terminal;
		
	}
	public void write(AsciiPanel terminal)
	{
		
		terminal.write("Traders Loot", rx, ry);
		terminal.write("Crypto :" + player.crypto(), rx, ry, Color.pink);
		
		renderItemList(terminal);
		displayOutput(terminal);
		
		terminal.repaint();
	}
	@Override
	public void selectItem()
	{
		if(scrollY - 3 >= 0)
    	{
    		if(other.inventory().get(equipIndex) != null)
    		{
    			isSelected = true;
    			OptionScreen = new  OptionsScreen( rx+17, ry, equipIndex, terminal, player, other);
    			isSelected = false;
    		}
    	}
	}
	public void renderItemList(AsciiPanel terminal)
    {
    	ArrayList<String> lines = getList(other.inventory().getItems());
    	
        int x = rx;
        int y = ry + 1;
        
        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());
    
    
        for (String line : lines)
        {
            terminal.write(line, x, y++, Color.white);
        }
        terminal.write(other.inventory().getItemCount() + "/" + other.inventory().getCapacity(), rx +12 , ry);
    }
    private ArrayList<String> getList(Item[] items) 
    {
        ArrayList<String> lines = new ArrayList<String>();
        
    
        for (int i = 0; i < items.length; i++)
        {
            Item item = items[i];
            String line;

        	if(item != null)
        		line = item.glyph() + " - " + item.name() + " : " + item.value();
        	else 
        		line = "...";
        	
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
*/