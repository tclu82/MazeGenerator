package zac.com;

import java.util.Stack;

/**
 * Created by zac on 11/18/16.
 */
public class Maze {

    // 2D Cell array for whole maze class.
    private Cell[][] myMaze;

    // Row count
    private int myRow;

    // Column count
    private int myCol;

    // The result of myRow x myCol
    private int myCellCount;

    // Counter for visited Cells.
    private int myVisited;

    // Stack to record last step.
    private Stack<Cell> myLastRightStep;

    // Stack to store solution.
    private Stack<Cell> mySolution;

    // Flag shows if reach the end.
    private boolean myReachEnd;

    // Flag for debug mode or regular mode switch.
    private boolean myDebug;

    /**
     * Constructor of Maze.
     *
     * @param width
     * @param height
     * @param theDebug
     */
    public Maze(int width, int height, boolean theDebug) {

        // Row takes 2, 1 for border 1 for path, +1 for fence and post
        myRow = 2 * height + 1;

        // Same as row
        myCol = 2 * width + 1;
        myMaze = new Cell[myRow][myCol];
        myCellCount = width * height;

        // Show the answer or not (debug mode)
        myDebug = theDebug;
        myVisited = 0;
        myReachEnd = false;

        myLastRightStep = new Stack<>();
        mySolution = new Stack<>();

        // Helper method to build a maze.
        buildMaze();
    }

    /**
     * Helper method used in Constructor.
     */
    private void buildMaze() {
        for (int i=0; i<myRow; i++) {

            for (int j=0; j<myCol; j++) {

                // For top most, bottom most, left most, and right most, set Cell as border.
                if (i == 0 || i == myRow-1 || j == 0 || j == myCol-1)
                    myMaze[i][j] = new Cell(i, j, true);

                else
                    myMaze[i][j] = new Cell(i, j, false);
            }

        }

        // Set up starting point.
        myMaze[0][1].isBorder = false;
        myMaze[0][1].isStart = true;

        // Set up ending point.
        myMaze[myRow-1][myCol-1].isBorder = false;
        myMaze[myRow-1][myCol-1].isEnd = true;

        mazeHelper();
    }

    /**
     * Helper method called inside buildMaze to draw actual maze.
     */
    private void mazeHelper() {








    }






    /**
     * Private class for individual maze cell.
     */
    private class Cell {

        private int row;

        private int col;

        private boolean isBorder;

        private boolean isVisted;

        private boolean isStart;

        private boolean isEnd;

        private boolean isPath;

        /**
         * Constructor of Cell.
         *
         * @param theRow
         * @param theCol
         * @param theBorder
         */
        private Cell(int theRow, int theCol, boolean theBorder) {
            row = theRow;
            col = theCol;
            isBorder = theBorder;
            isVisted = false;
            isStart = false;
            isEnd = false;
            isPath = false;
        }
    }
}
