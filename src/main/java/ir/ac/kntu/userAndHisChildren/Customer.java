package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Menus;
import ir.ac.kntu.ScannerWrapper;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.userInterface.Start;
import ir.ac.kntu.userInterface.UserLoggedIn;

public class Customer extends User{
    public Customer(String name, String userName, String email, String password, String nationalId, String mobileNumber) {
        super(name, userName, email, password, nationalId, mobileNumber);
    }

    public Customer(User a) {
        super(a);
    }

    @Override
    public void printMenu() {
        Menus.printCustomerMenu();
    }

    enum LoggedInUserOption {
        CREATE_A_COURSE, DELETE_A_COURSE, ENROLL_IN_A_CLASS, ENTER_A_CLASS, ENTER_A_MATCH, VIEW_QUESTIONBANK, ADD_QUESTION_TO_QUESTIONBANK,
        VIEW_MATCHES_AND_PARTICIPATE_IN, VIEW_MESSAGES_AND_ANSWER_THEM, LOG_OUT
    }

    @Override
    public void handleCommand() {
        UserLoggedIn userLoggedIn = new UserLoggedIn(this);
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        int userInput = scanner.nextInt();
        if (userInput > 10 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
        } else {
            LoggedInUserOption[] options = LoggedInUserOption.values();
            LoggedInUserOption option;
            option = options[userInput - 1];
            switch (option) {
                case CREATE_A_COURSE -> userLoggedIn.createCourse();
                case DELETE_A_COURSE -> userLoggedIn.deleteCourse();
                case ENROLL_IN_A_CLASS -> userLoggedIn.enrollInCourse();
                case ENTER_A_CLASS -> {
                    userLoggedIn.enterCourse();
                    return;
                }
                case ENTER_A_MATCH -> {
                    userLoggedIn.enterMatch();
                    return;
                }
                case VIEW_QUESTIONBANK -> FirstLayerCommands.viewQuestionBank();
                case ADD_QUESTION_TO_QUESTIONBANK -> userLoggedIn.createAndAddQuestionToQuestionBank();
                case VIEW_MATCHES_AND_PARTICIPATE_IN -> userLoggedIn.viewMatchesAndParticipateIn();
                case VIEW_MESSAGES_AND_ANSWER_THEM -> userLoggedIn.viewMessagesAndAnswerThem();
                default -> {
                    new Start().startProgram();
                    return;
                }
            }
        }
        printMenu();
        handleCommand();
    }
}
