package entities;

import wolrdbuilding.Palette;

import java.awt.*;
import java.util.ArrayList;

public class Effect
{
    public int getTotalLength()
    {
        return totalLength;
    }

    public enum Effects
    {
        BURN_1(10),
        BROKEN_ARM(10),
        DIZZY(1),
        CONFUSED(1),
        VISION_LOSS(1),
        TRAUMA(10),
        WACKED_OUT(1),
        SUFFOCATION(-1000),
        SATITATED(100),
        RELAXED(69), DESTROYED_HANDS(180), MEGA_RELAXED(1500), STONED(2800), FRIENDLY(780), HAPPY(999);

        private int value;

        Effects(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    private Effects e;
    private String tag; // what gets displayed
    private Color c;
    private int totalLength;
    private int effectLength;

    public Effect(Effects e, String tag, Color c)
    {
        this.e = e;
        this.tag = tag;
        this.c = c;

        this.effectLength = e.getValue();
        this.totalLength = effectLength;
    }
    public void modifyLength(int length)
    {
        if(length > 0)
            totalLength += length;

        effectLength += length;
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
    public int getEffectLength()
    {
        return effectLength;
    }

    public void update()
    {
            if(effectLength > 0)
                effectLength--;
            else if(effectLength < 0)
            {
                effectLength++;
            }
    }
    @Override
    public boolean equals(Object o)
    {
        Effect ne = null;

        if(o instanceof Effect)
        {
            ne = (Effect)o;
        }
        else
            return false;

        if(this.tag.equals(ne.tag))
            return true;
        else
            return false;
    }
}
