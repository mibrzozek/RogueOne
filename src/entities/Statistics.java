package entities;

import structures.NameGenerator;
import wolrdbuilding.Palette;

import java.io.Serializable;
import java.util.*;

import static wolrdbuilding.Palette.r;

public class Statistics implements Serializable
{
	private static final double FULL_VITALS = 1000;

	private static final double HEAD_MAX = 300;
	private static final double TORSO_MAX = 300;

	private static final double ARMS_MAX = 200;
	private static final double LEFT_ARMS_MAX = 100;
	private static final double RIGHT_ARMS_MAX = 100;

	private static final double LEGS_MAX = 200;
	private static final double RIGHT_LEGS_MAX = 100;
	private static final double LEFT_LEGS_MAX = 100;
	private String name;
	private String role;
	
	private int points;
	private static final int MAX_POINTS = 22;

	private int height;
	private int weight;
	private int hunger;
	private int thirst;
	private int focus;
	// Character determined attributes
	private int strength;
	private int agility;
	private int intelligence;
	private int charisma;
	// Randomized attributes
	private int burden;
	private double stealth;


	private int crypto;
	private double vitals;
	private double head;
	private double torso;
	private double lHand;
	private double rHand;
	private double lLeg;
	private double rLeg;

	private boolean dead = false;
	private boolean breathing = true;
	private boolean alert = false;

	private ArrayList<Effect> effects;

	private ArrayList<Traits> traits;
	private ArrayList<String> skills;
	private Map<String, Integer> attributeMap;
	private Map<Limb, List<Double>> vitalsMap;
	public enum Limb
	{
		LEFT_LEG, RIGHT_LEG, LEFT_ARM, RIGHT_ARM, TORSO, HEAD;
	}
	public Statistics()
	{
		this.points = MAX_POINTS;

		this.name = "Maynard Buckson";
		this.role = "";
		
		this.head = 300;
		this.torso = 300;
		this.lHand = 100;
		this.rHand = 100;
		this.lLeg = 100;
		this.rLeg = 100;

		this.vitalsMap = new HashMap<>();
		this.vitalsMap.put(Limb.HEAD, Arrays.asList(HEAD_MAX, HEAD_MAX));
		this.vitalsMap.put(Limb.TORSO, Arrays.asList(TORSO_MAX, TORSO_MAX));
		this.vitalsMap.put(Limb.LEFT_ARM, Arrays.asList(LEFT_ARMS_MAX, LEFT_ARMS_MAX));
		this.vitalsMap.put(Limb.RIGHT_ARM, Arrays.asList(RIGHT_ARMS_MAX, RIGHT_ARMS_MAX));
		this.vitalsMap.put(Limb.RIGHT_LEG, Arrays.asList(RIGHT_LEGS_MAX, RIGHT_LEGS_MAX));
		this.vitalsMap.put(Limb.LEFT_LEG, Arrays.asList(LEFT_LEGS_MAX, LEFT_LEGS_MAX));

		this.height = r.nextInt(19) + 62;
		if(height <68)
		{
			weight = 120 + r.nextInt(45);
		}
		else if (height <74)
		{
			weight = 165 + r.nextInt(45);
		}
		else
		{
			weight = 210 + r.nextInt(65);
		}
		rollCharacter(null);
		this.effects = new ArrayList<>();
		effects.add(new Effect(Effect.Effects.SATITATED, "Full", Palette.green));
		this.vitals = head + torso + lHand + rHand + lLeg + rLeg;
	}
	public Map getVitalsMap()
	{
		this.vitalsMap.put(Limb.HEAD, Arrays.asList(head, HEAD_MAX));
		this.vitalsMap.put(Limb.TORSO, Arrays.asList(torso, TORSO_MAX));
		this.vitalsMap.put(Limb.LEFT_ARM, Arrays.asList(lHand, LEFT_ARMS_MAX));
		this.vitalsMap.put(Limb.RIGHT_ARM, Arrays.asList(rHand, RIGHT_ARMS_MAX));
		this.vitalsMap.put(Limb.RIGHT_LEG, Arrays.asList(rLeg, RIGHT_LEGS_MAX));
		this.vitalsMap.put(Limb.LEFT_LEG, Arrays.asList(lLeg, LEFT_LEGS_MAX));

		return vitalsMap;
	}
	public void rollCharacter(NameGenerator nameGen)
	{
		if(nameGen != null)
			this.name = nameGen.getRandomName();
		distributeAttributePoints();
		rollTraits();
		this.stealth = 5 + r.nextInt(10);
		this.burden = 3 + r.nextInt(5);
	}
	private void rollTraits()
	{
		traits = new ArrayList<>();
		int traitCount = r.nextInt(5) -1;
		for(int i = 0; i < traitCount; i++)
		{
			Traits t = Traits.getRandomTrait();

			if(!traits.contains(t))
				traits.add(t);
		}
	}
	private void distributeAttributePoints()
	{
		Random r = new Random();

		for(int i = 0; i < MAX_POINTS; i++)  // for every point to distribute
		{
			int attribute = r.nextInt(4); // choose random attribute defined by index

			if(attribute == 0) // strength
			{
					strength += 1;
			}
			else if(attribute == 1) // agility
			{
					agility += 1;
			}
			else if(attribute == 2) // intelligence
			{
					intelligence += 1;
			}
			else if(attribute == 3) // charisma
			{
					charisma += 1;
			}
		}
	}

