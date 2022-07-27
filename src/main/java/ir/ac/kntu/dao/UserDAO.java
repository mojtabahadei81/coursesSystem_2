package ir.ac.kntu.dao;

import ir.ac.kntu.userAndHisChildren.User;

import java.io.*;
import java.util.ArrayList;

public class UserDAO {

    private final File file = new File("src/dataBase/usersData.txt");

    public ArrayList<User> getAllUsers() {
        ArrayList<User> savedUsers = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while (true) {
                try {
                    User user = (User) input.readObject();
                    savedUsers.add(user);
                } catch (EOFException e) {
                    break;
                } catch (Exception e) {
                    System.out.println("Problem with some of the records in the Users data file");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data for Users has been saved.");
        }
        return savedUsers;
    }

    public void saveAllUsers(ArrayList<User> users) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            for (User u : users) {
                try {
                    output.writeObject(u);
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

    public User getUser(String username) {
        ArrayList<User> users = getAllUsers();
        for (User u : users) {
            if (username.equals(u.getUserName())) {
                return u;
            }
        }
        System.out.println("the user not found.");
        return null;
    }

    public void updateUser(User user) {
        ArrayList<User> users = getAllUsers();
        if (users.contains(user)) {
            users.set(users.indexOf(user), user);
            System.out.println(user.getUserName() + " successfully updated.");
        } else {
            users.add(user);
            System.out.println(user.getUserName() + " successfully added.");
        }
        saveAllUsers(users);
    }

    public void deleteUser(User user) {
        ArrayList<User> users = getAllUsers();
        if (users.contains(user)) {
            users.remove(user);
            System.out.println(user.getUserName() + " successfully removed.");
        } else {
            System.out.println("No users have been registered with this username.");
        }
        saveAllUsers(users);
    }
}
