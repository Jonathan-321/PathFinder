
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