package com.mycompany.app.classes;

import interfaces.FlashcardApp;

public class LearnFlashcard implements FlashcardApp {
    static LocalConsole console = new LocalConsole();

    @Override
    public void startApp() {
        Flashcard[] flashcards = Database.flashcards;
        console.say("\nLearn (Question | Answer) ----------------");
        for (int i = 0; i < flashcards.length; i++) {
            String questionString = flashcards[i].getQuestion();
            String answerString = flashcards[i].getAnswer();
            console.say("[%s]: %s | %s", Integer.toString(i + 1), questionString, answerString);
        }
    }
}
