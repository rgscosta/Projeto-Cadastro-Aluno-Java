package domain;

import lombok.Data;

@Data
public class Professor {

    private static int nextMatricula = 1;
    private int matricula;
    private String nome;

    public Professor(String nome) {
        this.matricula = nextMatricula++;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + matricula + " | Professor: " + nome;
    }


}
