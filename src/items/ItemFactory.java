package items;

import wolrdbuilding.Palette;
import wolrdbuilding.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory implements Serializable
{
	private World world;
	public Random r;
	private List<Item> itemList;
	
	public ItemFactory(World world)
	{
		this.world = world;
		this.r = new Random();
		initItemList();
	}
	public ItemFactory()
	{
		this.world = null;
		this.r = new Random();
		initItemList();
	}
	public Item getRandomItem()
	{
		return itemList.get(r.nextInt(itemList.size()));
	}
	public void initItemList()
	{
		itemList = new ArrayList<Item>();
		//`
		itemList.add(newPlateCarrier6());
		itemList.add(newPlateCarrier5());
		itemList.add(newPlateCarrier4());
		itemList.add(newPlateCarrier3());
		itemList.add(newPlateCarrier2());
		itemList.add(newCompensator45());
		itemList.add(newMags45());
		itemList.add(newShotgunChoke());
		itemList.add(newShotgunCompensator());
		itemList.add(newCompensator76251());
		itemList.add(newExtendedMags76251());
		itemList.add(newExtendedMags762());
		itemList.add(newExtendedMags556());
		itemList.add(newCompensator556());
		itemList.add(newCompensator762());
		itemList.add(newForegripSway());
		itemList.add(newForegripVertical());
		itemList.add(newForegripHorizontal());
		itemList.add(newSuppressor57());
		itemList.add(newSuppressor45());
		itemList.add(newSuppressor76251());
		itemList.add(newSuppressor762());
		itemList.add(newSuppressor556());
		itemList.add(newLpvoMarch());
		itemList.add(newLpvoBurris());
		itemList.add(newLpvoVortex());
		itemList.add(newMonstrumSight());
		itemList.add(newMaxHealth());
		itemList.add(newArmorPlate3());
		itemList.add(newWeaponCharm());
		itemList.add(newTwelveGauge());
		itemList.add(newKelTecKSG());
		itemList.add(newAug());
		itemList.add(newMkMutant());
		itemList.add(newDvlSniper());
		itemList.add(newHkUMP());
		itemList.add(newGlock18C());
		itemList.add(newSevenSix51());
		itemList.add(newKar98());
		itemList.add(newWinchester());
		itemList.add(newThirtyEight());
		itemList.add(newChiefsSpecial());
		itemList.add(newCZSHADOW());
		itemList.add(newFortyFive());
		itemList.add(newM1911());
		itemList.add(newAdrenaline());
		itemList.add(newMorphine());
		itemList.add(newBodySling());
		itemList.add(newTacticalRig());
		itemList.add(newRpgGrenade());
		itemList.add(newFiveSevenAmmo());
		itemList.add(newP90());
		itemList.add(newRpg());
		itemList.add(newVector());
		itemList.add(newHkmp7());
		itemList.add(newRifleGunParts());
		itemList.add(newPistolGunParts());
		itemList.add(newGunParts());
		itemList.add(newPs762());
		itemList.add(newAk74());
		itemList.add(newM855556());
		itemList.add(newM4a1());
		itemList.add(newHoloSight());
		itemList.add(newRedDotSight());
		itemList.add(newPstGZH100());
		itemList.add(newApsx());
		itemList.add(newPstGZH());
		itemList.add(newGlock19ExtendedMags());
		itemList.add(newBlueLaser());
		itemList.add(newBaldProFlashlight());
		itemList.add(newRmrRedDot());
		itemList.add(newGlockCompensator());
		itemList.add(newGlockSupressor());
		itemList.add(newGlock19());
		itemList.add(newBoltAction());
		itemList.add(newUniqueKey());
		itemList.add(newHealKit5());
		itemList.add(newHealKit4());
		itemList.add(newHealKit3());
		itemList.add(newHealKit2());
		itemList.add(newHealKit1());
		itemList.add(newFoldedSteelKnife());
		itemList.add(newCleaver());
		itemList.add(newSerratedKnife());
		itemList.add(newAdvancedPants());
		itemList.add(newHybridPants());
		itemList.add(newKevlarPants());
		itemList.add(newArmorPants());
		itemList.add(newArmorSleevesTwo());
		itemList.add(newArmorSleeves());
		itemList.add(newKevlarSleeves());
		itemList.add(newLeatherSleeves());
		itemList.add(newBasicHelmetII());
		itemList.add(newBasicTorso());
		itemList.add(newHybridTorso());
		itemList.add(newCeramicTorso());
		itemList.add(newHeavyHelmet());
		itemList.add(newAdvancedHelmet());
		itemList.add(newBasicHelmet());
		itemList.add(newKevlarTorso());
		itemList.add(newAluSplint());
		itemList.add(newClearanceGreen());
		itemList.add(newMedicalKit());
		itemList.add(newSurgeryKit());
		itemList.add(newRustyKnife());
		itemList.add(newOpticalMagnifier());
		itemList.add(newVisorDehazer());
		itemList.add(newClearanceRed());
		itemList.add(newTerrainMapper());
		itemList.add(newOre());
		itemList.add(newTimber());
		itemList.add(newFarmAxe());
		itemList.add(newClothPack());
		itemList.add(newArrow());
		itemList.add(newQuiver());
		itemList.add(newLongBow());
		itemList.add(newPickAxe());
		itemList.add(newSmokeGrenade());
		itemList.add(newFragGrenade());
		itemList.add(newArmor1Chest());
		itemList.add(newTurkey());
		itemList.add(newStealthDevice());
		itemList.add(newBlueClearance());
		itemList.add(newFullHeal());
		itemList.add(newPaperBook());
		itemList.add(newScopedRifle());
		itemList.add(newMacroUzi());
		itemList.add(newDevSword());
		itemList.add(newWallBomb());
		itemList.add(newMiningBeam());
		itemList.add(newPlasmaPack());
		itemList.add(newPlasmaPod());
		itemList.add(newPlasmaJuice());
		itemList.add(newHeatShieldShard());
		itemList.add(newStickOfRam());
		itemList.add(newInvisibilityCloak());
		itemList.add(newNeuralLink());
		itemList.add(newNeuronExten());
		itemList.add(newMemModule());
		itemList.add(newCortexChip());
		itemList.add(newBasicAiUnit());
		itemList.add(newClearanceGold());
		itemList.add(newHelmet3());
		itemList.add(newHelmet2());
		itemList.add(newHelmet1());
		itemList.add(newConKit());
		itemList.add(newMedicinal());
		itemList.add(newFirstAid());
		itemList.add(newBandages());
		itemList.add(newGR2());
		itemList.add(newGR5());
		itemList.add(newGR7());
		itemList.add(newRedVisor());
		itemList.add(newBlueVisor());
		itemList.add(newDirtBrick());
		itemList.add(newOxygenMask());
		itemList.add(newWaterCannon());
		itemList.add(newSpoiledPlasma());
		itemList.add(newCyberneticSyringe());
		itemList.add(newShotgun());
		itemList.add(newRifle());
		itemList.add(newPistol());
		itemList.add(newPeaShooter());
		itemList.add(newPlasmaAxe());
		itemList.add(newRoseBerkinstocks());
		itemList.add(newBioLard());
		itemList.add(newNanoSword());
		itemList.add(newDiscoBall());
		itemList.add(newTunnelAxe());
		itemList.add(newSparklingBoots());
		itemList.add(newReflectiveShall());
	}
	public Item newReflectiveShall()
	{
		Item reflectiveShall = new Item((char)29, Palette.purple, Type.STEALTH,
				"Reflective Shall", 
				"A loose shall which drapes over your neck and reflects all the light that hits it.",
				25,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), reflectiveShall);
		return reflectiveShall;
	}
	public Item newSparklingBoots()
	{
		Item sparklingBoots = new Item((char)121, Palette.purple, Type.STEALTH,
				"Sparkling Boots", 
				"Some glittery sparkling boots have found you, and now it's time to find yourself!",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), sparklingBoots);
		return sparklingBoots;
	}
	public Item newTunnelAxe()
	{
		Item tunnelAxe = new Item((char)121, Palette.monoPurple, Type.DEVICE,
				"Tunneling Axe", 
				"It sports a nice grip, and an even nicer blade. It's sharp so be carefull. It will cut metal, wood, dirt, enemies, allies, and anything else that gets in its way. It's a damn sharp thing, and it's a force to be reckoned with.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), tunnelAxe);
		return tunnelAxe;
	}
	public Item newDiscoBall()
	{
		Item discoBall = new Item((char)121, Palette.monoRed, Type.WEAPON,
				"Disco Ball", 
				"An egg shaped disco ball. Some groovy bird must've given birth to this thing, groovy.",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), discoBall);
		return discoBall;
	}
	public Item newNanoSword()
	{
		Item nanoSword = new Item((char)121, Palette.monoRed, Type.WEAPON,
				"Nano Sword", 
				"A sword made of nano bots! It slices, it shoots, and it's pretty smart.",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), nanoSword);
		return nanoSword;
	}
	public Item newBioLard()
	{
		Item bioLard = new Item((char)121, Palette.green, Type.CONSUMABLE,
				"Bio Lard", 
				"Some kind of lab grown fat. It's more like lard than butter. Akin to coconut oil but clearer and denser. It tastes like you're drinking water and brushing your teeth, in a good way.",
				15,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), bioLard);
		return bioLard;
	}
	public Item newRoseBerkinstocks()
	{
		Item roseBerkinstocks = new Item((char)121, Palette.pink, Type.LEGS,
				"Rose Berkinstocks", 
				"Some fine rose colored birkenstocks. They have a mustard stain near the zipper.",
				15,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), roseBerkinstocks);
		return roseBerkinstocks;
	}
	public Item newPlasmaAxe()
	{
		Item plasmaAxe = new Item((char)121, Palette.monoPurple, Type.DEVICE,
				"Plasma Axe", 
				"A nifty tool. Cuts through rocks pretty damn good. Fueled by plasma and a quest for a better future.",
				3,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaAxe);
		return plasmaAxe;
	}
	public Item newPeaShooter()
	{
		Item peaShooter = new Item((char)121, Palette.red, Type.GUN,
				"Pea Shooter", 
				"A nice little pocket gun. It shoots peas, as expected. It doesn't do a lot of damage but it might feed your opponent!",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), peaShooter);
		return peaShooter;
	}
	public Item newPistol()
	{
		Item pistol = new Item((char)234, Palette.red, Type.GUN,
				"Pistol", 
				"Standard issue P two nine eleven. The P2911 is beautifully crafted by expert craftsmen and balanced by top engineers for stability, accuracy and maximum zuc. Great for destroying NPCS!",
				25,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), pistol);
		return pistol;
	}
	public Item newRifle()
	{
		Item rifle = new Item((char)234, Palette.red, Type.GUN,
				"Rifle", 
				"Standard issue lever action rifle designed by the Northingham Corporation and printed by your local 3D printer. The laser lever reloads the chamber extremely effectively always does so at the wrong time.",
				60,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), rifle);
		return rifle;
	}
	public Item newShotgun()
	{
		Item shotgun = new Item((char)234, Palette.red, Type.GUN,
				"Shotgun", 
				"Standard issue auto shotgun developed by the Free Planet Militia. Designed to liberate two shells at a time for maximum spread and home defense. Best used at close range.",
				35,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), shotgun);
		return shotgun;
	}
	public Item newCyberneticSyringe()
	{
		Item cyberneticSyringe = new Item((char)127, Palette.monoGreen, Type.MELEE,
				"Cybernetic Syringe", 
				"A junkie needle. Blunt like a butter knife, and dirty like the underside of a dead plasma rat. Good for shooting up and passing around diseases.",
				15,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), cyberneticSyringe);
		return cyberneticSyringe;
	}
	public Item newSpoiledPlasma()
	{
		Item spoiledPlasma = new Item((char)92, Palette.cyan, Type.BAD_PLASMA,
				"Spoiled Plasma", 
				"A small plasma pod used by some junkie. The plasma seems unstable. Probably produces a great high. Probably why the junkie lost his plasma in the first place.",
				15,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), spoiledPlasma);
		return spoiledPlasma;
	}
	public Item newWaterCannon()
	{
		Item waterCannon = new Item((char)121, Palette.red, Type.GUN,
				"Water Cannon", 
				"A machine which bonds two hydrogen molecules with one oxygen molecule to produce the most basic fire extinguishing liquid",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), waterCannon);
		return waterCannon;
	}
	public Item newOxygenMask()
	{
		Item oxygenMask = new Item((char)121, Palette.paperBlue, Type.OXYGEN,
				"Oxygen Mask", 
				"A bio mask which feeds off of carbon dioxide to produce fresh air.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), oxygenMask);
		return oxygenMask;
	}
	public Item newDirtBrick()
	{
		Item dirtBrick = new Item((char)121, Palette.brown, Type.BRICK,
				"Dirt Brick", 
				"A deep brown dirt brick. It's tough, and good for making internal walls, but brittle. Will crumble under enough pressure.",
				1,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), dirtBrick);
		return dirtBrick;
	}
	public Item newBlueVisor()
	{
		Item blueVisor = new Item((char)121, Palette.perfectBlue, Type.VISION,
				"Blue Light Visor", 
				"A blue light visor to fit into any strandard NPS.",
				15,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), blueVisor);
		return blueVisor;
	}
	public Item newRedVisor()
	{
		Item redVisor = new Item((char)121, Palette.perfectBlue, Type.VISION,
				"Red Light Visor", 
				"A red light visor with standard NPS attachment device and vision clearing AI chip",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), redVisor);
		return redVisor;
	}
	public Item newGR7()
	{
		Item GR7 = new Item((char)121, Palette.monoYellow, Type.INVENTORY,
				"GR7 Mk2 Tactical Bag", 
				"American made, bullet proof, 35 liter capacity all purpose tactical bag. Nano stitching allows 200lb support on each thread. Fire proof.",
				35,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), GR7);
		return GR7;
	}
	public Item newGR5()
	{
		Item GR5 = new Item((char)121, Palette.monoYellow, Type.INVENTORY,
				"GR5 Tactical Bag", 
				"American made, bullet proof, 20 liter capacity all purpose tactical bag.",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), GR5);
		return GR5;
	}
	public Item newGR2()
	{
		Item GR2 = new Item((char)121, Palette.monoYellow, Type.INVENTORY,
				"GR7 Tactical Bag", 
				"American made, bullet proof, 30 liter capacity all purpose tactical bag. Fire proof.",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), GR2);
		return GR2;
	}
	public Item newBandages()
	{
		Item bandages = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"Brite Aid Bandages", 
				"Two sticky sides and an absorbent middle. Comes with many skin tone choices. Durable and good for healing small wounds.",
				50,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), bandages);
		return bandages;
	}
	public Item newFirstAid()
	{
		Item firstAid = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"First Aid Kit", 
				"Gauze. Anesthetic. Probiotic. Sanitizing Alcohol. Needle. Pliers. Nano thread.",
				150,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), firstAid);
		return firstAid;
	}
	public Item newMedicinal()
	{
		Item medicinal = new Item((char)127, Palette.paperGreen, Type.PASSIVE_HEALING,
				"Medicinal Herb", 
				"A synthesized lab grown medicine bio herb which heals slowly over time when smokes or sprinkled into small wounds.",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), medicinal);
		return medicinal;
	}
	public Item newConKit()
	{
		Item conKit = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"Concussion Kit", 
				"Nano compressed neck brace and brain stimulator. Neck brace administers local anesthetic to the back of the neck and connect directly to spine to facilitate brain repair.",
				450,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), conKit);
		return conKit;
	}
	public Item newHelmet1()
	{
		Item helmet1 = new Item((char)234, Palette.manaBlue, Type.HELMET,
				"EVA Helmet", 
				"Standard EVA helmet. Looks good and will keep the pressure good.",
				3,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), helmet1);
		return helmet1;
	}
	public Item newHelmet2()
	{
		Item helmet2 = new Item((char)234, Palette.manaBlue, Type.HELMET,
				"Dome Helmet", 
				"Dome helmet design for panoramic viewing. Hologram and display casting possible.",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), helmet2);
		return helmet2;
	}
	public Item newHelmet3()
	{
		Item helmet3 = new Item((char)234, Palette.manaBlue, Type.HELMET,
				"Fractal Helmet", 
				"Air tight. Fractal structural design. 220 degree visor with holographic displays. Rated most comfortable helmet by the Hitch Hikers Guide to the Galaxy.",
				8,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), helmet3);
		return helmet3;
	}
	public Item newClearanceGold()
	{
		Item clearanceGold = new Item((char)173, Palette.monoYellow, Type.GOLD,
				"Gold Card", 
				"A circuit like design runs around the bezel of the clearance card. A single biometric identity chip sits at the center of it. A true work of art, and a key into every room.",
				6,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), clearanceGold);
		return clearanceGold;
	}
	public Item newBasicAiUnit()
	{
		Item basicAiUnit = new Item((char)167, Palette.manaBlue, Type.AI_CHIP,
				"I900 AI Chip", 
				"Top of the line AI chip for the NewMan Mk5 Droid made by RBT. Slides right into an AI assembly block and any Mk5 droid. Balanced to be tolerable by humans but still completely conscience and intelligent.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), basicAiUnit);
		return basicAiUnit;
	}
	public Item newCortexChip()
	{
		Item cortexChip = new Item((char)167, Palette.monoPerfect, Type.AI_ATTACHMENT,
				"Cortex Facilitator", 
				"An AI chip attachment which gives the system meaning. Comes with 12 presets for perfect maintenance routines/",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), cortexChip);
		return cortexChip;
	}
	public Item newMemModule()
	{
		Item memModule = new Item((char)167, Palette.monoPerfect, Type.AI_ATTACHMENT,
				"Instant Memory Module", 
				"A quantum memory module designed for efficient memory storage.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), memModule);
		return memModule;
	}
	public Item newNeuronExten()
	{
		Item neuronExten = new Item((char)167, Palette.monoPerfect, Type.AI_ATTACHMENT,
				"Neuron Extension", 
				"An array of thousands of cybernetic mesh neurons which plug directly into the neural link.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), neuronExten);
		return neuronExten;
	}
	public Item newNeuralLink()
	{
		Item neuralLink = new Item((char)101, Palette.manaBlue, Type.AUGMENTATION,
				"Neural Link", 
				"The connecting piece between the human brain and an AI chip designed to manage it. Requires a spine port to be active.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), neuralLink);
		return neuralLink;
	}
	public Item newInvisibilityCloak()
	{
		Item invisibilityCloak = new Item((char)131, Palette.purple, Type.STEALTH,
				"Cloak of Shadows", 
				"It's better than being seen!",
				50,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), invisibilityCloak);
		return invisibilityCloak;
	}
	public Item newStickOfRam()
	{
		Item StickOfRam = new Item((char)95, Palette.brown, Type.PART,
				"Stick of Ram", 
				"A good ole standard 1 terabyte stick of dual channel ram.",
				500,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), StickOfRam);
		return StickOfRam;
	}
	public Item newHeatShieldShard()
	{
		Item HeatShieldShard = new Item((char)95, Palette.brown, Type.PART,
				"Heat Shield Shard", 
				"A shard of a thermal shield layer typically found on C class spaceships.",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), HeatShieldShard);
		return HeatShieldShard;
	}
	public Item newPlasmaJuice()
	{
		Item plasmaJuice = new Item((char)5, Palette.cyan, Type.PLASMA,
				"Plasma Juice", 
				"A nifty little plasma device which fits a whole lot of plasma into a whole little of space.",
				500,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaJuice);
		return plasmaJuice;
	}
	public Item newPlasmaPod()
	{
		Item plasmaPod = new Item((char)249, Palette.cyan, Type.APLASMA,
				"Plasma Pod", 
				"A pod of plasma. Don't eat it or you might die from gastrointestinal problems.",
				500,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaPod);
		return plasmaPod;
	}
	public Item newPlasmaPack()
	{
		Item plasmaPack = new Item((char)253, Palette.cyan, Type.APLASMA,
				"Plasma Pack", 
				"Seven plasma pods hanging out together make a plasma pack!",
				700,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), plasmaPack);
		return plasmaPack;
	}
	public Item newMiningBeam()
	{
		Item miningBeam = new Item((char)101, Palette.gray, Type.MINING,
				"Mining Beam", 
				"Standardized industrial mining beam. It's good for cutting rocks.",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), miningBeam);
		return miningBeam;
	}
	public Item newWallBomb()
	{
		Item wallBomb = new Item((char)121, Palette.monoPurple, Type.DEVICE,
				"Wall Bomb", 
				"Fires a directed and very powerful plasma blast into the wall upon which it was attached. Ideal tunneling tool.",
				20,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), wallBomb);
		return wallBomb;
	}
	public Item newDevSword()
	{
		Item devSword = new Item((char)234, Palette.red, Type.GUN,
				"Dev Sword", 
				"A developmental tool which has transcended many compilers, squashed many bugs, and ultimately killed all the foes of the Starborn Alliance.",
				10000,
				Item.Rarity.ONE_OF_A_KIND);
		if(world != null)
			world.spawnInside(r.nextInt(5), devSword);
		return devSword;
	}
	public Item newMacroUzi()
	{
		Item macroUzi = new Item((char)234, Palette.red, Type.GUN,
				"Macro Uzi", 
				"A plasma UZI in the macro variety. Everything you loved about the micro, stuffed in a same sized package with macro capabilities.",
				100,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), macroUzi);
		return macroUzi;
	}
	public Item newScopedRifle()
	{
		Item scopedRifle = new Item((char)234, Palette.red, Type.GUN,
				"Scoped Rifle", 
				"A classic rifle with a good ranged scope. Will kill an enemy form far away while keeping you out of danger.",
				50,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), scopedRifle);
		return scopedRifle;
	}
	public Item newPaperBook()
	{
		Item paperBook = new Item((char)1, Palette.cyan, Type.PAPER_BOOK,
				"Paper Book", 
				"You've come across a relic of an old world. A piece from the core. It's symbols scribed on the front mean nothing to you.",
				1000,
				Item.Rarity.ONE_OF_A_KIND);
		if(world != null)
			world.spawnInside(r.nextInt(5), paperBook);
		return paperBook;
	}
	public Item newFullHeal()
	{
		Item fullHeal = new Item((char)127, Palette.green, Type.FULL_HEAL,
				"Adrenaline Regulator", 
				"Instructions : Place regulator at the back of the neck. Once light is green the regulator is aligned with the spinal cord, hit the apple button and relax, this might hurt for a second.",
				1000,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), fullHeal);
		return fullHeal;
	}
	public Item newBlueClearance()
	{
		Item blueClearance = new Item((char)173, Palette.blue, Type.BLUE_CARD,
				"Blue Clearance", 
				"Blue clearance chip. This will open most doors, but looks like it's falling apart quickly.",
				10,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), blueClearance);
		return blueClearance;
	}
	public Item newStealthDevice()
	{
		Item stealthDevice = new Item((char)29, Palette.purple, Type.STEALTH,
				"Stealth Knick Knack", 
				"Martian stealth cloaking technology. Works really well for helping you not be seen.",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), stealthDevice);
		return stealthDevice;
	}
	public Item newTurkey()
	{
		Item turkey = new Item((char)1, Palette.yellow, Type.TURKEY,
				"Fresh Turkey Carcass", 
				"Freshly shot, good sized 29lb Turkey. Free range and non gmo grass fed Wagyura Farms.",
				1000,
				Item.Rarity.UNCOMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), turkey);
		return turkey;
	}
	public Item newArmor1Chest()
	{
		Item armor1Chest = new Item((char)191, Palette.darkerGray, Type.ARMOR,
				"Leather Chest Piece", 
				"A tough synthetic patch of leather which straps to the chest piece",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), armor1Chest);
		return armor1Chest;
	}
	public Item newFragGrenade()
	{
		Item fragGrenade = new Item((char)67, Palette.green, Type.UTILITY,
				"Frag Grenade", 
				"Standard issue military grenade. 4 second timer with a tastefully gold pin.",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), fragGrenade);
		return fragGrenade;
	}
	public Item newSmokeGrenade()
	{
		Item smokeGrenade = new Item((char)67, Palette.green, Type.UTILITY,
				"Smoke Grenade", 
				"Standard issue space marine smoke grenade. 3 second timer.",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), smokeGrenade);
		return smokeGrenade;
	}
	public Item newPickAxe()
	{
		Item pickAxe = new Item((char)67, Palette.green, Type.MINING,
				"Pick Axe", 
				"Some soft metal at the end of an old stick.",
				35,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), pickAxe);
		return pickAxe;
	}
	public Item newLongBow()
	{
		Item longBow = new Item((char)69, Palette.blue, Type.RANGED,
				"Long Bow", 
				"A well crafted bow. Elvish design.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), longBow);
		return longBow;
	}
	public Item newQuiver()
	{
		Item quiver = new Item((char)69, Palette.blue, Type.BOW,
				"Quiver", 
				"A dark leather quiver good to hold 20 arrows. Elvish design.",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), quiver);
		return quiver;
	}
	public Item newArrow()
	{
		Item arrow = new Item((char)30, Palette.blue, Type.ARROW,
				"Basic Arrow", 
				"A stick with a sharp metal point at the end.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), arrow);
		return arrow;
	}
	public Item newClothPack()
	{
		Item clothPack = new Item((char)89, Palette.white, Type.INVENTORY,
				"Cloth Sack", 
				"A cloth sack with 2 large pockets for storage",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), clothPack);
		return clothPack;
	}
	public Item newFarmAxe()
	{
		Item farmAxe = new Item((char)10, Palette.red, Type.AXE,
				"Farmers Axe", 
				"A sturdy farmers axe. Will cut many a tree.",
				45,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), farmAxe);
		return farmAxe;
	}
	public Item newTimber()
	{
		Item timber = new Item((char)10, Palette.brown, Type.TIMBER,
				"Pile of Timber", 
				"Good raw timber. Perfect for crafting.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), timber);
		return timber;
	}
	public Item newOre()
	{
		Item ore = new Item((char)10, Palette.gray, Type.ORE,
				"Ore", 
				"Good raw ore. Perfect for crafting.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), ore);
		return ore;
	}
	public Item newTerrainMapper()
	{
		Item terrainMapper = new Item((char)10, Palette.gray, Type.DEVICE,
				"Terrain Mapper", 
				"A echo response terrain mapper good for identifying the area around you.",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), terrainMapper);
		return terrainMapper;
	}
	public Item newClearanceRed()
	{
		Item clearanceRed = new Item((char)173, Palette.red, Type.RED,
				"Red Card", 
				"A circuit like design runs around the bezel of the clearance card. A single biometric identity chip sits at the center of it. A true work of art, and a key into the red room.",
				1,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), clearanceRed);
		return clearanceRed;
	}
	public Item newVisorDehazer()
	{
		Item visorDehazer = new Item((char)56, Palette.purple, Type.VISION,
				"Visor Dehazer", 
				"A dehazer desgined to increae your vision through the visor.",
				15,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), visorDehazer);
		return visorDehazer;
	}
	public Item newOpticalMagnifier()
	{
		Item opticalMagnifier = new Item((char)56, Palette.purple, Type.VISION,
				"Optical Magnifier", 
				"Visor attachement which allows for optical magnification.",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), opticalMagnifier);
		return opticalMagnifier;
	}
	public Item newRustyKnife()
	{
		Item rustyKnife = new Item((char)56, Palette.red, Type.MELEE,
				"Rusty Knife", 
				"A rusty shank. Good for shanking.",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), rustyKnife);
		return rustyKnife;
	}
	public Item newSurgeryKit()
	{
		Item surgeryKit = new Item((char)127, Palette.green, Type.HEALTH,
				"Surgery Kit", 
				"Comprehensive kit for all things surgery.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), surgeryKit);
		return surgeryKit;
	}
	public Item newMedicalKit()
	{
		Item medicalKit = new Item((char)127, Palette.green, Type.FULL_HEAL,
				"Medical Kit", 
				"Comprehensive kit for all types of wounds and damages caused to soft tissue.",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), medicalKit);
		return medicalKit;
	}
	public Item newClearanceGreen()
	{
		Item clearanceGreen = new Item((char)173, Palette.green, Type.GREEN,
				"Green Card", 
				"A circuit like design runs around the bezel of the clearance card. A single biometric identity chip sits at the center of it. A true work of art, and a key into the green room.",
				2,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), clearanceGreen);
		return clearanceGreen;
	}
	public Item newAluSplint()
	{
		Item aluSplint = new Item((char)127, Palette.green, Type.SPLINT,
				"Aluminum Splint", 
				"A sturdy splint. Good for improving mobility while on a fractured limb.",
				2,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), aluSplint);
		return aluSplint;
	}
	public Item newKevlarTorso()
	{
		Item kevlarTorso = new Item((char)19, Palette.gray, Type.TORSO,
				"Kevlar Body Armor", 
				"Rugged armor made out of kevlar. Good for stopping low caliber bullets",
				200,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), kevlarTorso);
		return kevlarTorso;
	}
	public Item newBasicHelmet()
	{
		Item basicHelmet = new Item((char)19, Palette.gray, Type.HEAD,
				"Shell Helmet", 
				"Standard issue helmet. Can stop a low caliber round sometimes.",
				200,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), basicHelmet);
		return basicHelmet;
	}
	public Item newAdvancedHelmet()
	{
		Item advancedHelmet = new Item((char)19, Palette.gray, Type.HEAD,
				"Rugged Helmet", 
				"Advanced helmet. Can stop low to mid caliber rounds.",
				400,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), advancedHelmet);
		return advancedHelmet;
	}
	public Item newHeavyHelmet()
	{
		Item heavyHelmet = new Item((char)19, Palette.gray, Type.HEAD,
				"Altyn", 
				"Extremely armored helmet. Contains small eye holes to look through.",
				600,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), heavyHelmet);
		return heavyHelmet;
	}
	public Item newCeramicTorso()
	{
		Item ceramicTorso = new Item((char)19, Palette.gray, Type.TORSO,
				"Ceramic Body Armor", 
				"Full coverage ceramic body armor",
				400,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), ceramicTorso);
		return ceramicTorso;
	}
	public Item newHybridTorso()
	{
		Item hybridTorso = new Item((char)19, Palette.gray, Type.TORSO,
				"Hybrid Body Armor", 
				"Fll coverage synthetic and ceramic body armor with comfy fleece lining.",
				600,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), hybridTorso);
		return hybridTorso;
	}
	public Item newBasicTorso()
	{
		Item basicTorso = new Item((char)19, Palette.gray, Type.TORSO,
				"Armored Shirt", 
				"A cotton shirt with some armored parts. Synthetic bullet stopping armor that sometimes works.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), basicTorso);
		return basicTorso;
	}
	public Item newBasicHelmetII()
	{
		Item basicHelmetII = new Item((char)19, Palette.gray, Type.HEAD,
				"Tough Beanie", 
				"A comfy beanie, with armor pieces. It'll help from random things falling on your head.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), basicHelmetII);
		return basicHelmetII;
	}
	public Item newLeatherSleeves()
	{
		Item leatherSleeves = new Item((char)19, Palette.gray, Type.ARMS,
				"Leather Sleeves", 
				"Leather sleeves that will keep you warm and block some damage",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), leatherSleeves);
		return leatherSleeves;
	}
	public Item newKevlarSleeves()
	{
		Item kevlarSleeves = new Item((char)19, Palette.gray, Type.ARMS,
				"Kevlar Sleeves", 
				"Kevlar sleeves that will stop a bullet sometimes",
				200,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), kevlarSleeves);
		return kevlarSleeves;
	}
	public Item newArmorSleeves()
	{
		Item armorSleeves = new Item((char)19, Palette.gray, Type.ARMS,
				"Armor Sleeves", 
				"Armor sleeves that will do a decent job of protecting your hands",
				400,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), armorSleeves);
		return armorSleeves;
	}
	public Item newArmorSleevesTwo()
	{
		Item armorSleevesTwo = new Item((char)19, Palette.gray, Type.ARMS,
				"Advanced Sleeves", 
				"Armor sleeves with advanced features that give you strength and will block most bullets",
				600,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), armorSleevesTwo);
		return armorSleevesTwo;
	}
	public Item newArmorPants()
	{
		Item armorPants = new Item((char)19, Palette.gray, Type.LEGS,
				"Shielded Pants", 
				"Comfy pants with synthetic armor patches, mostly around the groin",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), armorPants);
		return armorPants;
	}
	public Item newKevlarPants()
	{
		Item kevlarPants = new Item((char)19, Palette.gray, Type.LEGS,
				"Kevlar Pants", 
				"Less comfy pants with kevlar armor patches, mostly around the groin and knees.",
				200,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), kevlarPants);
		return kevlarPants;
	}
	public Item newHybridPants()
	{
		Item hybridPants = new Item((char)19, Palette.gray, Type.LEGS,
				"Hybrid Armor Pants", 
				"Rigid pants that have near full protection from melee and projectiles. Kevlar and synthetic armor material with rugged knee pads.",
				400,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), hybridPants);
		return hybridPants;
	}
	public Item newAdvancedPants()
	{
		Item advancedPants = new Item((char)19, Palette.gray, Type.LEGS,
				"Advanced Marine Pants", 
				"Advanced pants with nano tech that increases your legs speed and strength and provides the best armor coverage",
				600,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), advancedPants);
		return advancedPants;
	}
	public Item newSerratedKnife()
	{
		Item serratedKnife = new Item((char)56, Palette.red, Type.MELEE,
				"Serrated Knife", 
				"A serrated knife. Good for cutting bread.",
				45,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), serratedKnife);
		return serratedKnife;
	}
	public Item newCleaver()
	{
		Item Cleaver = new Item((char)56, Palette.red, Type.MELEE,
				"Cleaver", 
				"A cleaver. Excellent for cleaving. Two of these could really chop up some garlic.",
				60,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), Cleaver);
		return Cleaver;
	}
	public Item newFoldedSteelKnife()
	{
		Item foldedSteelKnife = new Item((char)56, Palette.red, Type.MELEE,
				"Folded Steel Knife", 
				"Knife made out of folded steel. Very tough. You will cut yourself using this knife.",
				90,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), foldedSteelKnife);
		return foldedSteelKnife;
	}
	public Item newHealKit1()
	{
		Item healKit1 = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"Big Bandage", 
				"A small kit good for fixing a bleed or two.",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), healKit1);
		return healKit1;
	}
	public Item newHealKit2()
	{
		Item healKit2 = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"First Aid Kit", 
				"A small kit good for fixing a bleed or five.",
				150,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), healKit2);
		return healKit2;
	}
	public Item newHealKit3()
	{
		Item healKit3 = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"Healing Kit", 
				"A small kit good for fixing a bleed and anesthetizing.",
				200,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), healKit3);
		return healKit3;
	}
	public Item newHealKit4()
	{
		Item healKit4 = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"H.I.N.D Trauma Kit", 
				"Help i'm nearly dead.",
				300,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), healKit4);
		return healKit4;
	}
	public Item newHealKit5()
	{
		Item healKit5 = new Item((char)127, Palette.paperGreen, Type.HEALING,
				"Bio Hacking Kit", 
				"For newly developed injuries and lifetime scars. Smooth over the wrinkles the bullets left in your skin.",
				500,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), healKit5);
		return healKit5;
	}
	public Item newUniqueKey()
	{
		Item uniqueKey = new Item((char)98, Palette.paperGreen, Type.UNIQUE,
				"Unique Key", 
				"A unique key. The key to what's beyond.",
				500,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), uniqueKey);
		return uniqueKey;
	}
	public Item newBoltAction()
	{
		Item boltAction = new Item((char)98, Palette.red, Type.GUN,
				"Bolt Action Rifle", 
				"A good old fashioned bolt action rifle.",
				130,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), boltAction);
		return boltAction;
	}
	public Item newGlock19()
	{
		Item glock19 = new Item((char)98, Palette.red, Type.GUN,
				"Glock 19", 
				"A trusty glizzy",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), glock19);
		return glock19;
	}
	public Item newGlockSupressor()
	{
		Item glockSupressor = new Item((char)98, Palette.red, Type.ATTACHMENT,
				"9mm Supressor", 
				"Threaded to 5mm. Reduces loudness range",
				50,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), glockSupressor);
		return glockSupressor;
	}
	public Item newGlockCompensator()
	{
		Item glockCompensator = new Item((char)98, Palette.red, Type.ATTACHMENT,
				"Glizzy Comp", 
				"Increase accuracy and ergonomics",
				50,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), glockCompensator);
		return glockCompensator;
	}
	public Item newRmrRedDot()
	{
		Item rmrRedDot = new Item((char)98, Palette.red, Type.ATTACHMENT,
				"RMR Red Dot Sight", 
				"Increase range, accuracy, and makes your gun look cool.",
				6,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), rmrRedDot);
		return rmrRedDot;
	}
	public Item newBaldProFlashlight()
	{
		Item baldProFlashlight = new Item((char)98, Palette.red, Type.ATTACHMENT,
				"Bald Pro Flash Light", 
				"Blinds the enemy, decreasing their accuracy and give you some extra range.",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), baldProFlashlight);
		return baldProFlashlight;
	}
	public Item newBlueLaser()
	{
		Item blueLaser = new Item((char)98, Palette.red, Type.ATTACHMENT,
				"Blue Laser", 
				"Increases range, accuracy, and makes your gun look cool.",
				100,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), blueLaser);
		return blueLaser;
	}
	public Item newGlock19ExtendedMags()
	{
		Item glock19ExtendedMags = new Item((char)98, Palette.red, Type.ATTACHMENT,
				"Glock Extended Mags", 
				"Increases ammo capacity",
				33,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), glock19ExtendedMags);
		return glock19ExtendedMags;
	}
	public Item newPstGZH()
	{
		Item pstGZH = new Item((char)98, Palette.red, Type.AMMO_9MM,
				"9x19 PST GZH", 
				"Tried and true",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), pstGZH);
		return pstGZH;
	}
	public Item newApsx()
	{
		Item apsx = new Item((char)98, Palette.red, Type.AMMO_46x30,
				"4.6 x 30 AP SX Ammo", 
				"Navy seal issued armor piercing ammo",
				30,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), apsx);
		return apsx;
	}
	public Item newPstGZH100()
	{
		Item pstGZH100 = new Item((char)98, Palette.red, Type.AMMO_9MM,
				"9x19 PST GZH", 
				"Tried and true",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), pstGZH100);
		return pstGZH100;
	}
	public Item newRedDotSight()
	{
		Item redDotSight = new Item((char)55, Palette.red, Type.ATTACHMENT,
				"Red Dot Sight", 
				"A nice sight increases aimed range by 5",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), redDotSight);
		return redDotSight;
	}
	public Item newHoloSight()
	{
		Item holoSight = new Item((char)55, Palette.red, Type.ATTACHMENT,
				"Holographic Sight", 
				"A nicer sight increases aimed range by 7",
				7,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), holoSight);
		return holoSight;
	}
	public Item newM4a1()
	{
		Item m4a1 = new Item((char)98, Palette.red, Type.GUN,
				"Colt M4A1", 
				"Americas most trusted rifle",
				300,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), m4a1);
		return m4a1;
	}
	public Item newM855556()
	{
		Item m855556 = new Item((char)98, Palette.red, Type.AMMO_556,
				"5.56 M855", 
				"Low pen high availability 556 ammo",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), m855556);
		return m855556;
	}
	public Item newAk74()
	{
		Item ak74 = new Item((char)98, Palette.red, Type.GUN,
				"Kalashnikova AK 74", 
				"Russias most trusted rifle",
				325,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), ak74);
		return ak74;
	}
	public Item newPs762()
	{
		Item ps762 = new Item((char)98, Palette.red, Type.AMMO_762,
				"PS 7.62 Ammo", 
				"Low pen high availability 762 ammo",
				100,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), ps762);
		return ps762;
	}
	public Item newGunParts()
	{
		Item gunParts = new Item((char)98, Palette.red, Type.GUN_PARTS,
				"Random Gun Parts", 
				"An assortment of random gun parts of different sorts",
				1000,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), gunParts);
		return gunParts;
	}
	public Item newPistolGunParts()
	{
		Item pistolGunParts = new Item((char)98, Palette.red, Type.PISTOL_GUN_PARTS,
				"Pistol Gun Parts", 
				"An assortment of random pistol parts. Just enough to make something work.",
				1000,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), pistolGunParts);
		return pistolGunParts;
	}
	public Item newRifleGunParts()
	{
		Item rifleGunParts = new Item((char)98, Palette.red, Type.RIFLE_GUN_PARTS,
				"RIFLE Gun Parts", 
				"An assortment of random rifle parts. Just enough to make something work.",
				1000,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), rifleGunParts);
		return rifleGunParts;
	}
	public Item newHkmp7()
	{
		Item hkmp7 = new Item((char)98, Palette.red, Type.GUN,
				"HK MP7", 
				"Navy seals issued MP7",
				220,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), hkmp7);
		return hkmp7;
	}
	public Item newVector()
	{
		Item vector = new Item((char)98, Palette.red, Type.GUN,
				"Kriss Vector", 
				"American made. True and reliable.",
				190,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), vector);
		return vector;
	}
	public Item newRpg()
	{
		Item rpg = new Item((char)98, Palette.red, Type.GUN,
				"RPG", 
				"Rocket Propelled Grenade. Quite inaccurate, highly deadly.",
				2000,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), rpg);
		return rpg;
	}
	public Item newP90()
	{
		Item P90 = new Item((char)98, Palette.red, Type.GUN,
				"FN P90", 
				"FN P90 is a SMG chambered for the 5.7×28mm cartridge. Belgian manufactured.",
				280,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), P90);
		return P90;
	}
	public Item newFiveSevenAmmo()
	{
		Item fiveSevenAmmo = new Item((char)98, Palette.red, Type.AMMO_57x28,
				"SS190 5.7 x 28mm", 
				"Devastatingly belgian.",
				150,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), fiveSevenAmmo);
		return fiveSevenAmmo;
	}
	public Item newRpgGrenade()
	{
		Item rpgGrenade = new Item((char)98, Palette.red, Type.AMMO_RPG,
				"RPG Explosive", 
				"Boom",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), rpgGrenade);
		return rpgGrenade;
	}
	public Item newTacticalRig()
	{
		Item tacticalRig = new Item((char)77, Palette.blue, Type.INVENTORY,
				"Tactical Rig", 
				"Chest rig for magazines, grenades, utilities, and other tactical facilities.",
				5,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), tacticalRig);
		return tacticalRig;
	}
	public Item newBodySling()
	{
		Item bodySling = new Item((char)77, Palette.blue, Type.INVENTORY,
				"Body Sling", 
				"Sling for hauling things on your back.",
				6,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), bodySling);
		return bodySling;
	}
	public Item newMorphine()
	{
		Item morphine = new Item((char)25, Palette.green, Type.STIMULANT,
				"Morphine Syringe", 
				"Stab yourself with this syringe and you'll be able to run full speed perfect form with broken legs.",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), morphine);
		return morphine;
	}
	public Item newAdrenaline()
	{
		Item adrenaline = new Item((char)25, Palette.green, Type.STIMULANT,
				"Adrenaline Syringe", 
				"Stab yourself with this syringe and you'll temporarily gain more head and torso vitality",
				10,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), adrenaline);
		return adrenaline;
	}
	public Item newM1911()
	{
		Item M1911 = new Item((char)98, Palette.red, Type.GUN,
				"Colt M1911", 
				"Chambered in .45, one of America's most trusty sidearms. Yeeehawk.",
				145,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), M1911);
		return M1911;
	}
	public Item newFortyFive()
	{
		Item fortyFive = new Item((char)98, Palette.red, Type.AMMO_45,
				"Point 45 ACP", 
				"Devastatingly American",
				40,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), fortyFive);
		return fortyFive;
	}
	public Item newCZSHADOW()
	{
		Item CZSHADOW = new Item((char)98, Palette.red, Type.GUN,
				"CZ 75 SP-01 Shadow", 
				"An all time favorite amongst Eastern Europeans",
				165,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), CZSHADOW);
		return CZSHADOW;
	}
	public Item newChiefsSpecial()
	{
		Item chiefsSpecial = new Item((char)98, Palette.red, Type.GUN,
				"Chiefs Special", 
				"A snub nosed revolver chambered for the .38 Special round.",
				165,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), chiefsSpecial);
		return chiefsSpecial;
	}
	public Item newThirtyEight()
	{
		Item thirtyEight = new Item((char)98, Palette.red, Type.AMMO_38_SPECIAL,
				"Point 38 Special", 
				"A popular revolver round.",
				36,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), thirtyEight);
		return thirtyEight;
	}
	public Item newWinchester()
	{
		Item winchester = new Item((char)98, Palette.red, Type.GUN,
				"Winchester Model 1873", 
				"Americas iconic rifle. Lever action. Reliable. Sexy. Chambered in .38 special.",
				165,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), winchester);
		return winchester;
	}
	public Item newKar98()
	{
		Item kar98 = new Item((char)98, Palette.red, Type.GUN,
				"KAR98K", 
				"World war 2 bolt action rifle.",
				165,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), kar98);
		return kar98;
	}
	public Item newSevenSix51()
	{
		Item sevenSix51 = new Item((char)98, Palette.red, Type.AMMO_762_51,
				"Seven Six Two Fifty One", 
				"NATO peace keeping round",
				25,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), sevenSix51);
		return sevenSix51;
	}
	public Item newGlock18C()
	{
		Item glock18C = new Item((char)98, Palette.red, Type.GUN,
				"Glock G18C", 
				"A fully automatic pistol by Glock",
				130,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), glock18C);
		return glock18C;
	}
	public Item newHkUMP()
	{
		Item hkUMP = new Item((char)98, Palette.red, Type.GUN,
				"HK UMP", 
				"Another HK fan favorite. Snug. Accurate. Full auto.",
				220,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), hkUMP);
		return hkUMP;
	}
	public Item newDvlSniper()
	{
		Item dvlSniper = new Item((char)98, Palette.red, Type.GUN,
				"DVL Saboteur", 
				"Sleek and silent bolt action rifle",
				450,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), dvlSniper);
		return dvlSniper;
	}
	public Item newMkMutant()
	{
		Item mkMutant = new Item((char)98, Palette.red, Type.GUN,
				"Mk47 Mutant", 
				"CMMGs modern AK.",
				250,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), mkMutant);
		return mkMutant;
	}
	public Item newAug()
	{
		Item aug = new Item((char)98, Palette.red, Type.GUN,
				"Steyr AUG", 
				"A gun that looks like it belongs in a 60s sci fi film",
				235,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), aug);
		return aug;
	}
	public Item newKelTecKSG()
	{
		Item kelTecKSG = new Item((char)98, Palette.red, Type.GUN,
				"KEL TEC KSG", 
				"A fun full auto all American bull pup rifle",
				190,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), kelTecKSG);
		return kelTecKSG;
	}
	public Item newTwelveGauge()
	{
		Item twelveGauge = new Item((char)98, Palette.red, Type.AMMO_12_GAUGE,
				"Buckshot", 
				"Good for duck hunting and unarmored folks.",
				20,
				Item.Rarity.COMMON);
		if(world != null)
			world.spawnInside(r.nextInt(5), twelveGauge);
		return twelveGauge;
	}
	public Item newWeaponCharm()
	{
		Item weaponCharm = new Item((char)5, Palette.purple, Type.CHARM,
				"Weapon Charm", 
				"A charm to garnish your weapon. A form of payment. A relic of the old world.",
				1,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), weaponCharm);
		return weaponCharm;
	}
	public Item newArmorPlate3()
	{
		Item armorPlate3 = new Item((char)35, Palette.red, Type.PLATE_3,
				"Armor Plate III", 
				"An armor plate made to stop bullets",
				200,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), armorPlate3);
		return armorPlate3;
	}
	public Item newMaxHealth()
	{
		Item maxHealth = new Item((char)43, Palette.purple, Type.REGEN,
				"Vitality Syringe", 
				"Drugs that heal you over time",
				999,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), maxHealth);
		return maxHealth;
	}
	public Item newMonstrumSight()
	{
		Item monstrumSight = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Red Dot Sight", 
				"Perfect sized scope for that 2X edge",
				7,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), monstrumSight);
		return monstrumSight;
	}
	public Item newLpvoVortex()
	{
		Item lpvoVortex = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Vortex Razor LPVO", 
				"A nice long range variable optic with 1x-6x magnification",
				10,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), lpvoVortex);
		return lpvoVortex;
	}
	public Item newLpvoBurris()
	{
		Item lpvoBurris = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Burris Fulfield", 
				"A nice long range variable optic with 1x-4x magnification",
				9,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), lpvoBurris);
		return lpvoBurris;
	}
	public Item newLpvoMarch()
	{
		Item lpvoMarch = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"March Tactical", 
				"A long range variable optic with 6x-12x magnification",
				12,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), lpvoMarch);
		return lpvoMarch;
	}
	public Item newSuppressor556()
	{
		Item suppressor556 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"5.56mm Silencer", 
				"Made to silence NATO rounds",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), suppressor556);
		return suppressor556;
	}
	public Item newSuppressor762()
	{
		Item suppressor762 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"7.62mm Silencer", 
				"Made to silence soviet rounds",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), suppressor762);
		return suppressor762;
	}
	public Item newSuppressor76251()
	{
		Item suppressor76251 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"7.62x51 Silencer", 
				"Made to silence big rounds",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), suppressor76251);
		return suppressor76251;
	}
	public Item newSuppressor45()
	{
		Item suppressor45 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				".45 Silencer", 
				"Made to silence small rounds",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), suppressor45);
		return suppressor45;
	}
	public Item newSuppressor57()
	{
		Item suppressor57 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"5.7mm Silencer", 
				"Made to silence medium rounds",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), suppressor57);
		return suppressor57;
	}
	public Item newForegripHorizontal()
	{
		Item foregripHorizontal = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Horizontal Fore Grip", 
				"Helps control horizontal recoil",
				10,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), foregripHorizontal);
		return foregripHorizontal;
	}
	public Item newForegripVertical()
	{
		Item foregripVertical = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Vertical Fore Grip", 
				"Helps control vertical recoil",
				10,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), foregripVertical);
		return foregripVertical;
	}
	public Item newForegripSway()
	{
		Item foregripSway = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Tactical Fore Grip", 
				"Helps steady the hands",
				15,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), foregripSway);
		return foregripSway;
	}
	public Item newCompensator762()
	{
		Item Compensator762 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"7.62mm Muzzle Compensator", 
				"Helps with accuracy and makes your barrel look dangerous",
				3,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), Compensator762);
		return Compensator762;
	}
	public Item newCompensator556()
	{
		Item Compensator556 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"5.56mm Muzzle Compensator", 
				"Helps with accuracy and makes your barrel look dangerous",
				3,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), Compensator556);
		return Compensator556;
	}
	public Item newExtendedMags556()
	{
		Item ExtendedMags556 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"5.56mm Extended Mags", 
				"Gives you those extra bullets that you always need",
				12,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), ExtendedMags556);
		return ExtendedMags556;
	}
	public Item newExtendedMags762()
	{
		Item ExtendedMags762 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"7.62mm Extended Mags", 
				"Gives you those extra bullets that you always need",
				14,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), ExtendedMags762);
		return ExtendedMags762;
	}
	public Item newExtendedMags76251()
	{
		Item ExtendedMags76251 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"7.62x51 Extended Mags", 
				"Gives you those extra bullets that you always need",
				9,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), ExtendedMags76251);
		return ExtendedMags76251;
	}
	public Item newCompensator76251()
	{
		Item compensator76251 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"7.62x51 Compensator", 
				"Helps keep your gun on target",
				3,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), compensator76251);
		return compensator76251;
	}
	public Item newShotgunCompensator()
	{
		Item shotgunCompensator = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Shotgun Compensator", 
				"Steadies your trigger finger",
				7,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), shotgunCompensator);
		return shotgunCompensator;
	}
	public Item newShotgunChoke()
	{
		Item shotgunChoke = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"Shotgun Choke", 
				"Tightens the spread of your buckshot",
				10,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), shotgunChoke);
		return shotgunChoke;
	}
	public Item newMags45()
	{
		Item mags45 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"45mm Extended Mags", 
				"Gives your pistol that extra longetivity",
				6,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), mags45);
		return mags45;
	}
	public Item newCompensator45()
	{
		Item compensator45 = new Item((char)145, Palette.red, Type.ATTACHMENT,
				"45mm Compensator", 
				"Gives your barrel that extra length",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), compensator45);
		return compensator45;
	}
	public Item newPlateCarrier2()
	{
		Item plateCarrier2 = new Item((char)69, Palette.brown, Type.PLATE_CARRIER,
				"Plate Carrier Infantry", 
				"Good for holding armor plates and has some extra storage",
				2,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), plateCarrier2);
		return plateCarrier2;
	}
	public Item newPlateCarrier3()
	{
		Item plateCarrier3 = new Item((char)69, Palette.brown, Type.PLATE_CARRIER,
				"Plate Carrier Spec", 
				"Good for holding armor plates and has some extra storage",
				3,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), plateCarrier3);
		return plateCarrier3;
	}
	public Item newPlateCarrier4()
	{
		Item plateCarrier4 = new Item((char)69, Palette.brown, Type.PLATE_CARRIER,
				"Plate Carrier Operations", 
				"Good for holding armor plates and has some extra storage",
				4,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), plateCarrier4);
		return plateCarrier4;
	}
	public Item newPlateCarrier5()
	{
		Item plateCarrier5 = new Item((char)69, Palette.brown, Type.PLATE_CARRIER,
				"Plate Carrier Heavy", 
				"Good for holding armor plates and has some extra storage",
				5,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), plateCarrier5);
		return plateCarrier5;
	}
	public Item newPlateCarrier6()
	{
		Item plateCarrier6 = new Item((char)69, Palette.brown, Type.PLATE_CARRIER,
				"Plate Carrier Bomb Squad", 
				"Good for holding armor plates and has some extra storage",
				6,
				Item.Rarity.RARE);
		if(world != null)
			world.spawnInside(r.nextInt(5), plateCarrier6);
		return plateCarrier6;
	}
}