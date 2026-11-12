package controller;

import domain.Aluno;
import domain.AlunoMateria;
import domain.NotaUnidade;

import java.util.InputMismatchException;

import static controller.AlunoController.alunoService;
import static controller.AlunoController.scanner;
import static validation.AlunoValidator.*;

public class AlunoUpdateController {

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

        Aluno alunoLocalizado = alunoService.consultarAlunoPorMatriculaService(matricula);
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
            System.out.println("- Digite (5) para alterar notas");
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
                    alunoLocalizado.setNome(adicionarNomeAlunos(null));
                    mensagemAlteracao = "\nNome do aluno alterado com sucesso.\n";
                    break;

                case 2:
                    alunoLocalizado.setDataNascimento(adicionarDataNascimento(null));
                    mensagemAlteracao = "\nData de nascimento alterada com sucesso.\n";
                    break;

                case 3:
                    alunoLocalizado.setClasse(adicionarClasse(null));
                    mensagemAlteracao = "\nClasse alterada com sucesso.\n";
                    break;

                case 4:
                    alunoLocalizado.setTurma(adicionarTurma(null));
                    mensagemAlteracao = "\nTurma alterada com sucesso.\n";
                    break;
                case 5:
                    alterarNotas(alunoLocalizado);
                    mensagemAlteracao = "\nNotas alteradas com sucesso.\n";
                    break;
                case 0:
                    System.out.println("\nSaindo do menu de alterações \n");
                    return;

                default:
                    System.out.println("\nOpção inválida. Por favor, tente novamente.");
                    continue;
            }

            if (alunoService.alterarAlunoService(alunoLocalizado)) {
                System.out.println(mensagemAlteracao);
            } else {
                System.out.println("Erro ao alterar cadastro.");
            }
        }
    }

    private static void alterarNotas(Aluno aluno) {
        if (aluno.getMaterias().isEmpty()) {
            System.out.println("Este aluno não possui matérias vinculadas.");
            return;
        }

        System.out.println("\nMatérias do aluno:");
        for (AlunoMateria am : aluno.getMaterias()) {
            System.out.println(am.getMateria().getMatricula() + " - " + am.getMateria().getNome());
        }

        System.out.print("Digite o ID da matéria para alterar notas: ");
        int idMateria = scanner.nextInt();
        scanner.nextLine();

        AlunoMateria alunoMateria = aluno.getMaterias().stream()
                .filter(am -> am.getMateria().getMatricula() == idMateria)
                .findFirst()
                .orElse(null);

        if (alunoMateria == null) {
            System.out.println("Matéria não encontrada para este aluno.");
            return;
        }

        System.out.println("Alterando notas da matéria: " + alunoMateria.getMateria().getNome());

        System.out.print("Nova nota 1ª unidade: ");
        Double n1 = scanner.nextDouble();
        System.out.print("Nova nota 2ª unidade: ");
        Double n2 = scanner.nextDouble();
        System.out.print("Nova nota 3ª unidade: ");
        Double n3 = scanner.nextDouble();
        System.out.print("Nova nota 4ª unidade: ");
        Double n4 = scanner.nextDouble();
        scanner.nextLine();

        alunoMateria.setNota(new NotaUnidade(n1, n2, n3, n4));

        System.out.println("Notas atualizadas com sucesso!");
        System.out.println("Média atual: " + alunoMateria.getNota().calcularMedia());
        System.out.println("Situação: " + alunoMateria.getNota().situacaoAluno());
    }

}
