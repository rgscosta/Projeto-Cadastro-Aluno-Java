package model;

import java.util.InputMismatchException;

import static model.CadastroAluno.alunoService;
import static model.CadastroAluno.scanner;
import static model.ValidarCadastroAluno.*;

public class AlterarAluno {

    public static void alterarAlunos() {
        System.out.print("************************** Menu Alterar Cadastro *****************************\n");
        System.out.print("\nDigite a matrícula do aluno que deseja alterar: ");

        int matricula;
        try {
            matricula = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Matrícula deve ser um número.");
            scanner.nextLine();
            return;
        }

        Aluno alunoLocalizado = alunoService.consultarAlunoPorMatricula(matricula);
        if (alunoLocalizado == null) {
            System.out.println("\nAluno não encontrado.\n");
            return;
        }

        System.out.println("\nAlterando dados do cadastro do aluno: " + alunoLocalizado.getNome());

        while (true) {
            System.out.println("\nDigite a opção para alteração de cadastro:");
            System.out.println("- Digite (1) para alterar nome do aluno");
            System.out.println("- Digite (2) para alterar data de nascimento");
            System.out.println("- Digite (3) para alterar classe/ano");
            System.out.println("- Digite (4) para alterar turma");
            System.out.println("- Digite (0) para finalizar alteração");
            System.out.print("=> ");

            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                continue;
            }

            String mensagemAlteracao;

            switch (opcao) {
                case 1:
                    alunoLocalizado.setNome(cadastrarNomeAlunos(null));
                    mensagemAlteracao = "\nNome do aluno alterado com sucesso.\n";
                    break;

                case 2:
                    alunoLocalizado.setDataNascimento(cadastrarDataNascimento(null));
                    mensagemAlteracao = "\nData de nascimento alterada com sucesso.\n";
                    break;

                case 3:
                    alunoLocalizado.setClasse(cadastrarClasse(null));
                    mensagemAlteracao = "\nClasse alterada com sucesso.\n";
                    break;

                case 4:
                    alunoLocalizado.setTurma(cadastrarTurma(null));
                    mensagemAlteracao = "\nTurma alterada com sucesso.\n";
                    break;

                case 0:
                    System.out.println("\nSaindo do menu de alterações \n");
                    return;

                default:
                    System.out.println("\nOpção inválida. Por favor, tente novamente.");
                    continue;
            }

            if (alunoService.alterarAluno(alunoLocalizado)) {
                System.out.println(mensagemAlteracao);
            } else {
                System.out.println("Erro ao alterar cadastro.");
            }
        }
    }
}
