package entities;
import entities.entityAI.*;
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
	public Entity newSecurity(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity security = new Entity(stats, world, Tile.SECURITY);

		new VagrantAi(security, player);
		return security;
	}
	public Entity newDroid(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity droid = new Entity(stats, world, Tile.DROID);

		new DroidAi(droid, player);
		return droid;
	}
	public Entity newRogue(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity rogue = new Entity(stats, world, Tile.ROGUE);

		new RogueAi(rogue, player);
		return rogue;
	}
	public Entity newHeavy(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity heavy = new Entity(stats, world, Tile.HEAVY);

		new HeavyAi(heavy, player);
		return heavy;
	}
	public Entity newRanged(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ranged = new Entity(stats, world, Tile.RANGED);

		new VagrantAi(ranged, player);
		return ranged;
	}
	public Entity newMarine(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity marine = new Entity(stats, world, Tile.MARINE);

		new MarineAi(marine, player);
		return marine;
	}
	public Entity newVagrant(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity vagrant = new Entity(stats, world, Tile.VAGRANT);

		new VagrantAi(vagrant, player);
		return vagrant;
	}
	public Entity newKamikaze(int z, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ent = new Entity(stats, world, Tile.GRUNT);

		new VagrantAi(ent, player);
		return ent;
	}
	public Entity newDroidSkeleton(int depth, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ent = new Entity(stats, world, Tile.DROID);

		new DroidAi(ent, player);
		return ent;
	}
    public Entity newPlasmaJunkie(int depth, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ent = new Entity(stats, world, Tile.JUNKIE);

		new JunkieAi(ent, player);
		return ent;
	}
    public Entity newHitman(int depth, Entity player)
    {
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ent = new Entity(stats, world, Tile.HITMAN);

		new HitmanAi(ent, player);
		return ent;
    }
	public Entity newBoss(Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ent = new Entity(stats, world, Tile.BOSS);

		new BossAi(ent, player);
		return ent;
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

	public Entity newCommando(Entity boss, Entity player)
	{
		Statistics stats = new Statistics();
		stats.rollCharacter(world.getNameGenerator());
		Entity ent = new Entity(stats, world, Tile.COMMANDO);

		new CommandoAi(ent, player, boss);
		return ent;
	}
}
