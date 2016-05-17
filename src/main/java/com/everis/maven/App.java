package com.everis.maven;

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class App {



    public static void main(String[] args) throws IOException, SQLException {
        String[] consulta;

        //Construye consulta
        Consulta query = new Consulta();
        consulta = query.ConstruyeConsulta();

        Archivo archivo = new Archivo();
        archivo.setNombreArchivo(consulta[1]);
        archivo.setConsulta(consulta[0]);

        archivo.generaArchivo();
    }

}
