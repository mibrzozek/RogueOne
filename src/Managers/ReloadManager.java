package Managers;

import entities.Entity;

public class ReloadManager
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
