package items;

import java.awt.*;
import java.io.Serializable;

import entities.Effect;
import entities.Entity;
import wolrdbuilding.Palette;

public class Item implements Serializable
{
    private char glyph;
    private Color color;
    
    private Type type;
    private String name;
    private String description;
    
    private int attack;
    private int defense;
    private int value;
    
    private boolean usable = false;
    private boolean equiped = false;
   
    public Item(char glyph, Color color, Type type, String name, String description, int attack, int defense, int value)
    {
        this.glyph = glyph;
        this.color = color;
        this.type = type;
        this.name = name;
        this.description = description;
        this.attack = attack;
        this.defense = defense;
        this.value = value;
        if(this.type.equals("plasma"))
        	this.usable = true; 
    }
    public Type type() 				{ return type; }
    public String name() 			{ return name; }
    public Color color() 			{ return color; }
    public char glyph() 			{ return glyph; }
    public String description()		{ return description; }
    public int attack() 			{ return attack;}
    public int defense() 			{ return defense;}
    public int value() 				{ return value; }
    public boolean usable()			{ return usable; }
    
    public boolean isEquiped() { return equiped; }
    public void equip() { equiped = true; }
    public void unEquip() { equiped = false; }
    
    public void useItemOn(Entity other)
    {
    	if(this.type == Type.APLASMA)
    	{
    		other.modifyPlasma(1000);
    		other.notify("Mmmm, wow, so much plasma...");
    		System.out.println("Insisde use");
    	}
    	else if(this.type == Type.CONSUMABLE)
    	{
    		other.notify("Mmmmm, i love bio food!");

    		other.stats.addEffect(new Effect(Effect.Effects.MEGA_RELAXED, "Relaxed", Palette.monoPerfect));
    	}
        else if(this.type == Type.HEALING)
        {
            other.notify("This will stop the bleeding");

            other.stats.healAllVitals(this.value);

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
}