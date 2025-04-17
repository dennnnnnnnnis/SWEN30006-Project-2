package src.map.checker.LevelChecker;

import src.map.editor.Constants;

public class PortalCheck implements LevelCheck{

    public boolean check(String board, String filename){
        int portalW = 0;
        int portalY = 0;
        int portalC = 0;
        int portalD = 0;
        for (int i = 0; i < board.length(); i++){
            if (board.charAt(i) == 'w'){
                portalW ++;
            }
            if (board.charAt(i) == 'y'){
                portalY ++;
            }
            if (board.charAt(i) == 'c'){
                portalC ++;
            }
            if (board.charAt(i) == 'd'){
                portalD ++;
            }
        }

        if (portalW != 2 && portalW != 0){
            System.out.print("[Level " + filename + "- portal White count is not 2: ");
            for (int i = 0; i < Constants.MAP_HEIGHT; i++)
            {
                for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                    if (board.charAt(Constants.MAP_WIDTH * i + k) == 'w'){
                        System.out.print("(" + k + "," + i + "); ");
                    }
                }
            }
            System.out.println("]");
            return false;
        }
        if (portalY != 2 && portalY != 0){
            System.out.print("[Level " + filename + "- portal Yellow count is not 2: ");
            for (int i = 0; i < Constants.MAP_HEIGHT; i++)
            {
                for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                    if (board.charAt(Constants.MAP_WIDTH * i + k) == 'y'){
                        System.out.print("(" + k + "," + i + "); ");
                    }
                }
            }
            System.out.println("]");
            return false;
        }
        if (portalC != 2 && portalC != 0){
            System.out.print("[Level " + filename + "- portal Dark Gold count is not 2: ");
            for (int i = 0; i < Constants.MAP_HEIGHT; i++)
            {
                for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                    if (board.charAt(Constants.MAP_WIDTH * i + k) == 'c'){
                        System.out.print("(" + k + "," + i + "); ");
                    }
                }
            }
            System.out.println("]");
            return false;
        }
        if (portalD != 2 && portalD != 0){
            System.out.print("[Level " + filename + "- portal Dark Grey count is not 2: ");
            for (int i = 0; i < Constants.MAP_HEIGHT; i++)
            {
                for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                    if (board.charAt(Constants.MAP_WIDTH * i + k) == 'd'){
                        System.out.print("(" + k + "," + i + "); ");
                    }
                }
            }
            System.out.println("]");
            return false;
        }
        return true;
    }
}
