package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen 
{

	@Override
	public void displayOutput(AsciiPanel terminal) 
	{
	    terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
		
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
