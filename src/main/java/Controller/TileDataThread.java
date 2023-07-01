package Controller;

import Model.Tile;
import javafx.animation.Transition;
import javafx.util.Duration;

public class TileDataThread extends Transition {
    private Tile tile;
    private boolean runner;

    public TileDataThread(Tile tile) {
        this.tile = tile;
        runner = true;

        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }


    public void setRunner(boolean runner) {
        this.runner = runner;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    protected void interpolate(double v) {
        if (v <= 0.25) {
            tile.updateDataPane();
        }
    }
}
