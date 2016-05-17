package com.everis.maven;

/**
 * Created by cmunoros on 17-05-2016.
 */
public class Consulta {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public String[] ConstruyeConsulta() {

        String[] queryTable = new String[2];

        queryTable[0] = "select idf_cto_ods, cod_contenido, fec_data, cod_pais, cod_entidad, cod_centro, cod_producto, cod_subprodu, num_cuenta, num_secuencia_cto, " +
                "cod_divisa, cod_reajuste, idf_pers_ods, cod_centro_cont, cod_ofi_comercial, cod_gestor_prod " +
                "from landing.he0_dt_cto_activo_dia limit 10";
        queryTable[1] = "he0_dt_cto_activo_dia";
        return queryTable;
    }

}
