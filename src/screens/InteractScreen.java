package screens;

import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.Script;
import wolrdbuilding.Palette;

public class InteractScreen extends UIScreen
{
	private Entity entity;
	private String name;
	private int mo;
	private Script script;

	public InteractScreen(Entity player, PlayScreen ps, Entity entity)
	{
		super(player, ps);
		setCursor(false);
		this.bw = 50;
		this.bh = 15;
		this.bx = 23;
		this.by = 7;
		this.entity = entity;
		this.script = player.getScript();

		if(this.entity == null)
		{
			this.name = "Laura";
		}
		else
		{
			this.name = entity.name();
		}
		mo = name.length() + 2;
	}
	@Override
	public void render(AsciiPanel terminal)
	{
		String s = script.getDialogue(Script.Type.TERMINAL).get(0);
		Message msg = new Message(s, bw - mo);
		List<String> msgLineList = msg.getLines();

		int mx = bx + mo + 1;
		int my = by + 1;

		terminal.write(name + ":", bx+1, by +1, Palette.randomNewColor());
		for(String x :  msgLineList)
		{
			terminal.write(x, mx, my++);
		}
	}
}
