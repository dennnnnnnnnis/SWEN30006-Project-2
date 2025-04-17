package src;

import src.map.editor.Controller;

public class Driver {
    /**
     * Starting point
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        if (args.length > 0) {
            if (args[0].endsWith(".xml")){
                new Controller(0, args[0]);
            } else {
                new Controller(1, args[0]);
            }
        } else { //
            new Controller();
        }
    }
}
