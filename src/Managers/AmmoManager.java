package Managers;

import items.Item;
import items.Type;

public class AmmoManager
{
    public static Type identifyAmmo(Item i)
    {
        if(i.name().contains("Glock")
            ||i.name().contains("Kriss Vector"))
            return Type.AMMO_9MM;
        else if(i.name().contains("Colt M4A1"))
            return Type.AMMO_556;
        else if(i.name().contains("Kalashnikova AK 74"))
            return Type.AMMO_762;
        else if(i.name().contains("HK MP7"))
            return Type.AMMO_46x30;
        else if(i.name().contains("RPG"))
            return Type.AMMO_RPG;
        else if(i.name().contains("FN P90"))
            return Type.AMMO_57x28;
        else
            return null;

    }
}
