package Character;
import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import WorldBuilding.Tile;
import WorldBuilding.World;
import asciiPanel.AsciiPanel;
import items.ItemFactory;

public class EntityFactory implements Serializable
{
    private World world;
    private FieldOfView fov;
    private ItemFactory nullFactory = new ItemFactory();
	private Random r;
    
    public EntityFactory()
    {
        this.world = null;
        this.fov = null;
        this.r = new Random();
    }
    public EntityFactory(World world, FieldOfView fov)
    {
        this.world = world;
        this.fov = fov;
    }
    // name, world, glyph, color, maxHP, attack, defense
    
    public Entity newHitman(int depth, Entity player)
    {
        Entity hitman = new Entity("Hitman", world, Tile.HITMAN, 600, 10, 10);
        
        if(world!= null)
        	world.addAtEmptyLocation(1, hitman);
        new HitmanAi(hitman, player);
        return hitman;
    }
	public Entity newMutant()
	{
	    Entity mutant = new Entity("Mutant", world, Tile.MUTANT, 300, 100, 0);
	    
	    if(world!= null)
	    	world.addAtEmptyLocation(1, mutant);
	    new RogueAi(mutant, this);
	    mutant.inventory().add(nullFactory.newPlasmaPack());
	    mutant.inventory().add(nullFactory.newPlasmaPack());
	    mutant.inventory().add(nullFactory.newPlasmaPack());
	    mutant.inventory().add(nullFactory.newVileOfBioReactant());
	    return mutant;
	}
	public Entity newDroid()
	{
	    Entity droid = new Entity("Droid", world, Tile.DROID , 300, 100, 0);
	    
	    if(world!= null)
	    	world.addAtEmptyLocation(1, droid);
	    new RogueAi(droid, this);
	    droid.inventory().add(nullFactory.newPlasmaPack());
	    droid.inventory().add(nullFactory.newPlasmaPack());
	    droid.inventory().add(nullFactory.newPlasmaPack());
	    droid.inventory().add(nullFactory.newStickOfRam());
	    return droid;
	}
	public Entity newRogue()
	{
	    Entity rogue = new Entity("Rogue", world, Tile.ROGUE, 300, 100, 0);
	    
	    if(world!= null)
	    	world.addAtEmptyLocation(1, rogue);
	    new RogueAi(rogue, this);
	    rogue.inventory().add(nullFactory.newPlasmaPack());
	    rogue.inventory().add(nullFactory.newPlasmaPack());
	    rogue.inventory().add(nullFactory.newPlasmaPack());
	    rogue.inventory().add(nullFactory.newHeatShieldShard());
	    
	    return rogue;
	}
	public Entity newPlayer(List<String> messages, Statistics stats)
	{
		Entity player = new Entity(stats, world, Tile.PLAYER);
		if(world!= null)
			world.spawnInside(0, player);
		player.setEntityAi(new PlayerAi(player, messages, fov));
		player.modifyPlasma(200);
		player.modifyCrypto(1000);
		
		player.inventory().add(new ItemFactory().newMiningBeam());
		player.inventory().add(new ItemFactory().newDevSword());
		
		player.inventory().add(new ItemFactory().newRuggedCap());		
		player.inventory().add(new ItemFactory().newLoinCloth());
		player.inventory().add(new ItemFactory().newDankBoots());
		player.inventory().add(new ItemFactory().newRacingGloves());
		player.inventory().add(new ItemFactory().newWallBomb());
		

		player.inventory().moveToEquiped(5);
		player.inventory().moveToEquiped(4);
		player.inventory().moveToEquiped(3);
		player.inventory().moveToEquiped(2);
		player.inventory().moveToEquiped(1);
		
		return player;
	}
	public Entity newFungus(int depth)
	{
	    Entity fungus = new Entity("Fungus", world, Tile.FUNGUS, 100, 20, 5);
	    
	    if(world!= null)
	    	world.addAtEmptyLocation(5, fungus);
	    new FungusAi(fungus, this);
	    fungus.inventory().add(nullFactory.newPlasmaPod());
	    fungus.inventory().add(nullFactory.newPlasmaPod());
	    fungus.inventory().add(nullFactory.newPlasmaPod());
	    
	    return fungus;
	}
	public Entity newTrader()
	{
	    Entity trader = new Entity("Trader", world, Tile.TRADER, 10, 0, 0);
	    
	    if(world!= null)
	    	world.addAtEmptyLocation(5, trader);
	    new TraderAi(trader, this);
	    trader.inventory().add(new ItemFactory().newVictoryItem(0));
	    
	    return trader;
	}
	public Entity newMech()
	{
	    Entity mech = new Mech("Mech", world, Tile.MECH, 500);
	    
	    if(world!= null)
	    	world.addMech((Mech)mech);
	    new TraderAi(mech, this);

	    
	    return mech;
	}

}
