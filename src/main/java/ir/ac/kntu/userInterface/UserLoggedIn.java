package ir.ac.kntu.userInterface;

import ir.ac.kntu.*;
import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.commands.SecondLayerCommands;
import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.matchandhischildren.PrivateMatch;
import ir.ac.kntu.userAndHisChildren.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserLoggedIn {

    private User loggedUser;

    private final ScannerWrapper scanner = ScannerWrapper.getInstance();

    public UserLoggedIn(User user) {
        loggedUser = user;
    }

    public void createCourse() {
        FirstLayerCommands.createCourse(loggedUser);
    }

    public void deleteCourse() {
        System.out.println("please enter (class name) and (institute name) and (academic year) to delete class.");
        System.out.print("class name: ");
        String className = scanner.next();
        System.out.print("institute name: ");
        String instituteName = scanner.next();
        System.out.print("academic year: ");
        String academicYear = scanner.next();
        Course courseToBeDelete = new CourseDAO().getCourseWithUsingProfile(className, instituteName, academicYear);
        if (courseToBeDelete != null) {
            FirstLayerCommands.deleteCourse(loggedUser, courseToBeDelete);
        }
    }

    public void enrollInCourse() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("please enter (class name) and (institute name) and (academic year) to enroll in class.");
        System.out.print("class name: ");
        String className = scanner.next();
        System.out.print("institute name: ");
        String instituteName = scanner.next();
        System.out.print("academic year: ");
        String academicYear = scanner.next();
        Course course = new CourseDAO().getCourseWithUsingProfile(className, instituteName, academicYear);
        if (course == null) {
            return;
        }
        boolean passwordIsCorrect = true;
        if (course.getCourseState().equals(Course.CourseState.OPEN_PRIVATE)) {
            System.out.print("please enter password: ");
            if (!course.isCorrectPassword(scanner.next())) {
                passwordIsCorrect = false;
            }
        }
        if (passwordIsCorrect) {
            FirstLayerCommands.enrollInCourse(loggedUser, course);
        }
    }

    public void createAndAddQuestionToQuestionBank() {
        do {
            Question question = ReadDataFromKeyboard.questionRead();
            if (question != null) {
                FirstLayerCommands.addQuestionToQuestionBank(question);
            }
            System.out.println("want to create another question? (yes|no)");
        } while (scanner.next().equals("yes"));
    }

    public void viewMatchesAndParticipateIn() {
        FirstLayerCommands.viewMatchesNotPrivate();
        ArrayList<Match> matches = new MatchDAO().getAllMatches();
        matches.removeIf(m -> m instanceof PrivateMatch);
        System.out.println("do you want to participate in one of them? (yes|no)");
        if (scanner.next().equals("yes")) {
            System.out.print("Please enter the match number you want to participate: ");
            int matchNumber = scanner.nextInt();
            if (matchNumber < 1 || matchNumber > matches.size()) {
                System.out.println("a match with this number not exist.");
            } else {
                Match match = matches.get(matchNumber - 1);
                FirstLayerCommands.participateInMatch(loggedUser, match);
            }
        }
    }

    public void deleteMatch() {
        System.out.println("please enter (name) and (start time) to delete match.");
        System.out.print("name: ");
        String name = scanner.next();
        LocalDateTime startTime = ReadDataFromKeyboard.readDate();
        for (Match m: new MatchDAO().getAllMatches()) {
            if (name.equals(m.getMatchName()) && startTime.equals(m.getStartTime())) {
                FirstLayerCommands.deleteMatch(m);
            }
        }
    }

    public void viewMessagesAndAnswerThem() {
        if (loggedUser.getMessages().isEmpty()) {
            System.out.println("there are no message for you.");
            return;
        }
        FirstLayerCommands.viewMessages(loggedUser);
        while (true){
            System.out.println("do you want to answer one of them? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of message that you want to answer it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= loggedUser.getMessages().size()) {
                    Message message = loggedUser.getMessages().get(number - 1);
                    FirstLayerCommands.answerToMessage(message);
                }
            } else {
                break;
            }
        }
    }

    public void inviteCustomerToMatch() {
        Message<Match> message = ReadDataFromKeyboard.matchMessageRead(loggedUser);
        FirstLayerCommands.sendMessage(message);
    }

    public void enterCourse() {
        if (loggedUser.getAllCourses().isEmpty()) {
            System.out.println("You are not currently in any class.");
            loggedUser.printMenu();
            loggedUser.handleCommand();
        }
        System.out.println("The classes shown below are the classes you attend.");
        FirstLayerCommands.showUserCourses(loggedUser);
        System.out.print("Please enter the course number you want to participate: ");
        int courseNumber = scanner.nextInt();
        if (courseNumber < 1 || courseNumber > loggedUser.getAllCourses().size()) {
            System.out.println("a course with this number not exist.");
        } else {
            Course course = loggedUser.getAllCourses().get(courseNumber - 1);
            for (User u : course.getAllParticipants()) {
                if (u.equals(loggedUser)) {
                    u.printMenu();
                    u.handleCommand();
                    return;
                }
            }
        }
        loggedUser.printMenu();
        loggedUser.handleCommand();
    }

    public void enterMatch() {
        if (loggedUser.getParticipatedMatches().isEmpty()) {
            System.out.println("You are not currently participated in any match.");
            loggedUser.printMenu();
            loggedUser.handleCommand();
            return;
        }
        System.out.println("The matches shown below are the matches you attend.");
        FirstLayerCommands.showUserMatches(loggedUser);
        System.out.print("Please enter the match number you want to enter: ");
        int matchNumber = scanner.nextInt();
        if (matchNumber < 1 || matchNumber > loggedUser.getParticipatedMatches().size()) {
            System.out.println("a match with this number not exist.");
        } else {
            Match match = loggedUser.getParticipatedMatches().get(matchNumber - 1);
            for (User u : match.getParticipants()) {
                if (u.equals(loggedUser)) {
                    if (u instanceof Admin) {
                        new EnterMatchAsAdmin(u, match).enterMatchOptionHandler();
                        return;
                    } else if(u instanceof Customer) {
                        new EnterMatchAsUser(u, match).enterMatchOptionHandler();
                        return;
                    }
                }
            }
        }
        loggedUser.printMenu();
        loggedUser.handleCommand();
    }

    public void upgradeACustomerToAdmin() {
        FirstLayerCommands.viewUsers(loggedUser);
        ArrayList<User> users = FirstLayerCommands.getAllUsersExcludeMeAndAdmins(loggedUser);
        while (true){
            System.out.println("Do you want to upgrade a user to admin? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("enter number of user that you want to choose it: ");
                int number = scanner.nextInt();
                if (number > 0 && number <= users.size()) {
                    User admin = new Admin(users.get(number - 1));
                    new UserDAO().deleteUser(users.get(number - 1));
                    new UserDAO().updateUser(admin);
                } else {
                    System.out.println("you entered wrong number!");
                }
            } else {
                break;
            }
        }
    }
}
