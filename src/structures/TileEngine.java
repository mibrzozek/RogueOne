package structures;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;
import screens.Message;
import wolrdbuilding.*;

public class TileEngine
{
	private static Random r = new Random();
	private static final Color DEFAULT_F_COLOR = Palette.darkGray;
	private static Color fColor = DEFAULT_F_COLOR;


	public static void loadingAnimation(AsciiPanel terminal)
	{
		System.out.println("in animation");
		terminal.write("Center loaidng", 20, 2);
		renderBox(terminal, 83, 6, 1, 30, TileSet.SIMPLE, Palette.lightGray);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("out animation");
	}
	public static ArrayList<TilePoint> displayTilesWithTransparentBox(AsciiPanel terminal, ArrayList<TilePoint> tileMap,
			Integer gw, Integer gh, Integer gx, Integer gy, Color c)
	{
		ArrayList<TilePoint> tiles = new ArrayList<>();


		for (TilePoint tile : tileMap)
		{
			tiles.add(tile);
			//System.out.println(tileMap.size());
			//System.out.println(tiles.size());
			if(gx == null)
			{
				terminal.write(tile.glyph(), tile.x(), tile.y(), tile.foreground());
			}
			else
			{
				if(tile.x() >= gx && tile.x() < gx + gw -1
						&& tile.y() >= gy && tile.y() <gy+gh-1) // within box
				{
					if(tile.x() == gx || tile.y() == gy || tile.x() == (gx + gw -1) || tile.y() == (gy + gh -1))
						terminal.write((char)Tile.randomTile().glyph(), tile.x(), tile.y(), c);
					else
						terminal.write(tile.glyph(), tile.x(), tile.y(), c);
						
				}
				else 
					terminal.write(tile.glyph(), tile.x(), tile.y(), tile.foreground());
			}
		}

		return tiles;
	}
	
	public static ArrayList<TilePoint> growTilesEffect(ArrayList<TilePoint> tileMap)
	{
		ArrayList<TilePoint> rPoints = new ArrayList<TilePoint>();
		
		for(int i = 0; i < (tileMap.size() / 20); i++)
		{
			rPoints.add(tileMap.get(r.nextInt(tileMap.size())));
		}
		
		
		return null;
	}
	public static ArrayList<TilePoint> sparkleAnime(ArrayList<TilePoint> tileMap)
	{
		ArrayList<TilePoint> rPoints = new ArrayList<TilePoint>();
		ArrayList<Integer> indicies = new ArrayList();

		for (int i = 0; i < (tileMap.size() * .50); i++) // get random points
		{
			int rIndex = r.nextInt(tileMap.size() - 1);
			indicies.add(rIndex);

			TilePoint rTile = tileMap.get(rIndex);
			rPoints.add(rTile);
		}

		if((r.nextInt(3) + 1) == 1) // removes random points
			//tileMap.removeAll(rPoints);

		for(Integer i : indicies)
		{
			TilePoint t = tileMap.get(i);
			if(t.ascii() != 32)
			{
				//t.setGlyph((char)r.nextInt((250)));
				//t.setForeColor(Palette.darkGray);
				int d = r.nextInt(4);
				if(d == 0) // north
				{
					if(t.y() + 1 > 65)
						t.setY(t.y() + 1);
					if(t.x() + 1 < 85)
						t.setX(t.x() + 1);

				}
				if(d == 1) // East
				{
					if(t.x() + 1 < 85)
						t.setX(t.x() + 1);
				}
				if(d == 2) // South
				{
					if(t.y() + 1 < 65)
						t.setY(t.y() + 1);
				}
				if(d == 3) // West
				{
					if(t.x() - 1 > 0)
						t.setX(t.x() - 1);
				}
			}
		}
		//tileMap.addAll(rPoints);

		return null;
	}

