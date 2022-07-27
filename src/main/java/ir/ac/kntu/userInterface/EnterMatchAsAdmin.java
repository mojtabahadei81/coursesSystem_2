package ir.ac.kntu.userInterface;

import ir.ac.kntu.*;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.dao.QuestionDAO;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.userAndHisChildren.Student;
import ir.ac.kntu.userAndHisChildren.Teacher;
import ir.ac.kntu.userAndHisChildren.User;

import java.time.LocalDateTime;

public class EnterMatchAsAdmin {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    private final ScannerWrapper scanner = ScannerWrapper.getInstance();

    private User loggedUser;

    private Match enteredMatch;

    public EnterMatchAsAdmin(User loggedUser, Match enteredMatch){
        this.enteredMatch = enteredMatch;
        this.loggedUser = loggedUser;
    }

    public enum EnterInMatchMenu {VIEW_QUESTIONS, ADD_QUESTION_FROM_QUESTIONBANK, CREATE_AND_ADD_QUESTION, CORRECT_USER_ANSWERS, EXIT}

    public void enterMatchOptionHandler() {
        Menus.enterMatchAsAdminMenu();
        int userInput = scanner.nextInt();
        if (userInput < 1 || userInput > 5) {
            System.out.println("The number entered is incorrect.");
        } else {
            EnterInMatchMenu[] enterInMatchMenus = EnterInMatchMenu.values();
            EnterInMatchMenu option;
            option = enterInMatchMenus[userInput - 1];
            switch (option) {
                case VIEW_QUESTIONS -> SecondLayerCommands.viewQuestionsOfAMatch(enteredMatch);
                case ADD_QUESTION_FROM_QUESTIONBANK -> addQuestionFromQuestionBankToMatch();
                case CREATE_AND_ADD_QUESTION -> createAndAddQuestion();
                case CORRECT_USER_ANSWERS -> correctAnswersThatSentToQuestionsOfAMatch();
                default -> {
                    loggedUser.printMenu();
                    loggedUser.handleCommand();
                    return;
                }
            }
        }
        enterMatchOptionHandler();
    }

    public void createAndAddQuestion() {
        Question question = ReadDataFromKeyboard.questionRead();
        SecondLayerCommands.addQuestionToAMatch(enteredMatch, question);
    }

    public void addQuestionFromQuestionBankToMatch() {
        FirstLayerCommands.viewQuestionBank();
        while (true){
            System.out.println("do you want to choose one of them? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of question that you want to choose it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= new QuestionDAO().getAllQuestions().size()) {
                    Question question = new QuestionDAO().getAllQuestions().get(number - 1);
                    enteredMatch.addQuestion(new Question(question));
                    new MatchDAO().updateMatch(enteredMatch);
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
    }

    public void correctAnswersThatSentToQuestionsOfAMatch() {
        SecondLayerCommands.viewQuestionsOfAMatch(enteredMatch);
        while (true){
            System.out.println("Do you want to correct the answers sent to one of these questions? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of question that you want to correct it answers: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= enteredMatch.getQuestions().size()) {
                    correctAnswersOfAQuestion(enteredMatch.getQuestions().get(number - 1));
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
        new MatchDAO().updateMatch(enteredMatch);
    }

    private void correctAnswersOfAQuestion(Question question) {
        SecondLayerCommands.viewAnswersOfAQuestions(question);
        for (int i = 0; i < question.getStudentAnswers().size(); i++) {
            System.out.println(ANSI_YELLOW + "please score to question " + (i+1) + ": " + ANSI_RESET);
            try {
                double score = scanner.nextDouble();
                question.getStudentAnswers().get(i).setScore(score, LocalDateTime.MAX, 1);
            } catch (ArithmeticException e) {
                System.out.println("please enter a number not else.");
                System.out.println(e.getMessage());
                i--;
            }
        }
    }

}
