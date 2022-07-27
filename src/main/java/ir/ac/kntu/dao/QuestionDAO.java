package ir.ac.kntu.dao;

import ir.ac.kntu.Question;

import java.io.*;
import java.util.ArrayList;

public class QuestionDAO {

    File file = new File("src/dataBase/questionBank.txt");

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> savedQuestions = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while (true) {
                try {
                    Question question = (Question) input.readObject();
                    savedQuestions.add(question);
                } catch (EOFException e) {
                    break;
                } catch (Exception e) {
                    System.out.println("Problem with some of the records in the questionBank data file");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data for questions has been saved.");
        }
        return savedQuestions;
    }

    public void saveAllQuestions(ArrayList<Question> questions) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            for (Question q : questions) {
                try {
                    output.writeObject(q);
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

    public void updateQuestions(Question question) {
        ArrayList<Question> questions = getAllQuestions();
        if (questions.contains(question)) {
            questions.set(questions.indexOf(question), question);
            System.out.println(question.getName() + " successfully updated.");
        } else {
            questions.add(question);
            System.out.println(question.getName() + " successfully added.");
        }
        saveAllQuestions(questions);
    }
}