	 static TilePoint getPointThatIsNot(int ascii, ArrayList<TilePoint> list)
	{
		TilePoint t;
		do
		{
			t = list.get(r.nextInt(list.size()-1));
		}while (t.ascii() != ascii);

		return t;
	}
	public static ArrayList<TilePoint> animateBox(ArrayList<TilePoint> tileMap)
	{
		ArrayList<TilePoint> rPoints = new ArrayList<TilePoint>();
		ArrayList indicies = new ArrayList();
		
		for(int i = 0; i < (tileMap.size() / 20); i++) // Get some random points
		{
			int rIndex = r.nextInt(tileMap.size()-1);
			indicies.add(rIndex);
			
			TilePoint rTile = tileMap.get(rIndex);
			rPoints.add(rTile);
		}
		if((r.nextInt(3) + 1) == 1) // removes random points
			tileMap.removeAll(rPoints);

		for(TilePoint t : rPoints)
		{
			int direction = r.nextInt(4) + 1;
			if(direction == 1)
			{
				if(t.x() + 1 < 85)
					t.setX(t.x() + 1);
			}
			else if(direction == 2)
			{
				if(t.x() - 2 > 0)
					t.setX(t.x() - 1);
			}
			else if(direction == 3)
			{
				if(t.y() + 1 < 62)
					t.setX(t.y() + 1);
			}
			else if(direction == 4)
			{
				if(t.y() - 1 > 0)
					t.setX(t.y() - 1);
			}
		}
		/* Moves to the left
		 * Nees to remove all duplicate above ^
		for(TilePoint t : rPoints)
		{
			if(t.x() + 2 < 85)
				t.setX(t.x() + 1);
		}
		*/
		tileMap.addAll(rPoints);

		return null;
	}
	public static ArrayList<TilePoint> renderItemList(AsciiPanel terminal, ArrayList<Item> list, int x, int y)
	{
		for(Item i : list)
		{
			if(i != null)
			{
				terminal.write("+" + Tile.INVENTORY_TYPE_ICON.glyph(), x, y, i.type().setColor(), Palette.darkestGray);
				terminal.write(i.name());
				y++;
			}
			else
			{
				terminal.write("+", x, y++, Palette.white, Palette.darkestGray);
			}
		}

		return null;
	}
	public static ArrayList<TilePoint> renderFrame(AsciiPanel terminal, int fw, int fh, int fx, int fy, TileSet ts, Color c)
	{
		ArrayList<TilePoint> tileMap = new ArrayList<>();


		for(int y = fy; y < fy+fh ; y++)
		{
			for(int x = fx; x < fx+fw; x++)
			{
				if(x == fx || x == fx+fw-1) // draws Left Right Wall and saves in list
				{
					tileMap.add(new TilePoint(ts.lrw().glyph(), c, x, y));
					terminal.write(ts.lrw().glyph(), x, y, c);
				}
				else if (y == fy || y == fy+fh-1) // draws Top Bottom Wall and saves in list
				{
					tileMap.add(new TilePoint(ts.tbw().glyph(), c, x, y));
					terminal.write(ts.tbw().glyph(), x, y, c);
				}
			}
		}
		terminal.write(ts.tlc().glyph(),fx, fy,  c);
		tileMap.add(new TilePoint(ts.tlc().glyph(), c, fx, fy));

		terminal.write(ts.trc().glyph(),fx + fw -1, fy,  c);
		tileMap.add(new TilePoint(ts.trc().glyph(), c, fx + fw -1, fy));

		terminal.write(ts.blc().glyph(),fx, fy + fh -1,  c);
		tileMap.add(new TilePoint(ts.blc().glyph(), c, fx, fy + fh -1));

		terminal.write(ts.brc().glyph(),fx + fw -1, fy+ fh - 1,  c);
		tileMap.add(new TilePoint(ts.brc().glyph(), c, fx + fw -1, fy+ fh - 1));

		return tileMap;
	}
	public static ArrayList<TilePoint> renderBox(AsciiPanel terminal, int bw, int bh, int bx, int by, TileSet ts, Color c)
	{
		fColor = c;
		renderBox(terminal, bw, bh,bx, by, ts);
		fColor = DEFAULT_F_COLOR;
		return null;
	}
	public static ArrayList<TilePoint> renderBox(AsciiPanel terminal, int bw, int bh, int bx, int by, TileSet ts)
	{
		ArrayList<TilePoint> tileMap = new ArrayList<>();
		for(int y = by; y < by+bh ; y++)
		{
			for(int x = bx; x < bx+bw; x++)
			{
				if(x == bx || x == bx+bw-1) {
					terminal.write(ts.lrw().glyph(), x, y, fColor);
					tileMap.add(new TilePoint(ts.lrw().glyph(), fColor, x, y));
				}
				else if (y == by || y == by+bh-1)
				{
					terminal.write(ts.tbw().glyph(), x, y, fColor);
					tileMap.add(new TilePoint(ts.tbw().glyph(), fColor, x, y));
				}
				else
				{
					if(bw < 85)
						terminal.write(" ", x, y);
					else
					{
						char glyph = Tile.randomTile().glyph();
						Color color = new Color(r.nextInt(255) + 0, 100 + 0, r.nextInt(255) + 0);
						
						terminal.write(glyph, x, y, color);
						tileMap.add(new TilePoint(glyph, color, x, y));
					}
				}
			}
		}
		terminal.write(ts.tlc().glyph(),bx, by,  fColor);
		tileMap.add(new TilePoint(ts.tlc().glyph(), fColor, bx, by));

		terminal.write(ts.trc().glyph(),bx + bw -1, by,  fColor);
		tileMap.add(new TilePoint(ts.trc().glyph(), fColor, bx + bw -1, by));

		terminal.write(ts.blc().glyph(),bx, by + bh -1,  fColor);
		tileMap.add(new TilePoint(ts.blc().glyph(), fColor, bx, by + bh -1));

		terminal.write(ts.brc().glyph(),bx + bw -1, by+ bh - 1,  fColor);
		tileMap.add(new TilePoint(ts.brc().glyph(), fColor, bx + bw -1, by+ bh - 1));

		return tileMap;
	}
	
