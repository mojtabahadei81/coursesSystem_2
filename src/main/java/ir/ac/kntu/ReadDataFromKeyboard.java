package ir.ac.kntu;

import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.matchandhischildren.NormalMatch;
import ir.ac.kntu.matchandhischildren.PrivateMatch;
import ir.ac.kntu.matchandhischildren.SpecialMatch;
import ir.ac.kntu.userAndHisChildren.Admin;
import ir.ac.kntu.userAndHisChildren.Customer;
import ir.ac.kntu.userAndHisChildren.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReadDataFromKeyboard {

    public static User readUser() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("please enter the requested information.");
        System.out.print("name: ");
        String name = scanner.next();
        System.out.print("username: ");
        String userName = scanner.next();
        System.out.print("nationalId: ");
        String nationalId = scanner.next();
        System.out.print("mobile number: ");
        String mobileNumber = scanner.next();
        System.out.print("email: ");
        String email = scanner.next();
        System.out.print("password: ");
        String password = scanner.next();
        return new Customer(name, userName, email, password, nationalId, mobileNumber);
    }

    public static Course readCourse(User loggedUser) {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("Please enter the requested information: ");
        System.out.print("name: ");
        String name = scanner.next();
        System.out.print("name of educational institution: ");
        String instituteName = scanner.next();
        String password = null;
        System.out.println("Choose your class type.\n 1)OPEN_PRIVATE\n 2)OPEN_PUBLIC\n 3)Close");
        Course.CourseState courseState = setCourseState();
        System.out.println("course description: ");
        scanner.nextLine();
        String courseDescription = scanner.nextLine();
        if (courseState.equals(Course.CourseState.OPEN_PRIVATE)) {
            System.out.print("please enter password: ");
            password = scanner.next();
        }
        return new Course(name, instituteName, String.valueOf(LocalDateTime.now().getYear()), courseDescription, loggedUser, courseState, password);
    }

    private static Course.CourseState setCourseState() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        Course.CourseState courseState = null;
        boolean q = true;
        while (q) {
            try {
                int i = scanner.nextInt();
                switch (i) {
                    case 1 -> {
                        courseState = Course.CourseState.OPEN_PRIVATE;
                        q = false;
                    }
                    case 2 -> {
                        courseState = Course.CourseState.OPEN_PUBLIC;
                        q = false;
                    }
                    case 3 -> {
                        courseState = Course.CourseState.CLOSE;
                        q = false;
                    }
                    default -> System.out.println("Please select a number within the range.");
                }
            } catch (Exception e) {
                System.out.println("please enter a number not another thing!");
            }
        }
        return courseState;
    }

    public static Question questionRead() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("please enter the requested information.");
        System.out.print("name: ");
        String questionName = scanner.next();
        System.out.print("score: ");
        int score = scanner.nextInt();
        System.out.print("question text: ");
        scanner.nextLine();
        String questionText = scanner.nextLine();
        System.out.println("question level? (1.veryHard | 2.hard | 3.medium | 4.easy)");
        int questionLevel = scanner.nextInt();
        if (questionLevel > 4 || questionLevel < 0) {
            System.out.println("The number entered is incorrect");
            return null;
        }
        System.out.println("question type? (1.test | 2.essay | 3.empty | 4.shortAnswer)");
        int questionType = scanner.nextInt();
        if (questionType > 4 || questionType < 0) {
            System.out.println("The number entered is incorrect");
            return null;
        }
        Question.DifficultyLevel[] difficultyLevels = Question.DifficultyLevel.values();
        Question.DifficultyLevel difficultyLevel = difficultyLevels[questionLevel - 1];
        Question.QuestionType[] questionTypes = Question.QuestionType.values();
        Question.QuestionType questionType1 = questionTypes[questionType - 1];
        return new Question(questionName, score, questionText, difficultyLevel, questionType1);
    }

    public static Match matchRead() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("Please enter the requested information: ");
        System.out.print("name: ");
        String name = scanner.next();
        System.out.println("start date: ");
        LocalDateTime startDate = readDate();
        System.out.println("duration of Match: ");
        LocalDateTime duration = readDate();
        System.out.println("Choose your class type.\n 1)NORMAL\n 2)PRIVATE\n 3)SPECIAL");
        do {
            switch (scanner.nextInt()) {
                case 1:
                    return new NormalMatch(name, startDate, duration);
                case 2:
                    return new PrivateMatch(name, startDate, duration);
                case 3:
                    return new SpecialMatch(name, startDate, duration);
                default:
            }
        } while (true);
    }

    public static LocalDateTime readDate() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        LocalDateTime time = LocalDateTime.now();
        System.out.print("month: ");
        int month = scanner.nextInt();
        System.out.print("day: ");
        int day = scanner.nextInt();
        System.out.print("hour: ");
        int hour = scanner.nextInt();
        System.out.print("minute: ");
        int minute = scanner.nextInt();
        return LocalDateTime.of(time.getYear(), month, day, hour, minute);
    }

    public static Message<Match> matchMessageRead(User sender) {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        Match match;
        User user;
        FirstLayerCommands.viewAllMatches();
        while (true) {
            System.out.println("Which match do you want to invite? please enter number.");
            int number = scanner.nextInt();
            if (number > 0 && number <= new MatchDAO().getAllMatches().size()) {
                match = new MatchDAO().getAllMatches().get(number - 1);
                break;
            } else {
                System.out.println("you entered a wrong number.");
            }
        }
        System.out.print("message text: ");
        scanner.nextLine();
        String messageText = scanner.nextLine();
        FirstLayerCommands.viewUsers(sender);
        while (true) {
            System.out.println("Which user do you want to invite? please enter number.");
            int number = scanner.nextInt();
            if (number > 0 && number <= new UserDAO().getAllUsers().size()) {
                user = FirstLayerCommands.getAllUsersExcludeMeAndAdmins(sender).get(number - 1);
                break;
            } else {
                System.out.println("you entered a wrong number.");
            }
        }
        return new Message<Match>(messageText, match, sender, user);
    }

    public static Answer readAnswer(User sender) {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("please enter text of your answer: ");
        scanner.nextLine();
        String answerText = scanner.nextLine();
        return new Answer(sender, answerText);
    }

}
