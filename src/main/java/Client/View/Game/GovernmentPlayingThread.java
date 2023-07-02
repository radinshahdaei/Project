package Client.View.Game;

import Client.Controller.GameMenuController;
import Client.Model.Game;
import Client.Model.Government;

import static Client.View.InputOutput.output;

public class GovernmentPlayingThread extends Thread{
    public volatile boolean run;
    public boolean mainRun = true;
    @Override
    public void run() {
        while (mainRun) {
            for (Government government : GameMenuController.game.getGovernments()) {
                run = true;
                if (GameMenuController.checkAllGovernmentsDead()) {
                    GameMenuController.countScores();
                    return;
                }
                if (government.isDead()) continue;
                GameMenu.numberOfTurns++;
                Game.currentGovernment = government;
                output("Currently " + government.getUser().getUsername() + " is playing");
                while (run) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void setMainRun(boolean mainRun) {
        this.mainRun = mainRun;
    }
}
