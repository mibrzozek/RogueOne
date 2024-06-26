package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponStats
{
    private int range;
    private int damage;
    private int reloadSpeed;
    private int bulletsPerTurn;
    private int magazineCapacity;
    private int bulletsInMagazine;
    private WeaponStats baseStats;
    private Weapon.Mode mode;

    private Map<WEAPON_STAT, List<Item>> buffMap;

    public enum WEAPON_STAT
    {
        RANGE, DAMAGE, RELOAD_SPEED, BULLETS_PER_TURN, MAG_CAPACITY, MODE
    }
    public WeaponStats(int range, int damage, int reloadSpeed, int bulletsPerTurn, int magazineCapacity, Weapon.Mode firingMode)
    {
        this.range = range;
        this.damage = damage;
        this.reloadSpeed = reloadSpeed;
        this.bulletsPerTurn = bulletsPerTurn;
        this.magazineCapacity = magazineCapacity;
        this.mode = firingMode;
        this.bulletsInMagazine = magazineCapacity;

        this.buffMap = new HashMap<>();
        for(WEAPON_STAT stat : WEAPON_STAT.values())
        {
            buffMap.put(stat, new ArrayList<>());
        }
    }
    public void buffStat(Item i, WEAPON_STAT stat)
    {
        List<Item> buffedList = buffMap.get(stat);
        buffedList.add(i);
        buffMap.put(stat, buffedList);
    }
    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getReloadSpeed() {
        return reloadSpeed;
    }
    public void setReloadSpeed(int reloadSpeed) {
        this.reloadSpeed = reloadSpeed;
    }
    public int getBulletsPerTurn() {
        return bulletsPerTurn;
    }
    public void setBulletsPerTurn(int bulletsPerTurn) {
        this.bulletsPerTurn = bulletsPerTurn;
    }
    public int getMagazineCapacity() {
        return magazineCapacity;
    }
    public void setMagazineCapacity(int magazineCapacity) {
        this.magazineCapacity = magazineCapacity;
    }
    public Weapon.Mode getMode() {
        return mode;
    }
    public void setMode(Weapon.Mode mode) {
        this.mode = mode;
    }
    public void setBaseRange(int range)
    {
        this.baseStats.setRange(range);
    }
    public void setBaseDamage(int damage)
    {
        this.baseStats.setDamage(damage);
    }
    public void setBaseReloadSpeed(int reloadSpeed)
    {
        this.baseStats.setReloadSpeed(reloadSpeed);
    }
    public void setBaseBulletsPerTurn(int bulletsPerTurn)
    {
        this.baseStats.setBulletsPerTurn(bulletsPerTurn);
    }
    public void setBaseMagCapacity(int magCapacity)
    {
        this.baseStats.setMagazineCapacity(bulletsPerTurn);
    }
    public int getBulletsInMagazine() {
        return bulletsInMagazine;
    }
    public void setBulletsInMagazine(int bulletsInMagazine) {
        this.bulletsInMagazine = bulletsInMagazine;
    }
    public void modifyGunStatsForAttachments(List<Item> attachments)
    {
        if(attachments == null)
            return;

        AttachmentTable table = new AttachmentTable();

        for(Item a : attachments)
        {
            if(table.getRangeAttachmentsTable().contains(a))
            {
                setRange(a.value());
            }
            else if(table.getMagazineAttachmentsTable().contains(a))
            {
                setMagazineCapacity(a.value());
            }
            else if(table.getReloadAttachmentsTable().contains(a))
            {
                setMagazineCapacity(a.value());
            }
        }
    }
    @Override
    public String toString() {
        return "WeaponStats{" +
                "range=" + range +
                ", damage=" + damage +
                ", reloadSpeed=" + reloadSpeed +
                ", bulletsPerTurn=" + bulletsPerTurn +
                ", magazineCapacity=" + magazineCapacity +
                ", mode=" + mode +
                '}';
    }

    public void setBaseStats()
    {
        this.baseStats = new WeaponStats(range, damage, reloadSpeed, bulletsPerTurn, magazineCapacity, mode);
    }
    public WeaponStats getBaseStats()
    {
        return this.baseStats;
    }
}
