package screens;

import asciiPanel.AsciiPanel;
import structures.MainFrame;
import structures.RexReader;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TilePoint;
import wolrdbuilding.TileSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class StartScreen implements Screen
{
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

	AsciiPanel terminal;
	private HashMap<String, ArrayList<TilePoint>> structureMap;
	ArrayList<TilePoint> testStructure;
	//playScreen
	private boolean rendered = false;
	private Screen subscreen;
	private JFrame main;
	
	
	public StartScreen(AsciiPanel terminal, JFrame main)
	{
		this.main = main;
		this.terminal = terminal;
		testStructure = new ArrayList<>();
		structureMap = new RexReader().getStructures();

		subscreen = new AnimationScreen(testStructure, terminal);
		//Thread t = new Thread(new AnimationScreen(testStructure, terminal));
		//t.start();

	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) 
	{

		this.terminal = terminal;

		if(!rendered)
		{
			testStructure = structureMap.get("finAsciiAR.csv");
			testStructure = TileEngine.displayTilesWithTransparentBox(terminal, testStructure, 15, 5, 35, 18, Palette.darkGray);
			System.out.println(testStructure.size());

			testStructure.addAll(TileEngine.renderFrame(terminal, 16, 7, 34, 17, TileSet.DOUBLE, Palette.gray));
		//	System.out.println(testStructure.size());
			testStructure.addAll(TileEngine.renderBox(terminal, 14, 5, 35, 18, TileSet.SIMPLE));
		//	System.out.println(testStructure.size());
			rendered = true;
		}
		else
		{
		//	System.out.println("From rendered " + testStructure.size());
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
			case KeyEvent.VK_SHIFT: //((AnimationScreen)subscreen).animate();
				break;
	    	case KeyEvent.VK_ESCAPE: return new LoseScreen(terminal, null, main);
	      	case KeyEvent.VK_ENTER:
			{
				return new CharacterCreationScreen((MainFrame) main);
			}
			case KeyEvent.VK_BACK_SPACE:
			{
				//Statistics stats = new Statistics();
				//return new PlayScreen(stats, (MainFrame) main, World.Map.TURKEY);
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
