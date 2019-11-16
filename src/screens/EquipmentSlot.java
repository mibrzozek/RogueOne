package screens;

import items.Item;

import java.io.Serializable;

public class EquipmentSlot implements Serializable
{
    private Item item;
    private int length;
    private int index;

    public EquipmentSlot(Item item, int index)
    {
        if(item != null)
            this.item = item;
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
    public int getLength()
    {
        return length;
    }
    public Item getItem()
    {
        return item;
    }
}
