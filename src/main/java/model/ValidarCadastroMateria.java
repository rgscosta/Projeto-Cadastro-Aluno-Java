package model;

import exception.NomeInvalidoException;

import static model.CadastroAluno.scanner;

public class ValidarCadastroMateria {

    public static String cadastrarMateria(String materiasAluno) {
        while (true) {
            try {
                if (materiasAluno == null || materiasAluno.trim().isEmpty()) {
                    System.out.print("Cadastre a materia: ");
                    materiasAluno = scanner.nextLine();

                }

                if (materiasAluno.matches("[A-Za-zÀ-ú ]+")) {
                    return materiasAluno.trim();
                } else {
                    throw new NomeInvalidoException(
                            "\nNome inválido. Por favor, digite um nome que contenha apenas letras.\n");
                }

            } catch (NomeInvalidoException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
                materiasAluno = null;
            }
        }
    }

    public static Integer validarMatriculaDoAluno(int matriculaAluno) {
        while (true) {
            if (matriculaAluno <= 0) {
                System.out.print("Digite a matrícula do aluno para cadastrar matérias:");
                try {
                    matriculaAluno = scanner.nextInt();
                    scanner.nextLine(); // consumir \n
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Matrícula deve ser um número..");
                    scanner.nextLine(); // consumir entrada inválida
                }
            } else {
                return matriculaAluno;
            }
        }
    }

}
