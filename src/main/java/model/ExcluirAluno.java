package model;

import java.util.InputMismatchException;

import static model.CadastroAluno.alunoService;
import static model.CadastroAluno.scanner;

public class ExcluirAluno {

    public static void excluirAlunos() {
        System.out.print("************************** Menu Exclusão de Cadastros *******************************\n");
        System.out.print("\nDigite (matrícula) do aluno que deseja excluir: ");

        int matricula;
        try {
            matricula = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Matrícula deve ser um número.");
            scanner.nextLine();
            return;
        }

        Aluno alunoExiste = alunoService.consultarAlunoPorMatricula(matricula);
        if (alunoExiste != null) {
            String nomeAluno = alunoExiste.getNome();
            Integer maticulaAluno = alunoExiste.getMatricula();
            String turma = alunoExiste.getTurma();
            Integer classe = alunoExiste.getClasse();

            System.out.println("\nDeseja excluir o Aluno:" + nomeAluno + " - Matricula:" + maticulaAluno + " - Classe:"
                    + classe + "º Ano" + " - Turma:" + turma);
            System.out.println("\nDigite 'S' para Sim e 'N' para Não");
            System.out.print("=> ");
            String escolha = scanner.nextLine();

            if (escolha.equalsIgnoreCase("S")) {
                if (alunoService.excluirAluno(matricula)) {
                    System.out.println("\nAluno excluído com sucesso.\n");
                } else {
                    System.out.println("\nErro ao excluir o aluno.\n");
                }
            } else {
                System.out.println("\nOperação de exclusão cancelada.\n");
            }
        } else {
            System.out.println("\nAluno não encontrado.\n");
        }
    }
}

