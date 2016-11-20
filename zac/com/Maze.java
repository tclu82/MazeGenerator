package zac.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by zac on 11/18/16.
 */
class Maze {

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
    private Stack<Cell> myLastCorrectStep;

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
    Maze(int width, int height, boolean theDebug) {

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

        myLastCorrectStep = new Stack<>();
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

                // Regular Cell, not border, entry or end.
                else
                    myMaze[i][j] = new Cell(i, j, false);
            }

        }

        // Set up starting point.
        myMaze[0][1].isBorder = false;
        myMaze[0][1].isStart = true;

        // Set up ending point.
        myMaze[myRow-1][myCol-2].isBorder = false;
        myMaze[myRow-1][myCol-2].isEnd = true;

        mazeHelper();
    }

    /**
     * Helper method called inside buildMaze to draw actual maze.
     */
    private void mazeHelper() {
        // Start point.
        Cell cur = myMaze[1][1];
        cur.isVisited = true;

        // Push Cell to mySolution stack, myVisit +1.
        mySolution.push(cur);
        ++myVisited;

        // An ArrayList to contain unvisited neighbors.
        List<Cell> unvisitedNeighbors;

        // A random decide which way to go next.
        Random rand = new Random();

        // Visit all Cells in the maze.
        while (myVisited < myCellCount) {
            // A list contains neighbor of unvisited Cells of current Cell.
            unvisitedNeighbors = checkNeighbors(cur.row, cur.col);

            // Current Cell has some unvisited neighbors.
            if (unvisitedNeighbors.size() > 0) {

                // Use random to determine next step
                int nextStep = rand.nextInt(unvisitedNeighbors.size());
                Cell next = unvisitedNeighbors.get(nextStep);
                next.isVisited = true;

                // Go up
                if (next.row < cur.row) myMaze[cur.row - 1][cur.col].isPath = true;

                    // Go down
                else if (next.row > cur.row) myMaze[cur.row + 1][cur.col].isPath = true;

                    // Go left
                else if (next.col < cur.col) myMaze[cur.row][cur.col - 1].isPath = true;

                    // Go right
                else myMaze[cur.row][cur.col + 1].isPath = true;

                // Store current Cell into myLastCorrectStep Stack.
                myLastCorrectStep.push(cur);
                // Update to next Cell.
                cur = next;

                // Check if reach the end.
                if (cur.row == myRow - 2 && cur.col == myCol - 2) {
                    myReachEnd = true;
                    mySolution.push(cur);
                }

                // Only push solution path when not reaching the end, don't put into solution Stack if reaches the end.
                if (!myReachEnd) mySolution.push(cur);

                // Increment myVisit count.
                ++myVisited;
            }

            // All visited and haven't reached the end, wrong way. Popup previous Cell route.
            else {

                if (!myLastCorrectStep.isEmpty()) cur = myLastCorrectStep.pop();

                if (!mySolution.isEmpty() && !myReachEnd) mySolution.pop();
            }
        }
    }

    /**
     * This method checks neighbors of current Cell and return a List of unvisited Cells.
     *
     * @param theRow current Cell row
     * @param theCol current Cell col
     * @return A list contains all unvisited neighbor Cells of current Cell.
     */
    private List<Cell> checkNeighbors(int theRow, int theCol) {
        List<Cell> res = new ArrayList<>();

        // Top Cell not visited yet.
        if (theRow >= 2 && !myMaze[theRow-2][theCol].isVisited)
            res.add(myMaze[theRow-2][theCol]);

        // Down Cell not visited yet.
        if (theRow < (myRow-2) && !myMaze[theRow+2][theCol].isVisited)
            res.add(myMaze[theRow+2][theCol]);

        // Left Cell not visited yet.
        if (theCol >= 2 && !myMaze[theRow][theCol-2].isVisited)
            res.add(myMaze[theRow][theCol-2]);

        // Right Cell not visited yet.
        if (theCol < myCol-2 && !myMaze[theRow][theCol+2].isVisited)
            res.add(myMaze[theRow][theCol+2]);

        return res;
    }

    /**
     * Draw the actual Maze.
     */
    void drawMaze() {

        for (int i=0; i<myRow; i++) {

            for(int j=0; j<myCol; j++) {

                // Broader but not starting point, and not ending point.
                if (myMaze[i][j].isBorder ||
                    !(myMaze[i][j].isVisited || myMaze[i][j].isPath) && !(myMaze[i][j].isStart || myMaze[i][j].isEnd)) {

                    System.out.print("X ");
                }

                // Starting point.
                else if (myMaze[i][j].isStart) System.out.print("S ");

                // Ending point.
                else if (myMaze[i][j].isEnd) System.out.print("E ");

                // Show solution.
                else if (mySolution.contains(myMaze[i][j]) && myDebug)
                    System.out.print("* ");

                else System.out.print("  ");
            }
            System.out.println();

        }
        System.out.println();
    }

//    /**
//     * Private class for individual maze cell.
//     */
//    private class Cell {
//
//        private int row;
//
//        private int col;
//
//        private boolean isBorder;
//
//        private boolean isVisited;
//
//        private boolean isStart;
//
//        private boolean isEnd;
//
//        private boolean isPath;
//
//        /**
//         * Constructor of Cell.
//         *
//         * @param theRow
//         * @param theCol
//         * @param theBorder
//         */
//        private Cell(int theRow, int theCol, boolean theBorder) {
//            row = theRow;
//            col = theCol;
//            isBorder = theBorder;
//            isVisited = false;
//            isStart = false;
//            isEnd = false;
//            isPath = false;
//        }
//    }
}
