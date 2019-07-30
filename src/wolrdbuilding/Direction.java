package wolrdbuilding;

import static wolrdbuilding.Palette.r;

public enum Direction
{
	NORTH("North", 0),
	NORTH_WEST("North West", 7),
	NORTH_EAST("North East", 1),
	
	SOUTH("South", 4), 
	SOUTH_WEST("South West", 5),
	SOUTH_EAST("South East", 3),
	
	EAST("East", 2), 
	WEST("West", 6), 
	;
	
	private String cardDirection;
	private int intDirection;
	
	Direction(String cardinal, int intDir)
	{
		this.cardDirection = cardinal;
		this.intDirection = intDir;
	}
	public static Direction fromIntToString(int intDirection)
	{
		if(intDirection == 0) {
			return Direction.NORTH;
		}
		else if(intDirection == 7) {
			return Direction.NORTH_WEST;
		}
		else if(intDirection == 1) {
			return Direction.NORTH_EAST;
		}
		else if(intDirection == 4) {
			return Direction.SOUTH;
		}
		else if(intDirection == 3) {
			return Direction.SOUTH_EAST;
		}
		else if(intDirection == 5) {
			return Direction.SOUTH_WEST;
		}
		else if(intDirection == 6) {
			return Direction.WEST;
		}
		else if(intDirection == 2) {
			return Direction.EAST;
		}
		else return null;
		
	}
	public static Direction getRandomDirection()
	{
		int d = r.nextInt(4) + 1;

		if(d == 1)
			return Direction.NORTH;
		else if(d == 2)
			return Direction.SOUTH;
		else if(d == 3)
			return Direction.EAST;
		else if(d == 4)
			return Direction.WEST;
		else
			return Direction.NORTH;
	}
	public static int getMovement(Direction d)
	{
		if(d.equals(Direction.NORTH) || d.equals(Direction.WEST))
			return -1;
		else if(d.equals(Direction.EAST) || d.equals(Direction.SOUTH))
			return 1;
		else
			return 0;
	}
	public String getDirection()
	{
		return cardDirection;
	}
	public int getIntDirection()
	{
		return intDirection;
	}
	public void setDirection(String cardinal)
	{
		this.cardDirection = cardinal;
	}
	public void setFurthest(int i)
	{
		this.intDirection = i;
	}
}
