package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.PlayerAi;
import items.Type;

import javax.swing.*;

public class AttackBox extends UIScreen 
{
	private Entity enemy;
	
	public AttackBox(Entity player, int bw, int bh, int bx, int by, Entity enemy, PlayScreen ps, JFrame main)
	{
		super(player, ps, main);
		
		this.bw = bw;
		this.bx = bx;
		this.bh = bh;
		this.by = by;
		this.enemy = enemy;
		
		PlayerAi  ai = (PlayerAi)player.getEntityAi();
		
		setList(ai.getAttacks());
		setTopBottomBounds(by + 1, by + itemList.size());
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
			if (!player.inventory().get(Type.GUN).isEmpty())
			{
				double dmg = player.inventory().getTypeDuration(Type.GUN);

				System.out.println(dmg);

				enemy.modifyHp(-dmg);
				System.out.println("Shooting");
				if (enemy.hp() < 0)
					setNull();
			}
			else
			{
				player.notify("You try shooting but don't seem to have a gun!");
			}
			ps.updateWorld();
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
