package proviz;

/**
 * Created by Burak on 8/16/17.
 */
public class GlobalValues {
    private static GlobalValues ourInstance = new GlobalValues();

    public static GlobalValues getInstance() {
        return ourInstance;
    }

    private int boardCounter;

    public int getBoardCounter() {
        return boardCounter;
    }

    public void setBoardCounter(int boardCounter) {
        this.boardCounter = boardCounter;
    }

    public void increaseBoardCounterbyOne()
    {
        this.boardCounter++;
    }
    private GlobalValues() {
        boardCounter =1;
    }
}
