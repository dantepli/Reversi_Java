package application;

import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

public class WinGameController {
    @FXML
    private AmbientLight lights;

    @FXML
    public void initialize() {
        PointLight light = new PointLight();
        light.setColor(Color.RED);
    }
}