	public ArrayList displayStats()
	{
		ArrayList list = new ArrayList();
		
		list.add(name);
		list.add(role);
		list.add(strength);
		list.add(agility);
		list.add(intelligence);
		list.add(charisma);
		list.add(stealth);
		list.add(burden);
		list.add(traits);
		list.add(effects);
		list.add(skills);
		list.add(height);
		list.add(weight);
	
		return list;
	}
	
	public void dealDamage(int amount)
	{
		if(head > 0)
			head -= amount;
		else if(torso > 0)
			torso -= amount;
		else if(lHand > 0)
			lHand -= amount;
		else if(rHand > 0)
			rHand -= amount;
		else if(rLeg > 0)
			rLeg -= amount;
		else if(lLeg > 0)
			lLeg -= amount;
	}
	
	public void setName(String name)	{	this.name = name;		}
	public void setRole(String role)	{	this.role = role;		}
	public void setPoints(int points)	{	this.points = points;	}
	public void setStrength(int strength)	{	this.strength = strength;	}
	public void setAgility(int agility){	this.agility = agility;	}
	public void setIntelligence(int intelligence){this.intelligence = intelligence;}
	public void setCharisma(int charisma){	this.charisma = charisma;}
	public void setFocus(int focus){	this.focus = focus;}
	public void setHeight(int in)	{	this.height = in;}
	public void setWeight(int lbs)	{	this.weight = lbs;}
	public void setHunger(int hunger){this.hunger = hunger;}
	public void setThirst(int thirst){this.thirst = thirst;}
	public void setBurden(int burden){	this.burden = burden;}
	public void setCrypto(int crypto){this.crypto = crypto;}
	public void setVitals(double vitals){this.vitals = checkForNegative(vitals);}
	public void setTorso(double newVal)
	{
		this.torso = newVal;
		List<Double> limb = vitalsMap.get(Limb.TORSO);
		limb.set(0, checkForNegative(newVal));

		this.vitalsMap.put(Limb.TORSO, limb);
	}
	public void setHead(double head)
	{
		this.head = head;
		List<Double> limb = vitalsMap.get(Limb.HEAD);
		limb.set(0, checkForNegative(head));

		this.vitalsMap.put(Limb.HEAD, limb);
	}
	public void setlHand(double lHand)
	{
		this.lHand = lHand;
		List<Double> limb = vitalsMap.get(Limb.LEFT_ARM);
		limb.set(0, checkForNegative(lHand));

		this.vitalsMap.put(Limb.LEFT_ARM, limb);
	}
	public void setrHand(double rHand)
	{
		this.rHand = rHand;
		List<Double> limb = vitalsMap.get(Limb.RIGHT_ARM);
		limb.set(0, checkForNegative(rHand));

		this.vitalsMap.put(Limb.RIGHT_ARM, limb);
	}
	public void setlLeg(double lLeg)
	{
		this.lLeg = lLeg;
		List<Double> limb = vitalsMap.get(Limb.LEFT_LEG);
		limb.set(0, checkForNegative(lLeg));

		this.vitalsMap.put(Limb.LEFT_LEG, limb);
	}
	public void setrLeg(double rLeg)
	{
		this.rLeg = rLeg;
		List<Double> limb = vitalsMap.get(Limb.RIGHT_LEG);
		limb.set(0, checkForNegative(rLeg));

		this.vitalsMap.put(Limb.RIGHT_LEG, limb);
	}
	public void setStealth(double stealth){this.stealth = stealth;}
	public void setTraits(ArrayList<Traits> traits){	this.traits = traits;}
	public void setEffects(ArrayList<Effect> effects)	{	this.effects = effects;	}
	public void setSkills(ArrayList<String> skills)		{	this.skills = skills;	}
	public void setDead(boolean dead)					{ 	this.dead = dead;}
	public void setBreathing(boolean b){ this.breathing = b;}

