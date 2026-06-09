package model;

import lombok.Data;

@Data
public class Materia {

    private static int nextMatriculaMateria = 1;

    private Integer matriculaMateria;
    private String nomeMateria;
    private String professorMateria;

    private Double notaPriUnidade;
    private Double notaSecUnidade;
    private Double notaTecUnidade;
    private Double notaQuaUnidade;


    public Materia(String nomeMateria, String professor, Double notaPriUnidade, Double notaSegUnidade, Double notaTerUnidade, Double notaQuaUnidade) {
        this.matriculaMateria = nextMatriculaMateria++;
        this.nomeMateria = nomeMateria;
        this.professorMateria = professor;
        // Notas começam como null (Em Andamento)
        this.notaPriUnidade = notaPriUnidade;
        this.notaSecUnidade = notaSegUnidade;
        this.notaTecUnidade = notaTerUnidade;
        this.notaQuaUnidade = notaQuaUnidade;
    }

    // comportamento: calcular média
    public double calcularMedia() {
        double soma = 0;
        int quantidade = 0;

        if (notaPriUnidade != null) {
            soma += notaPriUnidade;
            quantidade++;
        }
        if (notaSecUnidade != null) {
            soma += notaSecUnidade;
            quantidade++;
        }
        if (notaTecUnidade != null) {
            soma += notaTecUnidade;
            quantidade++;
        }
        if (notaQuaUnidade != null) {
            soma += notaQuaUnidade;
            quantidade++;
        }

        return quantidade == 0 ? 0 : soma / quantidade;
    }

    public String situacaoAluno(){
        // Verificar se as 4 notas estão cadastradas
        if (notaPriUnidade == null || notaSecUnidade == null || notaTecUnidade == null || notaQuaUnidade == null) {
            return "Em Andamento";
        }

        double media = calcularMedia();
        if (media >= 7.0) {
            return "Aprovado";
        }
        else if (media < 7.0 && media >= 3.5) {
            return "Em Recuperação";
        }
        else {
            return "Reprovado";
        }
    }
}