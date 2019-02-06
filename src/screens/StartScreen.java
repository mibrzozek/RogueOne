package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import structures.RexReader;
import structures.TileEngine;
import wolrdbuilding.Tile;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;

public class StartScreen implements Screen
{
	AsciiPanel terminal;
	//playScreen

	
	
	public StartScreen()
	{

	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) 
	{
		TileEngine.renderBox(terminal, sw, sh, 0, 0,  TileSet.SIMPLE);
		TileEngine.renderBox(terminal, 45, 22, 20, 10,  TileSet.SIMPLE);
		
		terminal.writeCenter("_Abandoned_Project_" + Tile.dblTLC.glyph(), 13);
		terminal.writeCenter("- a quest for answers -", 15);
		
		terminal.writeCenter("PLAY [enter]", 19);
		terminal.writeCenter("LOAD [space]", 20);
		terminal.writeCenter("LOSE [ esc ]", 21);


		ArrayList<TilePoint> testStructure = RexReader.getStructure();
		while(!testStructure.isEmpty())
		{
			TilePoint t = testStructure.remove(0);
			terminal.write(t.glyph(), t.x(), t.y(), t.foreground(), t.background());
		}
		//renderBox(terminal, 25, 25, 30, 5);
		
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
	      	case KeyEvent.VK_ENTER: return new CharacterCreationScreen();
	      	case KeyEvent.VK_SPACE: 
	      	try
			{
	      		return loadSavedScreen();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
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
