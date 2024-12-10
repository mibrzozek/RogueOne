package Managers;

import items.Item;
import items.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttachmentManager implements Serializable
{
    public static List<Item> returnAttachmentsForEquippedWeapon(Item weapon, List<Item> equippedItems)
    {
        List<Item> attachments = new ArrayList<>();
        List<Item> allAttachmentsEquipped = equippedItems;

        if(allAttachmentsEquipped.isEmpty())
            return null;

        if(weapon.name().equals("Glock 19"))
        {
            for(Item i : allAttachmentsEquipped)
            {
                if(i.name().equals("Glock Extended Mags"))
                    attachments.add(i);
                else if(i.name().equals("Glizzy Comp"))
                    attachments.add(i);
                else if(i.name().equals("9mm Supressor"))
                    attachments.add(i);
            }
        }
        else if(weapon.name().equals("Colt M4A1"))
        {
            /*
            for(Item i : allAttachmentsEquipped)
            {
                if(i.name().equals("Glock Extended Mags"))
                    attachments.add(i);
                else if(i.name().equals("Glizzy Comp"))
                    attachments.add(i);
                else if(i.name().equals("9mm Supressor"))
                    attachments.add(i);
            }

             */
        }
        else if(weapon.name().equals("Kalashnikova AK 74"))
        {
            /*
            for(Item i : allAttachmentsEquipped)
            {
                if(i.name().equals("Glock Extended Mags"))
                    attachments.add(i);
                else if(i.name().equals("Glizzy Comp"))
                    attachments.add(i);
                else if(i.name().equals("9mm Supressor"))
                    attachments.add(i);
            }

             */
        }
        // For universal attachments
        for(Item i : allAttachmentsEquipped)
        {
            if(i.name().equals("Red Dot Sight"))
                attachments.add(i);
            else if(i.name().equals("Holographic Sight"))
                attachments.add(i);
            else if(i.name().equals("Blue Laser"))
                attachments.add(i);
            else if(i.name().equals("Bald Pro Flash Light"))
                attachments.add(i);
            else if(i.name().equals("RMR Red Dot Sight"))
                attachments.add(i);
        }
        return attachments;
    }
    public static boolean attachmentBelongOnGun(Item i, Weapon primary)
    {
        if(primary.name() == "Glock 19")
        {
            if(i.name().equals("Glock Extended Mags")
                    || i.name().equals("Glizzy Comp")
                    || i.name().equals("9mm Supressor")
                    || i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "Colt M4A1")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "HK MP7")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "FN P90")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "Kriss Vector")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "CZ 75 SP-01 Shadow")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "Colt M1911")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "Glock G18C")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "HK UMP")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "Mk47 Mutant")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "Steyr AUG")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else if(primary.name() == "KEL TEK KSG")
        {
            if(i.name().equals("Red Dot Sight")
                    || i.name().equals("Holographic Sight")
                    || i.name().equals("Blue Laser")
                    || i.name().equals("Bald Pro Flashlight")
                    || i.name().equals("RMR Red Dot Sight"))
                return true;
            else return false;
        }
        else return false;
    }
}
