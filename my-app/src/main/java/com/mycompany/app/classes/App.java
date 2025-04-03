package classes;
import interfaces.FlashcardApp;
import interfaces.FlashcardData;

public class App {
    public static void main(String[] args) throws Exception {
        Commander commander = new Commander();
        commander.sayHello();
        commander.startApp();
    }
}