	public String getName()				{	return name;			}
	public String getRole()				{	return role;			}
	public int getPoints()				{	return points;			}
	public int getStrength()			{	return strength;		}
	public int getAgility()			{	return agility;		}
	public int getIntelligence(){return intelligence;}
	public int getCharisma(){return charisma;}
	public int getHunger(){return hunger;}
	public int getThirst()	{return thirst;}
	public int getFocu(){return focus;}
	public int getHeight() { return height; }
	public int getWeight() { return weight; }
	public int getBurden(){return burden;}
	public int getCrypto(){return crypto;}
	public double getVitals(){	return vitalsMap.get(Limb.HEAD ).get(0)
			+ vitalsMap.get(Limb.TORSO ).get(0)
			+ vitalsMap.get(Limb.LEFT_ARM ).get(0)
			+ vitalsMap.get(Limb.RIGHT_ARM ).get(0)
			+ vitalsMap.get(Limb.LEFT_LEG ).get(0)
			+ vitalsMap.get(Limb.RIGHT_LEG ).get(0)
			;}
	public double getHead(){return vitalsMap.get(Limb.HEAD).get(0);}
	public double getTorso(){	return vitalsMap.get(Limb.TORSO).get(0);}
	public double getlHand(){return vitalsMap.get(Limb.LEFT_ARM).get(0);}
	public double getrHand(){return vitalsMap.get(Limb.RIGHT_ARM).get(0);}
	public double getlLeg(){return vitalsMap.get(Limb.LEFT_LEG).get(0);}
	public double getrLeg(){return vitalsMap.get(Limb.RIGHT_LEG).get(0);}
	public double getStealth(){return stealth;}
	public ArrayList<Traits> getTraits(){return traits;}
	public ArrayList<Effect> getEffects()	{return effects;	}
	public ArrayList<String> getSkills()	{	return skills;	}
	public boolean isDead()					{	return dead;	}
	public boolean isBreathing()			{	return breathing;	}

