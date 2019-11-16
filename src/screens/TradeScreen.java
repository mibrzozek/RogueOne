package screens;

import java.awt.Color;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import wolrdbuilding.Palette;


/*
public class TradeScreen extends singleListScrollingBasedScreen
{
	private int lastY = 0;
	private boolean moreToDisplay = false;
	private ArrayList<String> itemCost = new ArrayList<>();
	
	public TradeScreen(Entity player, Entity other, AsciiPanel terminal)
	{
		super(player, other, terminal);
		
	}

	public void write(AsciiPanel terminal)
	{
		renderDialog(terminal);
		renderTradersItems(terminal);

		terminal.write((char) 16 + "" +  equipIndex , scrollX, scrollY+8, Color.GREEN);
	
		terminal.write(player.crypto() + "", rx+ boxWidth -10, ry+ boxHeight-1, Color.GREEN);
		
		if(OptionScreen!= null)
			OptionScreen.displayOutput(terminal);
	}
	public void selectItem()
	{
	    if(equipIndex >= 0 )
	    {
	    	if(other.inventory().get(equipIndex) != null)
	    	{
	    		isSelected = true;
	    		OptionScreen = new  OptionsScreen(rx+boxWidth, ry, equipIndex, terminal, player, other);
	    		isSelected = false;
	    	}
	    }
	}
	public void renderTradersItems(AsciiPanel terminal)
	{
		ArrayList<String> lines = getList(other.inventory().getItems(), true);
		itemCost = getList(other.inventory().getItems(), false);
    	
    	
    	int limit = 10;
        int x = rx+2;
        int y = lastY+2;;
    	
    	if(moreToDisplay && equipIndex-9 >= 0)
    	{
    		lines = updateList(equipIndex-9, true);
    		itemCost = updateList(equipIndex-9, false);
    	}
    	
        for (int i = 0; i < limit; i++)	
        {
            terminal.write(lines.get(i), x, y, Color.white);
            terminal.write(itemCost.get(i).toString(), x + 30, y++, Color.white);
        }
	}
    private ArrayList<String> updateList(int startingIndex, boolean updateName)
    {
    	Item[] items = other.inventory().getItems();
    	
    	ArrayList<String> toDisplay = new ArrayList<>();
    	
    	int counter =  startingIndex +1;
    	
        if(updateName)
        {
        	for(int i = 0; i <= 9; i++)
        	{
        		if(items[startingIndex] != null)
        			toDisplay.add(" " + (char)175 + items[startingIndex].name());
        		else
        			toDisplay.add("+"); 
        		startingIndex++;
        		
        	}
        	return toDisplay;
        }
        else
        {
        	for(int i = 0; i <= 9; i++)
        	{
        		if(items[startingIndex] != null)
        		{
        			Integer ints = items[startingIndex].value();
        			toDisplay.add(ints.toString());
        		
        		}
        		else
        			toDisplay.add("+"); 
        		startingIndex++;
        		
        	}
        	return toDisplay;
        }
	}
   
    private ArrayList<String> getList(Item[] list, boolean updateName) 
    {
        ArrayList<String> itemName = new ArrayList<String>();
        ArrayList<String> itemCost = new ArrayList<String>();
        if(updateName)
        {
        	for (int i = 0; i < list.length; i++)
        	{
        		Item item = list[i];
        		String line;
        		String cost = "+";

        		if(item != null)
        		{
        			line = " " +(char)175 +  item.name();
        			Integer ints = (Integer) item.value();
        			cost = ints.toString();
        		}
        		else 
        			line = "+";
        	
        		itemName.add(line);
        		itemCost.add(cost);
        	}
        	return itemName;
        }
        else
        {
        	for (int i = 0; i < list.length; i++)
        	{
        		Item item = list[i];
        		String cost = "+";

        		if(item != null)
        		{
        			Integer ints = (Integer) item.value();
        			cost = ints.toString();
        		}
        		itemCost.add(cost);
        	}
        	return itemCost;
        }
    }
	public void renderDialog(AsciiPanel terminal)
	{
		String dialog = "Hello there stranger. You seem like a trustworthy guy, "
				+ " so let me tell you about something that might tickle your fancy."
				+ " A long time ago, in a glaxy far, far away, there lived this guy that"
				+ " liked to shoot things and sell stuff. That would be me. I like to"
				+ "  shoot things and sell stuff!";
		//int dy = 6;
		
		terminal.write(other.name(), rx+1, ry-1, Color.yellow);
		
		String[] split = dialog.split(" ");
		int count = 0;
	
		for (int y = ry+1; y < ry+22; y++)
		{	
			int charCount= 0;
			int cx = rx+1;
		
			while(charCount < 38  && count < split.length)
			{
				String word = split[count++];
				word += " ";
				charCount += word.length();
				terminal.write(word, cx, y);
				lastY=y;
				cx += word.length();
			}
		}
		for(int x = rx+1; x < rx+boxWidth-1; x++)
			terminal.write("-", x, lastY+1);
	}
    public void scrollDown()
    {	
    	if(equipIndex >= 9 && equipIndex <= other.inventory().getCapacity())
    	{
    		moreToDisplay = true;
    		if (equipIndex+1 < other.inventory().getCapacity())
    			equipIndex++;
    	}
    	else
    	{
    		moreToDisplay = false;
    		
    		if(ry - scrollY  < 20)
    			scrollY++;
    		equipIndex = scrollY-ry-1;
    	}
    }

    public void scrollUp()
    {	
    	if(equipIndex >= 10)
    	{
    		equipIndex--;
    	}
    	else
    	{
    		moreToDisplay = false;
    		
    		if(ry-scrollY + 1!= 0)
    			scrollY--;
    		equipIndex = scrollY-ry-1;

    	}
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