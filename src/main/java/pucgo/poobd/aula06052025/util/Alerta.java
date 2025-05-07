package pucgo.poobd.aula06052025.util;

import javafx.scene.control.Alert;

import javax.swing.*;

public final class Alerta {
    public static void confirmacao(String conteudo) {
        Alert a =  new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }

    public static void erro(String conteudo) {
        Alert a =  new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }

    public static void erroSwing(String conteudo) {
        JOptionPane.showMessageDialog(null, conteudo);
    }

    public static void informacao(String conteudo) {
        Alert a =  new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }

    public static void aviso(String conteudo) {
        Alert a =  new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }
}
