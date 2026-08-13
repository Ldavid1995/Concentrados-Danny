package com.concentrados.Danny.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:oracle:thin:@localhost:8085:xe"; // Ajusta tu host/SID/ServiceName
    private static final String USER = "Concentrados";
    private static final String PASSWORD = "Proyecto$2026";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}