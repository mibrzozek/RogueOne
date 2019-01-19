package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.PlayerAi;

public class AttackBox extends UIScreen 
{
	private Entity enemy;
	
	public AttackBox(Entity player, int bw, int bh, int bx, int by, Entity enemy, PlayScreen ps)
	{
		super(player, ps);
		
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
	public Entity getEntity()
	{
		return enemy;
	}

	@Override
	public void select()
	{
		if(itemList.get(index).equals("Shoot"))
		{
			enemy.modifyHp(-10);
			System.out.println("Shooting");
			ps.updateWorld();
			if(enemy.hp() < 0)
				setNull();
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
