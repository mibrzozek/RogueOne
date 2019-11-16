package structures;

import wolrdbuilding.Palette;

import java.awt.*;
import java.io.Serializable;

import static wolrdbuilding.Palette.r;

public enum Theme implements Serializable
{
    MONO_BLUE(Palette.morePaleWhite, Palette.theNewBlue),
    MIDNIGHT_PURPLE(Palette.paperPink, Palette.theNewMagenta),

    WHITE_NOT(Palette.monoGrayBlue, Palette.morePaleWhite),
    GRAY_NOT(Palette.monoPurple, Palette.gray),
    PAPER_PINK(Palette.manaTealHarder, Palette.paperPink),

    CLASSIC(Palette.morePaleWhite, Palette.darkestGray),
    ALIEN(Palette.green, Palette.darkestGray),

    ALIEN_2(Palette.green, Palette.darkerGray),
    PASTEL(Palette.pastelGreen, Palette.theNewDarkerBlue),
    MURDER(Palette.lightRed, Palette.morePaleWhite);

    Color f;
    Color b;

   Theme(Color f, Color b)
    {
        this.f = f;
        this.b =b;
    };

    public Color getFore() {
        return f;
    }
    public Color getBack() {
        return b;
    }

    public static Theme getRandom()
    {
        Theme[] ts = Theme.values();

        return ts[r.nextInt(ts.length-1)];
    }
}
