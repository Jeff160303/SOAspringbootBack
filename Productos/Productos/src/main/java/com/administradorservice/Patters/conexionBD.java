package com.administradorservice.Patters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBD {

    private static String url="jdbc:sqlserver://localhost:1434;databaseName=INVENTARIO;TrustServerCertificate=true";


    private static String userName = "sa";
    private static String password = "sa1234";
    private static Connection con;

    public static Connection getConexion() throws SQLException {

        con = DriverManager.getConnection(url,userName, password);



        return con;
    }

    public static Connection setConection(String url, String userName, String password) throws SQLException {



        con = DriverManager.getConnection(url);


        return con;
    }

}