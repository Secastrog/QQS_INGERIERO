package co.poli.edu.model;

import java.util.List;

public class QuestionList {

    // Lista de preguntas
    /**
     * Lista que almacena las preguntas de tipo Question.
     */
    private List<Question> questions;

    // Getter para questions
    /**
     * Obtiene la lista de preguntas.
     * @return La lista de preguntas.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    // Setter para questions
    /**
     * Establece la lista de preguntas.
     * @param questions La lista de preguntas a establecer.
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
