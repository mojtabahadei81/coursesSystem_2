package ir.ac.kntu;

import ir.ac.kntu.userAndHisChildren.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Answer implements Serializable {

    private final User sender;

    private final LocalDateTime postageTime;

    private double score;

    private String answerText;

    public Answer(User sender, String answerText) {
        this.sender = sender;
        this.postageTime = LocalDateTime.now();
        this.answerText = answerText;
    }

    public LocalDateTime getPostageTime() {
        return postageTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score, LocalDateTime delayTime, double delayCoefficient) {
        if (delayTime.isAfter(postageTime)) {
            this.score = score;
        } else {
            this.score = score * delayCoefficient;
        }
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "answer: " +  answerText + "sender: " + sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        Answer answer = (Answer) o;
        return Objects.equals(sender, answer.sender) && Objects.equals(answerText, answer.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, answerText);
    }
}
