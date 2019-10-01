package structures;

import wolrdbuilding.TileV;

public class Air
{
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


    public Air(TileV[][][] worldMap)
    {
        airMap = getAirMap(worldMap);
    }

    /** Get Air Map from made dungeon
     *      Count all empty spaces.
     *
      * @param worldMap
     * @return
     */
    public TileV[][][] getAirMap(TileV[][][] worldMap)
    {
        TileV[][][] airMap = worldMap;

        for(int i = 0; i < 200; i++)
        {

        }

        return null;
    }

}
