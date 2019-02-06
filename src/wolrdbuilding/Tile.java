package wolrdbuilding;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import asciiPanel.AsciiPanel;
import wolrdbuilding.Palette;

public enum Tile 
{
	// World materials
	UNKNOWN(' ', Palette.white),
    FLOOR((char)250, Palette.randomNewColor()),
    TERMINAL_ACESS((char)250, Palette.red),
    INSIDE_FLOOR((char)250, Palette.gray),
    BLASTED_TERRAIN((char)176, Palette.brown),
    WHITE_TERRAIN((char)176, Palette.white),
    WALL((char)177, Palette.randomColor()),
    RED_WALL((char)177, Palette.black),
    BROWN_WALL((char)177, Palette.randomColor()),
    SILVER_WALL((char)177, Palette.randomColor()),
    BOUNDS('X', Palette.black),
    CURSOR('x', AsciiPanel.brightBlack),

    // Obstacles
    UP_DOWN_LASER((char)186, Color.red),
    LEFT_RIGHT_LASER((char)205, Color.red),
    
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

    /*
        The following tiles are to always be displayed
        These are the primary building components for
        all the structures in the game
     */
    PLASMA_CANISTER((char)7, Palette.blue, true),
    PLASMA_CANISTER_2((char)4, Palette.blue, true),
    INTERFACE((char)216, Palette.paleWhite, true),

    // SIMPLE

    simpleLRWS((char)179, Color.white, true),
    simpleLRW_L_KNOB((char)180, Color.white, true),
    simpleLRW_L_DBL_KNOB((char)181, Color.white, true),
    simpleTRCS((char)191, Color.white, true),
    simpleBLCS((char)192, Color.white, true),
    simpleT_Shape_UPSIDEDOWN((char)193, Color.white, true),
    simpleT_Shape((char)194, Color.white, true),
    simpleLRW_R_KNOB((char)195, Color.white, true),
    simpleTBWS((char)196, Color.white, true),
    simpleCROSS((char)197, Color.white, true),
    simpleLRW_DOUBLE_R_KNOB((char)198, Color.white, true),
    simpleLRW_DBL_LR_KNOBS((char)216, Color.white, true),
    simpleBRCS((char)217, Color.white, true),
    simpleTLCS((char)218, Color.white, true),

    // DOUBLE
    dblTLCS((char)201, Palette.paleWhite, true),
    dblTRCS((char)187, Palette.paleWhite, true),
    dblBLCS((char)200, Palette.paleWhite, true),
    dblBRCS((char)188, Palette.paleWhite, true),
    dblLRWS((char)186, Palette.paleWhite, true),
    dblTBWS((char)205, Palette.paleWhite, true),

    dblLRW_RIGHT_KNOB((char)199, Palette.paleWhite, true),
    dblLRW_LEFT_KNOB((char)182, Palette.paleWhite, true),

    dblLRW_DOUBLE_R_KNOB((char)204, Palette.paleWhite, true),
    dblLRW_DOUBLE_L_KNOB((char)185, Palette.paleWhite, true),

    dblT_SHAPE((char)203, Palette.paleWhite, true),
    dblT_SHAPE_UPSIDEDOWN((char)202, Palette.paleWhite, true),

    dblCROSS((char)206, Palette.paleWhite, true),
    dblTBW_UP_KNOB((char)207, Palette.paleWhite, true),
    dblTBW_DOWN_KNOB((char)209, Palette.paleWhite, true),

    dblTBW_DOUBLE_KNOB((char)216, Palette.paleWhite, true),
    dblLRW_DOUBLE_KNOB((char)215, Palette.paleWhite, true),


    // Room material
    dblTLC((char)201, Palette.paleWhite),
    dblTRC((char)187, Palette.paleWhite),
    dblBLC((char)200, Palette.paleWhite),
    dblBRC((char)188, Palette.paleWhite),
    dblLRW((char)186, Palette.paleWhite),
    dblTBW((char)205, Palette.paleWhite),

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
    private boolean targetted =  false;
    private boolean isStructure = false;

    Tile(char glyph, Color color, boolean isStructure)
    {
        this.swapable = true;
        this.glyph = glyph;
        this.color = color;
        this.backColor = Palette.darkestGray;

        this.isStructure = isStructure;
    }

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
    public static Tile returnTile(int c)
    {
        if(c == 4)
            return Tile.PLASMA_CANISTER_2;
        if(c == 7)
            return Tile.PLASMA_CANISTER;
        else if(c == 32)
            return Tile.INSIDE_FLOOR;
        else if(c == 182)
            return Tile.dblLRW_LEFT_KNOB;
        else if(c == 185)
            return Tile.dblLRW_DOUBLE_L_KNOB;
        else if(c == 186)
            return Tile.dblLRWS;
        else if(c == 187)
            return Tile.dblTRCS;
        else if(c == 188)
            return Tile.dblBRCS;
        else if(c == 199)
            return Tile.dblLRW_RIGHT_KNOB;
        else if(c == 200)
            return Tile.dblBLCS;
        else if(c == 201)
            return Tile.dblTLCS;
        else if(c == 202)
            return Tile.dblT_SHAPE_UPSIDEDOWN;
        else if(c == 203)
            return Tile.dblT_SHAPE;
        else if(c == 204)
            return Tile.dblLRW_DOUBLE_R_KNOB;
        else if(c == 205)
            return Tile.dblTBWS;
        else if(c == 206)
            return Tile.dblCROSS;
        else if(c == 207)
            return Tile.dblTBW_UP_KNOB;
        else if(c == 209)
            return Tile.dblTBW_DOWN_KNOB;
        else if(c == 215)
            return Tile.dblLRW_DOUBLE_KNOB;
        else if(c == 216)
            return Tile.dblTBW_DOUBLE_KNOB;
        else
            return Tile.randomTile();
    }

    public void target()
    {
        this.targetted = true;
    }
    public void animate()
    {

    }
    public boolean isStructure()
    {
        return isStructure;
    }
    public boolean isGround() {
        return this ==  Tile.FLOOR || this ==  Tile.INSIDE_FLOOR
        		|| this ==  Tile.BLASTED_TERRAIN || this == Tile.STAIRS_DOWN
        		|| this == Tile.STAIRS_UP		 || this == Tile.TERMINAL_ACESS;
    }
    public boolean isDiggable() 
    {
        return this == Tile.WALL;
    }
    public boolean isFloor()
    {
    	return this == Tile.INSIDE_FLOOR;
    }
    public boolean isRoom()
    {
        return this == Tile.simpleBLC || this == Tile.simpleBRC
        		|| this == Tile.simpleLRW || this == Tile.simpleTBW
        		|| this == Tile.simpleTLC || this == Tile.simpleTRC;
    }
    public boolean isWall() 
    {
        return this == Tile.WALL || this == Tile.RED_WALL; 
        		//|| this == Tile.simpleBLC || this == Tile.simpleBRC
        		//|| this == Tile.simpleLRW || this == Tile.simpleTBW
        		//|| this == Tile.simpleTLC || this == Tile.simpleTRC;
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
        if(targetted)
            return backColor;
        else
            return Palette.darkestGray;
        /*
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
    	*/

    }
    public void setColor(Color color)
    {
    	this.color = color;
    }
    public static Tile randomTile()
    {
    	return Tile.values()[new Random().nextInt(Tile.values().length)];
    }


}