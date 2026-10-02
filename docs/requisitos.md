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
- XP em Backend ou Frontend determina Mago do Backend ou Ladino do Frontend; equilibrio com experiencia suficiente permite Arquimago Fullstack.
- Custos, penalidades, limites de nivel e regras de empate ainda devem ser definidos pela dupla. As formulas da plataforma anterior nao sao obrigatorias.

## Excecoes previstas

- `PreRequisitoNaoAtendidoException`
- `EnergiaEsgotadaException`
- `RespostaInvalidaException`

## Validacao do MVP

Criar personagem, responder quiz, receber XP, atualizar nivel e arquetipo e ver o resultado na janela. Testar resposta incorreta, recompensa repetida, masmorra bloqueada e descanso.

## Fora do escopo

Login, banco, servidor, administracao de conteudo, habilidades aprimoraveis, moedas, loja, envio de arquivos e avaliacao por professor. Persistencia em arquivo sera considerada apenas se sobrar tempo.