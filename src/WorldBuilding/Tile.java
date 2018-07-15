package WorldBuilding;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import asciiPanel.AsciiPanel;

public enum Tile 
{
	// World materials
	UNKNOWN(' ', AsciiPanel.white),
    FLOOR((char)250, AsciiPanel.white),
    INSIDE_FLOOR((char)250, Color.GRAY),
    WALL((char)177, Color.black),
    BOUNDS('X', AsciiPanel.brightBlack),
    //
    CURSOR('x', AsciiPanel.brightBlack),
    //Projectiles
    DEAD('X', AsciiPanel.red),
    TAGGED('x', AsciiPanel.red),
    
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

	STAIRS_DOWN('>', AsciiPanel.white),
    STAIRS_UP('<', AsciiPanel.white),
	STAIRS_EXIT('<', AsciiPanel.red);
	
	private boolean swapable;
   
	private char glyph;
    public char glyph() 
    {
    	return glyph; 
    }

    private Color color;
    public Color color() 
    {
    	return color;
    }

    Tile(char glyph, Color color)
    {
    	this.swapable = true;
        this.glyph = glyph;
        this.color = color;
    }
    Tile(char glyph, Color color, Boolean swapable)
    {
    	this.swapable = swapable;
        this.glyph = glyph;
        this.color = color;
    }
    public boolean isGround() {
        return this != WALL && this != BOUNDS
        		&& this !=tlCorner && this !=trCorner 
        		&& this !=blCorner && this !=brCorner
        		&& this !=tbWall   && this != lrWall;
    }
    public boolean isDiggable() 
    {
        return this == Tile.WALL;
    }
    public boolean isWall() 
    {
        return this == Tile.WALL;
    }
    public boolean isSwapable()
    {
    	return swapable;
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