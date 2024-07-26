package Managers;

import entities.Entity;
import items.Item;
import items.Type;
import items.Weapon;

import java.util.List;

public class WeaponManager
{
    /*
    True stops the turn from advancing because
        Out of ammo
        need to reload
        or enemy was killed after damage
    False
        updates world
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
            else if(player.inventory().getPrimaryWeapon().isEmpty())
            {
                player.notify("You need to reload!");
                WeaponManager.processOutOfAmmoState(player, player.inventory().getPrimaryWeapon());
                return true;
            }
            else // Attach enemy
            {
                DamageMan.resolvePlayerShootingEnemy(player, enemy);
                return false;
            }
        }
        else
        {
            player.notify("You try shooting but don't seem to have a ranged weapon!");
            return true;
        }
    }
    public static void processOutOfAmmoState(Entity player, Weapon weapon)
    {
        Type equippedWeaponCaliber = AmmoManager.identifyAmmo(player.inventory().getPrimaryWeapon());

        if(player.inventory().get(equippedWeaponCaliber).isEmpty())
        {
            player.notify("No more ammo!");
            return;
        }
        else
        {
            weapon.setReloading(true);
            weapon.setTurnsForReloadTime(weapon.getStats().getReloadSpeed());
            System.out.println("\tReload speed: " + weapon.getStats().getReloadSpeed());
            List<Item> bulletsItemList = player.inventory().get(equippedWeaponCaliber);
            Item bullets = bulletsItemList.get(0);
            return;
        }
    }
    public static void changeWeaponMode(Entity player)
    {
        if(player.inventory().getPrimaryWeapon() ==  null)
            return;

        player.inventory().getPrimaryWeapon().changeFiringMode(player);
    }
}
