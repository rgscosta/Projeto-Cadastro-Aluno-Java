package repository;

import domain.Materia;
import java.util.ArrayList;
import java.util.List;

public class MateriaRepository {

    private final List<Materia> materias = new ArrayList<>();

    public void adicionar(Materia materia) {
        materias.add(materia);
    }

    public List<Materia> listar() {
        return new ArrayList<>(materias);
    }

    public Materia buscarPorMatricula(int matricula) {
        return materias.stream()
                .filter(m -> m.getMatricula() == matricula)
                .findFirst()
                .orElse(null);
    }

    public boolean excluir(int matricula) {
        return materias.removeIf(m -> m.getMatricula() == matricula);
    }
}
