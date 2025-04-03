package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    static LocalConsole console = new LocalConsole();
    static Flashcard[] flashcards;
    private static final String DB_FILE = "db.txt";

    // Файлаас өгөгдлийг унших
    public void initDatabase() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(DB_FILE));
        String line;
        ArrayList<Flashcard> flashcardList = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length == 2) {
                String question = parts[0].trim();
                String answer = parts[1].trim();
                flashcardList.add(new Flashcard(answer, question));
            }
        }
        reader.close();

        flashcards = new Flashcard[flashcardList.size()];
        flashcardList.toArray(flashcards);
    }

    // Өгөгдлийг файлд шинэчлэх
    public void updateDatabase() throws IOException {
        FileWriter writer = new FileWriter(DB_FILE);
        for (Flashcard flashcard : flashcards) {
            writer.write(flashcard.getQuestion() + ";" + flashcard.getAnswer() + "\n");
        }
        writer.close();
        console.say("** Success **");
    }
}
