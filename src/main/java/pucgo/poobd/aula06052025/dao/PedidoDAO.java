package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
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
                "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "valor NUMERIC CHECK (valor = ROUND(valor, 2))," +
                "observacao TEXT, " +
                "data DATE NOT NULL, " +
                "status BOOLEAN NOT NULL" +
                ")";
        try (Statement st = conexao.createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int inserir(Pedido pedido) {
        String sql = "INSERT INTO pedidos (valor, observacao, data, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // arredondar valor total com BigDecimal, pois antes estava dando erro de decimal no CHECK da tabela
            BigDecimal valorArredondado = new BigDecimal(String.valueOf(pedido.getValorTotal()))
                    .setScale(2, RoundingMode.HALF_UP);
            
            ps.setBigDecimal(1, valorArredondado);
            ps.setString(2, pedido.getObservacao());
            ps.setDate(3, Date.valueOf(pedido.getDataPedido()));
            ps.setBoolean(4, pedido.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    @Override
    public void alterarPorID(int id, Pedido pedido) {
        String sql = "UPDATE pedidos SET valor = ?, observacao = ?, data = ?, status = ?, WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            // arredondar valor total com BigDecimal, pois antes estava dando erro de decimal no CHECK da tabela
            BigDecimal valorArredondado = new BigDecimal(String.valueOf(pedido.getValorTotal()))
                    .setScale(2, RoundingMode.HALF_UP);

            ps.setBigDecimal(1, valorArredondado);
            ps.setString(2, pedido.getObservacao());
            ps.setDate(3, Date.valueOf(pedido.getDataPedido()));
            ps.setBoolean(4, pedido.getStatus());
            ps.setInt(3, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluirPorID(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Pedido> buscarPorID(int id) {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        Optional<Pedido> pedido = Optional.empty();
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setValorTotal(rs.getFloat("valor"));
                p.setObservacao(rs.getString("observacao"));
                p.setDataPedido(rs.getDate("data").toLocalDate());
                p.setStatus(rs.getBoolean("status"));
                pedido = Optional.of(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedido> buscarTodos() {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> pedidos = new ArrayList<>();
        try (Statement st = conexao.createStatement()) {
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setValorTotal(rs.getFloat("valor"));
                pedido.setObservacao(rs.getString("observacao"));
                pedido.setDataPedido(rs.getDate("data").toLocalDate());
                pedido.setStatus(rs.getBoolean("status"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public void alterarStatusPorID(int id, boolean b) {
        String sql = "UPDATE pedidos SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setBoolean(1, b);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
