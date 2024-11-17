import java.io.IOException;

import co.poli.edu.controller.GameController;

/**
 * Clase principal de la aplicación.
 * Se encarga de inicializar el controlador del juego y comenzar el flujo.
 */
public class MainApp {

    /**
     * Método principal que inicia la ejecución de la aplicación.
     *
     * @param args argumentos de línea de comandos.
     * @throws IOException si ocurre un error de entrada/salida durante la ejecución.
     */
    public static void main(String[] args) throws IOException {
        // Instancia del controlador del juego
        GameController gameController = new GameController();

        // Inicia el juego
        gameController.startGame();
    }
}
