package codequest.interfacegrafica;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import codequest.dominio.Desafio;
import codequest.dominio.EnergiaEsgotadaException;
import codequest.dominio.Leitura;
import codequest.dominio.Personagem;
import codequest.dominio.Quiz;
import codequest.dominio.RespostaInvalidaException;
import codequest.dominio.Trilha;

public final class JanelaJogo extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color FUNDO = new Color(25, 29, 27);
    private static final Color PAINEL = new Color(39, 36, 32);
    private static final Color PAINEL_INTERNO = new Color(31, 30, 27);
    private static final Color TEXTO = new Color(242, 232, 208);
    private static final Color TEXTO_SUAVE = new Color(190, 178, 152);
    private static final Color OURO = new Color(206, 171, 91);
    private static final Color BOTAO_VINHO = new Color(91, 43, 42);
    private static final Color BOTAO_ATIVO = new Color(128, 62, 52);
    private static final Color VERDE_FUNDO = new Color(34, 72, 52);
    private static final Color VERDE_BORDA = new Color(98, 196, 138);
    private static final Color ERRO_FUNDO = new Color(84, 34, 34);
    private static final Color ERRO_BORDA = new Color(228, 112, 98);
    private static final Color AVISO_FUNDO = new Color(76, 60, 28);

    private final String[] arquivos = {"aprendiz", "bardo", "paladino", "guerreiro", "warlock", "sorcerer"};
    private final JComboBox<String> aparencia = new JComboBox<>(
            new String[] {"Aventureiro", "Bardo", "Paladino", "Guerreiro", "Bruxo", "Feiticeiro"});
    private final JTextField nome = new JTextField(16);
    private final JLabel avatar = new JLabel("", SwingConstants.CENTER);
    private final JLabel cena = new JLabel("", SwingConstants.CENTER);
    private final JLabel identidade = rotulo("Novo aventureiro", 22);
    private final JLabel especializacao = rotulo("Aprendiz", 16);
    private final JLabel bannerAscensao = rotulo("", 13);
    private final JLabel classe = rotulo("Aprendiz de Aventureiro", 24);
    private final JLabel tituloEpico = rotulo("", 15);
    private final JLabel fase = rotulo("O inicio de uma lenda", 15);
    private final JTextArea descricaoHeroi = new JTextArea(3, 36);
    private final JButton botaoContinuarJornada = new JButton("Continuar Desafios ->");
    private final JLabel ordemFixa = rotulo("Aventureiro", 18);
    private final JLabel experiencia = rotulo("Backend: 0 XP | Frontend: 0 XP", 14);
    private final JProgressBar barraXp = new JProgressBar(0, 100);
    private final JProgressBar barraEnergia = new JProgressBar(0, 100);
    private final JButton descansar = new JButton("Descansar");
    private final JLabel mensagem = rotulo("", 14);
    private final JPanel cadastro = painel(new BorderLayout(8, 6));

    private final CardLayout cartoesCentro = new CardLayout();
    private final JPanel areaCartoes = painel(cartoesCentro);
    private final JButton abaHeroi = new JButton("Salao do Heroi");
    private final JButton abaDesafios = new JButton("Desafios da Academia");
    private final JLabel contadorProgresso = rotulo("Concluidos: 0 / 0", 14);

    private final DefaultListModel<Desafio> modeloDesafios = new DefaultListModel<>();
    private final JList<Desafio> listaDesafios = new JList<>(modeloDesafios);
    private final JLabel badgeTipoTrilha = rotulo("", 12);
    private final JLabel badgeStatus = rotulo("", 12);
    private final JLabel tituloDesafio = rotulo("", 20);
    private final JLabel detalhesDesafio = rotulo("", 13);
    private final JTextArea textoAtividade = new JTextArea();
    private final JPanel painelOpcoes = new JPanel(new GridLayout(0, 1, 0, 8));
    private final ButtonGroup grupoOpcoes = new ButtonGroup();
    private final transient List<JToggleButton> botoesOpcoes = new ArrayList<>();
    private final transient Map<Desafio, Integer> indiceCorretoConhecido = new HashMap<>();
    private final JPanel faixaFeedback = new JPanel(new BorderLayout(10, 0));
    private final JLabel resultadoDesafio = rotulo("", 14);
    private final JButton responder = new JButton("Confirmar Resposta");
    private final JButton proximo = new JButton("Proximo ->");

    private transient Personagem personagem;
    private final transient List<Desafio> desafios;
    private boolean abaDesafiosAtiva;
    private boolean atualizandoLista;
    private int ultimoIndiceErrado = -1;

    public JanelaJogo() {
        this(null, criarCatalogoDesafios());
    }

    public JanelaJogo(Personagem personagem) {
        this(personagem, criarCatalogoDesafios());
    }

    public JanelaJogo(Personagem personagem, List<Desafio> desafios) {
        super("CodeQuest | Jornada do personagem");
        this.personagem = personagem;
        this.desafios = desafios == null ? List.of() : List.copyOf(desafios);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(860, 720));
        setSize(1140, 810);
        setLocationRelativeTo(null);

        JPanel conteudo = painel(new BorderLayout(16, 12));
        conteudo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO, 3), BorderFactory.createEmptyBorder(14, 18, 12, 18)));
        JPanel cabecalho = painel(new BorderLayout(8, 6));
        JLabel titulo = rotulo("CODEQUEST", 30);
        titulo.setForeground(OURO);
        cabecalho.add(titulo, BorderLayout.WEST);
        JLabel subtitulo = rotulo("CRONICAS DA ACADEMIA", 14);
        subtitulo.setHorizontalAlignment(SwingConstants.RIGHT);
        cabecalho.add(subtitulo, BorderLayout.EAST);
        cabecalho.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, OURO));
        conteudo.add(cabecalho, BorderLayout.NORTH);

        JPanel centro = painel(new BorderLayout(16, 12));
        centro.add(criarPerfil(), BorderLayout.WEST);
        centro.add(criarAreaPrincipal(), BorderLayout.CENTER);
        conteudo.add(centro, BorderLayout.CENTER);

        JPanel rodape = painel(new BorderLayout(8, 6));
        nome.setName("nome");
        nome.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        nome.setBackground(PAINEL);
        nome.setForeground(TEXTO);
        nome.setCaretColor(OURO);
        nome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        cadastro.add(rotulo("Nome do personagem", 13), BorderLayout.NORTH);
        cadastro.add(nome, BorderLayout.CENTER);
        JButton criar = new JButton("Criar personagem");
        estilizarBotao(criar);
        criar.setName("criar");
        criar.addActionListener(event -> criarPersonagem());
        cadastro.add(criar, BorderLayout.EAST);
        rodape.add(cadastro, BorderLayout.NORTH);
        mensagem.setName("mensagem");
        rodape.add(mensagem, BorderLayout.SOUTH);
        conteudo.add(rodape, BorderLayout.SOUTH);

        setContentPane(conteudo);

        aparencia.addActionListener(event -> atualizarImagens());
        descansar.addActionListener(event -> {
            if (this.personagem != null) {
                this.personagem.descansar();
                atualizarPerfil();
                definirFeedback("Energia restaurada em 100/100! Continue seus desafios.", VERDE_FUNDO, VERDE_BORDA);
                mensagem.setText("Energia recuperada. Sua jornada continua!");
            }
        });

        carregarListaDesafios(0);
        atualizarAbas();
        atualizarPerfil();
        atualizarImagens();
    }

    private JPanel criarAreaPrincipal() {
        JPanel principal = painel(new BorderLayout(0, 10));
        JPanel barraTopo = painel(new BorderLayout(10, 0));
        JPanel navegacao = painel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        abaHeroi.setName("abaHeroi");
        abaDesafios.setName("abaDesafios");
        abaHeroi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        abaDesafios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        abaHeroi.addActionListener(event -> alternarAba(false));
        abaDesafios.addActionListener(event -> alternarAba(true));
        navegacao.add(abaHeroi);
        navegacao.add(abaDesafios);
        barraTopo.add(navegacao, BorderLayout.WEST);

        contadorProgresso.setForeground(OURO);
        contadorProgresso.setHorizontalAlignment(SwingConstants.RIGHT);
        barraTopo.add(contadorProgresso, BorderLayout.EAST);
        principal.add(barraTopo, BorderLayout.NORTH);

        areaCartoes.add(criarPainelEstandarte(), "estandarte");
        areaCartoes.add(criarPainelDesafios(), "desafios");
        principal.add(areaCartoes, BorderLayout.CENTER);
        return principal;
    }

    private JPanel criarPainelEstandarte() {
        JPanel estandarte = painel(new BorderLayout(0, 8));

        JPanel topoHeroi = painel(new BorderLayout(0, 4));
        bannerAscensao.setName("bannerAscensao");
        bannerAscensao.setOpaque(true);
        bannerAscensao.setHorizontalAlignment(SwingConstants.CENTER);
        bannerAscensao.setFont(new Font(Font.SERIF, Font.BOLD, 13));
        bannerAscensao.setBackground(VERDE_FUNDO);
        bannerAscensao.setForeground(OURO);
        bannerAscensao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        bannerAscensao.setVisible(false);

        JPanel titulos = painel(new GridLayout(2, 1, 0, 1));
        classe.setName("classe");
        classe.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        classe.setForeground(OURO);
        classe.setHorizontalAlignment(SwingConstants.CENTER);
        tituloEpico.setName("tituloEpico");
        tituloEpico.setFont(new Font(Font.SERIF, Font.ITALIC, 16));
        tituloEpico.setForeground(TEXTO_SUAVE);
        tituloEpico.setHorizontalAlignment(SwingConstants.CENTER);
        titulos.add(classe);
        titulos.add(tituloEpico);

        topoHeroi.add(bannerAscensao, BorderLayout.NORTH);
        topoHeroi.add(titulos, BorderLayout.CENTER);
        estandarte.add(topoHeroi, BorderLayout.NORTH);

        JPanel molduraCena = painel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cena.setName("cena");
        cena.setPreferredSize(new Dimension(368, 368));
        molduraCena.add(cena);
        estandarte.add(molduraCena, BorderLayout.CENTER);

        JPanel pergaminhoLore = new JPanel(new BorderLayout(12, 6));
        pergaminhoLore.setBackground(PAINEL);
        pergaminhoLore.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));

        fase.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        fase.setForeground(OURO);
        fase.setHorizontalAlignment(SwingConstants.CENTER);
        pergaminhoLore.add(fase, BorderLayout.NORTH);

        descricaoHeroi.setName("descricaoHeroi");
        descricaoHeroi.setEditable(false);
        descricaoHeroi.setFocusable(false);
        descricaoHeroi.setLineWrap(true);
        descricaoHeroi.setWrapStyleWord(true);
        descricaoHeroi.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        descricaoHeroi.setBackground(PAINEL);
        descricaoHeroi.setForeground(TEXTO);
        pergaminhoLore.add(descricaoHeroi, BorderLayout.CENTER);

        botaoContinuarJornada.setName("continuarJornada");
        estilizarBotaoSecundario(botaoContinuarJornada);
        botaoContinuarJornada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoContinuarJornada.setVisible(false);
        botaoContinuarJornada.addActionListener(event -> {
            selecionarProximoPendente();
            alternarAba(true);
        });
        JPanel barraBotaoLore = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        barraBotaoLore.setBackground(PAINEL);
        barraBotaoLore.add(botaoContinuarJornada);
        pergaminhoLore.add(barraBotaoLore, BorderLayout.EAST);

        estandarte.add(pergaminhoLore, BorderLayout.SOUTH);
        return estandarte;
    }

    private void alternarAba(boolean mostrarDesafios) {
        this.abaDesafiosAtiva = mostrarDesafios;
        atualizarAbas();
        if (mostrarDesafios) {
            atualizarDesafioSelecionado(true);
            cartoesCentro.show(areaCartoes, "desafios");
        } else {
            atualizarImagens();
            cartoesCentro.show(areaCartoes, "estandarte");
        }
        areaCartoes.revalidate();
        areaCartoes.repaint();
    }

    private void atualizarAbas() {
        estilizarAba(abaHeroi, !abaDesafiosAtiva);
        estilizarAba(abaDesafios, abaDesafiosAtiva);
    }

    private JPanel criarPainelDesafios() {
        JPanel container = painel(new BorderLayout(12, 0));

        JPanel colunaLista = painel(new BorderLayout(0, 6));
        colunaLista.setPreferredSize(new Dimension(225, 380));
        JLabel rotuloLista = rotulo("DESAFIOS (" + desafios.size() + " ATIVIDADES)", 12);
        rotuloLista.setForeground(OURO);
        colunaLista.add(rotuloLista, BorderLayout.NORTH);

        listaDesafios.setName("listaDesafios");
        listaDesafios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaDesafios.setBackground(PAINEL_INTERNO);
        listaDesafios.setForeground(TEXTO);
        listaDesafios.setFixedCellHeight(56);
        listaDesafios.setCellRenderer(this::renderizarItemDesafio);
        listaDesafios.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !atualizandoLista) {
                ultimoIndiceErrado = -1;
                atualizarDesafioSelecionado(true);
            }
        });
        JScrollPane rolagemLista = new JScrollPane(listaDesafios);
        rolagemLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rolagemLista.setBorder(BorderFactory.createLineBorder(OURO));
        estilizarRolagem(rolagemLista);
        colunaLista.add(rolagemLista, BorderLayout.CENTER);
        container.add(colunaLista, BorderLayout.WEST);

        JPanel cartao = new JPanel(new BorderLayout(0, 12));
        cartao.setBackground(PAINEL);
        cartao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(14, 16, 14, 16)));

        JPanel cabecalhoAtividade = new JPanel(new BorderLayout(0, 6));
        cabecalhoAtividade.setBackground(PAINEL);
        JPanel linhaBadges = new JPanel(new BorderLayout(8, 0));
        linhaBadges.setBackground(PAINEL);
        badgeTipoTrilha.setOpaque(true);
        badgeTipoTrilha.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        badgeTipoTrilha.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        badgeStatus.setOpaque(true);
        badgeStatus.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        badgeStatus.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        linhaBadges.add(badgeTipoTrilha, BorderLayout.WEST);
        linhaBadges.add(badgeStatus, BorderLayout.EAST);

        JPanel blocoTitulo = new JPanel(new GridLayout(2, 1, 0, 2));
        blocoTitulo.setBackground(PAINEL);
        tituloDesafio.setName("tituloDesafio");
        tituloDesafio.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        tituloDesafio.setForeground(OURO);
        detalhesDesafio.setName("detalhesDesafio");
        detalhesDesafio.setForeground(TEXTO_SUAVE);
        blocoTitulo.add(tituloDesafio);
        blocoTitulo.add(detalhesDesafio);

        cabecalhoAtividade.add(linhaBadges, BorderLayout.NORTH);
        cabecalhoAtividade.add(blocoTitulo, BorderLayout.CENTER);
        cabecalhoAtividade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(90, 78, 52)),
                BorderFactory.createEmptyBorder(0, 0, 8, 0)));
        cartao.add(cabecalhoAtividade, BorderLayout.NORTH);

        JPanel conteudoRolavel = new JPanel(new BorderLayout(0, 12));
        conteudoRolavel.setBackground(PAINEL);

        textoAtividade.setName("textoAtividade");
        textoAtividade.setEditable(false);
        textoAtividade.setLineWrap(true);
        textoAtividade.setWrapStyleWord(true);
        textoAtividade.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        textoAtividade.setBackground(PAINEL_INTERNO);
        textoAtividade.setForeground(TEXTO);
        textoAtividade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(110, 92, 56)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        conteudoRolavel.add(textoAtividade, BorderLayout.NORTH);

        JPanel envoltorioOpcoes = new JPanel(new BorderLayout());
        envoltorioOpcoes.setBackground(PAINEL);
        painelOpcoes.setName("painelOpcoes");
        painelOpcoes.setBackground(PAINEL);
        envoltorioOpcoes.add(painelOpcoes, BorderLayout.NORTH);
        conteudoRolavel.add(envoltorioOpcoes, BorderLayout.CENTER);

        JScrollPane rolagemMiolo = new JScrollPane(conteudoRolavel);
        rolagemMiolo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rolagemMiolo.setBorder(BorderFactory.createEmptyBorder());
        rolagemMiolo.getViewport().setBackground(PAINEL);
        estilizarRolagem(rolagemMiolo);
        cartao.add(rolagemMiolo, BorderLayout.CENTER);

        JPanel rodapeAtividade = new JPanel(new BorderLayout(0, 8));
        rodapeAtividade.setBackground(PAINEL);

        faixaFeedback.setBackground(PAINEL_INTERNO);
        faixaFeedback.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        resultadoDesafio.setName("resultadoDesafio");
        resultadoDesafio.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        faixaFeedback.add(resultadoDesafio, BorderLayout.CENTER);
        rodapeAtividade.add(faixaFeedback, BorderLayout.NORTH);

        JPanel botoesAcao = new JPanel(new BorderLayout(8, 0));
        botoesAcao.setBackground(PAINEL);
        responder.setName("responder");
        estilizarBotao(responder);
        responder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        responder.addActionListener(event -> executarDesafioSelecionado());
        botoesAcao.add(responder, BorderLayout.CENTER);

        proximo.setName("proximo");
        estilizarBotaoSecundario(proximo);
        proximo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        proximo.addActionListener(event -> selecionarProximoPendente());
        botoesAcao.add(proximo, BorderLayout.EAST);
        rodapeAtividade.add(botoesAcao, BorderLayout.SOUTH);

        cartao.add(rodapeAtividade, BorderLayout.SOUTH);
        container.add(cartao, BorderLayout.CENTER);
        return container;
    }

    private Component renderizarItemDesafio(
            JList<? extends Desafio> lista, Desafio desafio, int indice, boolean selecionado, boolean foco) {
        JPanel item = new JPanel(new BorderLayout(4, 2));
        item.setOpaque(true);
        item.setBackground(selecionado ? BOTAO_VINHO : (indice % 2 == 0 ? PAINEL : PAINEL_INTERNO));
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, selecionado ? 4 : 0, 1, 0, selecionado ? OURO : new Color(65, 58, 48)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        String icone = desafio.isConcluido() ? "[OK] " : (indice + 1) + ". ";
        JLabel nomeItem = rotulo(icone + desafio.getTituloDesafio(), 13);
        nomeItem.setFont(new Font(Font.SERIF, Font.BOLD, 13));
        nomeItem.setForeground(desafio.isConcluido() ? VERDE_BORDA : (selecionado ? OURO : TEXTO));

        String tipo = desafio instanceof Quiz ? "Quiz" : "Leitura";
        JLabel meta = rotulo(
                tipo + " • " + nomeTrilha(desafio.getTrilha()) + " • +" + desafio.getRecompensaXp() + " XP",
                12);
        meta.setForeground(TEXTO_SUAVE);

        item.add(nomeItem, BorderLayout.NORTH);
        item.add(meta, BorderLayout.SOUTH);
        return item;
    }

    private JPanel criarPerfil() {
        JPanel perfil = painel(new BorderLayout(0, 12));
        perfil.setBackground(PAINEL);
        perfil.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        perfil.setPreferredSize(new Dimension(265, 480));
        aparencia.setName("aparencia");
        estilizarCombo(aparencia);
        avatar.setName("avatar");
        identidade.setName("identidade");
        especializacao.setName("especializacao");
        barraXp.setName("xp");
        barraEnergia.setName("energia");
        descansar.setName("descansar");
        barraXp.setStringPainted(true);
        barraXp.setForeground(OURO);
        barraEnergia.setStringPainted(true);
        barraEnergia.setForeground(new Color(70, 135, 104));
        estilizarBarra(barraXp);
        estilizarBarra(barraEnergia);
        estilizarBotao(descansar);
        JPanel retrato = painel(new BorderLayout(0, 6));
        avatar.setPreferredSize(new Dimension(112, 112));
        JPanel escolha = painel(new BorderLayout(0, 4));
        escolha.add(rotulo("ORDEM DO AVENTUREIRO", 12), BorderLayout.NORTH);
        escolha.add(aparencia, BorderLayout.CENTER);
        ordemFixa.setForeground(OURO);
        escolha.add(ordemFixa, BorderLayout.SOUTH);
        retrato.add(escolha, BorderLayout.NORTH);
        retrato.add(avatar, BorderLayout.CENTER);
        perfil.add(retrato, BorderLayout.NORTH);
        JPanel atributos = painel(new GridLayout(6, 1, 0, 6));
        atributos.add(identidade);
        atributos.add(especializacao);
        atributos.add(barraXp);
        atributos.add(experiencia);
        atributos.add(barraEnergia);
        atributos.add(descansar);
        perfil.add(atributos, BorderLayout.CENTER);
        return perfil;
    }

    private void criarPersonagem() {
        try {
            personagem = new Personagem(nome.getText());
            atualizarPerfil();
            atualizarDesafioSelecionado(true);
            mensagem.setText("Personagem criado! Abra 'Desafios da Academia' no topo para evoluir ate Veterano (Nivel 5).");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Nome invalido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void carregarListaDesafios(int indiceSelecionado) {
        atualizandoLista = true;
        try {
            modeloDesafios.clear();
            for (Desafio desafio : desafios) {
                modeloDesafios.addElement(desafio);
            }
            if (!desafios.isEmpty() && indiceSelecionado >= 0 && indiceSelecionado < desafios.size()) {
                listaDesafios.setSelectedIndex(indiceSelecionado);
            }
        } finally {
            atualizandoLista = false;
        }
        atualizarContadorProgresso();
        atualizarDesafioSelecionado(true);
    }

    private long contarConcluidos() {
        return desafios.stream().filter(Desafio::isConcluido).count();
    }

    private void atualizarContadorProgresso() {
        long concluidos = contarConcluidos();
        contadorProgresso.setText("Concluidos: " + concluidos + " / " + desafios.size());
        proximo.setEnabled(personagem != null && concluidos < desafios.size());
    }

    private void selecionarProximoPendente() {
        if (desafios.isEmpty()) {
            return;
        }
        int atual = Math.max(0, listaDesafios.getSelectedIndex());
        for (int deslocamento = 1; deslocamento <= desafios.size(); deslocamento++) {
            int candidato = (atual + deslocamento) % desafios.size();
            if (!desafios.get(candidato).isConcluido()) {
                listaDesafios.setSelectedIndex(candidato);
                listaDesafios.ensureIndexIsVisible(candidato);
                return;
            }
        }
    }

    private void atualizarDesafioSelecionado(boolean resetarMensagemPadrao) {
        painelOpcoes.removeAll();
        grupoOpcoes.clearSelection();
        botoesOpcoes.clear();

        Desafio desafio = listaDesafios.getSelectedValue();
        if (desafio == null) {
            tituloDesafio.setText("Nenhum desafio disponivel");
            detalhesDesafio.setText("");
            textoAtividade.setText("");
            responder.setEnabled(false);
            validate();
            repaint();
            return;
        }

        String tipo = desafio instanceof Quiz ? "QUIZ DE MULTIPLA ESCOLHA" : "PERGAMINHO DE LEITURA";
        badgeTipoTrilha.setText(tipo + "  •  " + nomeTrilha(desafio.getTrilha()).toUpperCase());
        badgeTipoTrilha.setBackground(desafio.getTrilha() == Trilha.BACKEND ? new Color(46, 58, 78) : new Color(74, 48, 72));
        badgeTipoTrilha.setForeground(OURO);

        if (desafio.isConcluido()) {
            badgeStatus.setText("CONCLUIDO");
            badgeStatus.setBackground(VERDE_FUNDO);
            badgeStatus.setForeground(VERDE_BORDA);
        } else {
            badgeStatus.setText("DISPONIVEL");
            badgeStatus.setBackground(AVISO_FUNDO);
            badgeStatus.setForeground(OURO);
        }

        tituloDesafio.setText(desafio.getTituloDesafio());
        detalhesDesafio.setText("Recompensa: +" + desafio.getRecompensaXp() + " XP em " + nomeTrilha(desafio.getTrilha())
                + "  |  Custo: -" + desafio.getCustoEnergia() + " Energia");

        boolean disponivel = personagem != null && !desafio.isConcluido();
        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F'};

        if (desafio instanceof Quiz quiz) {
            textoAtividade.setText(quiz.getEnunciado());
            JLabel dicaOpcoes = rotulo(
                    desafio.isConcluido()
                            ? "Alternativas do desafio (ja concluido):"
                            : "Clique em uma alternativa abaixo e confirme sua resposta:",
                    14);
            dicaOpcoes.setForeground(OURO);
            painelOpcoes.add(dicaOpcoes);

            Integer indiceCorreto = indiceCorretoConhecido.get(desafio);
            List<String> alternativas = quiz.getAlternativas();
            for (int i = 0; i < alternativas.size(); i++) {
                char letra = i < letras.length ? letras[i] : (char) ('1' + i);
                String prefixo = indiceCorreto != null && indiceCorreto == i ? "[CORRETA]  " : "";
                JToggleButton botaoOpcao = new JToggleButton(prefixo + letra + ")  " + alternativas.get(i));
                botaoOpcao.setName("opcao" + (i + 1));
                botaoOpcao.setHorizontalAlignment(SwingConstants.LEFT);
                botaoOpcao.setFocusPainted(false);
                botaoOpcao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                botaoOpcao.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
                botaoOpcao.setEnabled(disponivel);
                botaoOpcao.addActionListener(event -> {
                    ultimoIndiceErrado = -1;
                    estilizarOpcoesQuiz(desafio);
                });
                grupoOpcoes.add(botaoOpcao);
                botoesOpcoes.add(botaoOpcao);
                painelOpcoes.add(botaoOpcao);
            }
            estilizarOpcoesQuiz(desafio);
            responder.setText(desafio.isConcluido() ? "Quiz ja concluido" : "Confirmar Resposta");
        } else if (desafio instanceof Leitura leitura) {
            textoAtividade.setText(leitura.getConteudo());
            JPanel caixaDica = new JPanel(new BorderLayout());
            caixaDica.setBackground(desafio.isConcluido() ? VERDE_FUNDO : PAINEL_INTERNO);
            caixaDica.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(desafio.isConcluido() ? VERDE_BORDA : new Color(90, 78, 52)),
                    BorderFactory.createEmptyBorder(12, 14, 12, 14)));
            JLabel instrucao = rotulo(
                    desafio.isConcluido()
                            ? "Leitura concluida! Clique em 'Proximo ->' para ir ao proximo desafio."
                            : "Leia o pergaminho acima e clique em 'Concluir Leitura' para receber +"
                                    + desafio.getRecompensaXp() + " XP.",
                    15);
            instrucao.setForeground(desafio.isConcluido() ? TEXTO : OURO);
            caixaDica.add(instrucao, BorderLayout.CENTER);
            painelOpcoes.add(caixaDica);
            responder.setText(desafio.isConcluido() ? "Leitura ja concluida" : "Concluir Leitura");
        }

        if (resetarMensagemPadrao) {
            if (desafio.isConcluido()) {
                definirFeedback(
                        "Desafio concluido! Recompensa de +" + desafio.getRecompensaXp()
                                + " XP recebida. Use 'Proximo ->' para avancar.",
                        VERDE_FUNDO,
                        VERDE_BORDA);
            } else {
                definirFeedback(
                        "Pronto para tentar! Acertando voce ganha +" + desafio.getRecompensaXp() + " XP.",
                        PAINEL_INTERNO,
                        OURO);
            }
        }

        textoAtividade.setCaretPosition(0);
        responder.setEnabled(disponivel);
        validate();
        repaint();
    }

    private void estilizarOpcoesQuiz(Desafio desafio) {
        Integer indiceCorreto = indiceCorretoConhecido.get(desafio);
        boolean concluido = desafio.isConcluido();
        for (int i = 0; i < botoesOpcoes.size(); i++) {
            JToggleButton botao = botoesOpcoes.get(i);
            if (concluido && indiceCorreto != null && indiceCorreto == i) {
                botao.setBackground(VERDE_FUNDO);
                botao.setForeground(VERDE_BORDA);
                botao.setFont(new Font(Font.SERIF, Font.BOLD, 16));
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(VERDE_BORDA, 2),
                        BorderFactory.createEmptyBorder(8, 13, 8, 13)));
            } else if (concluido) {
                botao.setBackground(PAINEL_INTERNO);
                botao.setForeground(TEXTO_SUAVE);
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(75, 68, 56)),
                        BorderFactory.createEmptyBorder(9, 14, 9, 14)));
            } else if (i == ultimoIndiceErrado) {
                botao.setBackground(ERRO_FUNDO);
                botao.setForeground(TEXTO);
                botao.setFont(new Font(Font.SERIF, Font.BOLD, 16));
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ERRO_BORDA, 2),
                        BorderFactory.createEmptyBorder(8, 13, 8, 13)));
            } else if (botao.isSelected()) {
                botao.setBackground(BOTAO_ATIVO);
                botao.setForeground(OURO);
                botao.setFont(new Font(Font.SERIF, Font.BOLD, 16));
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(OURO, 2),
                        BorderFactory.createEmptyBorder(8, 13, 8, 13)));
            } else {
                botao.setBackground(PAINEL_INTERNO);
                botao.setForeground(TEXTO);
                botao.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(95, 82, 58)),
                        BorderFactory.createEmptyBorder(9, 14, 9, 14)));
            }
        }
    }

    private void definirFeedback(String texto, Color fundo, Color borda) {
        faixaFeedback.setBackground(fundo);
        faixaFeedback.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borda, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        resultadoDesafio.setForeground(TEXTO);
        resultadoDesafio.setText(texto);
    }

    private void executarDesafioSelecionado() {
        if (personagem == null) {
            definirFeedback("Crie um personagem antes de iniciar os desafios.", ERRO_FUNDO, ERRO_BORDA);
            return;
        }

        Desafio desafio = listaDesafios.getSelectedValue();
        if (desafio == null) {
            return;
        }

        int indiceOpcaoEscolhida = obterIndiceOpcaoSelecionada();
        String resposta = desafio instanceof Leitura
                ? "CONFIRMAR"
                : (indiceOpcaoEscolhida >= 0 ? String.valueOf(indiceOpcaoEscolhida + 1) : "");
        int nivelAnterior = personagem.getNivel();

        try {
            boolean acertou = desafio.tentar(personagem, resposta);
            atualizarPerfil();
            atualizarContadorProgresso();
            listaDesafios.repaint();

            if (acertou) {
                ultimoIndiceErrado = -1;
                if (indiceOpcaoEscolhida >= 0) {
                    indiceCorretoConhecido.put(desafio, indiceOpcaoEscolhida);
                }
                atualizarDesafioSelecionado(false);
                int novoNivel = personagem.getNivel();
                boolean despertouOrdem = nivelAnterior < 3 && novoNivel >= 3;
                boolean ascendeuVeterano = nivelAnterior < 5 && novoNivel >= 5;
                boolean concluiuTudo = !desafios.isEmpty() && contarConcluidos() == desafios.size();

                String evolucao = novoNivel > nivelAnterior ? " SUBIU PARA O NIVEL " + novoNivel + "!" : "";
                String textoSucesso = "CORRETO! +" + desafio.getRecompensaXp()
                        + " XP em " + nomeTrilha(desafio.getTrilha()) + "!" + evolucao;
                definirFeedback(textoSucesso, VERDE_FUNDO, VERDE_BORDA);
                mensagem.setText(textoSucesso);

                if (ascendeuVeterano || concluiuTudo || despertouOrdem) {
                    alternarAba(false);
                }
            } else {
                ultimoIndiceErrado = indiceOpcaoEscolhida;
                estilizarOpcoesQuiz(desafio);
                String textoErro = "RESPOSTA INCORRETA! -" + desafio.getCustoEnergia()
                        + " de energia. A alternativa marcada em vermelho esta errada; tente outra!";
                definirFeedback(textoErro, ERRO_FUNDO, ERRO_BORDA);
                mensagem.setText(textoErro);
            }
        } catch (RespostaInvalidaException exception) {
            definirFeedback(
                    "Selecione uma alternativa acima antes de clicar em Confirmar (nenhuma energia gasta).",
                    AVISO_FUNDO,
                    OURO);
            mensagem.setText(exception.getMessage());
        } catch (EnergiaEsgotadaException exception) {
            atualizarPerfil();
            definirFeedback(
                    "ENERGIA INSUFICIENTE! Clique em 'Descansar' a esquerda para recuperar 100/100.",
                    ERRO_FUNDO,
                    ERRO_BORDA);
            mensagem.setText(exception.getMessage());
        }
    }

    private int obterIndiceOpcaoSelecionada() {
        for (int i = 0; i < botoesOpcoes.size(); i++) {
            if (botoesOpcoes.get(i).isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private void atualizarPerfil() {
        cadastro.setVisible(personagem == null);
        aparencia.setEnabled(personagem == null);
        aparencia.setVisible(personagem == null);
        ordemFixa.setVisible(personagem != null);
        abaDesafios.setEnabled(personagem != null && !desafios.isEmpty());
        descansar.setEnabled(personagem != null && personagem.getEnergia() < 100);
        atualizarContadorProgresso();
        if (personagem == null) {
            barraXp.setString("XP: 0 / 100");
            barraEnergia.setValue(100);
            barraEnergia.setString("Energia: 100 / 100");
            return;
        }
        identidade.setText(personagem.getNome());
        especializacao.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        String afinidade = switch (personagem.getArquetipo()) {
            case APRENDIZ -> "Inicial";
            case MAGO_BACKEND -> "Backend";
            case LADINO_FRONTEND -> "Frontend";
            case ARQUIMAGO_FULLSTACK -> "Fullstack";
        };
        especializacao.setText("Estudos: " + afinidade + " | Nivel " + personagem.getNivel());
        int progresso = personagem.getExperienciaGeral() % 100;
        barraXp.setValue(progresso);
        barraXp.setString("XP: " + progresso + " / 100 | Total: " + personagem.getExperienciaGeral());
        experiencia.setText("Backend: " + personagem.getExperienciaBackend()
                + " | Frontend: " + personagem.getExperienciaFrontend());
        barraEnergia.setValue(personagem.getEnergia());
        barraEnergia.setString("Energia: " + personagem.getEnergia() + " / 100");
        atualizarImagens();
    }

    private void atualizarImagens() {
        int indiceOrdem = aparencia.getSelectedIndex();
        String arquivo = arquivos[indiceOrdem];
        String escolhida = (String) aparencia.getSelectedItem();
        ordemFixa.setText(escolhida);

        int nivel = personagem == null ? 1 : personagem.getNivel();
        boolean iniciante = nivel < 3;
        boolean veterano = nivel >= 5;
        boolean concluiuTudo = personagem != null && !desafios.isEmpty() && contarConcluidos() == desafios.size();
        boolean restamDesafios = personagem != null && contarConcluidos() < desafios.size();

        String retrato = iniciante ? "aprendiz" : arquivo;
        avatar.setIcon(carregarImagem(retrato + ".png", 96));
        cena.setIcon(carregarImagem(veterano ? arquivo + "_action.png" : retrato + ".png", 360));
        cena.setBorder(veterano
                ? BorderFactory.createLineBorder(OURO, 2)
                : BorderFactory.createEmptyBorder());

        classe.setText(iniciante ? "Aprendiz de " + escolhida : veterano ? escolhida + " Veterano" : escolhida);
        fase.setText(iniciante ? "O inicio de uma lenda" : veterano ? "Forjado nas masmorras" : "A jornada ganha forma");

        if (personagem == null) {
            bannerAscensao.setVisible(false);
            tituloEpico.setText("Escolha sua Ordem e inicie sua cronica na Academia");
            descricaoHeroi.setText(descricaoInicialOrdem(indiceOrdem));
            botaoContinuarJornada.setVisible(false);
            return;
        }

        String nomeHeroi = personagem.getNome();
        String nomeArquetipo = personagem.getArquetipo().getNome();

        if (concluiuTudo) {
            bannerAscensao.setText("JORNADA COMPLETA (" + desafios.size() + "/" + desafios.size()
                    + ")  •  LENDA SUPREMA DA ACADEMIA");
            bannerAscensao.setBackground(VERDE_FUNDO);
            bannerAscensao.setVisible(true);
        } else if (veterano) {
            bannerAscensao.setText("ASCENSAO VETERANA ALCANÇADA  •  FORMA SUPREMA DESBLOQUEADA");
            bannerAscensao.setBackground(BOTAO_ATIVO);
            bannerAscensao.setVisible(true);
        } else if (!iniciante) {
            bannerAscensao.setText("ORDEM DESPERTADA (NIVEL " + nivel + ")  •  RUMO AO VETERANO NO NIVEL 5");
            bannerAscensao.setBackground(AVISO_FUNDO);
            bannerAscensao.setVisible(true);
        } else {
            bannerAscensao.setVisible(false);
        }

        if (veterano) {
            tituloEpico.setText(nomeHeroi + ", " + tituloVeterano(indiceOrdem) + "  •  " + nomeArquetipo);
            String sufixoFinal = concluiuTudo
                    ? " Conquistou todos os desafios da Academia e gravou seu nome na eternidade do CodeQuest!"
                    : "";
            descricaoHeroi.setText(historiaVeterana(indiceOrdem, nomeHeroi, nomeArquetipo) + sufixoFinal);
        } else if (!iniciante) {
            tituloEpico.setText(nomeHeroi + ", Guardiao Iniciado da Ordem de " + escolhida + "  •  " + nomeArquetipo);
            descricaoHeroi.setText(historiaIntermediaria(indiceOrdem, nomeHeroi, nomeArquetipo));
        } else {
            tituloEpico.setText(nomeHeroi + "  •  " + nomeArquetipo + " (Nivel " + nivel + ")");
            descricaoHeroi.setText(nomeHeroi + " deu os primeiros passos como Aprendiz de " + escolhida
                    + ". Alcance o Nivel 3 (200 XP) para vestir os trajes da sua Ordem e o Nivel 5 (400 XP) para despertar sua cena lendaria de Veterano!");
        }

        botaoContinuarJornada.setVisible(restamDesafios && !iniciante);
    }

    private static String descricaoInicialOrdem(int indiceOrdem) {
        return switch (indiceOrdem) {
            case 1 -> "Ordem dos Bardos: transforma criatividade e logica em cronicas memoraveis capazes de encantar toda a Academia.";
            case 2 -> "Ordem dos Paladinos: defensores da disciplina e do codigo integro, erguem escudos dourados contra qualquer falha.";
            case 3 -> "Ordem dos Guerreiros: mestres da linha de frente que superam masmorras complexas com determinacao implacavel.";
            case 4 -> "Ordem dos Bruxos: estudiosos de grimorios profundos que dominam pactos arcanos e segredos ocultos da computacao.";
            case 5 -> "Ordem dos Feiticeiros: canalizadores natos de tempestades arcanas, moldando arquiteturas inteiras com pura maestria.";
            default -> "Ordem dos Aventureiros: exploradores versateis e destemidos que desbravam tanto o Backend quanto o Frontend.";
        };
    }

    private static String tituloVeterano(int indiceOrdem) {
        return switch (indiceOrdem) {
            case 1 -> "Mestre Trovador das Cronicas Arcanas";
            case 2 -> "Campeao Sagrado da Luz Dourada";
            case 3 -> "Senhor Imortal da Lamina Runica";
            case 4 -> "Soberano Oculto dos Pactos Abissais";
            case 5 -> "Soberano Arcano da Tempestade";
            default -> "Desbravador Lendario dos Reinos";
        };
    }

    private static String historiaIntermediaria(int indiceOrdem, String nomeHeroi, String arquetipo) {
        return switch (indiceOrdem) {
            case 1 -> nomeHeroi + " deixou o manto de aprendiz e assumiu seu alaude encantado de Bardo! Evoluindo como "
                    + arquetipo + ", cada desafio vencido aproxima sua cancao da forma Veterana no Nivel 5.";
            case 2 -> nomeHeroi + " recebeu a armadura consagrada dos Paladinos! Guiado pelos estudos de "
                    + arquetipo + ", marcha firme rumo a consagracao Veterana no Nivel 5.";
            case 3 -> nomeHeroi + " empunhou o aco oficial dos Guerreiros da Academia! Fortalecido como "
                    + arquetipo + ", prepara-se para despertar sua Lamina Runica no Nivel 5.";
            case 4 -> nomeHeroi + " selou os primeiros tomos secretos dos Bruxos! Dominando a trilha de "
                    + arquetipo + ", reune poder para sua ascensao abissal no Nivel 5.";
            case 5 -> nomeHeroi + " despertou as chamas azuis da Ordem dos Feiticeiros! Como "
                    + arquetipo + ", acumula energia mistica para atingir a forma suprema no Nivel 5.";
            default -> nomeHeroi + " provou seu valor e conquistou o traje oficial de Aventureiro! Atuando como "
                    + arquetipo + ", segue rumo ao titulo de Veterano no Nivel 5.";
        };
    }

    private static String historiaVeterana(int indiceOrdem, String nomeHeroi, String arquetipo) {
        return switch (indiceOrdem) {
            case 1 -> "O Bardo " + nomeHeroi + " tornou-se o Mestre Trovador das Cronicas Arcanas! Consagrado como "
                    + arquetipo + ", transforma algoritmos e interfaces em acordes lendarios que ecoam por todo o reino.";
            case 2 -> "O Paladino " + nomeHeroi + " tornou-se o Campeao Sagrado da Luz Dourada! Revestido pela honra de "
                    + arquetipo + ", protege sistemas inteiros com perfeicao e coragem inabalavel.";
            case 3 -> "O Guerreiro " + nomeHeroi + " tornou-se o Senhor Imortal da Lamina Runica! Forjado nas masmorras como "
                    + arquetipo + ", nenhum desafio de codigo resiste a forca do seu golpe.";
            case 4 -> "O Bruxo " + nomeHeroi + " tornou-se o Soberano Oculto dos Pactos Abissais! Mestre absoluto como "
                    + arquetipo + ", curva sombras, excecoes e estruturas complexas a sua vontade.";
            case 5 -> "Flutuando sobre runas vivas de pura energia, o Feiticeiro " + nomeHeroi
                    + " tornou-se o Soberano Arcano da Tempestade! Consagrado como " + arquetipo
                    + ", comanda relampagos de logica e arquitetura suprema.";
            default -> "De simples iniciado a lenda viva, " + nomeHeroi
                    + " tornou-se o Desbravador Lendario dos Reinos! Com a maestria de " + arquetipo
                    + ", dominou os segredos mais altos da Academia.";
        };
    }

    private ImageIcon carregarImagem(String arquivo, int tamanho) {
        try {
            URL recurso = getClass().getResource("/avatares/" + arquivo);
            if (recurso == null) {
                Path caminhoLocal = Path.of("resources", "avatares", arquivo);
                if (Files.exists(caminhoLocal)) {
                    recurso = caminhoLocal.toUri().toURL();
                }
            }
            if (recurso == null) {
                throw new IllegalStateException("Imagem nao encontrada: " + arquivo + ". Inclua resources no classpath.");
            }
            Image imagem = ImageIO.read(recurso);
            if (imagem == null) {
                throw new IOException("Formato de imagem invalido");
            }
            return new ImageIcon(imagem.getScaledInstance(tamanho, tamanho, Image.SCALE_REPLICATE));
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel carregar " + arquivo, exception);
        }
    }

    private static String nomeTrilha(Trilha trilha) {
        return trilha == Trilha.BACKEND ? "Backend" : "Frontend";
    }

    // Catalogo fixo da Academia com 10 desafios (600 XP totais)
    private static List<Desafio> criarCatalogoDesafios() {
        return List.of(
                new Leitura(
                        "Encapsulamento em POO",
                        Trilha.BACKEND,
                        50,
                        15,
                        "Em POO, o encapsulamento oculta os atributos internos da classe (private) e permite alteracoes somente por metodos que validam as regras de negocio, impedindo estados invalidos."),
                new Quiz(
                        "Heranca em Java",
                        Trilha.BACKEND,
                        50,
                        20,
                        "Qual palavra-chave da linguagem Java indica que uma subclasse herda atributos e metodos de uma superclasse?",
                        List.of("implements", "extends", "super", "abstract"),
                        1),
                new Leitura(
                        "Semantica no HTML5",
                        Trilha.FRONTEND,
                        50,
                        15,
                        "Tags semanticas como <header>, <nav>, <main>, <article> e <footer> comunicam o significado estrutural de cada bloco da pagina para navegadores e leitores de tela."),
                new Quiz(
                        "Alinhamento Flexbox",
                        Trilha.FRONTEND,
                        50,
                        20,
                        "No CSS, qual declaracao transforma um container em um contexto flexivel para alinhar itens em linha ou coluna?",
                        List.of("position: relative", "display: flex", "float: inline-start", "box-sizing: border-box"),
                        1),
                new Quiz(
                        "Polimorfismo e @Override",
                        Trilha.BACKEND,
                        75,
                        25,
                        "Qual anotacao em Java verifica em tempo de compilacao que um metodo esta sobrescrevendo corretamente um metodo da superclasse?",
                        List.of("@Overload", "@Override", "@Polimorphic", "@Final"),
                        1),
                new Quiz(
                        "Especificidade no CSS",
                        Trilha.FRONTEND,
                        75,
                        25,
                        "Entre os seletores CSS abaixo, qual possui a MAIOR especificidade ao estilizar um elemento?",
                        List.of("Seletor de tag (div)", "Seletor de classe (.cartao)", "Seletor de ID (#perfil)", "Seletor universal (*)"),
                        2),
                new Leitura(
                        "Tratamento de Excecoes",
                        Trilha.BACKEND,
                        50,
                        15,
                        "Excecoes separam o fluxo normal do tratamento de falhas (try-catch). Excecoes de dominio como EnergiaEsgotadaException tornam os erros expressivos e preservam o estado do objeto."),
                new Quiz(
                        "Eventos no JavaScript",
                        Trilha.FRONTEND,
                        75,
                        25,
                        "Qual metodo padrao do JavaScript registra uma funcao para reagir ao clique de um botao sem sobrescrever outros ouvintes?",
                        List.of("elemento.onClickNow()", "elemento.addEventListener('click', fn)", "document.listenClick(fn)", "window.trigger('click')"),
                        1),
                new Quiz(
                        "Colecoes Imutaveis Java",
                        Trilha.BACKEND,
                        75,
                        25,
                        "Qual chamada cria uma copia imutavel de uma lista em Java moderno, lancando excecao caso alguem tente adicionar ou remover itens?",
                        List.of("new ArrayList<>(lista)", "List.copyOf(lista)", "Arrays.sort(lista)", "lista.clone()"),
                        1),
                new Quiz(
                        "Media Queries CSS",
                        Trilha.FRONTEND,
                        50,
                        20,
                        "Qual recurso do CSS permite aplicar estilos diferentes conforme a largura da tela do dispositivo (mobile, tablet ou desktop)?",
                        List.of("@media (max-width: 768px)", "@keyframes resize", "@import screen", "display: responsive"),
                        0));
    }

    private static JLabel rotulo(String texto, int tamanho) {
        JLabel label = new JLabel(texto);
        label.setForeground(TEXTO);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, tamanho));
        return label;
    }

    private static JPanel painel(java.awt.LayoutManager layout) {
        JPanel panel = new JPanel(layout) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.setColor(new Color(255, 255, 255, 5));
                for (int altura = 0; altura < getHeight(); altura += 4) {
                    graphics.drawLine(0, altura, getWidth(), altura);
                }
            }
        };
        panel.setBackground(FUNDO);
        return panel;
    }

    private static void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        combo.setBackground(PAINEL);
        combo.setForeground(OURO);
        combo.setBorder(BorderFactory.createLineBorder(OURO));
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        combo.setRenderer((lista, valor, indice, selecionado, foco) -> {
            JLabel item = (JLabel) renderer.getListCellRendererComponent(lista, valor, indice, selecionado, foco);
            item.setBackground(selecionado ? BOTAO_VINHO : PAINEL);
            item.setForeground(OURO);
            item.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
            return item;
        });
        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton seta = new JButton("v");
                seta.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                seta.setBackground(BOTAO_VINHO);
                seta.setForeground(OURO);
                seta.setFocusPainted(false);
                seta.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, OURO));
                return seta;
            }

            @Override
            public void paintCurrentValueBackground(Graphics graphics, Rectangle bounds, boolean hasFocus) {
                graphics.setColor(PAINEL);
                graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });
    }

    private static void estilizarRolagem(JScrollPane scroll) {
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(PAINEL_INTERNO);
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(110, 92, 56);
                this.trackColor = PAINEL_INTERNO;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return botaoInvisivel();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return botaoInvisivel();
            }

            private JButton botaoInvisivel() {
                JButton botao = new JButton();
                botao.setPreferredSize(new Dimension(0, 0));
                return botao;
            }
        });
    }

    private static void estilizarAba(JButton aba, boolean ativa) {
        aba.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        aba.setBackground(ativa ? BOTAO_ATIVO : PAINEL);
        aba.setForeground(ativa ? OURO : TEXTO_SUAVE);
        aba.setFocusPainted(false);
        aba.setBorder(ativa
                ? BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(OURO, 2),
                        BorderFactory.createEmptyBorder(7, 15, 7, 15))
                : BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(95, 82, 58), 1),
                        BorderFactory.createEmptyBorder(8, 16, 8, 16)));
    }

    private static void estilizarBotao(JButton botao) {
        botao.setFont(new Font(Font.SERIF, Font.BOLD, 17));
        botao.setBackground(BOTAO_VINHO);
        botao.setForeground(TEXTO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(8, 16, 8, 16)));
    }

    private static void estilizarBotaoSecundario(JButton botao) {
        botao.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        botao.setBackground(PAINEL_INTERNO);
        botao.setForeground(OURO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    }

    private static void estilizarBarra(JProgressBar barra) {
        barra.setBackground(PAINEL);
        barra.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        barra.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        barra.setUI(new BasicProgressBarUI() {
            @Override
            protected Color getSelectionBackground() {
                return TEXTO;
            }

            @Override
            protected Color getSelectionForeground() {
                return FUNDO;
            }
        });
    }
}
