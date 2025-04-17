package src.map.checker.LevelChecker;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.map.editor.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AccessCheck implements LevelCheck{

    private char[][] mazeArray = new char[Constants.MAP_HEIGHT][Constants.MAP_WIDTH];
    private ArrayList<Location> whitePortal = new ArrayList<>();
    private ArrayList<Location> yellowPortal = new ArrayList<>();
    private ArrayList<Location> grayPortal = new ArrayList<>();
    private ArrayList<Location> darkPortal = new ArrayList<>();
    public boolean check(String board, String filename){
        ArrayList<Location> visitedList = new ArrayList<>();
        Queue<Location> queue = new LinkedList<>();
        int totalItems = 0;

        // Copy structure into char array
        for (int i = 0; i < Constants.MAP_HEIGHT; i++) {
            for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                char value = board.charAt(Constants.MAP_WIDTH * i + k);
                mazeArray[i][k] = value;
            }
        }


        for (int i = 0; i < Constants.MAP_HEIGHT; i++) {
            for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                if (mazeArray[i][k] == 'g' || mazeArray[i][k] == '.'){
                    totalItems++; // count the total pills and golds
                }
                // looking for accessible items for pacman
                if (mazeArray[i][k] == 'a'){
                    Location location = new Location(k, i);
                    queue.add(location);
                }

                if (mazeArray[i][k] == 'w'){
                    Location location = new Location(k, i);
                    whitePortal.add(location);
                }
                if (mazeArray[i][k] == 'y'){
                    Location location = new Location(k, i);
                    yellowPortal.add(location);
                }
                if (mazeArray[i][k] == 'c'){
                    Location location = new Location(k, i);
                    darkPortal.add(location);
                }
                if (mazeArray[i][k] == 'd'){
                    Location location = new Location(k, i);
                    grayPortal.add(location);
                }
            }
        }

        // System.out.println(totalItems);

        // BFS search
        while (!queue.isEmpty()){
            Location curr = queue.poll();
            // if out of the game bound or is a wall, then continue
            if (curr.getX() < 0 || curr.getY() < 0 ||
                    curr.getX() >= Constants.MAP_WIDTH || curr.getY() >= Constants.MAP_HEIGHT ||
                    mazeArray[curr.getY()][curr.getX()] == 'x'){
                continue;
            }

            // check if it's gold or pill
            if (mazeArray[curr.getY()][curr.getX()] == 'g' || mazeArray[curr.getY()][curr.getX()] == '.'){
                totalItems--;
            }
            visitedList.add(curr); // mark the curr as visited

            // add all its neighbours if they are not visited and not in the queue
            Location right = new Location(curr.getX() + 1, curr.getY());
            right = jumpItem(right);
            if (!visitedList.contains(right) && !queue.contains(right)){
                queue.add(right);
            }

            Location left = new Location(curr.getX() - 1, curr.getY());
            left = jumpItem(left);
            if (!visitedList.contains(left) && !queue.contains(left)){
                queue.add(left);
            }

            Location up = new Location(curr.getX(), curr.getY() - 1);
            up = jumpItem(up);
            if (!visitedList.contains(up) && !queue.contains(up)){
                queue.add(up);
            }

            Location down = new Location(curr.getX(), curr.getY() + 1);
            down = jumpItem(down);
            if (!visitedList.contains(down) && !queue.contains(down)){
                queue.add(down);
            }
        }

        if (totalItems != 0){
            System.out.print("[Level " + filename + "- Gold not accessible: ");
            for (int i = 0; i < Constants.MAP_HEIGHT; i++)
            {
                for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                    char c = board.charAt(Constants.MAP_WIDTH * i + k);
                    if (c == 'g' || c == '.'){
                        Location location = new Location(k, i);
                        if (!visitedList.contains(location)){
                            System.out.print("(" + k + "," + i + "); ");
                        }
                    }
                }
            }
            System.out.println("]");
        }

        // System.out.println(totalItems == 0);
        return totalItems == 0;
    }

    private Location jumpItem(Location location){
        boolean isPortal = false;
        for (Location item : this.whitePortal){
            if (location.getX() == item.getX() && location.getY() == item.getY()) {
                isPortal = true;
            }
        }
        if (isPortal) {
            for (Location item : this.whitePortal) {
                if (location.getX() != item.getX() || location.getY() != item.getY()) {
                    location.x = item.getX();
                    location.y = item.getY();
                    break;
                }
            }
            return location;
        }

        for (Location item : this.grayPortal){
            if (location.getX() == item.getX() && location.getY() == item.getY()) {
                isPortal = true;
            }
        }
        if (isPortal) {
            for (Location item : this.grayPortal) {
                if (location.getX() != item.getX() || location.getY() != item.getY()) {
                    location.x = item.getX();
                    location.y = item.getY();
                    break;
                }
            }
            return location;
        }

        for (Location item : this.yellowPortal){
            if (location.getX() == item.getX() && location.getY() == item.getY()) {
                isPortal = true;
            }
        }
        if (isPortal) {
            for (Location item : this.yellowPortal) {
                if (location.getX() != item.getX() || location.getY() != item.getY()) {
                    location.x = item.getX();
                    location.y = item.getY();
                    break;
                }
            }
            return location;
        }

        for (Location item : this.darkPortal){
            if (location.getX() == item.getX() && location.getY() == item.getY()) {
                isPortal = true;
            }
        }
        if (isPortal) {
            for (Location item : this.darkPortal) {
                if (location.getX() != item.getX() || location.getY() != item.getY()) {
                    location.x = item.getX();
                    location.y = item.getY();
                    break;
                }
            }
            return location;
        }

        return location;
    }
}
