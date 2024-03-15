
package TestUI;

import mazePD.Maze;
import mazePD.Maze.Direction;
import mazePD.Maze.Content;
import mazePD.Maze.MazeMode;
import mazePD.Coordinates;
import DroidPD.Droid;
import java.util.Arrays;

public class mazeTest {
    public static void main(String[] args) {
        Maze maze = new Maze(6, 3, MazeMode.TEST);
        Droid droid = new Droid("TestDroid", maze);

        System.out.println("Maze - Depth: " + maze.getMazeDepth() + " Dim: " + maze.getMazeDim() + " Start: "
                + maze.getMazeStartCoord());
        System.out.println(maze);

        for (int level = 0; level < maze.getMazeDepth(); level++) {
            System.out.println("Level - " + level);
            String[] mazeLevel = maze.toStringLevel(level);
            for (String row : mazeLevel) {
                System.out.println(row);
            }
            System.out.println();
        }
        droid.enterMaze();
        droid.solveMaize();
    }
}
   /*  private static void solveMaze(Droid droid) {

        // Stack<Coordinates> path = new Stack<>();
        Coordinates currentPos = droid.getCurrentCoordinates();
        droid.path.push(currentPos);

         while (droid.scanCurLocation() != Content.END) {
            System.out.println("\n");
            System.out.println("Maze location: " + currentPos);
            System.out.println(Arrays.toString(droid.scanAdjLocation()));

            if (droid.scanCurLocation() == Content.PORTAL_DN) {
                currentPos = droid.usePortal(Direction.DN);
                droid.path.push(currentPos);
                continue;
            }
            Direction nextDirection = getNextDirection(droid);
            System.out.println("Direction: " + nextDirection);
            if (nextDirection != null) {
                droid.move(nextDirection);
                currentPos = droid.getCurrentCoordinates();
            }
        }

        System.out.println("Droid path:");
        while (!droid.path.isEmpty()) {
            Coordinates coords = droid.path.pop();
            System.out.println("Maze location: " + coords);
        }
    }

    private static Direction getNextDirection(Droid droid) {
        Content[] adjacencyContents = droid.scanAdjLocation();
        Direction[] directions = new Direction[4];
        for (int i = 0; i < 4; i++) {
            directions[i] = Direction.values()[i];
            System.out.println(directions[i]);
        }

        // If no end or unvisited cell, move towards a visited cell
        for (Direction direction : Direction.values()) {
            if (adjacencyContents[direction.ordinal()] != Content.BLOCK
                    && adjacencyContents[direction.ordinal()] != Content.NA && !droid.checkVisited(direction)) {
                return direction;
            }
        }
        // If all adjacent cells are blocks or out of bounds, return null
        return null;
    } */
