# Modelo de dominio

## Entidades principais

- **Usuario:** representa a conta do estudante.
- **Personagem:** concentra nivel, energia, experiencia, habilidades e arquetipo atual.
- **Escola:** representa uma trilha de conhecimento.
- **Masmorra:** agrupa atividades, dificuldade, requisitos e recompensas.
- **Atividade:** representa uma leitura, quiz, exercicio textual ou desafio pratico.
- **Habilidade:** representa uma competencia desbloqueavel.
- **ProgressoMasmorra:** registra status, tentativas e conclusao.
- **HistoricoArquetipo:** registra as mudancas de classe do personagem.

## Diagrama conceitual inicial

```mermaid
classDiagram
    Usuario "1" --> "1" Personagem
    Personagem "1" o-- "*" Habilidade
    Personagem "1" --> "*" ProgressoMasmorra
    Personagem "1" --> "*" HistoricoArquetipo
    Personagem --> EstrategiaArquetipo
    Escola "1" --> "*" Masmorra
    Escola "1" --> "*" Habilidade
    Masmorra "1" *-- "*" Atividade
    ProgressoMasmorra "*" --> "1" Masmorra

    class EstrategiaArquetipo {
        <<interface>>
        +calcularBonus()
        +aplicarRecompensa()
    }

    EstrategiaArquetipo <|.. MagoBackend
    EstrategiaArquetipo <|.. LadinoFrontend
    EstrategiaArquetipo <|.. PaladinoSeguranca
    EstrategiaArquetipo <|.. NecromanteDados
    EstrategiaArquetipo <|.. ArquimagoFullstack
```

## Padroes previstos

- **Strategy:** encapsula os bonus de cada arquetipo.
- **State:** controla os estados de energia do personagem e as atividades permitidas.
- **Factory:** seleciona a estrategia de arquetipo a partir da experiencia por escola.

O modelo sera refinado antes da implementacao para definir atributos, operacoes, cardinalidades e persistencia.
