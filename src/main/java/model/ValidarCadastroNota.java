package model;

import static model.CadastroAluno.scanner;

public class ValidarCadastroNota {

    public static Double cadastrarNota(String unidade) {
        while (true) {
            try {
                System.out.print("Digite a nota da " + unidade + " (0 a 10): ");
                String notaStr = scanner.nextLine();

                if (notaStr.trim().isEmpty()) {
                    System.out.println("Campo não pode estar vazio. Tente novamente.\n");
                    continue;
                }

                Double nota = Double.parseDouble(notaStr);

                if (nota >= 0 && nota <= 10) {
                    return nota;
                } else {
                    System.out.println("Nota inválida. Digite um valor entre 0 e 10.\n");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.\n");
            }
        }
    }
}

