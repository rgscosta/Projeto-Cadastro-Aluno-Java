package controller;

import domain.Aluno;
import domain.Materia;
import domain.NotaUnidade;
import service.AlunoService;
import service.MateriaService;

import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.Scanner;

import static controller.AlunoDeleteController.excluirAlunos;
import static controller.AlunoQueryController.consultarAlunos;
import static controller.AlunoUpdateController.alterarAlunos;
import static validation.AlunoValidator.*;

public class AlunoController {

    public static final Scanner scanner = new Scanner(System.in);
    public static final AlunoService alunoService = new AlunoService();



    public static void cadastroAlunos() {

        int opcao;

        while (true) {
            System.out.print("************************** Menu do Sistema **************************\n\n");
            System.out.println(
                    "- Digite (1) para cadastrar aluno\n" +
                            "- Digite (2) para consultar alunos (menu de consultas)\n" +
                            "- Digite (3) para alterar cadastro de aluno (menu de alterações)\n" +
                            "- Digite (4) para excluir aluno\n" +
                            "- Digite (5) para cadastro de matérias (menu de matérias)\n" +
                            "- Digite (6) para menu de cadastro de professores\n" +
                            "- Digite (7) para menu de notas\n" +
                            "- Digite (0) para sair");
            System.out.print("=> ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // consumir o \n

                System.out.print("\n");

                switch (opcao) {
                    case 1:
                        adicionarAlunos();
                        break;
                    case 2:
                        consultarAlunos();
                        break;
                    case 3:
                        alterarAlunos();
                        break;
                    case 4:
                        excluirAlunos();
                        break;
                    case 5:
                        new MateriaController().menuMaterias();
                        break;
                    case 6:
                        ProfessorController.menuProfessor();
                        break;
                    case 7:
                        NotaUnidadeController.menuNotas();   // <-- CHAMADA DO NOVO CONTROLLER
                        break;
                    case 0:
                        System.out.println("Saindo do menu de cadastro!");
                        return;
                    default:
                        System.out.println("Opção inválida. Por favor, digite uma opção válida.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // consumir a entrada inválida
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    public static void adicionarAlunos() {

        String nomeAluno;
        LocalDate dataNascimento;
        String turma;
        int tipoEnsino;
        int classe;
        int idade;

        while (true) {
            nomeAluno = adicionarNomeAlunos(null);
            dataNascimento = adicionarDataNascimento(null);
            if (alunoService.verificarAlunoCadastrado(nomeAluno, dataNascimento)) {
                System.out.println("Nome e data de nascimento já cadastrado na base.\n");
            } else {
                break;
            }
        }

        tipoEnsino = adicionarTipoEnsino();
        classe = adicionarClasse(tipoEnsino);
        turma = adicionarTurma(null);
        idade = calcularIdadeAluno(dataNascimento);

        // Criar aluno sem matérias
        Aluno aluno = new Aluno(nomeAluno, dataNascimento, turma, classe, tipoEnsino, idade, null);
        alunoService.adicionarAlunoService(aluno);

        System.out.println("\nAluno cadastrado com sucesso!");
        System.out.println("Matrícula: " + aluno.getMatricula() + " | Nome: " + aluno.getNome());

        // Perguntar se deseja adicionar matérias existentes
        adicionarMateriasAoAluno(aluno);
    }

    private static void adicionarMateriasAoAluno(Aluno aluno) {
        System.out.println("\nDeseja vincular matérias já cadastradas ao aluno? (S/N): ");
        String resposta = scanner.nextLine();

        if (!resposta.equalsIgnoreCase("S")) {
            return;
        }

        // Listar matérias disponíveis
        System.out.println("\nMatérias disponíveis:");
        MateriaService materiaService = new MateriaService();
        materiaService.listarMaterias().forEach(System.out::println);

        System.out.print("Digite os IDs das matérias separadas por vírgula (ex: 1,3,5): ");
        String entrada = scanner.nextLine();
        String[] ids = entrada.split(",");

        for (String idStr : ids) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Materia materia = materiaService.buscarPorMatricula(id);
                if (materia != null) {

                    // Perguntar se deseja cadastrar notas
                    System.out.print("Deseja cadastrar notas para " + materia.getNome() + "? (S/N): ");
                    String respNotas = scanner.nextLine();

                    NotaUnidade nota;
                    if (respNotas.equalsIgnoreCase("S")) {
                        System.out.print("Nota 1ª unidade: ");
                        Double n1 = scanner.nextDouble();
                        System.out.print("Nota 2ª unidade: ");
                        Double n2 = scanner.nextDouble();
                        System.out.print("Nota 3ª unidade: ");
                        Double n3 = scanner.nextDouble();
                        System.out.print("Nota 4ª unidade: ");
                        Double n4 = scanner.nextDouble();
                        scanner.nextLine();

                        nota = new NotaUnidade(n1, n2, n3, n4);
                    } else {
                        nota = new NotaUnidade(null, null, null, null);
                    }

                    aluno.adicionarMateria(materia, nota);
                    System.out.println("Matéria " + materia.getNome() + " vinculada ao aluno " + aluno.getNome());

                } else {
                    System.out.println("Matéria com ID " + id + " não encontrada.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido: " + idStr);
            }
        }
    }

    private static int calcularIdadeAluno(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(dataNascimento, dataAtual).getYears();
        return idade;
    }

}
