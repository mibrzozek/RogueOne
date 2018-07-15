package Screens;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import asciiPanel.AsciiPanel;

public interface Screen extends Serializable
{
	public void displayOutput(AsciiPanel terminal);
	public Screen respondToUserInput(KeyEvent key);
}
