package ir.ac.kntu.userInterface;

import ir.ac.kntu.*;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.userAndHisChildren.User;

public class EnterMatchAsUser {

    private final ScannerWrapper scanner = ScannerWrapper.getInstance();

    private User loggedUser;

    private Match enteredMatch;

    public EnterMatchAsUser(User loggedUser, Match enteredMatch){
        this.enteredMatch = enteredMatch;
        this.loggedUser = loggedUser;
    }

    public enum EnterInMatchMenu {VIEW_QUESTIONS, ANSWER_TO_QUESTIONS, EXIT}

    public void enterMatchOptionHandler() {
        Menus.enterMatchAsCustomerMenu();
        int userInput = scanner.nextInt();
        if (userInput < 1 || userInput > 3) {
            System.out.println("The number entered is incorrect.");
        } else {
            EnterInMatchMenu[] enterInMatchMenus = EnterInMatchMenu.values();
            EnterInMatchMenu option;
            option = enterInMatchMenus[userInput - 1];
            switch (option) {
                case VIEW_QUESTIONS -> SecondLayerCommands.viewQuestionsOfAMatch(enteredMatch);
                case ANSWER_TO_QUESTIONS -> answerToQuestions();
                default -> {
                    loggedUser.printMenu();
                    loggedUser.handleCommand();
                    return;
                }
            }
        }
        enterMatchOptionHandler();
    }

    public void answerToQuestions() {
        SecondLayerCommands.viewQuestionsOfAMatch(enteredMatch);
        while (true){
            System.out.println("do you want to answer one of them? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of question that you want to answer it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= enteredMatch.getQuestions().size()) {
                    Question question = enteredMatch.getQuestions().get(number - 1);
                    Answer answer = ReadDataFromKeyboard.readAnswer(loggedUser);
                    question.sendAnswer(answer);
                    new MatchDAO().updateMatch(enteredMatch);
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
    }
}
