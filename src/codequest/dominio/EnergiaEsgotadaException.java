package codequest.dominio;

public class EnergiaEsgotadaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EnergiaEsgotadaException(String mensagem) {
        super(mensagem);
    }
}