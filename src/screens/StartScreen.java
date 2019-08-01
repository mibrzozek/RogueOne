package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import asciiPanel.AsciiPanel;
import structures.RexReader;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.Tile;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;

public class StartScreen implements Screen
{
	AsciiPanel terminal;
	private HashMap<String, ArrayList<TilePoint>> structureMap;
	ArrayList<TilePoint> testStructure;
	//playScreen
	private boolean rendered = false;
	private Screen subscreen;
	
	
	public StartScreen(AsciiPanel terminal)
	{
		this.terminal = terminal;
		testStructure = new ArrayList<>();
		structureMap = new RexReader().getStructures();
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) 
	{	this.terminal = terminal;
		if(!rendered)
		{
			testStructure = structureMap.get("ascciWorld.csv");
			testStructure = TileEngine.displayTilesWithTransparentBox(terminal, testStructure, 15, 5, 35, 18, Palette.darkGray);
			System.out.println(testStructure.size());

			testStructure.addAll(TileEngine.renderFrame(terminal, 16, 7, 34, 17, TileSet.DOUBLE, Palette.gray));
			System.out.println(testStructure.size());
			testStructure.addAll(TileEngine.renderBox(terminal, 14, 5, 35, 18, TileSet.SIMPLE));
			System.out.println(testStructure.size());
			rendered = true;
		}
		else
		{
			System.out.println("From rendered " + testStructure.size());
			TileEngine.sparkleAnime(testStructure);
			TileEngine.displayTilesWithTransparentBox(terminal, testStructure, null, null, null, null, null);
		}
		terminal.writeCenter("PLAY [enter]", 19);
		terminal.writeCenter("LOAD [space]", 20);
		terminal.writeCenter("LOSE [ esc ]", 21);

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
	public void write(String s)
	{
		terminal.write(s, 10, 30, Color.white);
	}
	// Takes key event handled by main frame and returns a win or lose screen
	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{
		switch (key.getKeyCode())
		{
	    	case KeyEvent.VK_ESCAPE: return new LoseScreen(terminal);
	      	case KeyEvent.VK_ENTER:
			{
				return new CharacterCreationScreen();
			}
			case KeyEvent.VK_SPACE:
			{
				try
				{
					return loadSavedScreen();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
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
