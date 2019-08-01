package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import structures.TileEngine;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;

public class AnimationScreen implements Screen
{
	ArrayList<TilePoint> list;
	public AnimationScreen(ArrayList<TilePoint> list)
	{
		this.list = list;
	}

	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		TileEngine.displayTilesWithTransparentBox(terminal, list, null, null, null, null, null);

		TileEngine.renderBox(terminal, 84, 5, 1, 58, TileSet.INSIDE_TILE);

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

}
