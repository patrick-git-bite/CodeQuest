package codequest.dominio;

public class PersonagemTeste {
    public static void main(String[] args) {
        Personagem personagem = new Personagem(" Patrick ");
        verificar("Patrick".equals(personagem.getNome()), "Nome sem espacos externos");
        verificar(personagem.getNivel() == 1, "Nivel inicial");
        verificar(personagem.getExperienciaGeral() == 0, "XP inicial");
        verificar(personagem.getEnergia() == 100, "Energia inicial");
        verificar(personagem.getArquetipo() == Arquetipo.APRENDIZ, "Arquetipo inicial");

        personagem.ganharExperiencia(Trilha.BACKEND, 50);
        verificar(personagem.getArquetipo() == Arquetipo.MAGO_BACKEND, "Foco em Backend");
        personagem.ganharExperiencia(Trilha.FRONTEND, 50);
        verificar(personagem.getExperienciaBackend() == 50, "XP de Backend");
        verificar(personagem.getExperienciaFrontend() == 50, "XP de Frontend");
        verificar(personagem.getExperienciaGeral() == 100, "Soma de XP");
        verificar(personagem.getNivel() == 2, "Nivel 2 com 100 XP");
        verificar(personagem.getArquetipo() == Arquetipo.APRENDIZ, "Empate abaixo do minimo Fullstack");

        Personagem evolucao = new Personagem("Evolucao");
        evolucao.ganharExperiencia(Trilha.BACKEND, 99);
        verificar(evolucao.getNivel() == 1, "99 XP ainda e nivel 1");
        evolucao.ganharExperiencia(Trilha.BACKEND, 1);
        verificar(evolucao.getNivel() == 2, "100 XP sobe para nivel 2");
        evolucao.ganharExperiencia(Trilha.FRONTEND, 99);
        verificar(evolucao.getNivel() == 2, "199 XP ainda e nivel 2");
        verificar(evolucao.getArquetipo() == Arquetipo.MAGO_BACKEND, "100 contra 99 nao e Fullstack");
        evolucao.ganharExperiencia(Trilha.FRONTEND, 1);
        verificar(evolucao.getNivel() == 3, "200 XP sobe para nivel 3");
        verificar(evolucao.getArquetipo() == Arquetipo.ARQUIMAGO_FULLSTACK, "100 em cada trilha e Fullstack");
        evolucao.ganharExperiencia(Trilha.FRONTEND, 50);
        verificar(evolucao.getArquetipo() == Arquetipo.LADINO_FRONTEND, "Mudanca de foco depois de Fullstack");
        evolucao.ganharExperiencia(Trilha.BACKEND, 50);
        verificar(evolucao.getArquetipo() == Arquetipo.ARQUIMAGO_FULLSTACK, "Retorno ao equilibrio");
        evolucao.ganharExperiencia(Trilha.BACKEND, 350);
        verificar(evolucao.getNivel() == 7, "Recompensa grande pode subir varios niveis");
        verificar(evolucao.getArquetipo() == Arquetipo.MAGO_BACKEND, "Retorno ao foco Backend");

        Personagem frontend = new Personagem("Frontend");
        frontend.ganharExperiencia(Trilha.FRONTEND, 50);
        verificar(frontend.getArquetipo() == Arquetipo.LADINO_FRONTEND, "Inicio pela trilha Frontend");

        personagem.consumirEnergia(10);
        verificar(personagem.getEnergia() == 90, "Consumo de energia");
        esperarExcecao(EnergiaEsgotadaException.class, () -> personagem.consumirEnergia(91));
        verificar(personagem.getEnergia() == 90, "Falha nao consome energia");
        personagem.consumirEnergia(90);
        verificar(personagem.getEnergia() == 0, "Consumo ate zero");
        esperarExcecao(EnergiaEsgotadaException.class, () -> personagem.consumirEnergia(1));
        personagem.descansar();
        personagem.descansar();
        verificar(personagem.getEnergia() == 100, "Descanso limitado a 100");

        esperarExcecao(IllegalArgumentException.class, () -> new Personagem(" "));
        esperarExcecao(IllegalArgumentException.class, () -> new Personagem(null));
        esperarExcecao(IllegalArgumentException.class, () -> personagem.consumirEnergia(0));
        esperarExcecao(IllegalArgumentException.class, () -> personagem.consumirEnergia(-10));
        esperarExcecao(IllegalArgumentException.class, () -> personagem.ganharExperiencia(null, 50));
        esperarExcecao(IllegalArgumentException.class, () -> personagem.ganharExperiencia(Trilha.BACKEND, 0));
        esperarExcecao(IllegalArgumentException.class, () -> personagem.ganharExperiencia(Trilha.FRONTEND, -50));
        verificar(personagem.getExperienciaGeral() == 100, "Entrada invalida nao altera XP");
        verificar(personagem.getNivel() == 2, "Entrada invalida nao altera nivel");
        verificar(personagem.getArquetipo() == Arquetipo.APRENDIZ, "Entrada invalida nao altera arquetipo");

        Personagem limite = new Personagem("Limite");
        limite.ganharExperiencia(Trilha.BACKEND, Integer.MAX_VALUE);
        esperarExcecao(ArithmeticException.class, () -> limite.ganharExperiencia(Trilha.FRONTEND, 1));
        verificar(limite.getExperienciaFrontend() == 0, "Overflow nao altera XP");
        verificar(limite.getExperienciaGeral() == Integer.MAX_VALUE, "XP maximo preservado");
        verificar(limite.getNivel() == 21474837, "Nivel valido no limite inteiro");

        System.out.println("Personagem: " + personagem.getNome());
        System.out.println("Nivel: " + personagem.getNivel());
        System.out.println("Arquetipo: " + personagem.getArquetipo().getNome());
        System.out.println("XP geral: " + personagem.getExperienciaGeral());
        System.out.println("Energia apos descanso: " + personagem.getEnergia());
        System.out.println("Evolucao testada: Aprendiz -> Mago -> Fullstack -> Ladino -> Fullstack -> Mago");
        System.out.println("Todos os testes de Personagem passaram.");
    }

    private static void verificar(boolean condicao, String cenario) {
        if (!condicao) {
            throw new AssertionError("Falhou: " + cenario);
        }
    }

    private static void esperarExcecao(Class<? extends RuntimeException> tipo, Runnable acao) {
        try {
            acao.run();
        } catch (RuntimeException exception) {
            verificar(tipo.isInstance(exception), "Tipo da excecao: " + tipo.getSimpleName());
            return;
        }
        throw new AssertionError("Esperava " + tipo.getSimpleName());
    }
}