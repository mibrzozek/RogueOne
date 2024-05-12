package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.entityAI.PlayerAi;
import entities.Taunts;
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
	private int numEnemies, bhEnemyDescription;
	private Taunts taunts;
	
	public AttackBox(Entity player, int bw, int bh, int bx, int by, int enemyIndex, PlayScreen ps, JFrame main)
	{
		super(player, ps, main);
		setInputNumber(enemyIndex);
		
		this.bw = bw;
		this.bx = bx;
		this.bh = bh;
		this.by = by;
		this.enemy = (Entity)player.fov().getEntities().get(enemyIndex);
		this.enemy.identify();
		this.numEnemies = player.fov().getEntities().size();
		this.bhEnemyDescription = ps.getDisplayHeight() - ((numEnemies * 3) + 1);
		this.taunts = new Taunts();

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
				enemy.stats.vitals.dealDamageRandomly(-dmg);
				System.out.println("In AttackBox dmg is : " + dmg);
				if (enemy.stats.vitals.getVitals() < 1)
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
					//enemy.dealDamage(-dmg);
					if (enemy.stats.vitals.getVitals() < 1)
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
		else if(itemList.get(index).equals("Taunt"))
		{
			System.out.println("Should be taunting");

			if(player.stats.getIntelligence() < 5)
			{
				player.notify(taunts.getBasicTaunt());
				enemy.taunt();
			}
			else if(player.stats.getIntelligence() < 10 && player.stats.getIntelligence() > 5)
			{
				player.notify(taunts.getFunnyTaunt());
				enemy.taunt();
			}
			else if(player.stats.getIntelligence() < 15 && player.stats.getIntelligence() > 10)
			{
				player.notify(taunts.getAdvancedTaunt());
				enemy.taunt();
			}
			else if(player.stats.getIntelligence() < 20 && player.stats.getIntelligence() > 15)
			{
				player.notify(taunts.getHilariousTaunt());
				enemy.taunt();
			}
			ps.updateWorld();
		}
	}
	@Override
	public void render(AsciiPanel terminal)
	{
		int x = bx + 1;
		int y = by + 1;
		int bh = 6 +  2 + enemy.inventory().getEquipped().size();
		int by = bhEnemyDescription - bh;
		
		for(String i : itemList)
			terminal.write(i,x, y++ );

		terminal.write(enemy.tile().glyph(), enemy.x-ps.getLeftOffset()+ps.getPlayAreaOffset(), enemy.y - ps.getTopOffset(), Palette.white, Palette.darkRed);
		TileEngine.renderBox(terminal, 31, bh, 0, by++, TileSet.SIMPLE, Palette.paleWhite);
		TileEngine.renderPercentBlocksV2(terminal, 1, by++, enemy.name(), enemy.stats.vitals.getVitals(), enemy.stats.vitals.getFullVitals(), Palette.pastelGreen);
		TileEngine.renderPercentBlocksV2(terminal, 1, by++, "HEAD", enemy.stats.vitals.getHead(), enemy.stats.vitals.getHeadMax(), Palette.paperBlue);
		TileEngine.renderPercentBlocksV2(terminal, 1, by++, "TORSO", enemy.stats.vitals.getTorso(), enemy.stats.vitals.getTorsoMax(), Palette.paperBlue);
		TileEngine.renderPercentBlocksV2(terminal, 1, by++, "ARMS", enemy.stats.vitals.getArms(), enemy.stats.vitals.getArmsMax(), Palette.paperBlue);
		TileEngine.renderPercentBlocksV2(terminal, 1, by++, "LEGS", enemy.stats.vitals.getLegs(), enemy.stats.vitals.getLegsMax(), Palette.paperBlue);

		TileEngine.renderDisplayPlate(terminal, 1, by++, 29, "Equipment", true, Palette.monoRed, Palette.lightGray);
		int wy = by;
		for(Item i : enemy.inventory().getEquipped())
		{
			TileEngine.renderDisplayPlate(terminal, 1, wy++, 29, i.name(), false, Palette.paleWhite, Palette.monoRed);
		}
	}
	@Override
	public void update()
	{
		if(inputNumber < player.fov().getEntities().size())
		{
			this.enemy = (Entity) player.fov().getEntities().get(inputNumber);
			this.enemy.identify();
		}
	}
}