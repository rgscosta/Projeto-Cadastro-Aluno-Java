package model;

import exception.NomeInvalidoException;
import exception.TurmaInvalidaException;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import static model.CadastroAluno.scanner;


public class ValidarCadastroAluno {

    public static @NonNull String cadastrarNomeAlunos(String nomeAluno) {
        while (true) {
            try {
                if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
                    System.out.print("Digite o nome completo do Aluno: ");
                    nomeAluno = scanner.nextLine();
                }

                if (nomeAluno.matches("[A-Za-zÀ-ú ]+")) {
                    return nomeAluno.trim();
                } else {
                    throw new NomeInvalidoException(
                            "\nNome inválido. Por favor, digite um nome que contenha apenas letras.\n");
                }

            } catch (NomeInvalidoException e) {
                System.out.println(e.getMessage());
                nomeAluno = null; // força pedir de novo
            }
        }
    }

    public static LocalDate cadastrarDataNascimento(String dataNascimentoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

        while (true) {
            if (dataNascimentoStr == null || dataNascimentoStr.trim().isEmpty()) {
                System.out.print("Digite a data de nascimento no formato(dd/MM/yyyy): ");
                dataNascimentoStr = scanner.nextLine();
            }

            if (datePattern.matcher(dataNascimentoStr).matches()) {
                try {
                    LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
                    if (dataNascimento.getYear() >= 1900) {
                        return dataNascimento;
                    } else {
                        System.out.println("Ano inválido. Por favor, digite um ano à partir de 1900.");
                        dataNascimentoStr = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Data inválida. Por favor, digite no formato(dd/MM/yyyy)");
                    dataNascimentoStr = null;
                }
            } else {
                System.out.println("Data inválida. Por favor, digite no formato(dd/MM/yyyy)");
                dataNascimentoStr = null;
            }
        }
    }

    public static String cadastrarTurma(String turmaAluno) {
        while (true) {
            try {
                if (turmaAluno == null || turmaAluno.trim().isEmpty()) {
                    System.out.print("Digite a turma do aluno: ");
                    turmaAluno = scanner.nextLine();
                }
                String turma = turmaAluno.trim();

                if (turma.matches("[A-Za-zÀ-ú]") && turma.length() == 1) {
                    return turma.toUpperCase();
                } else {
                    throw new TurmaInvalidaException("Turma inválida. Por favor, digite apenas uma letra. ex:(A, B, C..)");
                }
            } catch (TurmaInvalidaException e) {
                System.out.println(e.getMessage());
                turmaAluno = null;
            }
        }
    }

    public static int selecionarTipoEnsino() {
        while (true) {
            System.out.println("** Cadastro do tipo do ensino do Aluno **");
            System.out.println("- Digite (1) para Ensino Fundamental (1º ao 9º)");
            System.out.println("- Digite (2) para Ensino Médio (1º ao 3º)");
            System.out.print("=> ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1 || opcao == 2) {
                    return opcao;
                } else {
                    System.out.println("Opção inválida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                scanner.nextLine();
            }
        }
    }

    public static int cadastrarClasse(Integer tipoEnsino) {
        while (true) {
            try {
                System.out.print("Digite a classe/Anoº do aluno: ");
                String classeAluno = scanner.nextLine();

                classeAluno = classeAluno.replaceAll("[^0-9]", "");

                if (classeAluno.isEmpty()) {
                    System.out.println("Digite um número válido.\n");
                    continue;
                }

                int classe = Integer.parseInt(classeAluno);

                if (tipoEnsino == 1) {
                    if (classe >= 1 && classe <= 9) {
                        return classe;
                    } else {
                        System.out.println("Classe inválida para Fundamental (1º a 9º).\n");
                    }
                } else {
                    if (classe >= 1 && classe <= 3) {
                        return classe;
                    } else {
                        System.out.println("Classe inválida para Ensino Médio (1º a 3º).\n");
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.\n");
            }
        }
    }
}
