package com.mycompany.app.classes;
import java.io.IOException;
import java.util.ArrayList;


public class Commander implements FlashcardApp {
    private String state;
    final private static LocalConsole console = new LocalConsole();

    public Commander(String state) {
        this.state = state;
    }

    public Commander() {
        this.state = "idle";
    }

    public void sayHello() {
        console.say("\n** Welcome! **\n");
    }

    public void startApp() throws IOException {
        Database database = new Database();
        database.initDatabase();

        ArrayList<FlashcardApp> apps = new ArrayList<>();
        apps.add(new PlayFlashcard());
        apps.add(new EditFlashcard());
        apps.add(new LearnFlashcard());

        while (true) {
            console.say("Input the action ----------------");
            console.say("Play");
            console.say("Manage");
            console.say("Learn");
            console.say("< Exit");

            String input = console.readLine().trim().toLowerCase();
            switch (input) {
                case "play":
                    apps.get(0).startApp();
                    break;
                case "manage":
                    apps.get(1).startApp();
                    break;
                case "learn":
                    apps.get(2).startApp();
                    break;
                case "exit":
                    return;
                default:
                    console.say("Please, Select the options below");
                    break;
            }
        }
    }
}
