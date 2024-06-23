package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class Modeloinfo {

    public Map<String, Informacion> informacion = new HashMap<>();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public Modeloinfo() {
        Type tipoMap = new TypeToken<Map<String, Usuarios>>() {}.getType();
        try (FileReader reader = new FileReader("informacion.json")) {
            informacion = gson.fromJson(reader, tipoMap);
            System.out.println("Datos cargados desde informacion.json");
        } catch (IOException e) {
            System.out.println("La tienda está vacía");
        }
    }


    private void actualizarArchivoJson() {
        try (FileWriter writer = new FileWriter("informacion.json")) {
            gson.toJson(informacion, writer);
            System.out.println("Datos escritos en informacion.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String agregarnoticia(Informacion nombre) {
        if (informacion.containsKey(nombre.getNombre())) {
            return "El usuario ya se encuentra dentro del sistema";
        } else {
            informacion.put(nombre.getNombre(),nombre );
            actualizarArchivoJson();
            return "Noticia  registrado con éxito";
        }
    }

    public void editarFecha(String referencia, String nuevaFecha) {
        if (informacion.containsKey(referencia)) {
            informacion.get(referencia).setFecha(nuevaFecha);
            actualizarArchivoJson(); // Guardar datos automáticamente
        }
    }
    public void editarnombrea(String referencia, String nuevoNombre) {
        if (informacion.containsKey(referencia)) {
            informacion.get(referencia).setFecha(nuevoNombre);
            actualizarArchivoJson(); // Guardar datos automáticamente
        }
    }

    public void editarDescripcion(String referencia, String nuevaDescripcion) {
        if (informacion.containsKey(referencia)) {
            informacion.get(referencia).setDescripcion(nuevaDescripcion);
            actualizarArchivoJson(); // Guardar datos automáticamente
        }
    }


    public void editarRama(String referencia, String nuevaRama) {
        if (informacion.containsKey(referencia)) {
            informacion.get(referencia).setRama(nuevaRama);
            actualizarArchivoJson(); // Guardar datos automáticamente
        }
    }
    public String eliminarNoticia(String nombreUsuario) {
        if (informacion.remove(nombreUsuario) != null) {
            actualizarArchivoJson();
            return "Usuario eliminado correctamente";
        } else {
            return "El usuario no se encontró en el sistema";
        }
    }

    public String mostrarLista() {
        StringBuilder mostrarLista = new StringBuilder();
        for (Informacion c : informacion.values()) {
            mostrarLista.append(c.toString()).append("\n");
        }
        return mostrarLista.toString();
    }


    //---------------------------------------------------------------------------------------------------------------


}

