package controller;

import domain.Materia;
import domain.Professor;
import service.MateriaService;
import service.ProfessorService;
import validation.MateriaValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MateriaController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MateriaService materiaService = new MateriaService();
    private static final ProfessorService professorService = ProfessorService.getInstance();

    public static void menuMaterias() {

        while (true) {
            System.out.println("\n************** Menu de Cadastro de Matérias **************");
            System.out.println("1 - Cadastrar matéria");
            System.out.println("2 - Listar matérias");
            System.out.println("3 - Excluir matéria");
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
                    cadastrarMateria();
                    break;
                case 2:
                    listarMaterias();
                    break;
                case 3:
                    excluirMateria();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarMateria() {

        // Nome da matéria
        String nomeMateria = MateriaValidator.cadastrarMaterias(null);

        if (!professorService.listarProfessores().isEmpty()) {
            // Listar professores disponíveis
            System.out.println("\nProfessores disponíveis:");
            professorService.listarProfessores().forEach(System.out::println);
        } else {
            System.out.println("\nE necessário ter um professor vinculado a materia cadastrada." +
                    "\nRealize o cadastro de um professor no sistema. Para vicular a matéria");
        }

        if (!professorService.listarProfessores().isEmpty()){
            System.out.print("Digite o ID do professor responsável: ");
        int idProfessor = scanner.nextInt();
        scanner.nextLine();

        Professor professor = professorService.buscarPorMatricula(idProfessor);

        if (professor == null) {
            System.out.println("Professor não encontrado.");
            return;
        }

        // Criar matéria global
        Materia materia = new Materia(nomeMateria, professor);
        materiaService.cadastrarMateria(materia);

        System.out.println("Matéria cadastrada com sucesso!");
        }
    }

    private static void listarMaterias() {
        System.out.println("\nMatérias cadastradas:");
        materiaService.listarMaterias().forEach(System.out::println);
    }

    private static void excluirMateria() {
        System.out.print("Digite o ID da matéria: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (materiaService.excluirMateria(id)) {
            System.out.println("Matéria excluída.");
        } else {
            System.out.println("Matéria não encontrada.");
        }
    }
}