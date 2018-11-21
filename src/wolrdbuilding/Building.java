package wolrdbuilding;

import java.awt.Point;

public class Building 
{
	int width;
	int height;
	int x;
	int y;
	
	Point buildingSeed;
	
	public Building(int width, int height)
	{
		this.width  = width;
		this.height = height;
		this.x = width;
		this.y = height;
		
		this.buildingSeed = new Point();
	}
	
	
}
