package Managers;

import entities.Entity;
import items.Type;

public class WeaponManager
{
    /*
    When enemy is dead or player is out of ammo,
    return true so you can't shoot again
     */
    public static boolean processWeapon(Entity player, Entity enemy)
    {
        if(!player.inventory().get(Type.GUN).isEmpty())
        {
            Type equippedWeaponCaliber = AmmoManager.identifyAmmo(player.inventory().getPrimaryWeapon());
            if(player.inventory().get(equippedWeaponCaliber).isEmpty())
            {
                player.notify("You're out of ammo");
                return true;
            }
            double dmg = player.inventory().getTypeDuration(Type.GUN);
            enemy.stats.vitals.dealDamageRandomly(-dmg);

            player.inventory().get(equippedWeaponCaliber).get(0).modifyValue(-1, player.inventory());
            if(enemy.stats.vitals.getVitals() < 1)
            {
                enemy.setDead(true);
                player.notify("Kill confirmed");
                return true;
            }
            return false;
        }
        else
        {
            player.notify("You try shooting but don't seem to have a ranged weapon!");
            return true;
        }
    }
}
