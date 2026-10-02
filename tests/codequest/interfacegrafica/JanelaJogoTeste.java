package codequest.interfacegrafica;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import codequest.dominio.Personagem;
import codequest.dominio.Trilha;

public class JanelaJogoTeste {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JanelaJogo janela = new JanelaJogo();
            try {
                janela.setVisible(true);
                verificar(!((JButton) buscar(janela, "descansar")).isEnabled(), "Descanso antes de criar");
                JComboBox<?> aparencia = (JComboBox<?>) buscar(janela, "aparencia");
                verificar(aparencia.getItemCount() == 6, "Seis avatares");
                verificar(aparencia.getItemAt(4).equals("Bruxo"), "Nome em portugues");
                verificar(aparencia.getItemAt(5).equals("Feiticeiro"), "Feiticeiro em portugues");
                for (int indice = 0; indice < aparencia.getItemCount(); indice++) {
                    aparencia.setSelectedIndex(indice);
                    verificar(((JLabel) buscar(janela, "avatar")).getIcon().getIconWidth() == 96, "Avatar carregado");
                    verificar(((JLabel) buscar(janela, "cena")).getIcon().getIconWidth() == 360, "Cena carregada");
                }
                ((JTextField) buscar(janela, "nome")).setText("Patrick");
                ((JButton) buscar(janela, "criar")).doClick();
                verificar(((JLabel) buscar(janela, "identidade")).getText().equals("Patrick"), "Criacao pela tela");
                verificar(!buscar(janela, "criar").isShowing(), "Criacao escondida apos iniciar");
                aparencia.setSelectedIndex(3);
                verificar(!aparencia.isEnabled(), "Classe travada depois da criacao");
                verificar(((JLabel) buscar(janela, "classe")).getText().equals("Aprendiz de Guerreiro"), "Titulo de aprendiz da classe");
                verificar(((JLabel) buscar(janela, "especializacao")).getText().contains("Inicial"), "Avatar nao altera especializacao");
                capturar(janela, "out/janela-desktop.png");
                janela.setSize(860, 700);
                janela.validate();
                capturar(janela, "out/janela-compacta.png");
            } finally {
                janela.dispose();
            }

            Personagem personagem = new Personagem("Michael");
            personagem.ganharExperiencia(Trilha.BACKEND, 150);
            personagem.consumirEnergia(40);
            JanelaJogo perfil = new JanelaJogo(personagem);
            try {
                verificar(((JProgressBar) buscar(perfil, "energia")).getValue() == 60, "Energia do dominio");
                verificar(((JProgressBar) buscar(perfil, "xp")).getValue() == 50, "Progresso de nivel");
                verificar(((JLabel) buscar(perfil, "especializacao")).getText().contains("Backend"), "Afinidade do dominio");
                JButton descansar = (JButton) buscar(perfil, "descansar");
                verificar(descansar.isEnabled(), "Descanso disponivel");
                descansar.doClick();
                verificar(personagem.getEnergia() == 100, "Botao chama o dominio");
                verificar(((JProgressBar) buscar(perfil, "energia")).getValue() == 100, "Barra atualizada");
                verificar(!descansar.isEnabled(), "Descanso desabilitado com energia cheia");
            } finally {
                perfil.dispose();
            }

            for (int nivel : new int[] {3, 5}) {
                Personagem evoluido = new Personagem("Sylas");
                evoluido.ganharExperiencia(Trilha.BACKEND, (nivel - 1) * 100);
                JanelaJogo avancada = new JanelaJogo(evoluido);
                try {
                    JComboBox<?> escolha = (JComboBox<?>) buscar(avancada, "aparencia");
                    for (int indice = 0; indice < escolha.getItemCount(); indice++) {
                        escolha.setSelectedIndex(indice);
                        String esperado = escolha.getItemAt(indice) + (nivel >= 5 ? " Veterano" : "");
                        verificar(((JLabel) buscar(avancada, "classe")).getText().equals(esperado), "Fase da classe escolhida");
                        verificar(((JLabel) buscar(avancada, "cena")).getIcon().getIconWidth() == 360, "Arte da fase carregada");
                    }
                    avancada.setVisible(true);
                    capturar(avancada, "out/janela-nivel-" + nivel + ".png");
                } finally {
                    avancada.dispose();
                }
            }
        });
        System.out.println("Todos os testes da janela passaram.");
    }

    private static Component buscar(Container pai, String nome) {
        for (Component componente : pai.getComponents()) {
            if (nome.equals(componente.getName())) {
                return componente;
            }
            if (componente instanceof Container container) {
                Component encontrado = buscar(container, nome);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }

    private static void capturar(JanelaJogo janela, String caminho) {
        BufferedImage imagem = new BufferedImage(janela.getWidth(), janela.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();
        janela.paint(graphics);
        graphics.dispose();
        try {
            ImageIO.write(imagem, "png", new File(caminho));
        } catch (IOException exception) {
            throw new IllegalStateException("Falha ao salvar captura", exception);
        }
    }

    private static void verificar(boolean condicao, String cenario) {
        if (!condicao) {
            throw new AssertionError("Falhou: " + cenario);
        }
    }
}