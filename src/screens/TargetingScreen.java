package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.PlayerAi;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

public class TargetingScreen implements Screen
{
	private Screen subScreen;
	private PlayScreen ps;
	private boolean exitGame;
	private AsciiPanel terminal;
	
	private Entity player;
	private List<Entity> inView;
	
	private int index, lastIndex, scrollY, scrollX;
	
	public TargetingScreen(Entity player, PlayScreen ps)
	{
		this.ps = ps;
		this.player = player;
		inView = player.fov().getEntites();
		this.scrollX = 0;
		this.scrollY = 48 - inView.size() + 1;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		if(inView.isEmpty())
			return;
		Entity enemy = inView.get(index);
		renderEnemyList(terminal);
		terminal.write((char) 16 + "" + index, scrollX, scrollY);

		// Render target selection
		terminal.write(enemy.tile().glyph(), enemy.x-ps.getLeftOffset(), enemy.y - ps.getTopOffset(), Palette.white, Palette.darkRed);
		
		if(subScreen instanceof AttackBox)
			((AttackBox) subScreen).displayOutput(terminal);
	}
	public void renderEnemyList(AsciiPanel terminal)
	{
		int x = 0;
		int y = 49 - inView.size() -1;
		
		TileEngine.renderBox(terminal, 31, inView.size() + 2 ,x, y,  TileSet.SIMPLE);
		
		y += 1;
		for(Entity e : inView)
		{	
			if(e.name() != null)
				terminal.write(e.name(), 1, y++);
		}
	}
	public void select()
	{
		PlayerAi  ai = (PlayerAi)player.getEntityAi();
		Entity enemy = inView.get(index);
		
		subScreen = new AttackBox(player, 31, ai.getAttacks().size() + 2, 31, 49 - ai.getAttacks().size() - 1, enemy, ps);
	}
	public void scrollUp()
	{
		if(scrollY == (48 - inView.size() +1 ))
			scrollY = scrollY;
		else
		{
			scrollY--;
			index--;
		}
	}
	public void scrollDown()
	{
		if(scrollY == (48))
			scrollY = scrollY;
		else
		{
			scrollY++;
			index++;
		}	
	}
	public void exitScreen()
	{
		ps.updateWorld();
	}
	@Override
	public Screen respondToUserInput(KeyEvent key)
	{	if(inView.size() < 1)
			return null;
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
				case KeyEvent.VK_UP:  scrollUp(); break;
				case KeyEvent.VK_DOWN: scrollDown(); break;
				case KeyEvent.VK_RIGHT:	select(); break;
				
				case KeyEvent.VK_T:
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
