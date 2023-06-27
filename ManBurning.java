import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ManBurning extends Transition {

    public ImageView man;

    public int last = -1;
    public double timing , imageNumber = 312;
    public ManBurning(ImageView man , double seconds) {
        this.man = man;
        this.setCycleDuration(Duration.millis(1000 * seconds));
        this.setCycleCount(-1);
        this.timing = (double)((seconds * 1000) / imageNumber) / seconds;
    }
    @Override
    protected void interpolate(double frac) {
        double n = frac / timing;
        int number = (int)n;
        if(number >= 312) this.stop();
        if(number != last) {
            Image image = new Image(ManBurning.class.getResource("/Images/NPCs _ Soldiers/Animals/body_man_burning.gm1/0_0img" + String.valueOf(number) + ".png").toExternalForm());
            this.man.setImage(image);
            last = number;
        }
    }
}
