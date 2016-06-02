package com.everis.maven;


import junit.framework.TestCase;


import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by cmunoros on 17-05-2016.
 */
public class ArchivoTest  extends TestCase {

    public void testGeneracionArchivo() throws IOException, SQLException {
        Archivo archivo = new Archivo();
        archivo.generaArchivo();
        assertTrue(true);
    }

}
