package structures;

import entities.Entity;
import items.ItemFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Script implements Serializable
{
    public enum Type{TERMINAL, TRADER, DROID };

    private Map<Type, ArrayList<String>> scriptMap;
    private Map<Type, Integer> progressMap;
    private Map<Type, ArrayList<ArrayList>> responseMap;
    private ItemFactory iF = new ItemFactory();

    public Script()
    {
        initProgress();
        initDialogues();
        initResponses();
    }
    public Integer getScriptProgress(Type d)     { return progressMap.get(d);}
    public ArrayList<String> getDialogue(Type d)
    {
        return scriptMap.get(d);
    }
    public ArrayList<ArrayList> getResponses(Type d){ return responseMap.get(d);}
    public int getProgressLevel(Type d){
        return progressMap.get(d);
    }
    public int getNumLevels(Type d)
    {
        return scriptMap.get(d).size();
    }

    public void processDecision(Type d, Entity player, int decisionNum)
    {
        System.out.println(decisionNum + " decisionNum and progress for temrinal " + progressMap.get(d) );
        if(d.equals(Type.TERMINAL))
        {
            if(progressMap.get(d) == 0) // Choose Weapon
            {
                if(decisionNum == 0)
                    player.inventory().add(iF.newRifle());
                if(decisionNum == 1)
                    player.inventory().add(iF.newShotgun());
                if(decisionNum == 2)
                    player.inventory().add(iF.newPistol());

                incrementProgress(d);
                player.notify("A new weapon reatomizes in your bag. Who knows where that's been...");
            }
            else if(progressMap.get(d) == 1) // Choose Weapon
            {
                if(decisionNum == 2 && player.plasma() > 1000)
                {
                    player.modifyPlasma(-1000);

                    incrementProgress(d);
                    player.notify("Your plasma depletes!");
                }
                else if(decisionNum == 2 && player.plasma() < 1000)
                {
                    player.notify("You don't have enough!");
                }
                else
                {
                    player.notify("To compute this I need more plasma");
                }
            }
            else if(progressMap.get(d) == 2) // Choose Weapon
            {
                if(decisionNum == 0)
                {
                    player.notify("That's why you're getting dizzy. Don't forget to check your 02 levels or you might die from suffocating.");
                }
                else if(decisionNum == 1)
                {
                    player.notify("It's in that one place you haven't checked yet.");
                }
                else if(decisionNum == 2)
                {
                    player.notify("This needs a condition to be satisfied to be progressed.");
                    incrementProgress(d);
                }
            }
            else if(progressMap.get(d) == 3) // Choose Weapon
            {
                if(decisionNum == 0)
                {
                    player.notify("No the smell is from the failed plumbing system. My data is corrupted, but my intuition is telling me we're many floors below" +
                            " the surface of wherever we are, so it's all flowing down here you know. Who know what's actually going on up there.");
                }
                else if(decisionNum == 1)
                {
                    player.notify("The security systems are non operational currently so we'll have to check manually.");
                }
                else if(decisionNum == 2)
                {
                    player.notify("This needs a condition to be satisfied to be progressed.");
                    incrementProgress(d);
                }
            }
        }
        System.out.println(decisionNum + " decisionNum and progress for temrinal " + progressMap.get(d) );
    }

    public void incrementProgress(Type d)
    {
        progressMap.put(d, progressMap.get(d) + 1);
    }

    private void initResponses()
    {
        responseMap = new HashMap<Type, ArrayList<ArrayList>>();


        ArrayList<String> r0 = new ArrayList<>();
        r0.add(" Rifle   | Damage 60 | Accuracy 70 | Range 90");
        r0.add(" Shotgun | Damage 30 | Accuracy 50 | Range 25");
        r0.add(" Pistol  | Damage 25 | Accuracy 45 | Range 35");

        ArrayList<String> r1 = new ArrayList<>();
        r1.add(" Who am i?");
        r1.add(" Where are we?");
        r1.add(" Here's your plasma now i want some answers!");

        ArrayList<String> r2 = new ArrayList<>();
        r2.add(" Wow, that's fascinating ...");
        r2.add(" That sounds like bullshit ...");
        r2.add(" When are you going to get to the good part ...?");

        ArrayList<String> r3 = new ArrayList<>();
        r3.add("Is that why i'm getting dizzy?");
        r3.add("Where is it located?");
        r3.add("Fuck you and your mother!");

        ArrayList<String> r4 = new ArrayList<>();
        r4.add("Is that why it's so smelly in here?");
        r4.add("What you don't have a camera there or something?");
        r4.add("On my way my love!");

        ArrayList<String> last = new ArrayList<>();
        last.add(" ...");
        last.add(" ...");
        last.add(" ...");

        ArrayList<ArrayList> listOfLists = new ArrayList<>();

        listOfLists.add((ArrayList) r0);
        listOfLists.add((ArrayList) r1);
        listOfLists.add((ArrayList) r2);
        listOfLists.add((ArrayList) r3);
        listOfLists.add((ArrayList) r4);


        listOfLists.add((ArrayList) last);
        responseMap.put(Type.TERMINAL, listOfLists);
    }
    private void initProgress()
    {
        Type[] allTypes = Type.values();
        progressMap = new HashMap<>();
        for(int i = 0; i < allTypes.length; i++)
        {
            progressMap.put(allTypes[i], 0);
        }
    }
    private void initDialogues()
    {
        ArrayList<String> terminalD = new ArrayList<>();
        scriptMap = new HashMap<>();
        terminalD.add("Hello interloper. My name is Laura and I will be your guide. I'm sure you have" +
                " a lot of questions and the answers will come soon enough but first you must feed me plasma" +
                " or we both won't be able to make it. You see, i manage this underground sector, and I do all the heavy lifting when it comes" +
                " to keeping this facilities running, and all I need from you is to bring me some plasma. Pick a weapon and be back soon !");
        terminalD.add("Ahh that is a fine choice. I always boast about our coordinates. 'Sector 2347' is one of the newer and classier ones', i often say. " +
                " 'We were one of the last to be effected by the plague, and we're right next to the lifeline so our supplies won't be running " +
                "thin anytime soon', that's one of my programmed lines! Anyway the point is this place has gone to shit so be careful out there, this level is " +
                "riddled with plasma junkies!");
        terminalD.add("Wow, that sure filled me up! My systems are running now and it seems we're suffering a serious 02 leak somewhere. " +
                "Go find it and patch it up while I send some repair droids to check on the life support and auxiliary systems.");
        terminalD.add("Cutting it close there with the leak. The droids reported back with good news on the life support but our ventilation systems seems" +
                " to be failing at the moment. Go see if you can see anything visibly wrong with it.");
        terminalD.add("I did a thourough scan of this level and we don't have any thorium in the regolith. If we don't the fusion reactor won't  have a way to balance itself out" +
                " and we'll all be fried!");

        scriptMap.put(Type.TERMINAL, terminalD);

    }

}
