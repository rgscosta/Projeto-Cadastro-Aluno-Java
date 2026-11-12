package service;

import domain.Aluno;
import domain.AlunoMateria;
import domain.NotaUnidade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {

    private final List<Aluno> alunos;

    public AlunoService() {
        this.alunos = new ArrayList<>();
    }

    public void adicionarAlunoService(Aluno aluno) {
        alunos.add(aluno);
    }

    public boolean verificarAlunoCadastrado(String nomeAluno, LocalDate dataNascimento) {
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equalsIgnoreCase(nomeAluno)
                    && aluno.getDataNascimento().equals(dataNascimento)) {
                return true;
            }
        }
        return false;
    }

    public List<Aluno> consultarAlunosService() {
        System.out.println("\n========= REGISTRO DE ALUNOS CADASTRADOS ==========\n");
        return new ArrayList<>(alunos);
    }

    public Aluno consultarAlunoPorMatriculaService(int matriculaMateria) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matriculaMateria) {
                System.out.println("\n=========  REGISTRO DO ALUNO CADASTRADO POR MATRICULA ==========\n");
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> consultarAlunoPorNomeService(String nome) {
        List<Aluno> alunosEncontrados = new ArrayList<>();
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                alunosEncontrados.add(aluno);
            }
        }
        System.out.println("\n=========  REGISTRO DO ALUNO CADASTRADO POR NOME ==========\n");
        return alunosEncontrados;
    }

    public boolean alterarAlunoService(Aluno alunoAlterado) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equals(alunoAlterado.getMatricula())) {
                alunos.set(i, alunoAlterado);
                return true;
            }
        }
        return false;
    }

    //Excluir o Aluno do cadastro
    public boolean excluirAlunoService(int matricula) {
        return alunos.removeIf(aluno -> aluno.getMatricula() == matricula);
    }

    //Exporta o arquivo com a lista de Alunos cadastrados
    public void exportarNotasParaArquivoService(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo + ".txt");

        try (PrintWriter writer = new PrintWriter(arquivo)) {
            if (alunos.isEmpty()) {
                writer.println("Nenhum aluno cadastrado.");
            } else {
                for (Aluno aluno : alunos) {
                    writer.println("Aluno: " + aluno.getNome() + " | Matrícula: " + aluno.getMatricula());
                    if (aluno.getMaterias().isEmpty()) {
                        writer.println("  Nenhuma matéria vinculada.");
                    } else {
                        for (AlunoMateria am : aluno.getMaterias()) {
                            writer.println("  Matéria: " + am.getMateria().getNome() +
                                    " | Professor: " + am.getMateria().getProfessor().getNome() +
                                    " | Média: " + am.getNota().calcularMedia() +
                                    " | Situação: " + am.getNota().situacaoAluno());
                        }
                    }
                    writer.println("--------------------------------------------------");
                }
            }
            System.out.println("Notas exportadas com sucesso para: " + arquivo.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao exportar notas: " + e.getMessage());
        }
    }
    public void exportarNotasParaCSVService(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo + ".csv");

        try (PrintWriter writer = new PrintWriter(arquivo)) {
            // Cabeçalho CSV
            writer.println("Matricula;Aluno;Materia;Professor;Nota1;Nota2;Nota3;Nota4;Media;Situacao");

            for (Aluno aluno : alunos) { // aqui funciona porque estamos dentro do service
                if (aluno.getMaterias().isEmpty()) {
                    writer.println(aluno.getMatricula() + ";" + aluno.getNome() + ";Nenhuma matéria;;;;;;;");
                } else {
                    for (AlunoMateria am : aluno.getMaterias()) {
                        NotaUnidade nota = am.getNota();
                        writer.println(
                                aluno.getMatricula() + ";" +
                                        aluno.getNome() + ";" +
                                        am.getMateria().getNome() + ";" +
                                        am.getMateria().getProfessor().getNome() + ";" +
                                        (nota.getPrimeiraUnidade() != null ? nota.getPrimeiraUnidade() : "") + ";" +
                                        (nota.getSegundaUnidade() != null ? nota.getSegundaUnidade() : "") + ";" +
                                        (nota.getTerceiraUnidade() != null ? nota.getTerceiraUnidade() : "") + ";" +
                                        (nota.getQuartaUnidade() != null ? nota.getQuartaUnidade() : "") + ";" +
                                        nota.calcularMedia() + ";" +
                                        nota.situacaoAluno()
                        );
                    }
                }
            }

            System.out.println("Notas exportadas em CSV para: " + arquivo.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }
}

