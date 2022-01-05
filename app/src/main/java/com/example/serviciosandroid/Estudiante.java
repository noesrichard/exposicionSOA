package com.example.serviciosandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Estudiante {
    private String cedula, nombre, apellido, direccion, telefono;

    public Estudiante(String cedula, String nombre, String apellido, String direccion, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Estudiante(){}

    public static Estudiante fromJson(JSONObject estudianteJson){
       Estudiante e = new Estudiante();
       try {
           e.cedula = estudianteJson.getString("EST_CEDULA");
           e.nombre = estudianteJson.getString("EST_NOMBRE");
           e.apellido = estudianteJson.getString("EST_APELLIDO");
           e.direccion = estudianteJson.getString("EST_DIRECCION");
           e.telefono = estudianteJson.getString("EST_TELEFONO");
       } catch(Exception ex) {
           ex.printStackTrace();
       }
       return e;
    }

    public static ArrayList<Estudiante> getEstudiantesFromJson(JSONArray estudiantesJArray){
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>(estudiantesJArray.length());
        for(int i = 0; i < estudiantesJArray.length(); i++) {
            try {
                Estudiante estudiante = fromJson(estudiantesJArray.getJSONObject(i));
                if( estudiante != null) {
                    estudiantes.add(estudiante);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return estudiantes;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
