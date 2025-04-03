package com.mycompany.app.classes;

import interfaces.FlashcardData;

public class Flashcard implements FlashcardData {
    private String answer;
    private String question;

    public Flashcard(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    @Override
    public String getQuestion() {
        return this.question;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }
}
