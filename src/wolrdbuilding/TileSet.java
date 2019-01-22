package wolrdbuilding;

public enum TileSet 
{
	DOUBLE_S(Tile.dblTLCS, Tile.dblTRCS, Tile.dblBLCS
			, Tile.dblBRCS, Tile.dblTBWS, Tile.dblLRWS),

	SIMPLE_S(Tile.simpleTLCS, Tile.simpleTRCS, Tile.simpleBLCS
			, Tile.simpleBRCS, Tile.simpleTBWS, Tile.simpleLRWS),

	DOUBLE(Tile.dblTLC, Tile.dblTRC, Tile.dblBLC
			, Tile.dblBRC, Tile.dblTBW, Tile.dblLRW),

	SIMPLE(Tile.simpleTLC, Tile.simpleTRC, Tile.simpleBLC
			, Tile.simpleBRC, Tile.simpleTBW, Tile.simpleLRW),

	INSIDE_TILE(Tile.INSIDE_FLOOR),

	CANISTERS(Tile.PLASMA_CANISTER),
	
	UP_DOWN_TUNNEL_S(Tile.simpleTRC, Tile.simpleTLC, Tile.simpleBRC,
					Tile.simpleBLC, Tile.INSIDE_FLOOR, Tile.simpleLRW),
	LEFT_RIGHT_TUNNEL_S(Tile.simpleBLC, Tile.simpleBRC, Tile.simpleTLC,
			Tile.simpleTRC, Tile.simpleTBW, Tile.INSIDE_FLOOR),
	ALL_GROUND_ROOM(Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR,
			Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR, Tile.INSIDE_FLOOR),
	
	;
	
	public Tile tlc;
	public Tile trc;
	public Tile blc;
	public Tile brc; 
	public Tile tbw; 
	public Tile lrw;
	
	public boolean isRoom;
	


	TileSet(Tile t)
	{
		this.tlc = t;
		this.trc = t;
		this.blc = t;
		this.brc = t;
		this.tbw = t;
		this.lrw = t;
		
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
}
