package service;

import model.Aluno;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CadastroAlunoService {

    private final List<Aluno> alunos;

    public CadastroAlunoService() {
        this.alunos = new ArrayList<>();
    }

    public void cadastrarAluno(Aluno aluno) {
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

    public List<Aluno> consultarAlunos() {
        System.out.println("\n========= REGISTRO DE ALUNOS CADASTRADOS ==========\n");
        return new ArrayList<>(alunos);
    }

    public Aluno consultarAlunoPorMatricula(int matriculaMateria) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matriculaMateria) {
                System.out.println("\n=========  REGISTRO DO ALUNO CADASTRADO POR MATRICULA ==========\n");
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> consultarAlunoPorNome(String nome) {
        List<Aluno> alunosEncontrados = new ArrayList<>();
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                alunosEncontrados.add(aluno);
            }
        }
        System.out.println("\n=========  REGISTRO DO ALUNO CADASTRADO POR NOME ==========\n");
        return alunosEncontrados;
    }

    public boolean alterarAluno(Aluno alunoAlterado) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equals(alunoAlterado.getMatricula())) {
                alunos.set(i, alunoAlterado);
                return true;
            }
        }
        return false;
    }

    //Excluir o Aluno do cadastro
    public boolean excluirAluno(int matricula) {
        return alunos.removeIf(aluno -> aluno.getMatricula() == matricula);
    }

    //Exporta o arquivo com a lista de Alunos cadastrados
    public void exportarAlunosParaArquivo(String caminhoDiretorio) {
        // Validação: verificar se há alunos cadastrados
        if (alunos.isEmpty()) {
            System.out.println("\nNenhum aluno cadastrado na base de dados. Não é possível exportar.\n");
            return;
        }

        File pasta = new File(caminhoDiretorio);
        if (!pasta.exists()) {
            if (!pasta.mkdirs()) {
                System.out.println("Não foi possível criar a pasta: " + caminhoDiretorio);
                return;
            }
        }

        String caminhoArquivo = caminhoDiretorio + File.separator + "alunos_cadastrados.txt";
        try (PrintWriter writer = new PrintWriter(new File(caminhoArquivo))) {
            for (Aluno aluno : alunos) {
                writer.println(aluno);
            }
            System.out.println("Alunos exportados com sucesso para o arquivo: " + caminhoArquivo + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao exportar alunos: " + e.getMessage());
        }
    }

}

