package ir.ac.kntu.matchandhischildren;

import ir.ac.kntu.userAndHisChildren.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SpecialMatch extends Match {

    private ArrayList<ArrayList<User>> groups;

    public SpecialMatch(String matchName, LocalDateTime startTime, LocalDateTime duration) {
        super(matchName, startTime, duration);
        groups = new ArrayList<>();
        setMaxMember(100);
    }



}
