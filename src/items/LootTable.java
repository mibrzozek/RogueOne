package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootTable implements Serializable
{
    private static final int GOLD_CARDS = 10;
    private static final int GREEN_CARDS = 5;
    private static final int RED_CARDS = 3;
    private static final int BLUE_CARDS = 1;

    private Random r;
    private ItemFactory itemFactory;
    List<Item> greenRoomItems;
    List<Item> redRoomItems;
    List<Item> goldRoomItems;
    List<Item> armorItems;
    List<Item> weapons;
    List<Item> attachments;
    List<Item> ammo;

    List<Item> floorOneLootTable;

    public LootTable()
    {
        greenRoomItems =  new ArrayList<>();
        redRoomItems =  new ArrayList<>();
        goldRoomItems =  new ArrayList<>();
        armorItems = new ArrayList<>();
        floorOneLootTable = new ArrayList<>();
        weapons = new ArrayList<>();
        ammo = new ArrayList<>();
        attachments = new ArrayList<>();

        r = new Random();
        itemFactory = new ItemFactory();

        // Init type lists
        initArmorLists();
        initWeaponItems();
        initAmmoItems();
        initAttachmentItems();

        // Init larger pools using type lists
        initRoomLists();
        initFloorOneLootTable();
    }

    private void initWeaponItems()
    {
        weapons.add(itemFactory.newGlock19());
        weapons.add(itemFactory.newVector());
        weapons.add(itemFactory.newGlock18C());
        weapons.add(itemFactory.newCZSHADOW());

        weapons.add(itemFactory.newChiefsSpecial());
        weapons.add(itemFactory.newWinchester());

        weapons.add(itemFactory.newM4a1());
        weapons.add(itemFactory.newAug());

        weapons.add(itemFactory.newAk74());
        weapons.add(itemFactory.newMkMutant());

        weapons.add(itemFactory.newP90());

        weapons.add(itemFactory.newKar98());
        weapons.add(itemFactory.newDvlSniper());

        weapons.add(itemFactory.newHkmp7());

        weapons.add(itemFactory.newRpg());

        weapons.add(itemFactory.newHkUMP());

        weapons.add(itemFactory.newAk74());
    }
    private void initAmmoItems()
    {
        ammo.add(itemFactory.newM855556());

        ammo.add(itemFactory.newPstGZH());
        ammo.add(itemFactory.newPstGZH100());

        ammo.add(itemFactory.newPs762());

        ammo.add(itemFactory.newFiveSevenAmmo());

        ammo.add(itemFactory.newRpgGrenade());

        ammo.add(itemFactory.newFortyFive());

        ammo.add(itemFactory.newThirtyEight());

        ammo.add(itemFactory.newSevenSix51());
    }
    private void initAttachmentItems()
    {
        attachments.add(itemFactory.newRedDotSight());
        attachments.add(itemFactory.newHoloSight());
        attachments.add(itemFactory.newBaldProFlashlight());

        attachments.add(itemFactory.newGlock19ExtendedMags());
        attachments.add(itemFactory.newGlockCompensator());
        attachments.add(itemFactory.newGlockSupressor());
    }

    private void initFloorOneLootTable()
    {
        // CARD LOOT
        for(int i = 0; i < RED_CARDS; i++) {
            floorOneLootTable.add(itemFactory.newClearanceRed());
        }
        for(int i = 0; i < GOLD_CARDS; i++) {
            floorOneLootTable.add(itemFactory.newClearanceGold());
        }
        for(int i = 0; i < BLUE_CARDS; i++) {
            floorOneLootTable.add(itemFactory.newBlueClearance());
        }
        for(int i = 0; i < GREEN_CARDS; i++) {
            floorOneLootTable.add(itemFactory.newClearanceGreen());
        }
        // OTHER
        floorOneLootTable.add(itemFactory.newTerrainMapper());
    }
    private void initArmorLists()
    {
        armorItems.add(itemFactory.newBasicHelmet());
        armorItems.add(itemFactory.newBasicHelmetII());
        armorItems.add(itemFactory.newHelmet3());
        armorItems.add(itemFactory.newHeavyHelmet());
        armorItems.add(itemFactory.newAdvancedHelmet());
        armorItems.add(itemFactory.newHelmet2());

        armorItems.add(itemFactory.newBasicTorso());
        armorItems.add(itemFactory.newCeramicTorso());
        armorItems.add(itemFactory.newKevlarTorso());
        armorItems.add(itemFactory.newHybridTorso());

        armorItems.add(itemFactory.newHybridPants());
        armorItems.add(itemFactory.newKevlarPants());
        armorItems.add(itemFactory.newArmorPants());
        armorItems.add(itemFactory.newAdvancedPants());

        armorItems.add(itemFactory.newLeatherSleeves());
        armorItems.add(itemFactory.newKevlarSleeves());
        armorItems.add(itemFactory.newArmorSleeves());
        armorItems.add(itemFactory.newArmorSleevesTwo());
    }
    public void initRoomLists()
    {
        greenRoomItems.add(itemFactory.newMedicalKit());
        greenRoomItems.add(itemFactory.newFullHeal());
        greenRoomItems.add(itemFactory.newMedicinal());
        greenRoomItems.add(itemFactory.newCyberneticSyringe());
        greenRoomItems.add(itemFactory.newBandages());
        greenRoomItems.add(itemFactory.newHealKit1());
        greenRoomItems.add(itemFactory.newHealKit2());
        greenRoomItems.add(itemFactory.newHealKit3());
        greenRoomItems.add(itemFactory.newHealKit4());
        greenRoomItems.add(itemFactory.newHealKit5());
        greenRoomItems.add(itemFactory.newMorphine());
        greenRoomItems.add(itemFactory.newAdrenaline());

        redRoomItems.add(itemFactory.newBlueClearance());
        redRoomItems.add(itemFactory.newFragGrenade());
        redRoomItems.add(itemFactory.newSmokeGrenade());
        redRoomItems.add(itemFactory.newTerrainMapper());
        redRoomItems.add(itemFactory.newBioLard());
        redRoomItems.add(itemFactory.newMemModule());
        redRoomItems.add(itemFactory.newBaldProFlashlight());
        redRoomItems.add(itemFactory.newBlueLaser());
        redRoomItems.add(itemFactory.newBasicAiUnit());
        redRoomItems.add(itemFactory.newGR2());
        redRoomItems.add(itemFactory.newGR5());
        redRoomItems.add(itemFactory.newCleaver());
        redRoomItems.add(itemFactory.newTacticalRig());
        redRoomItems.add(itemFactory.newBodySling());
        redRoomItems.addAll(getWeapons());
        redRoomItems.addAll(getAmmo());

        goldRoomItems.add(itemFactory.newBandages());
        goldRoomItems.add(itemFactory.newFragGrenade());
        goldRoomItems.add(itemFactory.newClearanceGreen());
        goldRoomItems.add(itemFactory.newClearanceGold());
        goldRoomItems.add(itemFactory.newHealKit1());
        goldRoomItems.add(itemFactory.newHealKit2());
        goldRoomItems.add(itemFactory.newHealKit3());
        goldRoomItems.add(itemFactory.newMorphine());
        goldRoomItems.add(itemFactory.newWinchester());
        goldRoomItems.add(itemFactory.newThirtyEight());
        goldRoomItems.add(itemFactory.newChiefsSpecial());

    }
    public List<Item> getAmmo() {
        return this.ammo;
    }
    public List<Item> getWeapons() {
        return this.weapons;
    }

    public Item getGreenRoomItem()
    {
        return greenRoomItems.get(r.nextInt(greenRoomItems.size()));
    }
    public Item getRedRoomItem()
    {
        return redRoomItems.get(r.nextInt(redRoomItems.size()));
    }
    public Item getGoldRoomItem()
    {
        return goldRoomItems.get(r.nextInt(goldRoomItems.size()));
    }
    public Item getArmorItem() { return armorItems.get(r.nextInt(armorItems.size())); }
    public Item removeRandomItemFromFloorOneTable()
    {
        return floorOneLootTable.remove(r.nextInt(floorOneLootTable.size()));
    }
    public List<Item> floorOneLootList()
    {
        return floorOneLootTable;
    }

}
