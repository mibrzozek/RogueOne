package screens;

import asciiPanel.AsciiPanel;
import entities.Effect;
import entities.Entity;
import items.Type;
import structures.MainFrame;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
		int y = ((MainFrame) main).getDisplayHeight() - (11 + player.stats.getEffects().size());
		int secondBoxY = ((MainFrame)main).getDisplayHeight()-(sheetH*2);
		Color a = Palette.monoPurple;
		Color b = Palette.monoGrayTeal;

		// Render Boxes //

		TileEngine.renderBox(terminal, 31 , sheetH ,0, ((MainFrame)main).getDisplayHeight()-sheetH,  TileSet.SIMPLE, true);
		TileEngine.renderBox(terminal, 31 , sheetH ,0, secondBoxY,  TileSet.SIMPLE, true);

		// Top Box //
		secondBoxY++;
		terminal.write(player.stats.getName(), 11, secondBoxY++);
		TileEngine.renderPercentBlocksV2(terminal, 1, secondBoxY++, "Strength", player.stats.getStrength(), 20, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, secondBoxY++, "Agility", player.stats.getAgility(), 20, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, secondBoxY++, "Intelligence", player.stats.getIntelligence(), 20, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, secondBoxY++, "Charisma", player.stats.getCharisma(), 20, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, secondBoxY++, "Burden", player.stats.getBurden(), 10, a);

		// Bottom Box //

		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Shield", player.shield(), 1000, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Vitals", player.stats.vitals.getVitals(), 1000, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Head", player.stats.vitals.getHead(), 300, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Torso", player.stats.vitals.getTorso(), 300, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Arms", (int)player.stats.vitals.getLeftHand() + (int)player.stats.vitals.getRightHand(), 200, a);
		TileEngine.renderPercentBlocksV2(terminal, 1, y++, "Legs", (int)player.stats.vitals.getLeftLeg() + (int)player.stats.vitals.getRightLeg(), 200, a);
		
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
		renderCharacterAttributes(sheetH, secondBoxY);
		//TileEngine.renderBox(terminal, 31 ,40 ,0, 22,  TileSet.SIMPLE, false);
	}

	public void renderCharacterAttributes(int sheetH, int secondBoxY)
	{

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
