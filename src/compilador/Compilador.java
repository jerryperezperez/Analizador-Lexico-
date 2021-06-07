package compilador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Compilador {

    public static void main(String[] args) {

        Interfaz nuevaInterfaz = new Interfaz();

        Controller controlador = new Controller(nuevaInterfaz);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        try {
            SwingUtilities.updateComponentTreeUI(nuevaInterfaz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        controlador.iniciar();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                nuevaInterfaz.setVisible(true);
            }
        });

    }

}
