package classes;

import interfaces.FlashcardApp;
import java.io.IOException;

public class EditFlashcard implements FlashcardApp {
    static LocalConsole console = new LocalConsole();

    @Override
    public void startApp() throws IOException {
        console.say("Input the action ----------------");
        console.say("+ Add");
        console.say("Edit");
        console.say("Delete");
        console.say("< Back");
        LearnFlashcard learnFlashcard = new LearnFlashcard();

        String input = console.readLine().trim().toLowerCase();
        switch (input) {
            case "add":
                this.addFlashcard();
                break;
            case "edit":
                learnFlashcard.startApp();
                this.editFlashcard();
                break;
            case "delete":
                learnFlashcard.startApp();
                this.deleteFlashcard();
                break;
        }
    }

    public void addFlashcard() throws IOException {
        Flashcard[] flashcards = Database.flashcards;

        console.say("Question: ");
        String questionString = console.readLine();
        console.say("Answer: ");
        String answerString = console.readLine();

        Flashcard newFlashcard = new Flashcard(answerString, questionString);

        Flashcard[] newFlashcards = new Flashcard[flashcards.length + 1];

        for (int i = 0; i < flashcards.length; i++) {
            newFlashcards[i] = flashcards[i];
        }

        newFlashcards[newFlashcards.length - 1] = newFlashcard;
        this.updateDatabase(newFlashcards);
    }

    public void editFlashcard() throws IOException {
        Flashcard[] flashcards = Database.flashcards;

        console.say("Select the card to update");
        Integer index = Integer.parseInt(console.readLine()) - 1;

        console.say("Question: ");
        String questionString = console.readLine();
        console.say("Answer: ");
        String answerString = console.readLine();

        Flashcard newFlashcard = new Flashcard(answerString, questionString);
        flashcards[index] = newFlashcard;

        this.updateDatabase(flashcards);
    }

    public void deleteFlashcard() {
        Flashcard[] flashcards = Database.flashcards;
        Flashcard[] new_flashcards = new Flashcard[flashcards.length - 1];

        console.say("Select the card to delete");
        Integer index = Integer.parseInt(console.readLine()) - 1;

        for (int i = 0, k = 0; i < flashcards.length; i++) {
            if (i != index) {
                new_flashcards[k] = flashcards[i];
                k++;
            }
        }
        this.updateDatabase(new_flashcards);
    }

    private void updateDatabase(Flashcard[] flashcards) {
        Database database = new Database();
        try {
            database.updateDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
