package codequest;

import javax.swing.SwingUtilities;
import codequest.interfacegrafica.JanelaJogo;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaJogo().setVisible(true));
    }
}