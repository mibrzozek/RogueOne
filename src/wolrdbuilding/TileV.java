package wolrdbuilding;


import java.awt.*;
import java.io.Serializable;

public class TileV implements Serializable
{
    private int asciiValue;
    private Tile t;

    private char glyph;
    private Color colorF;
    private Color colorB;

    private boolean isStructure, isFloor;

    public TileV(Tile t)
    {
        this.t = t;
        glyph = t.glyph();
        colorF = t.color();
        colorB = t.backColor();
    }
    public TileV(int asciiValue, char glyph, Color c)
    {
        this.asciiValue = asciiValue;
        this.glyph = (char) asciiValue;
        this.colorF = c;

        this.t = Tile.CUSTOM;
    }
    public TileV(char glyph, Color f)
    {
        this.asciiValue = asciiValue;
        this.glyph = (char) asciiValue;
        this.colorF = f;

        this.t = Tile.CUSTOM;
    }
    public TileV(char glyph, Color f, Color b)
    {
        this.asciiValue = asciiValue;
        this.glyph = (char) asciiValue;
        this.colorF = f;
        this.colorB = b;

        this.t = Tile.CUSTOM;
    }

    public char getGlyph()
    {
        if(t != Tile.CUSTOM)
            return t.glyph();
        else
            return glyph;
    }
    public int getAsciiValue()
    {
        return asciiValue;
    }
    public Color getColorF()
    {
        return colorF;
    }
    public Color getBackColor()
    {
        return t.backColor();
    }
    public Color getColorB(){return colorB; }
    public void setGlyph(char glyph)
    {
        this.glyph = glyph;
    }
    public void setGlyph(Tile t)
    {
        this.t = t;
        this.glyph = t.glyph();
        this.colorF = t.color();
    }
    public void setTile(char glyph, Color f, Color b, boolean isStructure)
    {
        this.glyph = glyph;
        this.colorF = f;
        this.colorB = b;
        this.t = Tile.CUSTOM;
        this.isStructure = isStructure;

        glyphCheck();
    }
    /*
        glyphCheck

        Checks the glyphs to see if they correspond to special ascii codes which
        are already defined in the enum Tile
     */
    public void glyphCheck()
    {
        if(glyph == (char)32)
        {
            this.t = Tile.INSIDE_FLOOR;
            this.glyph = t.glyph();
            this.colorF = t.color();
            this.colorB = t.color();
            this.isStructure = false;
        }
        else if(glyph == (char)249)
        {
            this.t = Tile.TERMINAL_ACESS;
            this.glyph = t.glyph();
            this.colorF = t.color();
            this.colorB = t.color();
            this.isStructure = false;
        }
    }
    public void setTile(Tile t)
    {
        this.t = t;
        this.glyph = t.glyph();
        this.colorF = t.color();
    }
    public boolean isDiggable()
    {
        if(getGlyph() == Tile.WALL.glyph())
            return true;
        else
            return true;
    }
    public boolean isFloor()
    {
        return t.isFloor();
    }
    public boolean isStructure()
    {
        if(t == Tile.CUSTOM)
        {
            return true;
        }
        else
            return false;
    }
    public boolean isGround()
    {
        if(t != null)
            return t.isGround();
        else
            return false;
    }
    public Tile getTile()
    {
        return this.t;
    }

    public void setBackColor(Color backColor) {
        this.colorB = backColor;
    }
}
