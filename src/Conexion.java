import java.sql.*;

public class Conexion {

    public Conexion() {}

    public Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/proyectobases";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }

    public boolean verificarID(String consulta) {
        try (Connection conexion = conectar();
             PreparedStatement statement = conexion.prepareStatement(consulta);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                return total > 0;
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente según el contexto
            e.printStackTrace();
        }
        return false;
    }

    public void ejecutarSentenciaSQL(String sentenciaSQL) {
        try (Connection conexion = conectar();
             PreparedStatement statement = conexion.prepareStatement(sentenciaSQL)) {

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa. Se han insertado " + filasAfectadas + " filas.");
            } else {
                System.out.println("No se ha realizado ninguna inserción.");
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente según el contexto
            e.printStackTrace();
        }
    }

    public void mostrarResultadosEnConsola(String consulta) {
        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(consulta)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente según el contexto
            e.printStackTrace();
        }
    }
}
