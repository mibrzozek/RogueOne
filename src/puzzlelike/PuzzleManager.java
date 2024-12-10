package puzzlelike;

import wolrdbuilding.Direction;
import wolrdbuilding.Point;
import wolrdbuilding.TerrainPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PuzzleManager implements Serializable
{
    List<Puzzle> allPuzzles;
    Puzzle currentPuzzle;
    Puzzle previousPuzzle;


    public PuzzleManager(Puzzle p)
    {
        this.currentPuzzle = p;
        this.allPuzzles = new ArrayList<>();
        this.allPuzzles.add(currentPuzzle);
    }
    public Puzzle getCurrent()
    {
        return currentPuzzle;
    }
    public void addNextPuzzle(Puzzle p)
    {
        this.previousPuzzle = currentPuzzle;
        this.currentPuzzle = p;
        this.allPuzzles.add(p);
    }
    public Puzzle getPreviousPuzzle()
    {
        return this.previousPuzzle;
    }
    public int getPuzzCount()
    {
        return allPuzzles.size();
    }
    public TerrainPoint getNextPuzzleTerrainOffset()
    {
        Point p = new Point(0, 0, 0);
        TerrainPoint tp = new TerrainPoint(p, Direction.getRandomDirection());

        switch(allPuzzles.size())
        {
            case 0:
            case 1:
                p = new Point(0, 0, 0);
                tp = new TerrainPoint(p, Direction.EAST);
                break;
            case 2:
                p = new Point(49, 0, 0);
                tp = new TerrainPoint(p, Direction.EAST);
                break;
            case 3:
                p = new Point(99, 0, 0);
                tp = new TerrainPoint(p, Direction.EAST);
                break;
            case 4:
                p = new Point(149, 0, 0);
                tp = new TerrainPoint(p, Direction.SOUTH);
                break;
            case 5:
                p = new Point(149, 49, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
            case 6:
                p = new Point(99, 49, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
            case 7:
                p = new Point(49, 49, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
            case 8:
                p = new Point(0, 49, 0);
                tp = new TerrainPoint(p, Direction.SOUTH);
                break;
            case 9:
                p = new Point(0, 99, 0);
                tp = new TerrainPoint(p, Direction.EAST);
                break;
            case 10:
                p = new Point(50, 99, 0);
                tp = new TerrainPoint(p, Direction.EAST);
                break;
            case 11:
                p = new Point(99, 99, 0);
                tp = new TerrainPoint(p, Direction.EAST);
                break;
            case 12:
                p = new Point(149, 99, 0);
                tp = new TerrainPoint(p, Direction.SOUTH);
                break;
            case 13:
                p = new Point(149, 149, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
            case 14:
                p = new Point(99, 149, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
            case 15:
                p = new Point(49, 149, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
            case 16:
                p = new Point(0, 149, 0);
                tp = new TerrainPoint(p, Direction.WEST);
                break;
        }
        return tp;
    }

}
