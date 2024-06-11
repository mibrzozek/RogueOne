package items;

import structures.RexReader;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item
{
    private Item item;
    private Mode mode;

    private List<Item> attachments;

    private WeaponStats stats;

    public enum Mode
    {
        SINGLE(), BURST(), AUTOMATIC(), SEMI();

        public static Mode getMode(String mode)
        {
            if (mode.equals("SINGLE"))
                return Mode.SINGLE;
            else if (mode.equals("AUTOMATIC"))
                return Mode.AUTOMATIC;
            else if (mode.equals("BURST"))
                return Mode.BURST;
            else if (mode.equals("SEMI"))
                return Mode.SINGLE;
            else
                return Mode.SINGLE;
        }
    }
    public Weapon(Item item)
    {
        super(item.glyph(), item.color(), item.type(), item.name(), item.description(), item.value(), item.rarity());
        this.item = item;
        this.attachments = new ArrayList<>();
        this.stats = RexReader.retrieveStats(item.name());
        System.out.println("is null upon weapon creation? " + stats);
    }
    public WeaponStats getStats()
    {
        return stats;
    }
}
