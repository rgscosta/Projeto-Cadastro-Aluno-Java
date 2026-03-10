package mentoria_java_entidades;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import mentoria_java_servicos.AlunoService;

public class CadastroAluno {

	static Scanner scanner = new Scanner(System.in);
	static AlunoService alunoService = new AlunoService();

	public static void main(String[] args) {

		int opcao;

		while (true) {
			System.out.print("************************** Menu de Cadastros **************************\n\n");
			System.out.println(
					"- Digite (1) para cadastro de Aluno \n" +
							"- Digite (2) para consulta de Aluno \n" +
							"- Digite (3) para alterar cadastro de Aluno \n" +
							"- Digite (4) para excluir cadastro de Aluno \n" +
							"- Digite (0) para sair ");
			System.out.print("=> ");

			try {
				opcao = scanner.nextInt();
				scanner.nextLine(); // consumir o \n

				System.out.print("\n");

				switch (opcao) {
					case 1:
						cadastro();
						break;
					case 2:
						consulta();
						break;
					case 3:
						alterar();
						break;
					case 4:
						excluir();
						break;
					case 0:
						System.out.println("Saindo do menu de cadastro!");
						scanner.close();
						return;
					default:
						System.out.println("Opção inválida. Por favor, digite uma opção válida.");
				}

			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, digite um número.");
				scanner.nextLine(); // consumir a entrada inválida
			} catch (Exception e) {
				// Evita mascarar erro real como se fosse erro de input
				System.out.println("Ocorreu um erro: " + e.getMessage());
			}
		}
	}

	// ========================= CADASTRO =========================

	public static void cadastro() {

		String nomeAluno;
		LocalDate dataNascimento;

		while (true) {
			// Agora pede input no console (não passa null)
			nomeAluno = cadastrarNomeAluno(null);
			dataNascimento = cadastrarDataNascimento(null);

			// Verificar duplicidade de aluno com base no nome e data de nascimento
			if (alunoService.verificarAlunoCadastrado(nomeAluno, dataNascimento)) {
				System.out.println("Nome e data de nascimento já cadastrado na base.\n");
			} else {
				break;
			}
		}

		// Cadastrar as Notas (lendo do console)
		double nota1 = cadastrarNota("Digite a nota 1 (entre 0 e 10): ", -1);
		double nota2 = cadastrarNota("Digite a nota 2 (entre 0 e 10): ", -1);

		// Cadastrar a Classe (lendo do console)
		int classe = cadastrarClasse(null);

		// Cadastrar a Turma (lendo do console)
		String turma = cadastrarTurma(null);

		// Criar aluno
		Aluno aluno = new Aluno(nomeAluno, dataNascimento, nota1, nota2, classe, turma);

		// Compatível com seu Aluno atual: métodos SEM parâmetros
		aluno.calcularMedia();
		aluno.determinarSituacao();

		alunoService.cadastrarAluno(aluno);

		System.out.println("Matrícula: " + aluno.getMatricula() + "\n");
		System.out.println("Aluno cadastrado com Sucesso.\n");
	}

	// ========================= VALIDADORES =========================

	/**
	 * Mantém a assinatura original, mas se vier null/inválido,
	 * lê do console até ficar válido.
	 */
	public static String cadastrarNomeAluno(String nomeAluno) {
		while (true) {
			try {
				if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
					System.out.print("Digite o nome do aluno: ");
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
				System.out.print("Digite a data de nascimento (dd/MM/yyyy): ");
				dataNascimentoStr = scanner.nextLine();
			}

			if (datePattern.matcher(dataNascimentoStr).matches()) {
				try {
					LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
					if (dataNascimento.getYear() >= 1900) {
						return dataNascimento;
					} else {
						System.out.println("Ano inválido. Por favor, digite um ano a partir de 1900.");
						dataNascimentoStr = null;
					}
				} catch (DateTimeParseException e) {
					System.out.println("Data inválida. Por favor, digite no formato dd/MM/yyyy.");
					dataNascimentoStr = null;
				}
			} else {
				System.out.println("Data inválida. Por favor, digite no formato dd/MM/yyyy.");
				dataNascimentoStr = null;
			}
		}
	}

