package model;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class Aluno {

    static int nextmatricula = 1;
    private Integer matricula;
    private String nome;
    private LocalDate dataNascimento;
    private String turma;
    private int classe;
    private int tipoEnsino;
    private int idadeAluno;
    private List<Materia> materias;

    public Aluno(String nome, LocalDate dataNascimento, String turma, int classe, int tipoEnsino, int idadeAluno, List<Materia> materias) {
        this.matricula = nextmatricula++;
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

        String retorno =
                "Matrícula: " + matricula + "\n" +
                        "Nome Completo: " + nome + "\n" +
                        "Data de Nascimento: " + formatter.format(dataNascimento) + "\n" +
                        "Idade: " + idadeAluno + " Anos" + "\n" +
                        "Turma: " + turma + "\n" +
                        "Tipo de Ensino: " + tipo + "\n" +
                        "Ano: " + classe + "º" + "Ano\n";
        // Mostrar matérias
        if (materias == null || materias.isEmpty()) {
            retorno += "Matérias: Nenhuma cadastrada\n";
        } else {
            retorno += "Matérias Cadastradas:\n";
            for (Materia m : materias) {
                retorno += "- " + m.getNomeMateria() + "\n";
            }
        }
        return retorno;
    }

    public void adicionarMateria(Materia materia) {
        if (this.materias == null) {
            this.materias = new ArrayList<>();
        }
        this.materias.add(materia);
    }
}
