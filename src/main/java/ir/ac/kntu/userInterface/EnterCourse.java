package ir.ac.kntu.userInterface;

import ir.ac.kntu.*;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.dao.QuestionDAO;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.userAndHisChildren.Student;
import ir.ac.kntu.userAndHisChildren.Teacher;
import ir.ac.kntu.userAndHisChildren.User;

import java.time.LocalDateTime;

public class EnterCourse {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    private ScannerWrapper scanner = ScannerWrapper.getInstance();

    private User loggedUser;

    private Course enteredCourse;

    public EnterCourse(User loggedUser, Course enteredCourse){
        this.enteredCourse = enteredCourse;
        this.loggedUser = loggedUser;
    }

    public void answerToQuestions() {
        SecondLayerCommands.viewQuestions(enteredCourse);
        while (true){
            System.out.println("do you want to answer one of them? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of question that you want to answer it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= loggedUser.getMessages().size()) {
                    Question question = enteredCourse.getAllQuestions().get(number - 1);
                    Answer answer = ReadDataFromKeyboard.readAnswer(loggedUser);
                    question.sendAnswer(answer);
                    new CourseDAO().updateCourse(enteredCourse);
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
    }

    public void createAndAddQuestion() {
        Question question = ReadDataFromKeyboard.questionRead();
        SecondLayerCommands.addQuestionToACourse(enteredCourse, question);
    }

    public void addQuestionFromQuestionBankToCourse() {
        FirstLayerCommands.viewQuestionBank();
        while (true){
            System.out.println("do you want to choose one of them? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of question that you want to choose it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= new QuestionDAO().getAllQuestions().size()) {
                    Question question = new QuestionDAO().getAllQuestions().get(number - 1);
                    enteredCourse.addQuestion(new Question(question));
                    new CourseDAO().updateCourse(enteredCourse);
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
    }

    public void upgradeAStudentToATeacher() {
        SecondLayerCommands.viewStudentsOfACourse(enteredCourse);
        while (true){
            System.out.println("Do you want to choose one of the participants as a teacher? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of participant that you want to choose it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= enteredCourse.getAllStudents().size()) {
                    Student participant = (Student) enteredCourse.getAllStudents().get(number - 1);
                    User teacher = new Teacher(participant.getGeneralRole(), enteredCourse);
                    enteredCourse.removeStudent(participant);
                    enteredCourse.setTeacher(teacher);
                    new CourseDAO().updateCourse(enteredCourse);
                    break;
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
    }

    public void addStudentInClassWithEmail() {
        System.out.print("please enter user email for add as student: ");
        String email = scanner.next();
        for (User u : new UserDAO().getAllUsers()) {
            if (email.equals(u.getEmail())) {
                enteredCourse.addStudent(new Student(u, enteredCourse));
                new CourseDAO().updateCourse(enteredCourse);
            }
        }
    }

    public void correctAnswersThatSentToQuestionsOfAClass() {
        SecondLayerCommands.viewQuestions(enteredCourse);
        while (true){
            System.out.println("Do you want to correct the answers sent to one of these questions? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of question that you want to correct it answers: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= enteredCourse.getAllQuestions().size()) {
                    correctAnswersOfAQuestion(enteredCourse.getAllQuestions().get(number - 1));
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
        new CourseDAO().updateCourse(enteredCourse);
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
