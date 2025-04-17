// PacGrid.java
package src;

import ch.aplu.jgamegrid.*;

import java.util.Arrays;

public class PacManGameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private int[][] mazeArray;

  public PacManGameGrid(int nbHorzCells, int nbVertCells, String maze)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new int[nbVertCells][nbHorzCells];

    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        int value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }

  public int getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }
  private int toInt(char c) {
    if (c == 'x') // wall
      return 0;
    if (c == 'p') // path
      return 2;
    if (c == '.') // pill
      return 1;
    if (c == 'g') // gold
      return 3;
    if (c == 'i') // ice
      return 4;
    if (c == 'a') // pacman
      return 5;
    if (c == 'r') // troll
      return 6;
    if (c == 't') // tx5
      return 7;
    if (c == 'w') // whiteTile
      return 8;
    if (c == 'y') // yellowTile
      return 9;
    if (c == 'c') // darkGoldTile
      return 10;
    if (c == 'd') // darkGrayTile
      return 11;
    return -1;
  }

  public int[][] getMazeArray() {
    return mazeArray;
  }
}
