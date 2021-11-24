package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import entities.Entity;
import wolrdbuilding.Palette;

public class EscapeScreen extends singleListScrollingBasedScreen
{
	private static ArrayList<String> options;
	private static Screen screen;
	public static boolean exit;
	
	public EscapeScreen(AsciiPanel terminal, Screen ccs)
	{
		super(null, terminal, 15, 10);
		this.screen = ccs;
		options = new ArrayList<>();
		options.add("Resume");
		options.add("Main Menu");
	}
	public EscapeScreen(Entity player, AsciiPanel terminal, Screen ps)
	{
		super(player, terminal, 15, 10);
		this.screen = ((PlayScreen)ps);
		options = new ArrayList<>();
		options.add("Resume");
		options.add("Save");
		options.add("Main menu");
		options.add("New theme");
		options.add("Set fullscreen");
		
	}
	public static void write(AsciiPanel terminal)
	{
		renderOptionsList(terminal);
	}
	public static void renderOptionsList(AsciiPanel terminal)
	{
		terminal.write("Options " + index, rx + 1, ry, Palette.lightBlue);
		terminal.write((char)16, scrollX, scrollY, Palette.lightBlue);
		int oy = ry+1;
		
		for(String s : options)
			terminal.write(s, rx+ 2, oy++);
	}
	@Override
	public Screen returnScreen(Screen screen)
	{
		return null;
	}
	public void selectItem()
	{
		if(index >= 0 && index < options.size())
		{
			if(index == 0)
				exit = true;
			else if(index == 1)
			{
				if(screen instanceof PlayScreen)
					((PlayScreen)screen).saveGame();
				
				if(options.get(index) == "Main Menu")
					((CharacterCreationScreen)screen).returnStartScreen();
				exit = true;
			}
			else if(index == 2)
			{
				((PlayScreen)screen).returnStartScreen();
			}
			else if(index == 3)
			{
				((PlayScreen)screen).setNewTheme();
			}

		}
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
