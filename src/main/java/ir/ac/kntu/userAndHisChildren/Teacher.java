package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Course;
import ir.ac.kntu.Menus;
import ir.ac.kntu.ScannerWrapper;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.userInterface.EnterCourse;

public class Teacher extends User {

    private User generalRole;

    private Course enteredCourse;

    public Teacher(String name, String userName, String email, String password, String nationalId, String mobileNumber, User generalRole, Course enteredCourse) {
        super(name, userName, email, password, nationalId, mobileNumber);
        this.generalRole = generalRole;
        this.enteredCourse = enteredCourse;
    }

    public Teacher(User a, Course enteredCourse) {
        super(a);
        generalRole = a;
        this.enteredCourse = enteredCourse;
    }

    @Override
    public void printMenu() {
        Menus.printTeacherMenu();
    }

    enum LoggedInUserOption {ADD_STUDENT_IN_CLASS_WITH_EMAIL, VIEW_QUESTIONS, CREATE_AND_ADD_QUESTION, ADD_QUESTION_FROM_QUESTIONBANK, CORRECT_STUDENT_ANSWERS, EXIT}

    @Override
    public void handleCommand() {
        EnterCourse enterCourse = new EnterCourse(this, enteredCourse);
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        int userInput = scanner.nextInt();
        if (userInput > 4 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
        } else {
            LoggedInUserOption[] options = LoggedInUserOption.values();
            LoggedInUserOption option;
            option = options[userInput - 1];
            switch (option) {
                case ADD_STUDENT_IN_CLASS_WITH_EMAIL -> enterCourse.addStudentInClassWithEmail();
                case VIEW_QUESTIONS -> SecondLayerCommands.viewQuestions(enteredCourse);
                case CREATE_AND_ADD_QUESTION -> enterCourse.createAndAddQuestion();
                case ADD_QUESTION_FROM_QUESTIONBANK -> enterCourse.addQuestionFromQuestionBankToCourse();
                case CORRECT_STUDENT_ANSWERS -> enterCourse.correctAnswersThatSentToQuestionsOfAClass();
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
