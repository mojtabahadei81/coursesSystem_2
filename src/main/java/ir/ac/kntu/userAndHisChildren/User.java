package ir.ac.kntu.userAndHisChildren;

import ir.ac.kntu.Course;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public abstract class User implements Serializable {
    private String name;

    private String userName;

    private String email;

    private String password;

    private String nationalId;

    private String mobileNumber;

    private final ArrayList<Course> ownerCourses;

    private final ArrayList<Course> enrolledCourses;

    private final ArrayList<Match> participatedMatches;

    private int score;

    private int level;

    private ArrayList<Message> messages;

    public User(String name, String userName, String email, String password, String nationalId, String mobileNumber) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.nationalId = nationalId;
        this.mobileNumber = mobileNumber;
        participatedMatches = new ArrayList<>();
        enrolledCourses = new ArrayList<>();
        ownerCourses = new ArrayList<>();
        messages = new ArrayList<>();
        score = 0;
        level = 1;
    }

    public User(User a) {
        this.name = a.name;
        this.userName = a.userName;
        this.email = a.email;
        this.password = a.password;
        this.nationalId = a.nationalId;
        this.mobileNumber = a.mobileNumber;
        this.enrolledCourses = a.getEnrolledCourses();
        this.ownerCourses = a.getOwnerCourses();
        this.messages = a.messages;
        this.score = a.score;
        this.level = a.level;
        this.participatedMatches = a.participatedMatches;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Course> getEnrolledCourses() {
        ArrayList<Course> copyOfEnrolledCourses = new ArrayList<>();
        for (Course a : enrolledCourses) {
            copyOfEnrolledCourses.add(new Course(a));
        }
        return copyOfEnrolledCourses;
    }

    public ArrayList<Course> getOwnerCourses() {
        ArrayList<Course> copyOfCourses = new ArrayList<>();
        for (Course a : ownerCourses) {
            copyOfCourses.add(new Course(a));
        }
        return copyOfCourses;
    }

    public void addOwnerClass(Course course) {
        if (!ownerCourses.contains(course)) {
            ownerCourses.add(course);
        }
    }

    public void addEnrolledCourses(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void addMatch(Match match) {
        if (!participatedMatches.contains(match)) {
            participatedMatches.add(match);
        }
    }

    public void deleteOwnerClass(Course course) {
        ownerCourses.remove(course);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void removeMatch(Match match) {
        participatedMatches.remove(match);
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> copyOfCourses = new ArrayList<>();
        for (Course c: ownerCourses) {
            copyOfCourses.add(new Course(c));
        }
        for (Course c: enrolledCourses) {
            copyOfCourses.add(new Course(c));
        }
        return copyOfCourses;
    }

    public ArrayList<Message> getMessages() {
        ArrayList<Message> copyOfMessages = new ArrayList<>();
        for (Message m : messages) {
            copyOfMessages.add(new Message(m));
        }
        return copyOfMessages;
    }

    public void addMessage(Message message) {
        if (!messages.contains(message)) {
            messages.add(message);
        } else {
            System.out.println("you sent this message before!");
        }
    }

    public ArrayList<Match> getParticipatedMatches() {
        return new ArrayList<>(participatedMatches);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public abstract void printMenu();

    public abstract void handleCommand();

    public void handleLevel() {
        level = score / 50;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(userName, user.userName) && Objects.equals(nationalId, user.nationalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userName, nationalId);
    }

    @Override
    public String toString() {
        handleLevel();
        return "User{" +
                " name:" + name +
                "| userName: " + userName +
                "| email: " + email +
                "| score: " + score +
                "| level: " + level +
                " }";
    }
}
