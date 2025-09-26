package com.example.courseJava.service;

import com.example.courseJava.Question;

import java.util.Collection;

public interface QuestionService {
    //В нем должны быть
    // методы работы с вопросами определенного предмета.
    //добавление вопроса
    Question addQuestion(String question, String answer);

    Question addQuestion(Question question);

    //удаление вопроса
    Question removeQuestion(Question question);

    //поиска вопроса
    Collection<Question> getAll();

    Question getRandomQuestion();


}
