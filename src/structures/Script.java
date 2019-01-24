package structures;

import screens.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Script
{
    public enum Type{TERMINAL, TRADER, DROID };

    public Map<Type, ArrayList<String>> scriptMap;

    public Script()
    {
        initDialogues();
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
    public ArrayList<String> getDialogue(Type d)
    {
        return scriptMap.get(d);
    }
}