	public static ArrayList<TilePoint> renderBox(AsciiPanel terminal, int bw, int bh, int bx, int by, String header)
	{
		ArrayList<TilePoint> tileMap = new ArrayList<>();
		for(int y = by; y < by+bh ; y++)
		{
			for(int x = bx; x < bx+bw; x++)
			{
				if(x == bx || x == bx+bw-1)
					terminal.write(Tile.lrWall.glyph(), x, y, Color.DARK_GRAY);
				else if (y == by || y == by+bh-1)
					terminal.write(Tile.tbWall.glyph(), x, y,  Color.DARK_GRAY);
				else
				{
					if(bw < 85)
						terminal.write(" ", x, y);
					else
					{
						char glyph = Tile.randomTile().glyph();
						Color color = new Color(r.nextInt(255) + 0, 100 + 0, r.nextInt(255) + 0);
						terminal.write(glyph, x, y, color);
						
						tileMap.add(new TilePoint(glyph, color, x, y));
					}
				}
			}
		}
		if(header != null)
			terminal.write(header, bx + 14, by, Palette.lightBlue);
		return tileMap;
	}
	/**
	 * Below are all the methods used to render the PlayScreen area
	 * 
	 *
	 */
	public static void displayMessages(AsciiPanel terminal, List<String> messages, int screenWidth, int screenHeight) 
	{
		int top = screenHeight - messages.size();
		int msgSpace = 10;
		int xo = 31;
		
		ArrayList<String> toDisplay = new ArrayList<>(10);
		ArrayList<Message> msgList = new ArrayList<>();
		
		for(String s : messages)
		{
			msgList.add(new Message(s, screenWidth - xo));
		}
		// Loads toDisplay w/ lines to display
		// lastIndex is the message counter
		// j in the loop is incremented if multi line message
		int lastIndex = msgList.size()-1;
		
		for (int i = 0; i < 10; i++)
		{
			if(lastIndex > -1)
			{
				if(i == 0) // Prints top message in white
				{	
					if(msgList.get(lastIndex).numLines() == 1)
						terminal.write(msgList.get(lastIndex).toString(), xo, screenHeight+i+1, Color.WHITE);	
					else
					{
						ArrayList<String> lines = msgList.get(lastIndex).getLines();

						for(int q = 0; q < lines.size(); q++)
						{
							terminal.write(lines.get(q), xo, screenHeight + (i) + 1, Color.WHITE);
							if(q != lines.size()-1)
								i += 1;
						}
					}
					lastIndex--;
				}
				else
				{
					if(msgList.get(lastIndex).numLines() == 1)
						terminal.write(msgList.get(lastIndex).toString(), xo, screenHeight+i+1, Color.DARK_GRAY);	
					else
					{
						ArrayList<String> lines = msgList.get(lastIndex).getLines();

						for(int q = 0; q < lines.size(); q++)
						{
							terminal.write(lines.get(q), xo, screenHeight + (i) + 1, Color.DARK_GRAY);
							if(q != lines.size()-1)
								i += 1;
						}
					}
					lastIndex--;
				}
			}
		}
	}
    public static void renderTarget(AsciiPanel terminal, int screenWidth, int screenHeight, Entity player)
    {
    	if(player.lastTargetedEnemy != null && player.lastTargetedEnemy.hp() > 0)
    	{
    		
        	for(int x = 0; x < 25; x++)
        	{
        		for(int y = screenHeight-5; y < screenHeight-2; y++)
        		{
        			
        			if(x == 0 || x == 25-1)
        				terminal.write(Tile.lrWall.glyph(), x, y, Color.DARK_GRAY);
    				else if (y == screenHeight-5 || y == screenHeight-2-1)
    					terminal.write(Tile.tbWall.glyph(), x, y,  Color.DARK_GRAY);
    				else
    					terminal.write(' ', x, y,  Color.WHITE);
        		}
        	}
        	
        	terminal.write("Target /", 1, screenHeight-4);
        	terminal.write(player.lastTargetedEnemy.name(), player.lastTargetedEnemy.tile().color());
        	terminal.write("/ " +player.lastTargetedEnemy.hp() + " hp");
        	
        	
        	terminal.write(Tile.tlCorner.glyph(), 0, screenHeight-5);
        	terminal.write(Tile.trCorner.glyph(), 24, screenHeight-5);
        	terminal.write(Tile.brCorner.glyph(), 24, -2+screenHeight-1);
        	terminal.write(Tile.blCorner.glyph(), 0, -2+screenHeight-1);
    	}
    }
	
