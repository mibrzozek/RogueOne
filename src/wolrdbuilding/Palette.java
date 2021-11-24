package wolrdbuilding;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Palette
{
	public static Color theNewPerfect = new Color(18,35,36);
	public static Color theNewBlue = new Color(8,32,40);
	public static Color theNewBluesShadow = new Color(0,38,55);
	public static Color theNewDarkerBlue = new Color(4,16,20);
	public static Color theNewMagenta = new Color(4,8,20);

	public static Color purple = new Color(148,0,211);
	public static Color paperPurple = new Color(198,172,242);
	public static Color pastelPurple = new Color(210,149,255);
	public static Color monoPurple = new Color(120,91, 131);


	public static Color white = new Color(255,255,255);
	public static Color paleWhite = new Color(222,222,222);
	public static Color morePaleWhite = new Color(200,200,200);
	public static Color black = new Color(0,0,0);
	
	public static Color lightGray = new Color(204,204,204);
	public static Color gray = new Color(153,153,153);
	public static Color darkGray = new Color(102,102,102);
	public static Color darkerGray = new Color(35,35,35);
	public static Color darkestGray = new Color(10,10,10);

	public static Color monoYellow = new Color(131,126, 91);
	public static Color pastelYellow = new Color(252,255,149);
	public static Color paperYellow = new Color(239,242,172);
	public static Color yellow = new Color(255,255,0);
	public static Color darkYellow = new Color(255,204,0);

	public static Color pastelPink = new Color(255,149,203);
	public static Color palePink = new Color(252,129,178);
	public static Color paperPink = new Color(242,172,200);

	public static Color monoRed = new Color(131,91, 91);
	public static Color pastelRed = new Color(255,149,149);
	public static Color lightRed = new Color(255,90,102);
	public static Color red = new Color(255,51,51);
	public static Color darkRed = new Color(180, 0, 0);
	public static Color darkerRed = new Color(220, 0, 0);

	public static Color pastelOrange = new Color(255,178,149);
	public static Color monoOrange = new Color(131,107, 91);

	public static Color methane = new Color(1, 20,50, 200); // darkBlue


	public static Color manaBlue = new Color(82,82, 98);
	public static Color newManaBlue = new Color(102,102, 98);
	public static Color manaTeal = new Color(82,98, 96);



	public static Color manaTealHarder = new Color(54,97, 91);

	public static Color monoPerfect = new Color(91,131, 128);
	public static Color monoGrayTeal = new Color(91,119, 131);
	public static Color monoGrayBlue = new Color(91,94, 131);
	public static Color perfectBlue = new Color(149,255,252);
	public static Color pastelBlue = new Color(149,203,255);
	public static Color paperBlue = new Color(172,214,242);
	public static Color lightBlue = new Color(51, 150,150);
	public static Color blue = new Color(51,153,255);
	public static Color darkBlue = new Color(0,0,255);
	
	public static Color lightGreen = new Color(102,255,102);

	public static Color monoGreen = new Color(100,131, 91);
	public static Color pastelGreen = new Color(149,255,164);
	public static Color paperGreen = new Color(172,242,177);
	public static Color green = new Color(0,204,0);
	
	public static Color brown = new Color(100 ,42 ,42);

	public static Random r =  new Random();
    public static Color brightBlue = AsciiPanel.brightBlue;
	public static Color cyan = AsciiPanel.cyan;
	public static Color pink = Color.pink;
	public static Color darkGreen = new Color(87 ,146 ,9);
	public static Color darkerGreen = new Color(61 ,104 ,6);
	public static Color darkBrown = new Color(94,70,37);
    public static Color lightBrown = new Color(192,108,35);

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
