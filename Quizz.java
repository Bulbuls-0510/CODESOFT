import java.util.*;
import java.util.concurrent.*;

class QuizApp {
    private static final int TIME_LIMIT = 10; // seconds per question
    private List<Question> questions;
    private int score;
    private int totalQuestions;
    private Scanner scanner;

    public QuizApp() {
        this.questions = new ArrayList<>();
        this.score = 0;
        this.totalQuestions = 0;
        this.scanner = new Scanner(System.in);
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("What is the time complexity of accessing an element in an array?", 
                                   new String[]{"1. O(1)", "2. O(n)", "3. O(log n)", "4. O(n^2)"}, 1));
        questions.add(new Question("Which data structure is used in Breadth First Search of a graph?", 
                                   new String[]{"1. Stack", "2. Queue", "3. Array", "4. Linked List"}, 2));
        questions.add(new Question("What is the worst-case time complexity of QuickSort?", 
                                   new String[]{"1. O(n log n)", "2. O(n^2)", "3. O(n)", "4. O(log n)"}, 2));
        questions.add(new Question("Which data structure is used to implement recursion?", 
                                   new String[]{"1. Queue", "2. Stack", "3. Array", "4. Linked List"}, 2));
        questions.add(new Question("What is the maximum number of children a node can have in a binary tree?", 
                                   new String[]{"1. 1", "2. 2", "3. 3", "4. 4"}, 2));
        questions.add(new Question("Which data structure is used to convert infix to postfix notation?", 
                                   new String[]{"1. Stack", "2. Queue", "3. Tree", "4. Graph"}, 1));
        questions.add(new Question("What is the time complexity of inserting an element into a binary search tree?", 
                                   new String[]{"1. O(n)", "2. O(log n)", "3. O(n log n)", "4. O(1)"}, 2));
        questions.add(new Question("Which traversal algorithm is used to traverse a tree in depth-first order?", 
                                   new String[]{"1. BFS", "2. DFS", "3. Insertion", "4. Deletion"}, 2));
        questions.add(new Question("What is the space complexity of Merge Sort?", 
                                   new String[]{"1. O(1)", "2. O(n)", "3. O(log n)", "4. O(n log n)"}, 2));
        questions.add(new Question("Which data structure is used to store a set of disjoint sets?", 
                                   new String[]{"1. Disjoint-set", "2. Queue", "3. Stack", "4. Array"}, 1));
        totalQuestions = questions.size();
    }

    public void start() {
        printWelcomeMessage();

        for (Question question : questions) {
            boolean answered = false;
            while (!answered) {
                answered = askQuestion(question);
            }
        }

        displayResults();
    }

    private boolean askQuestion(Question question) {
        System.out.println("\n" + question.getText());
        for (String option : question.getOptions()) {
            System.out.println(option);
        }
        System.out.print("Your answer: ");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next(); // clear invalid input
                return -1; // indicate invalid input
            }
        });

        final boolean[] timeUp = {false};

        Thread timerThread = new Thread(() -> {
            for (int i = TIME_LIMIT; i > 0; i--) {
                System.out.print("\rTime left: " + i + " seconds");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.print("\rTime's up!                  \n");
            timeUp[0] = true;
            future.cancel(true); // cancel the input if time is up
        });

        timerThread.start();

        try {
            int answer = future.get(TIME_LIMIT, TimeUnit.SECONDS);
            timerThread.interrupt(); // Stop the timer if the user answers in time
            if (answer == question.getCorrectAnswer()) {
                System.out.println("Correct!");
                score++;
                return true;
            } else if (answer != -1) {
                System.out.println("Incorrect!");
                question.setUserAnswer(answer); // Record user's incorrect answer
                return true;
            } else {
                System.out.println("Invalid input. Please try again.");
                return false;
            }
        } catch (TimeoutException e) {
            if (timeUp[0]) {
                System.out.println("Time's up!");
                return true;
            } else {
                System.out.println("Invalid input. Please try again.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            return false;
        } finally {
            executor.shutdownNow();
        }
    }

    private void displayResults() {
        printResultsHeader();
        System.out.println("Your score: " + score + " out of " + totalQuestions);

        // Display a decorative summary of incorrect answers
        System.out.println("\nSummary of Incorrect Answers:");
        for (Question question : questions) {
            if (question.getUserAnswer() != 0 && question.getUserAnswer() != question.getCorrectAnswer()) {
                System.out.println(question.getText());
                System.out.println("Your answer: " + question.getOptions()[question.getUserAnswer() - 1]);
                System.out.println("Correct answer: " + question.getOptions()[question.getCorrectAnswer() - 1]);
                System.out.println("--------------------------------");
            }
        }

        printTryAgainMessage();
    }


   
    private void printWelcomeMessage() {
        String ascii ="""
        
        
 ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
 ██░▄▄░█░██░██▄██▄▄░███░▄▄▀██░▄▄░██░▄▄░██░████▄░▄██░▄▄▀█░▄▄▀█▄▄░▄▄█▄░▄██░▄▄▄░██░▀██░
 ██░██░█░██░██░▄█▀▄████░▀▀░██░▀▀░██░▀▀░██░█████░███░████░▀▀░███░████░███░███░██░█░█░
 ██▄▄░▀██▄▄▄█▄▄▄█▄▄▄███░██░██░█████░█████░▀▀░█▀░▀██░▀▀▄█░██░███░███▀░▀██░▀▀▀░██░██▄░
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
    
        
        
        
        
        """;
        System.out.println(ascii);
 
     }
 
     private void printResultsHeader() {
        
         System.out.println("=========================================");
         System.out.println("                Results                  ");
         System.out.println("=========================================");
     }
 
     private void printTryAgainMessage() {
         String ascii = """
                 
 
 ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
 █▄▄░▄▄██░▄▄▀██░███░███░▄▄▀██░▄▄░█░▄▄▀█▄░▄██░▀██░
 ███░████░▀▀▄██▄▀▀▀▄███░▀▀░██░█▀▀█░▀▀░██░███░█░█░
 ███░████░██░████░█████░██░██░▀▀▄█░██░█▀░▀██░██▄░
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
 

 
 
 
                 """;
                 System.out.println(ascii);
     }
    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.start();
    }
}

class Question {
    private String text;
    private String[] options;
    private int correctAnswer;
    private int userAnswer;

    public Question(String text, String[] options, int correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.userAnswer = 0;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }
}
