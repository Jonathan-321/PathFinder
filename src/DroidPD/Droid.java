package DroidPD;

import java.util.Stack;

import mazePD.Coordinates;
import mazePD.DroidInterface;
import mazePD.Maze;
import mazePD.Maze.Content;
import mazePD.Maze.Direction;
import java.util.ArrayList;


public class Droid implements DroidInterface{
    // properties 
    private String name;
    private Maze maze;
    public Stack<Coordinates> path;
    private ArrayList<Coordinates>visitedCoordinates = new ArrayList<>();


    // constructor
    public Droid(String name, Maze maze) {
        this.name = name;
        this.maze = maze;
        this.path = new Stack<>();
    }

    // getters and setters

    public String getName() {
        return name;
    }

    
    public Stack<Coordinates> getPath() {
        return path;
    }

    public void setPath(Stack<Coordinates> path) {
        this.path = path;
    }

    // methods 
    // enterMaze method that calls the maze's enterMaze method and 
    //pushes the returned coordinates onto the path stack
    // 

    public void enterMaze() { 
        Coordinates start = maze.enterMaze(this);
        visitedCoordinates.add(start);
        path.push(start);
    }


    // move method that calls the maze's move method and pushes the
    // returned coordinates onto the path stack

     public void solveMaize(){

        // while we have not reached then end 
        while (!checkLocation()) {
            System.out.println("CurrentLocation: " + maze.getCurrentCoordinates(this));

            printAdjLoc();
            isItOkToMove();
            System.out.println();
        }
    }
    // This method just scansThe Adjacent Location and prints overall what's around it 
    // if there's either a block , empty spot or block in either direction or there's just no
    // pathways to be able to move to the direction 


    // this is a quick function that iterated through all the the adjacent location and then 
    // prints them 
    public void printAdjLoc(){
        for(Content c: scanAdjLocation()){
            System.out.print(c + " ");
        }
        System.out.print("\n");
    }



    // 
     public Boolean checkLocation(){
        if (maze.scanCurLoc(this) == Content.END) {
            return true;
        }
        else if (maze.scanCurLoc(this)== Content.PORTAL_DN) {
            maze.usePortal(this, Direction.D90);
            visitedCoordinates.clear();
            return false;
        }
        else{
            return false;
        }
     }


    public void isItOkToMove(){

        // can_move shall indicate whether its possible to move to a new location or not
        // if can_move is true it means the program can proceed to move to new location. If false, it signals
        // that the movement is not possible 


        // boolean desireToMove is a flag that shall be used to check if we need to move immediately 
        // acting like a priority gate , if its true 
        boolean can_move= false;
        boolean desireToMove= false;
        int directionNumber =0;
        int coordinatesNumber =0;
        int nextLocation =0;

        for (Content locationContent : scanAdjLocation()) {
            if (locationContent == Content.PORTAL_DN || locationContent == Content.PORTAL_UP || locationContent == Content.EMPTY || locationContent == Content.END) {

                // Since we have 3 levels of the maze if we have a bottom portal or reach the
                // end of the maze, we want to move there immediately as that would give us the shortest path  
                if (locationContent == Content.PORTAL_DN || locationContent == Content.END) {
                    move(directionforInt(directionNumber));
                    can_move = true;
                    desireToMove = true;
                    System.out.println(maze.getCurrentCoordinates(this));
                    break;
                }

                // we want to on to new coordinates only once we haven't visited the Node / coordinates 
                // before we can move from there 

                // we just don't need to say move up or down when we have ways to go there
                // we also want to check whether or not we visited the coordinates  in a specific direction 
                // if we have not visited them , we then move to that location 
                else if(!ifVisited(coordinatesForInt(coordinatesNumber))){
                    nextLocation=  coordinatesNumber;
                    can_move = true;
                }
            }
            directionNumber++;
            coordinatesNumber++;
        }


        // from the above checks once we have figured that we can move, 
        // but there isn't a strong desire to move, we move to the other locations
        // meaning content.UP, content.DOWN, content. 


        if (can_move && !desireToMove) {
            move(directionforInt(nextLocation));
            System.out.println(maze.getCurrentCoordinates(this));;
            
        }
        // if we hit a dead-end for some reason, we'll 
        else if(!can_move){
            backtrack();
            System.out.println(maze.getCurrentCoordinates(this));
        }
    }



