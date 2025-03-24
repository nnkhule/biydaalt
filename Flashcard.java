import java.io.*;
import java.util.*;

interface CardOrganizer {
    void organize(List<FlashCard> cards);
}

class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public void organize(List<FlashCard> cards) {
        cards.sort(Comparator.comparingInt(c -> -c.mistakes));
    }
}

class FlashCard {
    String question;
    String answer;
    int mistakes;
    int correctAnswers;
    int attempts;

    FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.mistakes = 0;
        this.correctAnswers = 0;
        this.attempts = 0;
    }
}

class FlashCardLoader {
    static List<FlashCard> loadCards(String filename) throws IOException {
        List<FlashCard> cards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2) {
                    cards.add(new FlashCard(parts[0], parts[1]));
                }
            }
        }
        return cards;
    }
}

class FlashCardQuiz {
    private List<FlashCard> cards;
    private Scanner scanner;
    private boolean invert;
    private long startTime;

    FlashCardQuiz(List<FlashCard> cards, boolean invert) {
        this.cards = cards;
        this.scanner = new Scanner(System.in);
        this.invert = invert;
    }

    void startQuiz(int repetitions) {
        startTime = System.currentTimeMillis();
        boolean allCorrect = true;
        for (int i = 0; i < repetitions; i++) {
            for (FlashCard card : cards) {
                System.out.println("Question: " + (invert ? card.answer : card.question));
                String userAnswer = scanner.nextLine().trim();
                card.attempts++;
                if (userAnswer.equalsIgnoreCase(invert ? card.question : card.answer)) {
                    System.out.println("Correct!");
                    card.correctAnswers++;
                } else {
                    System.out.println("Wrong! Correct answer: " + (invert ? card.question : card.answer));
                    card.mistakes++;
                    allCorrect = false;
                }
            }
        }
        checkAchievements(allCorrect);
    }

    void checkAchievements(boolean allCorrect) {
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        boolean fastCompletion = duration / cards.size() < 5;

        if (fastCompletion) {
            System.out.println("Achievement Unlocked: SPEEDSTER - Average response time under 5 seconds!");
        }
        if (allCorrect) {
            System.out.println("Achievement Unlocked: CORRECT - All answers correct in the last round!");
        }
        for (FlashCard card : cards) {
            if (card.attempts > 5) {
                System.out.println("Achievement Unlocked: REPEAT - Answered a card more than 5 times!");
                break;
            }
        }
        for (FlashCard card : cards) {
            if (card.correctAnswers >= 3) {
                System.out.println("Achievement Unlocked: CONFIDENT - Answered a card correctly at least 3 times!");
                break;
            }
        }
    }
}

public class FlashCardApp {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            System.out.println("Usage: flashcard <cards-file> [options]");
            System.out.println("--order <order>: random, worst-first, recent-mistakes-first");
            System.out.println("--repetitions <num>: number of times to repeat each card");
            System.out.println("--invertCards: swap question and answer");
            return;
        }

        String filename = args[0];
        String order = "random";
        int repetitions = 1;
        boolean invert = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order":
                    if (i + 1 < args.length) order = args[++i];
                    break;
                case "--repetitions":
                    if (i + 1 < args.length) repetitions = Integer.parseInt(args[++i]);
                    break;
                case "--invertCards":
                    invert = true;
                    break;
            }
        }

        try {
            List<FlashCard> cards = FlashCardLoader.loadCards(filename);
            CardOrganizer organizer = null;
            if ("worst-first".equals(order)) {
                cards.sort(Comparator.comparingInt(c -> -c.mistakes));
            } else if ("recent-mistakes-first".equals(order)) {
                organizer = new RecentMistakesFirstSorter();
                organizer.organize(cards);
            } else if ("random".equals(order)) {
                Collections.shuffle(cards);
            }

            FlashCardQuiz quiz = new FlashCardQuiz(cards, invert);
            quiz.startQuiz(repetitions);
        } catch (IOException e) {
            System.out.println("Error loading cards: " + e.getMessage());
        }
    }
}
