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
    public double getRightHand()
    {
        return vitalsMap.get(Limbs.RIGHT_HAND).get(LIMB_HEALTH);
    }
    public double getLeftHand()
    {
        return vitalsMap.get(Limbs.LEFT_HAND).get(LIMB_HEALTH);
    }
    public double getRightLeg()
    {
        return vitalsMap.get(Limbs.RIGHT_LEG).get(LIMB_HEALTH);
    }
    public double getLeftLeg()
    {
        return vitalsMap.get(Limbs.LEFT_LEG).get(LIMB_HEALTH);
    }

}
