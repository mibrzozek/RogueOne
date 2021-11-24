package wolrdbuilding;

import entities.Entity;

import java.io.Serializable;

public class OverWorld implements Serializable
{

    private TileV[][] tiles;
    private Entity player;

    private int oww = 66;
    private int owh = 66;

    public OverWorld()
    {
        makeOverworld();
    }

    public void makeOverworld()
    {
        tiles =  new TileV[oww][owh];

        for(int x = 0; x <  oww; x++)
        {
            for(int  y = 0; y < owh; y++)
            {
                tiles[x][y] =  new TileV(Tile.INSIDE_FLOOR);
            }
        }
    }
}
