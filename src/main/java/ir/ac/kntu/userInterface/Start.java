package ir.ac.kntu.userInterface;

import ir.ac.kntu.*;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.userAndHisChildren.Guest;
import ir.ac.kntu.userAndHisChildren.User;

public class Start {
    private final ScannerWrapper scanner = ScannerWrapper.getInstance();

    private User loggedUser = null;

    private final UserDAO userDAO = new UserDAO();

    public enum StartMenu {LOGIN, SIGNUP, CONTINUE_AS_GUEST, EXIT}

    public void startProgram() {
        Menus.printStartMenu();
        int userInput = scanner.nextInt();
        if (userInput < 1 || userInput > 4) {
            System.out.println("The number entered is incorrect.");
            startProgram();
        } else {
            StartMenu[] startMenus = StartMenu.values();
            StartMenu option;
            option = startMenus[userInput - 1];
            switch (option) {
                case LOGIN -> login();
                case SIGNUP -> signup();
                case CONTINUE_AS_GUEST -> continueAsGuest();
                default -> {}
            }
        }
    }

    public void login() {
        System.out.println("please enter your username and password");
        System.out.print("username: ");
        String username = scanner.next();
        System.out.print("password: ");
        String password = scanner.next();
        for (User s : userDAO.getAllUsers()) {
            if (username.equals(s.getUserName()) && password.equals(s.getPassword())) {
                loggedUser = s;
                loggedUser.printMenu();
                loggedUser.handleCommand();
                return;
            }
        }
        if (loggedUser == null) {
            System.out.println("username or password that you entered is incorrect.");
            startProgram();
        }
    }

    public void signup() {
        User user = ReadDataFromKeyboard.readUser();
        userDAO.updateUser(user);
        System.out.println(user);
        startProgram();
    }

    public void continueAsGuest() {
        loggedUser = new Guest();
        loggedUser.printMenu();
        loggedUser.handleCommand();
    }
}
