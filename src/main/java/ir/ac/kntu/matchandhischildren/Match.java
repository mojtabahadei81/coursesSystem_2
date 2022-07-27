package ir.ac.kntu.matchandhischildren;

import ir.ac.kntu.Acceptable;
import ir.ac.kntu.Answer;
import ir.ac.kntu.Question;
import ir.ac.kntu.userAndHisChildren.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Match implements Acceptable, Serializable {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";

    private String matchName;

    private LocalDateTime startTime;

    private LocalDateTime duration;

    private LocalDateTime endTime;

    private ArrayList<Question> questions;

    private ArrayList<User> participants;

    private ArrayList<Answer> answers;

    public enum MatchStatus {EXPIRED, NOT_STARTED, ACTIVE}

    private MatchStatus status;

    private int maxMember = Integer.MAX_VALUE;

    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
        } else {
            System.out.println("this question added before!");
        }
    }

    public Match(String matchName, LocalDateTime startTime, LocalDateTime duration) {
        this.matchName = matchName;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plusMinutes(duration.getMinute()).plusHours(duration.getHour())
                .plusDays(duration.getDayOfMonth()).plusMonths(duration.getMonthValue());
        System.out.println(endTime);
        questions = new ArrayList<>();
        participants = new ArrayList<>();
        handleMatchStatus();
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> copyOfQuestions = new ArrayList<>();
        for (Question q : questions) {
            copyOfQuestions.add(new Question(q));
        }
        return copyOfQuestions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        ArrayList<Question> copyOfQuestions = new ArrayList<>();
        for (Question q : questions) {
            copyOfQuestions.add(new Question(q));
        }
        this.questions = copyOfQuestions;
    }

    public ArrayList<User> getParticipants() {
        ArrayList<User> copyOfParticipants = new ArrayList<>();
        for (User u : participants) {
            copyOfParticipants.add(u);
        }
        return copyOfParticipants;
    }

    public void setParticipants(ArrayList<User> participants) {
        ArrayList<User> copyOfParticipants = new ArrayList<>();
        for (User u : participants) {
            copyOfParticipants.add(u);
        }
        this.participants = copyOfParticipants;
    }

    public void addParticipant(User participant) {
        if (status != MatchStatus.EXPIRED && !participants.contains(participant)) {
            participants.add(participant);
        } else {
            System.out.println("You can't participate in this Match.");
        }
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


    public int getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public void handleMatchStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            status = MatchStatus.NOT_STARTED;
        } else if (now.isAfter(endTime)) {
            status = MatchStatus.EXPIRED;
        } else {
            status = MatchStatus.ACTIVE;
        }
    }

    @Override
    public void accept(User user) {
        addParticipant(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        Match match = (Match) o;
        return maxMember == match.maxMember && Objects.equals(matchName, match.matchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchName, maxMember);
    }

    @Override
    public String toString() {
        return "Match{" + "matchName: " + matchName + "| startTime: " + startTime + "| endTime: " + endTime +
                "| maxMember: " + maxMember + ANSI_RED + "| status: " + status + ANSI_RESET +"| Type: " + this.getClass() + '}';
    }
}
