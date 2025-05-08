package pucgo.poobd.aula06052025;

import javafx.application.Application;
import pucgo.poobd.aula06052025.database.InicializadorBanco;
import pucgo.poobd.aula06052025.view.TelaSelecaoView;

public class Main {
    public static void main(String[] args) {
        if (InicializadorBanco.inicializarBanco()) {
            Application.launch(TelaSelecaoView.class);
        }
    }
}
