package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import items.Type;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import java.awt.*;
import java.util.*;
import java.util.List;

public class NInventoryScreen extends ScrollingBasedScreen
{

    AsciiPanel terminal;
    private Random r = new Random();

    int bx = 0, by = 30, bw = 31, bh= 20;
    int eo = 31;

    int scrollingLength = 17;
    int equipementListSize = 0;


    private boolean selectingFromInventory = true;
    private boolean moreToDisplay = false;

    List<EquipmentSlot> equipementList;
    private int bo = 16;

    public NInventoryScreen(Entity player, AsciiPanel terminal, boolean selectingFromInventory)
    {
        super(player, terminal, selectingFromInventory);
        this.scrollX = bx;
        this.scrollY = by + 1;
        this.equipIndex = 0;
        this.invIndex = 0;

        equipementList = new ArrayList<>();
    }

    public void write(AsciiPanel terminal) // gets called from parent class
    {
        TileEngine.renderBox(terminal,bw, bh, bx, by, TileSet.SIMPLE, true);
        renderItemList(terminal);
        Set<Type> set =  player.inventory().getEquippedMap().keySet();
        List types = Arrays.asList(set.toArray());

        if(player.inventory().getEquipped().size() > 0)
        {
            TileEngine.renderBox(terminal, (bw / 2) + 1, bh, eo, by, TileSet.SIMPLE, true);
            if(player.inventory().getEquipped().size() + types.size() > 18)
            {
                TileEngine.renderBox(terminal, (bw/2) + 1, bh, eo + 15 + 1, by, TileSet.SIMPLE, true);
            }
        }
        terminal.write("" + (char)16, scrollX, scrollY, Palette.red); // CURSOR

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
                    TileEngine.renderDisplayPlate(terminal, x, y++, 14, t.name(), false, Palette.monoGrayBlue, Palette.morePaleWhite);
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
                    TileEngine.renderItemPlate(terminal, x, y++, i, 14);
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

        terminal.write(equippCap, (bx+eo)-equippCap.length(), by + bh -2);

        System.out.println(equipementList.size() +" slots size ");

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
                //terminal.write("+" + Tile.INVENTORY_TYPE_ICON.glyph(), x, y, i.type().setColor(), Palette.darkestGray);
                //terminal.write(i.name());

                TileEngine.renderItemPlate(terminal, x, y, i, 14);
                y++;
            }
            else
            {
                //terminal.write("+", x, y++, Palette.white, Palette.darkestGray);
            }
        }


        terminal.write("Equipment", bx + eo + 3, by);
        terminal.write(TileSet.SIMPLE.brc().glyph(), bx + eo -1, by + bh-1, Palette.gray);
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
            //terminal.write(lines.get(i).name(), x, y, Color.white);

            if(equipIndex < scrollingLength)
            {
                if( i < player.inventory().getItems().size())
                    TileEngine.renderItemPlate(terminal, x, y, lines.get(i), 29);

            }
            else if(equipIndex >= scrollingLength)
            {
                if(selectingFromInventory)
                    TileEngine.renderItemPlate(terminal, x, y, player.inventory().getItems().get(equipIndex - scrollingLength + i), 29);
                else
                {
                    if(i < player.inventory().getItems().size())
                        TileEngine.renderItemPlate(terminal, x, y, player.inventory().getItems().get(i), 29);
                }
            }
            y++;
        }
        terminal.write("Inventory", bx + 1 + 3, by);

        String itemCount = player.inventory().getItemCount() + "/" + player.inventory().getCapacity();

        terminal.write(itemCount, bx + bw-itemCount.length(), by+bh-1);

        //TileEngine.renderItemList(terminal, new ArrayList<Item>(player.inventory().getItemList()), x, y);
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

        System.out.println("selecting from inventory " + selectingFromInventory);

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
                else if(equipIndex == 17 && player.inventory().getEquipped().size() > 17)
                {
                    equipIndex++;
                    scrollY = by + 1;
                    scrollX = bx + eo + bo;
                }
                else if(equipIndex > 17 && player.inventory().getEquipped().size() > 17 && equipIndex < 35)
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