	public void healAllVitals(int value)
	{
		System.out.println("Vitals before : " + getVitals() + " Healing value : " + value);

		double hv = 0;

		List<Limb> toHeal = new ArrayList<>();

		if(lHand < LEFT_ARMS_MAX) // see which limbs need healing
			toHeal.add(Limb.LEFT_ARM);
		if(rHand < RIGHT_ARMS_MAX)
			toHeal.add(Limb.RIGHT_ARM);
		if(rLeg < RIGHT_LEGS_MAX)
			toHeal.add(Limb.RIGHT_LEG);
		if(lLeg < LEFT_LEGS_MAX)
			toHeal.add(Limb.LEFT_LEG);
		if(head < HEAD_MAX)
			toHeal.add(Limb.HEAD);
		if(torso < TORSO_MAX)
			toHeal.add(Limb.TORSO);

		hv = value/toHeal.size(); // determine equal healing value per limb
		Double leftOverHealing = 0.0;
		for(Limb l : toHeal) // healLimbs
		{
			leftOverHealing = healLimb(l, hv + leftOverHealing); // healLimb returns unused healing points
		}

		System.out.println("Vitals after : " + getVitals() + " Healing value per limb : " + hv);
	}
	public Double healLimb(Limb l, double value)
	{
		Double leftoverHealingPower = 0.0;
		getVitalsMap(); // pulls correct values in map
		List<Double> limb = vitalsMap.get(l);

		System.out.println("\tHealing limb : " + l.toString() + " with value : " +  value + " BEFORE");
		System.out.println("\tvalue in map " + limb.get(0));

		if(limb.get(0) + value > limb.get(1)) // if adding the healing value to the current value of the injured limb goes over the MAX_LIMB value
		{
			leftoverHealingPower = (limb.get(0) + value) - limb.get(1); // add the healing to current and subtract from max to get leftover
			limb.set(0, value - leftoverHealingPower);
		}
		else
		{
			limb.set(0, limb.get(0) + value);
		}

		System.out.println("\tHealing limb : " + l.toString() + " with value : " +  value + " Leftover : " + leftoverHealingPower);
		System.out.println("\tvalue in map " + limb.get(0));
		vitalsMap.put(l, limb); // set the updated value of the limb
		System.out.println("\t\tChecking vitals map again " + vitalsMap.get(l).get(0));

		if(limb.equals(Limb.TORSO))
			torso = limb.get(0);
		else if(limb.equals(Limb.HEAD))
			head = limb.get(0);
		else if(limb.equals(Limb.LEFT_ARM))
			lHand = limb.get(0);
		else if(limb.equals(Limb.RIGHT_ARM))
			rHand = limb.get(0);
		else if(limb.equals(Limb.LEFT_LEG))
			lLeg = limb.get(0);
		else if(limb.equals(Limb.RIGHT_LEG))
			rLeg = limb.get(0);

		return leftoverHealingPower;
	}
	public void removeEffect(Effect e)
	{
		if(effects.contains(e))
		{
			effects.remove(e);
		}
	}
	public void addEffect(Effect e)
	{
		if(!effects.contains(e))
		{
			effects.add(e);
			//System.out.println(e.getEffectTag());
		}
		else
		{
			for(int i = 0; i < effects.size(); i++)
			{
				if(effects.get(i).getEffectTag().equals(e.getEffectTag())
						&& effects.get(i).getEffectLength() > 0)
				{
					effects.get(i).modifyLength(e.getEffects().getValue());
				}
			}
		}
	}
	public double checkForNegative(double newVal)
	{
		if(newVal >= 0)
			return newVal;
		else
			return 0.0;
	}
	// Allows to add attributes iteratively
	// in the character creation screen
	// Th equipIndex
	public void setAttribute(int index, String s)
	{	
		Integer parsed = 0;
		try {
			parsed = Integer.parseInt(s);
		}
		catch(Exception NumberFormatException)
		{
			
		}
		
		if(index == 0)
	   	{
	   		this.name = s;
	    }
	   	else if(index == 1)
	    {
	   		this.role = s;
	   	}
	   	else if(index == 2)
	   	{
	   		if(parsed <= MAX_POINTS				// Do i have enough to spend?
	   				&& points - parsed >= 0)	// Can i afford it?
	   		{
	   			if(parsed < 0) // Subtracting points
	   			{
	   				points -= parsed;
	   				strength += parsed;
	   			}
	   			else if(parsed > 0) // Adding points
	   			{
	   				points -= parsed;
	   				strength += parsed;
	   			}
	   		}
	    }
	    else if(index == 3)
	    {
	   		if(parsed <= MAX_POINTS				
	   				&& points - parsed >= 0)	
	   		{
	   			if(parsed < 0) 
	   			{
	   				points -= parsed;
	   				agility += parsed;
	   			}
	   			else if(parsed > 0)
	   			{
	   				points -= parsed;
	   				agility += parsed;
	   			}
	   		}
	   	}
	    else if(index == 4)
	    {
	   		if(parsed <= MAX_POINTS				
	   				&& points - parsed >= 0)	
	   		{
	   			if(parsed < 0) 
	   			{
	   				points -= parsed;
	   				intelligence += parsed;
	   			}
	   			else if(parsed > 0)
	   			{
	   				points -= parsed;
	   				intelligence += parsed;
	   			}
	   		}
	    }
	    else if(index == 5)
	    {
	   		if(parsed <= MAX_POINTS				
	   				&& points - parsed >= 0)	
	   		{
	   			if(parsed < 0) 
	   			{
	   				points -= parsed;
	   				charisma += parsed;
	   			}
	   			else if(parsed > 0)
	   			{
	   				points -= parsed;
	   				charisma += parsed;
	   			}
	   		}
	    }
	    else if(index == 6)
	    {
	    	
	    }
	    else if(index == 7)
	    {
	    	
	    }
	}
	/*
		PROCESSING EFFECTS

		Various checks to see if an effect is still active
		Cancels and starts new effects depending on conditions

	 */
	public void processEffects()
	{
		//System.out.println(getVitals() + " health\n");
		/*

		 */
		ArrayList<Effect> l = getEffects();
		List<Effect> indexToRemove = new ArrayList();

		for(Effect e : l) // update and queue done effects
		{
			e.update();

			if(e.getEffectLength() == 0) // zero is the condition in which effects end
			{

				if(e.getEffectTag().equals("Suffocating"))
				{
					//System.out.println(e.getEffectLength() + "  tis is the effect length");
					setDead(true);
				}
				if(!dead) // saves the cause of death for lose screen
					indexToRemove.add(e);
			}

			if(breathing && e.getEffectTag().equals("Suffocating"))
			{
				indexToRemove.add(e);
				setAlert(false);
			}
		}
		if(!indexToRemove.isEmpty()) // remove done effects
		{
			for(Effect es : indexToRemove)
			{
				getEffects().remove(es);
			}
		}
	}
	public Effect getMostDangerousEffect()
	{
		Effect e = null;

		for(Effect es : effects)
		{
			if(es.getGolor().equals(Palette.red)) // red effects are dangerous ones
			{
				if(e == null) // make the first red effect we look at the most dangerous
					e = es;
				else
				{
					if(e.getTotalLength() > es.getTotalLength()) // compare next red effect length to current red effect length
					{
						e = es;
					}
				}
			}
		}

		return e;
	}
	public boolean getAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public void processVitals()
	{
		if(getVitals() < 1)
		{
			setDead(true);
			//System.out.println(getVitals() + " setting DEAD\n");
		}

		if(getlHand() + getrHand() < 0)
		{
			addEffect(new Effect(Effect.Effects.DESTROYED_HANDS, "Broken Arm", Palette.red));
		}
	}

    public void fullHeal()
	{
		this.lLeg = 100;
		this.rLeg = 100;
		this.lHand = 100;
		this.rHand = 100;
		this. torso = 300;
		this.head = 300;
    }

	public boolean hasEffect(Effect.Effects effect)
	{
		boolean truth = false;

		for(Effect e : effects)
		{
			if(e.getEffects().equals(effect))
				truth = true;
		}

		return truth;
	}

	public int getMaxPoints()
	{
		return MAX_POINTS;
	}

	public double getFullVitals()
	{
		return FULL_VITALS;
	}

	public double getHeadMax() {
		return HEAD_MAX;
	}
	public double getTorsoMax() {
		return TORSO_MAX;
	}
	public double getArmsMax() {
		return ARMS_MAX;
	}
	public double getLegsMax() {
		return LEGS_MAX;
	}
	public double getArms() {
		return getrHand() + getlHand();
	}
	public double getLegs() {
		return getlLeg() + getrLeg();
	}
}
