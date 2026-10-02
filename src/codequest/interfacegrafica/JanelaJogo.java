package codequest.interfacegrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import codequest.dominio.Personagem;

public final class JanelaJogo extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color FUNDO = new Color(25, 29, 27);
    private static final Color PAINEL = new Color(39, 36, 32);
    private static final Color TEXTO = new Color(242, 232, 208);
    private static final Color OURO = new Color(206, 171, 91);
    private final String[] arquivos = {"aprendiz", "bardo", "paladino", "guerreiro", "warlock", "sorcerer"};
    private final JComboBox<String> aparencia = new JComboBox<>(
            new String[] {"Aventureiro", "Bardo", "Paladino", "Guerreiro", "Bruxo", "Feiticeiro"});
    private final JTextField nome = new JTextField(16);
    private final JLabel avatar = new JLabel("", SwingConstants.CENTER);
    private final JLabel cena = new JLabel("", SwingConstants.CENTER);
    private final JLabel identidade = rotulo("Novo aventureiro", 24);
    private final JLabel especializacao = rotulo("Aprendiz", 18);
    private final JLabel classe = rotulo("Aprendiz de Aventureiro", 22);
    private final JLabel fase = rotulo("O inicio de uma lenda", 18);
    private final JLabel ordemFixa = rotulo("Aventureiro", 18);
    private final JLabel experiencia = rotulo("Backend: 0 XP | Frontend: 0 XP", 14);
    private final JProgressBar barraXp = new JProgressBar(0, 100);
    private final JProgressBar barraEnergia = new JProgressBar(0, 100);
    private final JButton descansar = new JButton("Descansar");
    private final JLabel mensagem = rotulo("", 14);
    private final JPanel cadastro = painel(new BorderLayout(8, 8));
    private transient Personagem personagem;

    public JanelaJogo() {
        this(null);
    }

    public JanelaJogo(Personagem personagem) {
        super("CodeQuest | Jornada do personagem");
        this.personagem = personagem;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(860, 700));
        setSize(980, 720);
        setLocationRelativeTo(null);

        JPanel conteudo = painel(new BorderLayout(24, 24));
        conteudo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(OURO, 3), BorderFactory.createEmptyBorder(24, 24, 24, 24)));
        JPanel cabecalho = painel(new BorderLayout(8, 8));
        JLabel titulo = rotulo("CODEQUEST", 34);
        titulo.setForeground(OURO);
        cabecalho.add(titulo, BorderLayout.WEST);
        JLabel subtitulo = rotulo("CRONICAS DA ACADEMIA", 15);
        subtitulo.setHorizontalAlignment(SwingConstants.RIGHT);
        cabecalho.add(subtitulo, BorderLayout.EAST);
        cabecalho.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, OURO));
        conteudo.add(cabecalho, BorderLayout.NORTH);

        JPanel centro = painel(new BorderLayout(20, 20));
        centro.add(criarPerfil(), BorderLayout.WEST);
        cena.setName("cena");
        cena.setPreferredSize(new Dimension(380, 380));
        JPanel estandarte = painel(new BorderLayout(0, 10));
        classe.setName("classe");
        classe.setForeground(OURO);
        classe.setHorizontalAlignment(SwingConstants.CENTER);
        fase.setHorizontalAlignment(SwingConstants.CENTER);
        estandarte.add(classe, BorderLayout.NORTH);
        estandarte.add(cena, BorderLayout.CENTER);
        estandarte.add(fase, BorderLayout.SOUTH);
        centro.add(estandarte, BorderLayout.CENTER);
        conteudo.add(centro, BorderLayout.CENTER);

        JPanel rodape = painel(new BorderLayout(8, 8));
        nome.setName("nome");
        nome.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        nome.setBackground(PAINEL);
        nome.setForeground(TEXTO);
        nome.setCaretColor(OURO);
        nome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        cadastro.add(rotulo("Nome do personagem", 14), BorderLayout.NORTH);
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
        JScrollPane rolagem = new JScrollPane(conteudo);
        rolagem.setBorder(BorderFactory.createEmptyBorder());
        setContentPane(rolagem);

        aparencia.addActionListener(event -> atualizarImagens());
        descansar.addActionListener(event -> {
            if (this.personagem != null) {
                this.personagem.descansar();
                atualizarPerfil();
                mensagem.setText("Energia recuperada. Sua jornada continua!");
            }
        });
        atualizarImagens();
        atualizarPerfil();
    }

    private JPanel criarPerfil() {
        JPanel perfil = painel(new BorderLayout(0, 16));
        perfil.setBackground(PAINEL);
        perfil.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        perfil.setPreferredSize(new Dimension(320, 500));
        aparencia.setName("aparencia");
        aparencia.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        aparencia.setBackground(PAINEL);
        aparencia.setForeground(OURO);
        aparencia.setBorder(BorderFactory.createLineBorder(OURO));
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        aparencia.setRenderer((lista, valor, indice, selecionado, foco) -> {
            JLabel item = (JLabel) renderer.getListCellRendererComponent(lista, valor, indice, selecionado, foco);
            item.setBackground(selecionado ? new Color(91, 43, 42) : PAINEL);
            item.setForeground(OURO);
            item.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
            return item;
        });
        aparencia.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton seta = new JButton("v");
                estilizarBotao(seta);
                return seta;
            }
        });
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
        JPanel retrato = painel(new BorderLayout(0, 8));
        avatar.setPreferredSize(new Dimension(128, 128));
        JPanel escolha = painel(new BorderLayout(0, 6));
        escolha.add(rotulo("ORDEM DO AVENTUREIRO", 13), BorderLayout.NORTH);
        escolha.add(aparencia, BorderLayout.CENTER);
        ordemFixa.setForeground(OURO);
        escolha.add(ordemFixa, BorderLayout.SOUTH);
        retrato.add(escolha, BorderLayout.NORTH);
        retrato.add(avatar, BorderLayout.CENTER);
        perfil.add(retrato, BorderLayout.NORTH);
        JPanel atributos = painel(new GridLayout(6, 1, 0, 8));
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
            mensagem.setText("Personagem criado. Que comece sua jornada!");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Nome invalido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void atualizarPerfil() {
        cadastro.setVisible(personagem == null);
        aparencia.setEnabled(personagem == null);
        aparencia.setVisible(personagem == null);
        ordemFixa.setVisible(personagem != null);
        descansar.setEnabled(personagem != null && personagem.getEnergia() < 100);
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
        String arquivo = arquivos[aparencia.getSelectedIndex()];
        String escolhida = (String) aparencia.getSelectedItem();
        ordemFixa.setText(escolhida);
        int nivel = personagem == null ? 1 : personagem.getNivel();
        boolean iniciante = nivel < 3;
        boolean veterano = nivel >= 5;
        String retrato = iniciante ? "aprendiz" : arquivo;
        avatar.setIcon(carregarImagem(retrato + ".png", 96));
        cena.setIcon(carregarImagem(veterano ? arquivo + "_action.png" : retrato + ".png", 360));
        classe.setText(iniciante ? "Aprendiz de " + escolhida : veterano ? escolhida + " Veterano" : escolhida);
        fase.setText(iniciante ? "O inicio de uma lenda" : veterano ? "Forjado nas masmorras" : "A jornada ganha forma");
    }

    private ImageIcon carregarImagem(String arquivo, int tamanho) {
        URL recurso = getClass().getResource("/avatares/" + arquivo);
        if (recurso == null) {
            throw new IllegalStateException("Imagem nao encontrada: " + arquivo + ". Inclua resources no classpath.");
        }
        try {
            Image imagem = ImageIO.read(recurso);
            if (imagem == null) {
                throw new IOException("Formato de imagem invalido");
            }
            return new ImageIcon(imagem.getScaledInstance(tamanho, tamanho, Image.SCALE_REPLICATE));
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel carregar " + arquivo, exception);
        }
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

    private static void estilizarBotao(JButton botao) {
        botao.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        botao.setBackground(new Color(91, 43, 42));
        botao.setForeground(TEXTO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OURO), BorderFactory.createEmptyBorder(8, 16, 8, 16)));
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