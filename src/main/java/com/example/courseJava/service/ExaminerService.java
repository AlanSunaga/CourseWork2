package com.example.courseJava.service;

import com.example.courseJava.Question;

import java.util.Collection;


public interface ExaminerService {
    Collection<Question> getQuestions(int amount);

}
