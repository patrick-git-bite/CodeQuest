package codequest.interfacegrafica;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import codequest.dominio.Desafio;
import codequest.dominio.Leitura;
import codequest.dominio.Personagem;
import codequest.dominio.Quiz;
import codequest.dominio.Trilha;

public class JanelaJogoTeste {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JanelaJogo janela = new JanelaJogo();
            try {
                janela.setVisible(true);
                verificar(!((JButton) buscar(janela, "descansar")).isEnabled(), "Descanso antes de criar");
                verificar(!((JButton) buscar(janela, "abaDesafios")).isEnabled(), "Desafios bloqueados antes de criar");
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
                verificar(((JButton) buscar(janela, "abaDesafios")).isEnabled(), "Desafios liberados apos criar");
                aparencia.setSelectedIndex(3);
                verificar(!aparencia.isEnabled(), "Classe travada depois da criacao");
                verificar(((JLabel) buscar(janela, "classe")).getText().equals("Aprendiz de Guerreiro"), "Titulo de aprendiz da classe");
                verificar(((JLabel) buscar(janela, "especializacao")).getText().contains("Inicial"), "Avatar nao altera especializacao");
                capturar(janela, "out/janela-desktop.png");
                janela.setSize(880, 740);
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

            List<Desafio> desafiosTeste = List.of(
                    new Quiz(
                            "Quiz POO",
                            Trilha.BACKEND,
                            100,
                            40,
                            "Qual pilar protege o estado interno do objeto?",
                            List.of("Heranca", "Encapsulamento", "Polimorfismo"),
                            1),
                    new Leitura(
                            "Leitura Frontend",
                            Trilha.FRONTEND,
                            100,
                            40,
                            "CSS separa apresentacao visual da estrutura HTML."));
            Personagem estudante = new Personagem("Aventureira");
            JanelaJogo jornada = new JanelaJogo(estudante, desafiosTeste);
            try {
                jornada.setVisible(true);
                ((JButton) buscar(jornada, "abaDesafios")).doClick();
                JButton responder = (JButton) buscar(jornada, "responder");
                JButton proximo = (JButton) buscar(jornada, "proximo");
                JProgressBar barraEnergia = (JProgressBar) buscar(jornada, "energia");
                JProgressBar barraXp = (JProgressBar) buscar(jornada, "xp");

                responder.doClick();
                verificar(estudante.getEnergia() == 100, "Sem alternativa selecionada nao gasta energia");
                verificar(((JLabel) buscar(jornada, "resultadoDesafio")).getText().contains("alternativa"), "Aviso de resposta invalida");

                ((JToggleButton) buscar(jornada, "opcao1")).doClick();
                responder.doClick();
                verificar(estudante.getEnergia() == 60, "Erro no quiz consome energia");
                verificar(estudante.getExperienciaGeral() == 0, "Erro no quiz nao concede XP");
                verificar(barraEnergia.getValue() == 60, "Barra de energia reflete tentativa");

                ((JToggleButton) buscar(jornada, "opcao2")).doClick();
                responder.doClick();
                verificar(estudante.getEnergia() == 20, "Acerto consome energia");
                verificar(estudante.getExperienciaBackend() == 100, "Acerto concede XP Backend");
                verificar(estudante.getNivel() == 2, "Subiu para nivel 2");
                verificar(barraXp.getValue() == 0, "100 XP fecha ciclo da barra");
                verificar(!responder.isEnabled(), "Desafio concluido trava botao responder");
                capturar(jornada, "out/janela-desafio-quiz.png");

                proximo.doClick();
                @SuppressWarnings("unchecked")
                JList<Desafio> lista = (JList<Desafio>) buscar(jornada, "listaDesafios");
                verificar(lista.getSelectedIndex() == 1, "Botao proximo avanca para pendente");
                verificar(responder.isEnabled(), "Segundo desafio disponivel");
                responder.doClick();
                verificar(estudante.getEnergia() == 20, "Energia insuficiente preserva saldo");
                verificar(((JLabel) buscar(jornada, "resultadoDesafio")).getText().contains("ENERGIA INSUFICIENTE"), "Exibe excecao de energia");

                ((JButton) buscar(jornada, "descansar")).doClick();
                verificar(estudante.getEnergia() == 100, "Descansou para continuar");
                responder.doClick();
                verificar(estudante.getExperienciaFrontend() == 100, "Leitura concedeu XP Frontend");
                verificar(((JLabel) buscar(jornada, "especializacao")).getText().contains("Fullstack"), "Evoluiu para Fullstack na tela");
                verificar(!proximo.isEnabled(), "Proximo desabilitado quando todos concluidos");
                verificar(buscar(jornada, "cena").isShowing(), "Revela cena do heroi automaticamente na consagracao");
                verificar(((JTextArea) buscar(jornada, "descricaoHeroi")).getText().contains("Aventureira"), "Exibe historia personalizada com nome");
            } finally {
                jornada.dispose();
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
        File arquivo = new File(caminho);
        File pasta = arquivo.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
        BufferedImage imagem = new BufferedImage(janela.getWidth(), janela.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();
        janela.paint(graphics);
        graphics.dispose();
        try {
            ImageIO.write(imagem, "png", arquivo);
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
