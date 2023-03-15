package org.example.service;
import org.example.dao.IDao;
import org.example.model.Paciente;
import java.util.List;

public class PacienteService {
    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente salvar(Paciente paciente) {
        pacienteIDao.salvar(paciente);
        return paciente;
    }

    public Paciente buscar(int id) {
        return pacienteIDao.buscarPorId(id);
    }

    public List<Paciente> buscarTodos() {
        return pacienteIDao.buscarTodos();
    }

    public void excluir(Integer id) {
        pacienteIDao.excluir(id);
    }
}
