package validation;

import exception.InvalidNameException;
import lombok.NonNull;

import static controller.AlunoController.scanner;

public class ProfessorValidator {

    public static @NonNull String adicionarNomeProfessor(String nomeProfessor) {
        while (true) {
            try {
                if (nomeProfessor == null || nomeProfessor.trim().isEmpty()) {
                    System.out.print("Digite o nome completo do Professor: ");
                    nomeProfessor = scanner.nextLine();
                }

                if (nomeProfessor.matches("[A-Za-zÀ-ú ]+")) {
                    return nomeProfessor.trim();
                } else {
                    throw new InvalidNameException(
                            "\nNome inválido. Por favor, digite um nome que contenha apenas letras.\n");
                }

            } catch (InvalidNameException e) {
                System.out.println(e.getMessage());
                nomeProfessor = null; // força pedir de novo
            }
        }
    }
}