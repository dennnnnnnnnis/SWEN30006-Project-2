package src.move;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.PacActor;

public interface MovingStrategy {
    Location findNext(PacActor pacman, int[][] map, Game game);
}
