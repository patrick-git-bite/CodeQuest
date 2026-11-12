package codequest.dominio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DesafioTeste {
    public static void main(String[] args) {
        List<String> opcoesMutaveis = new ArrayList<>(List.of("int", "String", "boolean"));
        Quiz quiz = new Quiz(
                " Tipos em Java ",
                Trilha.BACKEND,
                50,
                15,
                " Qual tipo armazena texto em Java? ",
                opcoesMutaveis,
                1);
        opcoesMutaveis.add("double");

        verificar("Tipos em Java".equals(quiz.getTituloDesafio()), "Titulo sem espacos externos");
        verificar(quiz.getTrilha() == Trilha.BACKEND, "Trilha configurada");
        verificar(quiz.getRecompensaXp() == 50, "Recompensa configurada");
        verificar(quiz.getCustoEnergia() == 15, "Custo configurado");
        verificar(!quiz.isConcluido(), "Inicia nao concluido");
        verificar("Qual tipo armazena texto em Java?".equals(quiz.getEnunciado()), "Enunciado sem espacos externos");
        verificar(quiz.getAlternativas().size() == 3, "Copia defensiva da lista de alternativas");
        esperarExcecao(UnsupportedOperationException.class, () -> quiz.getAlternativas().add("char"));

        Personagem personagem = new Personagem("Patrick");

        esperarExcecao(RespostaInvalidaException.class, () -> quiz.tentar(personagem, null));
        esperarExcecao(RespostaInvalidaException.class, () -> quiz.tentar(personagem, " "));
        esperarExcecao(RespostaInvalidaException.class, () -> quiz.tentar(personagem, "abc"));
        esperarExcecao(RespostaInvalidaException.class, () -> quiz.tentar(personagem, "0"));
        esperarExcecao(RespostaInvalidaException.class, () -> quiz.tentar(personagem, "4"));
        esperarExcecao(RespostaInvalidaException.class, () -> quiz.tentar(personagem, "//2"));
        verificar(personagem.getEnergia() == 100, "Resposta invalida nao consome energia");
        verificar(personagem.getExperienciaGeral() == 0, "Resposta invalida nao concede XP");

        boolean erro = quiz.tentar(personagem, "1");
        verificar(!erro, "Alternativa incorreta retorna false");
        verificar(!quiz.isConcluido(), "Erro nao conclui o quiz");
        verificar(personagem.getEnergia() == 85, "Tentativa incorreta consome energia");
        verificar(personagem.getExperienciaGeral() == 0, "Erro nao concede XP");

        boolean acerto = quiz.tentar(personagem, " 2 ");
        verificar(acerto, "Alternativa correta retorna true");
        verificar(quiz.isConcluido(), "Acerto conclui o quiz");
        verificar(personagem.getEnergia() == 70, "Acerto consome energia da tentativa");
        verificar(personagem.getExperienciaBackend() == 50, "Acerto concede XP na trilha correta");
        verificar(personagem.getArquetipo() == Arquetipo.MAGO_BACKEND, "XP do quiz evolui arquetipo");

        boolean repetido = quiz.tentar(personagem, "2");
        verificar(!repetido, "Desafio ja concluido retorna false");
        verificar(personagem.getEnergia() == 70, "Desafio concluido nao consome energia novamente");
        verificar(personagem.getExperienciaBackend() == 50, "Desafio concluido nao concede XP duplicado");

        Desafio leitura = new Leitura(
                "Introducao ao HTML",
                Trilha.FRONTEND,
                50,
                10,
                " Tags estruturam documentos na web. ");
        verificar("Tags estruturam documentos na web.".equals(((Leitura) leitura).getConteudo()), "Conteudo da leitura");
        esperarExcecao(RespostaInvalidaException.class, () -> leitura.tentar(personagem, "ok"));
        verificar(personagem.getEnergia() == 70, "Confirmacao invalida nao gasta energia");

        verificar(leitura.tentar(personagem, " confirmar "), "Confirmacao valida conclui leitura");
        verificar(leitura.isConcluido(), "Leitura marcada como concluida");
        verificar(personagem.getEnergia() == 60, "Leitura consome energia");
        verificar(personagem.getExperienciaFrontend() == 50, "Leitura concede XP em Frontend");
        verificar(personagem.getNivel() == 2, "Quiz + Leitura somam 100 XP e sobem para nivel 2");
        verificar(!leitura.tentar(personagem, "CONFIRMAR"), "Leitura nao recompensa duas vezes");

        Quiz desafioPesado = new Quiz(
                "Boss de Energia",
                Trilha.BACKEND,
                100,
                65,
                "Pergunta exige mais energia do que resta.",
                List.of("Sim", "Nao"),
                0);
        esperarExcecao(EnergiaEsgotadaException.class, () -> desafioPesado.tentar(personagem, "1"));
        verificar(personagem.getEnergia() == 60, "Falha de energia preserva energia restante");
        verificar(!desafioPesado.isConcluido(), "Falha de energia nao conclui desafio");
        personagem.descansar();
        verificar(desafioPesado.tentar(personagem, "1"), "Apos descansar consegue concluir o desafio");
        verificar(personagem.getEnergia() == 35, "Energia descontada apos descanso");
        verificar(personagem.getExperienciaBackend() == 150, "XP concedido apos descanso");

        esperarExcecao(IllegalArgumentException.class, () -> desafioPesado.tentar(null, "1"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura(" ", Trilha.BACKEND, 10, 10, "Texto"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura("Titulo", null, 10, 10, "Texto"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura("Titulo", Trilha.BACKEND, 0, 10, "Texto"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura("Titulo", Trilha.BACKEND, 10, 0, "Texto"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura("Titulo", Trilha.BACKEND, -5, 10, "Texto"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura("Titulo", Trilha.BACKEND, 10, -5, "Texto"));
        esperarExcecao(IllegalArgumentException.class, () -> new Leitura("Titulo", Trilha.BACKEND, 10, 10, " "));
        esperarExcecao(IllegalArgumentException.class,
                () -> new Quiz("Titulo", Trilha.BACKEND, 10, 10, " ", List.of("A", "B"), 0));
        esperarExcecao(IllegalArgumentException.class,
                () -> new Quiz("Titulo", Trilha.BACKEND, 10, 10, "Enunciado", List.of("Unica"), 0));
        esperarExcecao(IllegalArgumentException.class,
                () -> new Quiz("Titulo", Trilha.BACKEND, 10, 10, "Enunciado", Arrays.asList("A", " "), 0));
        esperarExcecao(IllegalArgumentException.class,
                () -> new Quiz("Titulo", Trilha.BACKEND, 10, 10, "Enunciado", List.of("A", "B"), -1));
        esperarExcecao(IllegalArgumentException.class,
                () -> new Quiz("Titulo", Trilha.BACKEND, 10, 10, "Enunciado", List.of("A", "B"), 2));

        System.out.println("Todos os testes de Desafio, Quiz e Leitura passaram.");
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
