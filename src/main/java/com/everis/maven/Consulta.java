package com.everis.maven;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by cmunoros on 17-05-2016.
 */
public class Consulta {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private String nombreTabla;

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String ConstruyeConsulta() throws IOException, SQLException {

        String queryTable = "select ";
        int flag= 0; //1. Primer campo. sin coma la inicio. 0. Los demas que si deben llevar

        ResultSet resCampos;

        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("config.properties");
        prop.load(file);

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            System.exit(1);
        }
        Connection con = DriverManager.getConnection(prop.getProperty("jdbcString"), prop.getProperty("user"), prop.getProperty("password"));

        Statement stmt = con.createStatement();
        resCampos = stmt.executeQuery("select nombre_campo from cdeexp.tbl_param_flat where nombre_tabla = '" + this.nombreTabla + "'");

        while (resCampos.next()) {
            if (flag == 0)
                queryTable = queryTable + "cast("+resCampos.getString(1)+" as string) ";
            else
                queryTable = queryTable + " , " + "cast("+resCampos.getString(1)+" as string)";
            flag = 1;
        }

        //TODO: Revisar el tema en duro de "from landing"
        queryTable = queryTable + " from cdeexp." + this.nombreTabla;
        //TODO: ELiminar. solo para pruebas
        //queryTable = queryTable + " from cdeexp." + this.nombreTabla + " limit 10 ";

        return queryTable;
    }

    public ArrayList<String> listaTablas() throws IOException, SQLException {

        ArrayList<String> tablas = new ArrayList<String>();
        ResultSet res;
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("config.properties");

        prop.load(file);

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection(prop.getProperty("jdbcString"), prop.getProperty("user"), prop.getProperty("password"));

        Statement stmt = con.createStatement();
        res = stmt.executeQuery("select nombre_tabla from cdeexp.tbl_param_flat group by nombre_tabla");

        while (res.next()) {
            tablas.add(res.getString(1));
        }

        return tablas;

    }

}
