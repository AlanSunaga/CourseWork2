package com.example.courseJava;

import com.example.courseJava.service.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class JavaQuestionServiceTest {
    private JavaQuestionService javaQuestionService;

    @BeforeEach
    void setUp() {
        javaQuestionService = new JavaQuestionService();
    }

    //    Тест добавления вопроса по тексту
    @Test
    void addQuestion_ByText_ShouldAddQuestion() {
        // Arrange
        String questionText = "Что такое Spring?";
        String answerText = "Фреймворк";

        // Act
        Question result = javaQuestionService.addQuestion(questionText, answerText);

        // Assert
        assertNotNull(result);
        assertEquals(questionText, result.getQuestion());
        assertEquals(answerText, result.getAnswer());

        // Проверяем, что вопрос действительно добавился
        Collection<Question> allQuestions = javaQuestionService.getAll();
        assertTrue(allQuestions.contains(result));
    }

    //    Тест добавления объекта Question
    @Test
    void addQuestion_ByQuestionObject_ShouldAddQuestion() {
        // Arrange
        Question newQuestion = new Question("Что такое Maven?", "Инструмент сборки");

        // Act
        Question result = javaQuestionService.addQuestion(newQuestion);

        // Assert
        assertNotNull(result);
        assertEquals(newQuestion, result);
        assertTrue(javaQuestionService.getAll().contains(newQuestion));
    }

    //    Тест удаления существующего вопроса
    @Test
    void removeQuestion_WhenQuestionExists_ShouldRemoveAndReturnQuestion() {
        // Arrange - берем один из вопросов, добавленных в конструкторе
        Question existingQuestion = new Question("Что такое Java?", "Язык программирования");

        // Act
        Question result = javaQuestionService.removeQuestion(existingQuestion);

        // Assert
        assertNotNull(result);
        assertEquals(existingQuestion, result);
        assertFalse(javaQuestionService.getAll().contains(existingQuestion));
    }

    //    Тест удаления несуществующего вопроса
    @Test
    void removeQuestion_WhenQuestionNotExists_ShouldReturnNull() {
        // Arrange
        Question nonExistingQuestion = new Question("Несуществующий вопрос", "Ответ");

        // Act
        Question result = javaQuestionService.removeQuestion(nonExistingQuestion);

        // Assert
        assertNull(result);
    }

    //    Тест получения всех вопросов
    @Test
    void getAll_ShouldReturnCopyOfQuestions() {
        // Act
        Collection<Question> result = javaQuestionService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size()); // 5 вопросов добавлено в конструкторе

        // Проверяем, что это копия (изменения не влияют на оригинал)
        int originalSize = result.size();
        javaQuestionService.addQuestion("Новый вопрос", "Ответ");

        Collection<Question> newResult = javaQuestionService.getAll();
        assertEquals(originalSize + 1, newResult.size());
    }

    //    Тест получения случайного вопроса
    @Test
    void getRandomQuestion_ShouldReturnRandomQuestion() {
        // Act
        Question result = javaQuestionService.getRandomQuestion();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getQuestion());
        assertNotNull(result.getAnswer());

        // Проверяем, что вопрос из начального списка
        Collection<Question> allQuestions = javaQuestionService.getAll();
        assertTrue(allQuestions.contains(result));
    }

    //    Тест получения случайного вопроса при пустом списке
    @Test
    void getRandomQuestion_WhenListEmpty_ShouldThrowException() {
        // Arrange - очищаем список
        JavaQuestionService emptyService = new JavaQuestionService();

//        Рефлексивное удаление
        Collection<Question> questions = emptyService.getAll();
        for (Question question : questions) {
            emptyService.removeQuestion(question);
        }

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            emptyService.getRandomQuestion();
        });
    }

    //    Тест уникальности вопросов
    @Test
    void addDuplicateQuestion_ShouldNotAddDuplicate() {
        // Arrange
        Question question1 = new Question("Один и тот же вопрос", "Ответ");
        Question question2 = new Question("Один и тот же вопрос", "Ответ");

        // Act
        javaQuestionService.addQuestion(question1);
        javaQuestionService.addQuestion(question2); // Дубликат

        // Assert - в Set дубликаты не добавляются
        Collection<Question> allQuestions = javaQuestionService.getAll();
        long count = allQuestions.stream()
                .filter(q -> q.getQuestion().equals("Один и тот же вопрос"))
                .count();
        assertEquals(1, count);
    }
}
