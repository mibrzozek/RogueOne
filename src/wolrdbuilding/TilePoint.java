package wolrdbuilding;

import java.awt.Color;

public class TilePoint
{
	private	char glyph;
	private Color color;
	private  int x, y;
	
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
	public int x()			{ return this.x; }
	public int y()			{ return this.y; }
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
