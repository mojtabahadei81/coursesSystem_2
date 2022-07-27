package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Menus;
import ir.ac.kntu.ScannerWrapper;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.userInterface.Start;
import ir.ac.kntu.userInterface.UserLoggedIn;

public class Admin extends User {


    public Admin(String name, String userName, String email, String password, String nationalId, String mobileNumber) {
        super(name, userName, email, password, nationalId, mobileNumber);
    }

    public Admin(User a) {
        super(a);
    }

    @Override
    public void printMenu() {
        Menus.printAdminMenu();
    }

    enum LoggedInUserOption {
        CREATE_MATCH, CREATE_A_COURSE, VIEW_CLASSES_AND_EDIT_THEM, DELETE_A_COURSE, ENTER_A_MATCH, ENTER_A_CLASS, VIEW_MATCHES_AND_EDIT_THEM,
        DELETE_MATCH, VIEW_QUESTIONBANK_AND_EDIT_IT, ADD_QUESTION_TO_QUESTIONBANK, INVITE_CUSTOMERS_TO_PRIVATE_MATCH,
        VIEW_ALL_USERS, UPGRADE_A_USER_TO_ADMIN, EXIT
    }

    @Override
    public void handleCommand() {
        UserLoggedIn userLoggedIn = new UserLoggedIn(this);
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        int userInput = scanner.nextInt();
        if (userInput > 14 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
        } else {
            LoggedInUserOption[] options = LoggedInUserOption.values();
            LoggedInUserOption option;
            option = options[userInput - 1];
            switch (option) {
                case CREATE_MATCH -> FirstLayerCommands.createMatch(this);
                case CREATE_A_COURSE -> userLoggedIn.createCourse();
                case VIEW_CLASSES_AND_EDIT_THEM -> FirstLayerCommands.viewCourses();//TODO: edit
                case DELETE_A_COURSE -> userLoggedIn.deleteCourse();
                case ENTER_A_MATCH -> {
                    userLoggedIn.enterMatch();
                    return;
                }
                case ENTER_A_CLASS -> {
                    userLoggedIn.enterCourse();
                    return;
                }
                case VIEW_MATCHES_AND_EDIT_THEM -> FirstLayerCommands.viewAllMatches();//TODO: edit
                case DELETE_MATCH -> userLoggedIn.deleteMatch();
                case VIEW_QUESTIONBANK_AND_EDIT_IT -> FirstLayerCommands.viewQuestionBank();//TODO: edit
                case ADD_QUESTION_TO_QUESTIONBANK -> userLoggedIn.createAndAddQuestionToQuestionBank();
                case INVITE_CUSTOMERS_TO_PRIVATE_MATCH -> userLoggedIn.inviteCustomerToMatch();
                case VIEW_ALL_USERS -> FirstLayerCommands.viewUsers(this);
                case UPGRADE_A_USER_TO_ADMIN -> userLoggedIn.upgradeACustomerToAdmin();
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
