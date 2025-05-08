package pucgo.poobd.aula06052025.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import pucgo.poobd.aula06052025.view.TelaPrincipalClienteView;
import pucgo.poobd.aula06052025.view.TelaPrincipalOperadorView;
import pucgo.poobd.aula06052025.view.TelaSelecaoView;

public class TelaSelecaoController {
    @FXML
    private void botaoClienteOA() {
        TelaPrincipalClienteView telaPrincipalClienteView = new TelaPrincipalClienteView();
        try {
            telaPrincipalClienteView.start(TelaSelecaoView.getStage());
            TelaPrincipalClienteView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoOperadorOA() {
        TelaPrincipalOperadorView telaPrincipalOperadorView = new TelaPrincipalOperadorView();
        try {
            telaPrincipalOperadorView.start(TelaSelecaoView.getStage());
            TelaPrincipalOperadorView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoSairOA() {
        Platform.exit();
    }
}
