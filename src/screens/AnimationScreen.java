package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;

public class AnimationScreen implements Screen, Runnable
{

	private Color fore = Palette.paleWhite;
	private Color back = Palette.theNewBlue;

	AsciiPanel termianl;

	int x = 0, y = 20;

	ArrayList<TilePoint> list;
	public AnimationScreen(ArrayList<TilePoint> list, AsciiPanel terminal)
	{
		this.list = list;
		this.termianl = terminal;
	}

	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		TileEngine.displayTilesWithTransparentBox(terminal, list, null, null, null, null, null);

		TileEngine.renderBox(terminal, 84, 5, 0, 58, TileSet.INSIDE_TILE);

		terminal.writeCenter("E N T E R - > C O N T I N U E", 60);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key)
	{
		switch (key.getKeyCode()) {

			case KeyEvent.VK_ENTER:
				return null;
		}
		return null;
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
		return fore;
	}

	@Override
	public Color getBackColor() {
		return back;
	}

	@Override
	public void run()
	{

		boolean spin = true;

		while(spin)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			termianl.write("Hello", x++, 1);

			if(x < 80)
				x = 0;

			displayOutput(termianl);
			System.out.println("Spinning "  +x);
		}
	}
}
