package items;

import entities.Entity;
import wolrdbuilding.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stash implements Serializable
{
    private int capacity, stashSize;

    private boolean open = false;

    private Point p;

    private List<Item> items;
    private List<Type> stashTypes;
    private Random r;

    private ItemFactory iF;

    public Stash(Point p)
    {
        this.p = p;

        items = new ArrayList();
        stashTypes = new ArrayList();

        iF = new ItemFactory();

        r = new Random();
        capacity = r.nextInt(3) + 3;
        stashSize = r.nextInt(5) + 1;
    }
    public void openStash(Entity player)
    {
        if(player.inventory().containsInInventory(iF.newBlueClearance()))
            open = true;
    }
    public int getCapacity()
    {
        return capacity;
    }

    public List<Item> getStash()
    {
        return items;
    }

    public Item getItemFromStash(int index)
    {
        Item i = items.get(index);
        items.remove(i);
        return i;
    }
    public Point getPoint()
    {
        return p;
    }
    @Override
    public boolean equals(Object o)
    {
        Stash s;

        if(o  instanceof Stash)
        {
            s = (Stash) o;

            if(s.getPoint().equals(this.getPoint()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    public void addItem(Item item)
    {
        items.add(item);
    }
}
