package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Character.Entity;
import WorldBuilding.Tile;
import asciiPanel.AsciiPanel;
import items.Item;

public class OptionsScreen extends ScrollingBasedScreen
{
	private int scrollX;
	private int scrollY;
	
	private int renderX;
	private int renderY;
	
	private int index;
	private int itemIndex;
	
	private static Item[] items;
	private static Item[] equiped;
	private static Item[] tradersItems;
	
	private String mode;
	private Screen inspectScreen = null;
	
	private boolean selectingFromInventory;
	private List list;
	// option for inventory, one entity, scrollPoint, itemIndex, terminal
	// options for trading
	// options for crafting
	// optionss for dialog
	
	// Inventory Options Constructor
	public OptionsScreen(Entity player, int scrollX, int scrollY, int itemIndex, boolean fromInventory, AsciiPanel terminal)
	{
		super(player, terminal, fromInventory);
		this.player = player;
		this.selectingFromInventory = fromInventory;
		this.scrollX = scrollX;
		this.scrollY = scrollY;
		renderX = scrollX;
		renderY = scrollY;
		this.itemIndex = itemIndex;
		
	    items = player.inventory().getItems();
	    equiped = player.inventory().getEquiped();
	    tradersItems = null;
		
	    list = new ArrayList<String>();
		list.add("Inspect");
		list.add("Use");
		list.add("Drop");
		list.add("Craft");

		if(selectingFromInventory)
		{
			if(items[itemIndex].usable())
				list.add("Use");
		}
		else
		{
			if(equiped[itemIndex].usable())
				list.add("Use");
		}
	}
	// Trading Options Constructor
	public OptionsScreen(int scrollX, int scrollY, int itemIndex, AsciiPanel terminal, Entity ... ents)
	{
		
		super(ents[0], terminal, true);
		this.other = ents[1];
		this.scrollX = scrollX;
		this.scrollY = scrollY;
		this.itemIndex = itemIndex;
		renderX = scrollX;
		renderY = scrollY;
		
	    items = player.inventory().getItems();
	    tradersItems = other.inventory().getItems();
	    
		list = new ArrayList<String>();
		list.add("Inspect");
		list.add("Buy");
		list.add("Trade");
	}
	// Crafting Options Screen Constructor
	public OptionsScreen(Entity player, int scrollX, int scrollY, int itemIndex, String s, AsciiPanel terminal)
	{
		super(player, terminal, true);
		this.player = player;
		this.scrollX = scrollX;
		this.scrollY = scrollY;
		this.itemIndex = itemIndex;
		renderX = scrollX;
		renderY = scrollY;
		
	    items = player.inventory().getItems();
		list = new ArrayList<String>();
		list.add("Inspect");
		list.add("Craft");
		list.add("Modify");
	}
	public void renderBackground(AsciiPanel terminal)
	{
		int rx = scrollX;
		int ry = scrollY+1;
		
		for(int y = ry; y < ry+1; y++)
		{
			for(int x = rx; x < rx + 16; x++)
			{
				terminal.write((char)(254), x, y, Color.GREEN);
			}
		}
	}

