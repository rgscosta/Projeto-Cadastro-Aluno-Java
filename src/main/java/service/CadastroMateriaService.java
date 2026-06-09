package service;

import model.Materia;

import java.util.ArrayList;
import java.util.List;

public class CadastroMateriaService {

    private final List<Materia> materias;

    public CadastroMateriaService(List<Materia> materias) {
        this.materias = materias;
    }

    public boolean verificarMateriasCadastradas(List<Materia> materias) {
        for (Materia materia : materias) {
            if (materia.getNomeMateria().equalsIgnoreCase(materias.toString())) {
                return true;
            }
        }
        return false;
    }

    //Excluir o Aluno do cadastro
    public boolean excluirMateriasAluno(int matriculaMateria) {
        return materias.removeIf(materia -> materia.getMatriculaMateria() == matriculaMateria);
    }

    public void cadastrarMateriasAluno(Materia materia) {
        materias.add(materia);
    }

    public List<Materia> consultarMateriasAlunos() {
        return new ArrayList<>(materias);
    }

    public Materia consultarMateriaPorMatricula(int matriculaMateria) {
        for (Materia materia : materias) {
            if (materia.getMatriculaMateria().equals(matriculaMateria)) {
                return materia;
            }
        }
        return null;
    }

    public List<Materia> consultarMateriasPorNome(String nomeMateria) {
        List<Materia> materiasEncontradas = new ArrayList<>();
        for (Materia materia : materias) {
            if (materia.getNomeMateria().equalsIgnoreCase(nomeMateria)) {
                materiasEncontradas.add(materia);
            }
        }
        return materiasEncontradas;
    }

    public boolean alterarMateriasAluno(Materia materiaAlterada) {
        for (int i = 0; i < materias.size(); i++) {
            if (materias.get(i).getNomeMateria().equalsIgnoreCase(materiaAlterada.getNomeMateria())) {
                materias.set(i, materiaAlterada);
                return true;
            }
        }
        return false;
    }
}
