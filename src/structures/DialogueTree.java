package structures;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DialogueTree
{
    ArrayList<String> prompts;
    ArrayList<String> options;
    ArrayList<String> dialogue;

    public DialogueTree()
    {
        this.prompts = new ArrayList<>();
        this.options = new ArrayList<>();
    }


    public ArrayList<String> getPrompts()
    {
        return prompts;
    }
    public ArrayList<String> getOptions()
    {
        return options;
    }
    public void setPrompts(String s, String x, String y)
    {
        prompts.add(s);
        prompts.add(s);
        prompts.add(s);
    }
    public void setOptions(String r, String e, String p)
    {
        options.add(r);
        options.add(e);
        options.add(p);
    }

}
