package org.ottonielmenendez.db;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;

public class Conexion {

    private Connection conexion;
    private static Conexion instancia;

    /*
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
flush privileges;
     */
    private Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            conexion = DriverManager.getConnection("jdbc:mysql://192.168.1.2:3306/DBTonysKinal2023_2019484?userSSL=false", "quinto", "kinal");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBTonysKinal2023_2019484?userSSL=false", "root", "admin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
