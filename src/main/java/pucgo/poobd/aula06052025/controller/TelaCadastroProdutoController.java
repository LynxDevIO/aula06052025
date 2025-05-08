package pucgo.poobd.aula06052025.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import pucgo.poobd.aula06052025.dao.IProdutoDAO;
import pucgo.poobd.aula06052025.database.InicializadorBanco;
import pucgo.poobd.aula06052025.model.Produto;
import pucgo.poobd.aula06052025.util.Alerta;
import pucgo.poobd.aula06052025.view.TelaCadastroProdutoView;
import pucgo.poobd.aula06052025.view.TelaPrincipalOperadorView;

import java.util.function.UnaryOperator;

public class TelaCadastroProdutoController {
    @FXML
    private TextField nome;

    @FXML
    private TextField preco;

    @FXML
    private TextField estoque;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private CheckBox confirmacao;

    private final IProdutoDAO produtoDAO;

    public TelaCadastroProdutoController() {
        produtoDAO = InicializadorBanco.getProdutoDAO();
    }

    @FXML
    private void initialize() {
        configurarFormatoDosCampos();

        botaoCadastrar.setDisable(true);
        confirmacao.setOnAction(_ -> botaoCadastrar.setDisable(!confirmacao.isSelected()));
    }

    @FXML
    private void botaoCadastrarOA() {
        if (conferirCampos()) {
            float valor = Float.parseFloat(preco.getText().trim().replace(",", "."));
            Produto produto = new Produto();

            produto.setNome(nome.getText().trim());
            produto.setValor(valor);
            produto.setEstoque(Integer.parseInt(estoque.getText().trim()));

            produtoDAO.inserir(produto);

            nome.requestFocus();
            nome.clear();
            preco.clear();
            estoque.clear();
            confirmacao.setSelected(false);
            botaoCadastrar.setDisable(true);

            Alerta.informacao("Produto cadastrado com sucesso!");
        } else {
            Alerta.aviso("Todos os campos devem ser preenchidos!");
        }
    }

    @FXML
    private void botaoVoltarOA() {
        TelaPrincipalOperadorView telaPrincipalOperadorView = new TelaPrincipalOperadorView();
        try {
            telaPrincipalOperadorView.start(TelaCadastroProdutoView.getStage());
            TelaPrincipalOperadorView.getStage().centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarFormatoDosCampos() {
        UnaryOperator<TextFormatter.Change> filtroReal = change -> {
            String text = change.getControlNewText();
            if (text.matches("\\d*,?\\d{0,2}")) {
                return change;
            }
            return null;
        };

        UnaryOperator<TextFormatter.Change> filtroInteiro = change -> {
            String text = change.getControlNewText();
            if (text.matches("\\d*")) {
                return change;
            }
            return null;
        };

        preco.setTextFormatter(new TextFormatter<>(filtroReal));
        estoque.setTextFormatter(new TextFormatter<>(filtroInteiro));
    }

    private boolean conferirCampos() {
        return !nome.getText().trim().isEmpty() || !preco.getText().trim().isEmpty() || !estoque.getText().trim().isEmpty();
    }
}
