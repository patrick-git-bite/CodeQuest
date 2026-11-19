# CodeQuest

RPG desktop educativo de estudos de programacao em Java 21 e Java Swing, desenvolvido por **Patrick Brando** e **Michael S. Boff** para o Trabalho de Grau B da disciplina de Programacao Orientada a Objetos (Prof. Jason Santos).

## Proposta

O estudante escolhe uma das seis ordens de RPG (*Aventureiro*, *Bardo*, *Paladino*, *Guerreiro*, *Bruxo* ou *Feiticeiro*), cria seu personagem e resolve 10 desafios praticos de **Backend** e **Frontend** (600 XP totais), alternando entre atividades de **Quiz** e **Leitura**. Conforme acumula experiencia, seu personagem evolui de nivel, muda dinamicamente de arquetipo tecnico (*Aprendiz*, *Mago do Backend*, *Ladino do Frontend* ou *Arquimago Fullstack*) e desbloqueia novas artes e cronicas personalizadas ate atingir a consagracao Veterana no nivel 5+.

## Funcionalidades principais

- **Criacao de personagem:** escolha de nome e ordem de RPG com previa tematica.
- **Painel de atributos em tempo real:** retrato, nivel geral, afinidade de estudos, XP por trilha, barra de XP, barra de energia e botao *Descansar*.
- **Catalogo com 10 desafios (600 XP):** atividades polimorficas de `Quiz` (multipla escolha com destaque visual de acerto/erro) e `Leitura` (pergaminhos conceituais).
- **Recompensa unica:** cada desafio vencido registra sua conclusao no dominio e impede ganho duplicado de XP ou novo gasto de energia.
- **Evolucao visual e narrativa em 3 fases:**
  - **Niveis 1 e 2:** *Aprendiz da Ordem* com retrato inicial;
  - **Niveis 3 e 4 (200+ XP):** despertar do retrato proprio da Ordem;
  - **Nivel 5+ (400+ XP):** *Ascensao Veterana* com cena completa de acao (`_action.png`) e historia lendaria personalizada combinando nome, ordem e arquetipo.

## Conceitos de POO e Tecnologias

- **Java 21 (LTS) + Java Swing / Java 2D:** interface grafica desktop customizada sem frameworks externos.
- **Encapsulamento:** `Personagem` e `Desafio` protegem seus atributos internos sem setters publicos, derivando nivel e arquetipo sob demanda.
- **Classe Abstrata, Heranca e Polimorfismo:** `Desafio` define o contrato comum e centraliza o algoritmo no Template Method `public final boolean tentar(Personagem, String)`, especializado por `Quiz` e `Leitura`.
- **Composicao, Enums e Colecoes Imutaveis:** uso de `Trilha`, `Arquetipo` e copias defensivas com `List.copyOf`.
- **Tratamento de Excecoes:** excecoes customizadas de dominio (`EnergiaEsgotadaException` e `RespostaInvalidaException`), alem de validacoes de invariantes com `IllegalArgumentException` e `Math.addExact`.

## Estrutura do projeto

```text
src/       # pacotes codequest.dominio, codequest.interfacegrafica e Main
resources/ # retratos e cenas de acao em PNG + creditos
tests/     # suites automatizadas de dominio e interface Swing
docs/      # requisitos, modelo UML e artigo academico do Grau B
```

## Como compilar e executar

Com JDK 21 instalado (com suporte grafico para abrir a janela Swing), execute na raiz do projeto:

```bash
javac -d out src/codequest/dominio/*.java src/codequest/interfacegrafica/*.java src/codequest/Main.java
java -cp out:resources codequest.Main
```

> No Windows, utilize `;` no lugar de `:` (`java -cp out;resources codequest.Main`). Tambem e possivel abrir o projeto no VS Code / IntelliJ / Eclipse e clicar diretamente em **Run** em `src/codequest/Main.java`.

## Como executar os testes automatizados

```bash
javac -d out src/codequest/dominio/*.java src/codequest/interfacegrafica/*.java src/codequest/Main.java tests/codequest/dominio/*.java tests/codequest/interfacegrafica/*.java
java -cp out codequest.dominio.PersonagemTeste
java -cp out codequest.dominio.DesafioTeste
java -cp out:resources codequest.interfacegrafica.JanelaJogoTeste
```

## Documentacao

- [Requisitos e regras de negocio](docs/requisitos.md)
- [Modelo de dominio e diagrama UML](docs/modelo-dominio.md)
- [Artigo do Trabalho de Grau B (Markdown)](docs/artigo-grau-b.md)
- [Artigo do Trabalho de Grau B (Word .docx)](docs/Artigo_CodeQuest_Grau_B.docx)
