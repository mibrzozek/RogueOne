package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.MainFrame;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WinScreen implements Screen 
{
	transient AsciiPanel  terminal;
	private MainFrame main;
	private Entity player;
	private World world;
	public WinScreen(AsciiPanel terminal, MainFrame main, Entity player, World world)
	{
		this.world = world;
		this.player = player;
		this.main = main;
		this.terminal = terminal;
	}
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
        int screenW = main.getScreenWidth();
        int screenH = main.getScreenHeight();

		int statsBoxX = (screenW/2)/2;
		int statsBoxY = (screenH/2)/2;
		int statsBoxWidth = screenW/2;
		int statsBoxHeight = screenH/2;

		TileEngine.renderBox(terminal, screenW, screenH, 0, 0, TileSet.DOUBLE_S, Palette.theNewBlue);
		TileEngine.renderBox(terminal, statsBoxWidth, statsBoxHeight, statsBoxX, statsBoxY, TileSet.DOUBLE_S);
		terminal.writeCenter("You made it out, but at what cost " + player.stats.getName() + "?", statsBoxY/2);

		terminal.writeCenter("STATISTICS", statsBoxY);


		//terminal.write("Turns taken : " + world.getTurns(),statsBoxX + 1, 6);
		TileEngine.renderDisplayPlate(terminal, statsBoxX + 1, statsBoxY + 1, statsBoxWidth -2, "Turns taken : " + world.getTurns(), false, Palette.red, Palette.black);
		TileEngine.renderDisplayPlate(terminal, statsBoxX + 1, statsBoxY + 2, statsBoxWidth -2, "Crypto      : " + player.stats.getCrypto(), false, Palette.red, Palette.black);


		terminal.writeCenter("[ENTER] Main Menu", statsBoxHeight + statsBoxY - 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			return new StartScreen(terminal, main);
		}
		else return this;
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

	@Override
	public Color getForeColor() {
		return null;
	}

	@Override
	public Color getBackColor() {
		return null;
	}


}
