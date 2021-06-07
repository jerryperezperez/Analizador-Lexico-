
package compilador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class CreacionArchivo {

    public void crearArchivo(String contenido, String nombreArchivo) {
        try {
            String ruta = System.getProperty("user.dir") + "\\" + nombreArchivo + ".txt";

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
