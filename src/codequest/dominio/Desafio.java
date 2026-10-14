package codequest.dominio;

public abstract class Desafio {
    private final String tituloDesafio;
    private final Trilha trilha;
    private final int recompensaXp;
    private final int custoEnergia;
    private boolean concluido;

    public Desafio(String tituloDesafio, Trilha trilha, int recompensaXp, int custoEnergia) {

        if(tituloDesafio == null || tituloDesafio.isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }

        if(trilha == null) {
            throw new IllegalArgumentException("Trilha é obrigatório");
        }

        if(recompensaXp < 0 || custoEnergia < 0) {
            throw new IllegalArgumentException("Recompensa e custo de energia não podem ser valores negativos");
        }

        this.tituloDesafio = tituloDesafio;
        this.trilha = trilha;
        this.recompensaXp = recompensaXp;
        this.custoEnergia = custoEnergia;
        this.concluido = false;
    }

    protected abstract boolean respostaValida(String resposta);

    public abstract boolean avaliarResposta(String resposta);

    public final boolean tentar(Personagem personagem, String resposta) {
        if (personagem == null) {
            throw new IllegalArgumentException("O personagem é obrigatório!");
        }

        if (concluido) {
            return false;
        }

        if (!respostaValida(resposta)) {
            throw new IllegalArgumentException("Resposta inválida!");
        }

        personagem.consumirEnergia(custoEnergia);

        if (!avaliarResposta(resposta)) {
            return false;
        }

        personagem.ganharExperiencia(trilha, recompensaXp);

        concluido = true;
        return true;
    }


    public int getCustoEnergia() {
        return custoEnergia;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public int getRecompensaXp() {
        return recompensaXp;
    }

    public Trilha getTrilha() {
        return trilha;
    }

    public String getTituloDesafio() {
        return tituloDesafio;
    }
}