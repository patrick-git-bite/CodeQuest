# Requisitos e regras de negocio

## Ator

**Estudante:** utiliza o programa desktop local para estudar e evoluir um personagem. Nao existem contas ou administrador.

## Requisitos funcionais

- **RF01:** criar um personagem com nome e avatar.
- **RF02:** mostrar nivel, arquetipo, XP geral, XP por trilha e energia.
- **RF03:** listar tres masmorras com desafios fixos de Backend e Frontend.
- **RF04:** verificar energia, nivel e pre-requisitos antes de iniciar um desafio.
- **RF05:** apresentar quizzes, receber alternativas e informar o resultado.
- **RF06:** conceder XP uma unica vez por desafio concluido.
- **RF07:** atualizar nivel e arquetipo conforme a experiencia adquirida.
- **RF08:** recuperar energia ao descansar, sem ultrapassar 100.
- **RF09:** mostrar erros de entrada e de regras de negocio na janela Swing.

## Regras iniciais

- O personagem inicia como Aprendiz, nivel 1, XP zero e energia 100.
- O progresso existe apenas durante a execucao do programa.
- Resposta incorreta nao concede XP; resposta correta conclui o quiz e concede sua recompensa uma unica vez.
- Leitura simples pode ser concluida por confirmacao, conforme o desafio cadastrado.
- Masmorras bloqueadas exigem o cumprimento dos requisitos cadastrados.
- O nivel e `1 + XP geral / 100`, usando divisao inteira: 99 XP corresponde ao nivel 1, 100 ao nivel 2 e 200 ao nivel 3.
- Backend maior que Frontend determina Mago; Frontend maior determina Ladino.
- XP igual nas duas trilhas, com pelo menos 100 XP em cada, determina Arquimago Fullstack. Empate abaixo desse limite determina Aprendiz.
- O arquetipo pode mudar novamente quando o foco de estudo mudar. Fullstack nao e um desbloqueio permanente.
- Descanso recupera a energia para 100. Consumo maior que a energia disponivel lanca excecao e preserva o valor anterior.
- Nome vazio, trilha nula e quantidades nao positivas sao rejeitados. XP que excede o limite de `int` tambem e rejeitado antes de alterar o estado.
- Custos e recompensas dos desafios ainda devem ser definidos pela dupla. Sugestao inicial: 50 XP por acerto e 10 de energia por tentativa. Esses valores nao estao fixados na classe Personagem.

## Excecoes previstas

- `PreRequisitoNaoAtendidoException`
- `EnergiaEsgotadaException`
- `RespostaInvalidaException`

## Validacao do MVP

Criar personagem, responder quiz, receber XP, atualizar nivel e arquetipo e ver o resultado na janela. Testar resposta incorreta, recompensa repetida, masmorra bloqueada e descanso.

## Fora do escopo

Login, banco, servidor, administracao de conteudo, habilidades aprimoraveis, moedas, loja, envio de arquivos e avaliacao por professor. Persistencia em arquivo sera considerada apenas se sobrar tempo.