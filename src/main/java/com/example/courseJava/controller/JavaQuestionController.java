package com.example.courseJava.controller;

import com.example.courseJava.Question;
import com.example.courseJava.service.JavaQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/java")
public class JavaQuestionController {
    //Он должен позволять пользователю добавлять,
    // просматривать и удалять вопросы по Java.
    private final JavaQuestionService javaQuestionService;

    public JavaQuestionController(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam String question,
                                @RequestParam String answer) {
        return javaQuestionService.addQuestion(question, answer);
    }

    @GetMapping("/all")
    public Collection<Question> getAllQuestion() {
        return javaQuestionService.getAll();
    }

    @GetMapping("/remove")
    public Question removeQuestion(@RequestParam String question,
                                   @RequestParam String answer) {
        Question questionToRemove = new Question(question, answer);
        return javaQuestionService.removeQuestion(questionToRemove);
    }

    @GetMapping("/random")
    public Question getQuestion() {
        return javaQuestionService.getRandomQuestion();
    }
}
