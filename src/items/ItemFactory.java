package items;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

import asciiPanel.AsciiPanel;
import items.Item.Type;
import wolrdbuilding.Palette;
import wolrdbuilding.World;

public class ItemFactory implements Serializable
{
	private World world;
	public Random r;
	
	public ItemFactory(World world)
	{
		this.world = world;
		this.r = new Random();
	}
	public ItemFactory()
	{
		this.world = null;
	}

	public Item newReflectiveShall()
	{
		Item reflectiveShall = new Item((char)121, AsciiPanel.brightBlue, Type.STEALTH,
				"Reflective Shall", 
				"A loose shall which drapes over your neck and reflects all the light the hits it.",
				0, 0
				, 25);
		if(world != null)
			world.spawnInside(r.nextInt(5), reflectiveShall);
		return reflectiveShall;
	}
	public Item newSparklingBoots()
	{
		Item sparklingBoots = new Item((char)121, AsciiPanel.brightBlue, Type.STEALTH,
				"Sparkling Boots", 
				"Some glittery sparkling boots have found you, and now it's time to find yourself!",
				0, 0
				, 10);
		if(world != null)
			world.spawnInside(r.nextInt(5), sparklingBoots);
		return sparklingBoots;
	}
	public Item newTunnelAxe()
	{
		Item tunnelAxe = new Item((char)121, AsciiPanel.brightBlue, Type.DEVICE,
				"Tunneling Axe", 
				"It sports a nice grip, and an even nicer blade. It's sharp so be carefull. It will cut metal, wood, dirt, enemies, allies, and anything else that gets in its way. It's a damn sharp thing, and it's a force to be reckoned with.",
				10, 10
				, 0);
		if(world != null)
			world.spawnInside(r.nextInt(5), tunnelAxe);
		return tunnelAxe;
	}
	public Item newDiscoBall()
	{
		Item discoBall = new Item((char)121, AsciiPanel.brightBlue, Type.WEAPON,
				"Disco Ball", 
				"An egg shaped disco ball. Some groovy bird must've given birth to this thing, groovy.",
				30, 0
				, 0);
		if(world != null)
			world.spawnInside(r.nextInt(5), discoBall);
		return discoBall;
	}
	public Item newNanoSword()
	{
		Item nanoSword = new Item((char)121, AsciiPanel.brightBlue, Type.WEAPON,
				"Nano Sword", 
				"A sword made of nano bots! It slices, it shoots, and it's pretty smart.",
				30, 0
				, 30);
		if(world != null)
			world.spawnInside(r.nextInt(5), nanoSword);
		return nanoSword;
	}
	public Item newBioLard()
	{
		Item bioLard = new Item((char)121, AsciiPanel.brightBlue, Type.CONSUMABLE,
				"Bio Lard", 
				"Some kind of lab grown fat. It's more like lard than butter. Akin to coconut oil but clearer and denser. It tastes like you're drinking water and brushing your teeth, in a good way.",
				0, 0
				, 15);
		if(world != null)
			world.spawnInside(r.nextInt(5), bioLard);
		return bioLard;
	}
	/*
	 *		Stealth Items --------------------------------------------
	 */
	public Item newInvisibilityCloak()
	{
		Item cloak = new Item((char)131, Palette.blue, Type.STEALTH ,
				"Loin Cloak", 
				"It's better than being seen!",
				0, 200
				, 50);
		if(world != null)
			world.spawnInside(r.nextInt(5), cloak);
		return cloak;
	}
	public Item newInvisibilityChaps()
	{
		Item cloak = new Item((char)131, AsciiPanel.brightBlue, Type.STEALTH ,
				"Loin Chaps", 
				"Good for a late night dance party, or when running with the wolves.",
				0, 200
				, 50);
		if(world != null)
			world.spawnInside(r.nextInt(5), cloak);
		return cloak;
	}
	
