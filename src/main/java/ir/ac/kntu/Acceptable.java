package ir.ac.kntu;

import ir.ac.kntu.userAndHisChildren.User;

import java.io.Serializable;

public interface Acceptable extends Serializable {
    void accept(User user);
}