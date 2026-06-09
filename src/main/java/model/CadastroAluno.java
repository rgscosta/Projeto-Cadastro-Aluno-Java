package model;

import service.CadastroAlunoService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static model.AlterarAluno.alterarAlunos;
import static model.ConsultarAluno.consultarAlunos;
import static model.ExcluirAluno.excluirAlunos;
import static model.ValidarCadastroAluno.*;

public class CadastroAluno {

    public static final Scanner scanner = new Scanner(System.in);
    public static final CadastroAlunoService alunoService = new CadastroAlunoService();

    public static void iniciarCadastroAluno() {

        int opcao;

        while (true) {
            System.out.print("************************** Menu do Sistema **************************\n\n");
            System.out.println(
                    "- Digite (1) para cadastrar aluno\n" +
                            "- Digite (2) para consultar alunos (menu de consultas)\n" +
                            "- Digite (3) para alterar cadastro de aluno (menu de alterações) \n" +
                            "- Digite (4) para excluir aluno\n" +
                            "- Digite (5) para cadastro de matérias (menu de matérias)\n" +
                            "- Digite (0) para sair");
            System.out.print("=> ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // consumir o \n

                System.out.print("\n");

                switch (opcao) {
                    case 1:
                        cadastrarAlunos();
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
                        new CadastroMateriaAluno().cadastrarMateriasAlunos();
                        break;
                    case 0:
                        System.out.println("Saindo do menu de cadastro!");
                        return;
                    default:
                        System.out.println("Opção inválida. Por favor, digite uma opção válida." + "\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // consumir a entrada inválida
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    public static void cadastrarAlunos() {

        String nomeAluno;
        LocalDate dataNascimento;
        String turma;
        int tipoEnsino;
        int classe;
        int idade = 0;

        while (true) {
            nomeAluno = cadastrarNomeAlunos(null);
            dataNascimento = cadastrarDataNascimento(null);
            // Verificar duplicidade de aluno com base no nome e data de nascimento
            if (alunoService.verificarAlunoCadastrado(nomeAluno, dataNascimento)) {
                System.out.println("Nome e data de nascimento já cadastrado na base.\n");
            } else {
                break;
            }
        }
        //Cadastrar o tipo do ensino
        tipoEnsino = selecionarTipoEnsino();
        // Cadastrar tipoEnsino para cadastrar a classe
        classe = cadastrarClasse(tipoEnsino);
        // Cadastrar a Turma
        turma = cadastrarTurma(null);
        // Calcular Idade do Aluno
        idade = calcularIdadeAluno(dataNascimento);
        // Criar o Aluno com uma lista vazia de matérias
        List<Materia> materias = new ArrayList<>();
        Aluno aluno = new Aluno(nomeAluno, dataNascimento, turma, classe, tipoEnsino, idade, materias);
        // Cadastrar o Aluno
        alunoService.cadastrarAluno(aluno);

        System.out.println("\n");
        System.out.println("Matrícula:" + aluno.getMatricula() + "\n" + "Nome:" + aluno.getNome() + "\n ");
        System.out.println("Aluno cadastrado com Sucesso.\n");
    }

    private static int calcularIdadeAluno(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(dataNascimento, dataAtual).getYears();
        return idade;
    }

}
