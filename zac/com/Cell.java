package zac.com;

/**
 * Created by zac on 11/19/16.
 *
 * Cell class for individual maze cell.
 */
class Cell {

    int row;

    int col;

    boolean isBorder;

    boolean isVisited;

    boolean isStart;

    boolean isEnd;

    boolean isPath;

    /**
     * Constructor of Cell.
     *
     * @param theRow
     * @param theCol
     * @param theBorder
     */
    Cell(int theRow, int theCol, boolean theBorder) {
        row = theRow;
        col = theCol;
        isBorder = theBorder;
        isVisited = false;
        isStart = false;
        isEnd = false;
        isPath = false;
    }
}
