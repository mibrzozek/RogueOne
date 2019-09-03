package entities;

import wolrdbuilding.Palette;

import java.awt.*;

public class Effect
{
    public enum Effects
    {
        BURN_1(10),
        BROKEN_ARM(10),
        SATITATED(100);

        private int value;

        Effects(int value)
        {
            this.value = value;
        }
    }

    private Effects e;
    private String tag;
    private Color c;

    public Effect(Effects e, String tag, Color c)
    {
        this.e = e;
        this.tag = tag;
        this.c = c;
    }
    public String getEffectTag()
    {
        return tag;
    }
    public Color getGolor()
    {
        return c;
    }
    public Effects getEffects()
    {
        return e;
    }
}
