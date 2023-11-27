package puzzlelike;

public enum Solution
{
    OPEN_WITH_KEY("You use the lock to open the key"),
    KICK_THE_DOOR("You kick the door open");

    private String name;

    Solution(String name)
    {
        this.name = name;
    }

}
