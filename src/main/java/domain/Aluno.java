package domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Aluno {

    private static int nextMatricula = 1;
    private Integer matricula;
    private String nome;
    private LocalDate dataNascimento;
    private String turma;
    private int classe;
    private int tipoEnsino;
    private int idadeAluno;
    private List<AlunoMateria> materias = new ArrayList<>();

    public Aluno(String nome, LocalDate dataNascimento, String turma, int classe, int tipoEnsino, int idadeAluno, Object o) {
        this.matricula = nextMatricula++;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.turma = turma;
        this.classe = classe;
        this.tipoEnsino = tipoEnsino;
        this.idadeAluno = idadeAluno;
    }

    public void adicionarMateria(Materia materia, NotaUnidade nota) {
        this.materias.add(new AlunoMateria(materia, nota));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Aluno {")
                .append("matricula=").append(matricula)
                .append(", nome='").append(nome).append('\'')
                .append(", dataNascimento=").append(dataNascimento)
                .append(", turma='").append(turma).append('\'')
                .append(", classe=").append(classe)
                .append(", tipoEnsino=").append(tipoEnsino)
                .append(", idadeAluno=").append(idadeAluno)
                .append("}\n");

        if (materias.isEmpty()) {
            sb.append("  Nenhuma matéria vinculada.\n");
        } else {
            sb.append("  Matérias:\n");
            for (AlunoMateria am : materias) {
                sb.append("    - ").append(am.toString()).append("\n");
            }
        }
        return sb.toString();
    }

}