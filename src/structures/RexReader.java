package structures;

import screens.PlayScreen;
import wolrdbuilding.Tile;
import wolrdbuilding.TilePoint;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.awt.Color.*;

public class RexReader
{
    private static String filePath ="D:\\06 SOURCE\\01 JAVA PROJECTS\\02 JAVA PROJECTS\\RogueOne_UV1\\resources\\structures\\";

    private static ArrayList<TilePoint> structureTiles = new ArrayList<>();
    private static Map<String, ArrayList<TilePoint>> structureMap =  new HashMap<>();

    public RexReader()
    {
        loadStructures();
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

    public static void retrieveStructures() throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(filePath + "loadedStructures");
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

        System.out.println(files[0]);

        for(int i = 0; i < files.length; i++)
        {
            try (Scanner fileScan = new Scanner(new File(filePath + files[i]), "UTF-8"))
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
}
