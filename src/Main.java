import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
            int opcion;
            do {
                opcion = mostrarMenu();
                ejecutarAccion(opcion);
            } while (opcion != 5);
    }

    private static int mostrarMenu() {
        int opcion = 0;
        try {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("""
                    ¿Qué desea hacer?

                    1. Crear Examen
                    2. Agregar Preguntas
                    3. Presentar Examen
                    4. Reportes
                    5. Salir"""));

            if (opcion < 1 || opcion > 5) {
                JOptionPane.showMessageDialog(null, "Ingrese una opción válida");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese una opción válida");
        }
        return opcion;
    }

    private static void ejecutarAccion(int opcion) {
        switch (opcion) {
            case 1:
                new Examen();
                break;
            case 2:
                // Lógica para agregar preguntas
                break;
            case 3:
                // Lógica para presentar examen
                break;
            case 4:
                new Reporte();
                break;
        }
    }
}