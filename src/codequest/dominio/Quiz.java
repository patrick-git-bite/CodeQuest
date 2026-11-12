package codequest.dominio;
import java.util.List;

public class Quiz extends Desafio {
    private final String enunciado;
    private final List<String> alternativas;
    private final int indiceRC; // RC = Resposta correta.

    public Quiz(String tituloDesafio, Trilha trilha, int recompensaXp, int custoEnergia, String enunciado, List<String> alternativas, int indiceRC) {

        if(enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("Enunciado é obrigatório");
        }

        if(alternativas == null || alternativas.size() < 2) {
            throw new IllegalArgumentException("O Quiz precisa de pelo menos 2 alternativas");
        }

        if(indiceRC < 0 || indiceRC >= alternativas.size()) {
            throw new IllegalArgumentException("Indíce da resposta inválido");
        }

        super(tituloDesafio, trilha, recompensaXp, custoEnergia);
        this.enunciado = enunciado;
        this.alternativas = List.copyOf(alternativas);
        this.indiceRC = indiceRC;
    }

    @Override
    protected boolean respostaValida(String resposta) {
        if(resposta == null || !resposta.matches("//d+")) {
            return false;
        }

        try {
            int numero = Integer.parseInt(resposta);
            return numero >= 1 && numero <= alternativas.size();

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected boolean avaliarResposta(String resposta) {
        if(!respostaValida(resposta)) {
            throw new IllegalArgumentException("Alternativa não é válida");
        }

        int indiceEscolhido = Integer.parseInt(resposta) - 1;

        return indiceEscolhido == indiceRC;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }
}