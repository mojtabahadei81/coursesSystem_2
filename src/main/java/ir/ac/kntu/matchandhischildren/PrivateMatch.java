package ir.ac.kntu.matchandhischildren;

import java.time.LocalDateTime;

public class PrivateMatch extends Match{

    public PrivateMatch(String matchName, LocalDateTime startTime, LocalDateTime duration) {
        super(matchName, startTime, duration);
        setMaxMember(20);
    }


}
