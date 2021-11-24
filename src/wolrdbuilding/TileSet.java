package wolrdbuilding;

public enum TileSet 
{
	DOUBLE_S(Tile.dblTLCS, Tile.dblTRCS, Tile.dblBLCS
			, Tile.dblBRCS, Tile.dblTBWS, Tile.dblLRWS),

	SIMPLE_S(Tile.simpleTLCS, Tile.simpleTRCS, Tile.simpleBLCS
			, Tile.simpleBRCS, Tile.simpleTBWS, Tile.simpleLRWS),

	WOOD(Tile.WOOD_WALL, Tile.WOOD_WALL, Tile.WOOD_WALL
			, Tile.WOOD_WALL, Tile.WOOD_WALL, Tile.WOOD_WALL, Tile.DIRT_FLOOR),

	DOUBLE(Tile.dblTLC, Tile.dblTRC, Tile.dblBLC
			, Tile.dblBRC, Tile.dblTBW, Tile.dblLRW, Tile.INSIDE_FLOOR),

	SIMPLE(Tile.simpleTLC, Tile.simpleTRC, Tile.simpleBLC
			, Tile.simpleBRC, Tile.simpleTBW, Tile.simpleLRW, Tile.INSIDE_FLOOR),

	INSIDE_TILE(Tile.INSIDE_FLOOR),

	CANISTERS(Tile.PLASMA_CANISTER),
	
	UP_DOWN_TUNNEL_S(Tile.simpleTRC, Tile.simpleTLC, Tile.simpleBRC,
					Tile.simpleBLC, Tile.INSIDE_FLOOR, Tile.simpleLRW, Tile.INSIDE_FLOOR),
	LEFT_RIGHT_TUNNEL_S(Tile.simpleBLC, Tile.simpleBRC, Tile.simpleTLC,
			Tile.simpleTRC, Tile.simpleTBW, Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR),
	ALL_GROUND_ROOM(Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR,
			Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR),
	
	;
	
	public Tile tlc;
	public Tile trc;
	public Tile blc;
	public Tile brc; 
	public Tile tbw; 
	public Tile lrw;
	public Tile floor;
	
	public boolean isRoom;
	


	TileSet(Tile t)
	{
		this.tlc = t;
		this.trc = t;
		this.blc = t;
		this.brc = t;
		this.tbw = t;
		this.lrw = t;

		this.floor = Tile.INSIDE_FLOOR;
		
		this.isRoom = false;
	}
	TileSet(Tile tlc, Tile trc, Tile blc ,Tile brc ,Tile tbw , Tile lrw)
	{
		this.tlc = tlc;
		this.trc = trc;
		this.blc = blc;
		this.brc = brc;
		this.tbw = tbw;
		this.lrw = lrw;

		this.floor = Tile.INSIDE_FLOOR;

		this.isRoom = true;
	}
	TileSet(Tile tlc, Tile trc, Tile blc ,Tile brc ,Tile tbw , Tile lrw, Tile floor)
	{
		this.tlc = tlc;
		this.trc = trc;
		this.blc = blc;
		this.brc = brc;
		this.tbw = tbw;
		this.lrw = lrw;

		this.floor = floor;

		this.isRoom = true;
	}
	public Tile tlc()
	{
		return tlc;
	}
	public Tile trc()
	{
		return trc;
	}
	public Tile blc()
	{
		return blc;
	}
	public Tile brc()
	{
		return brc;
	}
	public Tile tbw()
	{
		return tbw;
	}
	public Tile lrw()
	{
		return lrw;
	}
	public Tile floor()
	{
		return floor;
	}
}
