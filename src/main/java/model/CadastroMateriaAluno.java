package model;

import java.util.ArrayList;
import java.util.InputMismatchException;

import static model.CadastroAluno.scanner;
import static model.ValidarCadastroMateria.validarMatriculaDoAluno;

public class CadastroMateriaAluno {

    public void cadastrarMateriasAlunos() {

        int opcao;

        while (true) {
            System.out.print("************************** Menu de Cadastros de Materias **************************\n\n");
            System.out.println(
                    "- Digite (1) para cadastrar matéria do aluno\n" +
                            "- Digite (2) para consultar matérias do aluno\n" +
                            "- Digite (3) para alterar dados da matéria do aluno\n" +
                            "- Digite (4) para excluir matéria do aluno\n" +
                            "- Digite (0) para retornar ao menu anterior");
            System.out.print("=> ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // consumir o \n
                System.out.print("\n");

                switch (opcao) {
                    case 1:
                        cadastroMaterias();
                        break;
                    case 2:
                        consultarMaterias();
                        break;
                    case 3:
                        alterarMaterias();
                        break;
                    case 4:
                        excluirMaterias();
                        break;
                    case 0:
                        System.out.println("Saindo do menu de cadastro de materias !\n");
                        return;
                    default:
                        System.out.println("Opção inválida. Por favor, digite uma opção válida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // consumir a entrada inválida
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    public static void cadastroMaterias() {

        String professor;
        String nomeMateria;
        String resp;

        int matricula = validarMatriculaDoAluno(0);
        Aluno aluno = CadastroAluno.alunoService.consultarAlunoPorMatricula(matricula);

        if (aluno == null) {
            System.out.println("\n Aluno não encontrado. Por favor informar uma matricula válida. \n");
            return;
        }
        // Garantir que a lista de materias não é nula
        if (aluno.getMaterias() == null) {
            aluno.setMaterias(new ArrayList<>());
        }
        boolean adicionarMais = true;
        while (adicionarMais) {
            //Cadastrando a materia do aluno pela matricula
            nomeMateria = ValidarCadastroMateria.cadastrarMateria(null);

            // Validar se matéria já existe para este aluno
            boolean materiaJaExiste = false;
            for (Materia m : aluno.getMaterias()) {
                if (m.getNomeMateria().equalsIgnoreCase(nomeMateria)) {
                    materiaJaExiste = true;
                    break;
                }
            }

            if (materiaJaExiste) {
                System.out.println("\nEsta matéria já foi cadastrada para este aluno. Por favor, cadastre uma matéria diferente.\n");
                continue;
            }

            //Cadastrando o professor da materia do aluno pela matricula
            professor = ValidarCadastroProfessor.cadastrarNomeProfessor(null);

            Materia materia = new Materia(nomeMateria, professor);
            aluno.adicionarMateria(materia);
            System.out.println("Matéria cadastrada com sucesso para o aluno: " + aluno.getNome() + "\n");

            System.out.print("Deseja cadastrar outra matéria para esse aluno? (S/N): ");
            resp = scanner.nextLine();
            if (!resp.equalsIgnoreCase("S") && !resp.equalsIgnoreCase("SIM")) {
                adicionarMais = false;
            }
        }
    }

    public static void consultarMaterias() {
        System.out.println("Consultar Materias");
    }

    public static void alterarMaterias() {
        System.out.println("Alterar Nomes das Materias");
    }

    public static void excluirMaterias() {
        System.out.println("Excluir Materias");
    }


}
