package screens;

import java.util.List;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.PlayerAi;
import wolrdbuilding.TileSet;

public class AttackBox extends UIScreen 
{
	private Entity enemy;
	
	public AttackBox(Entity player, int bw, int bh, int bx, int by, Entity enemy)
	{
		super(player);
		
		this.bw = bw;
		this.bx = bx;
		this.bh = bh;
		this.by = by;
		this.enemy = enemy;
		
		PlayerAi  ai = (PlayerAi)player.getEntityAi();
		
		setList(ai.getAttacks());
		setScrollX(bx);
		setScrollY(by +1 );
	}
	@Override
	public void select()
	{
		if(itemList.get(index).equals("Shoot"))
		{
			enemy.modifyHp(-100);
			System.out.println("Shooting");
		}
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
