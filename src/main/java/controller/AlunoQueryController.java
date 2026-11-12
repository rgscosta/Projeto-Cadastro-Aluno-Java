package controller;

import domain.Aluno;
import domain.AlunoMateria;
import domain.NotaUnidade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.List;

import static controller.AlunoController.alunoService;
import static controller.AlunoController.scanner;

public class AlunoQueryController {

    public static void consultarAlunos() {
        while (true) {
            System.out.print(
                    "************************** Menu de Consulta de Cadastros **************************\n\n");
            System.out.println("- Digite (1) para consultar todos os alunos");
            System.out.println("- Digite (2) para consultar por matrícula");
            System.out.println("- Digite (3) para consultar por nome");
            System.out.println("- Digite (4) para exportar notas dos alunos para arquivo");
            System.out.println("- Digite (5) para exportar notas dos alunos em CSV");
            System.out.println("- Digite (0) para voltar");
            System.out.print("=> ");

            int consultaOpcao;

            try {
                consultaOpcao = scanner.nextInt();
                scanner.nextLine(); // consumir \n
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                continue;
            }
            switch (consultaOpcao) {
                case 1:
                    List<Aluno> todosAlunos = alunoService.consultarAlunosService();
                    if (todosAlunos.isEmpty()) {
                        System.out.println("\nNenhum aluno cadastrado na base de dados.\n");
                    } else {
                        for (Aluno aluno : todosAlunos) {
                            System.out.println(aluno);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Digite a matrícula do aluno: ");
                    try {
                        int matricula = scanner.nextInt();
                        scanner.nextLine();

                        Aluno alunoPorMatricula = alunoService.consultarAlunoPorMatriculaService(matricula);
                        if (alunoPorMatricula == null) {
                            System.out.println("\nAluno não encontrado.\n");
                        } else {
                            System.out.println(alunoPorMatricula);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Matrícula deve ser um número.");
                        scanner.nextLine();
                    }
                    break;
                case 3:
                    System.out.print("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();
                    List<Aluno> alunosPorNome = alunoService.consultarAlunoPorNomeService(nome);
                    if (alunosPorNome.isEmpty()) {
                        System.out.println("\nAluno não encontrado.\n");
                    } else {
                        for (Aluno aluno : alunosPorNome) {
                            System.out.println(aluno);
                        }
                    }
                    break;
                case 4:
                    String userHome = System.getProperty("user.home");
                    String pastaCadastro = "Cadastro de Alunos";
                    String caminhoDiretorio = userHome + File.separator + "Desktop" + File.separator + pastaCadastro;
                    String caminhoArquivo = caminhoDiretorio + File.separator + "Notas dos Alunos";
                    alunoService.exportarNotasParaArquivoService(caminhoArquivo);
                    break;
                case 0:
                    System.out.println("\nFinalizando consulta\n");
                    return;
                default:
                    System.out.println("\nOpção inválida. Por favor, tente novamente.\n");
            }
        }
    }
    public void exportarNotasParaCSVService(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo + ".csv");

        try (PrintWriter writer = new PrintWriter(arquivo)) {
            // Cabeçalho CSV
            writer.println("Matricula;Aluno;Materia;Professor;Nota1;Nota2;Nota3;Nota4;Media;Situacao");

            for (Aluno aluno : alunoService.consultarAlunosService()) {
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

