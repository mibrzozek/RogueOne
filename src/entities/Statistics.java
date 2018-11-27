package entities;

import java.io.Serializable;
import java.util.ArrayList;

import allscreen.KeyInputScreen;

public class Statistics implements Serializable
{
	private String name;
	private String role;
	
	private int points;
	private static final int MAX_POINTS = 22;
	
	private int strength;
	private int dexterity;
	private int inteligence;
	private int charisma;
	
	private int hunger;
	private int thirst;
	private int burden;
	
	private int crypto;
	
	private double vitals;
	
	private double head;
	private double torso;
	
	private double lHand;
	private double rHand;
	
	private double lLeg;
	private double rLeg;
	
	private double stealth;
	
	private ArrayList<String> traits;	
	private ArrayList<String> effects;	
	private ArrayList<String> skills;
	
	public Statistics()
	{
		this.points = MAX_POINTS;
		
		this.head = 300;
		this.torso = 300;
		this.lHand = 100;
		this.rHand = 100;
		this.lLeg = 100;
		this.rLeg = 100;
		
		this.stealth = 0;
		
		this.vitals = head + torso + lHand + rHand + lLeg + rLeg;
	}
	
	public ArrayList displayStats()
	{
		ArrayList list = new ArrayList();
		
		list.add(name);
		list.add(role);
		list.add(strength);
		list.add(dexterity);
		list.add(inteligence);
		list.add(charisma);
		list.add(traits);
		list.add(effects);
		list.add(skills);
	
		return list;
	}
	
	public void dealDamage(int amount)
	{
		
	}
	
	public void setName(String name)	{	this.name = name;		}
	public void setRole(String role)	{	this.role = role;		}
	public void setPoints(int points)	{	this.points = points;	}
	public void setStrength(int strength)	{	this.strength = strength;	}
	public void setDexterity(int dexterity){	this.dexterity = dexterity;	}
	public void setInteligence(int inteligence){this.inteligence = inteligence;}
	public void setCharisma(int charisma){	this.charisma = charisma;}
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
	public void setTraits(ArrayList<String> traits){	this.traits = traits;}
	public void setEffects(ArrayList<String> effects)	{	this.effects = effects;	}
	public void setSkills(ArrayList<String> skills)		{	this.skills = skills;	}	
	
	
	public String getName()				{	return name;			}
	public String getRole()				{	return role;			}
	public int getPoints()				{	return points;			}
	public int getStrength()			{	return strength;		}
	public int getDexterity()			{	return dexterity;		}
	public int getInteligence(){return inteligence;}
	public int getCharisma(){return charisma;}
	public int getHunger(){return hunger;}
	public int getThirst()	{return thirst;}
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
	public ArrayList<String> getTraits(){return traits;}
	public ArrayList<String> getEffects()	{return effects;	}
	public ArrayList<String> getSkills()	{	return skills;	}
	
	public double checkForNegative(double newVal)
	{
		if(newVal >= 0)
			return newVal;
		else
			return 0.0;
	}
	// Allows to add attributes iteratively
	// in the character creation screen
	// Th index
	public void setAttribute(int index, String s)
	{	
		Integer parsed = 0;
		try {
			parsed = Integer.parseInt(s);
		}
		catch(Exception NumberFormartException)
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
	   				dexterity += parsed;
	   			}
	   			else if(parsed > 0)
	   			{
	   				points -= parsed;
	   				dexterity += parsed;
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
	   				inteligence += parsed;
	   			}
	   			else if(parsed > 0)
	   			{
	   				points -= parsed;
	   				inteligence += parsed;
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
	
}
