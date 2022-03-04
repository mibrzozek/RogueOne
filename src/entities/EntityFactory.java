package entities;
import items.ItemFactory;
import items.Type;
import wolrdbuilding.Tile;
import wolrdbuilding.World;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

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
	public Entity newKamikaze(int z, Entity player)
	{
		Entity kami = new Entity("Kamikaze", world, Tile.KAMIKAZE, 300, 100, 30);

		new KamikazeAi(kami, player);
		return kami;
	}
	public Entity newTurkeyGuardian(int depth, Entity player)
	{
		Entity turkeyG = new Entity("Turkey Guardian", world, Tile.TRADER, 200, 5, 30);

		new HitmanAi(turkeyG, player);
		return turkeyG;
	}
	public Entity newTurkey(int depth, Entity player)
	{
		Entity turkey = new Entity("Turkey", world, Tile.TRADER, 200, 5, 30);

		new TurkeyAI(turkey, player);
		return turkey;
	}
	public Entity newDroidSkeleton(int depth, Entity player)
	{
		Entity droidSkeleton = new Entity("Droid Skeleton", world, Tile.DROID, 200, 5, 30);

		new DroidAI(droidSkeleton, player);
		return droidSkeleton;
	}
    public Entity newPlasmaJunkie(int depth, Entity player)
	{
		Entity plasmaJunkie = new Entity("Plasma Junkie", world, Tile.JUNKIE, 200, 15, 15);

		new JunkieAi(plasmaJunkie, player);
		return plasmaJunkie;
	}
    public Entity newHitman(int depth, Entity player)
    {
        Entity hitman = new Entity("Hitman", world, Tile.HITMAN, 600, 10, 10);  

        new HitmanAi(hitman, player);
        return hitman;
    }
	public Entity newMutant()
	{
	    Entity mutant = new Entity("Mutant", world, Tile.MUTANT, 300, 100, 0);

	    new RogueAi(mutant, this);
	    return mutant;
	}
	public Entity newDroid()
	{
	    Entity droid = new Entity("Droid", world, Tile.DROID , 300, 100, 0);

	    new RogueAi(droid, this);
	    return droid;
	}
	public Entity newRogue()
	{
	    Entity rogue = new Entity("Rogue", world, Tile.ROGUE, 300, 100, 0);

	    new RogueAi(rogue, this);
	    return rogue;
	}
	public Entity newPlayer(List<String> messages, Statistics stats)
	{
		Entity player = new Entity(stats, world, Tile.PLAYER);
		if(world!= null)
			world.spawnPlayer(player);

		player.setEntityAi(new PlayerAi(player, messages, fov));
		player.modifyPlasma(1500);
		player.modifyCrypto(1000);
		player.stats.setStealth(player.inventory().getTypeDuration(Type.STEALTH));
		
		return player;
	}
	public Entity newFungus(int depth)
	{
	    Entity fungus = new Entity("Fungus", world, Tile.FUNGUS, 100, 20, 5);

	    new FungusAi(fungus, this);
	    return fungus;
	}
	public Entity newTrader()
	{
	    Entity trader = new Entity("Trader", world, Tile.TRADER, 10, 0, 0);

	    new TraderAi(trader, this);
	    return trader;
	}
	public Entity newMech()
	{
	    Entity mech = new Mech("Mech", world, Tile.MECH, 500);

	    new TraderAi(mech, this);
	    return mech;
	}


}
