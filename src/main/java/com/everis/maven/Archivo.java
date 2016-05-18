package com.everis.maven;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by cmunoros on 17-05-2016.
 */
public class Archivo {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private String nombreArchivo;
    private String consulta;

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public void generaArchivo() throws IOException, SQLException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("config.properties");

        prop.load(file);

        ResultSet res;
        FileWriter fichero = null;
        PrintWriter pw = null;
        final long startTime = System.currentTimeMillis();

        try {
            //TODO: El nombre del archivo debe tener concatenado el año, mes y dia.
            fichero = new FileWriter(this.nombreArchivo+".csv");
            pw = new PrintWriter(fichero);
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra archivo " + e.getMessage());
        }

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection(prop.getProperty("jdbcString"), prop.getProperty("user"), prop.getProperty("password"));

        // create statement
        Statement stmt = con.createStatement();

        res = stmt.executeQuery(consulta);

        //TODO: se debe realizar logica para generar datos con largos definidos en tabla hive parametrica.
        while (res.next()) {
            pw.println(res.getString(1) + ";" + res.getString(2) + ";" + res.getInt(3));
        }

        fichero.close();

        //Cierro conexion
        con.close();

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

}
