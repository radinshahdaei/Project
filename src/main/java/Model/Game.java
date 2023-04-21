package Model;

import java.util.ArrayList;

public class Game {
    private Map map;
    private User currentPlayer;
    private ArrayList<Government> governments = new ArrayList<Government>();
    private int turn;
    public static int currentX;
    public static int currentY;
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
