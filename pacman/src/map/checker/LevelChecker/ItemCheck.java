package src.map.checker.LevelChecker;

public class ItemCheck implements LevelCheck{

    public boolean check(String board, String filename){
        int count = 0;
        for (int i = 0; i < board.length(); i++){
            if (board.charAt(i) == '.' || board.charAt(i) == 'p'){
                count++;
            }
        }

        if (count < 2){
            System.out.println("[Level " + filename + "- less than 2 Gold and Pill]");
            return false;
        }
        return true;
    }
}
