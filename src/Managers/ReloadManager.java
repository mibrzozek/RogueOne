package Managers;

import entities.Entity;

public class ReloadManager
{
    public static void manageReload(Entity player)
    {
        if(player.inventory().getPrimaryWeapon() == null)
            return;

        player.inventory().getPrimaryWeapon().reload(player);
    }
}
