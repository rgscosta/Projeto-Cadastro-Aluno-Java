package validation;

import exception.InvalidNameException;

import static controller.AlunoController.scanner;

public class MateriaValidator {

    public static String cadastrarMaterias(String materiaPorAluno) {
        while (true) {
            try {
                if (materiaPorAluno == null || materiaPorAluno.trim().isEmpty()) {
                    System.out.print("Cadastre a materia: ");
                    materiaPorAluno = scanner.nextLine();
                }

                if (materiaPorAluno.matches("[A-Za-zÀ-ú ]+")) {
                    return materiaPorAluno.trim();
                } else {
                    throw new InvalidNameException(
                            "\nNome inválido. Por favor, digite um nome que contenha apenas letras.\n");
                }

            } catch (InvalidNameException e) {
                System.out.println(e.getMessage());
                materiaPorAluno = null;
            }
        }
    }
}