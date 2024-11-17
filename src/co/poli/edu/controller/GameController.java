package co.poli.edu.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.poli.edu.model.Game;
import co.poli.edu.model.Player;
import co.poli.edu.model.Question;
import co.poli.edu.model.QuestionList;

/**
 * Controlador principal del juego. Se encarga de la lógica del juego, manejo de preguntas,
 * control de puntos y vidas del jugador, y la interacción con el usuario.
 */
public class GameController {
	
	private final Scanner scanner;
	
	Game game= new Game();
	Player player = new Player();

    /**
     * Constructor de la clase GameController.
     * Inicializa el scanner para la entrada de datos por consola.
     */
    public GameController() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el juego, muestra el nombre del juego, la explicación, y carga las preguntas.
     * @throws IOException Si ocurre un error al cargar las preguntas.
     */
	public void startGame() throws IOException {
		game.startGame();
		initPlayer();
		showNameGame();
		showExplaniationGame();
		loadListQuestions();
	}

    /**
     * Carga la lista de preguntas y procede con la siguiente pregunta.
     * @throws IOException Si ocurre un error al cargar las preguntas desde el archivo.
     */
	public void loadListQuestions() throws IOException {
		game.setQuestions(loadQuestions());
		nextQuestion();
	}
	
    /**
     * Avanza a la siguiente pregunta, valida la respuesta y actualiza el estado del juego.
     * Si el jugador llega a 6 puntos o se queda sin vidas, termina el juego.
     * @throws IOException Si ocurre un error al cargar las preguntas.
     */
	public void nextQuestion() throws IOException{
		int points = getPoints();
		
		if(points == 6) {
			endGame("*******Felicidades, haz completado el juego, eres todo un ingeniero! :)*******");
		}
		int level = reviewLevel(points);
		
		boolean response = getNewQuestion(game.getQuestions(), level, game.getNumberQuestion());
		
		if(response) {
			addNewPoint();
			nextQuestion();
		}else {
			loseLive();
			int lives = getLives();
			if(lives == 0) {
				endGame("**********************¡No tienes mas vidas, fin del juego*********************");
			}else {
				nextQuestion();
			}
		}
	}

    /**
     * Aumenta el puntaje del jugador en 1.
     */
	public void addNewPoint() {
		player.setNumberPoints(player.getNumberPoints() + 1);
	}
	
    /**
     * Revisa el nivel del jugador basado en los puntos obtenidos.
     * @param points Los puntos actuales del jugador.
     * @return El nivel del jugador.
     */
	public int reviewLevel(int points) {
		int actuallyLevel = game.getNumberLevel();
		
		if(actuallyLevel == 1 && (points >= 2 && points < 4)) {
			changueLevel(2);
			return 2;
		}else if(actuallyLevel == 2 && points >= 4){
			changueLevel(3);
			return 3;
		}else if(actuallyLevel == 1 && points < 2){
			return 1;
		}
		
		return actuallyLevel;
	}
	
    /**
     * Cambia el nivel del juego.
     * @param level El nuevo nivel del juego.
     */
	public void changueLevel(int level) {
		game.setNumberLevel(level);
		game.setUseHelp(false);
	}
	
    /**
     * Finaliza el juego mostrando un mensaje.
     * @param message El mensaje final del juego.
     * @throws IOException Si ocurre un error al finalizar el juego.
     */
	public void endGame(String message) throws IOException {
		showEndMessage(message);
		continueGame();
	}
	
    /**
     * Permite al jugador decidir si quiere continuar con el juego después de terminar.
     * @throws IOException Si ocurre un error al reiniciar el juego.
     */
	public void continueGame() throws IOException { 
		System.out.println("");
		System.out.println("******************************************************************************");
		System.out.println("******************************************************************************");
		System.out.println("************************* ¿ Desea volver a intentar ? ************************");
		System.out.println("******************************************************************************");
		System.out.println("************************ SI ********************** NO ************************");
		System.out.println("******************************************************************************");
		System.out.println("");
		System.out.println("*************** Escriba su respuesta y presione la tecla Enter ***************");
		System.out.println("");
		
        // Leer la respuesta del usuario
        String userAnswer = scanner.nextLine().trim().toUpperCase();
        
        if(userAnswer.equals("SI")) {
        	startGame();
        }else {
        	System.exit(0);
        }
	}
	
