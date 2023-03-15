package org.example.dao.impl;
import org.apache.log4j.Logger;
import org.example.dao.IDao;
import org.example.model.Paciente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.example.config.ConfiguracaoJDBC;
import org.example.util.Util;
import org.example.model.Endereco;
public class PacienteDao implements IDao<Paciente> {

    //INSTANCIANDO A CONFIGURAÇÃO DO BANCO "H2"
    private ConfiguracaoJDBC configuracaoJDBC;

    //INSTANCIANDO A CLASSE ENDEREÇO
    private EnderecoDao enderecoDao;

    //DECLARANDO O LOGGER
    private final static Logger logger = Logger.getLogger(PacienteDao.class);

    //CONSTRUTOR, INSTANCIANDO UMA NOVA CONEXÃO NO BANCO
    public PacienteDao(EnderecoDao enderecoDao) {
        this.configuracaoJDBC = new ConfiguracaoJDBC();
        this.enderecoDao = enderecoDao;
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA SALVAR UM PACIENTE
    @Override
    public Paciente salvar(Paciente paciente) {
        logger.debug("Salvando novo paciente: " + paciente.toString());
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        paciente.setEndereco(enderecoDao.salvar(paciente.getEndereco()));
        String query = String.format("INSERT INTO paciente " +
                        "(nome, sobrenome, rg, data_nascimento, endereco_id)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s')",
                paciente.getNome(),
                paciente.getSobrenome(),
                paciente.getRg(),
                Util.dateToTimestamp(paciente.getDataNascimento()),
                paciente.getEndereco().getId());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                paciente.setId(generatedKeys.getInt(1));
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA BUSCAR UM PACIENTE PELO ID
    @Override
    public Paciente buscarPorId(int id) {
        logger.debug("Buscando paciente com id  : " + id);
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("SELECT ID, NOME, SOBRENOME, RG, DATA_NASCIMENTO, ENDERECO_ID FROM PACIENTE WHERE ID = '%s'", id);
        Paciente paciente = null;
        try {
            stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery(query);
            while (resultado.next()) {
                paciente = criarObjetoPaciente(resultado);
            }

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return paciente;
    }

    //// MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA EXCLUIR UM PACIENTE PELO ID
    @Override
    public void excluir(int id) {
        logger.debug("Excluindo paciente com id: " + id);
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("DELETE FROM paciente WHERE id = %s", id);
        try {
            stmt = conexao.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA BUSCAR TODOS OS PACIENTES
    @Override
    public List<Paciente> buscarTodos() {
        logger.debug("Buscando todos os pacientes");
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = "SELECT * FROM paciente";
        List<Paciente> pacientes = new ArrayList<>();
        try {
            stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery(query);
            while (resultado.next()) {
                pacientes.add(criarObjetoPaciente(resultado));
            }

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return pacientes;
    }

    // MÉTODO CRIADO PARA CRIAR PACIENTES PARA ADICIONAR NO ARRAYLIST
    private Paciente criarObjetoPaciente(ResultSet result) throws SQLException {
        Integer idPaciente = result.getInt("id");
        String nome = result.getString("nome");
        String sobrenome = result.getString("sobrenome");
        String rg = result.getString("rg");
        Date dataCadastro = result.getDate("data_nascimento");
        Endereco endereco = enderecoDao.buscarPorId(result.getInt("endereco_id"));
        Paciente paciente = new Paciente(idPaciente, nome, sobrenome, rg, dataCadastro, endereco);
        return paciente;
    }
}
