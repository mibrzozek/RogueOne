package items;

import java.awt.Color;

import wolrdbuilding.Palette;

public enum Type 
{
	PLASMA, APLASMA, BAD_PLASMA,
	HEAD, ARMS, TORSO, LEGS, 
	GUN, DEVICE, WEAPON, PART, MELEE,
	CONSUMABLE,
	VILE,
	STEALTH,
	VISION;
	
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
