package structures;

import Managers.AmmoManager;
import asciiPanel.AsciiPanel;
import entities.Effect;
import entities.Entity;
import items.Item;
import items.Type;
import items.Weapon;
import items.WeaponStats;
import screens.Message;
import wolrdbuilding.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class TileEngine
{
	private static Random r = new Random();
	private static final Color DEFAULT_F_COLOR = Palette.darkGray;
	private static Color fColor = DEFAULT_F_COLOR;
	private static boolean fillBox = false;


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
				{
					//if(tile.x() < terminal.getWidth() && tile.y() < terminal.getHeight())
						terminal.write(tile.glyph(), tile.x(), tile.y(), tile.foreground());
				}
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
	public static ArrayList<TilePoint> renderBox(AsciiPanel terminal, int bw, int bh, int bx, int by, TileSet ts, boolean filledBox)
	{
		fillBox = filledBox;
		renderBox(terminal, bw, bh,bx, by, ts);
		fillBox = false;
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

					if(fillBox)
						terminal.write(" ", x, y);
					/*
					else
					{
						char glyph = Tile.randomTile().glyph();
						Color color = new Color(r.nextInt(255) + 0, 100 + 0, r.nextInt(255) + 0);
						
						terminal.write(glyph, x, y, color);
						tileMap.add(new TilePoint(glyph, color, x, y));
					}
					*/
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
	 *
	 *
	 *
	 *
	 */
	public static void displayMessagesUI(AsciiPanel terminal, List<String> messages, int screenWidth, int screenHeight)
	{
		int top = screenHeight - messages.size();
		int msgSpace = 10;
		int xo = 32;
		
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


		TileEngine.renderBox(terminal, screenWidth-(xo) +1, 12, xo-1, screenHeight, TileSet.SIMPLE, Palette.gray);

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
							if(screenHeight+ i + 1 > terminal.getHeight() || xo > terminal.getWidth())
								continue;

							terminal.write(lines.get(q), xo, screenHeight + (i) + 1, Color.DARK_GRAY);
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
	public static void displayWeaponUI(AsciiPanel terminal, int screenWidth, int screenHeight, int sh, int sw, World w, RexReader rex, Entity player)
	{
		//Entire Weapon UI Area
		TileEngine.renderBox(terminal, 31, 55, 0, 0, TileSet.SIMPLE, Palette.gray);

		HashMap<String, ArrayList<TilePoint>> structureMap;
		ArrayList<TilePoint> testStructure;
		structureMap = rex.getStructures();

		if(player.inventory().getPrimaryWeapon() == null)
		{
			testStructure = structureMap.get("NO_WEAPON.csv");
			TileEngine.displayTilesWithTransparentBox(terminal, testStructure, 0,0,  31, 12, Palette.gray);
			TileEngine.renderBox(terminal, 31, 11, 0, 0, TileSet.SIMPLE, Palette.gray);
		}
		else
		{
			Weapon primWeap = player.inventory().getPrimaryWeapon();
			String resourceName = (primWeap.name().toString().replaceAll("\\s+","") + ".csv");
			System.out.println(resourceName);

			if(structureMap.get(resourceName) != null)
			{
				testStructure = structureMap.get(resourceName);
			}
			else
			{
				testStructure = structureMap.get("finAsciiAR.csv");
			}

			TileEngine.displayTilesWithTransparentBox(terminal, testStructure, 0,0,  31, 12, Palette.gray);
			TileEngine.renderBox(terminal, 31, 11, 0, 0, TileSet.SIMPLE, Palette.gray);
			TileEngine.renderDisplayPlate(terminal, 0, 10, 31, primWeap.name(), false, Palette.methane, Palette.paleWhite);

			String ammoValue = "0";
			Color ammoColor  = Color.red;
			Type equippedWeaponCaliber = AmmoManager.identifyAmmo(player.inventory().getPrimaryWeapon());
			if(!player.inventory().get(equippedWeaponCaliber).isEmpty())
			{
				ammoValue = Integer.toString(player.inventory().get(equippedWeaponCaliber).get(0).value());
				ammoColor = Palette.methane;
			}
			TileEngine.renderDisplayPlate(terminal, 0, 11, 31, "Ammo : " + ammoValue, false, ammoColor, Palette.paleWhite);
			Set<Item> gunAttachments = new HashSet<>(primWeap.getAllAttachments());

			if(gunAttachments != null)
			{
				TileEngine.renderDisplayPlate(terminal, 0, 20, 31, "Attachments", false, Palette.darkestGray, Palette.paleWhite);

				int count  = 0;
				for(Item i : gunAttachments)
				{
					TileEngine.renderWeaponStatPlate(terminal, 1, 21 + count++, 29, i.name(), "", null, null);
				}
			}
			//System.out.println(primWeap.getAllAttachments().size() + "Number of attachments");

			WeaponStats wStats = primWeap.getStats();
			if(wStats == null)
				return;

			TileEngine.renderWeaponStatPlate(terminal, 1,12,29, "Damage", Integer.toString(wStats.getDamage()), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,13,29, "Fire Rate", Integer.toString(wStats.getBulletsPerTurn()), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,14,29, "Magazine", Integer.toString(wStats.getMagazineCapacity()), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,15,29, "Reload Speed", Integer.toString(wStats.getReloadSpeed()), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,16,29, "Range", Integer.toString(wStats.getRange()), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,17,29, "Fire Mode",wStats.getMode().toString(), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,18,29, "Bullets In Mag",Integer.toString(wStats.getBulletsInMagazine()), null, null);
			TileEngine.renderWeaponStatPlate(terminal, 1,19,29, "Turns until ready",Integer.toString(primWeap.getTurnsUntilReloaded()), null, null);

		}
	}
	public static void renderWeaponStatPlate(AsciiPanel terminal, int x, int y, int length, String statLabel, String statValue, Color fc, Color bc)
	{
		int lengthOfLabels = statLabel.length() + statValue.length();
		int lengthOfPeriods = length - lengthOfLabels;
		String finalLabel = statLabel;

		if(lengthOfLabels > length)
		{
			System.out.println("Not enough space to add periods");
		}
		else
		{
			for(int i = 0; i < lengthOfPeriods; i++)
			{
				finalLabel += ".";
			}
			finalLabel += statValue;
			terminal.write(finalLabel, x, y);
		}
	}
    public static void displayStatsUI(AsciiPanel terminal, int screenWidth, int screenHeight, int sh, int sw, World w )
    {
    	int statsW = screenWidth;
    	int statsH = 12;
		Entity player = w.getPlayer();

    	terminal.write("Shield " + (char)218, 1, screenHeight + 1);
			renderPercentBlocksV2(terminal, 1, screenHeight+1, "Shield", (int)player.shield(), 250, Palette.monoPerfect);
    	terminal.write("Vitals " + (char)179, 1, screenHeight+ 2);
    		renderPercentBlocksV2(terminal, 1, screenHeight+2, "Vitals", (int)player.stats.vitals.getVitals(), 1000, Palette.monoPurple);
    	terminal.write("Plasma " + (char)179, 1, screenHeight+ 3);
			renderPercentBlocksV2(terminal, 1, screenHeight+3, "Plasma", (int)player.plasma(), 10000, Palette.monoGreen);
		terminal.write("02 Air " + (char)179 , 1, screenHeight+ 4);
			renderPercentBlocksV2(terminal, 1, screenHeight+4, "Ambient 02", (int)w.getAir().getOxygen(), 10000, Palette.lightBlue);
		terminal.write("02 Rsrv" + (char)179 , 1, screenHeight+ 5);
			renderPercentBlocksV2(terminal, 1, screenHeight+5, "Reserve 02", (int)player.inventory().getTypeDuration(Type.OXYGEN), 1000, Palette.lightBlue);
		TileEngine.renderPercentBlocksV2(terminal, 1, screenHeight + 6, "Stamina", player.stats.getStamina(), player.stats.getMaxStamina(), Palette.blue);
		TileEngine.renderWeaponStatPlate(terminal, 1, screenHeight + 7, 29, "Stamina", Integer.toString(player.stats.getStamina()), null, null);
		TileEngine.renderWeaponStatPlate(terminal, 1, screenHeight + 8, 29, "Sprinting", Boolean.toString(player.isSprinting()), null, null);


		TileEngine.renderBox(terminal, 31, 12, 0, screenHeight, TileSet.SIMPLE, Palette.gray);

		terminal.write((char)36+ "" + player.crypto() + " ", 1, screenHeight + 11);
		terminal.write(w.getTurns() + "",30 - new String(String.valueOf(w.getTurns())).length(), screenHeight + 11);

		String cardinal = player.getCardinal().toString();
		cardinal = cardinal.replaceAll("_", " ");
		int center = 15 - (cardinal.length()/2);
		terminal.write(cardinal + "",center , screenHeight + 11);

		if(player.getAlert())
		{
			renderEffectBlocks(terminal, 29, screenHeight + 10, player.stats.getMostDangerousEffect());
		}
    	renderLogScreenArea(terminal, screenHeight, screenWidth);
    }
	public static void renderPercentBlocksV2(AsciiPanel terminal, int x, int y, String name, double value, double outOf, Color c)
	{
		String display = name;

		char block = (char)178;

		String fs = "";
		int center = 14;
		int stringStart = 1;
		double percent = value / outOf;
		double numBlocks = 29 * percent;

		Color barB = Palette.morePaleWhite;
		Color barF = Palette.darkerGray;

		String ps = String.format("%.2f", percent * 100) + " %";

		if(percent < 0)
			ps = "0.00 %";

		for(int i =  1; i < 30; i++) // fill string w/ blocks
		{
			//terminal.write((char)178, i, ey, Palette.morePaleWhite, Palette.monoRed);
			fs += block;
		}

		char[] ec = display.toCharArray();
		char[] fsc = fs.toCharArray();
		char[] pc = ps.toCharArray();


		int ie = 0;
		for(int i = stringStart; i < stringStart + display.length(); i++)	// stamp effect onto percent
		{
			fsc[i] = ec[ie++];
		}
		for(int i = 0; i < pc.length; i++) // stamp percent
		{
			fsc[fsc.length - (pc.length - i)] = pc[i];
		}

		int px = x;
		for(int i = 0; i < fsc.length; i++) // print all
		{
			if(i < stringStart || i >= stringStart + display.length()) // bar color
			{
				terminal.write(fsc[i], px, y, barB, barF); // just the bar

				if(i >= fsc.length - ps.length() && i < fsc.length) // for printing percent value
				{
					terminal.write(fsc[i], px, y, barF, barB);
				}
			}
			else if(i >= stringStart && i < stringStart + display.length()) // Left aligned word color
			{
				terminal.write(fsc[i], px, y, barF, barB);

			}
			px++;
		}
		int cx = x;

		if(numBlocks > 1 && numBlocks <= 29) // print percent blocks
		{
			int startColor = 0;

			//System.out.println(numBlocks);

			for(int i = 0; i < numBlocks; i++)
			{
				if(Character.isAlphabetic(fsc[startColor]) || Character.isDigit(fsc[startColor])
						|| fsc[startColor] == ' ' || fsc[startColor] == '.' || fsc[startColor] == '%')
				{
					terminal.write(fsc[startColor], cx, y, barF, c);
				}
				else
					terminal.write(fsc[startColor], cx, y, c, c);

				startColor++;
				cx++;
			}
		}

		String print = new String(fsc);
	}
	public static void renderCreationPlate(AsciiPanel terminal, int x, int y, int length, String s1, String s2, boolean centered, Color f, Color b)
	{
		int column1Width = 12;
		int columen2Width = 6;

		char[] s1c = s1.toCharArray();
		char[] s2c = s2.toCharArray();

		String fs = "";

		for(int i = 0; i < length; i++)
		{
			fs += (char) 178;
		}

		char[] fsc = fs.toCharArray();
		int column2X = 0;


		for(int i = 0; i < fsc.length; i++)
		{
			if(i < s1c.length)
			{
				fsc[i] = s1c[i];
			}
			else if(i < 15)
			{
				fsc[i] = (char) 176;
			}

			if(i > 27 && i < 27 + 15) // second column starts at x 36
			{
				if(column2X < s2c.length)
					fsc[i] = s2c[column2X++];
				else
					fsc[i] = (char) 176;
			}
		}

		for(int i = 0; i < fsc.length; i++)
		{
			terminal.write(fsc[i], x + i, y, f, b);
		}
	}
	public static void renderDisplayPlate(AsciiPanel terminal, int x, int y, int length, String s, boolean centered, Color f, Color b)
	{
			String fs = "";
			int stringStart = 1;
			int mid = (length/2) -1;
			if(centered)
			{
				stringStart = mid - (s.length()/2);
			}
			for(int i = 0; i < length; i++)
			{
					fs += (char) 178;
			}
			char[] fsc = fs.toCharArray();
			char[] sc = s.toCharArray();

			for(int i = 0; i < length; i++)
			{
				if(i >= stringStart && i < stringStart + s.length())
				{
					fsc[i] = sc[i-stringStart];
				}
			}
			for(int i = 0; i < fsc.length; i++)
			{
				terminal.write(fsc[i], x + i, y, f, b);
			}
	}
    public static void renderEffectBlocks(AsciiPanel terminal, int x, int y, Effect e)
	{
		String effect = e.getEffectTag() + " "
				+ e.getEffectLength();

		String fs = "";
		int center = 14;
		int stringStart = center - (effect.length()/2);
		int numBlocks = Math.abs(e.getEffectLength()) / 100;

		for(int i =  1; i < 30; i++) // fill string w/ blocks
		{
			//terminal.write((char)178, i, ey, Palette.morePaleWhite, Palette.monoRed);
			fs += (char)178;
		}

		char[] ec = effect.toCharArray();
		char[] fsc = fs.toCharArray();

		int ie = 0;
		for(int i = stringStart; i < stringStart + effect.length(); i++)	// stamp effect onto percent
		{
			fsc[i] = ec[ie++];
		}

		int px = 1;
		for(int i = 0; i < fsc.length; i++) // print all
		{
			if(i < stringStart || i >= stringStart + effect.length()) // bar color
			{
				terminal.write(fsc[i], px, y, Palette.morePaleWhite, Palette.monoPurple);
			}
			else if(i >= stringStart && i < stringStart + effect.length()) // word color
			{
				terminal.write(fsc[i], px, y, Palette.darkerGray, Palette.morePaleWhite);
			}
			px++;
		}
		terminal.write(fsc[28], --px, y, e.getGolor(), Palette.morePaleWhite); // print last block

		int cx = 29 - numBlocks;

		if(numBlocks > 1) // print percent blocks
		{
			int startColor = 28 - numBlocks;

			for(int i = 0; i < numBlocks; i++)
			{
				if(Character.isAlphabetic(fsc[startColor]) || Character.isDigit(fsc[startColor])
						|| fsc[startColor] == ' ' )
				{
					terminal.write(fsc[startColor], cx, y, Palette.darkerGray, e.getGolor());
				}
				else
					terminal.write(fsc[startColor], cx, y, e.getGolor(), Palette.morePaleWhite);

				startColor++;
				cx++;
			}
		}

		String print = new String(fsc);
	}
    public static void renderLogScreenArea(AsciiPanel terminal, int screenHeight, int screenWidth)
    {
    	// Produces divider
    	int ox = 30;

    	/*
    	for (int i = 0; i < 12; i++)
    		terminal.write(Tile.simpleLRW.glyph(), ox, screenHeight + i, Color.DARK_GRAY);

    	terminal.write((char)194, ox, screenHeight , Color.DARK_GRAY);
    	terminal.write((char)193, ox, screenHeight+11, Color.DARK_GRAY );
    	// Updates display array
    	*/
    	//terminal.write((char)191 + "     " + (char)218, ox + 45, screenHeight - 11, Color.DARK_GRAY);
    	//terminal.write(" LOG ", ox + 46, screenHeight - 11, Palette.lightBlue);
    }
	public static void renderPercentBlocks(AsciiPanel terminal, Color fc, int x, int y, int value, int max, boolean showPercent)
	{
		renderPercentBlocks(terminal, fc, Palette.darkestGray, x, y, value, max, showPercent);
	}
	public static void renderPercentBlocks(AsciiPanel terminal, Color fc, Color bc, int x, int y, int value, int max, boolean showPercent)
	{
		double percent = 10 * ((double)value/max);
		double xi;

		for(int i = 0; i < percent; i++)
		{
			terminal.write((char) 223, x+i, y, fc, bc);
			xi=i;
		}
		if(showPercent)
		{
			if(percent > 0)
				terminal.write(" " + String.format("%.2f", percent * 10) + " %");
			else
				terminal.write(" " + String.format("%.2f", 0 * 10.0) + " %");
		}
	}
	public static void renderItemPlate(AsciiPanel terminal, int x, int y, Item i, int limit)
	{
		String s = i.name();
		String value = String.valueOf(i.value());

		String fs = "";
		char[] sc = s.toCharArray();
		char[] vc = value.toCharArray();


		Color c = Palette.morePaleWhite;
		Color d = Theme.CLASSIC.getBack();

		//if(s.length() < limit)
		{
			int vx = 0;
			for(int j = 0; j <  limit; j++)
			{
				if(j < s.length() + 1 && j > 0)
					terminal.write(sc[j-1], x + j, y, Palette.morePaleWhite, d); //writes text
				else
					terminal.write((char)178 + "", x + j, y, d, Palette.darkerGray);

				if(j > limit - 6)
				{
					if(j == limit-5)
					{
						terminal.write((char) 178 + "", x + j, y, i.color(), Palette.morePaleWhite);
					}
					else {
						if (vx < value.length()) {
							terminal.write(vc[vx++] + "", x + j, y, Palette.darkerGray, i.color());
						} else {
							terminal.write((char) 178 + "", x + j, y, i.color(), Palette.morePaleWhite);
						}
					}
				}
			}
			sc = fs.toCharArray();
		}
		//else
		{

		}
		System.out.println(fs);
	}
	public static void displayCharSheet(AsciiPanel terminal, World w, JFrame main)
	{
		Entity player = w.getPlayer();

		int sheetH =  11 + player.stats.getEffects().size() + 1;

		TileEngine.renderBox(terminal, 31 , sheetH ,0, ((MainFrame)main).getDisplayHeight()-sheetH,  TileSet.SIMPLE, true);

		Color a = Palette.monoPurple;
		Color b = Palette.monoGrayTeal;
		int y = ((MainFrame) main).getDisplayHeight() - (11 + player.stats.getEffects().size());

		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Shield", player.shield(), 1000, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Vitals", player.stats.vitals.getVitals(), 1000, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Head", player.stats.vitals.getHead(), 300, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Torso", player.stats.vitals.getTorso(), 300, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Arms", (int)player.stats.vitals.getLeftHand() + (int)player.stats.vitals.getRightHand(), 200, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Legs", (int)player.stats.vitals.getLeftLeg() + (int)player.stats.vitals.getRightLeg(), 200, a);

		terminal.write("Oxygen Sources", 8, y++);

		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Ambient 02", w.getAir().getOxygen(), 1000, b);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Reserve 02", (int)player.inventory().getTypeDuration(Type.OXYGEN), 200, b);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Stamina", player.stats.getStamina(), player.stats.getMaxStamina(), b);

		terminal.write("Effects", 12, y++);

		ArrayList<Effect> effects = player.stats.getEffects();

		if(!player.stats.getEffects().isEmpty())
		{
			int ex = 1;
			int ey = y;

			for(Effect e : effects)
			{
				TileEngine.renderEffectBlocks(terminal, ex, ey++, e);
			}
		}
	}
	public static void displayDynamicEnemyPopUp(AsciiPanel terminal, World w, JFrame main)
	{
		int ex = 0, ey = 0;
		int offset = 4;
		int count = 1;

		Entity player = w.getPlayer();
		List<Entity> entities = player.fov().getEntities();

		ey = ((MainFrame)main).getDisplayHeight() - (entities.size() * 3 + 1);

		for(Entity e : entities)
		{
			TileEngine.renderBox(terminal, 31, offset, ex, ey, TileSet.SIMPLE, true);
			TileEngine.renderPercentBlocksV2(terminal, ex + 1, ey + 1, e.name(), e.stats.vitals.getVitals(), e.stats.vitals.getFullVitals(), Palette.pastelGreen);
			terminal.write(" " + count++ + " ", ex+1, ey+2, Palette.paleWhite, Palette.red);

			TileEngine.renderDisplayPlate(terminal, ex+4, ey+2, 4, (int)e.inventory().getTypeDuration(Type.GUN) + "", true, Palette.morePaleWhite, Palette.monoRed);
			TileEngine.renderDisplayPlate(terminal, ex+4+4, ey+2, 4, (int)e.inventory().getTypeDuration(Type.MELEE) + "", true, Palette.morePaleWhite, Palette.monoGrayTeal);
			TileEngine.renderDisplayPlate(terminal, ex+4+8, ey+2, 4, (int)e.inventory().getTypeDuration(Type.ARMOR) + "", true, Palette.morePaleWhite, Palette.darkerGray);
			String id = "?";
			if(e.isIdentified())
				id = e.getCreed();
			TileEngine.renderDisplayPlate(terminal, ex+4+12, ey+2, 14, id, true,  Palette.morePaleWhite, Palette.lightRed);

			ey += offset -1;
		}
	}
}