package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Inventory implements Serializable
{
	public enum EquipmentSlot {HEAD, TORSO, ARMS, LEGS, DEVICE, WEAPON_ONE, WEAPON_TWO};
	
    private Item[] items;
    private Item[] equipped;
    
    private List<Item> devices;
    private int deviceIndex = -1;
    
    private boolean fullyEquiped = false;
    
    private Random r = new Random();

    public Inventory(int max)
    {
        items = new Item[max];
        equipped = new Item[7];
    }
    
    public Item[] getItems() { return items; }
    public Item[] getEquipped() { return equipped; }

    public List<Item> getItemList() { return Arrays.asList(items);}
	public List<Item> getEquippedList() { return Arrays.asList(equipped);}

    public Item getEquipped(int index) { return equipped[index]; }
    public boolean isFullyEquiped() { return fullyEquiped; }
    
    public Item get(int index) 
    {
    	if(index <= (items.length -1))
    			return items[index];
    	else
    		return null;
    }
    public void cycleDevice()
    {
    	if(devices.isEmpty())
    		return;
    	else
    	{
    		if(deviceIndex + 1 == devices.size() || deviceIndex < 0)
    			deviceIndex = 0;
    		else
    			deviceIndex++;
    	}
    }
    public Item getDevice()
    {
    	devices = get(Type.DEVICE);
    	deviceIndex = 0;
    	
    	if(devices.isEmpty())
    		return null;
    	else
    	{
    		return devices.get(deviceIndex);
    	}
    }
    public ArrayList<Item> get(Type type)
    {
    	ArrayList<Item> specifiedItems =  new ArrayList<>();
    	
    	for(int i = 0; i < equipped.length; i++)
    	{
    		if(equipped[i] != null && equipped[i].type().equals(type))
    			specifiedItems.add(equipped[i]);
    	}
    	//System.out.println(specifiedItems.size() + " items on " + type.toString());
    	
    	return specifiedItems;
    }
    public double getGunDamage()
	{
		double dmg = 0;
		for(int i = 0; i < equipped.length; i++)
		{
			if(equipped[i] != null && equipped[i].type() == Type.GUN)
				dmg += equipped[i].value();
		}
		return dmg;
	}
    public double getStealthNumber()
    {
    	Stream str = get(Type.STEALTH).stream();
    	
    	double stealth = 0;
    	for(int i = 0; i < equipped.length; i++)
    	{
    		if(equipped[i] != null && equipped[i].type() == Type.STEALTH)
    			stealth += equipped[i].value();
    	}
    	
    	return stealth;
    }
    public double getArmorNumber(EquipmentSlot slot, double damage)
    {
    	double d = damage;
    	
    	System.out.println(d + " before filters applied.");
    	
    	if(slot == EquipmentSlot.HEAD)
    		d = reduceDamage(get(Type.HEAD), damage);
    	else if(slot == EquipmentSlot.TORSO)
    		d = reduceDamage(get(Type.TORSO), damage);
    	else if(slot == EquipmentSlot.ARMS)
    		d = reduceDamage(get(Type.ARMS), damage);
    	else if(slot == EquipmentSlot.LEGS)
    		d = reduceDamage(get(Type.LEGS), damage);
    	
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
    	
    	for(int i = 0; i < equipped.length; i++)
    	{
    		if(item.equals(equipped[i]))
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
    			items[i] = equipped[index];
    			removeEquiped(equipped[index]);
    		}
    		
    		if(items[items.length - 1] != null)
    			fullyEquiped = true;
    	}
    }
    public void moveToEquiped(int index)
    {
    	for(int i = 0; i < equipped.length; i++)
    	{
    		if(equipped[i] == null)
    		{
    			equipped[i] = items[index];
    			remove(items[index]);
    		}
    		if(equipped[equipped.length - 1] != null)
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
        for (int i = 0; i < equipped.length; i++)
        {
            if (equipped[i] == item)
            {
                 equipped[i] = null;
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
