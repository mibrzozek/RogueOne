package Managers;

import items.Item;
import items.Type;

public class AmmoManager
{
    public static Type identifyAmmo(Item i)
    {
        if(i.name().contains("Glock"))
            return Type.AMMO_9MM;
        else
            return null;

    }
}
