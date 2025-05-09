package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.database.InicializadorBanco;
import pucgo.poobd.aula06052025.model.ItemPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemPedidoDAO implements IItemPedidoDAO {
    private final Connection conexao;
    private IProdutoDAO produtoDAO;

    public ItemPedidoDAO(Connection conexao) {
        this.conexao = conexao;
        this.produtoDAO = InicializadorBanco.getProdutoDAO();
    }

    @Override
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS itens_pedido (" +
                "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
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
            ps.execute();
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

    @Override
    public List<ItemPedido> buscarPorIDPedido(int idPedido) {
        String sql = "SELECT * FROM itens_pedido WHERE pedido = ?";
        List<ItemPedido> itens = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setId(rs.getInt("id"));
                itemPedido.setPedido(rs.getInt("pedido"));
                itemPedido.setProduto(produtoDAO.buscarPorID(rs.getInt("produto")).orElse(null));
                itemPedido.setQuantidade(rs.getInt("quantidade"));
                itens.add(itemPedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itens;
    }
}
