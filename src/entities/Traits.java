package entities;

import java.util.Random;

public enum Traits
{
    ALCOHOLIC, WOKE, DEAF, DEPRESSED, CHARMING,
    ANNOYING, SMART, GREEDY, LUCKY, UNLUCKY, FUNNY, STINKY,
    WITTY, LEADER, ATHLETE, DORKY, DEFIANT, JUNKIE, LIAR;

    Traits()
    {

    }

    public static Traits getRandomTrait()
    {
        return Traits.values()[new Random().nextInt(Traits.values().length)];
    }

}
