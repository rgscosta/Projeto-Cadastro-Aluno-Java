package domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Materia {

    private static int nextMatricula = 1;
    private Integer matricula;
    private String nome;
    private Professor professor;
    private List<AlunoMateria> materias = new ArrayList<>();

    public Materia(String nome, Professor professor) {
        this.matricula = nextMatricula++;
        this.nome = nome;
        this.professor = professor;
    }

    public void adicionarMateria(Materia materia, NotaUnidade nota) {
        this.materias.add(new AlunoMateria(materia, nota));
    }

    @Override
    public String toString() {
        return "ID: " + matricula +
                " | Matéria: " + nome +
                " | Professor: " + professor.getNome();
    }

}
