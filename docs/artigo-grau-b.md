# CODEQUEST: DESENVOLVIMENTO DE UM RPG EDUCATIVO EM JAVA SWING APLICANDO PROGRAMAÇÃO ORIENTADA A OBJETOS

**Patrick Brando**  
patrick@acad.ftec.com.br  

**Michael S. Boff**  
michael@acad.ftec.com.br  

**PROFESSOR ORIENTADOR: Jason Santos**  
jason.santos@acad.ftec.com.br  

**Programação Orientada a Objetos**  
**Caxias do Sul - RS**

---

**Resumo:** O aprendizado inicial de programação frequentemente enfrenta desafios de engajamento durante a fixação de conceitos teóricos de desenvolvimento de software. Este trabalho tem como objetivo desenvolver o *CodeQuest*, um jogo desktop no estilo RPG educativo voltado à revisão prática de conteúdos de *Backend* e *Frontend*, aplicando de forma integrada os pilares da Programação Orientada a Objetos (POO) e o tratamento de exceções em Java. A metodologia adotou a linguagem Java 21 em arquitetura separada entre modelo de domínio e interface gráfica construída com a biblioteca Java Swing, utilizando controle de versão colaborativo no GitHub e baterias próprias de testes automatizados. No domínio, aplicaram-se encapsulamento estrito na gestão de experiência e energia do personagem, tipos enumerados para trilhas e arquétipos, composição em coleções imutáveis, classes abstratas, herança, polimorfismo e exceções customizadas de negócio. Como resultado, obteve-se uma aplicação funcional com seis ordens visuais evolutivas em três estágios narrativos, cálculo dinâmico de especialização técnica, dez desafios interativos de múltipla escolha e leitura com recompensa única e validação completa das regras de negócio sem acoplamento à camada visual.

**Palavras-chave:** Programação Orientada a Objetos. Java. Swing. Gamificação. Tratamento de Exceções.

---

## 1 INTRODUÇÃO

O ensino e a prática de engenharia de software exigem a assimilação simultânea de fundamentos conceituais — como abstração, modularidade e regras de negócio — e de tecnologias voltadas tanto ao desenvolvimento *Backend* quanto ao *Frontend*. Nesse cenário amplo da educação em computação, estratégias baseadas em elementos de jogos (*gamificação* e jogos de interpretação de papéis, ou RPG) destacam-se por transformar exercícios de revisão em ciclos curtos de desafio, feedback imediato e progressão visível ao estudante.

Apesar da ampla oferta de plataformas online extensas, estudantes iniciantes frequentemente carecem de ferramentas leves, executáveis localmente e cujo próprio código-fonte sirva como objeto de estudo claro de arquitetura orientada a objetos. O foco deste projeto consiste em preencher essa lacuna prática por meio da construção do *CodeQuest*, um software desktop autoral no qual o estudante cria um aventureiro, resolve atividades temáticas de programação na Academia e observa seu personagem evoluir tanto em nível geral e forma visual quanto em afinidade técnica (*Mago do Backend*, *Ladino do Frontend* ou *Arquimago Fullstack*).

A questão norteadora deste trabalho investiga como estruturar um jogo educativo funcional utilizando exclusivamente recursos nativos da plataforma Java, garantindo que todas as regras de progressão, consumo de energia, recompensa única e validação de respostas permaneçam protegidas no modelo de domínio e desacopladas da interface gráfica. O valor acadêmico e técnico da solução reside na aplicação concreta dos pilares de Programação Orientada a Objetos estudados no semestre combinada ao uso da biblioteca gráfica *Java Swing* como diferencial tecnológico.

Quanto à delimitação do estudo, o escopo abrange uma aplicação desktop monousuário em Java 21, com estado mantido em memória durante a sessão de uso, contemplando criação de personagem, seis ordens com três estágios visuais e narrativos por nível, duas trilhas de conhecimento e um catálogo de dez desafios polimórficos de *Quiz* e *Leitura* (600 XP totais). Não fazem parte do escopo autenticação em rede, banco de dados relacional, servidor web, loja de itens, modo multijogador ou compilação dinâmica de código arbitrário enviado pelo usuário.

---

## 2 OBJETIVOS

### 2.1 Objetivo Geral

Desenvolver o software desktop *CodeQuest* em Java 21 com interface gráfica em *Java Swing*, aplicando os conceitos fundamentais de Programação Orientada a Objetos e o tratamento de exceções na construção de um RPG educativo de estudos de programação.

### 2.2 Objetivos Específicos

