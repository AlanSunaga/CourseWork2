package com.example.courseJava.service;

import com.example.courseJava.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Service
public class JavaQuestionService implements QuestionService {
    //oн должен
    // хранить список вопросов и обеспечивать работу с ним.
    private final Set<Question> listQuestion = new HashSet<>();
    private final Random random = new Random();

    public JavaQuestionService() {
        listQuestion.add(new Question("Что такое Java?", "Язык программирования"));
        listQuestion.add(new Question("Что такое JVM?", "Виртуальная машина Java"));
        listQuestion.add(new Question("Что такое JDK?", "Комплект разработки Java"));
        listQuestion.add(new Question("Что такое JRE?", "Среда выполнения Java"));
        listQuestion.add(new Question("Что такое ООП?", "Объектно-ориентированное программирование"));
    }

    @Override
    public Question addQuestion(String question, String answer) {
        Question questions = new Question(question, answer);
        listQuestion.add(questions);
        return questions;
    }
    /// этот
    @Override
    public Question addQuestion(Question question) {
        listQuestion.add(question);
        return question;
    }

    @Override
    public Question removeQuestion(Question question) {
        if (listQuestion.remove(question)) {
            return question;
        }
        return null;
    }

    @Override
    public Collection<Question> getAll() {
        return Set.copyOf(listQuestion);
    }

    @Override
    public Question getRandomQuestion() {
        if (listQuestion.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }

        int randomIndex = random.nextInt(listQuestion.size());
        return listQuestion.stream()
                .skip(randomIndex)
                .findFirst()
                .orElseThrow();
    }
}
