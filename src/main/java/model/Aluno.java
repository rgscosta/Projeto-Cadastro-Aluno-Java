package model;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private List<Materia> materias;

    public Aluno(String nome, LocalDate dataNascimento, String turma, int classe, int tipoEnsino, int idadeAluno, List<Materia> materias) {
        this.matricula = nextMatricula++;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.turma = turma;
        this.classe = classe;
        this.tipoEnsino = tipoEnsino;
        this.idadeAluno = idadeAluno;
        this.materias = materias != null ? materias : new ArrayList<>();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String tipo = (tipoEnsino == 1) ? "Ensino Fundamental" : "Ensino Médio";

        StringBuilder retorno = new StringBuilder();
        retorno.append("Matrícula Aluno: ").append(matricula).append("\n")
                .append("Nome Completo: ").append(nome).append("\n")
                .append("Data de Nascimento: ").append(formatter.format(dataNascimento)).append("\n")
                .append("Idade: ").append(idadeAluno).append(" Anos\n")
                .append("Turma: ").append(turma).append("\n")
                .append("Tipo de Ensino: ").append(tipo).append("\n")
                .append("Ano: ").append(classe).append("º Ano\n");

        // Mostrar matérias
        if (materias == null || materias.isEmpty()) {
            retorno.append("Matérias: Nenhuma cadastrada\n");
        } else {
            retorno.append("Matérias Cadastradas:\n");
            for (Materia materia : materias) {
                retorno.append("  • Matrícula: ").append(materia.getMatriculaMateria())
                        .append(" | Matéria: ").append(materia.getNomeMateria())
                        .append(" | Professor: ").append(materia.getProfessorMateria())
                        .append("\n    Notas: ");

                if (materia.getNotaPriUnidade() != null) {
                    retorno.append(String.format("1ª Un.: %.2f", materia.getNotaPriUnidade()));
                }
                if (materia.getNotaSecUnidade() != null) {
                    retorno.append(String.format(" | 2ª Un.: %.2f", materia.getNotaSecUnidade()));
                }
                if (materia.getNotaTecUnidade() != null) {
                    retorno.append(String.format(" | 3ª Un.: %.2f", materia.getNotaTecUnidade()));
                }
                if (materia.getNotaQuaUnidade() != null) {
                    retorno.append(String.format(" | 4ª Un.: %.2f", materia.getNotaQuaUnidade()));
                }
                retorno.append("\n  • Média Geral: ").append(String.format("%.2f", materia.calcularMedia()))
                        .append(" | Situação: ").append(materia.situacaoAluno())
                        .append("\n");

            }
        }
        return retorno.toString();
    }

    public void adicionarMateria(Materia materia) {
        if (this.materias == null) {
            this.materias = new ArrayList<>();
        }
        this.materias.add(materia);
    }
}
