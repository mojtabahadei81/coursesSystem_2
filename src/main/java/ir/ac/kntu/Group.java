package ir.ac.kntu;

import ir.ac.kntu.userAndHisChildren.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    private String name;

    private ArrayList<User> group;

    private User creator;

    public Group(String name, User creator) {
        this.creator = creator;
        this.name = name;
    }

    private void addUser(User user) {
        if (!group.contains(user)) {
            group.add(user);
        }
    }

    private void deleteUser(User user) {
        group.remove(user);
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getGroup() {
        return new ArrayList<>(group);
    }

    public User getCreator() {
        return creator;
    }
}
