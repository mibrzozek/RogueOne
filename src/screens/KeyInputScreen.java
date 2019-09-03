package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import structures.Console;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.Tile;
import wolrdbuilding.TileSet;

public class KeyInputScreen implements Screen
{
	private String customString;
	private int charLimit;
	private boolean onlyNumbers = false;
	private Screen screen;
	private Console c;
	
	int x;
	int y;
	
	private ArrayList<Character> inputs = new ArrayList<>(15);
	private AsciiPanel terminal;
	
	
	public KeyInputScreen(AsciiPanel terminal, Screen screen, int x, int y)
	{
		customString = "";
		this.terminal = terminal;
		this.screen = screen;
		this.charLimit = 3;
		this.onlyNumbers = true;
		this.x = x;
		this.y = y;
	}
	public KeyInputScreen(AsciiPanel terminal, Screen screen, int charLimit, int x, int y, Console c)
	{
		customString = "";
		this.terminal = terminal;
		this.screen = screen;
		this.charLimit = charLimit;
		this.x = x;
		this.y = y;
		this.c = c;
	}

	
	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		if(c != null)
		{
			int bh = c.getLog().size() + 3;

			if(c.getLog().size() + 3 > 50)
			{
				bh = 50;
			}

			TileEngine.renderBox(terminal, 20, bh, x - 2, y-1, TileSet.SIMPLE);


			for(int i = 0; i < c.getLog().size(); i++)
			{
				if(i < 47)
					terminal.write(c.getLog().get(c.getLog().size()-i -1), x, y + 1 + i, Palette.darkGray);
			}

			terminal.write("console", x+10, y-1);
		}


		terminal.write((char)221, x, y, Palette.lightRed); 			// Cursor
		terminal.write((char)175, x-1, y, Palette.lightRed);		// input begin


		
		int x1 = x;
		
		for(Character c : inputs)
			terminal.write(c, x1++, y, Palette.green);

		
		
		/*
		TileEngine.renderBox(terminal, 35, 5, 26, 20, "Inputs");
		
		int x = 30;
		for(Character c : inputs)
			terminal.write(c, x++, 22);
		
		terminal.write((char)221, x, 22, Palette.lightBlue); 			// Cursor
		terminal.write((char)175, 29, 22, Palette.lightBlue);			
		terminal.write("[ENTER] save", 30, 24, Palette.lightBlue); 		// Save
		terminal.write("[ ESC ] exit", 44, 24, Palette.lightRed);		// ESC
		*/	
	}

	@Override
	public Screen respondToUserInput(KeyEvent key)
	{
		switch (key.getKeyCode())
		{
	
			case KeyEvent.VK_A:
			case KeyEvent.VK_B:
			case KeyEvent.VK_C:
			case KeyEvent.VK_D:
			case KeyEvent.VK_E:
			case KeyEvent.VK_F:
			case KeyEvent.VK_G:
			case KeyEvent.VK_H: 
			case KeyEvent.VK_I: 
			case KeyEvent.VK_J:
			case KeyEvent.VK_K:
			case KeyEvent.VK_L:
			case KeyEvent.VK_M:
			case KeyEvent.VK_N: 
			case KeyEvent.VK_O: 
			case KeyEvent.VK_P: 
			case KeyEvent.VK_Q: 
			case KeyEvent.VK_R: 
			case KeyEvent.VK_S:
			case KeyEvent.VK_T: 
			case KeyEvent.VK_U: 
			case KeyEvent.VK_V: 
			case KeyEvent.VK_W: 
			case KeyEvent.VK_X: 
			case KeyEvent.VK_Y: 
			case KeyEvent.VK_Z: if(inputs.size() < charLimit  && !onlyNumbers) inputs.add(key.getKeyChar()); break;
			
			case KeyEvent.VK_0:
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
			case KeyEvent.VK_6:
			case KeyEvent.VK_7:
			case KeyEvent.VK_8:
			case KeyEvent.VK_9:
			case KeyEvent.VK_NUMPAD0:
			case KeyEvent.VK_NUMPAD1:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_NUMPAD3:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_NUMPAD5:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_NUMPAD7:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_MINUS:
			case KeyEvent.VK_NUMPAD9: if(inputs.size() < charLimit) inputs.add(key.getKeyChar()); break;
				
			
			case KeyEvent.VK_SPACE:if(inputs.size() < charLimit) inputs.add(key.getKeyChar()); break;
			case KeyEvent.VK_LEFT:return null;
				
			case KeyEvent.VK_BACK_SPACE: 
			{
				if(inputs.size() - 1 >= 0)
					inputs.remove(inputs.size()-1); break;
			}
			case KeyEvent.VK_ENTER:
			{
				if(inputs.size() > 0)
				{
					customString= "";
					for(Character c : inputs)
							customString += c;
					System.out.println(customString);
					
					if(screen instanceof CharacterCreationScreen)
						((CharacterCreationScreen)screen).setCustomString(customString);
					else if(screen instanceof PlayScreen) {
						((PlayScreen)screen).writeToConsole(customString);
					}

					customString= "";
					inputs.clear();

					if(c == null)
						return null;
				}
				else
					System.out.println("Not long enough name!");
				
				break;
			}
			case KeyEvent.VK_ESCAPE: return null;	
		}
		return this;
	}

	@Override
	public Screen returnScreen(Screen screen)
	{
		
		return null;
	}
	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
		
	}
	
}
