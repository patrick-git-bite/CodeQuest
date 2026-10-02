package codequest.dominio;

public class Personagem {
    private final String nome;
    private int experienciaBackend;
    private int experienciaFrontend;
    private int energia;

    public Personagem(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do personagem e obrigatorio.");
        }
        this.nome = nome.trim();
        this.energia = 100;
    }

    public void ganharExperiencia(Trilha trilha, int quantidade) {
        if (trilha == null || quantidade <= 0) {
            throw new IllegalArgumentException("Informe uma trilha e uma quantidade positiva de XP.");
        }
        Math.addExact(getExperienciaGeral(), quantidade);
        switch (trilha) {
            case BACKEND -> experienciaBackend += quantidade;
            case FRONTEND -> experienciaFrontend += quantidade;
        }
    }

    public void consumirEnergia(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("O consumo de energia deve ser positivo.");
        }
        if (quantidade > energia) {
            throw new EnergiaEsgotadaException("Energia insuficiente. Descanse antes de continuar.");
        }
        energia -= quantidade;
    }

    public void descansar() {
        energia = 100;
    }

    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return 1 + getExperienciaGeral() / 100;
    }

    public Arquetipo getArquetipo() {
        if (experienciaBackend == experienciaFrontend) {
            if (experienciaBackend >= 100) {
                return Arquetipo.ARQUIMAGO_FULLSTACK;
            }
            return Arquetipo.APRENDIZ;
        }
        if (experienciaBackend > experienciaFrontend) {
            return Arquetipo.MAGO_BACKEND;
        }
        return Arquetipo.LADINO_FRONTEND;
    }

    public int getExperienciaGeral() {
        return experienciaBackend + experienciaFrontend;
    }

    public int getExperienciaBackend() {
        return experienciaBackend;
    }

    public int getExperienciaFrontend() {
        return experienciaFrontend;
    }

    public int getEnergia() {
        return energia;
    }
}