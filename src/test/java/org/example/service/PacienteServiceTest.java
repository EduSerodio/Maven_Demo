package org.example.service;
import org.example.dao.impl.EnderecoDao;
import org.example.dao.impl.PacienteDao;
import org.example.model.Endereco;
import org.example.model.Paciente;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Date;
import java.util.List;

@RunWith(JUnit4.class)
public class PacienteServiceTest {

    private static PacienteService pacienteService = new PacienteService(new PacienteDao(new EnderecoDao()));
    private EnderecoService enderecoService = new EnderecoService(new EnderecoDao());

    @BeforeAll
    public static void carregarDataSet() {
        Endereco endereco = new Endereco("Av. Avenida Eudorado", "444", "São Paulo", "Centro");
        Paciente p = pacienteService.salvar(new Paciente("Carlos", "Alberto", "88888888", new Date(), endereco));
        Endereco endereco1 = new Endereco("Av. Avenida do Forte", "333", "Porto Alegre", "Santana");
        Paciente p1 = pacienteService.salvar(new Paciente("Marcela", "Moura", "99999999", new Date(), endereco1));
    }

    @Test
    public void cadastrarEBuscarPacienteTest() {
        Endereco endereco = new Endereco("Av. São João", "123", "Rio de Janeiro", "Copacabana");
        Paciente p = pacienteService.salvar(new Paciente("Marcio", "Oliveira", "12345678", new Date(), endereco));
        Assert.assertNotNull(pacienteService.buscar(p.getId()));
    }

    @Test
    public void excluirPacienteTest() {
        pacienteService.excluir(3);
        Assert.assertTrue(pacienteService.buscar(3) == null);
    }


    @Test
    public void trazerTodos() {
        Endereco endereco = new Endereco("Av. Avenida Eudorado", "444", "São Paulo", "Centro");
        Paciente p = pacienteService.salvar(new Paciente("Carlos", "Alberto", "88888888", new Date(), endereco));
        Endereco endereco1 = new Endereco("Av. Avenida do Forte", "333", "Porto Alegre", "Santana");
        Paciente p1 = pacienteService.salvar(new Paciente("Marcela", "Moura", "99999999", new Date(), endereco1));

        List<Paciente> pacientes = pacienteService.buscarTodos();
        Assert.assertTrue(!pacientes.isEmpty());
        Assert.assertTrue(pacientes.size() == 2);
        System.out.println(pacientes);
    }

}
