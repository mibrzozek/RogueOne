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

        TileEngine.renderBox(terminal, 31, 8, 0, y + 4, TileSet.SIMPLE, true);
        for(WeaponStats.WEAPON_STAT stat : primaryWeapon.getStats().getBuffMap().keySet())
        {
            //terminal.write(stat.toString() + " " + primaryWeapon.getStats().getBuffMap().get(stat).size(), x, y++ + 5);
            TileEngine.renderWeaponStatPlate(terminal, x, y + 5, 29,
                    stat.toString(),
                    Integer.toString(primaryWeapon.getStats().getBuffMap().get(stat).size()),
                    Palette.white, Palette.darkerGray);
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
    public void selectForStat(WeaponStats.WEAPON_STAT stat, Weapon primaryWeapon, List<Item> gunParts)
    {
        Integer nextUpgradeValue = RexReader.retrieveStatsForUpgradeLevel(primaryWeapon.name(), primaryWeapon.getStats().getBuffMap().get(stat).size() + 1, stat);
        if(nextUpgradeValue == 0) // if the next upgrade value is zero, means there are no more upgrades
            return;

        this.primaryWeapon.getStats().buffStat(gunParts.remove(0), stat);
        this.primaryWeapon.getStats().modifyGunStatsForBuffMap();
        // Consume gun part from equipped
        this.player.inventory().removeEquiped(new ItemFactory().newGunParts());

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
            selectForStat(WeaponStats.WEAPON_STAT.DAMAGE, primaryWeapon, gunParts);
        }
        else if(itemList.get(index).equals("Range"))
        {
            selectForStat(WeaponStats.WEAPON_STAT.RANGE, primaryWeapon, gunParts);
        }
        else if(itemList.get(index).equals("Fire Rate"))
        {
            selectForStat(WeaponStats.WEAPON_STAT.BULLETS_PER_TURN, primaryWeapon, gunParts);
        }
        else if(itemList.get(index).equals("Magazine"))
        {
            selectForStat(WeaponStats.WEAPON_STAT.MAG_CAPACITY, primaryWeapon, gunParts);
        }
        else if(itemList.get(index).equals("Reload Speed"))
        {
            selectForStat(WeaponStats.WEAPON_STAT.RELOAD_SPEED, primaryWeapon, gunParts);
        }
    }
}
