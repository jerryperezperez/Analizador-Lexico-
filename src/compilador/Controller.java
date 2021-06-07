package compilador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Controller implements ActionListener {

    Interfaz vista;
    Alfabeto alfa;
    InformacionTokens informacion = new InformacionTokens(vista, false);
    DefaultTableModel modeloError = new DefaultTableModel();
    DefaultTableModel modeloSimbolo = new DefaultTableModel();
    String palabra;
    String nombreArchivo;

    public Controller(Interfaz vista) {

        this.vista = vista;

        modeloSimbolo.addColumn("Token");
        modeloSimbolo.addColumn("Lexema");

        modeloError.addColumn("Token");
        modeloError.addColumn("Lexema");
        modeloError.addColumn("Línea");
        modeloError.addColumn("Descripción");

        vista.tablaSimbolos.setModel(modeloSimbolo);
        vista.tablaErrores.setModel(modeloError);
    }

    public void iniciar() {

        this.vista.jButton1.addActionListener(this);
        this.vista.botonGenerarArchivo.addActionListener(this);
        this.vista.botonLimpiar.addActionListener(this);
        this.vista.botonTokens.addActionListener(this);

        vista.setTitle("Compilador 3.0 Version");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setVisible(true);
    }

    public void insertarDatos() {
        inicializarTablas();

        modeloSimbolo.addColumn("Token");
        modeloSimbolo.addColumn("Lexema");
        modeloError.addColumn("Token");

        modeloError.addColumn("Lexema");
        modeloError.addColumn("Línea");
        modeloError.addColumn("Descripción");
        vista.tablaSimbolos.setModel(modeloSimbolo);
        vista.tablaErrores.setModel(modeloError);

        for (int i = 0; i < alfa.tablaSimboloToken.size(); i++) {
            String[] datos = {alfa.tablaSimboloToken.get(i), alfa.tablaSimboloLexema.get(i)};
            modeloSimbolo.addRow(datos);
        }

        for (int i = 0; i < alfa.tablaErrorToken.size(); i++) {
            Object[] datos = {alfa.tablaErrorToken.get(i), alfa.tablaErrorLexema.get(i), alfa.tablaLineaError.get(i), "Error léxico"};
            modeloError.addRow(datos);
        }

        vista.tablaErrores.setModel(modeloError);
        vista.textAreaCompilado.setText(alfa.cadenaCompilada.toString());
    }

    public void borrarDatos() {
        vista.textAreaCompilado.setText("");
        vista.textArea.setText("");

        inicializarTablas();
        modeloSimbolo.addColumn("Token");
        modeloSimbolo.addColumn("Lexema");
        modeloError.addColumn("Token");

        modeloError.addColumn("Lexema");
        modeloError.addColumn("Línea");
        modeloError.addColumn("Descripción");
        vista.tablaSimbolos.setModel(modeloSimbolo);
        vista.tablaErrores.setModel(modeloError);

    }

    public void inicializarTablas() {
        modeloError = null;
        modeloSimbolo = null;
        modeloError = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        modeloSimbolo = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    }

    @Override

    public void actionPerformed(ActionEvent ae) {
        if (vista.jButton1 == ae.getSource()) {
            alfa = null;
            alfa = new Alfabeto();
       
            palabra = vista.textArea.getText();
            alfa.separarCadena(palabra);
            insertarDatos();
           
        }

        if (vista.botonGenerarArchivo == ae.getSource()) {
          
            nombreArchivo = JOptionPane.showInputDialog(null, "Ingrese el nombre de su archivo a crear");
            CreacionArchivo creador = new CreacionArchivo();
            creador.crearArchivo(alfa.cadenaCompilada.toString(), nombreArchivo);
        }
        if (vista.botonLimpiar == ae.getSource()) {
            borrarDatos();
        }
        if (vista.botonTokens == ae.getSource()) {
            informacion.setVisible(true);
        }
    }
}
