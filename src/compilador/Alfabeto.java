package compilador;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alfabeto {

    String[] alfabeto2;
    String[] lineasPorCadena;

    boolean isError = true;

    String cadenaWhile = "while";
    String asignacion = "=";
    String[] relacion = {"<", "<=", ">", ">=", "==", "!="};
    String[] booleano = {"&&", "||"};
    String[] separador = {"{", "(", ")", "}", ",", ";"};
    String[] aritmetico = {"+", "-", "*", "/", "%"};
    String[] tipoDatos = {"numPointV", "textV", "numCerradoV", "simpleV"};

    int token = 0;
    int lexema = 0;
    int contadorSeparador = 0;
    int contadorAritmetico = 0;
    int contadorAsignacion = 0;
    int contadorRelacion = 0;
    int contadorLogico = 0;
    int contadorError = 0;
    int contadorIdentificador = 0;
    int contadorEntero = 0;
    int contadorDecimal = 0;
    int contadorTipoDato = 0;
    int contadorWhile = 0;
    StringBuffer cadenaCompilada = new StringBuffer();
    ArrayList<ArrayList<String>> tablaSimbolo = new ArrayList<>();
    ArrayList<String> tablaSimboloToken = new ArrayList<String>();
    ArrayList<Integer> tablaLineaError = new ArrayList<Integer>();
    ArrayList<String> tablaErrorToken = new ArrayList<String>();
    ArrayList<String> tablaErrorLexema = new ArrayList<String>();
    ArrayList<String> tablaSimboloLexema = new ArrayList<String>();

    public void separarCadena(String cadena) {
        tablaSimbolo.add(tablaSimboloLexema);
        tablaSimbolo.add(tablaSimboloToken);

        lineasPorCadena = cadena.split("\n");

        for (int j = 0; lineasPorCadena.length > j; j++) {
            lineasPorCadena[j] = lineasPorCadena[j].replaceAll("\n", " ");
            lineasPorCadena[j] = eliminarEspaciosEnBlanco(lineasPorCadena[j]);

            cadena = lineasPorCadena[j];
            alfabeto2 = cadena.split(" ");
            for (int i = 0; i < alfabeto2.length; i++) {
                isError = true;

                identificarBooleano(i);
                identificarTipoDato(i);
                identificarWhile(i);
                identificarNumerosCerrados(i);
                identificarNumerosDecimal(i);
                identificarRelacion(i);
                identificarAritmetico(i);
                identificarAsignacion(i);
                identificarIdentificador(i);
                identificarSeparador(i);
                if (isError == true) {

                    if (!comprobarErroresRepetidos(i, j)) {
                        contadorError++;
                        aumentarArregloError(i, "ERR", contadorError, j);
                    }
                }
            }
            agregarSaltoLinea();
        }
    }

    public void identificarTipoDato(int i) {
        for (int j = 0; j < tipoDatos.length; j++) {
            if (tipoDatos[j].equals(alfabeto2[i])) {

                isError = false;

                if (false == comprobarTokensRepetidos(i)) {
                    contadorTipoDato++;
                    aumentarArreglo(i, "TDA", contadorTipoDato);
                }
            }
        }
    }

    public void identificarWhile(int i) {

        if (cadenaWhile.equals(alfabeto2[i])) {

            isError = false;

            if (false == comprobarTokensRepetidos(i)) {
                contadorWhile++;
                aumentarArreglo(i, "WHI", contadorWhile);
            }
        }
    }

    public void identificarNumerosCerrados(int i) {

        Pattern pat = Pattern.compile("(-7[0-9]*|7[0-9]*)");
        Matcher mat = pat.matcher(alfabeto2[i]);

        if (mat.matches()) {

            isError = false;

            if (false == comprobarTokensRepetidos(i)) {
                contadorEntero++;
                aumentarArreglo(i, "ENT", contadorEntero);
            }
        }
    }

    public void identificarNumerosDecimal(int i) {
        Pattern pat = Pattern.compile("(-7[0-9]*\\.[0]*[1-9]+|7[0-9]*\\.[0]*[1-9]+)");
        Matcher mat = pat.matcher(alfabeto2[i]);

        if (mat.find()) {

            isError = false;

            if (false == comprobarTokensRepetidos(i)) {
                contadorDecimal++;
                aumentarArreglo(i, "DEC", contadorDecimal);
            }
        }
    }

    public void identificarBooleano(int i) {

        Pattern pat = Pattern.compile("(\\|\\||&&)");
        Matcher mat = pat.matcher(alfabeto2[i]);

        if (mat.find()) {
            isError = false;

            if (false == comprobarTokensRepetidos(i)) {
                contadorLogico++;
                aumentarArreglo(i, "LOG", contadorLogico);
            }
        }
    }

    public void identificarRelacion(int i) {

        for (int j = 0; j < relacion.length; j++) {
            if (relacion[j].equals(alfabeto2[i])) {

                if (false == comprobarTokensRepetidos(i)) {
                    contadorRelacion++;
                    aumentarArreglo(i, "REL", contadorRelacion);
                }
                isError = false;
            }
        }
    }

    public void identificarAsignacion(int i) {

        if (alfabeto2[i].equals("=")) {

            if (false == comprobarTokensRepetidos(i)) {
                contadorAsignacion++;
                aumentarArreglo(i, "ASI", contadorAsignacion);
            }
            isError = false;
        }
    }

    public void identificarAritmetico(int i) {

        Pattern pat = Pattern.compile("\\+|\\*|/|%|-");
        Matcher mat = pat.matcher(alfabeto2[i]);

        if (mat.matches()) {

            if (false == comprobarTokensRepetidos(i)) {
                contadorAritmetico++;
                aumentarArreglo(i, "ARI", contadorAritmetico);
            }
            isError = false;
        }
    }

    public void identificarSeparador(int i) {

        Pattern pat = Pattern.compile("\\{|\\(|\\)|\\}|,|;");
        Matcher mat = pat.matcher(alfabeto2[i]);

        if (mat.find()) {

            if (false == comprobarTokensRepetidos(i)) {
                contadorSeparador++;
                aumentarArreglo(i, "SEP", contadorSeparador);

            }
            isError = false;
        }
    }

    public void identificarIdentificador(int i) {

        Pattern pat = Pattern.compile("-([A-Z]|[a-z])+[0-9]*-");
        Matcher mat = pat.matcher(alfabeto2[i]);

        if (mat.find()) {

            contadorIdentificador++;
            aumentarArreglo(i, "IDE", contadorIdentificador);
            isError = false;
        }
    }

    public void aumentarArreglo(int i, String instruccionLexema, int contador) {

        tablaSimboloToken.add(instruccionLexema + contador);
        tablaSimboloLexema.add(alfabeto2[i]);
        agregarTokens(instruccionLexema + contador);

    }

    public void aumentarArregloError(int i, String instruccionLexema, int contador, int lineaActual) {

        tablaErrorToken.add(instruccionLexema + contador);
        tablaLineaError.add(lineaActual + 1);
        tablaErrorLexema.add(alfabeto2[i]);
        agregarTokens(instruccionLexema + contador);

    }

    public void agregarTokens(String token) {

        cadenaCompilada.append(token + " ");
    }

    public void agregarSaltoLinea() {

        cadenaCompilada.append("\n");
    }

    public boolean comprobarTokensRepetidos(int i) {

        boolean isRepetido = false;
        if (tablaSimboloLexema.size() != 0) {

            for (int j = 0; j < tablaSimboloLexema.size(); j++) {

                if (alfabeto2[i].equals(tablaSimboloLexema.get(j))) {

                    tablaSimboloToken.get(j);
                    agregarTokens(tablaSimboloToken.get(j));
                    isRepetido = true;
                }
            }
        }
        return isRepetido;
    }

    public boolean comprobarErroresRepetidos(int i, int numLinea) {

        boolean isRepetidoError = false;
        if (tablaErrorLexema.size() != 0) {

            for (int j = 0; j < tablaErrorLexema.size(); j++) {

                if (alfabeto2[i].equals(tablaErrorLexema.get(j)) && tablaLineaError.get(j) == numLinea + 1) {

                    tablaErrorToken.get(j);
                    agregarTokens(tablaErrorToken.get(j));
                    isRepetidoError = true;
                }
            }
        }
        return isRepetidoError;
    }

    public String eliminarEspaciosEnBlanco(String lineaCadena) {

        int contadorEspaciosInicial = 0;
        StringBuilder MyString = new StringBuilder(lineaCadena);

        while (MyString.charAt(0) == ' ') {
            MyString.deleteCharAt(0);
        }

        for (int i = 0; i < MyString.length(); i++) {
            if (MyString.charAt(i) == ' ') {
                contadorEspaciosInicial++;
                if (contadorEspaciosInicial == 2) {
                    MyString.deleteCharAt(i);
                    i = 0;
                }
            } else {
                contadorEspaciosInicial = 0;
            }

        }

        return MyString.toString();
    }
}
