# CodeQuest

RPG simples de estudos de programacao, desenvolvido por Patrick e Michael para o Trabalho de Grau B de Programacao Orientada a Objetos.

## Proposta

O estudante cria um personagem, responde pequenos quizzes em masmorras e ganha experiencia. Conforme estuda Backend ou Frontend, seu personagem muda de arquetipo. O objetivo e aprender Java e demonstrar os conceitos vistos em aula, nao construir uma plataforma educacional.

## Escopo inicial

- um personagem com nome, avatar, nivel, XP e energia;
- duas trilhas de estudo: Backend e Frontend;
- tres masmorras com desafios fixos cadastrados no programa;
- quizzes com alternativas e correcao automatica;
- recompensa concedida uma unica vez por desafio concluido;
- nivel, pre-requisitos e energia para controlar o acesso;
- botao de descanso para recuperar energia;
- evolucao de Aprendiz para Mago do Backend, Ladino do Frontend ou Arquimago Fullstack.

Os valores de XP, custos e criterios de evolucao serao definidos com exemplos simples antes da implementacao. O progresso ficara em memoria e sera perdido ao fechar o programa. Salvar em arquivo e uma melhoria opcional.

Ficam fora do escopo: login, banco, servidor web, painel administrativo, moedas, loja, uploads, multiplayer, combate em tempo real, mapa exploravel, IA e execucao de codigo enviado pelo aluno.

## Interface grafica

Uma janela desktop em Java Swing com tres areas:

1. **Personagem:** avatar, arquetipo, nivel, XP e energia.
2. **Masmorras:** desafios disponiveis e bloqueados.
3. **Atividade:** pergunta, alternativas e resultado.

O visual sera medieval e 2D, usando imagens PNG e barras de progresso. Nao precisamos de motor de jogos, 3D ou animacoes complexas. Recursos externos devem ter licenca adequada e creditos registrados.

## Tecnologias e POO

- **Java 21:** linguagem do projeto.
- **Swing:** interface grafica incluida no Java, sem framework externo.
- **Colecoes Java:** desafios e progresso em memoria.
- **Encapsulamento:** personagem controla XP, nivel e energia.
- **Composicao:** masmorras possuem desafios.
- **Heranca e polimorfismo:** contrato comum para desafios de quiz e leitura simples.
- **Excecoes customizadas:** acesso sem pre-requisito, energia insuficiente e resposta invalida.

Interfaces e padroes serao usados somente quando ajudarem a solucao e a dupla conseguir explica-los. Strategy, State e Factory nao sao requisitos obrigatorios.

## Organizacao planejada

```text
src/       # dominio, interface Swing e inicializacao em Java
resources/ # imagens do jogo e seus creditos
tests/     # testes das regras do jogo
docs/      # requisitos, UML e material para o artigo
```

As pastas de codigo serao criadas quando a implementacao comecar. As telas chamarao os objetos de dominio, sem calcular XP ou decidir a evolucao.

## Proximos passos

1. Fechar as regras de XP, energia e evolucao.
2. Implementar personagem e desafios com testes pequenos.
3. Construir uma janela Swing com um quiz completo.
4. Adicionar tres masmorras, avatares e mensagens de excecao.
5. Testar a demonstracao e preparar artigo e apresentacao.

Patrick pode cuidar de personagem e evolucao; Michael, de masmorras e desafios. Ambos revisam a interface e precisam compreender o codigo inteiro.

## Documentacao

- [Requisitos e regras de negocio](docs/requisitos.md)
- [Modelo de dominio](docs/modelo-dominio.md)

## Status

Escopo simplificado aprovado. A antiga base Spring Boot, PostgreSQL e Docker foi removida da versao atual e permanece no historico Git. A aplicacao Swing ainda nao foi implementada; portanto, ainda nao ha comando de execucao do jogo.