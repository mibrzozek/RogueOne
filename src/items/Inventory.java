package items;

import Managers.AttachmentManager;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable
{


	public enum EquipmentSlot {HEAD, TORSO, ARMS, LEGS, DEVICE, WEAPON_ONE, WEAPON_TWO, VISION};


	public static final int STARTING_INV_CAP = 10;
	public static final int STARTING_EQP_CAP = 6;

	private int max, maxEquip;

	private List<Item> inventory;
	private List<Item> equipped;

	private Set<Item> gunAttachments;

	private Map<Type, List<Item>> inventoryMap;
	private Map<Type, List<Item>> equippedMap;

	private List<Item> devices;
	private List<Item> opticalEnhancers;

	private int deviceIndex = -1;

	private boolean fullyEquiped = false;

	private Random r = new Random();
	private Item primaryWeapon;
	private Item SecondaryWeapon;
	private Item sideWeapon;
	private Item meleeWeapon;

	private List<Item> utilitySlots;

	public int getEquippedMax() {
		return maxEquip;
	}
	public int getRoom()
	{
		return getCapacity() - inventory.size();
	}
	public Weapon getPrimaryWeapon() { return (Weapon)primaryWeapon; }
	public void removePrimaryWeapon()
	{
		primaryWeapon = null;
	}
	public ArrayList<Item> getItems() { return (ArrayList<Item>) inventory; }
	public ArrayList<Item> getEquipped() { return (ArrayList<Item>) equipped; }
	public boolean isItemEquiped(Item item)
	{
		return equipped.contains(item);
	}
	public boolean containsInInventory(Item item)
	{
		return inventory.contains(item);
	}

	public Inventory(int max)
	{
		this.max = max;
		this.maxEquip = STARTING_EQP_CAP;

		inventory = new ArrayList<>();
		equipped = new ArrayList<>();

		inventoryMap = new HashMap<>();
		equippedMap = new HashMap<>();

		gunAttachments = new HashSet<>();
	}
	public void checkCapacity()
	{
		this.max = (int) getTypeDuration(Type.INVENTORY)+ STARTING_INV_CAP;
		this.maxEquip = (int)getTypeDuration(Type.HELMET) + (int)getTypeDuration(Type.TORSO)
				+ (int)getTypeDuration(Type.ARMS) + (int)getTypeDuration(Type.LEGS) + STARTING_EQP_CAP;
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
	public void setPrimaryWeapon(Item primary)
	{
		this.remove(primary);

		Weapon w = new Weapon(primary);
		w.setBaseStats();

		if(this.primaryWeapon == null)
		{
			this.primaryWeapon = w;
			this.equipAll(w);
		}
		else
		{
			this.add(this.primaryWeapon);
			this.removeEquiped(this.primaryWeapon);
			this.equipAll(w);
			this.primaryWeapon = w;
		}
	}
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
		else
		{
			value = 0;
		}
		//System.out.println("Type misssing" + type.name());
		return value;
	}
    public void moveToInventory(int index)
    {
    	if(inventory.size() + 1 <= max) {
			Item i = equipped.remove(index);
			if (i.equals(getPrimaryWeapon())) {
				this.removePrimaryWeapon();
			}
			if (i.type().equals(Type.ATTACHMENT))
			{
				System.out.println("Attachments belongs");
				((Weapon) primaryWeapon).removeAttachmentInSlot(AttachmentSlots.BARREL.getSlotForItem(i));
				((Weapon) primaryWeapon).getStats().modifyGunStatsForAttachments(((Weapon) primaryWeapon).getAllAttachments());
			}
			inventory.add(i);
			equippedMap = getEquippedMap();
			inventoryMap = getInventoryMap();

			/* Delete if nothing is broken lol
			if(AttachmentManager.returnAttachmentsForEquippedWeapon(getPrimaryWeapon(), get(Type.ATTACHMENT)) !=  null)
				gunAttachments = new HashSet(AttachmentManager.returnAttachmentsForEquippedWeapon(getPrimaryWeapon(), get(Type.ATTACHMENT)));

			 */
		}
	}
    public void moveToEquiped(int index)
    {
		if(equipped.size() + 1 <= maxEquip)
		{
			Item i = inventory.get(index);
			if(i.type().equals(Type.ATTACHMENT))
			{
				if(getPrimaryWeapon() == null)
				{
					// can't equip attachment since no weapon
					return;
				}
				if(getPrimaryWeapon().isAttachSlotEmpty(AttachmentSlots.BARREL.getSlotForItem(i)))
				{
					if(!AttachmentManager.attachmentBelongOnGun(i, (Weapon) primaryWeapon))
						return;
					getPrimaryWeapon().addAttachment(i);
					equipped.add(i);
					inventory.remove(index);
				}
				else
				{
					System.out.println("ATTACHMENT SLOT OCCUPIED");
					//Slot full error -> message? New ui?
				}
				return;
			}

			equipped.add(i);

			equippedMap = getEquippedMap();
			inventoryMap = getInventoryMap();

			// Looks for attachments for primary weapon, and returns them in a list w/o duplicates
			/*
			if(AttachmentManager.returnAttachmentsForEquippedWeapon(getPrimaryWeapon(), get(Type.ATTACHMENT)) !=  null) {
				gunAttachments = new HashSet(AttachmentManager.returnAttachmentsForEquippedWeapon(getPrimaryWeapon(), get(Type.ATTACHMENT)));
				System.out.println(gunAttachments.size() + " size of attachments in inventory");
				((Weapon) getPrimaryWeapon()).addAllAttachments(new ArrayList<Item>(gunAttachments));
				System.out.println(((Weapon) getPrimaryWeapon()).getAllAttachments());
			}

			 */
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
