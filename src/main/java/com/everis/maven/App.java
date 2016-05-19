package com.everis.maven;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class App {



    public static void main(String[] args) throws IOException, SQLException {
        String consulta;
        ArrayList<String> tablas = new ArrayList<String>();

        //Construye consulta, la obtiene y obtiene el nombre de la tabla.
        Consulta query = new Consulta();
        tablas = query.listaTablas();

        for(String val : tablas) {
            query.setNombreTabla(val);
            consulta = query.ConstruyeConsulta();

            Archivo archivo = new Archivo();
            archivo.setNombreArchivo(val);
            archivo.setConsulta(consulta);

            archivo.generaArchivo();

        }


    }



}
