package Character;

import java.awt.Color;

import WorldBuilding.Tile;
import WorldBuilding.World;

public class Mech extends Entity
{
	private char[][] mechGlyph;
	
	public Mech(String name, World world, Tile tile, int maxHP)
	{
		super(name, world, tile, maxHP);
		this.mechGlyph = mechGlyph;
	}
	

}
