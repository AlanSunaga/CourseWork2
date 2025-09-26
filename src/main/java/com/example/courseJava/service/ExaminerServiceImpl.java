package com.example.courseJava.service;

import com.example.courseJava.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    public ExaminerServiceImpl() {
        questions();
    }

    public void questions() {
        questions.add(new Question("Какой твой любимый цвет?", "Синий"));
        questions.add(new Question("Какая твоя любимая кухня?", "Русская"));
        questions.add(new Question("Какой твой любимый напиток?", "Дюшес"));
        questions.add(new Question("Какая ваша самая рекомендуемая книга?", "Война и мир"));
        questions.add(new Question("Какая твоя самая страшная история?", "Жизнь"));
    }


    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > questions.size()) {
            throw new IllegalArgumentException("Запрашиваеме число (" + amount +
                    ") превышает доступное количество вопросов (" + questions.size() + ")");
        }

        Set<Question> result = new HashSet<>();
        Question[] questionArray = questions.toArray(new Question[0]);

        // Добавляем случайные вопросы пока не наберем нужное количество
        while (result.size() < amount) {
            int randomIndex = random.nextInt(questions.size());
            result.add(questionArray[randomIndex]);
        }
        return result;

    }
}
