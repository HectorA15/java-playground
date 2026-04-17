package tarea.topicos.tema4;

import java.sql.*;

public class DB {

    public static void main(String[] args) {

        String url = "jdbc:sqlserver://localhost:1433;databaseName=DB;encrypt=true;trustServerCertificate=true"; //diferente para cada persona
        String user = "";
        String password = "";

        try {
            try (Connection connect = DriverManager.getConnection(url, user, password)) {
                System.out.println("Conectado a la DB");

                crearTabla(connect);
                insertarDatos(connect);
                leerTabla(connect);
            }

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    // esto es para MySql
    public static void crearTabla(Connection connect) {
        try (Statement command = connect.createStatement()) {
            String sql = "CREATE TABLE Empleados ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(50), "
                    + "salario INT, "
                    + "puesto VARCHAR(50))";
            try {
                command.executeUpdate(sql);
                System.out.println("Tabla creada con exito");
            } catch (SQLException e) {
                System.out.println("Error: ya existe o hubo un error inesperado");
            }

            command.close();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    // no cambia en ningun SGBD
    public static void insertarDatos(Connection connect) {
        try (Statement command = connect.createStatement()) {
            String sql1 = "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Luis', '12000', 'Becario')";
            String sql2 = "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Maria', '20000', 'Supervisor')";

            command.executeUpdate(sql1);
            command.executeUpdate(sql2);

            System.out.println("Datos agregados con exito");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    // no cambia en ningun SGBD
    public static void leerTabla(Connection connect) {
        try (Statement command = connect.createStatement()) {
            String sql = "SELECT * FROM Empleados";

            try (ResultSet resultados = command.executeQuery(sql)) {
                System.out.println("---- Empleados ----");

                while (resultados.next()) {
                    int id = resultados.getInt("id");
                    String nombre = resultados.getString("nombre");
                    String puesto = resultados.getString("puesto");
                    int salario = resultados.getInt("salario");

                    System.out.println("ID: " + id + " | Nombre: " + nombre + " | Puesto: " + puesto + " | Salario: " + salario);
                }
            } catch (Exception e) {
                System.out.println("Error : no se logro conseguir los datos de la tabla");
            }
            command.close();
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }

    }

}
