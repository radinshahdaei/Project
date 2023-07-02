package Client.View.Game;

import Client.Controller.GameMenuController;
import Client.Model.Game;
import Client.Model.Government;

import static Client.View.InputOutput.output;

public class GovernmentPlayingThread extends Thread{
    private volatile boolean runner;
    private boolean mainRun = true;
    @Override
    public void run() {
        while (mainRun) {
            System.out.println("another time");
            for (Government government : GameMenuController.game.getGovernments()) {
                runner = true;
                if (GameMenuController.checkAllGovernmentsDead()) {
                    GameMenuController.countScores();
                    mainRun = false;
                }
                if (government.isDead()) continue;
                GameMenu.numberOfTurns++;
                Game.currentGovernment = government;
                output("Currently " + government.getUser().getUsername() + " is playing");
                while (runner) {
                    Thread.onSpinWait();
                }
            }
        }
    }

    public void setRunner(boolean runner) {
        this.runner = runner;
    }

    public void setMainRun(boolean mainRun) {
        this.mainRun = mainRun;
    }
}
