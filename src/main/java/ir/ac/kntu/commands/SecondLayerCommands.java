package ir.ac.kntu.commands;

import ir.ac.kntu.Course;
import ir.ac.kntu.Question;
import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.userAndHisChildren.User;
import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.dao.UserDAO;

public class SecondLayerCommands {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    private static CourseDAO courseDAO = new CourseDAO();

    private static UserDAO userDAO = new UserDAO();

    public static void viewQuestions(Course course) {
        System.out.println(ANSI_RED + "Questions of this class:\n" + ANSI_RESET);
        for (int i = 0; i < course.getAllQuestions().size(); i++) {
            System.out.print(ANSI_YELLOW + "question" + (i + 1) + ANSI_RESET + ":      ");
            System.out.println(course.getAllQuestions().get(i));
        }
    }

    public static void addQuestion(Course course, Question question) {
        course.addQuestion(question);
        courseDAO.updateCourse(course);
    }

    public static void addStudentInCourse(Course course, User user) {
        course.addStudent(user);
        user.addEnrolledCourses(course);
        courseDAO.updateCourse(course);
        userDAO.updateUser(user);
    }

    public static void addQuestionToACourse(Course course, Question question) {
        course.addQuestion(question);
        courseDAO.updateCourse(course);
    }

    public static void viewStudentsOfACourse(Course course) {
        for (int i = 0; i < course.getAllStudents().size(); i++) {
            System.out.println((i + 1) + ": " + course.getAllStudents().get(i));
        }
    }

    public static void viewAnswersOfAQuestions(Question question) {
        for (int i = 0; i < question.getStudentAnswers().size(); i++) {
            System.out.println((i + 1) + ": " + question.getStudentAnswers().get(i));
        }
    }

    public static void viewQuestionsOfAMatch(Match match) {
        System.out.println(ANSI_RED + "Questions of this match:" + ANSI_RESET);
        for (int i = 0; i < match.getQuestions().size(); i++) {
            System.out.print(ANSI_YELLOW + "question" + (i + 1) + ANSI_RESET + ":      ");
            System.out.println(match.getQuestions().get(i));
        }
    }

    public static void addQuestionToAMatch(Match match, Question question) {
        match.addQuestion(question);
        new MatchDAO().updateMatch(match);
    }

}
