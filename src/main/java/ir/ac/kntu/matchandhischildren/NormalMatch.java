package ir.ac.kntu.matchandhischildren;

import java.time.LocalDateTime;

public class NormalMatch extends Match{

    public NormalMatch(String matchName, LocalDateTime startTime, LocalDateTime duration) {
        super(matchName, startTime, duration);
        setMaxMember(50);
    }


}
