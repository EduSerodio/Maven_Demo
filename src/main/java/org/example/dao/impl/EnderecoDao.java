package org.example.dao.impl;
import org.apache.log4j.Logger;
import org.example.dao.IDao;
import org.example.model.Endereco;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.example.config.ConfiguracaoJDBC;
public class EnderecoDao implements IDao<Endereco> {

    //INSTANCIANDO A CONFIGURAÇÃO DO BANCO "H2"
    private ConfiguracaoJDBC configuracaoJDBC;

    //DECLARANDO O LOGGER
    private final static Logger logger = Logger.getLogger(EnderecoDao.class);

    //INICIANDO UMA NOVA CONEXÃO DA CLASSE ENDEREÇO COM O BANCO
    public EnderecoDao() {
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA SALVAR'
    @Override
    public Endereco salvar(Endereco endereco) {
        logger.debug("Salvando novo endereço: " + endereco.toString());
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = String.format("INSERT INTO endereco (rua, numero, cidade, bairro) VALUES " +
                "('%s', '%s', '%s', '%s')", endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getBairro());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                endereco.setId(generatedKeys.getInt(1));
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endereco;
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA BUSCAR TODOS OS ENDEREÇOS
    @Override
    public List<Endereco> buscarTodos() {
        logger.debug("Buscando enderecos cadastrados...");
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = "SELECT * FROM endereco";
        List<Endereco> enderecos = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                enderecos.add(criarEndereco(resultSet));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enderecos;
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA EXCLUIR O ENDEREÇO PELO ID
    @Override
    public void excluir(int id) {
        logger.debug("Excluindo endereco com id: " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = String.format("DELETE FROM endereco WHERE id = '%s'", id);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA BUSCAR ENDEREÇO POR ID
    @Override
    public Endereco buscarPorId(int id) {
        logger.debug("Buscando endereco com id: " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = String.format("SELECT * FROM endereco WHERE id = '%s'", id);
        Endereco endereco = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                endereco = criarEndereco(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endereco;
    }

    // MÉTODO CRIADO PARA CRIAR ENDEREÇOS PARA ADICIONAR NO ARRAYLIST
    public Endereco criarEndereco(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String rua = resultSet.getString("rua");
        String numero = resultSet.getString("numero");
        String cidade = resultSet.getString("cidade");
        String bairro = resultSet.getString("bairro");
        Endereco endereco = new Endereco(id, rua, numero, cidade, bairro);
        return endereco;
    }

}
