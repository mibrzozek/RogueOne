package wolrdbuilding;

import java.awt.*;
import java.io.Serializable;

import asciiPanel.AsciiPanel;

public class Projectile implements Serializable
{
	private Tile tile;
	private Point p;
	private int direction;
	private int movement;
	private char glyph;
	private Color color;
	private boolean verticalMovement = true;
	private boolean diagonalMovement = true;
	
	public Projectile(int direction, Point p, Tile tile)
	{
		this.p = p;
		this.direction = direction;
		this.color = tile.color();
		this.glyph = tile.glyph();
		this.tile = tile;
		this.movement = -1;
		
		
		if(glyph == Tile.Y_SMALL.glyph() || tile.equals(Tile.PRO_PISTOL))
			movement = -1;
		else if(glyph == Tile.R_SNIPER.glyph())
			movement = -8;
		else if(glyph == Tile.G_SMALL.glyph())
			movement = -3;
		else if(tile.equals(Tile.PRO_SHOTGUN))
			movement = -3;

		if(direction == 0 || direction == 4 )
		{			
			diagonalMovement = false;
			verticalMovement = true;
		}
		else if(direction == 6 || direction == 2)
		{
			verticalMovement = false;
			diagonalMovement = false;
		}
		else if(direction == 5 || direction == 1)
		{
			diagonalMovement = true;
			verticalMovement = false;
		}
		else if(direction == 7 || direction == 3)
		{
			diagonalMovement = true;
			verticalMovement = false;
		}
	}

	public void update(World world)
	{
		int moveBy;
		
		if(movement > 0)
			moveBy = 1;
		else 
			moveBy = -1;
		/*
		for(int i = 0; i <= Math.abs(movement); i++)
		{	
			if(verticalMovement)
			{
				if(!world.tile(p.x, p.y + moveBy, p.z).isGround())
					terminate();
				else if(world.entity(p.x, p.y + moveBy, p.z) != null)
				{
					world.entity(p.x, p.y + moveBy, p.z).dealDamage(-100);
					terminate();
				}
			}
			else
			{
				if(!world.tile(p.x + moveBy, p.y, p.z).isGround())
					terminate();
				else if(world.entity(p.x + moveBy, p.y, p.z) != null)
				{
					world.entity(p.x + moveBy, p.y, p.z).dealDamage(-100);
					terminate();
				}
			}	
		}
		*/
		System.out.println(direction + " d : " + movement + " m" );
		if(!diagonalMovement)
		{
			if(verticalMovement && direction == 0)
				p.y += movement;
			else if(verticalMovement && direction == 4)
				p.y += Math.abs(movement);
			else if(!verticalMovement && direction == 6)
				p.x += movement;
			else if(!verticalMovement && direction == 2)
				p.x += Math.abs(movement);
		}
		else
		{
			if(direction == 1 )
			{
				p.x += Math.abs(movement);
				p.y += movement;
			}
			else if(direction == 5)
			{
				p.x += movement;
				p.y += Math.abs(movement);
			}
			else if(direction == 7)
			{
				p.x += movement;
				p.y += movement;
			}
			else if(direction == 3)
			{
				p.x += Math.abs(movement);
				p.y += Math.abs(movement);
			}
		}
	}
	public void terminate()
	{
		p.x= -1;
		p.y= -1;
		p.z= -1;
	}
	public Tile tile()		{ return tile;	}
	public char glyph() 	{ return glyph; }
	public Color color()	{ return color; }
	public int direction() 	{ return direction; }
	public Point point()	{ return p; }
}
