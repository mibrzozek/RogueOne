package Managers;

import items.Item;
import items.Type;

public class AmmoManager
{
    public static Type identifyAmmo(Item i)
    {
        if(i.name().contains("Glock"))
            return Type.AMMO_9MM;
        else if(i.name().contains("Colt M4A1"))
            return Type.AMMO_556;
        else if(i.name().contains("Kalashnikova AK 74"))
            return Type.AMMO_762;
        else
            return null;

    }
}
