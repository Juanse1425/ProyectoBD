import java.sql.*;

public class Conexion {

    String url = "jdbc:mysql://localhost:3306/proyectobases";
    String username = "root";
    String password = "root";
    Connection connection;

    public Conexion() {

    }

    public Connection conectar() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }

    public void desconectar() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al desconectar de la base de datos: " + e.getMessage());
        }
    }

    public boolean verificarID(String consulta) {
        boolean idExistente = false;

        try {
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();

            // Verificar si hay resultados y si el total es mayor que cero
            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                if (total > 0) {
                    idExistente = true; // El ID ya existe en la tabla
                }
            }
            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idExistente;
    }

    public void ejecutarSentenciaSQL(String sentenciaSQL) {
        Connection conexion = null;
        PreparedStatement statement = null;

        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sentenciaSQL);

            // Ejecutar la sentencia SQL para una inserción
            int filasAfectadas = statement.executeUpdate(); // Ejecutar un INSERT, UPDATE o DELETE

            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa. Se han insertado " + filasAfectadas + " filas.");
            } else {
                System.out.println("No se ha realizado ninguna inserción.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrarResultadosEnConsola(String consulta) {
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecer conexión con la base de datos
            conexion = conectar();

            // Crear una declaración (statement)
            statement = conexion.createStatement();

            // Ejecutar la consulta SQL
            resultSet = statement.executeQuery(consulta);

            // Iterar a través de los resultados y mostrar en la consola
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, Statement y Connection en el bloque finally
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}