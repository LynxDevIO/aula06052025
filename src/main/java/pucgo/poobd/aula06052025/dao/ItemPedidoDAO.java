package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.ItemPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class ItemPedidoDAO implements IItemPedidoDAO {
    private final Connection conexao;

    public ItemPedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS itens_pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pedido INTEGER NOT NULL, " +
                "produto INTEGER NOT NULL, " +
                "quantidade INTEGER NOT NULL, " +
                "FOREIGN KEY (pedido) REFERENCES pedidos(id) ON DELETE CASCADE," +
                "FOREIGN KEY (produto) REFERENCES produtos(id) ON DELETE CASCADE" +
                ")";
        try (Statement st = conexao.createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inserir(ItemPedido itemPedido) {
        String sql = "INSERT INTO itens_pedido (pedido, produto, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, itemPedido.getPedido());
            ps.setInt(2, itemPedido.getProduto().getId());
            ps.setInt(3, itemPedido.getQuantidade());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alterarPorID(int id, ItemPedido itemPedido) {

    }

    @Override
    public void excluirPorID(int id) {

    }

    @Override
    public Optional<ItemPedido> buscarPorID(int id) {
        return Optional.empty();
    }

    @Override
    public List<ItemPedido> buscarTodos() {
        return List.of();
    }
}
