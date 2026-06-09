package model;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;

import static model.CadastroAluno.alunoService;
import static model.CadastroAluno.scanner;

public class ConsultarAluno {

    public static void consultarAlunos() {
        while (true) {
            System.out.print(
                    "************************** Menu de Consulta de Cadastros **************************\n\n");
            System.out.println("- Digite (1) para consultar todos os alunos");
            System.out.println("- Digite (2) para consultar por matrícula");
            System.out.println("- Digite (3) para consultar por nome");
            System.out.println("- Digite (4) para exportar todos os alunos para arquivo");
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
                    List<Aluno> todosAlunos = alunoService.consultarAlunos();
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

                        Aluno alunoPorMatricula = alunoService.consultarAlunoPorMatricula(matricula);
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
                    List<Aluno> alunosPorNome = alunoService.consultarAlunoPorNome(nome);
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
                    String caminhoArquivo = caminhoDiretorio + File.separator + "Lista de Alunos Cadastrados";
                    alunoService.exportarAlunosParaArquivo(caminhoArquivo);
                    break;
                case 0:
                    System.out.println("\nFinalizando consulta\n");
                    return;
                default:
                    System.out.println("\nOpção inválida. Por favor, tente novamente.\n");
            }
        }
    }

}

