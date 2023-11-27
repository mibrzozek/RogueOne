package puzzlelike;

import items.Item;
import items.ItemFactory;
import wolrdbuilding.Direction;
import wolrdbuilding.Door;
import wolrdbuilding.Point;
import wolrdbuilding.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Puzzle
{
    // Problem
    // Resolutions
    // Requirements for map
        // spawn door
        // spawn key
    //
    private List<Problem> problems;
    private List<Solution> solutions;

    private List<Door> doors;
    private List<Item> items;

    private List<Terrain> terrains; // will determine map type and tileset
    private List<Point> itemSpawns;

    private Direction borderCardinalDirection;

    public Puzzle(Problem prob, Terrain terrain)
    {
        this.problems = new ArrayList<>();
        this.solutions = new ArrayList<>();
        this.terrains = new ArrayList<>();

        this.doors = new ArrayList<>();
        this.items = new ArrayList<>();
        this.itemSpawns = new ArrayList<>();

        this.borderCardinalDirection = null;

        problems.add(prob);
        solutions.addAll(prob.getSolutions());
        terrains.add(terrain);
    }
    public void addLockedDoor(Door d, Direction direction)
    {
        this.doors.add(d);
        Item key = new ItemFactory().newUniqueKey();
        this.items.add(key);
        this.borderCardinalDirection = direction;
    }
    public void addProblem(Problem prob)
    {
        this.problems.add(prob);
        this.solutions.addAll(prob.getSolutions());
    }

    public Problem problem()
    {
        return this.problems.get(0);
    }
    public Solution solution()
    {
        return solutions.get(0);
    }
    public List<Problem> problems()
    {
        return problems;
    }
    public List<Solution> solutions()
    {
        return solutions;
    }

    public boolean isSolved()
    {
        if (doors.isEmpty())
            return false;

        if(doors.get(0).isOpen())
        {
            return true;
        }
        else
            return false;
    }

    public void intialize(World w)
    {
        Random r = new Random();
        w.addItemAt(itemSpawns.get(r.nextInt(itemSpawns.size())), items.get(0));
    }
    public void addItemSpawnPoints(List<Point> allInsideFloor)
    {
        this.itemSpawns = allInsideFloor;
    }

    public List<Door> getDoors()
    {
        return this.doors;
    }

    public Direction getborderCardinalDirection() {
        return borderCardinalDirection;
    }
}
