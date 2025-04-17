package src.map.utilities;

import src.map.checker.GameCheck;
import src.map.checker.LevelChecker.LevelCheckFacade;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapLoader {
    private MapFileReader fileReader;
    public MapLoader() {
        this.fileReader = new XMLFileReader();
    }

    public String loadMap(String filename){
        // check folder validity
        boolean pass = GameCheck.check(filename.split("/")[0]);
        if (pass){ // pass game check
            String board = fileReader.readMapFile(filename);
            boolean passLevel = LevelCheckFacade.getInstance(board).check(filename);
            if (passLevel){ // pass level check
                return board;
            } else {
                return "Failed level checking";
            }
        } else {
            return "Failed game checking";
        }
    }

    public ArrayList<String> loadMaps(String filename){
        boolean pass = GameCheck.check(filename);
        ArrayList<String> boards = new ArrayList<>();
        if (!pass){ // game check not pass
            return boards;
        }
        File directory = new File(filename);
        File[] files = directory.listFiles();
        // sort the levels
        Arrays.sort(files, Comparator.comparing(File::getName));
        for (File file: files){
            Pattern pattern = Pattern.compile("^(\\d+).*\\.xml");
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()){ // only files that have specific format should be opened, others should be ignored
                String board = fileReader.readMapFile(filename + "/" + file.getName());
                boolean passLevel = LevelCheckFacade.getInstance(board).check(filename + "/" + file.getName());
                if (passLevel){
                    boards.add(board);
                } else {
                    boards.add("Failed level checking");
                    boards.add(filename + "/" + file.getName());
                    return boards;
                }
            }
        }
        return boards;
    }
}
