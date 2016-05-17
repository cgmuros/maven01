package com.everis.maven;

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class App {



    public static void main(String[] args) throws IOException, SQLException {
        Archivo archivo = new Archivo();
        archivo.generaArchivo();
    }

}
