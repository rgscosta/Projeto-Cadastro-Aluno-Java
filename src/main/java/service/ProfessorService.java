package service;

import domain.Professor;
import repository.ProfessorRepository;

import java.util.List;

public class ProfessorService {

    private static final ProfessorService instance = new ProfessorService();
    private final ProfessorRepository repository = new ProfessorRepository();

    private ProfessorService() {}

    public static ProfessorService getInstance() {
        return instance;
    }

    public void cadastrarProfessor(String nome) {
        repository.adicionar(new Professor(nome));
    }

    public List<Professor> listarProfessores() {
        return repository.listar();
    }

    public Professor buscarPorMatricula(int matricula) {
        return repository.buscarPorMatricula(matricula);
    }

    public boolean excluirProfessor(int matricula) {
        return repository.excluir(matricula);
    }
}
