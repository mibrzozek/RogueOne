package wolrdbuilding;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import asciiPanel.AsciiPanel;
import wolrdbuilding.Palette;

public enum Tile 
{
	// World materials
    CUSTOM(' ', Palette.white),
	UNKNOWN(' ', Palette.white),
    FLOOR((char)250, Palette.randomNewColor()),
    TERMINAL_ACESS((char)249, Palette.white),
    INSIDE_FLOOR((char)250, Palette.morePaleWhite),
    BLASTED_TERRAIN((char)176, Palette.brown),
    WHITE_TERRAIN((char)176, Palette.white),
    WALL((char)177, Palette.monoGrayTeal),
    RED_WALL((char)177, Palette.monoGrayBlue),
    BROWN_WALL((char)177, Palette.randomColor()),
    SILVER_WALL((char)177, Palette.randomColor()),
    BOUNDS('X', Palette.black),
    CURSOR('x', Palette.purple),


    // TRAPS / OBSTACLES / LAVA
    UP_DOWN_DBL_LASER((char)186, Palette.purple),
    LEFT_RIGHT_DBL_LASER((char)205, Palette.purple),


    UP_DOWN_SINGLE_LASER((char)179, Palette.purple),
    LEFT_RIGHT_SINGLE_LASER((char)196, Palette.purple),

    //
    INVENTORY_TYPE_ICON((char)254, Palette.purple, true),
    
    //Projectiles
    DEAD('X', Palette.purple),
    TAGGED('x', Palette.purple),
    TAGGED_PLAYER('@', Palette.darkRed),
    
    Y_SMALL('.', Palette.purple),
    R_SNIPER((char) 15, Palette.purple),
    
    Y_MED((char) 4, Palette.purple),
    Y_LARGE((char) 9, Palette.purple),
    
    R_MED((char) 248, Palette.purple),
    G_SMALL((char) 4, Palette.purple),
    
    
    WATER((char) 9, Palette.blue),
    
    
    STEAM((char)46, Palette.paleWhite),
    
    

    /*  STRUCTURES

        The following tiles are to always be displayed
        These are the primary building components for
        all the structures in the game
     */
    PLASMA_CANISTER((char)7, Palette.blue, true),
    PLASMA_CANISTER_2((char)4, Palette.blue, true),
    INTERFACE((char)216, Palette.paleWhite, true),
    BLOCK_CIRCLE((char)8, Palette.paleWhite, true),

    // DECORATIONS
    UP_ARROW((char)30, Palette.purple, true),
    DOWN_ARROW((char)31, Palette.purple, true),
    LEFT_ARROW((char)17, Palette.purple, true),
    RIGHT_ARROW((char)16, Palette.purple, true),

    BACKSLASH((char)92, Palette.purple, true),
    FORWARDSLASH((char)47, Palette.purple, true),
    DOUBLE_LEFT_ARROW((char)174, Palette.purple, true),
    V_DOWN_ARROW((char)118, Color.WHITE, true),

    CARROT_UP_ARROW((char)94, Color.RED, true),
    DOUBLE_RIGHT_ARROW((char)175, Palette.purple, true),
    
    
    // Fire
    
    FIRE_ONE((char)34, Palette.yellow),
    FIRE_TWO((char)39, Palette.lightRed),
    FIRE_THREE((char)157, Palette.darkRed),
    FIRE_FOUR((char)58, Palette.darkerRed),

    BURNED_FLOOR_1((char)176, Palette.gray),
    BURNED_FLOOR_2((char)177, Palette.darkerGray),
    BURNED_FLOOR_3((char)178, Palette.darkerGray),
    BURNED_FLOOR_4((char)250, Palette.darkestGray),


    // SIMPLE TO DOUBLE
    S2D183((char)183, Color.WHITE, true),
    S2D184((char)184, Color.WHITE, true),
    S2D189((char)189, Color.WHITE, true),
    S2D190((char)190, Color.WHITE, true),
    S2D211((char)211, Color.WHITE, true),
    S2D212((char)212, Color.WHITE, true),
    S2D213((char)213, Color.WHITE, true),
    S2D214((char)214, Color.WHITE, true),


    // SIMPLE

