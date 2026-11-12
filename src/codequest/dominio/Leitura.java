package codequest.dominio;

public class Leitura extends Desafio {
    private final String conteudo;

    public Leitura(String tituloDesafio, Trilha trilha, int recompensaXp, int custoEnergia, String conteudo) {
        super(tituloDesafio, trilha, recompensaXp, custoEnergia);

        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("Conteúdo precisa ser válido");
        }

        this.conteudo = conteudo.trim();
    }

    @Override
    protected boolean respostaValida(String resposta) {
        return resposta != null && resposta.trim().equalsIgnoreCase("CONFIRMAR");
    }

    @Override
    protected boolean avaliarResposta(String resposta) {
        if (!respostaValida(resposta)) {
            throw new RespostaInvalidaException("Confirmação inválida");
        }

        return true;
    }

    public String getConteudo() {
        return conteudo;
    }
}