    /**
     * Carga las preguntas desde un archivo JSON.
     * @return Lista de preguntas cargadas desde el archivo.
     * @throws IOException Si ocurre un error al cargar el archivo o procesar las preguntas.
     */
    public List<Question> loadQuestions() throws IOException {
    	
        // Usar la ruta relativa para acceder al archivo dentro de recursos
        URL resourceUrl = getClass().getClassLoader().getResource("resources/questions_list.json");

        // Verificar si el archivo existe
        if (resourceUrl == null) {
            throw new IOException("El archivo no se encontró en la ruta especificada.");
        }

        // Convertir el recurso URL a un objeto File
        File file = new File(resourceUrl.getFile());
        
        // Crear un ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // Convertir el JSON a un objeto List<Question>
        QuestionList questionList = objectMapper.readValue(file, QuestionList.class);
        
        return questionList.getQuestions();
    }
    
    /**
     * Obtiene una pregunta disponible del listado de preguntas filtradas por nivel.
     * @param questionList Lista de preguntas.
     * @param level El nivel de la pregunta que se desea obtener.
     * @return Una pregunta disponible.
     */
    public Question getAvailableQuestion(List<Question> questionList, int level) {
    
        // Filtrar las preguntas por nivel y disponibilidad
        List<Question> filteredQuestions = questionList.stream()
                .filter(question -> question.getNumberLevel() == level) // Filtrar por nivel
                .filter(Question::getIsAvaliable) // Filtrar por disponibilidad
                .collect(Collectors.toList());

        // Seleccionar una pregunta
        return filteredQuestions.get(0);
    }
    
