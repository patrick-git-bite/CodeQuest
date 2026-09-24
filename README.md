# CodeQuest

CodeQuest e um RPG educacional para estudantes de programacao. O jogador evolui um personagem ao concluir leituras, quizzes e desafios praticos organizados em masmorras. A experiencia obtida em cada trilha de estudo altera dinamicamente o arquetipo do personagem.

## Objetivo

O projeto sera usado como estudo de caso de Programacao Orientada a Objetos, com foco em:

- encapsulamento, heranca, polimorfismo e composicao;
- padroes Strategy, State e Factory;
- tratamento de excecoes customizadas;
- persistencia do progresso em banco de dados;
- testes das regras de evolucao do personagem.

## Arquetipos

| Arquetipo | Trilha predominante |
| --- | --- |
| Mago do Backend | Logica, APIs, algoritmos e banco de dados |
| Ladino do Frontend | Interfaces, HTML, CSS e JavaScript |
| Paladino da Seguranca | Criptografia, protecao e boas praticas |
| Necromante dos Dados | Estatistica, analise de dados e IA |
| Arquimago Fullstack | Equilibrio avancado entre Backend e Frontend |

O personagem comeca como **Aprendiz**. Seu arquetipo e recalculado conforme a distribuicao de experiencia entre as escolas, sem recriar o personagem.

## Masmorras iniciais

1. **Caverna da Sintaxe:** atividades sobre variaveis, condicionais e repeticoes.
2. **Labirinto da Persistencia:** atividades sobre SQL, modelagem e CRUD.
3. **Torre da Arquitetura:** Boss Fight sobre APIs, validacoes, excecoes e padroes de projeto.

As atividades iniciais serao leituras, quizzes, exercicios textuais e desafios praticos. O MVP nao executara nem corrigira codigo automaticamente.

## Escopo do MVP

- cadastro de usuario e personagem;
- listagem de escolas, masmorras e atividades;
- verificacao de nivel, energia e pre-requisitos;
- registro de respostas e conclusao de atividades;
- concessao de experiencia geral e por escola;
- evolucao de nivel e mudanca dinamica de arquetipo;
- desbloqueio de habilidades e novas masmorras;
- consulta do progresso e historico de arquetipos.

Ficam fora do MVP: multiplayer, combate em tempo real, integracao com GitHub, pagamentos, IA corretora e compilacao de codigo.

## Estrutura inicial

```text
CodeQuest/
|-- docs/
|   |-- modelo-dominio.md
|   `-- requisitos.md
|-- src/                  # implementacao futura
|-- tests/                # testes futuros
|-- .gitignore
`-- README.md
```

## Documentacao

- [Requisitos e regras de negocio](docs/requisitos.md)
- [Modelo de dominio](docs/modelo-dominio.md)

## Status

Projeto em fase de modelagem. A linguagem, o framework e o banco de dados ainda serao definidos antes do inicio da implementacao.
