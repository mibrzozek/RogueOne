package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttachmentTable implements Serializable
{
    private List<Item> rangeAttachments;
    private List<Item> damageAttachments;
    private List<Item> magazineAttachments;
    private List<Item> fireRateAttachments;
    private List<Item> reloadAttachments;

    ItemFactory itemFactory;

    public AttachmentTable()
    {
        this.rangeAttachments = new ArrayList<>();
        this.damageAttachments = new ArrayList<>();
        this.magazineAttachments = new ArrayList<>();
        this.fireRateAttachments = new ArrayList<>();
        this.reloadAttachments = new ArrayList<>();

        this.itemFactory = new ItemFactory();

        initRangeTable();
        initDamageTable();
        initMagazineTable();
        initFireRateTable();
        initReloadTable();
    }
    private void initRangeTable()
    {
        rangeAttachments.add(itemFactory.newRedDotSight());
        rangeAttachments.add(itemFactory.newHoloSight());
    }
    private void initDamageTable()
    {

    }
    private void initMagazineTable()
    {
        magazineAttachments.add(itemFactory.newGlock19ExtendedMags());
    }
    private void initFireRateTable()
    {

    }
    private void initReloadTable()
    {

    }

    public List<Item> getRangeAttachmentsTable() {
        return rangeAttachments;
    }
    public List<Item> getDamageAttachmentsTable() {
        return damageAttachments;
    }
    public List<Item> getMagazineAttachmentsTable() {
        return magazineAttachments;
    }
    public List<Item> getFireRateAttachmentsTable() {
        return fireRateAttachments;
    }
    public List<Item> getReloadAttachmentsTable() {
        return reloadAttachments;
    }
}
