package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

public class CharacterSheet implements Screen
{
	private Screen subScreen;
	private boolean exitGame;
	private AsciiPanel terminal;
	
	private Entity player;
	
	public CharacterSheet(Entity player)
	{
		this.player = player;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		TileEngine.renderBox(terminal, 31 ,40 ,0, 22,  TileSet.SIMPLE);
		
		terminal.write(player.stats.getName(), 1, 22, Palette.lightRed);
		terminal.write(player.stats.getRole(), 1, 61, Palette.lightRed);// name
		terminal.write("Shield "+(char)195, 1, 23);
		TileEngine.renderPercentBlocks(terminal, Color.GREEN, 9, 23, player.shield(), 100);
		terminal.write("Vitals "+(char)195, 1, 24);
		TileEngine.renderPercentBlocks(terminal, Palette.lightRed, 9, 24, (int)player.stats.getVitals(), 1000);
		
		terminal.write("Head   "+(char)195, 1, 25);
		TileEngine.renderPercentBlocks(terminal, Color.PINK, 9, 25, (int)player.stats.getHead(), 300);
		terminal.write("Torso  "+(char)195, 1, 26);
		TileEngine.renderPercentBlocks(terminal, Color.PINK, 9, 26, (int)player.stats.getTorso(), 300);
		terminal.write("Arms   "+(char)195, 1, 27);
		TileEngine.renderPercentBlocks(terminal, Color.PINK, 9, 27, (int)player.stats.getlHand() + (int)player.stats.getlHand() , 200);
		terminal.write("Legs   "+(char)195, 1, 28);
		TileEngine.renderPercentBlocks(terminal, Color.PINK, 9, 28, (int)player.stats.getlLeg() + (int)player.stats.getrLeg(), 200);
		
		terminal.write("Strgnth"+(char)195, 1, 29);
		TileEngine.renderPercentBlocks(terminal, Palette.blue, 9, 29, player.stats.getStrength(), 20);
		terminal.write("Dxtrty "+(char)195, 1, 30);
		TileEngine.renderPercentBlocks(terminal, Palette.blue, 9, 30, player.stats.getDexterity(), 20);
		terminal.write("Intlgnc"+(char)195, 1, 31);
		TileEngine.renderPercentBlocks(terminal, Palette.blue, 9, 31, player.stats.getInteligence(), 20);
		terminal.write("Charism"+(char)195, 1, 32);
		TileEngine.renderPercentBlocks(terminal, Palette.blue, 9, 32, player.stats.getCharisma(), 20);
		
		
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
			return new StartScreen();
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

}
