package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import entities.Effect;
import entities.Entity;
import items.Type;
import structures.MainFrame;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.Tile;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import javax.swing.*;

public class CharacterSheet implements Screen
{
	private Screen subScreen;
	private boolean exitGame;
	private AsciiPanel terminal;
	private int width, height;
	
	private Entity player;
	private World w;
	private JFrame main;

	public CharacterSheet(World w, JFrame main)
	{
		this.main = main;
		this.w = w;
		this.player = w.getPlayer();
		this.width = 31;
		this.height = 40;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		int sheetH =  11 + player.stats.getEffects().size() + 1;

		TileEngine.renderBox(terminal, 31 , sheetH ,0, ((MainFrame)main).getDisplayHeight()-sheetH,  TileSet.SIMPLE, true);
		
		//terminal.write(player.stats.getName(), 1, 22, Palette.lightRed);
		//write(player.stats.getRole(), 1, 61, Palette.lightRed);// name

		Color a = Palette.monoPurple;
		Color b = Palette.monoGrayTeal;
		int y = ((MainFrame) main).getDisplayHeight() - (11 + player.stats.getEffects().size());

		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Shield", player.shield(), 1000, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Vitals", player.stats.getVitals(), 1000, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Head", player.stats.getHead(), 300, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Torso", player.stats.getTorso(), 300, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Arms", (int)player.stats.getlHand() + (int)player.stats.getrHand(), 200, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Legs", (int)player.stats.getlLeg() + (int)player.stats.getrLeg(), 200, a);
		
		terminal.write("Oxygen Sources", 8, y++);

		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Ambient 02", w.getAir().getOxygen(), 1000, b);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Reserve 02", (int)player.inventory().getTypeDuration(Type.OXYGEN), 200, b);

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

		//TileEngine.renderBox(terminal, 31 ,40 ,0, 22,  TileSet.SIMPLE, false);
	}
	@Override
	public Screen respondToUserInput(KeyEvent key)
	{
		if(subScreen != null)
		{
			subScreen = subScreen.respondToUserInput(key);
			if(subScreen instanceof EscapeScreen)
			{
				if(((EscapeScreen)subScreen).exit == true)
				{
					subScreen = null;
					((EscapeScreen)subScreen).exit = false;
				}
			}
		}	
		else
		{
			switch (key.getKeyCode())
			{
				case KeyEvent.VK_UP:  break;
				case KeyEvent.VK_DOWN:  break;
				case KeyEvent.VK_RIGHT: break;
				
				case KeyEvent.VK_SHIFT:
				case KeyEvent.VK_LEFT: return null;
			
				case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(terminal, this); break;
	      		case KeyEvent.VK_ENTER:  break;
			}
		}
		if(exitGame)
		{
			exitGame = false;
			return new StartScreen(terminal, main);
		}
	return this;
	}
	@Override
	public Screen returnScreen(Screen screen)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
	}
	private Color fore = Palette.paleWhite;
	private Color back = Palette.theNewBlue;
	@Override
	public Color getForeColor() {
		return fore;
	}

	@Override
	public Color getBackColor() {
		return back;
	}

}
