package com.exemplo;
import com.exemplo.view.SistemaMercadinho;

import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaMercadinho().setVisible(true));
    }
}