package src.map.checker.LevelChecker;

public class LevelCheckFacade {
    private String board;
    private LevelCheck levelCheck;

    private LevelCheckFacade(String board){
        this.board = board;
    }

    public static LevelCheckFacade getInstance(String board) {
        return new LevelCheckFacade(board);
    }

    public boolean check(String filename){
        boolean pass1;
        levelCheck = new PacManCheck();
        pass1 = levelCheck.check(board, filename);

        boolean pass2;
        levelCheck = new PortalCheck();
        pass2 = levelCheck.check(board, filename);

        boolean pass3;
        levelCheck = new ItemCheck();
        pass3 = levelCheck.check(board, filename);

        boolean pass4;
        levelCheck = new AccessCheck();
        pass4 = levelCheck.check(board, filename);

        return pass1 && pass2 && pass3 && pass4;
    }
}
