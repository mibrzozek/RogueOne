package items;

import wolrdbuilding.Palette;

import java.awt.*;


public enum Type 
{
	PLASMA, APLASMA, BAD_PLASMA,
	HEAD, ARMS, TORSO, LEGS, 
	GUN, DEVICE, WEAPON, PART, MELEE,
	CONSUMABLE,
	VILE,
	STEALTH,
	MM9_AMMO,
	VISION, OXYGEN, BRICK, INVENTORY, HEALING, PASSIVE_HEALING, HEAD_HEALING, HELMET, CLEARANCE, GOLD, AI_CHIP, AI_ATTACHMENT, AUGMENTATION, MINING, PAPER_BOOK, FULL_HEAL, BLUE_CARD, TURKEY, ARMOR, UTILITY, BOW, RANGED, ARROW, AXE, TIMBER, ORE, RED, HEALTH, GREEN, SPLINT, KEY, UNIQUE, ATTACHMENT, AMMO, AMMO_9MM, AMMO_556, AMMO_762, GUN_PARTS, PISTOL_GUN_PARTS, RIFLE_GUN_PARTS, AMMO_46x30, AMMO_57x28, AMMO_RPG, STIMULANT, AMMO_45, AMMO_38_SPECIAL, AMMO_762_51, AMMO_12_GAUGE, CHARM, PLATE_3, REGEN;
	
	private Color color = setColor();
	
	Type()
	{
		color = setColor();
	}
	public Color getColor()
	{
		return color;
	}
	public Color setColor()
	{
		if(this.equals(Type.PLASMA) || this.equals(Type.APLASMA))
			color = Palette.lightBlue;
		else if(this.equals(Type.HEAD) || this.equals(Type.ARMS) 
				|| this.equals(Type.TORSO) || this.equals(Type.LEGS))
			color = Palette.lightRed;
		else if(this.equals(Type.GUN) || this.equals(Type.DEVICE) 
				|| this.equals(Type.WEAPON) || this.equals(Type.PART))
			color = Palette.red;
		else if(this.equals(Type.CONSUMABLE))
			color = Palette.green;
		else if(this.equals(Type.VILE))
			color = Palette.lightGreen;
		else if(this.equals(Type.STEALTH))
			color = Palette.purple;
		
		return color;
	}
}
