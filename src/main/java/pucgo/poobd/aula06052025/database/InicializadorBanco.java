package pucgo.poobd.aula06052025.database;

import pucgo.poobd.aula06052025.dao.*;
import pucgo.poobd.aula06052025.util.Alerta;

import java.sql.Connection;
import java.sql.SQLException;

public class InicializadorBanco {
    private static IPedidoDAO pedidoDAO;
    private static IProdutoDAO produtoDAO;
    private static IItemPedidoDAO pedidoItemDAO;

    public static boolean inicializarBanco() {
        Connection conexao = ConexaoBanco.getInstancia().getConexao(); // testado com Null, o alerta com Java Swing funciona ok

        try {
            if (conexao == null) {
                Alerta.erroSwing("Erro ao conecta-se com o banco de dados!");
                return false;
            } else if (conexao.isClosed()) {
                Alerta.erroSwing("Erro ao conecta-se com o banco de dados!");
                return false;
            } else {
                pedidoDAO = new PedidoDAO(conexao);
                produtoDAO = new ProdutoDAO(conexao);
                pedidoItemDAO = new ItemPedidoDAO(conexao);

                pedidoDAO.criarTabela();
                produtoDAO.criarTabela();
                pedidoItemDAO.criarTabela();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static IPedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public static IProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public static IItemPedidoDAO getItemPedidoDAO() {
        return pedidoItemDAO;
    }
}
