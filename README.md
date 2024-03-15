# PathFinder
This is a project that given a maze with multiple levels, a droid needs to find its way out
# Maze Solver

A Java program that find a path for a droid through a maze by using heuristic approach

## Description

This project implements a maze solver using a droid that navigates through a 3D maze. 
The maze is represented as a collection of cells, each containing information about its contents (empty, block, portal, or end). 
The droid starts at the entrance of the maze and explores the maze until it reaches the end.

## Features

- Generates a random 3D maze with specified dimensions and depth
- Droid navigates the maze 
- Supports portals for moving between levels of the maze
- Prints the path taken by the droid while solving the maze

## Classes

- `Maze`: Represents the 3D maze and provides methods for maze generation and manipulation
- `Droid`: Represents the droid that solves the maze using DFS algorithm
- `Coordinates`: Represents the coordinates of a cell in the maze
- `DroidInterface`: Interface defining the methods that the droid should implement
