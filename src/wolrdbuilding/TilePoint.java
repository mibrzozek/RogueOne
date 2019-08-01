package wolrdbuilding;

import java.awt.Color;
import java.io.Serializable;

public class TilePoint implements Serializable
{
	private	char glyph;
	private Color color, fColor, bColor;
	private  int x, y, ascii;
	private Point p;

	public TilePoint(int x, int y, int ascii, Color fColor, Color bColor)
	{
		this.x = x;
		this.y = y;
		this.ascii = ascii;
		this.glyph = (char)ascii;
		this.fColor = fColor;
		this.bColor = bColor;
	}
	public TilePoint(char glyph, Color color, int x, int y)
	{
		this.glyph = glyph;
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public void setGlyph(char glyph)		{ this.glyph = glyph; }
	public void setForeColor(Color color)		{ this.fColor = color; }
	public void setBackColor(Color color)		{ this.bColor = color; }
	public void setX(int x)					{ this.x = x; }
	public void setY(int y)				{ this.y = y; }
	public void setPoint(Point p)			{this.p = p;	}
	
	public char glyph()		{ return this.glyph; }
	public Color color()	{ return this.color; }
	public Point point() 	{ return p;	}

	public Color foreground()	{ return this.fColor; }
	public Color background()	{ return this.bColor; }

	public int x()			{ return this.x; }
	public int y()			{ return this.y; }
	public int ascii()			{ return this.ascii; }
	@Override
	public String toString()
	{
		return "x : " + x + " y : " + y +" ascii : " + ascii
				+ "\n\tGlyph : " + glyph + "\tColor = " + fColor;
	}
	@Override
	public boolean equals(Object obj)
	{
		TilePoint tp = null;
		if(obj instanceof TilePoint)
			tp = (TilePoint)obj;
		else return false;
		
		if(tp != null)
		{
			if(tp.x() == this.x() && tp.y == this.y)
			{
				return true;
			}
		}
		return false;
	}
	
}
