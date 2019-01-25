package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Script
{
    public enum Type{TERMINAL, TRADER, DROID };

    private Map<Type, ArrayList<String>> scriptMap;
    private Map<Type, Integer> progressMap;
    private Map<Type, ArrayList<ArrayList>> responseMap;

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


    private void initResponses()
    {
        responseMap = new HashMap<Type, ArrayList<ArrayList>>();


        ArrayList<String> responses = new ArrayList<>();
        responses.add("I've got the plasma right here!");
        responses.add("Before i do anything, first i'm going to need some gosh darn answers!");
        responses.add("I'm gonna be fine with or without you!");

        ArrayList<ArrayList> listOfLists = new ArrayList<>();

        listOfLists.add((ArrayList) responses);
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
                " to keeping this facilities running, and all I need from you is to bring me some plasma. It is the fuel which lets you breathe" +
                " and lets me compute. Go now, the time is running out !");

        scriptMap.put(Type.TERMINAL, terminalD);

    }

}
