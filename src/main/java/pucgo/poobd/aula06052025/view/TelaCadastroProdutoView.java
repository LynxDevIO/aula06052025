package pucgo.poobd.aula06052025.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaCadastroProdutoView extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pucgo/poobd/aula06052025/telas/operador/tela-cadastro-produto.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 470, 260);
        stage.setTitle("VIRTORANTE");
        stage.setScene(scene);
        setStage(stage);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        TelaCadastroProdutoView.stage = stage;
    }
}
