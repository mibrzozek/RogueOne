package wolrdbuilding;

import java.awt.Color;

public class TilePoint
{
	private	char glyph;
	private Color color, fColor, bColor;
	private  int x, y, ascii;


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
	public void setColor(Color color)		{ this.color = color; }
	public void setX(int x)					{ this.x = x; }
	public void setYlyph(int y)				{ this.y = y; }
	
	public char glyph()		{ return this.glyph; }
	public Color color()	{ return this.color; }

	public Color foreground()	{ return this.fColor; }
	public Color background()	{ return this.bColor; }

	public int x()			{ return this.x; }
	public int y()			{ return this.y; }
	public int ascii()			{ return this.ascii; }
	@Override
	public boolean equals(Object obj)
	{
		TilePoint tp = null;
		if(obj instanceof TilePoint)
			tp = (TilePoint)obj;
		
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