    /* 
     * The wat this function works is that it pops the top of the stack and compares it with the last 
     and position for instance if we're standing at the position (3,2) and our previous position is at (2,2)
     and we've hit a dead end , we subtract the current position from our last position and then use that as an indicator of 
     where we want to move. 
     */
    public void backtrack() {
        Coordinates lastPosition = path.pop();
        int dx = maze.getCurrentCoordinates(this).getX() - lastPosition.getX();
        int dy = maze.getCurrentCoordinates(this).getY() - lastPosition.getY();  
    
        visitedCoordinates.add(maze.getCurrentCoordinates(this));
    
        if (dx < 0) {
            maze.move(this, Direction.D90); // Move right
        } else if (dx > 0) {
            maze.move(this, Direction.D270); // Move left
        } else if (dy < 0) {
            maze.move(this, Direction.D180); // Move down
        } else if (dy > 0) {
            maze.move(this, Direction.D00); // Move up
        }
    }

// check whether a node/ cell is visited using the array list of coordinates 
// we pass in the coordinates and then since we check the coordinates of the node against every other node 
// in the visited Node array , if we already visited it we return false, else we return true
    public boolean ifVisited(Coordinates coordinates){
        for (Coordinates visited : visitedCoordinates) {
            if (visited.getX() == coordinates.getX() && visited.getY() == coordinates.getY()) {
                return true;
            }
        }
        return false;
    }


    // the move method calls the directions and then pushed that to the path stack 
    // and then moves the droid into the direction 
    // finally mark the droid current directions as visited 

    public void move(Direction direction) {

        path.push(maze.getCurrentCoordinates(this));
        maze.move(this, direction); 

      
        // why in this order , we mark it as visited because we're currently there now 
        visitedCoordinates.add(this.getCurrentCoordinates());
        // call maze's move method to get the next coordinates
        
    }

    // moveThroughPortal method that calls the maze's moveThroughPortal method
    // and pushes the returned coordinates onto the path stack

   public Coordinates usePortal(Direction direction){
    return maze.usePortal(this, direction);
   }

   // scanAdjLocation method that calls the maze's scanAdjLocation method
    // and returns the content of the adjacent location in the given direction
    // of the current location

    public Content[] scanAdjLocation() {
        return maze.scanAdjLoc(this);
    }

    // ScanCurLocation method that calls the maze's scanCurLocation method
    // and returns the content of the current location

    public Content scanCurLocation() {
        return maze.scanCurLoc(this);
    } 

    // getMazeDim() method that calls the maze's getMazeDim method and returns
    // the dimension of the maze

    public int getMazeDim() {
        return maze.getMazeDim();
    }

    // getMazeDepth() method that calls the maze's getMazeDepth method and returns
    // the depth of the maze

    public int getMazeDepth() {
        return maze.getMazeDepth();
    }

    public Coordinates getCurrentCoordinates(){
        return maze.getCurrentCoordinates(this);
    }


    public Direction directionforInt(Integer number) {
        switch (number) {
            case 0:
                return Direction.D00; // this is assumed to be down 
            case 1:
                return Direction.D90;// THIS IS Right
            case 2:
                return Direction.D180; // THIS IS Down
            case 3:
                return Direction.D270; // This is LEFT
            default:
                return Direction.UP; // 
        }
    }

    public Coordinates coordinatesForInt(Integer n){
        switch (n) {
            case 0:     // case 0 moves the Y coordinate down by 1 

            return new Coordinates(this.maze.getCurrentCoordinates(this).getX(),this.maze.getCurrentCoordinates(this).getY()-1,this.maze.getCurrentCoordinates(this).getZ());
            case 1:     // moves the X coordinate up by 1(right )

            return new Coordinates(this.maze.getCurrentCoordinates(this).getX()+1, this.maze.getCurrentCoordinates(this).getY(), this.maze.getCurrentCoordinates(this).getZ());

            case 2:    // moves the Y coordinates up by 1 
            return new Coordinates(this.maze.getCurrentCoordinates(this).getX(), this.maze.getCurrentCoordinates(this).getY()+1, this.maze.getCurrentCoordinates(this).getZ());

            case 3:    // moves the X coordinates down by 1 (left)
            return  new Coordinates(this.maze.getCurrentCoordinates(this).getX()-1, this.maze.getCurrentCoordinates(this).getY(), this.maze.getCurrentCoordinates(this).getZ());
        
        }
        return null;
    }

}


