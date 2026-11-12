package codequest.dominio;

import java.util.List;

public class Leitura extends Desafio {
    private final String conteudo;

    public Leitura(String tituloDesafio, Trilha trilha, int recompensaXp, int custoEnergia, String conteudo) {
        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("Conteudo precisa ser válido");
        }

        super(tituloDesafio, trilha, recompensaXp, custoEnergia);
        this.conteudo = conteudo;
    }

    @Override
    protected boolean respostaValida(String resposta) {
        return resposta != null && resposta.trim().equalsIgnoreCase("CONFIRMAR");
    }

    @Override
    protected boolean avaliarResposta(String resposta) {
       if(!respostaValida(resposta)) {
           throw new IllegalArgumentException("Confirmação inválida");
       }

       return true;
    }

    public String getConteudo() {
        return conteudo;
    }
}