    /**
     * Obtiene una nueva pregunta, muestra la pregunta y valida la respuesta.
     * @param questionList Lista de preguntas.
     * @param level El nivel de dificultad de la pregunta.
     * @param numberQuestion El número de la pregunta en el juego.
     * @return true si la respuesta es correcta, false si es incorrecta.
     */
    public boolean getNewQuestion(List<Question> questionList, int level, int numberQuestion) {
    	
    	try {
    		Question question = getAvailableQuestion(questionList, level);
    		
    		//Aumentar el numero de la pregunta
    		game.setNumberQuestion(numberQuestion + 1);
    		
            // Mostrar la pregunta
            showQuestion(question, game.getNumberQuestion());

            // Leer la respuesta del usuario
            String userAnswer = scanner.nextLine().trim().toUpperCase();

            if(userAnswer.equals("*")) {
            	
            	if(!game.isUseHelp()) {
                	adjustHelps();
                	
                	System.out.println("");
                	System.out.println("Pista:");
                	System.out.println("");
                	System.out.println(question.getClue());
            		System.out.println("");
            		System.out.println("Escriba la letra de la opcion que considera correcta y presione Enter.");
                	
                	userAnswer = scanner.nextLine().trim().toUpperCase();
            	}else {
            		System.out.println("");
            		System.out.println("Ya usaste tu ayuda en esta ronda, escribe la opcion que consideras correcta y presiona Enter.");
            		
            		userAnswer = scanner.nextLine().trim().toUpperCase();
            	}

            }
            	
	         // Validar la respuesta
	         boolean isCorrect = validateAnswer(userAnswer, question);	
            
	         // Actualizar el estado de disponibilidad
            question.setIsAvaliable(false);
            
            if (isCorrect) {
                System.out.println("*************************** ¡Respuesta correcta! *****************************");
                return true;
            } else {
                System.out.println("************************** Respuesta incorrecta :( ***************************");
                return false;
            }

		} catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
            return false;
		}
	}
    
    /**
     * Ajusta las ayudas disponibles para el jugador.
     */
    public void adjustHelps() { 
    	game.setUseHelp(true);
    	game.setNumberTohelps(game.getNumberTohelps() - 1);
    }
    
    /**
     * Muestra un mensaje al finalizar el juego.
     * @param message El mensaje que se mostrará al final del juego.
     */
	public void showEndMessage(String message) {
		System.out.println("");
		System.out.println("******************************************************************************");
		System.out.println("******************************************************************************");
		System.out.println(message);
		System.out.println("******************************************************************************");
		System.out.println("******************************************************************************");
		System.out.println("");
	}

    /**
     * Muestra el nombre del juego en consola.
     */
	public void showNameGame() {
		System.out.println("******************************************************************************");
		System.out.println("************** Bienvenido a ¿ Quien quiere ser Ingeniero ? *******************");
		System.out.println("******************************************************************************");
		System.out.println("");
	}
	
    /**
     * Muestra la explicación del juego en consola.
     */
	public void showExplaniationGame() {
		System.out.println("");
		System.out.println("*******************************************************************************");
		System.out.println("*************** ¡El objetivo es contestar 6 preguntas correctamente! **********");
		System.out.println("****** Si fallas alguna, pierdes una vida. Si te quedas sin vidas, pierdes. ***");
		System.out.println("********** Tienes una ayuda por cada nivel y cada dos preguntas ***************");
		System.out.println("********************* cambias de nivel, mucha suerte! *************************");
		System.out.println("*******************************************************************************");
		System.out.println("");
	}

    /**
     * Muestra la pregunta y sus opciones.
     * @param question La pregunta que se mostrará.
     * @param numberQuestion El número de la pregunta.
     */
	public void showQuestion(Question question, int numberQuestion) {
		System.out.println("");
		System.out.println("Pregunta N°: " + numberQuestion + " | vidas: " + player.getNumberLives() + " | Puntos: " + player.getNumberPoints() + " | Ayudas: " + game.getNumberTohelps() );
		System.out.println("");
		System.out.println(question.getStatementQuestion());
		System.out.println("");
		System.out.println("");
		System.out.println("Opciones de respuesta:");
		System.out.println("A. "+ question.getAnswerA().getStatement());
		System.out.println("B. "+ question.getAnswerB().getStatement());
		System.out.println("C. "+ question.getAnswerC().getStatement());
		System.out.println("D. "+ question.getAnswerD().getStatement());
		System.out.println("");
		System.out.println("Escriba la letra de la opcion que considera correcta y presione Enter.");
		System.out.println("");
		System.out.println("Si desea utilizar su ayuda de esta ronda escriba * y presione Enter.");
	}
	
    /**
     * Valida la respuesta del jugador.
     * @param userAnswer La respuesta proporcionada por el jugador.
     * @param question La pregunta correspondiente.
     * @return true si la respuesta es correcta, false si es incorrecta.
     */
    private boolean validateAnswer(String userAnswer, Question question) {
        switch (userAnswer) {
            case "A":
                return question.getAnswerA().getIsCorrect();
            case "B":
                return question.getAnswerB().getIsCorrect();
            case "C":
                return question.getAnswerC().getIsCorrect();
            case "D":
                return question.getAnswerD().getIsCorrect();
            default:
                System.out.println("Opción inválida. Asegúrate de escribir A, B, C o D.");
                userAnswer = scanner.nextLine().trim().toUpperCase();
                return validateAnswer(userAnswer, question);
        }
    }

    /**
     * Inicializa al jugador con los valores iniciales.
     */
	public void initPlayer() {
		player.setNumberLives(3);
		player.setNumberPoints(0);
	}
	
    /**
     * Obtiene el puntaje actual del jugador.
     * @return El puntaje actual del jugador.
     */
	public int getPoints() {
		return player.getNumberPoints();
	}
	
    /**
     * Obtiene el número de vidas restantes del jugador.
     * @return El número de vidas del jugador.
     */
	public int getLives() {
		return player.getNumberLives();
	}
	
    /**
     * Reduce una vida al jugador.
     */
	public void loseLive() {
		player.setNumberLives(player.getNumberLives() - 1);
	}
	
}
