package Character;

import java.util.List;

import WorldBuilding.Point;

public class Path 
{

  private static PathFinder pf = new PathFinder();

  private List<Point> points;
  public List<Point> points() { return points; }

  public Path(Entity entity, int x, int y)
  {
      points = pf.findPath(entity, 
                           new Point(entity.x, entity.y, entity.z), 
                           new Point(x, y, entity.z), 
                           300);
  }
}