    simpleLRWS((char)179, Color.WHITE, true),
    simpleLRW_L_KNOB((char)180, Color.WHITE, true),
    simpleLRW_L_DBL_KNOB((char)181, Color.WHITE, true),
    simpleTRCS((char)191, Color.WHITE, true),
    simpleBLCS((char)192, Color.WHITE, true),
    simpleT_Shape_UPSIDEDOWN((char)193, Color.WHITE, true),
    simpleT_Shape((char)194, Color.WHITE, true),
    simpleLRW_R_KNOB((char)195, Color.WHITE, true),
    simpleTBWS((char)196, Color.WHITE, true),
    simpleCROSS((char)197, Color.WHITE, true),
    simpleLRW_DOUBLE_R_KNOB((char)198, Color.WHITE, true),
    simpleLRW_DBL_LR_KNOBS((char)216, Color.WHITE, true),
    simpleBRCS((char)217, Color.WHITE, true),
    simpleTLCS((char)218, Color.WHITE, true),

    // DOUBLE
    dblLRW_LEFT_KNOB((char)182, Palette.paleWhite, true),
    dblLRW_DOUBLE_L_KNOB((char)185, Palette.paleWhite, true),
    dblLRWS((char)186, Palette.paleWhite, true),
    dblTRCS((char)187, Palette.paleWhite, true),
    dblBRCS((char)188, Palette.paleWhite, true),
    dblLRW_RIGHT_KNOB((char)199, Palette.paleWhite, true),
    dblBLCS((char)200, Palette.paleWhite, true),
    dblTLCS((char)201, Palette.paleWhite, true),
    dblT_SHAPE_UPSIDEDOWN((char)202, Palette.paleWhite, true),
    dblT_SHAPE((char)203, Palette.paleWhite, true),
    dblLRW_DOUBLE_R_KNOB((char)204, Palette.paleWhite, true),
    dblTBWS((char)205, Palette.paleWhite, true),
    dblCROSS((char)206, Palette.paleWhite, true),
    dblTBW_UP_KNOB((char)207, Palette.paleWhite, true),
    dblTBW_DOWN_KNOB((char)209, Palette.paleWhite, true),
    dblT_W_FLAT_TOP((char)210, Palette.paleWhite, true),
    dblLRW_DOUBLE_KNOB((char)215, Palette.paleWhite, true),
    dblTBW_DOUBLE_KNOB((char)216, Palette.paleWhite, true),


    // Room material
    dblTLC((char)201, Palette.paleWhite),
    dblTRC((char)187, Palette.paleWhite),
    dblBLC((char)200, Palette.paleWhite),
    dblBRC((char)188, Palette.paleWhite),
    dblLRW((char)186, Palette.paleWhite),
    dblTBW((char)205, Palette.paleWhite),

    CLOSED_DOOR((char)240, Palette.manaTeal),
    OPEN_DOOR((char)240, Palette.monoRed),
    
	lrWall((char)186, Color.WHITE),
	tbWall((char)205, Color.WHITE),
	tlCorner((char)218, Color.WHITE),
	trCorner((char)170, Color.WHITE),
	blCorner((char)200, Color.WHITE),
	brCorner((char)217, Color.WHITE),
	
	simpleTLC((char)218, Color.WHITE),
	simpleTRC((char)191, Color.WHITE),
	simpleBLC((char)192, Color.WHITE),
	simpleBRC((char)217, Color.WHITE),
	simpleTBW((char)196, Color.WHITE),
	simpleLRW((char)179, Color.WHITE),

	STAIRS_DOWN('>', Color.WHITE),
    STAIRS_UP('<', Color.WHITE),
	STAIRS_EXIT('<', Color.WHITE),
	
	// Entity Tiles
	PLAYER('@', Palette.paleWhite, Palette.darkGray),
	FUNGUS('f', Palette.green, Palette.lightRed),
	HITMAN('h', Palette.paleWhite, Palette.lightRed),
	TRADER('T', Palette.yellow, Palette.lightRed),
	MUTANT('M', Palette.blue, Palette.lightRed),
	DROID((char)225, Palette.darkRed, Palette.lightRed),
	ROGUE( (char)146, Palette.red, Palette.lightRed),
	MECH('M', Palette.paleWhite, Palette.lightRed),
	JUNKIE('J', Palette.blue, Palette.lightRed),
	
	// COMBUSTABLE TILES
	
