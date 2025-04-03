package com.mycompany.app.classes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    static LocalConsole console = new LocalConsole();
    static Flashcard[] flashcards;
    private static final String DB_FILE = "db.txt";
    private static final Gson gson = new Gson();

    // Reading the flashcards from the file
    public void initDatabase() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(DB_FILE));
        StringBuilder jsonContent = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();

        // Parse the JSON content into a JsonArray using Gson
        JsonArray jsonArray = gson.fromJson(jsonContent.toString(), JsonArray.class);
        ArrayList<Flashcard> flashcardList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject flashcardJson = jsonArray.get(i).getAsJsonObject();
            String question = flashcardJson.get("question").getAsString();
            String answer = flashcardJson.get("answer").getAsString();

            flashcardList.add(new Flashcard(answer, question));
        }

        flashcards = new Flashcard[flashcardList.size()];
        flashcardList.toArray(flashcards);
    }

    // Update the database (db.txt) after modification
}