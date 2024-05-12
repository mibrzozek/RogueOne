package wolrdbuilding;

public class TerrainPoint extends Point
{
    /*
        The direction here is the direction where the next terrain will be generated
            i.e if south then map will be generated below player
     */
    private Direction nextDirectionForMapGen;
    public TerrainPoint(Point p, Direction d)
    {
        super(p.x, p.y, p.z);
        this.nextDirectionForMapGen = d;
    }
    public Direction getNextGenDirection()
    {
        return this.nextDirectionForMapGen;
    }

}
