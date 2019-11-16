package screens;

import java.awt.*;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import javax.swing.*;

public class WinScreen implements Screen 
{
	AsciiPanel terminal;
	private JFrame main;

	public WinScreen(AsciiPanel terminal, JFrame main)

	{
		this.main = main;
		this.terminal = terminal;
	}

	@Override
	public void displayOutput(AsciiPanel terminal)
	{
	    terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
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

	@Override
	public Color getForeColor() {
		return null;
	}

	@Override
	public Color getBackColor() {
		return null;
	}


}
