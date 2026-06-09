//package service;
//
//
//import model.Aluno;
//import model.Materia;
//
//public class CondicaoAlunoService {
//
//    public double calcularMedia(Materia materia) {
//        double media = (materia.notaPriUnidade + materia.notaSecUnidade + materia.notaTecUnidade + materia.notaQuaUnidade / 2.0);
//        materia.setMediaMateria(media);
//        return media;
//    }
//
//    public String determinarSituacao(Aluno aluno) {
//        String situacao = (aluno.getMedia() >= 7.0) ? "Aprovado" : "Reprovado";
//        aluno.setSituacao(situacao);
//        return situacao;
//    }
//}
