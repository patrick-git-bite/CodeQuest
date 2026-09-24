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

## Stack definida

| Camada | Tecnologia | Motivo |
| --- | --- | --- |
| Linguagem | Java 21 | Versao LTS moderna e adequada ao estudo de POO |
| Framework | Spring Boot | Simplifica a API, injecao de dependencias, validacoes e acesso ao banco |
| Interface | Thymeleaf e Bootstrap | Permitem criar as telas no mesmo projeto Java, reduzindo a complexidade para a dupla |
| Persistencia | Spring Data JPA e Hibernate | Integram os objetos Java ao banco relacional |
| Banco de dados | PostgreSQL | Gratuito, open source e apropriado para os relacionamentos do dominio |
| Migracoes | Flyway | Mantem a estrutura do banco versionada junto ao codigo |
| Build | Maven | Gerencia dependencias, testes e empacotamento |
| Testes | JUnit 5 e Mockito | Cobrem as regras de negocio e os padroes de projeto |

O MVP sera uma aplicacao web unica. Frontend e backend separados, aplicativo mobile e interface 3D nao fazem parte do escopo inicial.

## Banco de dados

O banco escolhido e o **PostgreSQL**. Durante o desenvolvimento, ele podera ser executado gratuitamente na maquina de cada integrante, preferencialmente com Docker Compose para manter a mesma versao e configuracao.

Para uma demonstracao publicada na internet, podera ser usado um servico com plano gratuito, como Neon ou Supabase. Esses planos possuem limites e podem mudar, por isso a aplicacao nao dependera de recursos exclusivos de um provedor. O PostgreSQL local continuara sendo a referencia do projeto.

O H2 podera ser usado apenas em testes automatizados rapidos. Ele nao substituira o PostgreSQL no desenvolvimento nem na demonstracao final, evitando diferencas de comportamento entre os ambientes.

## Ferramentas de modelagem

Todas as ferramentas iniciais possuem opcao gratuita:

| Finalidade | Ferramenta recomendada | Uso no projeto |
| --- | --- | --- |
| UML e fluxos | diagrams.net | Diagramas de classes, casos de uso e sequencia |
| Modelo do banco | dbdiagram.io | Diagrama entidade-relacionamento e definicao das tabelas |
| Prototipos de telas | Figma | Organizacao das telas antes da implementacao |
| Diagramas versionados | Mermaid | Diagramas simples armazenados nos arquivos Markdown do repositorio |
| Personagens 2D | Universal LPC Spritesheet Generator | Geracao inicial de avatares e sprites customizaveis |
| Edicao de pixel art | Piskel | Ajustes nos sprites e criacao de elementos visuais simples |

O projeto usara personagens **2D em pixel art**. Isso permite variacoes de aparencia e arquetipo sem exigir modelagem 3D. Antes de incluir qualquer sprite no repositorio, a licenca e os creditos do recurso utilizado deverao ser registrados.

## Organizacao da dupla

- cada funcionalidade sera implementada em uma branch propria e revisada pelo outro integrante;
- ambos trabalharao com Java e regras de POO, evitando separar a equipe apenas entre frontend e backend;
- tarefas, responsaveis, dependencias e criterios de aceite serao acompanhados no Notion;
- decisoes tecnicas e diagramas finais serao mantidos tambem no repositorio.

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

Projeto em fase de modelagem. A stack inicial foi definida com Java 21, Spring Boot, Thymeleaf e PostgreSQL; as regras e os diagramas ainda serao refinados antes da implementacao.
