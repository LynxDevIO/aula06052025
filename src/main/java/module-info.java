module pucgo.poobd.aula06052025 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens pucgo.poobd.aula06052025 to javafx.fxml;
    exports pucgo.poobd.aula06052025;
    exports pucgo.poobd.aula06052025.view;
    opens pucgo.poobd.aula06052025.view to javafx.fxml;
    exports pucgo.poobd.aula06052025.controller;
    opens pucgo.poobd.aula06052025.controller to javafx.fxml;
}