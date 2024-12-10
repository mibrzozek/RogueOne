package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Taunts implements Serializable
{
    private List<String> basicTaunts;
    private List<String> funnyTaunts;
    private List<String> advancedTaunts;
    private List<String> hilariousTaunts;

    private Random r;


    public Taunts()
    {
        initTaunts();
        r = new Random();
    }

    private void initTaunts()
    {
        basicTaunts = new ArrayList<>();

        basicTaunts.add("You smell like a bag of farts!");
        basicTaunts.add("Your mom smells like a bag of farts");
        basicTaunts.add("taunt 3");
        basicTaunts.add("taunt 4");
        basicTaunts.add("taunt 5");
        basicTaunts.add("taunt 6");
        basicTaunts.add("taunt 7");
        basicTaunts.add("taunt 8");
        basicTaunts.add("taunt 9");

        funnyTaunts = new ArrayList<>();

        funnyTaunts.add("You smell like a bag of farts!");
        funnyTaunts.add("Your mom smells like a bag of farts");
        funnyTaunts.add("taunt 3");
        funnyTaunts.add("taunt 4");
        funnyTaunts.add("taunt 5");
        funnyTaunts.add("taunt 6");
        funnyTaunts.add("taunt 7");
        funnyTaunts.add("taunt 8");
        funnyTaunts.add("taunt 9");

        advancedTaunts = new ArrayList<>();

        advancedTaunts.add("Your mom is so fat that when she jumps for joy she gets stuck!");
        advancedTaunts.add("You're mom smells like a bag of farts");
        advancedTaunts.add("taunt 3");
        advancedTaunts.add("taunt 4");
        advancedTaunts.add("taunt 5");
        advancedTaunts.add("taunt 6");
        advancedTaunts.add("taunt 7");
        advancedTaunts.add("taunt 8");
        advancedTaunts.add("taunt 9");

        hilariousTaunts = new ArrayList<>();

        hilariousTaunts.add("You sir, are so useless, even a slave wouldn't find you helpful!");
        hilariousTaunts.add("You are more troublesome then porcupine!");
        hilariousTaunts.add("taunt 3");
        hilariousTaunts.add("taunt 4");
        hilariousTaunts.add("taunt 5");
        hilariousTaunts.add("taunt 6");
        hilariousTaunts.add("taunt 7");
        hilariousTaunts.add("taunt 8");
        hilariousTaunts.add("taunt 9");
    }
    public String getBasicTaunt()
    {
        return basicTaunts.get(r.nextInt(basicTaunts.size()));
    }
    public String getFunnyTaunt()
    {
        return funnyTaunts.get(r.nextInt(funnyTaunts.size()));
    }
    public String getAdvancedTaunt()
    {
        return advancedTaunts.get(r.nextInt(advancedTaunts.size()));
    }
    public String getHilariousTaunt()
    {
        return hilariousTaunts.get(r.nextInt(hilariousTaunts.size()));
    }
}
