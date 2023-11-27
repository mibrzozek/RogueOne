package puzzlelike;

import java.util.ArrayList;
import java.util.List;

public class PuzzleManager
{
    List<Puzzle> allPuzzles;
    Puzzle currentPuzzle;

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
        this.currentPuzzle = p;
        this.allPuzzles.add(p);
    }

}
