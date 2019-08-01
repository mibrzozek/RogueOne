package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;

public class LoseScreen implements Screen 
{
	private Entity player;
	ArrayList<TilePoint> m1;
	private boolean rendered = false;
	private AsciiPanel terminal;
	
	
	public LoseScreen(AsciiPanel terminal)
	{
		this.terminal = terminal;
	}
	public LoseScreen(Entity player)
	{
		this.player = player;
	}
	@Override
	public void displayOutput(AsciiPanel terminal) 
	{
		if(!rendered)
		{
			m1 = TileEngine.renderBox(terminal, sw, sh, 0, 0, TileSet.SIMPLE);
			
			rendered = true;
		}
		else
		{
			TileEngine.animateBox(m1); // transposes tiles on map
			TileEngine.renderBox(terminal, 50, 53, 18, 4, TileSet.SIMPLE); // creates border
			TileEngine.displayTilesWithTransparentBox(terminal, m1, 50,  53,  18,  5, Palette.darkerGray);
		}
		terminal.write("Here lies : ", 38, 6, Palette.lightRed);
		terminal.write("Killing Smokes", 38, 7, Palette.lightRed);
		
		
	}
	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen(terminal) : this;
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
