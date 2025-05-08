package pucgo.poobd.aula06052025.controller;

import javafx.fxml.FXML;
import pucgo.poobd.aula06052025.view.TelaCadastroProdutoView;
import pucgo.poobd.aula06052025.view.TelaPrincipalOperadorView;
import pucgo.poobd.aula06052025.view.TelaSelecaoView;
import pucgo.poobd.aula06052025.view.TelaVerPedidosView;

public class TelaPrincipalOperadorController {
    @FXML
    private void botaoVerPedidosOA() {
        // abrir tela de visualização dos pedidos
        TelaVerPedidosView telaVerPedidosView = new TelaVerPedidosView();
        try {
            telaVerPedidosView.start(TelaPrincipalOperadorView.getStage());
            TelaVerPedidosView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoCadastrarProdutoOA() {
        TelaCadastroProdutoView telaCadastroProdutoView = new TelaCadastroProdutoView();
        try {
            telaCadastroProdutoView.start(TelaPrincipalOperadorView.getStage());
            TelaCadastroProdutoView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoVoltarOA() {
        TelaSelecaoView telaSelecaoView = new TelaSelecaoView();
        try {
            telaSelecaoView.start(TelaPrincipalOperadorView.getStage());
            TelaSelecaoView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
