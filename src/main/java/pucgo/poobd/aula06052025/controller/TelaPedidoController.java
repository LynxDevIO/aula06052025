package pucgo.poobd.aula06052025.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pucgo.poobd.aula06052025.dao.*;
import pucgo.poobd.aula06052025.database.InicializadorBanco;
import pucgo.poobd.aula06052025.model.ItemPedido;
import pucgo.poobd.aula06052025.model.Pedido;
import pucgo.poobd.aula06052025.model.Produto;
import pucgo.poobd.aula06052025.util.Alerta;
import pucgo.poobd.aula06052025.view.TelaPedidoView;
import pucgo.poobd.aula06052025.view.TelaPrincipalClienteView;
import pucgo.poobd.aula06052025.view.TelaPrincipalOperadorView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private final IItemPedidoDAO itensPedidoDAO = InicializadorBanco.getItemPedidoDAO();

    @FXML
    private void initialize() {
        produtos = produtoDAO.buscarTodos();

        // criar a lista observável da ListView
        listaPedido.setItems(FXCollections.observableArrayList());

        configurarMenuContexto();

        // criar spinner
        quantidade = new Spinner<>();
        quantidade.setEditable(true);
        quantidade.setPrefWidth(60);

        // adicionar spinner ao vbox correto
        hbox.getChildren().add(quantidade);

        // conferir se há ou não produtos cadastrados
        if (produtos.isEmpty()) {
            hboxPedido.setDisable(true);
            hboxObs.setDisable(true);
            pedidoConfirmado.setDisable(true);

            Alerta.aviso("Nenhum produto encontrado.");
        } else {
            produtos = produtos.stream().filter(p -> p.getEstoque() > 0).collect(Collectors.toList());
            if (produtos.isEmpty()) { // somente produtos com estoque positivo!
                hboxPedido.setDisable(true);
                hboxObs.setDisable(true);
                pedidoConfirmado.setDisable(true);

                Alerta.aviso("Nenhum produto com estoque positivo.");
            } else {
                // popula o comboBox "escolherItem" com a lista de todos os produtos
                // e mostra o primeiro item da lista
                escolherItem.setItems(FXCollections.observableArrayList(
                        produtos
                ));
                escolherItem.setValue(produtos.getFirst());

                // define os limites padrão do spinner
                defineLimitesSpinner();

                // a cada seleção no ChoiceBox, o spinner ajusta seu limite superior
                escolherItem.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> {
                    defineLimitesSpinner();
                });
            }
        }

        // organizar componentes
        botaoEnviarPedido.setDisable(true);
        pedidoConfirmado.setOnAction(e -> {
            botaoEnviarPedido.setDisable(!pedidoConfirmado.isSelected());
        });
    }

    private void defineLimitesSpinner() {
        quantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, escolherItem.getValue().getEstoque(), 1));
    }

    @FXML
    private void botaoInserirOA() {
        // criar item de pedido
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(escolherItem.getValue());
        itemPedido.setQuantidade(quantidade.getValue());

        // adicionar o item pedido à lista de pedidos
        listaPedido.getItems().add(itemPedido);

        // atualizar o valor total do pedido
        atualizarValorTotalDoPedido();

        // retornar à seleção padrão
        escolherItem.setValue(produtos.getFirst());
    }

    @FXML
    private void botaoEnviarPedidoOA() {
        if (!listaPedido.getItems().isEmpty()) {
            // criar e configurar o pedido
            Pedido pedido = new Pedido();
            pedido.setItens(listaPedido.getItems());
            pedido.setValorTotal(valorTotalDoPedido);
            pedido.setObservacao(observacao.getText());
            pedido.setDataPedido(LocalDate.now());
            pedido.setStatus(true);

            // inserir o pedido
            int idPedido = pedidoDAO.inserir(pedido);

            // inserir itens do pedido
            pedido.getItens().forEach(item -> {
                item.setPedido(idPedido);
                itensPedidoDAO.inserir(item);
            });

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
        TelaPrincipalClienteView telaPrincipalClienteView = new TelaPrincipalClienteView();
        try {
            telaPrincipalClienteView.start(TelaPedidoView.getStage());
            TelaPrincipalClienteView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarMenuContexto() {
        ContextMenu menuContexto = new ContextMenu();
        MenuItem mi = new MenuItem("Remover");

        mi.setOnAction(_ -> {
            ItemPedido itemSelecionado = listaPedido.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                listaPedido.getItems().remove(itemSelecionado);
                atualizarValorTotalDoPedido();
            } else {
                Alerta.aviso("Nenhum item selecionado.");
            }
        });

        listaPedido.setContextMenu(menuContexto);

        // CÓDIGO PARCIALMENTE PRODUZIDO POR IA --
        // Adicionar listener para atualizar a visibilidade do menu de contexto
        listaPedido.getItems().addListener((ListChangeListener<ItemPedido>) _ -> {
            // MEU CÓDIGO --
            if (listaPedido.getItems().isEmpty()) {
                menuContexto.getItems().clear();
            } else {
                if (menuContexto.getItems().isEmpty()) {
                    menuContexto.getItems().add(mi);
                }
            }
            // -- FIM
        });
        // -- FIM
    }

    private void atualizarValorTotalDoPedido() {
        float valor = 0;
        valorTotalDoPedido = (float) listaPedido.getItems().stream()
                .mapToDouble(d -> d.getQuantidade() * d.getProduto().getValor())
                .sum();

        valor = new BigDecimal(valorTotalDoPedido).setScale(2, RoundingMode.HALF_UP).floatValue();

        total.setText(textoPadrao + valor);
    }
}