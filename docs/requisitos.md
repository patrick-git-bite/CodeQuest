# Requisitos e regras de negocio

## Atores

- **Estudante:** cria o personagem e realiza atividades.
- **Administrador ou professor:** cadastra escolas, masmorras, atividades e recompensas.

## Requisitos funcionais

- **RF01:** permitir o cadastro e a autenticacao do estudante.
- **RF02:** permitir que cada estudante crie um personagem.
- **RF03:** listar escolas, masmorras e atividades disponiveis.
- **RF04:** validar energia, nivel e pre-requisitos antes de iniciar uma masmorra.
- **RF05:** registrar respostas e tentativas das atividades.
- **RF06:** conceder experiencia geral e experiencia na escola relacionada.
- **RF07:** aumentar o nivel do personagem ao atingir a experiencia necessaria.
- **RF08:** recalcular automaticamente o arquetipo depois de uma recompensa.
- **RF09:** desbloquear habilidades e masmorras ao cumprir os requisitos.
- **RF10:** armazenar o progresso e o historico de mudancas de arquetipo.

## Regras de negocio iniciais

- **RN01:** todo personagem inicia como Aprendiz, no nivel 1 e com 100 pontos de energia.
- **RN02:** uma recompensa de conclusao so pode ser recebida uma vez por atividade.
- **RN03:** quizzes possuem correcao automatica; desafios praticos dependem de avaliacao manual.
- **RN04:** atividades podem consumir energia e respostas incorretas podem aplicar uma penalidade adicional.
- **RN05:** uma masmorra bloqueada nao pode ser iniciada antes do cumprimento de seus pre-requisitos.
- **RN06:** o arquetipo e determinado pela distribuicao de experiencia entre as escolas.
- **RN07:** Arquimago Fullstack exige nivel minimo, experiencia minima em Backend e Frontend e equilibrio entre essas escolas.
- **RN08:** toda mudanca de arquetipo deve ser registrada no historico.

## Excecoes de dominio previstas

- `PreRequisitoNaoAtendidoException`
- `NivelInsuficienteException`
- `EnergiaEsgotadaException`
- `MasmorraJaConcluidaException`
- `RespostaInvalidaException`

## Criterio de sucesso do estudo de caso

O fluxo principal deve demonstrar um personagem Aprendiz tornando-se Mago do Backend e, depois de equilibrar seus estudos em Frontend, evoluindo para Arquimago Fullstack em tempo de execucao.
