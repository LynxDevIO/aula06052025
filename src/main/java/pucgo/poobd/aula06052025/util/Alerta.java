package pucgo.poobd.aula06052025.util;

import javafx.scene.control.Alert;

import javax.swing.*;

public final class Alerta {
    public static void confirmacao(String conteudo) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmação");
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }

    public static void erro(String conteudo) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erro");
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }

    public static void erroSwing(String conteudo) {
        JOptionPane.showMessageDialog(null, conteudo);
    }

    public static void informacao(String conteudo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informação");
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }

    public static void atencao(String conteudo) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Atenção");
        a.setHeaderText(null);
        a.setContentText(conteudo);
        a.showAndWait();
    }
}
