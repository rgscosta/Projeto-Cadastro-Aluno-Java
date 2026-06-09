package model;

import lombok.Data;

@Data
public class Materia {

    private static int nextMatricula = 1;

    private Integer matriculaMateria;
    private String nomeMateria;
    private String professorMateria;

    private Double notaPriUnidade;
    private Double notaSecUnidade;
    private Double notaTecUnidade;
    private Double notaQuaUnidade;

    public Materia(String nomeMateria, String professorMateria) {
        this.matriculaMateria = nextMatricula++;
        this.nomeMateria = nomeMateria;
        this.professorMateria = professorMateria;
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
// MESMA COISA QUE LINHAS 53
//        if (quantidade == 0) {
//            return 0;
//        } else {
//            return soma / quantidade;
//        }

        return quantidade == 0 ? 0 : soma / quantidade;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "matriculaMateria=" + matriculaMateria +
                ", nomeMateria='" + nomeMateria + '\'' +
                ", professorMateria='" + professorMateria + '\'' +
                ", notaPriUnidade=" + notaPriUnidade +
                ", notaSecUnidade=" + notaSecUnidade +
                ", notaTecUnidade=" + notaTecUnidade +
                ", notaQuaUnidade=" + notaQuaUnidade +
                ", media=" + calcularMedia() +
                '}';
    }
}