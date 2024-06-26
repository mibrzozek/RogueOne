package items;

import entities.Effect;
import entities.Entity;
import wolrdbuilding.Palette;
import wolrdbuilding.Point;

import java.awt.*;
import java.io.Serializable;

public class Item implements Serializable
{
    public boolean isProjectileWeapon()
    {
        if(this.type == Type.RANGED || this.type == Type.GUN)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    public enum Rarity
    {
        COMMON(),
        TYPICAL(),
        UNCOMMON(),
        RARE(),
        ULTRA_RARE(),
        ONE_OF_A_KIND();

        Rarity()
        {
        }
    }
    private char glyph;
    private Color color;
    private Point location;
    
    private Type type;
    private String name;
    private String description;

    private Rarity r;

    private int value;
    
    private boolean usable = false;
    private boolean equiped = false;
   
    public Item(char glyph, Color color, Type type, String name, String description, int value, Rarity r)
    {
        this.glyph = glyph;
        this.color = color;
        this.type = type;
        this.name = name;
        this.description = description;
        this.value = value;
        this.r = r;
        this.location = new Point(0, 0, 0);

        if(this.type.equals("plasma"))
            this.usable = true;
    }
    public void setLocation(Point p)
    {
        this.location = p;
    }
    public Point getLocation()
    {
        return this.location;
    }
    public Type type() 				{ return type; }
    public String name() 			{ return name; }
    public Color color() 			{ return color; }
    public char glyph() 			{ return glyph; }
    public String description()		{ return description; }
    public int value() 				{ return value; }
    public boolean usable()			{ return usable; }
    public Rarity rarity()          { return r; }
    
    public boolean isEquiped() { return equiped; }
    public void equip() { equiped = true; }
    public void unEquip() { equiped = false; }
    public void modifyValue(int mod, Inventory inv)
    {
        if(mod < 0 && value + mod > -1)
        {
            value += mod;
        }
        if(value < 1)
            inv.removeEquiped(this);
    }
    public void useItemOn(Entity other)
    {
    	if(this.type == Type.APLASMA)
    	{
    		other.modifyPlasma(1000);
    		other.notify("Mmmm, wow, so much plasma...");
    	}
    	else if(this.type == Type.CONSUMABLE)
    	{
    		other.notify("Delicious unidentifiable sustenance");

    		other.stats.addEffect(new Effect(Effect.Effects.MEGA_RELAXED, "Relaxed", Palette.monoPerfect));
    	}
        else if(this.type == Type.HEALING)
        {
            other.notify("This will stop the bleeding");

            other.stats.vitals.disperseHealingEvenly(this.value);
        }
        else if(this.type == Type.FULL_HEAL)
        {
            other.stats.vitals.fullHeal();
        }
        else if(this.type == Type.PASSIVE_HEALING)
        {
            other.stats.addEffect(new Effect(Effect.Effects.PASSIVE_HEALING, "Healing", Palette.green));
        }
    }
    @Override
    public String toString()
    {
    	return name;
    }
    @Override
    public boolean equals(Object obj)
    {
    	Item item = null;
    	
    	if(obj instanceof Item)
    		item = (Item) obj;
    	
    	if(item != null && this.name.toString().equals(item.name.toString()))
    		return true;
    	else	
    		return false;
    }
    @Override
    public int hashCode()
    {
        return name != null ? name.hashCode() : 0;
    }
}