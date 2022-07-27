package ir.ac.kntu;

import ir.ac.kntu.dao.CourseDAO;
import ir.ac.kntu.userAndHisChildren.Student;
import ir.ac.kntu.userAndHisChildren.TeacherAssistant;
import ir.ac.kntu.userAndHisChildren.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Course implements Serializable {

    public enum CourseState {OPEN_PRIVATE, OPEN_PUBLIC, CLOSE}

    private String name;

    private String instituteName;

    private String academicYear;

    private final String password;

    private String courseDescription;

    private final User creator;

    private User teacher;

    private ArrayList<User> allParticipants;

    private ArrayList<User> students;

    private ArrayList<Question> questions;

    private CourseState courseState;

    public Course(String name, String instituteName,
                  String academicYear, String courseDescription, User creator, CourseState courseState, String password) {
        this.name = name;
        this.instituteName = instituteName;
        this.academicYear = academicYear;
        this.courseDescription = courseDescription;
        this.creator = new TeacherAssistant(creator, this);
        this.password = password;
        this.courseState = courseState;
        students = new ArrayList<>();
        questions = new ArrayList<>();
        allParticipants = new ArrayList<>();
        teacher = null;
    }

    public Course(Course a) {
        this.name = a.name;
        this.instituteName = a.instituteName;
        this.academicYear = a.academicYear;
        this.courseDescription = a.courseDescription;
        this.creator = a.creator;
        this.students = a.students;
        this.questions = a.questions;
        this.password = a.password;
        this.courseState = a.courseState;
        this.teacher = a.teacher;
        this.allParticipants = a.allParticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getCreatorName() {
        return creator.getName();
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public CourseState getCourseState() {
        return courseState;
    }

    public void setCourseState(CourseState courseState) {
        this.courseState = courseState;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public User getCreator() {
        return creator;
    }

    public void setTeacher(User teacher) {
        if (this.teacher != null) {
            System.out.println("you choose a teacher for this class before.");
        } else {
            this.teacher = teacher;
        }
    }

    public void removeStudent(User student) {
        students.remove(student);
    }

    public ArrayList<User> getAllParticipants() {
        allParticipants = addAllParticipantsToAllParticipantArrayList();
        return allParticipants;
    }

    private ArrayList<User> addAllParticipantsToAllParticipantArrayList() {
        ArrayList<User> allParticipants = new ArrayList<>();
        allParticipants.add(creator);
        if (teacher != null) {
            allParticipants.add(teacher);
        }
        allParticipants.addAll(students);
        return allParticipants;
    }

    public ArrayList<User> getAllStudents() {
        ArrayList<User> copyOfStudents = new ArrayList<>();
        for (User a : students) {
            copyOfStudents.add(a);
        }
        return copyOfStudents;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> copyOfQuestions = new ArrayList<>();
        for (Question a : questions) {
            copyOfQuestions.add(new Question(a));
        }
        return copyOfQuestions;
    }

    public void saveAllQuestions(ArrayList<Question> questions) {
        ArrayList<Question> copyOfQuestions = new ArrayList<>();
        for (Question a : questions) {
            copyOfQuestions.add(new Question(a));
        }
        this.questions = copyOfQuestions;
    }

    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
        }
    }

    public void addStudent(User student) {
        if (student.equals(creator)) {
            System.out.println("You have created this class yourself. You can not enroll as a student in the class.");
            return;
        }
        for (User a : students) {
            if (student.equals(a)) {
                System.out.println("you enrolled in this class before");
                return;
            }
        }
        students.add(student);
        new CourseDAO().updateCourse(this);
    }


    @Override
    public String toString() {
        return "Course{ " + "name: " + name + "| Institute name: " + instituteName +
                "| creatorName: " + creator + "| academicYear: " + academicYear +
                "| CourseState: " + courseState + "| courseDescription: " + courseDescription + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(instituteName, course.instituteName) && Objects.equals(creator.getName(), course.creator.getName()) && creator.equals(course.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instituteName, creator.getName(), creator);
    }
}