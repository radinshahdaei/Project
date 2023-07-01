import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileBurning {

    public ImageView building;

    public TileBurning(ImageView building) {
        this.building = building;
    }
    
    public ImageView burn() {
        double x = building.getX();
        double y = building.getY();
        double xx = building.getLayoutX();
        double yy = building.getLayoutY();
        double h = building.getFitHeight();
        double w = building.getFitWidth();

        Image image = new Image(TileBurning.class.getResource("/Images/flames-fire-png-1.png").toExternalForm());
        ImageView fire = new ImageView(image);
        fire.setX(x);
        fire.setY(y);
        fire.setLayoutX(xx);
        fire.setLayoutY(yy);

        return fire;
    }
}
