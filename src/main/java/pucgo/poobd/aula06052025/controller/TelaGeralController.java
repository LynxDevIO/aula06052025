package pucgo.poobd.aula06052025.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pucgo.poobd.aula06052025.view.TelaGeralView;
import pucgo.poobd.aula06052025.view.TelaPedidoView;

public class TelaGeralController {
    @FXML
    private Button botaoFazerPedidos, botaoVerPedidos, botaoSair;

    @FXML
    private void botaoFazerPedidosOA() {
        TelaPedidoView telaPedido = new TelaPedidoView();
        TelaGeralView.getStage().close();
        try {
            telaPedido.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoVerPedidosOA() {

    }

    @FXML
    private void botaoSairOA() {
        Platform.exit();
    }
}
