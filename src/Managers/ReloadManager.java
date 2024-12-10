package Managers;

import entities.Entity;

import java.io.Serializable;

public class ReloadManager implements Serializable
{
    public static void manageReload(Entity player)
    {
        if(player.inventory().getPrimaryWeapon() == null)
        {
            player.notify("No weapon equipped");
            return;
        }
        if(player.inventory().getPrimaryWeapon().isMagazineFull())
        {
            player.notify("Your mag is full!");
            return;
        }
        player.inventory().getPrimaryWeapon().reload(player);
        player.notify("Reloading!");
    }
}
