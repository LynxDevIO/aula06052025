package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class PedidoDAO implements IPedidoDAO {
    private final Connection conexao;

    public PedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS pedidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "valor NUMERIC CHECK (valor = ROUND(valor, 2))," +
                "observacao TEXT" +
                ")";
        try (Statement st = conexao.createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int inserir(Pedido pedido) {
        String sql = "INSERT INTO pedidos (valor, observacao) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            BigDecimal valorArredondado = new BigDecimal(String.valueOf(pedido.getValorTotal()))
                    .setScale(2, RoundingMode.HALF_UP);
            
            ps.setBigDecimal(1, valorArredondado);
            ps.setString(2, pedido.getObservacao());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    @Override
    public void alterarPorID(int id, Pedido pedido) {

    }

    @Override
    public void excluirPorID(int id) {

    }

    @Override
    public Optional<Pedido> buscarPorID(int id) {
        return Optional.empty();
    }

    @Override
    public List<Pedido> buscarTodos() {
        return List.of();
    }
}
