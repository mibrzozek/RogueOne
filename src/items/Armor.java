package items;

import entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Armor
{
    public enum DamageType
    {
        BLUNT,PIERCING,VITAL
    };

    private Item plateCarrier;
    private List<Item> plates;

    public Armor(Item plateCarrier, List<Item> plates)
    {
        this.plateCarrier = plateCarrier;
        if(plates == null)
        {
            this.plates = new ArrayList<>();
        }
        else
        {
            this.plates = plates;
        }
        if(plateCarrier != null)
        {
            this.plateCarrier = plateCarrier;
        }
    }
    public Item replacePlateCarrier(Item plateCarrier)
    {
        // No plate carrier, so add one
        if(this.plateCarrier == null)
        {
            this.plateCarrier = plateCarrier;
            return null;
        }
        // Current PC is better, don't replace
        if(plateCarrier.value() < this.plateCarrier.value())
            return plateCarrier;

        // New PC is better, return old
        Item old = this.plateCarrier;
        this.plateCarrier = plateCarrier;
        return old;
    }
    public Item getPlateCarrier()
    {
        return plateCarrier;
    }
    public List<Item> getPlates()
    {
        return plates;
    }
    public int getArmorPlateCount()
    {
        return plates.size();
    }
    public void addPlate(Item armorPlate)
    {
        if(isPlateCarrierFull())
        {
            return;
        }
        else
        {
            plates.add(armorPlate);
        }
    }
    public Item getRandomPlate()
    {
        if(plates.isEmpty())
            return null;
        else
        {
        Item plate = plates.get(new Random().nextInt(plateCarrier.value()));
        return plate;
        }
    }
    public boolean isPlateCarrierFull()
    {
        if(plateCarrier.value() == plates.size())
            return true;
        else
            return false;
    }
    public void removePlate()
    {
        if(!plates.isEmpty())
        {
            plates.remove(getRandomPlate());
        }
        else
        {
            System.out.println("There is no plate to remove");
        }
    }
    public double getPlateCoverage()
    {
        double maxCoverage = 8;
        double coverage = getPlates().size()/maxCoverage;

        return coverage;
    }
    public void resolveDamage(Entity attacker, Entity target)
    {
        Item primaryWeapon = attacker.inventory().getPrimaryWeapon();

        if(plateCarrier == null || plates.isEmpty())
        {
            target.stats.vitals.dealDamageRandomly(-primaryWeapon.value(), target);
            System.out.println("There is no armor, damage is dealt directly");
            return;
        }
        else if(!plates.isEmpty())
        {
            if(Math.random() < getPlateCoverage())
            {
                System.out.println("Hits covered area");
                // check armor plate health
                // determine penetration of bullet
                // determine
            }
            else
            {
                System.out.println("Direct hit in the body");
                target.stats.vitals.dealDamageRandomly(-primaryWeapon.value(), target);
            }
        }

        //  Determine plate coverage
        //      Determine if AP
        //          If pierces
        //              piercing damage
        //          else
        //              blunt
        System.out.println("Get coverage : " + getPlateCoverage() );
        //if(new Random().nextInt())
    }
}
