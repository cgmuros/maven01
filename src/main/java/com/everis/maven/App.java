package com.everis.maven;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class App {



    public static void main(String[] args) throws IOException, SQLException {
        String consulta;
        ArrayList<String> tablas = new ArrayList<String>();
        String nombreTabla = args[0];

        Consulta query = new Consulta();

        System.out.println("Iniciando el proceso de tabla: " + nombreTabla);

        query.setNombreTabla(nombreTabla);
        consulta = query.ConstruyeConsulta();

        Archivo archivo = new Archivo();
        archivo.setNombreArchivo(nombreTabla);
        archivo.setConsulta(consulta);
        archivo.generaArchivo();

    }



}
