package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable
{

    private Item[] items;
    private Item[] equiped;
    private boolean fullyEquiped = false;

    public Inventory(int max)
    {
        items = new Item[max];
        equiped = new Item[7];
    }
    
    public Item[] getItems() { return items; }
    public Item[] getEquiped() { return equiped; }

    public Item getEquiped(int index) { return equiped[index]; }
    public boolean isFullyEquiped() { return fullyEquiped; }
    
    public Item get(int index) 
    {
    	if(index <= (items.length -1))
    			return items[index];
    	else
    		return null;
    }
    public boolean isItemEquiped(Item item)
    {
    	boolean contains = false;
    	
    	for(int i = 0; i < equiped.length; i++)
    	{
    		if(item.equals(equiped[i]))
    			contains = true;
    	}
    	return contains;
    }
    public boolean containsInInventory(Item item)
    {
    	boolean contains = false;
    	
    	for(int i = 0; i < items.length; i++)
    	{
    		if(item.equals(items[i]))
    			contains = true;
    	}
    	return contains;
    }
    public void moveToInventory(int index)
    {
    	for(int i = 0; i < items.length; i++)
    	{
    		if(items[i] == null)
    		{
    			items[i] = equiped[index];
    			removeEquiped(equiped[index]);
    		}
    		
    		if(items[items.length - 1] != null)
    			fullyEquiped = true;
    	}
    }
    public void moveToEquiped(int index)
    {
    	for(int i = 0; i < equiped.length; i++)
    	{
    		if(equiped[i] == null)
    		{
    			equiped[i] = items[index];
    			remove(items[index]);
    		}
    		if(equiped[equiped.length - 1] != null)
    			fullyEquiped = true;
    	}
    }
    public void add(Item ... toAdd)
    {
    	for( Item item : toAdd)
    	{
    		for (int i = 0; i < items.length; i++)
    		{
    			if (items[i] == null)
    			{
    				items[i] = item;
    				break;
    			}
    		}
    	}
    }
    public void removeEquiped(Item item)
    {
        for (int i = 0; i < equiped.length; i++)
        {
            if (equiped[i] == item)
            {
                 equiped[i] = null;
                 return;
            }
        }
    }
    public void remove(Item item)
    {
        for (int i = 0; i < items.length; i++)
        {
            if (items[i] == item)
            {
                 items[i] = null;
                 return;
            }
        }
    }
    public boolean isFull()
    {
        int size = 0;
        for (int i = 0; i < items.length; i++)
        {
            if (items[i] != null)
                 size++;
        }
        return size == items.length;
    }
    public int getCapacity()
    {
    	return items.length;
    }
    public int getItemCount()
    {
    	int itemCount = 0;;
    	for(int i = 0; i < getCapacity(); i++)
    	{
    		if(items[i] != null)
    			itemCount++;
    	}
    	return itemCount;
    }
	
	
}
