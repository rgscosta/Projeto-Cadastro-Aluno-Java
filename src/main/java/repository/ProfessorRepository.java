package repository;

import domain.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository {

    private final List<Professor> professores = new ArrayList<>();

    public void adicionar(Professor professor) {
        professores.add(professor);
    }

    public List<Professor> listar() {
        if (!professores.isEmpty()) {
            return professores;
        }
        return new ArrayList<>(professores);
    }

    public Professor buscarPorMatricula(int matricula) {
        return professores.stream()
                .filter(p -> p.getMatricula() == matricula)
                .findFirst()
                .orElse(null);
    }

    public boolean excluir(int matricula) {
        return professores.removeIf(p -> p.getMatricula() == matricula);
    }
}