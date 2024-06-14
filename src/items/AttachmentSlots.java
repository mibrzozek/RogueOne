package items;

public enum AttachmentSlots
{
    OPTIC, BARREL, MAGAZINE, UTILITY;

    public AttachmentSlots getSlotForItem(Item i)
    {
        if(i.name().equals("Glock Compensator")
                || i.name().equals("9mm Supressor"))
        {
            return AttachmentSlots.BARREL;
        }
        else if(i.name().equals("Glock Extended Mags"))
        {
            return AttachmentSlots.MAGAZINE;
        }
        else if(i.name().equals("Red Dot Sight")
                || i.name().equals("Holographic Sight")
                || i.name().equals("RMR Red Dot Sight"))
        {
            return AttachmentSlots.OPTIC;
        }
        else if(i.name().equals("Bald Pro Flashlight"))
        {
            return AttachmentSlots.UTILITY;
        }
        else return null;
    }
}
