package Character;

import java.awt.Color;

import WorldBuilding.World;

public class Mech extends Entity
{
	private char[][] mechGlyph;
	
	public Mech(String name, World world, char glyph, Color color, int maxHP)
	{
		super(name, world, glyph, color, maxHP);
		this.mechGlyph = mechGlyph;
	}
	

}
