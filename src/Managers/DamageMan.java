package Managers;

import entities.Entity;
import items.Item;
import items.Type;

import java.util.Random;

public class DamageMan
{
    public static String resolveAttack(Entity attacker, Entity target, Item primaryWeapon)
    {
        Random r = new Random();
        // Resolve Attacker
        //System.out.println("Attackers Agility : " + attacker.stats.getAgility());
        if(attacker.stats.getAgility() < 10)
        {
            if(Math.random() < .3)
            {
                target.stats.vitals.dealDamageRandomly(-primaryWeapon.value(), target);
                target.notify("You're getting shot");
            }
            else
            {
                target.notify("Shots fly by your head");
            }
        }
        else
        {
            if(Math.random() < .5)
            {
                target.stats.vitals.dealDamageRandomly(-primaryWeapon.value(), target);
                target.notify("You're getting shot");
            }
            else
            {
                target.notify("Shots fly by your head");
            }
        }
        // Resolve Psyche
        // Resolve ACCURACY
        // Resolve Burden

        // Resolve Target

        // Wild Card
        // Armor Check

        return "";

    }

    public static void resolvePlayerShootingEnemy(Entity player, Entity enemy)
    {
        Type equippedWeaponCaliber = AmmoManager.identifyAmmo(player.inventory().getPrimaryWeapon());
        int bulletsFired = 0;
        bulletsFired = player.inventory().getPrimaryWeapon().processWeaponFiring(player);// Automatic gunfire logic, reduces bullets appropriately
        for(int i = 0; i < bulletsFired; i++)
        {
            double dmg = player.inventory().getTypeDuration(Type.GUN);
            enemy.stats.vitals.dealDamageRandomly(-dmg, enemy);
            enemy.stats.processVitals();
            player.inventory().get(equippedWeaponCaliber).get(0).modifyValue(-1, player.inventory());
            player.notify("You deal damage with " + bulletsFired + " bullets");
        }
    }
}
