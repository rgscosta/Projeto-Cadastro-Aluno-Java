package controller;

import domain.Aluno;
import domain.AlunoMateria;
import domain.NotaUnidade;
import service.AlunoService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NotaUnidadeController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AlunoService alunoService = new AlunoService();

    public static void menuNotas() {
        while (true) {
            System.out.println("\n************** Menu de Notas **************");
            System.out.println("1 - Cadastrar notas");
            System.out.println("2 - Consultar notas");
            System.out.println("3 - Alterar notas");
            System.out.println("4 - Excluir notas");
            System.out.println("0 - Voltar");
            System.out.print("=> ");

            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1: cadastrarNotas(); break;
                case 2: consultarNotas(); break;
                case 3: alterarNotas(); break;
                case 4: excluirNotas(); break;
                case 0: return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarNotas() {
        System.out.print("Digite a matrícula do aluno: ");
        int matricula = scanner.nextInt();
        scanner.nextLine();

        Aluno aluno = alunoService.consultarAlunoPorMatriculaService(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("\nMatérias do aluno:");
        for (AlunoMateria am : aluno.getMaterias()) {
            System.out.println(am.getMateria().getMatricula() + " - " + am.getMateria().getNome());
        }

        System.out.print("Digite o ID da matéria: ");
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

        System.out.print("Nota 1ª unidade: ");
        Double n1 = scanner.nextDouble();
        System.out.print("Nota 2ª unidade: ");
        Double n2 = scanner.nextDouble();
        System.out.print("Nota 3ª unidade: ");
        Double n3 = scanner.nextDouble();
        System.out.print("Nota 4ª unidade: ");
        Double n4 = scanner.nextDouble();
        scanner.nextLine();

        alunoMateria.setNota(new NotaUnidade(n1, n2, n3, n4));
        System.out.println("Notas cadastradas com sucesso!");
    }

    private static void consultarNotas() {
        System.out.print("Digite a matrícula do aluno: ");
        int matricula = scanner.nextInt();
        scanner.nextLine();

        Aluno aluno = alunoService.consultarAlunoPorMatriculaService(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("\nNotas do aluno " + aluno.getNome() + ":");
        for (AlunoMateria am : aluno.getMaterias()) {
            System.out.println(am);
        }
    }

    private static void alterarNotas() {
        // fluxo parecido com cadastrarNotas, mas substitui valores existentes
    }

    private static void excluirNotas() {
        // fluxo parecido, mas define Nota como null ou zera valores
    }
}
