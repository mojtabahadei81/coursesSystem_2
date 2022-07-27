package ir.ac.kntu.dao;

import ir.ac.kntu.Course;

import java.io.*;
import java.util.ArrayList;

public class CourseDAO {

    private final File file = new File("src/dataBase/coursesData.txt");

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> savedCourses = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while (true) {
                try {
                    Course course = (Course) input.readObject();
                    savedCourses.add(course);
                } catch (EOFException e) {
                    break;
                } catch (Exception e) {
                    System.out.println("Problem with some of the records in the Courses data file");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data for courses has been saved.");
        }
        return savedCourses;
    }

    public void saveAllCourses(ArrayList<Course> courses) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            for (Course c : courses) {
                try {
                    output.writeObject(c);
                } catch (IOException e) {
                    System.out.println("An error occurred while trying to save info");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while trying to save info");
            System.out.println(e.getMessage());
        }
    }

    public void updateCourse(Course course) {
        ArrayList<Course> courses = getAllCourses();
        if (courses.contains(course)) {
            courses.set(courses.indexOf(course), course);
            System.out.println(course.getName() + " successfully updated.");
        } else {
            courses.add(course);
            System.out.println(course.getName() + " successfully added.");
        }
        saveAllCourses(courses);
    }

    public void deleteCourse(Course course) {
        ArrayList<Course> courses = getAllCourses();
        if (courses.contains(course)) {
            courses.remove(course);
            System.out.println(course.getName() + " successfully removed.");
        } else {
            System.out.println("No course have been registered with this username.");
        }
        saveAllCourses(courses);
    }

    public Course getCourseWithUsingProfile(String courseName, String instituteName, String academicYear) {
        for (Course a : getAllCourses()){
            if (courseName.equals(a.getName()) && instituteName.equals(a.getInstituteName()) && academicYear.equals(a.getAcademicYear())) {
                return a;
            }
        }
        return null;
    }
}
