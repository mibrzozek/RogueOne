package allscreen;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import asciiPanel.AsciiPanel;

public interface Screen extends Serializable 
{
	public static final int sw = 85;
	public static final int sh = 50 + 12;
	
	public void displayOutput(AsciiPanel terminal);
	public Screen respondToUserInput(KeyEvent key);
	public Screen returnScreen(Screen screen);
	public void animate();
}
