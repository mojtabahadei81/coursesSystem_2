package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Course;
import ir.ac.kntu.Menus;
import ir.ac.kntu.ScannerWrapper;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.userInterface.EnterCourse;

public class Student extends User{

    private User generalRole;

    private Course enteredCourse;

    public Student(String name, String userName, String email, String password, String nationalId, String mobileNumber, User generalRole, Course enteredCourse) {
        super(name, userName, email, password, nationalId, mobileNumber);
        this.generalRole = generalRole;
        this.enteredCourse = enteredCourse;
    }

    public Student(User a, Course enteredCourse) {
        super(a);
        generalRole = a;
        this.enteredCourse = enteredCourse;
    }

    public User getGeneralRole() {
        return generalRole;
    }

    @Override
    public void printMenu() {
        Menus.printStudentMenu();
    }

    enum EnterCourseOption {VIEW_QUESTIONS, ANSWER_TO_QUESTION, EXIT_FROM_THIS_COURSE}

    @Override
    public void handleCommand() {
        EnterCourse enterCourse = new EnterCourse(this, enteredCourse);
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        int userInput = scanner.nextInt();
        if (userInput > 3 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
        } else {
            EnterCourseOption[] options = EnterCourseOption.values();
            EnterCourseOption option;
            option = options[userInput - 1];
            switch (option) {
                case VIEW_QUESTIONS -> SecondLayerCommands.viewQuestions(enteredCourse);
                case ANSWER_TO_QUESTION -> enterCourse.answerToQuestions();
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

    @Override
    public void handleLevel() {
        super.handleLevel();
        generalRole.setScore(getScore());
        generalRole.handleLevel();
    }
}
