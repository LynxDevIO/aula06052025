package pucgo.poobd.aula06052025.controller;

import javafx.fxml.FXML;
import pucgo.poobd.aula06052025.view.TelaPedidoView;
import pucgo.poobd.aula06052025.view.TelaPrincipalClienteView;
import pucgo.poobd.aula06052025.view.TelaSelecaoView;

public class TelaPrincipalClienteController {
    @FXML
    private void botaoFazerPedidoOA() {
        TelaPedidoView telaPedido = new TelaPedidoView();
        try {
            telaPedido.start(TelaPrincipalClienteView.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoVoltarOA() {
        TelaSelecaoView telaSelecaoView = new TelaSelecaoView();
        try {
            telaSelecaoView.start(TelaPrincipalClienteView.getStage());
            TelaSelecaoView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
