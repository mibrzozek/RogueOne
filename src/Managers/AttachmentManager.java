package Managers;

import items.Item;

import java.util.ArrayList;
import java.util.List;

public class AttachmentManager
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
}
