package pucgo.poobd.aula06052025;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TelaPedidoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}