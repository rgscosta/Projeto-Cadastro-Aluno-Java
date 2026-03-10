package mentoria_java_entidades;

import lombok.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Aluno {

	static int nextmatricula = 1;

	private Integer matricula;
	private String nomeAluno;
	private String situacao;
	private String turma;
	private LocalDate dataNascimento;
	private int classe;
	private double nota1;
	private double nota2;
	private double media;

	public Aluno(String nomeAluno, LocalDate dataNascimento, double nota1, double nota2, int classe, String turma) {
		this.nomeAluno = nomeAluno;
		this.dataNascimento = dataNascimento;
		this.classe = classe;
		this.turma = turma;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.matricula = nextmatricula++;
		calcularMedia();
		determinarSituacao();
	}

	public double calcularMedia() {
		this.media = (nota1 + nota2) / 2.0;
		return this.media;
	}

	public String determinarSituacao() {
		this.situacao = (this.media >= 7.0) ? "Aprovado" : "Reprovado";
		return this.situacao;
	}

//	public static int getNextmatricula() {
//		return nextmatricula;
//	}
//
//	public static void setNextmatricula(int nextmatricula) {
//		Aluno.nextmatricula = nextmatricula;
//	}
//
//	public Integer getMatricula() {
//		return matricula;
//	}
//
//	public void setMatricula(Integer matricula) {
//		this.matricula = matricula;
//	}
//
//	public String getNomeAluno() {
//		return nomeAluno;
//	}
//
//	public void setNomeAluno(String nomeAluno) {
//		this.nomeAluno = nomeAluno;
//	}
//
//	public String getSituacao() {
//		return situacao;
//	}
//
//	public String getTurma() {
//		return turma;
//	}
//
//	public void setTurma(String turma) {
//		this.turma = turma;
//	}
//
//	public LocalDate getDataNascimento() {
//		return dataNascimento;
//	}
//
//	public void setDataNascimento(LocalDate dataNascimento) {
//		this.dataNascimento = dataNascimento;
//	}
//
//	public int getClasse() {
//		return classe;
//	}
//
//	public void setClasse(int classe) {
//		this.classe = classe;
//	}
//
//	public double getNota1() {
//		return nota1;
//	}
//
//	public void setNota1(double nota1) {
//		this.nota1 = nota1;
//		calcularMedia();
//		determinarSituacao();
//	}
//
//	public double getNota2() {
//		return nota2;
//	}
//
//	public void setNota2(double nota2) {
//		this.nota2 = nota2;
//		calcularMedia();
//		determinarSituacao();
//	}
//
//	public double getMedia() {
//		return media;
//	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return "\nMatrícula: " + matricula
				+ "\nNome: " + nomeAluno
				+ "\nData Nascimento: " + formatter.format(dataNascimento)
				+ "\nClasse: " + classe + "º Ano"
				+ "\nTurma: " + turma
				+ "\nNota 1: " + nota1
				+ "\nNota 2: " + nota2
				+ "\nMédia: " + media
				+ "\nSituação: " + situacao + "\n";
	}
}