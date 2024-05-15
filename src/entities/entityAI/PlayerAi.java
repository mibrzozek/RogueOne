package entities.entityAI;

import entities.Effect;
import entities.Entity;
import entities.FieldOfView;
import entities.Line;
import items.Item;
import items.ItemFactory;
import items.LootTable;
import structures.Script;
import wolrdbuilding.Palette;
import wolrdbuilding.Point;
import wolrdbuilding.Tile;

import java.util.ArrayList;
import java.util.List;

public class PlayerAi extends EntityAi
{
	private List<String> messages;
	private FieldOfView fov;
	private boolean canMine = false;

	private String lastMessage = "";
	
	private List<String> attacks;
	 
    public PlayerAi(Entity entity, List<String> messages, FieldOfView fov)
    {
    	super(entity);
    	this.entity.setVisionRadius(6);
		this.entity.identify();
    	this.messages = messages;
    	this.fov = fov;
    	attacks = new ArrayList<String>();

		//this.entity.x = 1;
		//this.entity.y = 1;
		//this.entity.z = 0;
    	
    	attacks.add("Taunt");
    	attacks.add("Shoot");
    	attacks.add("Melee");
    	
    	this.entity.tagged = Tile.TAGGED_PLAYER;
    	this.entity.setScript(new Script());

    	entity.inventory().setMax(8);

		this.entity.inventory().add(new LootTable().getGoldRoomItem());
		this.entity.inventory().add(new ItemFactory().newMedicalKit());
		this.entity.inventory().add(new ItemFactory().newClearanceGold());
		this.entity.inventory().add(new ItemFactory().newGlock19());
		this.entity.inventory().add(new ItemFactory().newFragGrenade());
		this.entity.inventory().add(new ItemFactory().newFragGrenade());
		//this.entity.inventory().add(new ItemFactory().newTerrainMapper());
		//this.entity.inventory().equipAll(new ItemFactory().newUniqueKey(), new ItemFactory().newDevSword(), new ItemFactory().newMiningBeam());

    	this.entity.stats.addEffect(new Effect(Effect.Effects.STONED, "High", Palette.lightBlue));
		this.entity.stats.addEffect(new Effect(Effect.Effects.FRIENDLY, "Social", Palette.monoYellow));
		this.entity.stats.addEffect(new Effect(Effect.Effects.HAPPY, "Happy", Palette.pastelPink));
		this.entity.stats.addEffect(new Effect(Effect.Effects.BROKEN_ARM, "Broken Arm", Palette.red));
		this.entity.stats.addEffect(new Effect(Effect.Effects.DESTROYED_HANDS, "Broken Fingers", Palette.red));
		this.entity.stats.addEffect(new Effect(Effect.Effects.SATITATED, "Stuffed", Palette.green));

    }
    public ArrayList<String> getAttacks()
    {
    	return (ArrayList<String>) attacks;
    }
    
    public void setFOV(FieldOfView fov) 	{	this.fov = fov;	}
    public FieldOfView getFOV()				{	return fov;     }

    public void onEnter(int x, int y, int z, Tile tile)
    {
    	entity.setTradeMode(false);

    	if (tile.isGround())
    	{
            entity.x = x;
            entity.y = y;
            entity.z = z;
			if(tile == Tile.METHANE)
			{
				entity.setSpark();

				System.out.println("Stepping on methane!");
			}
			if(tile.isFire())
			{
				entity.stats.addEffect(new Effect(Effect.Effects.BURN_1, "BURNED", Palette.lightRed));
			}
        }
    	else if (tile.isDiggable() && canMine()) 
        {
    		if(entity.plasma() > 10)
    		{
    			entity.dig(x, y, z);
    			entity.modifyPlasma(-10);
    		}
    		else
    			entity.notify("Aww shit, all out of Plasma!");
    	}
        else if(tile.isGround())
        {
        	return;
        }
    }
    public void onNotify(String message)
    {
    	if(!lastMessage.equals(message)) // won't send duplicate messages
    		messages.add(message);

    	lastMessage = message;
    }
    @Override
	public boolean canSee(int wx, int wy, int wz)
	{
		ItemFactory iF = new ItemFactory();

		if(entity.inventory().isItemEquiped(iF.newDevSword()))
			return true;
		if (entity.z != wz)
			return false;
		if ((entity.x-wx)*(entity.x-wx) + (entity.y-wy)*(entity.y-wy) > entity.visionRadius()*entity.visionRadius())
			return false;
		for (Point p : new Line(entity.x, entity.y, wx, wy))
		{
			if (entity.tile(p.x, p.y, wz).isGround() || entity.isLookingAtStructure(p.x, p.y, wz) || p.x == wx && p.y == wy)
				continue;

			return false;
		}
		return true;
	}
	public boolean canMine()
	{
		Item item = new ItemFactory().newMiningBeam();
		
		if(entity.inventory().isItemEquiped(item))
			return true;
		else 
			return false;
	}
}
