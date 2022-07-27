package ir.ac.kntu.commands;

import ir.ac.kntu.*;
import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.dao.QuestionDAO;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.matchandhischildren.PrivateMatch;
import ir.ac.kntu.userAndHisChildren.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FirstLayerCommands {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";

    private static CourseDAO courseDAO = new CourseDAO();

    private static UserDAO userDAO = new UserDAO();

    public static void createCourse(User owner) {
        Course course = ReadDataFromKeyboard.readCourse(owner);
        owner.addOwnerClass(course);

        new CourseDAO().updateCourse(course);
        new UserDAO().updateUser(owner);
    }

    public static void deleteCourse(User user, Course course) {
        if (isItPossibleToDeleteCourse(user, course)) {
            course.getCreator().deleteOwnerClass(course);
            courseDAO.deleteCourse(course);
            userDAO.updateUser(user);
        }
    }

    private static boolean isItPossibleToDeleteCourse(User user, Course course) {
        if (user instanceof Admin) {
            return true;
        } else if (user instanceof Guest) {
            return false;
        } else {
            return user.equals(course.getCreator());
        }
    }

    public static void viewQuestionBank() {
        QuestionDAO questionDAO = new QuestionDAO();
        for (int i = 0; i < questionDAO.getAllQuestions().size(); i++) {
            System.out.println((i+1) + ": " + questionDAO.getAllQuestions().get(i));
        }
    }

    public static void enrollInCourse(User user, Course course) {
        if (isItPossibleToEnrollInCourse(user, course)) {
            User student = new Student(user, course);
            course.addStudent(student);
            user.addEnrolledCourses(course);
            courseDAO.updateCourse(course);
            userDAO.updateUser(user);
        }
    }

    private static boolean isItPossibleToEnrollInCourse(User user, Course course) {
        if (user instanceof Admin || user instanceof Guest) {
            return false;
        } else {
            if (user.equals(course.getCreator())) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static void viewUsers(User user) {
        ArrayList<User> users = FirstLayerCommands.getAllUsersExcludeMeAndAdmins(user);
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ": " + users.get(i));
        }
    }

    public static void viewCourses() {
        for (int i = 0; i < courseDAO.getAllCourses().size(); i++) {
            System.out.println((i + 1) + ": " + courseDAO.getAllCourses().get(i));
        }
    }

    public static void addQuestionToQuestionBank(Question question) {
        new QuestionDAO().updateQuestions(question);
    }

    public static void viewExpiredMatches() {
        System.out.println(ANSI_RED + "expired matches: " + ANSI_RESET);
        for (Match m : new MatchDAO().getAllMatches()) {
            if (m.getEndTime().isBefore(LocalDateTime.now())) {
                System.out.println(m);
            }
        }
    }

    public static void viewMatchesNotPrivate() {
        ArrayList<Match> matches = new MatchDAO().getAllMatches();
        matches.removeIf(m -> m instanceof PrivateMatch);
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ":  " + matches.get(i));
        }
    }

    public static void viewAllMatches() {
        for (int i = 0; i < new MatchDAO().getAllMatches().size(); i++) {
            System.out.println((i + 1) + ":  " + new MatchDAO().getAllMatches().get(i));
        }
    }

    public static void participateInMatch(User user, Match match) {
        match.addParticipant(user);
        user.addMatch(match);
        new MatchDAO().updateMatch(match);
        new UserDAO().updateUser(user);
    }

    public static void createMatch(User creator) {
        Match match = ReadDataFromKeyboard.matchRead();
        participateInMatch(creator, match);
    }

    public static void deleteMatch(Match match) {
        for (User u : match.getParticipants()) {
            u.removeMatch(match);
            userDAO.updateUser(u);
        }
        new MatchDAO().deleteMatch(match);
    }

    public static void viewMessages(User user) {
        for (int i = 0; i < user.getMessages().size(); i++) {
            System.out.println((i + 1) + ": " + user.getMessages().get(i));
        }
    }

    public static void answerToMessage(Message message) {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("you want to accept this request? (yes|no)");
        if (scanner.next().equals("yes")) {
            message.accept();
            new MatchDAO().updateMatch((Match) message.getAcceptable());
        }
        message.getTargetUser().removeMessage(message);
        userDAO.updateUser(message.getTargetUser());
    }

    public static void sendMessage(Message message) {
        message.sendMessage();
        userDAO.updateUser(message.getTargetUser());
    }

    public static void showUserCourses(User user) {
        for (int i = 0; i < user.getAllCourses().size(); i++) {
            System.out.println((i + 1) + ": " + user.getAllCourses().get(i));
        }
    }

    public static void showUserMatches(User user) {
        for (int i = 0; i < user.getParticipatedMatches().size(); i++) {
            System.out.println((i + 1) + ": " + user.getParticipatedMatches().get(i));
        }
    }

    public static ArrayList<User> getAllUsersExcludeMeAndAdmins(User me) {
        ArrayList<User> users = new ArrayList<>();
        for (User u : new UserDAO().getAllUsers()) {
            if (!(u.equals(me) || u.getClass().equals(Admin.class))) {
                users.add(u);
            }
        }
        return users;
    }
}
