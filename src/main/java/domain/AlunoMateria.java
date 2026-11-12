package domain;

import lombok.Data;

@Data
public class AlunoMateria {

    private Materia materia;
    private NotaUnidade nota;

    public AlunoMateria(Materia materia, NotaUnidade nota) {
        this.materia = materia;
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Matéria: " + materia.getNome() +
                " | Professor: " + materia.getProfessor().getNome() +
                " | Média: " + nota.calcularMedia() +
                " | Situação: " + nota.situacaoAluno();
    }
}
