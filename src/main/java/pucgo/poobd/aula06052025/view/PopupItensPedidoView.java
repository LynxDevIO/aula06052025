package pucgo.poobd.aula06052025.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pucgo.poobd.aula06052025.model.ItemPedido;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.List;

public class PopupItensPedidoView extends Application {
    private static Stage stage;
    private final TableView<ItemPedido> tabelaItens = new TableView<>();

    @Override
    public void start(Stage stage) throws IOException {
        PopupItensPedidoView.stage = stage;
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(10));

        tabelaItens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // configurar colunas
        TableColumn<ItemPedido, String> colItem = new TableColumn<>("Item");
        colItem.setCellValueFactory(cellData -> {
            ItemPedido item = cellData.getValue();
            return new SimpleStringProperty(item.getProduto().getNome());
        });

        TableColumn<ItemPedido, Integer> colQuantidade = new TableColumn<>("Quantidade");
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        tabelaItens.getColumns().add(colItem);
        tabelaItens.getColumns().add(colQuantidade);

        // obs: a tabela já é populada na sua criação

        // botão voltar
        HBox hboxBotao = new HBox();
        hboxBotao.setAlignment(Pos.CENTER_LEFT);
        Button botaoVoltar = new Button("Voltar");
        botaoVoltar.setOnAction(_ -> ((Stage) botaoVoltar.getScene().getWindow()).close());
        hboxBotao.getChildren().add(botaoVoltar);

        root.getChildren().addAll(tabelaItens, hboxBotao);

        // cria a cena
        Scene scene = new Scene(root, 400, 200);
        stage.setTitle("Itens do Pedido");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        PopupItensPedidoView.stage = stage;
    }

    public void setItens(List<ItemPedido> itens) {
        tabelaItens.setItems(FXCollections.observableArrayList(itens));
    }

    public void show() {
        stage.centerOnScreen();
        stage.setAlwaysOnTop(true);
        // stage.toFront();
        stage.show();
    }
}
