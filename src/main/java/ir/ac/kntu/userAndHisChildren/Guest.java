package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Menus;
import ir.ac.kntu.ScannerWrapper;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.userInterface.Start;

public class Guest extends User{

    private static int numberOfGuests = 0;

    public Guest() {
        super("guest", "guest_" + numberOfGuests, "0", "0", "0","0");
        numberOfGuests++;
    }

    public Guest(User a) {
        super(a);
    }

    @Override
    public void printMenu() {
        Menus.printGuestMenu();
    }

    enum LoggedInUserOption {VIEW_QUESTIONBANK, VIEW_EXPIRED_MATCHES, SIGNUP, Exit}

    @Override
    public void handleCommand() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        int userInput = scanner.nextInt();
        if (userInput > 4 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
        } else {
            LoggedInUserOption[] options = LoggedInUserOption.values();
            LoggedInUserOption option;
            option = options[userInput - 1];
            switch (option) {
                case VIEW_QUESTIONBANK -> FirstLayerCommands.viewQuestionBank();
                case VIEW_EXPIRED_MATCHES -> FirstLayerCommands.viewExpiredMatches();
                case SIGNUP -> {
                    new Start().signup();
                    return;
                }
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