    METHANE('m',Palette.methane, Palette.darkestGray);
	
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
    public static Tile getFire()
    {
        if(Math.random() < 0.5)
            return (Math.random() < 0.5 ? Tile.FIRE_ONE : Tile.FIRE_TWO);
        else
            return (Math.random() < 0.5 ? Tile.FIRE_THREE : Tile.FIRE_FOUR);

    }
    public static Tile getBurnedTile()
    {
        if(Math.random() < 0.5)
            return (Math.random() < 0.5 ? Tile.BURNED_FLOOR_1 : Tile.BURNED_FLOOR_2);
        else
            return (Math.random() < 0.5 ? Tile.BURNED_FLOOR_3 : Tile.BURNED_FLOOR_4);

    }
    public static Tile returnTile(int c)
    {
        if(c == 4)
            return Tile.PLASMA_CANISTER_2;
        else if(c == 7)
            return Tile.PLASMA_CANISTER;
        else if(c == 8)
            return Tile.BLOCK_CIRCLE;
        else if(c == 16)
            return Tile.LEFT_ARROW;
        else if(c == 17)
            return Tile.RIGHT_ARROW;
        else if(c == 30)
            return Tile.UP_ARROW;
        else if(c == 31)
            return Tile.DOWN_ARROW;
        else if(c == 32)
            return Tile.INSIDE_FLOOR;
        else if(c == 47)
            return Tile.FORWARDSLASH;
        else if(c == 92)
            return Tile.BACKSLASH;
        else if(c == 94)
            return Tile.CARROT_UP_ARROW;
        else if(c == 118)
            return Tile.V_DOWN_ARROW;
        else if(c == 174)
            return Tile.DOUBLE_LEFT_ARROW;
        else if(c == 175)
            return Tile.DOUBLE_RIGHT_ARROW;
        else if(c == 179)
            return Tile.simpleLRWS;
        else if(c == 180)
            return Tile.simpleLRW_L_KNOB;
        else if(c == 182)
            return Tile.dblLRW_LEFT_KNOB;
        else if(c == 183)
            return Tile.S2D183;
        else if(c == 184)
            return Tile.S2D184;
        else if(c == 185)
            return Tile.dblLRW_DOUBLE_L_KNOB;
        else if(c == 186)
            return Tile.dblLRWS;
        else if(c == 187)
            return Tile.dblTRCS;
        else if(c == 188)
            return Tile.dblBRCS;
        else if(c == 189)
            return Tile.S2D189;
        else if(c == 190)
            return Tile.S2D190;
        else if(c == 191)
            return Tile.simpleTRCS;
        else if(c == 192)
            return Tile.simpleBLCS;
        else if(c == 193)
            return Tile.simpleT_Shape_UPSIDEDOWN;
        else if(c == 194)
            return Tile.simpleT_Shape;
        else if(c == 195)
            return Tile.simpleLRW_R_KNOB;
        else if(c == 196)
            return Tile.simpleTBWS;
        else if(c == 197)
            return Tile.simpleCROSS;
        else if(c == 198)
            return Tile.simpleLRW_DOUBLE_R_KNOB;
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
        else if(c == 210)
            return Tile.dblTBW_DOWN_KNOB;
        else if(c == 211)
            return Tile.S2D211;
        else if(c == 212)
            return Tile.S2D212;
        else if(c == 213)
            return Tile.S2D213;
        else if(c == 214)
            return Tile.S2D214;
        else if(c == 215)
            return Tile.dblLRW_DOUBLE_KNOB;
        else if(c == 216)
            return Tile.dblTBW_DOUBLE_KNOB;
        else if(c == 217)
            return Tile.simpleBRCS;
        else if(c == 218)
            return Tile.simpleTLCS;
        else if(c == 249)
            return Tile.TERMINAL_ACESS;
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
        		|| this == Tile.STAIRS_UP		 || this == Tile.TERMINAL_ACESS
                || this == Tile.METHANE 
                || this == Tile.FIRE_ONE
                || this == Tile.FIRE_TWO
                || this == Tile.FIRE_THREE
                || this == Tile.FIRE_FOUR
                || this == Tile.BURNED_FLOOR_1
                || this == Tile.BURNED_FLOOR_2
                || this == Tile.BURNED_FLOOR_3
                || this == Tile.BURNED_FLOOR_4
                || this == Tile.STEAM
                || this == Tile.OPEN_DOOR
                ;
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
    public boolean isFire()
    {
        return this == Tile.FIRE_ONE || this == Tile.FIRE_TWO || this == Tile.FIRE_THREE 
        		|| this == Tile.FIRE_FOUR;
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