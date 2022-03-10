package entities;

import structures.NameGenerator;
import wolrdbuilding.Palette;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static wolrdbuilding.Palette.r;

public class Statistics implements Serializable
{
	private static final double FULL_VITALS = 1000;
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
	public void setTorso(double newVal){this.torso = checkForNegative(newVal);}
	public void setHead(double head){this.head = checkForNegative(head);;}
	public void setlHand(double lHand){this.lHand = checkForNegative(lHand);;}
	public void setrHand(double rHand){	this.rHand = checkForNegative(rHand);}
	public void setlLeg(double lLeg){this.lLeg = checkForNegative(lLeg);}
	public void setrLeg(double rLeg){this.rLeg = checkForNegative(rLeg);}
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
	public double getVitals(){	return head + torso + lHand + rHand + lLeg + rLeg;}
	public double getHead(){return head;}
	public double getTorso(){	return torso;}
	public double getlHand(){return lHand;}
	public double getrHand(){return rHand;}
	public double getlLeg(){return lLeg;}
	public double getrLeg(){return rLeg;}
	public double getStealth(){return stealth;}
	public ArrayList<Traits> getTraits(){return traits;}
	public ArrayList<Effect> getEffects()	{return effects;	}
	public ArrayList<String> getSkills()	{	return skills;	}
	public boolean isDead()					{	return dead;	}
	public boolean isBreathing()			{	return breathing;	}

	public void healAllVitals(int value)
	{
		int sl = 0;
		int hv = 0;

		if(lHand > 0)
			sl++;
		if(rHand > 0)
			sl++;
		if(rLeg > 0)
			sl++;
		if(lLeg > 0)
			sl++;
		if(head > 0)
			sl++;
		if(torso > 0)
			sl++;

		hv = value/sl;

		if(lHand > 0 && lHand + hv < 101)
			lHand += hv;
		else
			lHand = 100;
		if(rHand > 0 && rHand + hv < 101)
			rHand += hv;
		else
			rHand = 100;
		if(rLeg > 0 && rLeg + hv < 101)
			rLeg += hv;
		else
			rLeg = 100;
		if(lLeg > 0 && lLeg + hv < 101)
			lLeg += hv;
		else
			lLeg = 100;
		if(head > 0 && head + hv < 301)
			head += hv;
		else
			head = 300;
		if(torso > 0 && torso + hv < 301)
			torso += hv;
		else
			torso = 300;
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
}
