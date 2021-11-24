package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import asciiPanel.AsciiPanel;

public interface Screen extends Serializable
{
	public void displayOutput(AsciiPanel terminal);
	public Screen respondToUserInput(KeyEvent key);
	public Screen returnScreen(Screen screen);
	public void animate();
	public Color getForeColor();
	public Color getBackColor();
}