	public static double cadastrarNota(String pergunta, double notaDigitadaAluno) {
		while (true) {
			try {
				double nota;

				if (notaDigitadaAluno >= 0 && notaDigitadaAluno <= 10) {
					return notaDigitadaAluno;
				}

				System.out.print(pergunta);
				nota = scanner.nextDouble();
				scanner.nextLine(); // consumir \n

				if (nota >= 0 && nota <= 10) {
					return nota;
				} else {
					System.out.println("Nota inválida. Por favor, digite uma nota entre 0 e 10.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, digite um número (ex: 8.5).");
				scanner.nextLine();
			}
		}
	}

	public static int cadastrarClasse(String classeAluno) {
		while (true) {
			try {
				if (classeAluno == null || classeAluno.trim().isEmpty()) {
					System.out.print("Digite a classe (1º a 9º): ");
					classeAluno = scanner.nextLine();
				}

				// CORREÇÃO: precisa atribuir o replace
				classeAluno = classeAluno.replace("º", "").trim();

				int classe = Integer.parseInt(classeAluno);

				if (classe >= 1 && classe <= 9) {
					return classe;
				} else {
					System.out.println("Classe inválida. Por favor, digite uma classe entre 1º e 9º.");
					classeAluno = null;
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Por favor, digite um número válido.");
				classeAluno = null;
			}
		}
	}

	public static String cadastrarTurma(String turmaAluno) {
		while (true) {
			try {
				if (turmaAluno == null || turmaAluno.trim().isEmpty()) {
					System.out.print("Digite a turma (uma letra): ");
					turmaAluno = scanner.nextLine();
				}

				String turma = turmaAluno.trim();

				if (turma.matches("[A-Za-zÀ-ú]") && turma.length() == 1) {
					return turma.toUpperCase();
				} else {
					throw new TurmaInvalidaException("Turma inválida. Por favor, digite apenas uma letra.");
				}
			} catch (TurmaInvalidaException e) {
				System.out.println(e.getMessage());
				turmaAluno = null;
			}
		}
	}

	// ========================= CONSULTA =========================

	public static void consulta() {
		while (true) {
			System.out.print(
					"************************** Menu de Consulta de Cadastros *****************************\n\n");
			System.out.println("Digite (1) para consultar todos os alunos");
			System.out.println("Digite (2) para consultar por matrícula");
			System.out.println("Digite (3) para consultar por nome");
			System.out.println("Digite (4) para exportar todos os alunos para um arquivo");
			System.out.println("Digite (0) para finalizar consulta");
			System.out.print("=> ");

			int consultaOpcao;

			try {
				consultaOpcao = scanner.nextInt();
				scanner.nextLine(); // consumir \n
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, digite um número.");
				scanner.nextLine();
				continue;
			}

			switch (consultaOpcao) {
				case 1:
					List<Aluno> todosAlunos = alunoService.consultarAlunos();
					if (todosAlunos.isEmpty()) {
						System.out.println("\nNenhum aluno cadastrado na base de dados.\n");
					} else {
						for (Aluno aluno : todosAlunos) {
							System.out.println(aluno);
						}
					}
					break;

				case 2:
					System.out.print("Digite a matrícula do aluno: ");
					try {
						int matricula = scanner.nextInt();
						scanner.nextLine();

						Aluno alunoPorMatricula = alunoService.consultarAlunoPorMatricula(matricula);
						if (alunoPorMatricula == null) {
							System.out.println("\nAluno não encontrado.\n");
						} else {
							System.out.println(alunoPorMatricula);
						}
					} catch (InputMismatchException e) {
						System.out.println("Entrada inválida. Matrícula deve ser um número.");
						scanner.nextLine();
					}
					break;

				case 3:
					System.out.print("Digite o nome do aluno: ");
					String nome = scanner.nextLine();
					List<Aluno> alunosPorNome = alunoService.consultarAlunoPorNome(nome);
					if (alunosPorNome.isEmpty()) {
						System.out.println("\nAluno não encontrado.\n");
					} else {
						for (Aluno aluno : alunosPorNome) {
							System.out.println(aluno);
						}
					}
					break;

				case 4:
					String userHome = System.getProperty("user.home");
					String pastaCadastro = "Cadastro de Alunos";
					String caminhoDiretorio = userHome + File.separator + "Desktop" + File.separator + pastaCadastro;
					String caminhoArquivo = caminhoDiretorio + File.separator + "Lista de Alunos Cadastrados";
					alunoService.exportarAlunosParaArquivo(caminhoArquivo);
					break;

				case 0:
					System.out.println("\nFinalizando consulta\n");
					return;

				default:
					System.out.println("\nOpção inválida. Por favor, tente novamente.\n");
			}
		}
	}

	// ========================= ALTERAR =========================

	public static void alterar() {
		System.out.print("************************** Menu Alterar Cadastro *****************************\n");
		System.out.print("\nDigite a matrícula do aluno que deseja alterar: ");

		int matricula;
		try {
			matricula = scanner.nextInt();
			scanner.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida. Matrícula deve ser um número.");
			scanner.nextLine();
			return;
		}

		Aluno alunoExistente = alunoService.consultarAlunoPorMatricula(matricula);
		if (alunoExistente == null) {
			System.out.println("\nAluno não encontrado.\n");
			return;
		}

		System.out.println("\nAlterando dados do cadastro do aluno: " + alunoExistente.getNomeAluno());

		while (true) {
			System.out.println("\nDigite a opção para alteração de cadastro:");
			System.out.println("- Digite (1) Alterar o Nome");
			System.out.println("- Digite (2) Alterar Data de Nascimento");
			System.out.println("- Digite (3) Alterar Nota 1");
			System.out.println("- Digite (4) Alterar Nota 2");
			System.out.println("- Digite (5) Alterar Classe");
			System.out.println("- Digite (6) Alterar Turma");
			System.out.println("- Digite (0) Finalizar alteração");
			System.out.print("=> ");

			int opcao;
			try {
				opcao = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Digite um número.");
				scanner.nextLine();
				continue;
			}

			String mensagemAlteracao;

			switch (opcao) {
				case 1:
					alunoExistente.setNomeAluno(cadastrarNomeAluno(null));
					mensagemAlteracao = "\nNome do aluno alterado com sucesso.\n";
					break;

				case 2:
					alunoExistente.setDataNascimento(cadastrarDataNascimento(null));
					mensagemAlteracao = "\nData de nascimento alterada com sucesso.\n";
					break;

				case 3:
					alunoExistente.setNota1(cadastrarNota("\nDigite a nova nota 1 (entre 0 e 10): ", -1));
					mensagemAlteracao = "Nota 1 alterada com sucesso.";
					break;

				case 4:
					alunoExistente.setNota2(cadastrarNota("\nDigite a nova nota 2 (entre 0 e 10): ", -1));
					mensagemAlteracao = "Nota 2 alterada com sucesso.";
					break;

				case 5:
					alunoExistente.setClasse(cadastrarClasse(null));
					mensagemAlteracao = "\nClasse alterada com sucesso.\n";
					break;

				case 6:
					alunoExistente.setTurma(cadastrarTurma(null));
					mensagemAlteracao = "\nTurma alterada com sucesso.\n";
					break;

				case 0:
					System.out.println("\nSaindo do menu de alterações \n");
					return;

				default:
					System.out.println("\nOpção inválida. Por favor, tente novamente.");
					continue;
			}

			// Compatível com seu Aluno atual: métodos SEM parâmetros
			alunoExistente.calcularMedia();
			alunoExistente.determinarSituacao();

			if (alunoService.alterarAluno(alunoExistente)) {
				System.out.println(mensagemAlteracao);
			} else {
				System.out.println("Erro ao alterar cadastro.");
			}
		}
	}

	// ========================= EXCLUIR =========================

	public static void excluir() {
		System.out.print("************************** Menu Exclusão de Cadastros *******************************\n");
		System.out.print("\nDigite a matrícula do aluno que deseja excluir: ");

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
			String nomeAluno = alunoExiste.getNomeAluno();
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