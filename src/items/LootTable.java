package items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootTable
{
    private Random r;
    private ItemFactory itemFactory;
    List<Item> greenRoomItems;
    List<Item> redRoomItems;
    List<Item> goldRoomItems;

    public LootTable()
    {
        greenRoomItems =  new ArrayList<>();
        redRoomItems =  new ArrayList<>();
        goldRoomItems =  new ArrayList<>();
        r = new Random();
        itemFactory = new ItemFactory();

        initRoomLists();
    }
    public void initRoomLists()
    {
        greenRoomItems.add(itemFactory.newMedicalKit());
        greenRoomItems.add(itemFactory.newFullHeal());
        greenRoomItems.add(itemFactory.newMedicinal());
        greenRoomItems.add(itemFactory.newCyberneticSyringe());
        greenRoomItems.add(itemFactory.newBandages());

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

        goldRoomItems.add(itemFactory.newBandages());
        goldRoomItems.add(itemFactory.newRustyKnife());
        goldRoomItems.add(itemFactory.newFragGrenade());
        goldRoomItems.add(itemFactory.newMemModule());
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

}