* Modelar um domínio orientado a objetos coeso e encapsulado para representar personagens, trilhas de estudo, arquétipos evolutivos e atividades avaliativas;
* Implementar abstração, herança e polimorfismo por meio da superclasse abstrata `Desafio` e das subclasses concretas `Quiz` e `Leitura`, avaliando diferentes formatos de estudo sob um contrato único;
* Realizar o tratamento de exceções com classes customizadas de domínio (`EnergiaEsgotadaException` e `RespostaInvalidaException`) para impedir estados inválidos em situações de energia insuficiente e entrada malformada;
* Construir uma interface gráfica temática 2D utilizando *Java Swing* como tecnologia complementar, preservando a separação entre visualização e regras de negócio;
* Validar o comportamento do domínio e dos componentes visuais por meio de classes de testes automatizados executáveis sem dependências externas.

---

## 3 MATERIAL, MÉTODOS E REFERENCIAL TEÓRICO

O desenvolvimento do *CodeQuest* foi conduzido pelos dois autores utilizando fluxo colaborativo de branches no *Git* e *GitHub* (`main` para entregas estáveis e `feat/masmorra-desafio` para evolução incremental e integração das classes de desafios). O ambiente técnico baseou-se no *OpenJDK 21 (LTS)*, empregando exclusivamente pacotes nativos da biblioteca padrão do Java (`java.util`, `java.nio.file`, `java.awt`, `javax.swing` e `javax.imageio`), assegurando reprodutibilidade exata em qualquer estação de trabalho com JDK 21 instalado.

### 3.1 Arquitetura e Pilares de Orientação a Objetos

O código-fonte foi dividido nos pacotes `codequest.dominio` e `codequest.interfacegrafica`, além do ponto de entrada `codequest.Main` e da árvore `tests/`. Os conceitos de POO estudados na disciplina foram aplicados nos seguintes componentes:

* **Encapsulamento e Invariantes de Estado (`Personagem`):** Os atributos internos (`nome`, `experienciaBackend`, `experienciaFrontend` e `energia`) possuem visibilidade `private` e não expõem métodos *setters* públicos. O nível geral (`1 + experienciaGeral / 100`) e o arquétipo técnico são calculados dinamicamente a partir do XP acumulado, evitando dessincronização de estado, enquanto `Math.addExact` previne *overflow* aritmético de inteiros.
* **Tipos Enumerados (`Trilha` e `Arquetipo`):** Restringem em tempo de compilação os eixos de estudo (`BACKEND` e `FRONTEND`) e encapsulam os títulos formatados de especialização (`Aprendiz`, `Mago do Backend`, `Ladino do Frontend` e `Arquimago Fullstack`).
* **Classe Abstrata, Herança e Polimorfismo (`Desafio`, `Quiz` e `Leitura`):** `Desafio` foi declarada como `abstract class`, reunindo os atributos imutáveis de título, trilha, recompensa de XP, custo de energia e o estado `concluido`. No método `public final boolean tentar(Personagem, String)` — estruturado segundo o padrão comportamental *Template Method* (Gamma et al., 2000) — a superclasse controla a ordem invariável de validação, desconto de energia e recompensa única, delegando `respostaValida` e `avaliarResposta` polimorficamente às subclasses `Quiz` e `Leitura`.
* **Composição, Sobrecarga e Coleções Imutáveis (`JanelaJogo` e `Quiz`):** `Quiz` aplica cópia defensiva via `List.copyOf(alternativas)`, impedindo mutações externas na lista de opções, enquanto `JanelaJogo` compõe `Personagem` e `List<Desafio>` por meio de três construtores sobrecarregados.
* **Tratamento de Exceções (`EnergiaEsgotadaException` e `RespostaInvalidaException`):** Argumentos nulos ou não positivos nos construtores lançam `IllegalArgumentException` (Deitel; Deitel, 2016), enquanto violações de regras de negócio durante a partida lançam exceções específicas: `RespostaInvalidaException` rejeita tentativas vazias ou fora da faixa antes de descontar energia, e `EnergiaEsgotadaException` interrompe tentativas sem saldo suficiente preservando a energia restante.

### 3.2 Diferencial Tecnológico: Interface Gráfica com Java Swing e Testes Automatizados

