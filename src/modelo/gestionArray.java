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


public class gestionArray {

    public Map<String, Usuarios> mapUsuarios = new HashMap<>();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public gestionArray() {
        Type tipoMap = new TypeToken<Map<String, Usuarios>>() {}.getType();
        try (FileReader reader = new FileReader("data.json")) {
            mapUsuarios = gson.fromJson(reader, tipoMap);
            System.out.println("Datos cargados desde data.json");
        } catch (IOException e) {
            System.out.println("La tienda está vacía");
        }
    }


    private void actualizarArchivoJson() {
        try (FileWriter writer = new FileWriter("data.json")) {
            gson.toJson(mapUsuarios, writer);
            System.out.println("Datos escritos en data.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String agregarUsuario(Usuarios usuario) {
        if (mapUsuarios.containsKey(usuario.getUsuario())) {
            return "El usuario ya se encuentra dentro del sistema";
        } else {
            mapUsuarios.put(usuario.getUsuario(), usuario);
            actualizarArchivoJson();
            return "Usuario registrado con éxito";
        }
    }

    public String modificarUsuario(String nombreUsuario, String nuevoNombreUsuario, String nuevaClave) {
        Usuarios usuarioExistente = mapUsuarios.get(nombreUsuario);
        if (usuarioExistente != null) {
            mapUsuarios.remove(nombreUsuario);
            usuarioExistente.setUsuario(nuevoNombreUsuario);
            usuarioExistente.setClave(nuevaClave);
            mapUsuarios.put(nuevoNombreUsuario, usuarioExistente);
            actualizarArchivoJson();
            return "Usuario modificado correctamente";
        } else {
            return "El usuario no se encontró en el sistema";
        }
    }

    public String eliminarUsuario(String nombreUsuario) {
        if (mapUsuarios.remove(nombreUsuario) != null) {
            actualizarArchivoJson();
            return "Usuario eliminado correctamente";
        } else {
            return "El usuario no se encontró en el sistema";
        }
    }

    public String mostrarLista() {
        StringBuilder mostrarLista = new StringBuilder();
        for (Usuarios c : mapUsuarios.values()) {
            mostrarLista.append(c.toString()).append("\n");
        }
        return mostrarLista.toString();
    }


    //---------------------------------------------------------------------------------------------------------------


}