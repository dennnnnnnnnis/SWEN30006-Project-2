package src.map.checker.LevelChecker;

import src.map.editor.Constants;

public class PacManCheck implements LevelCheck{
    public boolean check(String board, String filename){
        int count = 0;
        for (int i = 0; i < board.length(); i++){
            if (board.charAt(i) == 'a'){
                count += 1;
            }
        }

        if (count == 0){
            System.out.println("[Level " + filename + "- no start for PacMan]");
            return false;
        } else if (count > 1){
            System.out.print("[Level " + filename + "- more than one start for PacMan: ");
            for (int i = 0; i < Constants.MAP_HEIGHT; i++)
            {
                for (int k = 0; k < Constants.MAP_WIDTH; k++) {
                    if (board.charAt(Constants.MAP_WIDTH * i + k) == 'a'){
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
