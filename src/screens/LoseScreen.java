package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import javax.swing.*;

public class LoseScreen implements Screen 
{
	private World w;

	private Entity player;

	ArrayList<TilePoint> m1;

	private boolean rendered = false;
	private AsciiPanel terminal;

	private JFrame main;
	
	
	public LoseScreen(AsciiPanel terminal, World w, JFrame main)
	{
		this.main = main;
		this.w =  w;

		if(w != null)
			this.player = w.getPlayer();

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
			//TileEngine.renderBox(terminal, 50, 53, 18, 4, TileSet.SIMPLE); // creates border
			TileEngine.displayTilesWithTransparentBox(terminal, m1, 50,  53,  18,  5, Palette.darkerGray);
		}
		int mid  =41, rAlign = 29;
		String ts = "";
		terminal.write(ts = "YOU ARE DEAD", mid - (ts.length()/2), 16, Palette.perfectBlue);

		if(w != null)
		{
			String cause = " ";

			if(player.stats.getMostDangerousEffect() == null)
				cause = "Bleed out";
			else
				cause = player.stats.getMostDangerousEffect().getEffectTag();

			terminal.write(ts = ("Turns : " + w.getTurns()), rAlign, 18, Palette.morePaleWhite);
			terminal.write(ts = ("Cause : " + cause), rAlign, 19, Palette.morePaleWhite);
		}

		//TileEngine.renderBox(terminal, 30, 20, 26, 15, TileSet.SIMPLE, Palette.monoPerfect);
	}
	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen(terminal, main) : this;
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
