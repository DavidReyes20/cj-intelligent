package modelo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Array {

    public Map<String, Recursos> recursosLegales = new HashMap<>();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Array(){
        Type tipoMap = new TypeToken<Map<Integer,Recursos>>() {}.getType();
        try (FileReader reader = new FileReader("recursos.json")) {
            recursosLegales = gson.fromJson(reader, tipoMap);
            System.out.println("Datos escritos en recursos.json");
        }catch (IOException e) {
            recursosLegales = new HashMap<>();
            actualizarDatos();
        }
    }

    private void actualizarDatos(){
        try (FileWriter writer = new FileWriter("recursos.json")) {
            gson.toJson(recursosLegales, writer);
            System.out.println("Datos escritos en data.json");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String agregarRecurso(String codi ,Recursos pdt){
        String mensaje = "";
        boolean estaono = recursosLegales.containsKey(codi);
        if (estaono == true){
            JOptionPane.showInputDialog("el estudiante ya se encuentra ");
        }else {
            recursosLegales.put(codi,pdt);
            actualizarDatos();
            mensaje = ("Se agrego ");
        }
        return mensaje;
    }


    public String buscarRecursoLegal(int numero) {
        boolean estaono = recursosLegales.containsKey(numero);
        if (estaono == true) {
            JOptionPane.showInputDialog("El recurso legal está presente en la lista.");
            JOptionPane.showInputDialog(String.valueOf(recursosLegales.get(numero)));
        } else {
            JOptionPane.showInputDialog("El recurso legal no se encuentra en la lista.");
        }
        return "";
    }

    public String modificarRecursoLegal(int numero, String nuevoRecurso,String nuevoEscrito) {
        Recursos l = recursosLegales.get(numero);
        if (l != null) {
            l.setRecurso(nuevoRecurso);
            l.setEscrito(nuevoEscrito);
            JOptionPane.showInputDialog("Recurso legal modificado correctamente.");
            actualizarDatos();
        }else {
            JOptionPane.showInputDialog("El recurso legal no se encuentra en la lista.");
        }
        return "";
    }



    public int eliminarRecursoLegal(int borrar) {

        boolean estaono = recursosLegales.containsKey(borrar);
        if (estaono == true) {
            recursosLegales.remove(borrar);
            JOptionPane.showInputDialog("Recurso legal eliminado correctamente.");
            actualizarDatos();
        }else {
            JOptionPane.showInputDialog("El recurso legal no se encuentra en la lista.");
        }
        return 0;
    }

    public String recorrer(){
        JOptionPane.showInputDialog(recursosLegales);
        actualizarDatos();
        return "";
    }
}
