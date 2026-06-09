package model;

import exception.NomeInvalidoException;
import lombok.NonNull;

import static model.CadastroAluno.scanner;

public class ValidarCadastroProfessor {


    public static @NonNull String cadastrarNomeProfessor(String nomeProfessor) {
        while (true) {
            try {
                if (nomeProfessor == null || nomeProfessor.trim().isEmpty()) {
                    System.out.print("Digite o nome completo do Professor: ");
                    nomeProfessor = scanner.nextLine();
                }

                if (nomeProfessor.matches("[A-Za-zÀ-ú ]+")) {
                    return nomeProfessor.trim();
                } else {
                    throw new NomeInvalidoException(
                            "\nNome inválido. Por favor, digite um nome que contenha apenas letras.\n");
                }

            } catch (NomeInvalidoException e) {
                System.out.println(e.getMessage());
                nomeProfessor = null; // força pedir de novo
            }
        }
    }
}