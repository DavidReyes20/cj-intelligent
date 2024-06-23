package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.imageio.IIOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;


public class HashMap {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public Map<String, Consultorio> mapEstudiantes = new java.util.HashMap<>();

    public HashMap() {
        //TypeToken es una clase de la biblioteca Gson que ayuda a capturar y pasar tipos genéricos
        // carga los archivos desde archivo JSON
        // llamamos la variable tipoMap y con el getType llamamos un objeto de tipo type que nos devuelve -> <Map<String, Producto>>
        Type tipoMap = new TypeToken<Map<String, Consultorio>>() {}.getType();
        try (FileReader reader = new FileReader("casos.json")) {
            //gson.fromJson(reader, tipoMap) es una llamada al método fromJson de la instancia gson de Gson.
            //fromJson deserializa el JSON leído desde el FileReader (reader) y lo convierte
            // en un objeto del tipo (Map<String, Producto>), el cual se asigna a mapProductos.
            mapEstudiantes = gson.fromJson(reader, tipoMap);
            System.out.println("Datos cargados desde casos.json");
        } catch (IOException e) {
            System.out.println("La tienda esta vacia");
        }

    }


    public String imprimirReporte() {
        String mensaje = "";

        try (FileWriter writer = new FileWriter("casos.json")) {
            gson.toJson(mapEstudiantes, writer);
            writer.close();
            mensaje = "Reporte extraido con exito";
        } catch (IIOException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return mensaje;
    }

    public String agregarElemento(String key, Consultorio estudiante) {
        String mensaje;
        boolean esta = mapEstudiantes.containsKey(key);
        if (esta) {
            mensaje = "EL codigo del caso se encuentra ya registrado en el sistema";
        } else {
            mapEstudiantes.put(key, estudiante);
            imprimirReporte();
            mensaje = "caso registrado con exito";
        }
        return mensaje;
    }

    public String recorrerLista() {
        StringBuilder mostrarLista = new StringBuilder();
        for (Map.Entry<String, Consultorio> consulta : mapEstudiantes.entrySet()) {
            mostrarLista.append(consulta.getKey()).append(": ").append(consulta.getValue().toString()).append("\n");
        }
        return mostrarLista.toString();
    }

    public String buscarCodigo(String codigo) {
        StringBuilder mensaje = new StringBuilder();
        boolean esta = false;
        for (Map.Entry<String, Consultorio> consulta : mapEstudiantes.entrySet()) {
            if (consulta.getKey().equalsIgnoreCase(codigo)) {
                mensaje.append(consulta.getKey()).append(": ").append(consulta.getValue().toString()).append("\n");
                esta = true;
                break;
            }
        }
        if (!esta) {
            mensaje.append("caso no encontrado dentro del sistema");
        }
        return mensaje.toString();
    }

    /*public String buscarNombre(String codigo) {
        StringBuilder mensaje = new StringBuilder();
        boolean esta = false;

        for (Map.Entry<String, Consultorio> consulta : mapEstudiantes.entrySet()) {
            Consultorio consultorio = consulta.getValue();

            // Imprimir la clave y el valor actuales
            System.out.println("Evaluando clave: " + consulta.getKey());
            System.out.println("Consultorio: " + consultorio.toString());

            if (consultorio.getNombreUsuario().equalsIgnoreCase(codigo) ||
                    consultorio.getTipoCaso().equalsIgnoreCase(codigo) ||
                    consultorio.getActivo_noActivo().equalsIgnoreCase(codigo)) {

                mensaje.append(consulta.getKey()).append(": ").append(consultorio.toString()).append("\n");
                esta = true;
            }
        }

        if (!esta) {
            mensaje.append("Estudiante no encontrado dentro del sistema.");
        }

        return mensaje.toString();
    }*/
    public String buscarNombre(String parametro) {
        boolean encontrado = false;
        StringBuilder mensaje = new StringBuilder();

        for (Map.Entry<String, Consultorio> entry : mapEstudiantes.entrySet()) {
            Consultorio consulta = entry.getValue();
            if (consulta.getNombreUsuario().equalsIgnoreCase(parametro) || consulta.getTipoCaso().equalsIgnoreCase(parametro)
                    ||consulta.getActivo_noActivo().equalsIgnoreCase(parametro)) {
                mensaje.append("caso encontrado: ").append(consulta).append("\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            mensaje.append("El producto no se pudo encontrar");
        }

        return mensaje.toString();
    }

    public String Eliminar(String codigo){
        boolean encontrado = false;
        String mensaje = "";
        for (Map.Entry<String, Consultorio> consulta : mapEstudiantes.entrySet()) {
            if (consulta.getKey().equalsIgnoreCase(codigo)) {
                mapEstudiantes.remove(codigo);
                mensaje = "caso eliminado con exito";
                encontrado = true;
                imprimirReporte();
                break;
            }
        }
        if (!encontrado) {
            mensaje = "El caso del usuario no se encontró en el sistema";
        }

        return mensaje;
    }

    public String modificarNombre(String nombreUsuario, String nuevonombre) {
        String mensaje = "";
        boolean encontrado = false;

        for (Map.Entry<String, Consultorio> entry : mapEstudiantes.entrySet()) {
            Consultorio usuario = entry.getValue();
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                usuario.setNombreUsuario(nuevonombre);
                mensaje = "Nombre modificado con éxito";
                encontrado = true;
                imprimirReporte();
                break;
            }
        }

        if (!encontrado) {
            mensaje = "El usuario no se encontró en el sistema";
        }

        return mensaje;
    }





}