Como tecnologia extra além do escopo básico de console, desenvolveu-se a classe `JanelaJogo` em *Java Swing* e *AWT*, customizando barras de progresso (`BasicProgressBarUI`), listas de atividades (`JList` com renderizador próprio), barras de rolagem (`BasicScrollBarUI`), alternância de painéis via `CardLayout` e carregamento resiliente de retratos e cenas PNG 2D (`ImageIO`). Os eventos de interface apenas invocam os métodos do domínio dentro de blocos `try-catch` e refletem o estado atualizado na tela. Para permitir repetição exata dos testes por terceiros, implementaram-se três suítes automatizadas (`PersonagemTeste`, `DesafioTeste` e `JanelaJogoTeste`).

---

## 4 RESULTADOS E DISCUSSÃO

O software resultou em uma aplicação desktop completa composta por painel lateral fixo de perfil do personagem e área central alternável entre o *Salão do Herói* e o painel *Desafios da Academia*. Na criação da partida, o estudante escolhe entre seis ordens (*Aventureiro*, *Bardo*, *Paladino*, *Guerreiro*, *Bruxo* e *Feiticeiro*), conforme apresentado na Figura 1.

![Figura 1 — Tela inicial do Salão do Herói após criação do personagem](../out/janela-compacta.png)

Na aba *Desafios da Academia* (Figura 2), o catálogo disponibiliza dez atividades (totalizando 600 XP, sendo 300 XP em *Backend* e 300 XP em *Frontend*). A interface renderiza polimorficamente cada item selecionado na lista lateral: instâncias de `Quiz` geram botões de alternativa interativos com destaque cromático imediato (verde para alternativa correta e vermelho para incorreta), enquanto instâncias de `Leitura` exibem o pergaminho conceitual e a confirmação de estudo.

![Figura 2 — Resolução polimórfica de Quiz com feedback visual de acerto e progressão de XP](../out/janela-desafio-quiz.png)

Paralelamente ao cálculo do arquétipo técnico (*Mago do Backend*, *Ladino do Frontend* ou *Arquimago Fullstack* quando há empate a partir de 100 XP em cada trilha), o jogo realiza revelações automáticas no *Salão do Herói* conforme o nível geral avança: nos níveis 1 e 2 exibe-se o retrato comum de Aprendiz; nos níveis 3 e 4 desperta-se o retrato específico da Ordem escolhida; e a partir do nível 5 (400+ XP) desbloqueia-se a *Ascensão Veterana* com cena completa de ação e crônica personalizada unindo nome, ordem e arquétipo do jogador (Figura 3).

![Figura 3 — Ascensão Veterana no Nível 5 com cena de ação e narrativa personalizada da classe](../out/janela-nivel-5.png)

A execução das suítes automatizadas `PersonagemTeste`, `DesafioTeste` e `JanelaJogoTeste` comprovou objetivamente todos os cenários planejados: (a) tentativas sem seleção de alternativa lançam `RespostaInvalidaException` preservando 100% da energia; (b) respostas incorretas descontam energia sem conceder XP; (c) acertos concedem XP uma única vez e travam recompensas repetidas; e (d) falta de energia lança `EnergiaEsgotadaException` até que o botão *Descansar* restaure os 100 pontos de energia.

---

## 5 CONSIDERAÇÕES FINAIS

O desenvolvimento do *CodeQuest* atingiu integralmente os objetivos geral e específicos propostos para o Trabalho de Grau B. Construiu-se um RPG educativo funcional em Java 21 e *Java Swing* que demonstra, de forma prática e verificável por testes automatizados, a eficácia do encapsulamento, da herança, das classes abstratas, do polimorfismo, da composição e do tratamento de exceções customizadas na proteção das regras de negócio de um software.

Como recomendações para trabalhos futuros, sugerem-se a adição de persistência local do progresso em arquivos JSON ou banco embarcado SQLite, o carregamento externo de novos pacotes de desafios e a expansão para novas trilhas temáticas, como Banco de Dados e Arquitetura de Software.

---

## REFERÊNCIAS

DEITEL, Paul; DEITEL, Harvey. **Java: como programar**. 10. ed. São Paulo: Pearson Prentice Hall, 2016.

GAMMA, Erich; HELM, Richard; JOHNSON, Ralph; VLISSIDES, John. **Padrões de projetos: soluções reutilizáveis de software orientado a objetos**. Porto Alegre: Bookman, 2000.

ORACLE. **JDK 21 Documentation: Java Platform, Standard Edition & Java Development Kit Version 21 API Specification**. Redwood Shores: Oracle Corporation, 2023. Disponível em: <https://docs.oracle.com/en/java/javase/21/>. Acesso em: 19 nov. 2026.

SCHILDT, Herbert. **Java: a referência completa**. 11. ed. Porto Alegre: Bookman, 2020.
