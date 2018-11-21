package wolrdbuilding;

public class RoomPoint extends Point
{
	public int x, y, z, w, h, tw, th;
	private Direction doorDirection;
	
	private Point tunnelPosition;
	private Point doorPosition;
	private TileSet s;
	private TileSet tunnelS;
	
	private boolean tunnel =false;
	
	public RoomPoint(Point p, int w, int h)
	{
		super(p.x, p.y, p.z);
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
		this.w = w;
		this.h = h;
		
		addWidth(w);
		addHeight(h);
		this.doorDirection = Direction.NORTH;
		this.doorPosition = getDoorPoint();
		
	}
	
	public RoomPoint(Point p, int w, int h, Direction d)
	{
		super(p.x, p.y, p.z);
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
		this.w = w;
		this.h = h;
		
		addWidth(w);
		addHeight(h);
		this.doorDirection = d;
		this.doorPosition = getDoorPoint();
		
	}
	public Direction direction()
	{
		return doorDirection;
	}
	public void setDirection(Direction d)
	{
		this.doorDirection = d;
	}
	public Point getTunnelPosition()
	{
		return tunnelPosition;
	}
	public Point point()
	{
		return new Point(x, y, z);
	}
	public TileSet tileSet()
	{
		return s;
	}
	public void setTileSet(TileSet ts)
	{
		this.s = ts;
	}
	
	public RoomPoint calculateTunnelPoint()
	{
		int m = 0;
		int nw = 0, nh = 0;
		int count = 0;
		Point tunnelP = null;
		Point p = point();
		RoomPoint theTunnel;
		
		if(!tunnel)
		{
		if(doorDirection.toString().equals("NORTH"))
		{
			do
			{			
				nw = r.nextInt(PlanetPrinter.MAX_TUNNEL_WIDTH) + 3;
				nh = r.nextInt(PlanetPrinter.MAX_TUNNEL_LENGTH)+ 3;
			
				tunnelP = new Point((doorPosition.x -1), doorPosition.y - nh + 1, p.z);
				count++;
			} while(!PlanetPrinter.isValidPoint(tunnelP, nw, nh) && count < 20);
			s = TileSet.UP_DOWN_TUNNEL_S;
			tunnelP.addHeight(nh);
			tunnelP.addWidth(3);			
			tw = 3;
			th = nh;
		}
		else if(doorDirection.toString().equals("SOUTH"))
		{
			do
			{			
				nw = r.nextInt(PlanetPrinter.MAX_TUNNEL_WIDTH) + 3;
				nh = r.nextInt(PlanetPrinter.MAX_TUNNEL_LENGTH)+ 3;
		
				tunnelP = new Point((doorPosition.x -1), doorPosition.y, p.z);
				count++;
			} while(!PlanetPrinter.isValidPoint(tunnelP, nw, nh) && count < 20);
			s = TileSet.UP_DOWN_TUNNEL_S;
			tunnelP.addHeight(nh);
			tunnelP.addWidth(3);	
			tw = 3;
			th = nh;
		}
		else if(doorDirection.toString().equals("EAST"))
		{
			do
			{			
				nw = r.nextInt(PlanetPrinter.MAX_TUNNEL_WIDTH) + 3;
				nh = r.nextInt(PlanetPrinter.MAX_TUNNEL_LENGTH)+ 3;
		
				tunnelP = new Point((doorPosition.x), doorPosition.y -1, p.z);
				count++;
			} while(!PlanetPrinter.isValidPoint(tunnelP, nw, nh) && count < 20);
			s = TileSet.LEFT_RIGHT_TUNNEL_S;

			tunnelP.addHeight(3);
			tunnelP.addWidth(nw);
			tw = nw;
			th = 3;
		}
		else if(doorDirection.toString().equals("WEST"))
		{
			do
			{			
				nw = r.nextInt(PlanetPrinter.MAX_TUNNEL_WIDTH) + 3;
				nh = r.nextInt(PlanetPrinter.MAX_TUNNEL_LENGTH)+ 3;
		
				tunnelP = new Point((doorPosition.x - nw + 1), doorPosition.y -1, p.z);
				count++;
			} while(!PlanetPrinter.isValidPoint(tunnelP, nw, nh) && count < 20);
			s = TileSet.LEFT_RIGHT_TUNNEL_S;

			tunnelP.addHeight(3);
			tunnelP.addWidth(nw);
			tw = nw;
			th = 3;
		}
		theTunnel = new RoomPoint(tunnelP.point(), tunnelP.w, tunnelP.h, this.direction());
		theTunnel.setTileSet(s);
		tunnelS = s;
		tunnelPosition = tunnelP.point();
		tunnel = true;
		
		}
		else
		{
			theTunnel = new RoomPoint(tunnelPosition, tw, th, this.direction());
			theTunnel.setTileSet(tunnelS);
			tunnel = false;
		}
		
		if(!PlanetPrinter.isValidPoint(theTunnel, theTunnel.w, theTunnel.h))
		{
			System.out.println(doorDirection.toString() + " not valid point");
			doorDirection = PlanetPrinter.chooseNextUnoccupiedDirection(point(), doorDirection);
			doorPosition = getDoorPoint();
			theTunnel = calculateTunnelPoint();
			
			System.out.println(doorDirection.toString() + " not valid point");
		}

		return theTunnel;
	}
	public Point getDoorPoint()
	{
		Point p = null;
		if(doorDirection == Direction.NORTH)
		{	
			if(w>=3)
				p= new Point(r.nextInt(w - 2) + x + 1, y, z);
			else 
				System.out.println(w + " w this is it");
		}
		else if(doorDirection == Direction.SOUTH)
		{
			if(w>=3)
				p= new Point(r.nextInt(w - 2) + x + 1, y + h - 1, z);
			else 
				System.out.println(w + " w this is it");
			
		}
		else if(doorDirection == Direction.EAST)
		{
			p= new Point(x + w - 1, r.nextInt(h -2) + y + 1, z);
		}
		else if(doorDirection == Direction.WEST)
		{
			p= new Point(x, r.nextInt(h -2) + y + 1, z);
		}
		return p;
	}
	
	
	
	
	
}
