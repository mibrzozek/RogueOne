package wolrdbuilding;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Palette
{
	public static Color purple = new Color(148,0,211, 0);
	public static Color white = new Color(255,255,255, 0);
	public static Color paleWhite = new Color(222,222,222, 0);
	public static Color black = new Color(0,0,0, 0);
	
	public static Color lightGray = new Color(204,204,204, 0);
	public static Color gray = new Color(153,153,153, 0);
	public static Color darkGray = new Color(102,102,102, 0);
	public static Color darkerGray = new Color(35,35,35, 0);
	public static Color darkestGray = new Color(10,10,10, 0);
	
	public static Color yellow = new Color(255,255,0, 0);
	public static Color darkYellow = new Color(255,204,0, 0);
	
	public static Color lightRed = new Color(255,90,102, 0);
	public static Color red = new Color(255,51,51, 0);
	public static Color darkRed = new Color(180, 0, 0, 0);
	public static Color darkerRed = new Color(220, 0, 0, 0);

	public static Color methane = new Color(1, 20,50, 200); // darkBlue

	public static Color lightBlue = new Color(51, 150,150, 0);
	public static Color blue = new Color(51,153,255, 0);
	public static Color darkBlue = new Color(0,0,255, 0);
	
	public static Color lightGreen = new Color(102,255,102, 0);
	public static Color green = new Color(0,204,0, 0);
	
	public static Color brown = new Color(100 ,42 ,42, 0);
	

	public static Random r =  new Random();
	
	public static Color randomColor()
	{
		ArrayList<Color> colors = new ArrayList<>();
		colors.add(white);
		colors.add(black);
		colors.add(lightGray);
		colors.add(gray);
		colors.add(darkGray);
		colors.add(lightRed);
		colors.add(red);
		colors.add(darkRed);
		colors.add(lightBlue);
		colors.add(blue);
		colors.add(darkBlue);
		
		return colors.get(r.nextInt(colors.size() -1));
	}
	public static Color randomNewColor()
	{
		return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), 0);
	}
}
