package structures;

import wolrdbuilding.Tile;
import wolrdbuilding.TilePoint;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.awt.Color.*;

public class RexReader
{
    public RexReader()
    {

    }
    public static ArrayList getStructure()
    {
        String line = "";
        String[] fields;
        String filePath ="C:\\Users\\Michal\\Desktop\\[PROGRAMS]\\rexPaint\\images\\buildingParts.csv";

        int x, y, ascii;
        Color fColor, bColor;

        ArrayList<TilePoint> structureTiles = new ArrayList<>();

        try(Scanner fileScan = new Scanner(new File(filePath), "UTF-8"))
        {
            while(fileScan.hasNextLine())
            {
                line = fileScan.nextLine();
                if(line.contains("X"))
                    continue;
                fields = line.split(",");
                x = Integer.parseInt(fields[0]);
                y = Integer.parseInt(fields[1]);
                ascii = Integer.parseInt(fields[2]);
                fColor = Color.decode(fields[3]);
                bColor = Color.decode(fields[4]);

                structureTiles.add(new TilePoint(x, y, ascii, fColor, bColor));
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return structureTiles;
    }
}
