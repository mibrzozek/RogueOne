package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NInventory implements Serializable
{
    private int maxInvSlots;
    private int maxUtilitySlots;

    private Item primaryWeapon;
    private Item SecondaryWeapon;
    private Item sideWeapon;
    private Item meleeWeapon;

    private Map<Type, List<Item>> inventoryMap;

    private List<Item> inventory;
    private List<Item> utilitySlots;

    public NInventory(int maxInv, int maxUtil)
    {
        this.maxInvSlots = maxInv;
        this.maxUtilitySlots = maxUtil;

        this.utilitySlots = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }




}
