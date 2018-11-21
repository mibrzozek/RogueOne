package Screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Character.Entity;
import WorldBuilding.Palette;
import WorldBuilding.TilePoint;
import WorldBuilding.TileSet;
import _Structures.TileEngine;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen 
{
	private Entity player;
	ArrayList<TilePoint> m1;
	private boolean rendered = false;
	
	
	public LoseScreen()
	{
		
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
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen() : this;	
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
