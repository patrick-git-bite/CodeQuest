package codequest.dominio;

public enum Arquetipo {
    APRENDIZ("Aprendiz"),
    MAGO_BACKEND("Mago do Backend"),
    LADINO_FRONTEND("Ladino do Frontend"),
    ARQUIMAGO_FULLSTACK("Arquimago Fullstack");

    private final String nome;

    Arquetipo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}