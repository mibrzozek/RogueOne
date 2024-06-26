package items;

import entities.Entity;
import structures.RexReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weapon extends Item
{
    private int turnUntilGunReadyToShoot = 0;
    private boolean reloading = false;

    private Item item;
    private Mode mode;

    private List<Item> attachments;
    private WeaponStats stats;
    private Map<AttachmentSlots, Item> attachmentMap;

    public boolean isReloading() {
        return reloading;
    }

    public Integer getTurnsUntilReloaded()
    {
        return turnUntilGunReadyToShoot;
    }

    public void setReloading(boolean b)
    {
        this.reloading = b;
    }

    public void setTurnsForReloadTime(int reloadSpeed)
    {
        this.turnUntilGunReadyToShoot = reloadSpeed;
    }

    public boolean isEmpty()
    {
        if(getStats().getBulletsInMagazine() == 0)
            return true;
        else return false;
    }

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
        this.attachmentMap = new HashMap<>();
        this.stats.setBaseStats();

        for(AttachmentSlots slot : AttachmentSlots.values())
        {
            attachmentMap.remove(slot, null);
        }
    }
    public void reload()
    {
        if(reloading)
        {
            if(turnUntilGunReadyToShoot > 0) // need more time to reload
                turnUntilGunReadyToShoot -= 1;
            else if(turnUntilGunReadyToShoot == 0)
            {
                stats.setBulletsInMagazine(stats.getMagazineCapacity());
                reloading = false;
            }
            System.out.println("Realoading turns left : " + turnUntilGunReadyToShoot);
        }
    }
    public void processWeaponFiring(Entity player)
    {
        if(stats.getBulletsInMagazine() > 0)
            stats.setBulletsInMagazine(stats.getBulletsInMagazine() -1);
    }
    public void setBaseStats()
    {
        this.stats.setBaseStats();
    }
    public void calculateStats()
    {
        this.stats = RexReader.retrieveStats(name());
        stats.modifyGunStatsForAttachments(this.getAllAttachments());
    }
    public boolean isAttachSlotEmpty(AttachmentSlots attachment)
    {
        if(attachmentMap.get(attachment) == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void addAttachment(Item i)
    {
        AttachmentSlots slot = AttachmentSlots.BARREL.getSlotForItem(i);

        if(attachmentMap.get(slot) == null)
        {
            attachmentMap.put(slot, i);
            System.out.println("Added attachmentto slot : " + slot.toString());
            this.calculateStats();
        }
        else
        {
            // slot is full
        }
    }
    public Item removeAttachmentInSlot(AttachmentSlots slotForItem)
    {
        Item toRemove = attachmentMap.get(slotForItem);

        attachmentMap.put(slotForItem, null);
        this.calculateStats();
        return toRemove;
    }
    /*
    public void addAllAttachments(List<Item> attachments)
    {
        this.attachments = attachments;
        this.stats.modifyGunStatsForAttachments(this.attachments);
    }

     */
    public List<Item> getAllAttachments()
    {
        attachments.clear();

        for(AttachmentSlots slot : attachmentMap.keySet())
        {
            if(attachmentMap.get(slot) == null)
                continue;
            else
            {
                attachments.add(attachmentMap.get(slot));
            }
        }
        return attachments;
    }
    public WeaponStats getStats()
    {
        return stats;
    }
}
