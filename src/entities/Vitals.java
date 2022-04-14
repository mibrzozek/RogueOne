package entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vitals
{
    public enum Limbs
    {
        LEFT_LEG, RIGHT_LEG, LEFT_HAND, RIGHT_HAND, TORSO, HEAD;
    }
    private static final int LIMB_HEALTH = 0; // array index where corresponding limb health is stored

    private static final double FULL_VITALS = 1000;

    private static final double HEAD_MAX = 300;
    private static final double TORSO_MAX = 300;

    private static final double ARMS_MAX = 200;
    private static final double LEFT_HAND_MAX = 100;
    private static final double RIGHT_HAND_MAX = 100;

    private static final double LEGS_MAX = 200;
    private static final double RIGHT_LEG_MAX = 100;
    private static final double LEFT_LEG_MAX = 100;

    private double vitals;

    private double head;
    private double torso;
    private double lHand;
    private double rHand;
    private double lLeg;
    private double rLeg;

    private Map<Limbs, List<Double>> vitalsMap;

    public Vitals()
    {
        this.vitals = FULL_VITALS;
        this.head = HEAD_MAX;
        this.torso = TORSO_MAX;
        this.lHand = LEFT_HAND_MAX;
        this.rHand = RIGHT_HAND_MAX;
        this.lLeg = LEFT_LEG_MAX;
        this.rLeg = RIGHT_LEG_MAX;

        this.vitalsMap = new HashMap<>();
        vitalsMap.put(Limbs.HEAD, Arrays.asList(head, HEAD_MAX));
        vitalsMap.put(Limbs.TORSO, Arrays.asList(torso, TORSO_MAX));

        vitalsMap.put(Limbs.LEFT_HAND, Arrays.asList(lHand, LEFT_HAND_MAX));
        vitalsMap.put(Limbs.RIGHT_HAND, Arrays.asList(rHand, RIGHT_HAND_MAX));

        vitalsMap.put(Limbs.LEFT_LEG, Arrays.asList(lLeg, LEFT_LEG_MAX));
        vitalsMap.put(Limbs.RIGHT_LEG, Arrays.asList(rLeg, RIGHT_LEG_MAX));
    }
    public double getVitals()
    {
        return getHead() + getTorso() + getRightHand() + getLeftHand() + getLeftLeg() + getRightLeg();
    }
    public double getHead()
    {
        return vitalsMap.get(Limbs.HEAD).get(LIMB_HEALTH);
    }
    public double getTorso()
    {
        return vitalsMap.get(Limbs.TORSO).get(LIMB_HEALTH);
    }
    public double getArms()
    {
        return getRightHand() + getLeftHand();
    }
    public double getRightHand()
    {
        return vitalsMap.get(Limbs.RIGHT_HAND).get(LIMB_HEALTH);
    }
    public double getLeftHand()
    {
        return vitalsMap.get(Limbs.LEFT_HAND).get(LIMB_HEALTH);
    }
    public double getLegs()
    {
        return getRightLeg() + getLeftLeg();
    }
    public double getRightLeg()
    {
        return vitalsMap.get(Limbs.RIGHT_LEG).get(LIMB_HEALTH);
    }
    public double getLeftLeg()
    {
        return vitalsMap.get(Limbs.LEFT_LEG).get(LIMB_HEALTH);
    }
    public double getFullVitals()
    {
        return FULL_VITALS;
    }
    public double getHeadMax()
    {
        return HEAD_MAX;
    }
    public double getTorsoMax()
    {
        return TORSO_MAX;
    }
    public double getArmsMax()
    {
        return ARMS_MAX;
    }
    public double getLeftHandMax()
    {
        return LEFT_HAND_MAX;
    }
    public double getRightHandMax()
    {
        return RIGHT_HAND_MAX;
    }
    public double getLegsMax()
    {
        return LEGS_MAX;
    }
    public double getRightLegMax()
    {
        return RIGHT_LEG_MAX;
    }
    public double getLeftLegMax()
    {
        return LEFT_LEG_MAX;
    }
    public void dealDamageRandomly(double damage)
    {

    }
    // fullHeal()
    {

    }
    /*
    	public void dealDamage(Double amount)
	{
		Limb[] limbs = Limb.values();
		Limb targetedLimb = limbs[r.nextInt(limbs.length)];
		double damage = amount;

		do
		{
			if(vitalsMap.get(targetedLimb).get(0) - damage < 0) // has leftover damage
			{
				damage = Math.abs(vitalsMap.get(targetedLimb).get(0) - damage);
				vitalsMap.get(targetedLimb).set(0, 0.0); // destroy limb - set to zero
				targetedLimb = limbs[r.nextInt(limbs.length)];
			}
			else
			{
				vitalsMap.get(targetedLimb).set(0, vitalsMap.get(targetedLimb).get(0) - damage);
				damage = 0;
			}
		} while(damage > 0); // deal out all damage even if limb is destroyed
	}
     */
    /*
    	public void healAllVitals(int value)
	{
		System.out.println("Vitals before : " + getVitals() + " Healing value : " + value);

		double hv = 0;
		List<Limb> toHeal = new ArrayList<>();

		if(vitalsMap.get(Limb.LEFT_ARM).get(0) < LEFT_ARMS_MAX) // see which limbs need healing
			toHeal.add(Limb.LEFT_ARM);
		if(vitalsMap.get(Limb.RIGHT_ARM).get(0) < RIGHT_ARMS_MAX)
			toHeal.add(Limb.RIGHT_ARM);
		if(vitalsMap.get(Limb.RIGHT_LEG).get(0) < RIGHT_LEGS_MAX)
			toHeal.add(Limb.RIGHT_LEG);
		if(vitalsMap.get(Limb.LEFT_LEG).get(0) < LEFT_LEGS_MAX)
			toHeal.add(Limb.LEFT_LEG);
		if(vitalsMap.get(Limb.HEAD).get(0) < HEAD_MAX)
			toHeal.add(Limb.HEAD);
		if(vitalsMap.get(Limb.TORSO).get(0) < TORSO_MAX)
			toHeal.add(Limb.TORSO);

		if(toHeal.size() < 1) // no limbs need healing
			return;

		hv = value/toHeal.size(); // determine equal healing value per limb
		Double leftOverHealing = 0.0;

		for(Limb l : toHeal) // healLimbs
		{
			System.out.println("Actively healing. Healing Value : " + hv + " leftOver : " + leftOverHealing);
			leftOverHealing = healLimb(l, hv + leftOverHealing); // healLimb returns unused healing points
		}
		System.out.println("Vitals after : " + getVitals() + " Healing value per limb : " + hv);
	}
     */
    /*
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
			limb.set(0, value + leftoverHealingPower);
		}
		else
		{
			limb.set(0, limb.get(0) + value);
		}

		System.out.println("\tHealing limb : " + l.toString() + " with value : " +  value + " Leftover : " + leftoverHealingPower);
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

     */
    /*
    	public void dealDamageToLimb(double damage, Limb head)
	{
		if(vitalsMap.get(head).get(0) + damage < 0) // has leftover damage
		{
			damage = Math.abs(vitalsMap.get(head).get(0) + damage); // saves leftover damage
			vitalsMap.get(head).set(0, 0.0); // destroys lib -set to zero
			dealDamage(damage); // randomly disperse rest of damage
		}
		else
		{
			vitalsMap.get(head).set(0, vitalsMap.get(head).get(0) + damage);
		}
	}
     */
}
