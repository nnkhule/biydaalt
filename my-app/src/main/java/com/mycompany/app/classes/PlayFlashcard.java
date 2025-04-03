package classes;

import interfaces.FlashcardApp;
import java.io.IOException;
import java.util.Random;

public class PlayFlashcard implements FlashcardApp {
    static LocalConsole console = new LocalConsole();

    @Override
    public void startApp() throws IOException {
        console.say("How many questions to answer?");
        Integer questionNumber = Integer.parseInt(console.readLine().trim().toLowerCase());
        console.say("Shuffle? [y/n]");
        String option = console.readLine().trim().toLowerCase();
        boolean confirmShuffle = option.equals("y") ? true : false;
        console.say("** Good Luck, If you want to stop the game, input 'back' or 'b' keyword! **");
        this.play(confirmShuffle, questionNumber);
    }

    private void play(Boolean shuffle, Integer questionNumber) throws IOException {
        Flashcard[] flashcards = Database.flashcards;

        Random generator = new Random();
        Integer correct = 0;

        for (int i = 0; i < questionNumber; i++) {
            if (i >= flashcards.length) {
                break;
            }
            String sequenceString = Integer.toString(i + 1);
            String questionString = "";
            String answerString = "";
            if (shuffle) {
                Integer randIndex = generator.nextInt(flashcards.length - i);
                answerString = flashcards[randIndex].getAnswer();
                questionString = flashcards[randIndex].getQuestion();
            } else {
                questionString = flashcards[i].getQuestion();
                answerString = flashcards[i].getAnswer();
            }

            console.say("Card #" + sequenceString + "----------------");
            console.say(questionString);

            String question = console.readLine();
            if (question.equals("back") || question.equals("b")) {
                break;
            }
            if (question.toLowerCase().equals(answerString)) {
                console.say("** Correct! **\n");
                correct += 1;
            } else {
                console.say("__ Wrong! The right answer is %s __", answerString);
            }
        }
        console.say("Total: %s / %s ----------------", questionNumber, correct);
    }
}
