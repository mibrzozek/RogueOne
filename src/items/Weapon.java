package items;

import Managers.AmmoManager;
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

    public void changeFiringMode(Entity entity)
    {
        //if(Mode == Mode.)
    }

    public boolean isMagazineFull()
    {
        if(stats.getBulletsInMagazine() == stats.getMagazineCapacity())
            return true;
        else
            return false;
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
    public void reload(Entity entity)
    {
        if(reloading)
        {
            if(turnUntilGunReadyToShoot > 0) // need more time to reload
                turnUntilGunReadyToShoot -= 1;
            else if(turnUntilGunReadyToShoot == 0)
            {
                Item ammoStack = entity.inventory().get(AmmoManager.identifyAmmo(entity.inventory().getPrimaryWeapon())).get(0);
                int bulletsRemaining = ammoStack.value();

                if(bulletsRemaining <= stats.getMagazineCapacity())
                {
                    stats.setBulletsInMagazine(bulletsRemaining);
                    ammoStack.modifyValue(bulletsRemaining, entity.inventory());
                    reloading =  false;
                }
                else
                {
                    stats.setBulletsInMagazine(stats.getMagazineCapacity());
                    ammoStack.modifyValue(stats.getMagazineCapacity(), entity.inventory());
                    reloading = false;
                }
            }
            System.out.println("Realoading turns left : " + turnUntilGunReadyToShoot);
        }
        else
        {
            setReloading(true);
            setTurnsForReloadTime(stats.getReloadSpeed());
            System.out.println("");
        }
    }
    public int processWeaponFiring(Entity player)
    {
        if(stats.getBulletsInMagazine() > 0)
        {
            System.out.println("Weapon mode is " +  stats.getMode());
            if(stats.getMode().equals(Mode.AUTOMATIC))
            {
                //If enough bullets in mag to fire all bullets
                if(stats.getBulletsInMagazine() >= stats.getBulletsPerTurn())
                {
                    stats.setBulletsInMagazine(stats.getBulletsInMagazine() - stats.getBulletsPerTurn());
                    return stats.getBulletsPerTurn();
                }
                else
                {
                    stats.setBulletsInMagazine(0);
                    return stats.getBulletsInMagazine();
                }
            }
            else
            {
                stats.setBulletsInMagazine(stats.getBulletsInMagazine() - 1);
                return 1;
            }

        }
        else return 0;
    }
    public void setBaseStats()
    {
        this.stats.setBaseStats();
    }
    public void calculateStats()
    {
        System.out.println("Hello");
        this.stats = RexReader.retrieveStats(name());
        stats.modifyGunStatsForBuffMap();
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
