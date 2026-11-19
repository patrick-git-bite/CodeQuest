# Requisitos e regras de negocio

## Ator

**Estudante:** utiliza o programa desktop local para estudar conteudos de Backend e Frontend e evoluir seu personagem em estilo RPG. Nao existem contas remotas ou administrador.

## Requisitos funcionais

- **RF01:** criar um personagem escolhendo nome e uma entre seis ordens visuais de RPG.
- **RF02:** mostrar nivel, arquetipo de estudos, XP geral, XP por trilha e barra de energia.
- **RF03:** listar um catalogo fixo de 10 desafios da Academia divididos entre Backend e Frontend.
- **RF04:** apresentar polimorficamente desafios de `Quiz` (multipla escolha) e `Leitura` (estudo guiado) e informar o resultado visualmente.
- **RF05:** validar energia disponivel e formato da resposta antes de concluir uma tentativa.
- **RF06:** conceder XP uma unica vez por desafio concluido, bloqueando recompensas repetidas.
- **RF07:** atualizar nivel, arquetipo tecnico e evolucao visual/narrativa da ordem conforme a experiencia adquirida.
- **RF08:** recuperar energia ao descansar, restaurando o limite maximo de 100 pontos.
- **RF09:** exibir alertas claros de validacao e de excecoes de negocio na janela Swing.

## Regras de negocio implementadas

- O personagem inicia como `Aprendiz`, nivel 1, XP zero e energia 100.
- O progresso e mantido em memoria durante a execucao do programa.
- Tentativa com formato invalido (ex.: nenhuma alternativa selecionada) lanca `RespostaInvalidaException` antes de consumir energia.
- Resposta incorreta em `Quiz` consome a energia da tentativa, destaca a opcao incorreta e nao concede XP; resposta correta conclui o desafio e concede sua recompensa uma unica vez.
- `Leitura` e concluida mediante confirmacao explicita (`"CONFIRMAR"`), consumindo a energia configurada e concedendo XP uma unica vez.
- O nivel geral e calculado por `1 + XP geral / 100` (divisao inteira): 0 a 99 XP = nivel 1; 100 a 199 XP = nivel 2; 200 XP = nivel 3; 400 XP = nivel 5.
- `Backend > Frontend` define `Mago do Backend`; `Frontend > Backend` define `Ladino do Frontend`.
- Empate nas duas trilhas com pelo menos 100 XP em cada define `Arquimago Fullstack`; empate abaixo desse limite define `Aprendiz`.
- A ordem de RPG escolhida evolui visualmente e narrativamente conforme o nivel geral:
  - Niveis 1 e 2: retrato de Aprendiz e titulo *Aprendiz da Ordem*;
  - Niveis 3 e 4: retrato proprio da Ordem escolhida e revelacao no Salao do Heroi;
  - Nivel 5 em diante: cena completa de acao (`_action.png`), titulo *Veterano* e cronica lendaria personalizada com nome, ordem e arquetipo.
- Descanso recupera a energia para 100. Consumo maior que a energia disponivel lanca `EnergiaEsgotadaException` e preserva o saldo anterior.
- Nome vazio, trilha nula, quantidades nao positivas (`<= 0`) e overflow de `int` sao rejeitados antes de alterar o estado dos objetos.

## Excecoes customizadas do dominio

- `EnergiaEsgotadaException`
- `RespostaInvalidaException`

## Fora do escopo

Login, banco de dados, servidor web, administracao externa de conteudo, moedas, loja, multiplayer e execucao dinamica de codigo enviado pelo estudante.