	/*
	 *		Head Items --------------------------------------------
	 */
	public Item newRuggedCap()
	{
		Item ruggedCap = new Item((char)131, AsciiPanel.brightBlue, Type.HEAD ,
				"Rugged Cap", 
				"It's like wearing a rug on your head!",
				0, 200
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), ruggedCap);
		return ruggedCap;
	}
	/*
	 *		Torso Items --------------------------------------------
	 */
	public Item newLoinCloth()
	{
		Item loinCloth = new Item((char)131, AsciiPanel.brightBlue, Type.TORSO ,
				"Loin Cloth", 
				"It's better than being naked!",
				0, 200
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), loinCloth);
		return loinCloth;
	}
	/*
	 *		Arm Items --------------------------------------------
	 */
	public Item newRacingGloves()
	{
		Item racingGloves = new Item((char)131, AsciiPanel.brightBlue, Type.ARMS,
				"Racing Gloves", 
				"It's like wearing a rug on your head!",
				0, 200
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), racingGloves);
		return racingGloves;
	}
	/*
	 *		Leg Items --------------------------------------------
	 */
	public Item newDankBoots()
	{
		Item dankBoots = new Item((char)131, AsciiPanel.brightBlue, Type.LEGS ,
				"Dank Boots", 
				"It's like wearing a rug on your head!",
				0, 200
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), dankBoots);
		return dankBoots;
	}
	/*
	 *		GUN Items --------------------------------------------
	 */
	public Item newMusketGun()
	{
		Item musketGun = new Item((char)131, AsciiPanel.brightBlue, Type.GUN ,
				"Musket Gun", 
				"Another revistited classic, the musket is slow to shoot, and slow to kill."
				+ "It's slow and small rounds don't even damage the loot your soon to be slain enemy"
				+ "is carrying. The musket is often inprecise and can be of a tile or two."
				+" It surely is a skill gun though.",
				200, 0
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), musketGun);
		return musketGun;
	}
	public Item newScopedRifle()
	{
		Item scopedRifle = new Item((char)131, AsciiPanel.brightBlue, Type.GUN ,
				"Scoped Rifle", 
				"A classic hunting rifle, with a proper 8x scope, quick bullet travel and "
				+ "deals absolutely massive damage. Hard to use, but quite the reward when you make the shot."
				+ " Good for killing off dazed mutants from far awar, or getting those clumsy malfunctioned "
				+" maintence droids clear of your path ahead. Good luck with using this thing"
				+", it's bound to attract others attention.",
				200, 0
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), scopedRifle);
		return scopedRifle;
	}
	public Item newMacroUzi()
	{
		Item macroUzi = new Item((char)131, AsciiPanel.brightBlue, Type.GUN ,
				"Macro Uzi", 
				"The sucessor of the beloved micro, the macro is everthing that the micrco was, "
				+ "but in a completely macro way. Macro stock, macro compensator, and macro skins."
				+ "Using temporal technologies, engineers were able to fit everything extra that"
				+" the macro produced into the body of a micro. It has the classic look and feel but"
				+" that macro will kick you on your ass if you're not ready for it.",
				100, 0
				, 250);
		if(world != null)
			world.spawnInside(r.nextInt(5), macroUzi);
		return macroUzi;
	}
	public Item newSmartSword()
	{
		Item smartSword = new Item('\\', AsciiPanel.brightBlue, Type.GUN ,
				"Smart Sword", 
				"You've come across a 5th generation smart sword, equipped with auto beheading algorithms"
				+ " and boomerang functionality, this smart sword is good for close quarters combat along with"
				+ " ranged combat as well.",
				100, 100
				,250);
		if(world != null)
			world.spawnInside(r.nextInt(5), smartSword);
		return smartSword;
	}
	public Item newDevSword()
	{
		Item devSword = new Item('|', AsciiPanel.brightBlue, Type.GUN ,
				"Dev Sword", 
				"This is the almighty and powerfull, totally not overpowerd, completely super easy to find and weild Developemental tool."
				+ "It is said thay many men fear anyone who wields this beast. They will pee there pants as they see you and will still be"
				+ " too scared to change them even if they get back home!",
				100, 100
				,10000);

		return devSword;
	}
	public Item newWinchester2194()
	{
		Item winchester2194 = new Item('+', Color.white, Type.GUN ,
				"Winchester 2194", 
				"The 300 year annivesary edition of a classic, the Winchester Model 1894 hunting rifle."
				+ " You can see this particular one if quite old, beat up, and in need of some cleaning."
				+ " It'd be worth doing so since these are know for the longetivity.. ",
				0, 0
				, 1000);
		if(world != null)
			world.spawnInside(r.nextInt(5), winchester2194);
		
		return winchester2194;
	}
	// Viles //
	public Item newVileOfNanobots()
	{
		Item vileOfNanobots = new Item('v', Color.white, Type.GUN ,
				"Vile Of Nanobots", 
				"A small diamond vile filled with a healthy dose of little nano critters."
				+ " Be cautious about breaking one of these; who knows what some loose nanobots can"
				+ " do when let loose in your inventory. ",
				0, 0
				, 1000);
		if(world != null)
			world.spawnInside(r.nextInt(5), vileOfNanobots);
		
		return vileOfNanobots;
		
	}
	public Item newNeuralVile()
	{
		Item neuralVile = new Item('`', Color.white, Type.GUN ,
				"Neural Vile", 
				"Filled with dopamines, sugars, vitamins, minerals and stem matter for a boost to nyour vitals."
				+ " Be carefull about taking too much or you might be able to develope a dependency. These aren't too "
				+ " easy to find either.",
				0, 0
				, 200);
		if(world != null)
			world.spawnInside(r.nextInt(5), neuralVile);
		
		return neuralVile;
	}
	// Devices // Used to gain an edge in playing
	public Item newWallBomb()
	{
		Item wallBomb = new Item('*', Color.cyan, Type.DEVICE ,
				"Wall Bomb", 
				"Stuck in a room with no doors? Grab this handy"
				+ "  wall bomb and place it oewhere. It will digg aa tunnel for you!",
				0, 0
				, 200);
		if(world != null)
			world.spawnInside(r.nextInt(5), wallBomb);
		
		return wallBomb;
	}
	public Item newEnergySiphon()
	{
		Item energySiphon = new Item('`', Color.white, Type.GUN ,
				"Energy Siphon", 
				"A device which allows you to siphon energy form the area around you."
				+ " When properly integrated, the siphon, using energy transmitted through the air, "
				+ "can help you power your devices.",
				0, 0
				, 200);
		if(world != null)
			world.spawnInside(r.nextInt(5), energySiphon);
		
		return energySiphon;
	}
	public Item newTemporalScanner()
	{
		Item temporalScanner = new Item('~', Color.YELLOW,  Type.GUN ,
				"Temporal Scanner", 
				"This high fidelity device scans the time and space around you, allowing for an acurate"
				+ " read out of your surrondings. This unit drains energy quickly so you'll have to "
				+ " have a powerfull enough battery to handle this.",
				0, 0
				, 1000);
		if(world != null)
			world.spawnInside(r.nextInt(5), temporalScanner);
		
		return temporalScanner;
		
	}

	public Item newMiningBeam()
	{
		Item miningBeam = new Item('=', Color.GRAY,  Type.GUN ,
				"Mining Beam", 
				"Standard issue industrial mining beam. Good for cutting rocks.",
				0, 0
				, 20);
		if(world != null)
			world.spawnInside(r.nextInt(5), miningBeam);
		
		return miningBeam;
	}
	
	/*
	 *		Auto Plasma Items --------------------------------------------
	 */
	public Item newPlasmaPack()
	{
		Item plasmaPack = new Item((char)253, Color.CYAN,  Type.APLASMA ,
				"Plasma Pack", 
				"A pack of plasma pods, precisely six, good for cracking with the boys"
				+ " on a hunger starved night when nothing else is around. "
				+ "If you bring back the pods, you'll get a discount on your next pack!",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaPack);
		return plasmaPack;
	}
	public Item newPlasmaPod()
	{
		Item plasmaPod = new Item((char)249, Color.CYAN, Type.APLASMA ,
				"Plasma Pod", 
				"A small plasma pod which will hold your plasma nicely podded up."
				+ " Completely not likely to leak in your bag, so don't worry if you're afraid of breaking it."
				+ "No warranty, and use at your own risk, you're dealing with plasma for good sakes... !",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaPod);
		return plasmaPod;
	}
	public Item newVictoryItem(int depth)
	{
        Item item = new Item('*', AsciiPanel.brightWhite, Type.APLASMA,
        		"Teddy Bear",
        		"This is the one thing you need to win the game. Walk to the rrd staircase and you win.", 
        		0, 0
        		,100);
        
        if(world != null)
        	world.addAtEmptyLocation(depth, item);
        return item;
    }
	/*
	 *		Plasma Items --------------------------------------------------
	 */
	public Item newPlasmaJuice()
	{
		Item plasmaJuice = new Item((char)5, Color.CYAN, Type.PLASMA ,
				"Temporal Plasma Pack", 
				"This nifty item squeezes a shit ton of plasma into a tiny little fucking box."
				+ " Technically it doesn't do that, but it allows you acess to plasma from different points in the "
				+ "expnsion of the universe. A nifty little device, hold onto it.",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaJuice);
		
		return plasmaJuice;
	}
	/*
	 *		Part Items --------------------------------------------------
	 */
	public Item newAnimatronicSkeleton()
	{
		Item animatronicSkeleton = new Item('#', Color.GRAY, Type.PART,
				"Animatronic Skeleton", 
				"A carefully crafted, all purpose skeleton used for making makeshift robots of all sizes and shaped."
				+ " The center core seems to need some type of fluid to make the skeleton move."
				+ " A small slit is visible on teh underside next to the Noki Core logo.",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), animatronicSkeleton);
		
		return animatronicSkeleton;
	}
	public Item newVileOfBioReactant()
	{
		Item bioReactant = new Item((char)239, Color.BLUE,  Type.PART,
				"Bio Chemical Reactant", 
				"You've scored a vile of bio chemical mutant reactant which means you'll"
				+ " be able to  mutate yourself. This stuff is addictive, like tatoos, or heroin, "
				+ " so make sure you don't mutate yourself to the grave.",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), bioReactant);
		return bioReactant;
	}
	public Item newHeatShieldShard()
	{
		Item heatShieldShard = new Item((char)239, Color.BLUE,  Type.PART,
				"Heat Shield Shard", 
				"A shard of shield which must have fallen off a scaled industrial heat shield."
				+ " These things are rare so hold on to it. Be careful, you might be in trouble "
				+ "if you get caught with one of those out in the open."
				+" With enough of these you'd be able to make yourself a proper heat shield.",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), heatShieldShard);
		return heatShieldShard;
	}
	public Item newStickOfRam()
	{
		Item stickOfRam = new Item((char)95, Color.BLUE,  Type.PART ,
				"Stick of RAM", 
				"A good ole, standard size, 1 TB stick of ram."
				+ "This stck happens to have a stylized red casing, with rgb light hooks, and "
				+ "various other party mode features. Works well in parellel with another stick of ram.",
				0, 0
				, 500);
		if(world != null)
			world.spawnInside(r.nextInt(5), stickOfRam);
		return stickOfRam;
	}
}