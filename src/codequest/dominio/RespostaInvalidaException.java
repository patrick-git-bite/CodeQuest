package codequest.dominio;

public class RespostaInvalidaException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    public RespostaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
