package com.mycompany.app.classes;

import java.util.ArrayList;
import java.util.Scanner;

public class LocalConsole {
    final private ArrayList<String> log = new ArrayList<>();
    final private Scanner scanner = new Scanner(System.in);

    public void say(String string, Object... args) {
        String message = String.format(string, args);
        say(message);
    }

    public void say(String message) {
        System.out.println(message);
        log.add(message);
    }

    public String readLine() {
        String input = scanner.nextLine();
        log.add(input);
        return input;
    }

    public ArrayList<String> getLog() {
        return log;
    }
}
