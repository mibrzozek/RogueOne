package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.PlayerAi;
import items.Item;
import items.ItemFactory;
import items.Type;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;

import javax.swing.*;

public class AttackBox extends UIScreen 
{
	private Entity enemy;
	
	public AttackBox(Entity player, int bw, int bh, int bx, int by, int enemyIndex, PlayScreen ps, JFrame main)
	{
		super(player, ps, main);
		setInputNumber(enemyIndex);
		
		this.bw = bw;
		this.bx = bx;
		this.bh = bh;
		this.by = by;
		this.enemy = (Entity)player.fov().getEntities().get(enemyIndex);
		
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
		System.out.println("Entity " + enemy.stats.getName() + " Dead : " + enemy.isDead());

		if(itemList.get(index).equals("Shoot"))
		{
			if (!player.inventory().get(Type.GUN).isEmpty())
			{
				double dmg = player.inventory().getTypeDuration(Type.GUN);
				enemy.dealDamage(-dmg);
				if (enemy.stats.getVitals() < 1)
				{
					enemy.setDead(true);
					setNull();
				}
			}
			else if(!player.inventory().get(Type.RANGED).isEmpty())
			{
				if(!player.inventory().get(Type.ARROW).isEmpty()) // if arrows equipped
				{
					System.out.println("We're shooting arrows");
					double dmg = player.inventory().getTypeDuration(Type.RANGED);
					player.inventory().removeEquiped(new ItemFactory().newArrow());
					enemy.dealDamage(-dmg);
					if (enemy.stats.getVitals() < 1)
					{
						enemy.setDead(true);
						setNull();
					}
				}
				else
				{
					player.notify("You're out of bows!");
				}
			}
			else
			{
				player.notify("You try shooting but don't seem to have a ranged weapon!");
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

		terminal.write(enemy.tile().glyph(), enemy.x-ps.getLeftOffset()+ps.getPlayAreaOffset(), enemy.y - ps.getTopOffset(), Palette.white, Palette.darkRed);
		TileEngine.renderBox(terminal, 31, 31, 0, 0, TileSet.SIMPLE, Palette.paleWhite);
		TileEngine.renderPercentBlocksV2(terminal, 1, 1, enemy.name(), enemy.stats.getVitals(), enemy.stats.getFullVitals(), Palette.lightGreen);
		TileEngine.renderPercentBlocksV2(terminal, 1, 2, "HEAD", enemy.stats.getHead(), enemy.stats.getHeadMax(), Palette.monoPurple);
		TileEngine.renderPercentBlocksV2(terminal, 1, 3, "TORSO", enemy.stats.getTorso(), enemy.stats.getTorsoMax(), Palette.monoPurple);
		TileEngine.renderPercentBlocksV2(terminal, 1, 4, "ARMS", enemy.stats.getArms(), enemy.stats.getArmsMax(), Palette.monoPurple);
		TileEngine.renderPercentBlocksV2(terminal, 1, 5, "LEGS", enemy.stats.getLegs(), enemy.stats.getLegsMax(), Palette.monoPurple);


		TileEngine.renderDisplayPlate(terminal, 1, 6, 29, "Equipment", true, Palette.red, Palette.lightGray);
		int wy = 7;
		for(Item i : enemy.inventory().getEquipped())
		{
			TileEngine.renderDisplayPlate(terminal, 1, wy++, 29, i.name(), false, Palette.paleWhite, Palette.red);
		}
	}
	@Override
	public void update()
	{
		if(inputNumber < player.fov().getEntities().size())
			this.enemy = (Entity)player.fov().getEntities().get(inputNumber);

		System.out.println(inputNumber);
	}
}
