package Model;

import java.util.ArrayList;

public class Game {
    private Map map;
    private static ArrayList<Government> governments = new ArrayList<Government>();
    private int turn;
    public static int currentX;
    public static int currentY;
    public static Government currentGovernment;

    public static Government getGovernmentByUser(User owner) {
        for (Government government : governments) {
            if (government.getUser().equals(owner)) return government;
        }
        return null;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public void setGovernments(ArrayList<Government> governments) {
        this.governments = governments;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

}
