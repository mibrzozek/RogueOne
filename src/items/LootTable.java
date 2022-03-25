package items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootTable
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

    List<Item> floorOneLootTable;

    public LootTable()
    {
        greenRoomItems =  new ArrayList<>();
        redRoomItems =  new ArrayList<>();
        goldRoomItems =  new ArrayList<>();
        armorItems = new ArrayList<>();
        floorOneLootTable = new ArrayList<>();

        r = new Random();
        itemFactory = new ItemFactory();

        initRoomLists();
        initArmorLists();
        initFloorOneLootTable();
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
        greenRoomItems.add(itemFactory.newBioLard());

        redRoomItems.add(itemFactory.newScopedRifle());
        redRoomItems.add(itemFactory.newBlueClearance());
        redRoomItems.add(itemFactory.newOre());
        redRoomItems.add(itemFactory.newFragGrenade());
        redRoomItems.add(itemFactory.newSmokeGrenade());
        redRoomItems.add(itemFactory.newHelmet3());
        redRoomItems.add(itemFactory.newMiningBeam());
        redRoomItems.add(itemFactory.newTerrainMapper());
        redRoomItems.add(itemFactory.newBioLard());
        redRoomItems.add(itemFactory.newReflectiveShall());
        redRoomItems.add(itemFactory.newMemModule());

        goldRoomItems.add(itemFactory.newBandages());
        goldRoomItems.add(itemFactory.newRustyKnife());
        goldRoomItems.add(itemFactory.newFragGrenade());
        goldRoomItems.add(itemFactory.newPickAxe());
        goldRoomItems.add(itemFactory.newPlasmaAxe());
        goldRoomItems.add(itemFactory.newClearanceGreen());
        goldRoomItems.add(itemFactory.newDiscoBall());
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
    public Item getFloorOneLoot()
    {
        return floorOneLootTable.remove(r.nextInt(floorOneLootTable.size()));
    }

}