	public void displayOutput(AsciiPanel terminal)
	{
		int x = renderX+1;
		int y = renderY+1;
	
		renderBox(terminal);
		if(list.get(1).equals("Use"))
		{
			if(selectingFromInventory)
				list.set(1, "Equip");
			else
				list.set(1, "Unequip");
		}
		
		//renderBackground(terminal);
		
		for(int i = 0; i < list.size(); i++)
		{
				terminal.write(""+ list.get(i), x+1, y++ );
		}
		
		terminal.write((char)16 + "" +  index , scrollX+1, scrollY+1, AsciiPanel.brightGreen);
		
		if(inspectScreen != null)
			inspectScreen.displayOutput(terminal);
	}
	public void renderBox(AsciiPanel terminal)
	{
		int xo = 14;
		int yo = 5;
		
		for(int x = renderX; x <= renderX + xo; x++)
		{
			for(int y = renderY; y <= renderY +yo; y++)
			{
				if(x == renderX || x == renderX + xo)
    				terminal.write((char) 179, x, y, Color.CYAN);
				else if (y == renderY || y == renderY+yo)
					terminal.write((char) 196, x, y,  Color.CYAN);
				else
					terminal.write(' ', x, y,  Color.WHITE);
			}
		}
    	terminal.write((char) 194, renderX, renderY);
    	terminal.write((char) 194, renderX-1, renderY);
    	terminal.write(Tile.trCorner.glyph(), renderX+xo , renderY);
    	terminal.write(Tile.brCorner.glyph(), renderX+xo, renderY+yo);
    	terminal.write((char) 192, renderX, renderY + yo);
	}
    public void scrollDown()
    {	
  
    	if(scrollY + 1 != renderY+5)
    		scrollY++;
    	
    	index = scrollY - renderY;
    
    }
    public void scrollUp()
    {	
 
    	if(scrollY - 1 != renderY-1)
    		scrollY--;
    	
    	index = scrollY - renderY;
    }
	public void inspectItem()
	{
		if(tradersItems == null)
		{
			if(selectingFromInventory)
			{
				inspectScreen = new InspectScreen(player.inventory().getItems()
						, itemIndex, renderX-45, renderY);
			}
			else
			{
				inspectScreen = new InspectScreen(player.inventory().getEquiped()
						, itemIndex, renderX-45, renderY);
			}
		}
		else
		{
			inspectScreen = new InspectScreen(other.inventory().getItems()
					, itemIndex, renderX-45, renderY);
		}

	}
	public void useItem()
	{
		if(equiped[itemIndex]!= null)
			System.out.println(equiped[itemIndex].type());
		
		if(selectingFromInventory)
		{
			items[itemIndex].useItemOn(player);
			player.inventory().remove(items[itemIndex]);
		}
		else
		{
			equiped[itemIndex].useItemOn(player);
			//player.inventory().removeEquiped(equiped[itemIndex]);
		}	
		System.out.println(player.name());
	}
	public void equipUnequip()
	{
		System.out.println(""+ (scrollY -3));
		System.out.println(selectingFromInventory);
		if(selectingFromInventory)
		{
			player.equipItem(items[itemIndex]);
			player.inventory().moveToEquiped(itemIndex);
			
		}
		else
		{
			player.uniequipItem(equiped[itemIndex]);
			player.inventory().moveToInventory(itemIndex);
		}			
	}
	public void dropItem()
	{
		System.out.println(""+ (scrollY -2));
		player.drop(itemIndex, selectingFromInventory);
	}
	public void buyItem()
	{
		Item item = other.inventory().get(itemIndex);
		
		System.out.println("Hello " + item.value());
		
		if(player.crypto() > item.value())
		{
			player.modifyCrypto(-item.value());
			player.inventory().add(item);
			other.inventory().remove(item);
			player.notify(item.name() + " added to inventory");
		}
		else
			player.notify("Insufficient funds");
			
	}
	public void addToCraftingTable(Item item)
	{
		
	}
	public Screen respondToUserInput(KeyEvent key) 
	{

		if(inspectScreen != null)
		{
			inspectScreen = inspectScreen.respondToUserInput(key);
		}
		else
		{
			if (key.getKeyCode() == KeyEvent.VK_LEFT)	
			{
				return null;
			}
			else if(key.getKeyCode() == KeyEvent.VK_UP)		
			{
				scrollUp();
				return this;
			}
			else if(key.getKeyCode() == KeyEvent.VK_DOWN)
			{
				scrollDown();
				return this;
			}
			else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(index == 0)
				{
					inspectItem();
					return this;
				} 
				else if(index == 1)
				{
					if(tradersItems == null)
						equipUnequip();
					else
						buyItem();
					
					return null;
				}
				else if(index == 2)
				{
					if(tradersItems == null)
						dropItem();
					
					return null;	
				}
				else if(index == 3)
				{
					if(tradersItems == null)
						addToCraftingTable(items[index]);
					
					return null;	
				}
				else if(index == 4 && list.get(index).equals("Use"))
				{
					if(tradersItems == null)
						useItem();
					
					
					return null;
				}
				else 
					return this;
			}
			else
				return this;
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
