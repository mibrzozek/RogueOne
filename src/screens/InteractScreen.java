package screens;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import structures.Script;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import javax.swing.*;

public class InteractScreen extends UIScreen
{
	private Entity entity;
	private String name;

	private int mo, ry;
	private Script script;

	private String displayMessage;
	private ArrayList<ArrayList> responses;
	private ArrayList<ArrayList> replies;

	public InteractScreen(Entity player, PlayScreen ps, Entity entity, JFrame main)
	{
		super(player, ps, main);
		this.bw = 50;
		this.bh = 15;
		this.bx = 1;
		this.by = 1;
		this.entity = entity;
		this.script = player.getScript();

		responses = script.getResponses(Script.Type.TERMINAL);
		setList(responses.get(script.getScriptProgress(Script.Type.TERMINAL)));

		ry = by+bh;
		setTopBottomBounds(ry , ry+itemList.size() -1);
		setScrollX(bx + 1);
		setScrollY(ry);
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
	public void select()
	{
		if(index == 0)
		{
			processSelect();
		}
		else if(index  == 1)
		{
			processSelect();
		}
		else if(index == 2)
		{
			processSelect();
		}

	}
	public void processSelect()
	{
		System.out.println("equipIndex " + index );
		if(script.getProgressLevel(Script.Type.TERMINAL) + 1 < script.getNumLevels(Script.Type.TERMINAL))
		{ // Increments progress if there is more dialgue to be had
			script.processDecision(Script.Type.TERMINAL, player, index);
		}
		else if(script.getProgressLevel(Script.Type.TERMINAL) + 1 == script.getNumLevels(Script.Type.TERMINAL))
		{ // Proceces but doesn't increment b/c no more progress to be had
			//script.processDecision(Script.Type.TERMINAL, player, equipIndex);
		}
	}
	@Override
	public void render(AsciiPanel terminal)
	{
		setList(responses.get(script.getScriptProgress(Script.Type.TERMINAL)));

		String s = script.getDialogue(Script.Type.TERMINAL).get(script.getScriptProgress(Script.Type.TERMINAL));
		Message msg = new Message(s, bw - mo);
		List<String> msgLineList = msg.getLines();

		int mx = bx + mo + 1;
		int my = by + 1;

		terminal.write(name + ":", bx + 1, by + 1);
		for (String x : msgLineList)
		{
			terminal.write(x, mx, my++, Palette.morePaleWhite);
		}
		int mry = ry, brh = 0;
		List<Message> mResponses = new ArrayList<>();
		for (String r : itemList)
		{
			Message m = new Message(r, bw - 2);
			brh += m.getMsgLineCount();
			mResponses.add(m);
		}
		TileEngine.renderBox(terminal, bw, brh + 2, bx, by + bh - 1, TileSet.DOUBLE, true);
		for (Message m : mResponses)
		{
			if (index == mResponses.indexOf(m))
			{
				Color c = Palette.paleWhite;
				if (m.numLines == 1)
					terminal.write(m.getFullMessage(), bx + 3, mry++, c);
				else
				{
					List<String> brokenM = m.getLines();
					for (String q : brokenM)
					{
						terminal.write(q, bx + 3, mry++, c);
					}
				}
			}
			else
			{
				Color c = Palette.darkGray;
				if (m.numLines == 1)
					terminal.write(m.getFullMessage(), bx + 3, mry++, c);
				else
				{
					List<String> brokenM = m.getLines();
					for (String q : brokenM)
					{
						terminal.write(q, bx + 3, mry++, c);
					}
				}
			}
		}
	}
}
