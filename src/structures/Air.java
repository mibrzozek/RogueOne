package structures;

import wolrdbuilding.TileV;
import wolrdbuilding.World;

public class Air
{
    public static final double OXYGEN = .21;
    public static final double NITROGEN = .78;
    public static final double OTHER = .01;

    private int oxygen = 1000;

    private int nitrogen = 4000;
    private int other = 100;

    public enum Molecule
    {
        METHANE("METHANE"),
        HYDROGEN("Hydrogen");

        private String name;

        Molecule(String s)
        {
            this.name = s;
        }
    }

    private TileV[][][] airMap;
    private World world;

    public Air(World world)
    {
        this.world = world;
    }

    /** Get Air Map from made dungeon
     *      Count all empty spaces.
     *
      * @param worldMap
     * @return
     */
    public TileV[][][] getAirMap(TileV[][][] worldMap)
    {
        TileV[][][] airMap = new TileV[worldMap.length][worldMap[0].length][worldMap[0][0].length];

        for(int z = 0; z < worldMap[0][0].length; z++)
        {
            for(int y = 0; y < worldMap[0].length; y++)
            {
                for(int x = 0; x < worldMap.length; x++)
                {

                }
            }
        }
        return null;
    }
    public void modifyAir(int o2)
    {
        if(oxygen > -1 )
            oxygen += o2;
    }

    public void modifyNitrogen(int n)
    {
        if(nitrogen + n > 0)
            oxygen +=n;
    }
    public void modifyOther(int gas)
    {
        if(other + gas > 0)
            other +=gas;
    }
    public int getOxygen()
    {
        return oxygen;
    }
}
