import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int opcion = 0;
        do {
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog("¿Que desea hacer?\n\n" +
                        "1. Crear Examen\n" +
                        "2. Agregar Preguntas\n" +
                        "3. Presentar Examen\n" +
                        "4. Reportes\n" +
                        "5. Salir"));
                if(opcion >= 1 && opcion <= 5){
                    switch (opcion){
                        case 1:
                            new Examen();
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            new Reporte();
                            break;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
            }
        } while (opcion != 5);
    }
}
