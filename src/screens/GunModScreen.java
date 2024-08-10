package screens;

import asciiPanel.AsciiPanel;
import entities.Entity;
import items.*;
import structures.RexReader;
import structures.TileEngine;
import wolrdbuilding.Palette;
import wolrdbuilding.TileSet;
import wolrdbuilding.World;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GunModScreen extends UIScreen
{
    private World w;
    private Entity player;
    private Weapon primaryWeapon;

    public GunModScreen(World w, int bw, int bh, int bx, int by, PlayScreen ps, JFrame main)
    {
        super(w.getPlayer(), ps, main);

        this.bw = bw;
        this.bh = bh;
        this.bx = bx;
        this.by = by;

        this.player = w.getPlayer();
        this.primaryWeapon = player.inventory().getPrimaryWeapon();

        List<String> options =  new ArrayList<>();

        options.add("Damage");
        options.add("Fire Rate");
        options.add("Magazine");
        options.add("Reload Speed");
        options.add("Range");

        setList((ArrayList<String>) options);
        setTopBottomBounds(by + 1, by + itemList.size());
        setScrollX(bx);
        setScrollY(by +1 );
        setCursor(true);
    }
    @Override
    public void render(AsciiPanel terminal)
    {
        terminal.write("Upgrade Weapon", bx+7, by, Palette.red);
        TileEngine.renderBox(terminal, bw, 3, bx, by + 7, TileSet.SIMPLE);
        terminal.write("Available Parts", bx+7, by+7, Palette.red);

        int x = bx + 1;
        int y = by + 1;

        for(String i : itemList)
            terminal.write(i, x, y++ );

        for(WeaponStats.WEAPON_STAT stat : primaryWeapon.getStats().getBuffMap().keySet())
        {
            terminal.write(stat.toString() + " " + primaryWeapon.getStats().getBuffMap().get(stat).size(), x, y++ + 5);
        }
        List<Item> gunParts = new ArrayList<>();
        gunParts = player.inventory().get(Type.GUN_PARTS);

        if(gunParts == null || gunParts.isEmpty())
        {
            TileEngine.renderWeaponStatPlate(terminal, bx+1, by+8, 29, "Gun Parts", "0", Palette.white, Palette.darkerGray);
        }
        else
        {
            TileEngine.renderWeaponStatPlate(terminal, bx+1, by+8, 29, "Gun Parts", Integer.toString(gunParts.size()), Palette.white, Palette.darkerGray);
        }
    }
    @Override
    public void select()
    {
        ItemFactory iF = new ItemFactory();
        boolean opened = false;
        Color c = Palette.paleWhite;
        List<Item> gunParts = player.inventory().get(Type.GUN_PARTS);

        if(gunParts == null || gunParts.isEmpty())
        {
            return;
        }

        if(itemList.get(index).equals("Damage"))
        {
            Integer nextUpgradeValue = RexReader.retrieveStatsForUpgradeLevel(primaryWeapon.name(), primaryWeapon.getStats().getBuffMap().get(WeaponStats.WEAPON_STAT.DAMAGE).size() + 1, WeaponStats.WEAPON_STAT.DAMAGE);
            if(nextUpgradeValue == 0) // if the next upgrade value is zero, means there are no more upgrades
                return;

            this.primaryWeapon.getStats().buffStat(gunParts.remove(0), WeaponStats.WEAPON_STAT.DAMAGE);
            this.primaryWeapon.getStats().modifyGunStatsForBuffMap();
        }
        else if(itemList.get(index).equals("Range"))
        {
            this.primaryWeapon.getStats().buffStat(gunParts.remove(0), WeaponStats.WEAPON_STAT.RANGE);
        }
        else if(itemList.get(index).equals("Fire Rate"))
        {
            this.primaryWeapon.getStats().buffStat(gunParts.remove(0), WeaponStats.WEAPON_STAT.BULLETS_PER_TURN);
        }
        else if(itemList.get(index).equals("Magazine"))
        {
            this.primaryWeapon.getStats().buffStat(gunParts.remove(0), WeaponStats.WEAPON_STAT.MAG_CAPACITY);
        }
        else if(itemList.get(index).equals("Reload Speed"))
        {
            this.primaryWeapon.getStats().buffStat(gunParts.remove(0), WeaponStats.WEAPON_STAT.RELOAD_SPEED);
        }
    }
}
