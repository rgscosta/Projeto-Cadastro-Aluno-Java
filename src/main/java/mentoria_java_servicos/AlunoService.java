package mentoria_java_servicos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import mentoria_java_entidades.Aluno;

public class AlunoService {

	private final List<Aluno> alunos;

	public AlunoService() {
		this.alunos = new ArrayList<>();
	}

	public void cadastrarAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public List<Aluno> consultarAlunos() {
		return new ArrayList<>(alunos);
	}

	public Aluno consultarAlunoPorMatricula(int matricula) {
		for (Aluno aluno : alunos) {
			if (aluno.getMatricula() == matricula) {
				return aluno;
			}
		}
		return null;
	}

	public List<Aluno> consultarAlunoPorNome(String nome) {
		List<Aluno> alunosEncontrados = new ArrayList<>();
		for (Aluno aluno : alunos) {
			if (aluno.getNomeAluno().equalsIgnoreCase(nome)) {
				alunosEncontrados.add(aluno);
			}
		}
		return alunosEncontrados;
	}

	public boolean alterarAluno(Aluno alunoAlterado) {
		for (int i = 0; i < alunos.size(); i++) {
			if (alunos.get(i).getMatricula().equals(alunoAlterado.getMatricula())) {
				alunos.set(i, alunoAlterado);
				return true;
			}
		}
		return false;
	}

	public boolean excluirAluno(int matricula) {
		return alunos.removeIf(aluno -> aluno.getMatricula() == matricula);
	}

	public void exportarAlunosParaArquivo(String caminhoDiretorio) {
		File pasta = new File(caminhoDiretorio);
		if (!pasta.exists()) {
			if (!pasta.mkdirs()) {
				System.out.println("Não foi possível criar a pasta: " + caminhoDiretorio);
				return;
			}
		}

		String caminhoArquivo = caminhoDiretorio + File.separator + "alunos_cadastrados.txt";
		try (PrintWriter writer = new PrintWriter(new File(caminhoArquivo))) {
			for (Aluno aluno : alunos) {
				writer.println(aluno);
			}
			System.out.println("Alunos exportados com sucesso para o arquivo: " + caminhoArquivo + "\n");
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao exportar alunos: " + e.getMessage());
		}
	}

	public boolean verificarAlunoCadastrado(String nomeAluno, LocalDate dataNascimento) {
		for (Aluno aluno : alunos) {
			if (aluno.getNomeAluno().equalsIgnoreCase(nomeAluno)
					&& aluno.getDataNascimento().equals(dataNascimento)) {
				return true;
			}
		}
		return false;
	}

}