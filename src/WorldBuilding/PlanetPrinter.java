package WorldBuilding;



public class PlanetPrinter
{
	private int width;
	private int height;
	private int depth;
	private Tile[][][] tiles;

	public PlanetPrinter(int width, int height, int depth)
	{
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		this.tiles = new Tile[width][height][depth];
	}
	public World build()
	{
		return new World(tiles, null, null);
	}
	private PlanetPrinter makeSomething()
	{
		
		return this;
	}
}
