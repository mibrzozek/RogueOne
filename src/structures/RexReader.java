package structures;

import items.Item;
import items.LootTable;
import items.Weapon;
import items.WeaponStats;
import wolrdbuilding.TilePoint;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class RexReader
{
    private static String filePath = "C:\\006 SOURCE\\01 JAVA PROJECTS\\004 ROGUE ONE\\RogueOne\\resources\\structures\\";
    private static String filePath1 ="C:\\006 SOURCE\\01 JAVA PROJECTS\\004 ROGUE ONE\\RogueOne\\resources\\structures\\";


    private static ArrayList<TilePoint> structureTiles = new ArrayList<>();
    private static Map<String, ArrayList<TilePoint>> structureMap =  new HashMap<>();

    
    public RexReader()
    {
        loadStructures();
        getDecorations();
        /*
        try
        {
            retrieveStructures();
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Retrieving structures  || IN CONSTRUCTOR");
        }
        */
    }


    public HashMap getStructures()
    {
        return (HashMap) structureMap;
    }

    public static void retrieveStructures(String s) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(String.valueOf(RexReader.class.getResource("resources/loadedStructures")));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        try
        {
            structureMap = (HashMap) objectInputStream.readObject();
        } catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Retrieving structures  || IN RETRIEVE METHOD");
        }
        objectInputStream.close();
    }
    public HashMap<Integer , ArrayList<TilePoint>> getDecorations()
    {
        HashMap<Integer , ArrayList<TilePoint>> partsMap= new HashMap<>();

        ArrayList<TilePoint> tbwDecor = new ArrayList();
        if(structureMap.containsKey("tbwDecor.csv"))
            tbwDecor = structureMap.get("tbwDecor.csv");

        ArrayList<TilePoint> temp = new ArrayList();

        for(TilePoint t : tbwDecor)
        {
            if(t.ascii() == 32)
                continue;
            if(partsMap.containsKey(t.y()))
            {
                temp = partsMap.get(t.y());
                temp.add(t);
                partsMap.put(t.y(), temp);
            }
            else
            {
                temp = new ArrayList<>();
                temp.add(t);
                partsMap.put(t.y(), temp);
            }
        }

        return partsMap;
    }
    public static void loadStructures()
    {
        String line = "";
        String[] fields;

        int x, y, ascii;
        Color fColor, bColor;

        File file = new File(filePath);
        String[] files = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });

        for(int i = 0; i < files.length; i++)
        {
            try (Scanner fileScan = new Scanner(new File(filePath +  files[i]), "UTF-8"))
            {
                while (fileScan.hasNextLine())
                {
                    line = fileScan.nextLine();
                    if (line.contains("X"))
                        continue;
                    fields = line.split(",");
                    x = Integer.parseInt(fields[0]);
                    y = Integer.parseInt(fields[1]);
                    ascii = Integer.parseInt(fields[2]);
                    fColor = Color.decode(fields[3]);
                    bColor = Color.decode(fields[4]);

                    structureTiles.add(new TilePoint(x, y, ascii, fColor, bColor));
                }

                structureMap.put(files[i], new ArrayList<>(structureTiles));
                structureTiles.clear();

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(new File(filePath + "loadedStructures"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(structureMap);
            oos.close();
            fos.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Saving structures FAILED");
        }
    }
    public static int retrieveMaxUpgradesForStat(WeaponStats.WEAPON_STAT stat)
    {


        return 1;
    }

    public static Integer retrieveStatsForUpgradeLevel(String weaponName, Integer level, WeaponStats.WEAPON_STAT weaponStat)
    {
        String weaponPath = "C:\\006 SOURCE\\01 JAVA PROJECTS\\004 ROGUE ONE\\RogueOne\\resources\\gun_charts\\";
        List<Item> allGuns = new ArrayList();
        allGuns = new LootTable().getWeapons();
        Integer upgradeValue = 0;

        switch(weaponName){
            case "Glock 19": weaponPath += "GLOCK_19_TREE"; break;
            case "Kalashnikova AK 74": weaponPath += "AK_74_TREE"; break;
            case "Colt M4A1": weaponPath += "M4_TREE"; break;
            case "HK MP7": weaponPath += "MP7_TREE"; break;
            case "FN P90": weaponPath += "P90_TREE"; break;
            case "RPG": weaponPath += "RPG_TREE"; break;
            case "Kriss Vector": weaponPath += "VECTOR_TREE"; break;
            case "Colt M1911": weaponPath += "M1911_TREE"; break;
            case "CZ 75 SP-01 Shadow": weaponPath += "CZ_TREE"; break;
            case "Chiefs Special": weaponPath += "CHIEFS_SPECIAL_TREE"; break;
            case "Winchester Model 1873": weaponPath += "WINCHESTER_TREE"; break;
            case "KAR98K": weaponPath += "KAR98_TREE"; break;
            case "Glock G18C": weaponPath += "G18C_TREE"; break;
            case "HK UMP": weaponPath += "UMP_TREE"; break;
            case "DVL Saboteur": weaponPath += "DVL_TREE"; break;
            case "Mk47 Mutant": weaponPath += "MUTANT_TREE"; break;
            case "Steyr AUG": weaponPath += "AUG_TREE"; break;
            case "KEL TEK KSG": weaponPath += "KEL_TEK_TREE"; break;
        }
        weaponPath += ".csv";
        System.out.println(weaponPath + " path check");

        String line = "";
        String[] fields;

        WeaponStats stats = null;

        try (Scanner fileScan = new Scanner(new File(weaponPath), "UTF-8"))
        {
            while (fileScan.hasNextLine())
            {
                line = fileScan.nextLine();

                fields = line.split(",");

                if(fields[0].equals(weaponName))
                    continue;

                if(Integer.parseInt(fields[0]) == level)
                {
                    switch (weaponStat)
                    {
                        case RANGE:
                            upgradeValue = getStringValue(fields[1]);
                            break;
                        case DAMAGE:
                            upgradeValue = getStringValue(fields[2]);
                            break;
                        case RELOAD_SPEED:
                            upgradeValue = getStringValue(fields[3]);
                            break;
                        case BULLETS_PER_TURN:
                            upgradeValue = getStringValue(fields[4]);
                            break;
                        case MAG_CAPACITY:
                            upgradeValue = getStringValue(fields[5]);
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return upgradeValue;
    }
    // Returns zero if String is empty
    public static Integer getStringValue(String field)
    {
        if(field.isEmpty())
            return 0;
        else
            return Integer.parseInt(field);
    }
    public static WeaponStats retrieveStats(String weaponName)
    {
        String weaponPath = "C:\\006 SOURCE\\01 JAVA PROJECTS\\004 ROGUE ONE\\RogueOne\\resources\\gun_charts\\R1_GUN_TABLE.csv";
        String line = "";
        String[] fields;

        WeaponStats stats = null;

            try (Scanner fileScan = new Scanner(new File(weaponPath), "UTF-8"))
            {
                while (fileScan.hasNextLine())
                {
                    line = fileScan.nextLine();
                    if (!line.contains(weaponName))
                        continue;
                    fields = line.split(",");
                    stats = new WeaponStats(weaponName, Integer.parseInt(fields[1]),
                            Integer.parseInt(fields[2]),
                            Integer.parseInt(fields[3]),
                            Integer.parseInt(fields[4]),
                            Integer.parseInt(fields[5]),
                            Weapon.Mode.getMode(fields[6]));
                }
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        return stats;
    }
}