package screens;

import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;

public class InteractScreen extends UIScreen
{

	public InteractScreen(Entity player, PlayScreen ps) 
	{
		super(player, ps);
		setCursor(false);
		this.bw = 35;
		this.bh = 15;
		this.bx = 23;
		this.by = 7;
	}
	@Override
	public void render(AsciiPanel terminal)
	{
		String s = "This is a test message. We will see how well i designed this test message class. This message should wrap neatly"
				+ " in the interact screen box. I don't know where these messages will be stored yet, but they must be stored somewhere.";
		Message msg = new Message(s, 33);
		List<String> msgLineList = msg.getLines();
		int mx = bx + 1;
		int my = by + 1;
		System.out.println(mx + " " + my + " " + msgLineList.size());
		for(String x :  msgLineList)
		{
			terminal.write(x, mx, my++);
		}
		
	}
}
