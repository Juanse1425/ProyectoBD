import javax.swing.*;

public class Reporte {
    private Conexion conexion = new Conexion();
    public Reporte() {
        String consulta;
        int opcion = 0;
        do {
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog("¿Que desea hacer?\n\n" +
                        "1. Reporte 1\n" +
                        "2. Reporte 2\n" +
                        "3. Reporte 3\n" +
                        "4. Reporte 4\n" +
                        "5. Reporte 5\n" +
                        "6. Reporte 6\n" +
                        "7. Reporte 7\n" +
                        "8. Volver"));
                if(opcion >= 1 && opcion <= 8){
                    switch (opcion){
                        case 1:
                            consulta = """
                                    SELECT resp.*, est.*
                                    FROM examen exam
                                    JOIN respuesta resp ON exam.idExamen = resp.idExamen
                                    JOIN estudiante est ON est.dni_Estudiante = resp.dni_Estudiante;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                        case 2:
                            consulta = """
                                    SELECT AVG(resp.calificacion) AS promedioCalificacion
                                    FROM examen exam
                                    JOIN respuesta resp ON exam.idExamen = resp.idExamen
                                    JOIN estudiante est ON est.dni_Estudiante = resp.dni_Estudiante;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                        case 3:
                            consulta = """
                                    SELECT exam.*
                                    FROM examen exam
                                    JOIN horario hor ON hor.idHorario = exam.idHorario
                                    WHERE MONTH(hor.fechaExamen) > 6;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                        case 4:
                            consulta = """
                                    SELECT exam.idExamen , AVG(resp.calificacion) AS promedioCalificacionPregunta
                                    FROM examen exam
                                    JOIN respuesta resp ON exam.idExamen = resp.idExamen
                                    JOIN estudiante est ON est.dni_Estudiante = resp.dni_Estudiante
                                    GROUP BY exam.idExamen;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                        case 5:
                            consulta = """
                                    SELECT cur.*, und.idPlanEstudio, tem.codigoUnidad, und.nombre, und.descripcion, tem.idTema, tem.nombre
                                    FROM tema tem
                                    JOIN unidades und ON tem.codigoUnidad = und.codigo
                                    JOIN planestudio plan ON und.idPlanEstudio = plan.id
                                    JOIN curso cur ON plan.codigoCurso = cur.codigo;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                        case 6:
                            consulta = """
                                    SELECT est.dni_Estudiante, (SUM(examEst.calificacion)/COUNT(*)) AS CalificacionFinal,
                                    CASE
                                    \tWHEN (SUM(examEst.calificacion) / COUNT(*)) < 60 THEN 'Perdio'
                                    \tELSE 'Gano'
                                    END AS resultado
                                    FROM examenestudiante examEst
                                    JOIN estudiante est ON examEst.dni_Estudiante = est.dni_Estudiante
                                    GROUP BY examEst.dni_Estudiante;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                        case 7:
                            consulta = """
                                    SELECT examEst.idExamen, MIN(examEst.calificacion) as calificacion_minima,
                                        (SELECT est_min.nombre
                                         FROM examenestudiante examEst_min
                                         JOIN estudiante est_min ON examEst_min.dni_Estudiante = est_min.dni_Estudiante
                                         WHERE examEst_min.idExamen = examEst.idExamen
                                         AND examEst_min.calificacion = MIN(examEst.calificacion)
                                         LIMIT 1) as estudiante_minimo, MAX(examEst.calificacion) as calificacion_maxima,
                                        (SELECT est_max.nombre
                                         FROM examenestudiante examEst_max
                                         JOIN estudiante est_max ON examEst_max.dni_Estudiante = est_max.dni_Estudiante
                                         WHERE examEst_max.idExamen = examEst.idExamen
                                         AND examEst_max.calificacion = MAX(examEst.calificacion)
                                         LIMIT 1) as estudiante_maximo
                                    FROM examenestudiante examEst
                                    JOIN estudiante est ON examEst.dni_Estudiante = est.dni_Estudiante
                                    GROUP BY examEst.idExamen;""";
                            conexion.mostrarResultadosEnConsola(consulta);
                            break;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
            }
        } while (opcion != 8);
    }
}
