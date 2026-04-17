package tarea.topicos.tema4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.*;

public class DBController {
    @FXML
    private Button btnConnect;

    @FXML
    private GridPane gridDB;

    @FXML
    private Label lblBD;

    private int filaActual = 0;
    private boolean conectado = false;

    @FXML
    public void initialize() {
        gridDB.getChildren().clear();
        gridDB.getRowConstraints().clear(); // Borra las reglas de altura de las filas
        gridDB.getColumnConstraints().clear(); // Borra las reglas de ancho de las columnas

        gridDB.setHgap(15);
        gridDB.setVgap(5);
        gridDB.setPadding(new Insets(20));

        lblBD.setText("Base de datos: Empleados");
        lblBD.getStyleClass().add("-fx-font-size: 20px; -fx-font-weight: bold;");


        btnConnect.getStyleClass().add("btn-connect");

        gridDB.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        filaActual = 0;
    }


    @FXML
    void DeleteDB(ActionEvent actionEvent) {
        String url = "jdbc:mysql://localhost:3306/logitech";
        String user = "root";
        String password = "admin";

        try (Connection connect = DriverManager.getConnection(url, user, password)) {
            borrarTabla(connect);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

    }

    @FXML
    private void Connect(ActionEvent actionEvent) {


        String url = "jdbc:mysql://localhost:3306/logitech";
        String user = "root";
        String password = "admin";

        if (conectado) {

            gridDB.getChildren().clear();
            gridDB.getRowConstraints().clear();
            gridDB.getColumnConstraints().clear();

            filaActual = 0;

            btnConnect.setText("Conectar");

            btnConnect.getStyleClass().remove("btn-disconnect");
            btnConnect.getStyleClass().add("btn-connect");

            conectado = false;
            gridDB.layout();
            System.out.println("Desconectando...");
            return;
        }

        try {
            try (Connection connect = DriverManager.getConnection(url, user, password)) {
                System.out.println("Conectado a la DB");

                gridDB.getChildren().clear();
                filaActual = 0;

                crearTabla(connect);
                insertarDatos(connect);
                leerTabla(connect);
            }
            conectado = true;
            btnConnect.setText("Desconectar");

            btnConnect.getStyleClass().remove("btn-connect");
            btnConnect.getStyleClass().add("btn-disconnect");
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

    }

    // esto es para MySql
    public void crearTabla(Connection connect) {
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
    public void insertarDatos(Connection connect) {
        try (Statement command = connect.createStatement()) {
            String[] sql = {
                    "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Luis', '12000', 'Becario')",
                    "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Maria', '20000', 'Supervisor')",
                    "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Carlos', '15000', 'Desarrollador')",
                    "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Ana', '18000', 'Analista')",
                    "INSERT INTO Empleados (nombre, salario, puesto) VALUES ('Sofia', '22000', 'Gerente')"
            };

            for (String datos : sql) {
                command.executeUpdate(datos);
            }

            System.out.println("Datos agregados con exito");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    // no cambia en ningun SGBD
    public void leerTabla(Connection connect) {
        try (Statement command = connect.createStatement()) {
            String sql = "SELECT * FROM Empleados";

            try (ResultSet resultados = command.executeQuery(sql)) {

                agregarHeader(new String[]{"ID", "Nombre", "Puesto", "Salario"});
                filaActual = 1;

                while (resultados.next()) {
                    int id = resultados.getInt("id");
                    String nombre = resultados.getString("nombre");
                    String puesto = resultados.getString("puesto");
                    int salario = resultados.getInt("salario");

                    agregarAlGrid(id, nombre, puesto, salario);
                    filaActual++;
                }
            } catch (Exception e) {
                System.out.println("Error : no se logro conseguir los datos de la tabla");
            }
            command.close();
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }

    }

    public void borrarTabla(Connection connect) {
        try (Statement command = connect.createStatement()) {
            String sql = "DROP TABLE IF EXISTS Empleados";
            command.executeUpdate(sql);
            System.out.println("Tabla borrada");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void agregarAlGrid(int id, String nombre, String puesto, int salario) {
        Label idLabel = new Label(String.valueOf(id));
        Label nombreLabel = new Label(nombre);
        Label puestoLabel = new Label(puesto);
        Label salarioLabel = new Label(String.valueOf(salario));


        Label[] labels = {idLabel, nombreLabel, puestoLabel, salarioLabel};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setAlignment(Pos.CENTER);
            gridDB.add(labels[i], i, filaActual);
        }
    }

    public void agregarHeader(String[] headers) {
        for (int i = 0; i < headers.length; i++) {
            Label header = new Label(headers[i]);
            header.getStyleClass().add("data-header");
            header.setAlignment(Pos.CENTER);
            gridDB.add(header, i, 0);
        }
    }

}
