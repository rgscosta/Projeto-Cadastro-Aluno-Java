package controller;

import domain.Professor;
import service.ProfessorService;
import validation.ProfessorValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProfessorController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProfessorService professorService = ProfessorService.getInstance();

    public static void menuProfessor() {

        while (true) {
            System.out.println("\n************** Menu de Cadastro de Professores **************");
            System.out.println("1 - Cadastrar professor");
            System.out.println("2 - Listar professores");
            System.out.println("3 - Buscar professores por matricula");
            System.out.println("4 - Excluir professor");
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
                case 1:
                    cadastrarProfessor();
                    break;
                case 2:
                    listarProfessores();
                    break;
                case 3:
                    buscarPorMatricula();
                    break;
                case 4:
                    excluirProfessor();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarProfessor() {
        String nome = ProfessorValidator.adicionarNomeProfessor(null);
        professorService.cadastrarProfessor(nome);
        System.out.println("Professor cadastrado com sucesso!");
    }

    private static void listarProfessores() {
        System.out.println("\nProfessores cadastrados:");
        professorService.listarProfessores().forEach(System.out::println);
    }

    private static void buscarPorMatricula() {
        System.out.print("Digite a matricula do professor: ");
        int matricula = scanner.nextInt();
        scanner.nextLine();

        Professor professor = professorService.buscarPorMatricula(matricula);
        if (professor != null) {
            System.out.println(professor);
        } else {
            System.out.println("Professor não encontrado.");
        }
    }

    private static void excluirProfessor() {
        System.out.print("Digite a matricula do professor: ");
        int matricula = scanner.nextInt();
        scanner.nextLine();

        if (professorService.excluirProfessor(matricula)) {
            System.out.println("Professor excluído.");
        } else {
            System.out.println("Professor não encontrado.");
        }
    }
}
