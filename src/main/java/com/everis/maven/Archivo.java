package com.everis.maven;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
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
        Integer intCantidadCampos = 0;

        ArrayList<String> arrLargos = new ArrayList<String>();

        prop.load(file);

        ResultSet res;
        ResultSet resLargos; // Largo de cada campo de la tabla consultada
        FileWriter fichero = null;
        PrintWriter pw = null;
        final long startTime = System.currentTimeMillis();

        try {
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
        Statement stmtDatos = con.createStatement();

        //Ejecuta consulta ya pre configurada
        //Ejemplo. select cod_cta_cont, cod_entidad, cod_pais from he0_dm_plan_cta
        res = stmtDatos.executeQuery(consulta);

        //Consulta largos de cada campo desde la tabla parametrica
        resLargos = stmt.executeQuery("select " + prop.getProperty("nombreCampoLargoParamtrica") + " from " + prop.getProperty("baseTablaParametrica") + "  where " + prop.getProperty("nombreCampoTablaParametrica") + " = '" + nombreArchivo + "'");

        //Obtengo cantidad de campos y traspaso valores a arraylist
        while (resLargos.next()) {
            arrLargos.add(resLargos.getString(1));
            intCantidadCampos++;
        }

        while (res.next()) {
            int i = 1;
            ArrayList<String> arrCamposFormateados = new ArrayList<String>();
            //Recorro cada uno de los campos

            try {
                for (String val: arrLargos) {
                    String strDato = res.getString(i);

                    //Para casos en que el dato es nulo
                    if ( res.getString(i) == null) {
                        arrCamposFormateados.add("");
                    } else {
                        //Agrego espacios mientras sea menor al largo del dato
                        while (strDato.length() < Integer.parseInt(val)) {
                            strDato = " " + strDato;
                        }
                        arrCamposFormateados.add(strDato);
                    }
                    i++;
                }
            } catch (Exception e) {
                System.out.println("Campo: "+ res.getString(i));
                System.out.println("Error: "+ e.getMessage());
            }


            String fila = "";
            //escribo fila formateada en archivo
            for (String val: arrCamposFormateados) {
                fila = fila + val+";";
            }

            pw.println(fila);
        }

        fichero.close();

        //Cierro conexion
        con.close();

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

}
