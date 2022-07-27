package ir.ac.kntu;

import ir.ac.kntu.commands.FirstLayerCommands;
import ir.ac.kntu.userInterface.Start;

public class Menus {
    public static void printStartMenu() {
        System.out.println("""
                1: login
                2: signup
                3: continue as guest
                4: exit
                if you have an account enter 1 otherwise enter 2 to create an account""");
    }

    public static void printAdminMenu() {
        System.out.println("""
               1: create new match
               2: create class
               3: view classes and edit them
               4: delete a class
               5: enter a match
               6: enter a class
               7: view matches and edit them
               8: delete a match
               9: view questionBank and edit it
               10: add question to questionBank
               11: invite customers a match
               12: view all users
               13: upgrade a user to admin
               14: log out""");
    }

    public static void printCustomerMenu() {
        System.out.println("""
                1: create a class
                2: delete a class
                3: enroll in a class
                4: enter a class
                5: enter a match
                6: view questionBank
                7: add question to questionBank
                8: view matches and participate in them
                9: view messages and answer them
                10: log out""");
    }

    public static void printGuestMenu() {
        System.out.println("""
                1: view QuestionBank
                2: View expired matches
                3: signup
                4: log out""");
    }

    public static void printTeacherMenu() {
        System.out.println("""
                1: add a student in this class.
                2: view questions.
                3: create and add question.
                4: add question from question bank.
                5: exit from this course.""");
    }

    public static void printStudentMenu() {
        System.out.println("""
                1: view questions.
                2: answer to questions.
                3: exit from this course.""");
    }

    public static void printTeacherAssistantMenu() {
        System.out.println("""
                1: create and add question.
                2: add question from question bank.
                3: view questions.
                4: upgrade a student to a teacher
                5: exit from this class.""");
    }

    public static void enterMatchAsAdminMenu() {
        System.out.println("""
                1: view questions.
                2: add question from question bank.
                3: createAndAddQuestion.
                4: correct user answers.
                5: exit from this match.""");
    }

    public static void enterMatchAsCustomerMenu() {
        System.out.println("""
                1: view questions.
                2: answer to questions.
                3: exit from this match.""");
    }

}

