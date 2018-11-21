package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inventory implements Serializable
{
	public enum EquipementSlot{HEAD, TORSO, ARMS, LEGS};
	
    private Item[] items;
    private Item[] equiped;
    private boolean fullyEquiped = false;
    
    private Random r = new Random();

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
    public ArrayList<Item> get(EquipementSlot slot)
    {
    	ArrayList<Item> specifiedItems =  new ArrayList<>();
    	
    	for(int i = 0; i < equiped.length; i++)
    	{
    		if(equiped[i] != null && equiped[i].type().	toString().equals(slot.toString()))
    			specifiedItems.add(equiped[i]);
    	}
    	
    	return specifiedItems;
    }
    public double getArmorNumber(EquipementSlot slot, double damage)
    {
    	double d = damage;
    	
    	System.out.println(d + " before filters applied.");
    	
    	if(slot == EquipementSlot.HEAD)
    		d = reduceDamage(get(slot), damage);
    	else if(slot == EquipementSlot.TORSO)
    		d = reduceDamage(get(slot), damage);
    	else if(slot == EquipementSlot.ARMS)
    		d = reduceDamage(get(slot), damage);
    	else if(slot == EquipementSlot.LEGS)
    		d = reduceDamage(get(slot), damage);
    	
    	System.out.println(d + " after filters applied..");
    	
    	return d;
    }
    public double reduceDamage(ArrayList<Item> slotItems, double damage)
    {
    	double d = damage;
    	for(Item i : slotItems)
    	{
    		d = (d +  ((i.defense()/(r.nextInt(3) +5))));
    		System.out.println(i.name() + " this is the item name" +  i.type().toString() + "is the slot");
    	}
    	return d;
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
