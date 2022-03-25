package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import items.Type;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class OptionsScreen extends ScrollingBasedScreen
{
	private int scrollX;
	private int scrollY;
	
	private int renderX;
	private int renderY;
	
	private int index;
	private int itemIndex;
	
	private List<Item> inventory;
	private List<Item> equipped;
	private List<Item> tradersItems;
	
	private String mode;
	private Screen inspectScreen = null;
	
	private boolean selectingFromInventory;
	private List optionList;
	// option for inventory, one entity, scrollPoint, itemIndex, terminal
	// options for trading
	// options for crafting
	// options for dialog
	
	// Inventory Options Constructor
	public OptionsScreen(Entity player, int scrollX, int scrollY, int itemIndex, boolean fromInventory, AsciiPanel terminal) {
		super(player, terminal, fromInventory);
		this.player = player;
		this.selectingFromInventory = fromInventory;
		this.scrollX = scrollX;
		this.scrollY = scrollY;
		renderX = scrollX;
		renderY = scrollY;
		this.itemIndex = itemIndex;

		inventory = player.inventory().getItems();
		equipped = player.inventory().getEquipped();
		tradersItems = null;

		optionList = new ArrayList<String>();
		optionList.add("Inspect");
		optionList.add("equipUnequip");
		optionList.add("Drop");
		optionList.add("Craft");

		if (selectingFromInventory)
		{
			if (inventory.get(itemIndex).type().equals(Type.APLASMA))
				optionList.add("Use");
			else if (inventory.get(itemIndex).type().equals(Type.CONSUMABLE))
				optionList.add("Eat");
			else if (inventory.get(itemIndex).type().equals(Type.VILE))
				optionList.add("Drink");
			else if (inventory.get(itemIndex).type().equals(Type.HEALING)
					|| inventory.get(itemIndex).type().equals(Type.HEAD_HEALING)
					|| inventory.get(itemIndex).type().equals(Type.PASSIVE_HEALING)
					|| inventory.get(itemIndex).type().equals(Type.FULL_HEAL)
					)
				optionList.add("Heal");
		}
		else
		{
			if (equipped.get(itemIndex).type().equals(Type.APLASMA))
				optionList.add("Use");
			else if (equipped.get(itemIndex).type().equals(Type.CONSUMABLE))
				optionList.add("Eat");
			else if (equipped.get(itemIndex).type().equals(Type.VILE))
				optionList.add("Drink");
			else if (equipped.get(itemIndex).type().equals(Type.HEALING)
					|| equipped.get(itemIndex).type().equals(Type.HEAD_HEALING)
					|| equipped.get(itemIndex).type().equals(Type.PASSIVE_HEALING)
					|| equipped.get(itemIndex).type().equals(Type.FULL_HEAL)
					)
				optionList.add("Heal");
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
		
	    inventory = player.inventory().getItems();
	    tradersItems = other.inventory().getItems();
	    
		optionList = new ArrayList<String>();
		optionList.add("Inspect");
		optionList.add("Buy");
		optionList.add("Trade");
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
		
	    inventory = player.inventory().getItems();

		optionList = new ArrayList<String>();
		optionList.add("Inspect");
		optionList.add("Craft");
		optionList.add("Modify");
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

		TileEngine.renderBox(terminal, 16, optionList.size() + 2, renderX, renderY, TileSet.SIMPLE, true);

		if(selectingFromInventory)
			optionList.set(1, "Equip");
		else
			optionList.set(1, "Unequip");
		//renderBackground(terminal);
		
		for(int i = 0; i < optionList.size(); i++)
		{
				terminal.write(""+ optionList.get(i), x, y++, Palette.morePaleWhite);
		}
		terminal.write((char)16 + "", scrollX, scrollY+1, Palette.morePaleWhite);
		
		if(inspectScreen != null)
			inspectScreen.displayOutput(terminal);
	}

    public void scrollDown()
    {	
  
    	if(scrollY + 1 != renderY+optionList.size())
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
						, itemIndex, renderX, renderY);
			}
			else
			{
				inspectScreen = new InspectScreen(player.inventory().getEquipped()
						, itemIndex, renderX, renderY);
			}
		}
		else
		{
			inspectScreen = new InspectScreen(other.inventory().getItems()
					, itemIndex, renderX, renderY);
		}

	}
	public void useItem()
	{
		//if(equiped[itemIndex]!= null)
			//System.out.println(equiped[itemIndex].type());
		
		if(selectingFromInventory)
		{
			inventory.get(itemIndex).useItemOn(player);
			player.inventory().remove(inventory.get(itemIndex));
		}
		else
		{
			equipped.get(itemIndex).useItemOn(player);
			//player.inventory().removeEquiped(equiped[itemIndex]);
		}	
		System.out.println(player.name());
	}
	public void equipUnequip()
	{
		if(selectingFromInventory)
		{
			player.inventory().moveToEquiped(itemIndex);
		}
		else
		{
			player.inventory().moveToInventory(itemIndex);
		}			
		player.updateStats();
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
						addToCraftingTable(inventory.get(index));
					
					return null;	
				}
				else if(index == 4 && index < optionList.size())
				{
					if(tradersItems == null)
					{	
						if(optionList.get(index).equals("Use")
							|| optionList.get(index).equals("Eat")
								|| optionList.get(index).equals("Heal"))
						{
							useItem();
						}
					}
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
