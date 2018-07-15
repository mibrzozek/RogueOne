package items;

import java.util.ArrayList;

import Character.Entity;

public class CraftingTable
{
	private ArrayList<Item> toCraft;
	private ArrayList<String> comboList;
	
	
	public CraftingTable()
	{
		toCraft = new ArrayList<>();
		comboList = new ArrayList<>();
		
		comboList.add("Stick of RAM");
		comboList.add("Animatronic Skeleton");
		comboList.add("Vile of Nanobots");
		comboList.add("Teddy Bear");
	}
	public void addItem(Item item)
	{
		toCraft.add(item);
	}
	public void checkForMatch(Entity player)
	{
		ItemFactory nf = new ItemFactory();
		
		int count = 0;
		
		if(toCraft.size() == comboList.size())
		{
			for(Item i : toCraft)
			{
				if(comboList.contains(i.name()))
					count++;
			}
		}
		if(count ==  4)
		{
			// HORAY
		}
		
	}
	
}