    public static void renderStats(AsciiPanel terminal, int screenWidth, int screenHeight, int sh, int sw, Entity player )
    {
    	int statsW = screenWidth;
    	int statsH = 12;
    	
    	for(int x = 0; x < statsW; x++)
    	{
    		for(int y = 0; y < statsH; y++)
    		{
    			
    			if(x == 0 || x == statsW-1)
    				terminal.write(Tile.lrWall.glyph(), x, y+screenHeight, Color.DARK_GRAY);
				else if (y == 0 || y == statsH-1)
					terminal.write(Tile.tbWall.glyph(), x, y+screenHeight,  Color.DARK_GRAY);
				else
					terminal.write(' ', x, y+screenHeight,  Color.WHITE);
    		}
    	}
    	terminal.write(Tile.tlCorner.glyph(), 0, screenHeight);
    	terminal.write(Tile.trCorner.glyph(), screenWidth-1, screenHeight);
    	terminal.write(Tile.brCorner.glyph(), screenWidth-1, statsH+screenHeight-1);
    	terminal.write(Tile.blCorner.glyph(), 0, statsH+screenHeight-1);
    	
    	terminal.write("Shield [", 1, screenHeight+ 1);
    		renderPercentBlocks(terminal, Color.GREEN, 9, screenHeight+1, player.shield(), 100);
    	terminal.write("Vitals [", 1, screenHeight+ 2);
    		renderPercentBlocks(terminal, Color.PINK, 9, screenHeight+2, (int)player.stats.getVitals(), 1000);
    	terminal.write("Plasma [", 1, screenHeight+ 3);
    		renderPercentBlocks(terminal, Color.CYAN, 9, screenHeight+3, player.plasma(), 10000);
    	terminal.write("Crypto [" + player.crypto(), 1, screenHeight+ 4);
    	terminal.write("Dirctn [" + player.getCardinal().getDirection(), 1, screenHeight+ 5);
    	terminal.write("Stlth  [" + player.getStealth(), 1, screenHeight+ 6);
    	
    	
    	// Write the player name at bottom
    	terminal.write((char)187, 13, screenHeight + 11, Palette.darkGray);
    	for(int i = 0; i < player.stats.getName().length() ; i++)
    		terminal.write(" ");
    	terminal.write((char)201, Palette.darkGray);
    	terminal.write(player.stats.getName(), 14, screenHeight + 11, Palette.lightRed);
    
    	
    	renderLogScreenArea(terminal, screenHeight, screenWidth);
    	
    }
    public static void renderPercentBlocks(AsciiPanel terminal, Color color, int x, int y, int value, int max)
    {
    	double percent = 10 * ((double)value/max);
    	double xi;

    	for(int i = 0; i < percent; i++)
    	{
    		terminal.write((char) 223, x+i, y, color);
    		xi=i;
    	}
    	terminal.write(" "+ String.format("%.2f",percent*10) + " %", color);
    }
    public static void renderLogScreenArea(AsciiPanel terminal, int screenHeight, int screenWidth)
    {
    	// Produces divider
    	int ox = 30;
    	
    	for (int i = 0; i < 12; i++)
    		terminal.write((char)186, ox, screenHeight + i, Color.DARK_GRAY);

    	terminal.write((char)203, ox, screenHeight , Color.DARK_GRAY);
    	terminal.write((char)202, ox, screenHeight+11, Color.DARK_GRAY );
    	// Updates display array
    	
    	terminal.write((char)187 + "     " + (char)201, ox + 45, screenHeight + 11, Palette.darkGray);
    	terminal.write(" LOG ", ox + 46, screenHeight + 11, Palette.lightBlue);
    		
    }

}