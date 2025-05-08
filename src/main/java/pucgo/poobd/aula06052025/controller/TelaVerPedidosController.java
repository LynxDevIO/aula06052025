package pucgo.poobd.aula06052025.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pucgo.poobd.aula06052025.dao.IItemPedidoDAO;
import pucgo.poobd.aula06052025.dao.IPedidoDAO;
import pucgo.poobd.aula06052025.dao.IProdutoDAO;
import pucgo.poobd.aula06052025.database.InicializadorBanco;
import pucgo.poobd.aula06052025.model.ItemPedido;
import pucgo.poobd.aula06052025.model.Pedido;
import pucgo.poobd.aula06052025.util.Alerta;
import pucgo.poobd.aula06052025.view.PopupItensPedidoView;
import pucgo.poobd.aula06052025.view.TelaPrincipalOperadorView;
import pucgo.poobd.aula06052025.view.TelaVerPedidosView;

import java.util.List;
import java.util.stream.Collectors;

public class TelaVerPedidosController {
    @FXML
    private TableView<Pedido> tabelaPedidos;

    @FXML
    private TableColumn<Pedido, String> colID;

    @FXML
    private TableColumn<Pedido, String> colValorTotal;

    @FXML
    private TableColumn<Pedido, String> colObservacao;

    private final IItemPedidoDAO itemPedidoDAO;

    private List<Pedido> pedidos;
    private IPedidoDAO pedidoDAO;
    private IProdutoDAO produtoDao;

    private ContextMenu menuContexto;

    public TelaVerPedidosController() {
        this.itemPedidoDAO = InicializadorBanco.getItemPedidoDAO();
    }

    @FXML
    private void initialize() {
        pedidoDAO = InicializadorBanco.getPedidoDAO();
        produtoDao = InicializadorBanco.getProdutoDAO();
        pedidos = pedidoDAO.buscarTodos();

        if (pedidos.isEmpty()) {
            tabelaPedidos.setDisable(true);
            Alerta.aviso("Nenhum pedido encontrado!");
        } else if (pedidos.stream().noneMatch(Pedido::getStatus)) {
            tabelaPedidos.setDisable(true);
            Alerta.aviso("Nenhum pedido ativo!");
        } else {
            pedidos = pedidos.stream().filter(Pedido::getStatus).collect(Collectors.toList());

            // configurar colunas da tabela
            colID = new TableColumn<>("ID");
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));

            colValorTotal = new TableColumn<>("Total");
            // colValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
            // linha acima comentada, porque a implementação abaixo é mais customizável

            colValorTotal.setCellValueFactory(cellData -> {
                Pedido pedido = cellData.getValue();
                return new SimpleStringProperty(String.format("R$ %.2f", pedido.getValorTotal()));
            });

            colObservacao = new TableColumn<>("Observação");
            colObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));

            // configurar menu de contexto para os itens da tabela
            MenuItem mi = new MenuItem("Ver itens");
            MenuItem mi1 = new MenuItem("Baixar pedido");
            mi.setOnAction(_ -> {
                // ação ver pedidos
                Pedido pedidoSelecionado = tabelaPedidos.getSelectionModel().getSelectedItem();
                if (pedidoSelecionado != null) {
                    mostrarItensDoPedido(pedidoSelecionado);
                }
            });

            mi1.setOnAction(_ -> {
                // ação baixar pedido
                Pedido pedidoSelecionado = tabelaPedidos.getSelectionModel().getSelectedItem(); // pega o pedido, pois precisa do ID, mas ele não vem com os ITENS, pois o banco de dados não guarda os itens na tabela pedidos

                if (pedidoSelecionado != null) {
                    int idPedidoSelecionado = pedidoSelecionado.getId();

                    // alterar o status do pedido e atualizar a tabela
                    pedidoDAO.alterarStatusPorID(idPedidoSelecionado, false);
                    tabelaPedidos.getItems().remove(tabelaPedidos.getSelectionModel().getSelectedIndex());
                    tabelaPedidos.refresh();

                    Alerta.informacao("Pedido baixado com sucesso!");
                } else {
                    Alerta.erro("Erro ao processar pedido! Pedido não encontrado.");
                }
            });
            menuContexto = new ContextMenu();
            menuContexto.getItems().add(mi);
            menuContexto.getItems().add(mi1);
            tabelaPedidos.setContextMenu(menuContexto);

            // configurar rowfactor pra limitar a exibição do menu de contexto
            tabelaPedidos.setRowFactory(tv -> {
                TableRow<Pedido> row = new TableRow<>();
                row.setOnContextMenuRequested(event -> {
                    if (!row.isEmpty()) {
                        menuContexto.show(row, event.getScreenX(), event.getScreenY());
                    }
                });
                return row;
            });

            // itens da tabela e definição do tamanho das colunas
            tabelaPedidos.setItems(FXCollections.observableArrayList(pedidos));
            tabelaPedidos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

            // adicionar colunas na tabela
            tabelaPedidos.getColumns().add(colID);
            tabelaPedidos.getColumns().add(colValorTotal);
            tabelaPedidos.getColumns().add(colObservacao);
        }
    }
    @FXML
    private void botaoVoltarOA() {
        TelaPrincipalOperadorView telaPrincipalOperadorView = new TelaPrincipalOperadorView();
        try {
            telaPrincipalOperadorView.start(TelaVerPedidosView.getStage());
            TelaPrincipalOperadorView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void mostrarItensDoPedido(Pedido pedidoSelecionado) {
        List<ItemPedido> itens = itemPedidoDAO.buscarPorIDPedido(pedidoSelecionado.getId());
        PopupItensPedidoView popup = new PopupItensPedidoView();
        try {
            popup.setItens(itens);
            popup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
