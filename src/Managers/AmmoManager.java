package Managers;

import items.Item;
import items.Type;

public class AmmoManager
{
    public static Type identifyAmmo(Item i)
    {
        if(i.name().contains("Glock")
            ||i.name().contains("Kriss Vector")
                ||i.name().contains("Glock G18C")
                ||i.name().contains("CZ 75 SP-01 Shadow"))
            return Type.AMMO_9MM;
        else if(i.name().contains("Colt M4A1")
                ||i.name().contains("Steyr AUG"))
            return Type.AMMO_556;
        else if(i.name().contains("Kalashnikova AK 74")
                ||i.name().contains("Mk47 Mutant"))
            return Type.AMMO_762;
        else if(i.name().contains("HK MP7"))
            return Type.AMMO_46x30;
        else if(i.name().contains("RPG"))
            return Type.AMMO_RPG;
        else if(i.name().contains("FN P90"))
            return Type.AMMO_57x28;
        else if(i.name().contains("Chiefs Special")
        ||i.name().contains("Winchester Model 1873"))
            return Type.AMMO_38_SPECIAL;
        else if(i.name().contains("KEL TEC KSG"))
            return Type.AMMO_12_GAUGE;
        else if(i.name().contains("KAR98K")
        || i.name().contains("DVL Saboteur"))
            return Type.AMMO_762_51;
        else if(i.name().contains("HK UMP")
        || i.name().contains("Colt M1911"))
            return Type.AMMO_45;
        else
            return null;

    }
}
