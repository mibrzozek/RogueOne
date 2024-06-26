package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import items.Type;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import java.awt.*;
import java.util.List;
import java.util.*;

public class NInventoryScreen extends ScrollingBasedScreen
{

    AsciiPanel terminal;
    private Random r = new Random();

    int bx = 0, by = 30, bw = 31, bh= 20;
    int eo = 31;

    int scrollingLength = 17;
    int equipementListSize = 0;

    private List types;

    private boolean selectingFromInventory = true;
    private boolean moreToDisplay = false;

    List<EquipmentSlot> equipementList;
    private int bo = 16;

    public NInventoryScreen(Entity player, AsciiPanel terminal, boolean selectingFromInventory, int dHeight)
    {
        super(player, terminal, selectingFromInventory);
        by = dHeight - bh;
        this.scrollX = bx;
        this.scrollY = by + 1;
        this.equipIndex = 0;
        //this.invIndex = 0;

        Set<Type> set =  player.inventory().getEquippedMap().keySet();
        types = Arrays.asList(set.toArray());

        equipementList = new ArrayList<>();
    }
    public void write(AsciiPanel terminal) // gets called from parent class
    {
        TileEngine.renderBox(terminal,bw, bh, bx, by, TileSet.SIMPLE, true);
        renderItemList(terminal);
        Set<Type> set =  player.inventory().getEquippedMap().keySet();
        types = Arrays.asList(set.toArray());

        if(player.inventory().getEquipped().size() > 0)
        {
            TileEngine.renderBox(terminal, (bw / 2) + 1, bh, eo, by, TileSet.SIMPLE, true);
            if(player.inventory().getEquipped().size() + types.size() > 18)
            {
                TileEngine.renderBox(terminal, (bw/2) + 1, bh, eo + 15 + 1, by, TileSet.SIMPLE, true);
            }
        }
        terminal.write("" + (char)16, scrollX , scrollY); // CURSOR

        if(equipIndex < 0 && selectingFromInventory)
            TileEngine.renderDisplayPlate(terminal, scrollX, scrollY, 31, "Inventory", true, getBackColor(), getForeColor());
        else if(equipIndex < 0 && !selectingFromInventory)
            TileEngine.renderDisplayPlate(terminal, scrollX, scrollY, 16, "Equipment", true, getBackColor(), getForeColor());

        int x = bx + eo + 1;
        int y = by + 1;

        int ri = 0;
        equipementList.clear();

        for(Object o : types) // For every type of item in Equipped List
        {
            if(o instanceof Type)
            {
                boolean rendered = false;
                Type t = (Type)o;
                //System.out.println(player.inventory().getEquippedMap().get(t).toString());

                List<Item> itemsInCategory = (List<Item>) player.inventory().getEquippedMap().get(t);

                if(!rendered) // Adds and displays ITEM TYPE on equipment list
                {
                    TileEngine.renderDisplayPlate(terminal, x, y++, 30, t.name(), false, Palette.monoGrayBlue, Palette.morePaleWhite);
                    equipementList.add(new EquipmentSlot(null, ri++));
                    rendered=true;
                    equipementListSize++;
                }
                if(y > by + 18) // if at bottom of screen
                {
                    x += bo;
                    y = by + 1;
                }
                for(Item i: itemsInCategory)
                {
                    equipementList.add(new EquipmentSlot(i, ri++));
                    TileEngine.renderItemPlate(terminal, x, y++, i, 30);
                    equipementListSize++;
                    if(y > by + 18) // if at bottom of screen
                    {
                        x += bo;
                        y = by + 1;
                    }
                }
            }
        }
        String equippCap = player.inventory().getEquipped().size() + "/" + player.inventory().getEquippedMax();

        if(player.inventory().getEquipped().size() > 0)
            terminal.write(equippCap, (bx+eo)-equippCap.length() + 15, by + bh - 1);

        terminal.repaint();
    }
    public void renderEquipementList(AsciiPanel terminal)
    {
        List<Item> lines = getList(player.inventory().getEquipped());
        List<Item> equipped = player.inventory().getEquipped();

        int x = bx + eo + 1;
        int y = by + 1;

        for(Item i : equipped)
        {
            if(i != null)
            {
                TileEngine.renderItemPlate(terminal, x, y, i, 14);
                y++;
            }
            else
            {
                //terminal.write("+", x, y++, Palette.white, Palette.darkestGray);
            }
        }
    }
    public void renderItemList(AsciiPanel terminal)
    {
        List<Item> lines = getList(player.inventory().getItems());
        List<Item> iL = player.inventory().getItems();


        int limit = 18;
        int x = bx+1;
        int y = by+1;

        if(moreToDisplay && equipIndex -scrollingLength >= 0)
        {
            lines = updateList(equipIndex -scrollingLength);
        }

        for (int i = 0; i < limit; i++)
        {
            if(equipIndex < scrollingLength)
            {
                if( i < player.inventory().getItems().size())
                    TileEngine.renderItemPlate(terminal, x, y, lines.get(i), 29);
            }
            else if(equipIndex >= scrollingLength)
            {
                if(selectingFromInventory)
                {
                    if(equipIndex - scrollingLength + i < player.inventory().getItems().size())
                        TileEngine.renderItemPlate(terminal, x, y, player.inventory().getItems().get(equipIndex - scrollingLength + i), 29);
                }
                else
                {
                    if(i < player.inventory().getItems().size())
                        TileEngine.renderItemPlate(terminal, x, y, player.inventory().getItems().get(i), 29);
                }
            }
            y++;
        }
        //terminal.write("Inventory", bx + 1 + 3, by);
        String itemCount = player.inventory().getItemCount() + "/" + player.inventory().getCapacity();
        terminal.write(itemCount, bx + bw-itemCount.length() -1, by+bh-1);
    }
    private List<Item> getList(List<Item> list)
    {
        List<Item> lines = new ArrayList();

        for (int i = 0; i < player.inventory().getCapacity(); i++)
        {
            if(i < list.size())
                lines.add(list.get(i));
        }
        return lines;
    }
    private List<Item> updateList(int startingIndex)
    {
        List<Item> inventory = player.inventory().getItems();
        List<Item> toDisplay = new ArrayList<>();

        int counter =  startingIndex +1;

        for(int i = 0; i <= scrollingLength; i++)
        {
            if(startingIndex < player.inventory().getItems().size())
            {
                toDisplay.add(inventory.get(startingIndex));
            }

            startingIndex++;
        }
        return toDisplay;
    }
    @Override
    public void selectItem()
    {
        if(equipIndex >= 0 )
        {
            if(selectingFromInventory)
            {
                Item i = player.inventory().get(equipIndex);

                if(i != null)
                {
                    isSelected = true;
                    OptionScreen = new  OptionsScreen(player, bx+bw, by, equipIndex, true, terminal);
                    isSelected = false;
                }
            }
            else // selecting from equipped
            {
                Item i = null;

                if(equipIndex < equipementList.size())
                    i  = equipementList.get(equipIndex).getItem();

                if(i != null)
                {
                    isSelected = true;
                    OptionScreen = new  OptionsScreen(player, bx+bw, by, player.inventory().getEquipped().indexOf(i), false, terminal);
                    isSelected = false;
                }
            }
        }
        else if(equipIndex < 0)
        {
            selectingFromInventory = false;
            scrollX = bx + eo;
        }
    }
    public void scrollLeft()
    {
        scrollX = bx;
        scrollY = by;
        equipIndex = -1;
        isSelected = false;
        selectingFromInventory = true;
    }
    public void scrollDown()
    {
        if(equipIndex >= scrollingLength && equipIndex <= player.inventory().getCapacity() && selectingFromInventory)
        {
            moreToDisplay = true;
            if (equipIndex +1 < player.inventory().getItems().size())
                equipIndex++;
        }
        else
        {
            moreToDisplay = false;

            if(selectingFromInventory)
            {
                if(scrollY-by-1  < player.inventory().getItems().size() && equipIndex < player.inventory().getItems().size() -1)
                    scrollY++;

                equipIndex = scrollY-by-1;
            }
            else
            {
                if (equipIndex < 17)
                {
                    scrollY++;
                    equipIndex = scrollY - by - 1;
                }
                else if(equipIndex == 17 && player.inventory().getEquipped().size() + types.size() > 17)
                {
                    equipIndex++;
                    scrollY = by + 1;
                    scrollX = bx + eo + bo;
                }
                else if(equipIndex > 17 && player.inventory().getEquipped().size() + types.size() > 17 && equipIndex < 35)
                {
                    equipIndex++;
                    scrollY++;
                }
            }

        }
    }
    public void scrollUp()
    {
            if(equipIndex > scrollingLength)
            {
                equipIndex--;

                if(!selectingFromInventory)
                {
                    scrollY--;

                    if(equipIndex == scrollingLength)
                    {
                        scrollX = bx + eo;
                        scrollY = by + bh - 2;
                    }
                }
            }
            else
            {
                moreToDisplay = false;

                if(by-scrollY != 0)
                    scrollY--;

                equipIndex = scrollY-by-1;
                //invIndex = scrollY-by-1;
            }
    }

    @Override
    public Screen returnScreen(Screen screen) {
        return null;
    }

    @Override
    public void animate() {

    }

    @Override
    public Color getForeColor() {
        return null;
    }

    @Override
    public Color getBackColor() {
        return null;
    }
}
