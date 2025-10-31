package com.oberon.monitoramento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://44.207.133.113:3306/bdOberon";
    private static final String USER = "oberon_cliente";
    private static final String PASSWORD = "ClienteOberon123"; // coloque sua senha aqui

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
