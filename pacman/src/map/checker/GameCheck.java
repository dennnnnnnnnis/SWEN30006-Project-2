package src.map.checker;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameCheck {

    public static boolean check(String dirName){
        File folder = new File(dirName);
        if (!folder.isDirectory()){  // whether the folder exists
            System.out.println("[Game folder name not exists!]");
            return false;
        }

        if (folder.listFiles().length == 0){ // no files in the folder
            System.out.println("[" + dirName + "- no maps found]");
            return false;
        }

        // checking duplicate levels, number can be above 10
        // checking formats of the files as well
        ArrayList<Integer> levelNumbers = new ArrayList<>();
        for (File file : folder.listFiles()) {
            Pattern pattern = Pattern.compile("^(\\d+).*\\.xml");
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()) {
                int level = Integer.parseInt(matcher.group(1));
                if (levelNumbers.contains(level)) {
                    System.out.print("[" + dirName + "- multiple maps at same level: ");
                    for (File f: folder.listFiles()){
                        matcher = pattern.matcher(f.getName());
                        if (matcher.find()){
                            if (Integer.parseInt(matcher.group(1)) == level){
                                System.out.print(f.getName() + "; ");
                            }
                        }
                    }
                    System.out.println("]");
                    return false;
                }
                levelNumbers.add(level);
            }
        }


        if (levelNumbers.isEmpty()){
            System.out.println("[" + dirName + "- no maps found]");
            return false;
        }

        return true;
    }
}
