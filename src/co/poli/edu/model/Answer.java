package co.poli.edu.model;

public class Answer {
    // Atributos privados
    /**
     * Representa el enunciado de la respuesta.
     */
    private String statement;

    /**
     * Indica si la respuesta es correcta o no.
     */
    private boolean isCorrect;

    // Constructor vacío
    /**
     * Constructor por defecto para la clase Answer.
     */
    public Answer() {
    }

    // Constructor con parámetros
    /**
     * Constructor con parámetros para inicializar la respuesta.
     * @param statement El enunciado de la respuesta.
     * @param isCorrect Indica si la respuesta es correcta.
     */
    public Answer(String statement, boolean isCorrect) {
        this.statement = statement;
        this.isCorrect = isCorrect;
    }

    // Getter para statement
    /**
     * Obtiene el enunciado de la respuesta.
     * @return El enunciado de la respuesta.
     */
    public String getStatement() {
        return statement;
    }

    // Setter para statement
    /**
     * Establece el enunciado de la respuesta.
     * @param statement El enunciado a establecer.
     */
    public void setStatement(String statement) {
        this.statement = statement;
    }

    // Getter para isCorrect
    /**
     * Obtiene si la respuesta es correcta.
     * @return true si la respuesta es correcta, false si es incorrecta.
     */
    public boolean getIsCorrect() {
        return isCorrect;
    }

    // Setter para isCorrect
    /**
     * Establece si la respuesta es correcta o no.
     * @param isCorrect true si la respuesta es correcta, false si no lo es.
     */
    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
