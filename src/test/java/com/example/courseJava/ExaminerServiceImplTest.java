package com.example.courseJava;

import com.example.courseJava.service.ExaminerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExaminerServiceImplTest {
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    void setUp() {
        examinerService = new ExaminerServiceImpl();
    }

    //Тест получения корректного количества вопросов
    @Test
    void getQuestions_WithValidAmount_ShouldReturnCorrectNumberOfQuestions() {
        // Arrange
        int requestedAmount = 3;

        // Act
        Collection<Question> result = examinerService.getQuestions(requestedAmount);

        // Assert
        assertNotNull(result);
        assertEquals(requestedAmount, result.size());

        // Проверяем, что все вопросы уникальны (Set гарантирует уникальность)
        assertEquals(requestedAmount, result.stream().distinct().count());
    }

    //Тест получения максимального количества вопросов
    @Test
    void getQuestions_WithMaxAmount_ShouldReturnAllQuestions() {
        // Arrange
        int totalQuestions = 5; // столько вопросов в конструкторе
        int requestedAmount = totalQuestions;

        // Act
        Collection<Question> result = examinerService.getQuestions(requestedAmount);

        // Assert
        assertNotNull(result);
        assertEquals(totalQuestions, result.size());
    }

    //    Тест получения одного вопроса
    @Test
    void getQuestions_WithAmountOne_ShouldReturnSingleQuestion() {
        // Act
        Collection<Question> result = examinerService.getQuestions(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        // Проверяем, что вопрос из начального списка
        Question question = result.iterator().next();
        assertNotNull(question.getQuestion());
        assertNotNull(question.getAnswer());
    }

    //    Тест исключения при нулевом количестве
    @Test
    void getQuestions_WithZeroAmount_ShouldThrowException() {
        // Arrange
        int invalidAmount = 0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(invalidAmount);
        });

        assertEquals("Amount must be positive", exception.getMessage());
    }

    //    Тест исключения при отрицательном количестве
    @Test
    void getQuestions_WithNegativeAmount_ShouldThrowException() {
        // Arrange
        int invalidAmount = -5;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(invalidAmount);
        });

        assertEquals("Amount must be positive", exception.getMessage());
    }

    //    Тест исключения при превышении доступного количества
    @Test
    void getQuestions_WithAmountExceedingTotal_ShouldThrowException() {
        // Arrange
        int totalQuestions = 5;
        int excessiveAmount = totalQuestions + 1;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(excessiveAmount);
        });

        assertTrue(exception.getMessage().contains("Запрашиваеме число"));
        assertTrue(exception.getMessage().contains("превышает доступное количество"));
    }

    //    Тест случайности возвращаемых вопросов
    @Test
    void getQuestions_ShouldReturnRandomQuestions() {
        // Arrange
        int requestedAmount = 3;

        // Act - делаем несколько вызовов
        Collection<Question> firstCall = examinerService.getQuestions(requestedAmount);
        Collection<Question> secondCall = examinerService.getQuestions(requestedAmount);

        // Assert - с большой вероятностью наборы будут разные
        // (но теоретически могут совпасть, поэтому проверяем структуру)
        assertEquals(requestedAmount, firstCall.size());
        assertEquals(requestedAmount, secondCall.size());

        // Проверяем, что все вопросы валидны
        assertTrue(firstCall.stream().allMatch(q ->
                q.getQuestion() != null && q.getAnswer() != null));
        assertTrue(secondCall.stream().allMatch(q ->
                q.getQuestion() != null && q.getAnswer() != null));
    }

    //    Тест уникальности возвращаемых вопросов
    @Test
    void getQuestions_ShouldReturnUniqueQuestions() {
        // Arrange
        int requestedAmount = 3;

        // Act
        Collection<Question> result = examinerService.getQuestions(requestedAmount);

        // Assert - в результате не должно быть дубликатов
        assertEquals(requestedAmount, result.size());
        assertEquals(requestedAmount, result.stream().distinct().count());

        // Проверяем, что вопросы из начального набора
        Set<Question> allQuestions = Set.of(
                new Question("Какой твой любимый цвет?", "Синий"),
                new Question("Какая твоя любимая кухня?", "Русская"),
                new Question("Какой твой любимый напиток?", "Дюшес"),
                new Question("Какая ваша самая рекомендуемая книга?", "Война и мир"),
                new Question("Какая твоя самая страшная история?", "Жизнь")
        );

        assertTrue(allQuestions.containsAll(result));
    }

    //    Тест граничного случая - все вопросы
    @Test
    void getQuestions_WithAllQuestions_ShouldReturnCompleteSet() {
        // Arrange
        int totalQuestions = 5;

        // Act
        Collection<Question> result = examinerService.getQuestions(totalQuestions);

        // Assert
        assertEquals(totalQuestions, result.size());

        // Создаем ожидаемый набор вопросов
        Set<Question> expectedQuestions = Set.of(
                new Question("Какой твой любимый цвет?", "Синий"),
                new Question("Какая твоя любимая кухня?", "Русская"),
                new Question("Какой твой любимый напиток?", "Дюшес"),
                new Question("Какая ваша самая рекомендуемая книга?", "Война и мир"),
                new Question("Какая твоя самая страшная история?", "Жизнь")
        );

        assertEquals(expectedQuestions, Set.copyOf(result));
    }
}
