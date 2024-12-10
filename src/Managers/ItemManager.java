package Managers;

import entities.Effect;
import entities.Entity;
import items.Item;
import items.Type;
import wolrdbuilding.Palette;

import java.io.Serializable;

public class ItemManager implements Serializable
{
    public  static void UseItem(Item i, Entity entity)
    {
        if(i.type() == Type.APLASMA)
        {
            entity.modifyPlasma(1000);
            entity.notify("Mmmm, wow, so much plasma...");
        }
        else if(i.type() == Type.CONSUMABLE)
        {
            entity.notify("Delicious unidentifiable sustenance");

            entity.stats.addEffect(new Effect(Effect.Effects.MEGA_RELAXED, "Relaxed", Palette.monoPerfect));
        }
        else if(i.type() == Type.HEALING)
        {
            entity.notify("This will stop the bleeding");

            entity.stats.vitals.disperseHealingEvenly(i.value());
        }
        else if(i.type() == Type.FULL_HEAL)
        {
            entity.stats.vitals.fullHeal();
        }
        else if(i.type() == Type.PASSIVE_HEALING)
        {
            entity.stats.addEffect(new Effect(Effect.Effects.PASSIVE_HEALING, "Healing", Palette.green));
        }
    }
}
