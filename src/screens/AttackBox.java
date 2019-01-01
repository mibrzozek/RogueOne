package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.PlayerAi;
import wolrdbuilding.TileSet;

public class AttackBox extends UIScreen 
{
	public AttackBox(Entity player, int bw, int bh, int bx, int by) 
	{
		super(player);
		setBoxProperties(bw, bh, bx, by, TileSet.SIMPLE);
		this.bw = bw;
		this.bh = bh;
		this.bx = bx;
		this.by = by;
		PlayerAi  ai = (PlayerAi)player.getEntityAi();
		setList(ai.getAttacks());
	}
	@Override
	public void render(AsciiPanel terminal)
	{
		int x = bx + 1;
		int y = by + 1;
		
		for(String i : itemList)
			terminal.write(i,x, y++ );
			
			
	}

}
