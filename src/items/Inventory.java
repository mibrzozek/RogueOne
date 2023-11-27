package items;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable
{

	private Item primaryWeapon;

	public void checkCapacity()
	{
		this.max = (int) getTypeDuration(Type.INVENTORY)+ STARTING_INV_CAP;
		this.maxEquip = (int)getTypeDuration(Type.HELMET) + (int)getTypeDuration(Type.TORSO)
				+ (int)getTypeDuration(Type.ARMS) + (int)getTypeDuration(Type.LEGS) + STARTING_EQP_CAP;
	}

	public int getEquippedMax() {
		return maxEquip;
	}

    public int getRoom()
	{
        return getCapacity() - inventory.size();
    }

	public void clear()
	{
		inventory = new ArrayList<>();
		equipped = new ArrayList<>();

		inventoryMap = new HashMap<>();
		equippedMap = new HashMap<>();
	}

	public Item getEquippedItem(Item item)
	{
		System.out.println("invItem going in " + item.toString());

		if(equipped.contains(item))
		{
			System.out.println("Equipped contains item");
			for(Item i : equipped)
			{
				if(i.equals(item))
					return i;
			}
		}
		return null;
	}

    public boolean hasProjectileWeapon()
	{
		boolean truth = false;
		for(Item i : equipped)
		{
			if(i.isProjectileWeapon())
			{
				truth = true;
			}
		}
		return truth;
    }

	public Item getPrimaryWeapon()
	{
		return primaryWeapon;
	}

	public void setPrimaryWeapon(Item primary)
	{
		this.primaryWeapon = primary;
	}

	public enum EquipmentSlot {HEAD, TORSO, ARMS, LEGS, DEVICE, WEAPON_ONE, WEAPON_TWO, VISION};

	private Item[] equiped;
    private Item[] items;

    public static final int STARTING_INV_CAP = 10;
	public static final int STARTING_EQP_CAP = 6;


	private int max, maxEquip;


	private List<Item> inventory;
	private List<Item> equipped;

	private Map<Type, List<Item>> inventoryMap;
	private Map<Type, List<Item>> equippedMap;

    private List<Item> devices;
	private List<Item> opticalEnhancers;

    private int deviceIndex = -1;
    
    private boolean fullyEquiped = false;
    
    private Random r = new Random();

    public Inventory(int max)
    {
    	this.max = max;
    	this.maxEquip = STARTING_EQP_CAP;

        items = new Item[max];

        inventory = new ArrayList<>();
		equipped = new ArrayList<>();

        inventoryMap = new HashMap<>();
		equippedMap = new HashMap<>();
    }
    public ArrayList<Item> getItems() { return (ArrayList<Item>) inventory; }
    public ArrayList<Item> getEquipped() { return (ArrayList<Item>) equipped; }

    public Map getInventoryMap()
	{
		inventoryMap.clear();

		for(Item i :  inventory)
		{
			if(inventoryMap.containsKey(i.type()))
			{
				List<Item> list = inventoryMap.get(i.type());
				list.add(i);
				inventoryMap.put(i.type(), list);
			}
			else
			{
				List<Item> list = new ArrayList<>();
				list.add(i);
				inventoryMap.put(i.type(), list);
			}
		}
		return inventoryMap;
	}
	public Map getEquippedMap()
	{
		equippedMap.clear();

		for(Item i :  equipped)
		{
			if(equippedMap.containsKey(i.type()))
			{
				List<Item> list = equippedMap.get(i.type());
				list.add(i);
				equippedMap.put(i.type(), list);
			}
			else
			{
				List<Item> list = new ArrayList<>();
				list.add(i);
				equippedMap.put(i.type(), list);
			}
		}
		return equippedMap;
	}
	public void setMax(int max)
	{
		this.max = max;
	}
	public void setMaxEquip(int max)
	{
		this.maxEquip = max;
	}
	public Item get(int index)
	{
		if(index < inventory.size())
    		return inventory.get(index);
		else
			return null;
	}
    public Item getEquipped(int index)
	{
    	if(!equipped.isEmpty())
    		return equipped.get(index);
    	else
    		return null;
    }

    public boolean isFullyEquiped() { return fullyEquiped; }

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
	public Item getVisionRadius()
	{
		opticalEnhancers = get(Type.VISION);
		deviceIndex = 0;

		if(opticalEnhancers.isEmpty())
			return null;
		else
		{
			return opticalEnhancers.get(deviceIndex);
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
    	
    	for(Item i : equipped)
    	{
    		if(i != null && i.type().equals(type))
    			specifiedItems.add(i);
    	}
    	//System.out.println(specifiedItems.size() + " items on " + type.toString());
    	
    	return specifiedItems;
    }
	public double getTypeDuration(Type type)
	{
		double  value = 0.0;
		equippedMap = getEquippedMap();

		if(equippedMap.containsKey(type))
		{
			value = equippedMap.get(type).get(0).value();
			//System.out.println("There is this type " + type.name());
		}
		//System.out.println("Type misssing" + type.name());
		return value;
	}
    public boolean isItemEquiped(Item item)
    {
		return equipped.contains(item);
    }
    public boolean containsInInventory(Item item)
    {
		return inventory.contains(item);
    }
    public void moveToInventory(int index)
    {
    	if(inventory.size() + 1 <= max)
    	{
			Item i = equipped.remove(index);
			inventory.add(i);
			equippedMap = getEquippedMap();
			inventoryMap = getInventoryMap();
		}
    }
    public void moveToEquiped(int index)
    {
		if(equipped.size() + 1 <= maxEquip)
		{
			Item i = inventory.remove(index);
			equipped.add(i);

			equippedMap = getEquippedMap();
			inventoryMap = getInventoryMap();
		}

    }
    public void equipAll(Item ... toAdd)
	{
		for(Item item : toAdd)
		{
			if(equipped.size() + 1 <= maxEquip)
			{
				equipped.add(item);
			}
		}
	}
	public void emptyBag()
	{
		inventory.clear();
		equipped.clear();
	}
    public void add(Item ... toAdd)
    {
		for(Item i : toAdd)
		{
			if(inventory.size() + 1 <= max)
			{
				inventory.add(i);
			}
		}
    }
    public void removeEquiped(Item item)
    {
    	equipped.remove(item);
        for (Item i : equipped)
        {
            if (i == item)
            {
                 i = null;
                 return;
            }
        }
    }
    public void remove(Item item)
    {
		inventory.remove(item);
    }
    public boolean isFull()
    {
		if(inventory.size() >= max)
			return true;
		else
			return false;
    }
    public int getCapacity()
    {
    	return max;
    }
    public int getItemCount()
    {
    	return inventory.size();
    }

}
