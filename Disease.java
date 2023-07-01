import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Disease {

    public ImageView building;

    public Disease(ImageView building) {
        this.building = building;
    }

    public ImageView add() {
        double x = building.getX();
        double y = building.getY();
        double xx = building.getLayoutX();
        double yy = building.getLayoutY();
        double h = building.getFitHeight();
        double w = building.getFitWidth();

        Image image = new Image(TileBurning.class.getResource("/Images/disease.png").toExternalForm());
        ImageView disease = new ImageView(image);
        disease.setX(x);
        disease.setY(y);
        disease.setLayoutX(xx);
        disease.setLayoutY(yy);

        return disease;
    }
}
