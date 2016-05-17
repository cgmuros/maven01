package com.everis.maven;

import java.sql.*;
import java.io.*;

public class App 
{

  private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main( String[] args ) throws IOException,SQLException
    {

      ResultSet res;
      FileWriter fichero = null;
      PrintWriter pw = null;
      final long startTime = System.currentTimeMillis();

        System.out.println( "Iniciando creacion de Archivo" );


        try {
            fichero = new FileWriter("prueba.txt");
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

        Connection con = DriverManager.getConnection("jdbc:hive2://10.172.24.11:10000/default", "cgmuros", "Alfa@1414");

        // create statement
        Statement stmt = con.createStatement();

        // execute statement
        res = stmt.executeQuery("select idf_cto_ods, cod_contenido, fec_data, cod_pais, cod_entidad, cod_centro, cod_producto, cod_subprodu, num_cuenta, num_secuencia_cto, " +
                                "cod_divisa, cod_reajuste, idf_pers_ods, cod_centro_cont, cod_ofi_comercial, cod_gestor_prod " +
                                "from landing.he0_dt_cto_activo_dia");
            while (res.next()) {
              pw.println(res.getString(1) + ";" + res.getString(2) + ";" + res.getInt(3) + ";" + res.getInt(4) + ";" + 
                        res.getString(5) + ";" + res.getString(6) + ";" + res.getString(7) + ";" + res.getString(8) + ";" + 
                        res.getString(9) + ";" + res.getString(10) + ";" + res.getString(11) + ";" + res.getString(12) + ";" + 
                        res.getString(13) + ";" + res.getString(14) + ";" + res.getString(15) + ";" + res.getString(16));

        }

        fichero.close();

        //Cierro conexion
        con.close();

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) );
    }
}
