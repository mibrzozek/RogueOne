package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import WorldBuilding.Tile;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen
{
	AsciiPanel terminal;
	//playScreen
	private final int sw = 85;
	private final int sh = 50 + 12;
	
	
	public StartScreen(AsciiPanel Temrinal)
	{
		this.terminal = terminal;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) 
	{
		renderBox(terminal, sw, sh, 0, 0);
		renderInsideBox(terminal, 45, 22, 20, 10);
		
		terminal.writeCenter("Surface Dwellers United", 13);
		terminal.writeCenter(" -The night of the great borg!-", 15);
		
		
		
		terminal.writeCenter("PLAY [enter]", 19);
		terminal.writeCenter("LOAD [space]", 20);
		terminal.writeCenter("LOSE [ esc ]", 21);
		
		//renderBox(terminal, 25, 25, 30, 5);
		
	}
	public void renderInsideBox(AsciiPanel terminal, int bw, int bh, int bx, int by)
	{
		
		for(int y = by; y < by+bh ; y++)
		{
			for(int x = bx; x < bx+bw; x++)
			{
				if(x == bx || x == bx+bw-1)
    				terminal.write(Tile.lrWall.glyph(), x, y, Color.DARK_GRAY);
				else if (y == by || y == by+bh-1)
					terminal.write(Tile.tbWall.glyph(), x, y,  Color.DARK_GRAY);
				else
					terminal.write(" ", x, y);
			}
		}
	}
	public void renderBox(AsciiPanel terminal, int bw, int bh, int bx, int by)
	{
		
		for(int y = by; y < by+bh ; y++)
		{
			for(int x = bx; x < bx+bw; x++)
			{
				if(x == bx || x == bx+bw-1)
    				terminal.write(Tile.lrWall.glyph(), x, y, Color.DARK_GRAY);
				else if (y == by || y == by+bh-1)
					terminal.write(Tile.tbWall.glyph(), x, y,  Color.DARK_GRAY);
				else
					terminal.write(Tile.randomTile().glyph(), x, y);
			}
		}
	}
	// Using Serialization we are able to save the PlayScreen instance
	// and reload it, setting the old screen as the current screen!
	public PlayScreen loadSavedScreen() throws IOException
	{
	    FileInputStream fileInputStream = new FileInputStream("D:\\06 SOURCE\\saveFile");
	    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	    
	    PlayScreen savedScreen = null;
		try
		{
			savedScreen = (PlayScreen) objectInputStream.readObject();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    objectInputStream.close();
		
		return savedScreen;
	}
	// Takes key event handled by main frame and returns a win or lose screen
	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{
		switch (key.getKeyCode())
		{
	    	case KeyEvent.VK_ESCAPE: return new LoseScreen();
	      	case KeyEvent.VK_ENTER: return new PlayScreen();
	      	case KeyEvent.VK_SPACE: try
			{
				return loadSavedScreen();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return this;
	}
}
