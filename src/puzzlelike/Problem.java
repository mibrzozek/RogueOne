package puzzlelike;

import java.util.ArrayList;
import java.util.List;

public enum Problem
{
    LOCK_AND_KEY("Lock and key", Solution.OPEN_WITH_KEY);

    private String name;
    private List<Solution> solutions;

    Problem(String name, Solution solution)
    {
        this.name = name;
        this.solutions = new ArrayList<>();
        this.solutions.add(solution);
    }
    public void addSolution(Solution solute)
    {
        solutions.add(solute);
    }

    public List<Solution> getSolutions()
    {
        return this.solutions;
    }
}
