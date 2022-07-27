package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Course;
import ir.ac.kntu.Menus;
import ir.ac.kntu.ScannerWrapper;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.userInterface.EnterCourse;

public class TeacherAssistant extends User{

    private User generalRole;

    private Course enteredCourse;

    public TeacherAssistant(String name, String userName, String email, String password, String nationalId, String mobileNumber, User generalRole, Course enteredCourse) {
        super(name, userName, email, password, nationalId, mobileNumber);
        this.generalRole = generalRole;
        this.enteredCourse = enteredCourse;
    }

    public TeacherAssistant(User a, Course enteredCourse) {
        super(a);
        generalRole = a;
        this.enteredCourse = enteredCourse;
    }

    @Override
    public void printMenu() {
        Menus.printTeacherAssistantMenu();
    }

    private enum LoggedInUserOption {CREATE_AND_ADD_QUESTION, ADD_QUESTION_FROM_QUESTIONBANK, VIEW_QUESTIONS, UPGRADE_A_USER_TO_A_TEACHER, EXIT}

    @Override
    public void handleCommand() {
        EnterCourse enterCourse = new EnterCourse(this, enteredCourse);
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        int userInput = scanner.nextInt();
        if (userInput > 5 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
        } else {
            LoggedInUserOption[] options = LoggedInUserOption.values();
            LoggedInUserOption option;
            option = options[userInput - 1];
            switch (option) {
                case CREATE_AND_ADD_QUESTION -> enterCourse.createAndAddQuestion();
                case ADD_QUESTION_FROM_QUESTIONBANK -> enterCourse.addQuestionFromQuestionBankToCourse();
                case VIEW_QUESTIONS -> SecondLayerCommands.viewQuestions(enteredCourse);
                case UPGRADE_A_USER_TO_A_TEACHER -> enterCourse.upgradeAStudentToATeacher();
                default -> {
                    generalRole.printMenu();
                    generalRole.handleCommand();
                    return;
                }
            }
        }
        printMenu();
        handleCommand();
    }
}
