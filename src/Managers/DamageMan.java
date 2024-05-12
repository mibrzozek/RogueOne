package Managers;

import entities.Entity;
import items.Item;

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
                target.stats.vitals.dealDamageRandomly(-primaryWeapon.value());
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
                target.stats.vitals.dealDamageRandomly(-primaryWeapon.value());
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
}
