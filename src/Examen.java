import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Examen {
    private Conexion conexion = new Conexion();

    public Examen() {
        String categorias = devolvercategorias();
        int ultimaCategoria = obtenerUltimaCategoria(categorias);

        while (true) {
            String id = obtenerInputValido("Ingrese el id del examen");
            boolean existeId = conexion.verificarID("SELECT COUNT(*) AS total FROM examen WHERE idExamen = " + id);

            if (!existeId) {
                String nombre = obtenerInputValido("Ingrese el nombre del examen");
                int categoria = obtenerCategoriaValida(categorias, ultimaCategoria);
                double tiempo = obtenerDoubleValido("Ingrese el tiempo del examen");
                String descripcion = obtenerInputValido("Ingrese la descripción del examen");
                int cant_preguntas = obtenerEnteroValido("Ingrese la cantidad de preguntas del examen");
                int cant_preguntas_estudiante = obtenerCantPreguntasEstudianteValida(cant_preguntas);
                double peso = obtenerDoubleValido("Ingrese el peso del examen");
                double umbral = obtenerDoubleValido("Ingrese el umbral del examen");
                String sentencia = "INSERT INTO examen (idExamen, nombre, id_categoria, tiempo, descripcion, idHorario, cant_preguntas, cant_preguntas_estudiante, peso, estado, umbral) VALUES (" + id + ", '" + nombre + "', " + categoria + ", " + tiempo + ", '" + descripcion + "', NULL, " + cant_preguntas + ", " + cant_preguntas_estudiante + ", " + peso + ", 'creado', " + umbral + ")";
                conexion.ejecutarSentenciaSQL(sentencia);
                break;
            } else {
                System.out.println("El id ya existe");
            }
        }
    }

    private String devolvercategorias() {
        StringBuilder message = new StringBuilder();
        try (Connection connection = conexion.conectar();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre FROM categoria");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                message.append(id).append("- ").append(nombre).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    private int obtenerUltimaCategoria(String categorias) {
        String[] partes = categorias.split("\n");
        return Integer.parseInt(partes[partes.length - 1].split("-")[0]);
    }

    private String obtenerInput(String mensaje) {
        return JOptionPane.showInputDialog(mensaje);
    }

    private String obtenerInputValido(String mensaje) {
        String input;
        do {
            input = obtenerInput(mensaje);
        } while (input == null || input.trim().isEmpty());
        return input;
    }

    private int obtenerEnteroValido(String mensaje) {
        int valor;
        do {
            try {
                valor = Integer.parseInt(obtenerInputValido(mensaje));
            } catch (NumberFormatException e) {
                valor = 0;
            }
        } while (valor < 1);
        return valor;
    }

    private double obtenerDoubleValido(String mensaje) {
        double valor;
        do {
            try {
                valor = Double.parseDouble(obtenerInputValido(mensaje));
            } catch (NumberFormatException e) {
                valor = 0;
            }
        } while (valor < 1);
        return valor;
    }

    private int obtenerCantPreguntasEstudianteValida(int cantPreguntas) {
        int valor;
        do {
            valor = obtenerEnteroValido("Ingrese la cantidad de preguntas que se le mostrarán al estudiante");
        } while (valor < 1 || valor > cantPreguntas);
        return valor;
    }

    private int obtenerCategoriaValida(String categorias, int ultimaCategoria) {
        int categoria;
        do {
            categoria = obtenerEnteroValido("Ingrese el id de la categoría del examen\n\n" + categorias);
        } while (categoria < 1 || categoria > ultimaCategoria);
        return categoria;
    }

}