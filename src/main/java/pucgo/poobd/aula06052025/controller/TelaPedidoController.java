package pucgo.poobd.aula06052025.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pucgo.poobd.aula06052025.dao.*;
import pucgo.poobd.aula06052025.database.InicializadorBanco;
import pucgo.poobd.aula06052025.model.ItemPedido;
import pucgo.poobd.aula06052025.model.Pedido;
import pucgo.poobd.aula06052025.model.Produto;
import pucgo.poobd.aula06052025.util.Alerta;
import pucgo.poobd.aula06052025.view.TelaGeralView;
import pucgo.poobd.aula06052025.view.TelaPedidoView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

public class TelaPedidoController {
    @FXML
    private HBox hbox, hboxPedido, hboxObs;

    @FXML
    private Label total;

    @FXML
    private TextField observacao;

    @FXML
    private CheckBox pedidoConfirmado;

    @FXML
    private Button botaoInserir, botaoEnviarPedido, botaoVoltar;

    @FXML
    private ComboBox<Produto> escolherItem;

    @FXML
    private ListView<ItemPedido> listaPedido;

    private Spinner<Integer> quantidade;
    private List<Produto> produtos;
    private float valorTotalDoPedido = 0;
    private final String textoPadrao = "Total = R$ ";

    private final IPedidoDAO pedidoDAO = InicializadorBanco.getPedidoDAO();
    private final IProdutoDAO produtoDAO = InicializadorBanco.getProdutoDAO();
    private final IItemPedidoDAO itensPedidoDAO = InicializadorBanco.getPedidoItemDAO();

    @FXML
    private void initialize() {
        produtos = produtoDAO.buscarTodos();

        // criar a lista observável da ListView
        listaPedido.setItems(FXCollections.observableArrayList());

        // criar spinner
        quantidade = new Spinner<>();
        quantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        quantidade.setEditable(true);
        quantidade.setPrefWidth(60);

        // adicionar spinner ao vbox correto
        hbox.getChildren().add(quantidade);

        // conferir se há ou não produtos cadastrados
        if (produtos.isEmpty()) {
            hboxPedido.setDisable(true);
            hboxObs.setDisable(true);
            pedidoConfirmado.setDisable(true);

            Alerta.erro("Nenhum produto encontrado");
        } else {
            // popula o comboBox "escolherItem" com a lista de todos os produtos
            // e mostra o primeiro item da lista
            escolherItem.setItems(FXCollections.observableArrayList(
                    produtos
            ));
            escolherItem.setValue(produtos.getFirst());
        }

        // etc
        botaoEnviarPedido.setDisable(true);
        pedidoConfirmado.setOnAction(e -> {
            botaoEnviarPedido.setDisable(!pedidoConfirmado.isSelected());
        });
    }

    @FXML
    private void botaoInserirOA() {
        float valorArredondado;

        // criar item de pedido
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(escolherItem.getValue());
        itemPedido.setQuantidade(quantidade.getValue());

        // adicionar o item pedido à lista de pedidos
        listaPedido.getItems().add(itemPedido);

        // atualizar o valor total do pedido
        valorTotalDoPedido = (float) listaPedido.getItems().stream()
                .mapToDouble(d -> d.getQuantidade() * d.getProduto().getValor())
                .sum();

        valorArredondado = new BigDecimal(valorTotalDoPedido).setScale(2, RoundingMode.HALF_UP).floatValue();

        total.setText(textoPadrao + "R$ " + valorArredondado);

        // retornar à seleção padrão
        escolherItem.setValue(produtos.getFirst());
    }

    @FXML
    private void botaoEnviarPedidoOA() {
        if (!listaPedido.getItems().isEmpty()) {
            // Criar e configurar o pedido
            Pedido pedido = new Pedido();
            pedido.setItens(listaPedido.getItems());
            pedido.setValorTotal(valorTotalDoPedido);
            pedido.setObservacao(observacao.getText());

            // inserir o pedido
            int idPedido = pedidoDAO.inserir(pedido);

            // inserir itens do pedido
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(idPedido);
                itensPedidoDAO.inserir(item);
            }

            Alerta.aviso("Pedido enviado com sucesso");

            // reorganizar a interface após envio
            listaPedido.getItems().clear();
            valorTotalDoPedido = 0;
            total.setText(textoPadrao + "0.00");
            observacao.clear();
            pedidoConfirmado.setSelected(false);
            botaoEnviarPedido.setDisable(true);
        } else {
            Alerta.aviso("Lista de itens vazia!");
        }
    }

    @FXML
    private void botaoVoltarOA() {
        TelaGeralView telaGeral = new TelaGeralView();
        TelaPedidoView.getStage().close();
        try {
            telaGeral.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}