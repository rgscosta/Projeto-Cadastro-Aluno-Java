package domain;

import lombok.Data;

@Data
public class NotaUnidade {

    private Double primeiraUnidade;
    private Double segundaUnidade;
    private Double terceiraUnidade;
    private Double quartaUnidade;

    public NotaUnidade(Double primeiraUnidade, Double segundaUnidade, Double terceiraUnidade, Double quartaUnidade) {
        this.primeiraUnidade = primeiraUnidade;
        this.segundaUnidade = segundaUnidade;
        this.terceiraUnidade = terceiraUnidade;
        this.quartaUnidade = quartaUnidade;
    }

    public double calcularMedia() {
        double soma = 0;
        int quantidade = 0;

        if (primeiraUnidade != null) { soma += primeiraUnidade; quantidade++; }
        if (segundaUnidade != null) { soma += segundaUnidade; quantidade++; }
        if (terceiraUnidade != null) { soma += terceiraUnidade; quantidade++; }
        if (quartaUnidade != null) { soma += quartaUnidade; quantidade++; }

        return quantidade == 0 ? 0 : soma / quantidade;
    }

    public String situacaoAluno() {
        double media = calcularMedia();
        if (media >= 7.0) return "Aprovado";
        else if (media >= 3.5) return "Em Recuperação";
        else return "Reprovado";
    }
}
