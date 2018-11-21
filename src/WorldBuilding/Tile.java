package WorldBuilding;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import WorldBuilding.Palette;

import asciiPanel.AsciiPanel;

public enum Tile 
{
	// World materials
	UNKNOWN(' ', Palette.white),
    FLOOR((char)250, Palette.randomNewColor()),
    INSIDE_FLOOR((char)250, Palette.gray),
    BLASTED_TERRAIN((char)176, Palette.brown),
    
    WALL((char)177, Palette.randomColor()),
    RED_WALL((char)177, Palette.black),
    BROWN_WALL((char)177, Palette.randomColor()),
    SILVER_WALL((char)177, Palette.randomColor()),
    
    BOUNDS('X', Palette.black),
    //
    CURSOR('x', AsciiPanel.brightBlack),
    //Projectiles
    DEAD('X', AsciiPanel.red),
    TAGGED('x', AsciiPanel.red),
    TAGGED_PLAYER('@', Palette.darkRed),
    
    Y_SMALL('.', AsciiPanel.brightYellow),
    R_SNIPER((char) 15, AsciiPanel.brightRed),
    
    Y_MED((char) 4, AsciiPanel.brightYellow),
    Y_LARGE((char) 9, AsciiPanel.brightYellow),
    
    R_MED((char) 248, AsciiPanel.brightRed),
    G_SMALL((char) 4, AsciiPanel.brightGreen),
    
    // Room material
    DOOR((char)240, AsciiPanel.red),
    
	lrWall((char)186, Color.white),
	tbWall((char)205, Color.white),
	tlCorner((char)218, Color.white),
	trCorner((char)170, Color.white),
	blCorner((char)200, Color.white),
	brCorner((char)217, Color.white),
	
	simpleTLC((char)218, Color.white),
	simpleTRC((char)191, Color.white),
	simpleBLC((char)192, Color.white),
	simpleBRC((char)217, Color.white),
	simpleTBW((char)196, Color.white),
	simpleLRW((char)179, Color.white),

	STAIRS_DOWN('>', AsciiPanel.white),
    STAIRS_UP('<', AsciiPanel.white),
	STAIRS_EXIT('<', AsciiPanel.red),
	
	// Entity Tiles
	PLAYER('@', Palette.paleWhite, Palette.darkGray),
	
	FUNGUS('f', Palette.green, Palette.lightRed),
	HITMAN('h', Palette.paleWhite, Palette.lightRed),
	TRADER('T', Palette.yellow, Palette.lightRed),
	MUTANT('M', Palette.blue, Palette.lightRed),
	DROID((char)225, Palette.darkRed, Palette.lightRed),
	ROGUE( (char)146, Palette.red, Palette.lightRed),
	MECH('M', Palette.paleWhite, Palette.lightRed),
	
	;
	
	private boolean swapable;
	public Random r =  new Random();
	private Color color;
	private Color backColor = Palette.darkestGray;
	private Color tempColor;
	private char glyph;
	
    Tile(char glyph, Color color)
    {
    	this.swapable = true;
        this.glyph = glyph;
        this.color = color;
        this.backColor = Palette.darkestGray;
    }
    Tile(char glyph, Color color, Color backColor)
    {
    	this.swapable = swapable;
        this.glyph = glyph;
        this.color = color;
        this.backColor = backColor;
        this.tempColor = backColor;
    }
    Tile(char glyph, Color color, Boolean swapable)
    {
    	this.swapable = swapable;
        this.glyph = glyph;
        this.color = color;
        this.backColor = Palette.darkestGray;
    }
    public void animate()
    {

    	
    }
    public boolean isGround() {
        return this ==  Tile.FLOOR || this ==  Tile.INSIDE_FLOOR
        		|| this ==  Tile.BLASTED_TERRAIN || this == Tile.STAIRS_DOWN
        		|| this == Tile.STAIRS_UP;
    }
    public boolean isDiggable() 
    {
        return this == Tile.WALL;
    }
    public boolean isFloor()
    {
    	return this == Tile.INSIDE_FLOOR;
    }
    public boolean isWall() 
    {
        return this == Tile.WALL || this == Tile.RED_WALL;
    }
    public boolean isSwapable()
    {
    	return swapable;
    }
    public char glyph() 
    {
    	return glyph; 
    }
    public Color color() 
    {
    	return color;
    }
    public Color backColor() 
    {
    	if(backColor == Palette.darkestGray)
    	{	
    		backColor = tempColor;
    		return backColor;
    	}
    	else
    	{
    		backColor = Palette.darkestGray;
    		return backColor;
    	}
    }
    public void setColor(Color color)
    {
    	this.color = color;
    }
    public static Tile randomTile()
    {
    	int pick = new Random().nextInt(Tile.values().length);
    	return Tile.values()[pick];
    }
    public void setSwapable(Boolean truth)
    {
    	if(truth = true)
    		swapable = true;
    	else
    		swapable = false;
    }

}