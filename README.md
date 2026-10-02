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

As regras iniciais de evolucao estao implementadas: cada 100 XP sobe um nivel; a trilha com mais XP define Mago ou Ladino; empate com pelo menos 100 XP em cada trilha define Fullstack. Empate abaixo desse limite define Aprendiz. Os valores das recompensas e custos dos desafios ainda devem ser combinados com Michael. O progresso ficara em memoria e sera perdido ao fechar o programa. Salvar em arquivo e uma melhoria opcional.

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

As pastas `src/` e `tests/` ja contem a primeira implementacao de Personagem. Imagens e interface serao adicionadas depois. As telas chamarao os objetos de dominio, sem calcular XP ou decidir a evolucao.

## Executar o teste inicial

Com o JDK 21 instalado, execute na raiz do projeto:

```bash
javac -d out src/codequest/dominio/*.java tests/codequest/dominio/PersonagemTeste.java
java -cp out codequest.dominio.PersonagemTeste
```

O programa testa XP por trilha, nivel, mudancas de arquetipo, consumo de energia, descanso e entradas invalidas, e imprime os valores do personagem. Ainda nao abre uma janela. A recompensa unica por desafio sera controlada na integracao com as classes de desafios, nao pelo metodo generico de ganhar XP.

## Parte do personagem

`Personagem` nao possui setters de XP, nivel ou energia. Nivel e arquetipo sao calculados a partir do XP para que nao fiquem desatualizados. As operacoes disponiveis sao:

- `new Personagem(nome)`: cria personagem no nivel 1, sem XP e com energia 100.
- `ganharExperiencia(trilha, quantidade)`: soma XP positivo em `Trilha.BACKEND` ou `Trilha.FRONTEND`.
- `consumirEnergia(quantidade)`: desconta energia positiva ou lanca `EnergiaEsgotadaException` sem alterar o estado.
- `descansar()`: recupera a energia para 100.
- getters: permitem consultar nome, XP por trilha, XP geral, nivel, energia e arquetipo.
- `getArquetipo().getNome()`: fornece o nome da classe para a futura tela.

Exemplo de evolucao: 100 XP Backend produz Mago de nivel 2; mais 100 XP Frontend produz Fullstack de nivel 3; mais 50 XP Frontend produz Ladino de nivel 3. O mesmo objeto e mantido em todas as etapas.

Michael implementara masmorras e desafios separadamente. Antes de integrar, devemos combinar custo e recompensa. A tentativa consome energia; so uma resposta correta e ainda nao recompensada chama `ganharExperiencia`. Respostas invalidas devem ser rejeitadas antes de consumir energia. A interface Swing sera trabalho conjunto depois dos objetos de dominio.

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

Parte de dominio do Personagem implementada: XP, nivel, energia, descanso e quatro arquetipos, com testes no terminal. Masmorras, desafios, conclusoes, avatares e interface Swing ainda nao foram implementados. A antiga base Spring Boot, PostgreSQL e Docker permanece apenas no historico Git.