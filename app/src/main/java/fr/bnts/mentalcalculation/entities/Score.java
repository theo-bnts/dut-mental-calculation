package fr.bnts.mentalcalculation.entities;

public class Score {

    private final String nickname;
    private final int score;
    private final int remainingLives;
    private final String date;

    public Score(String nickname, int score, int remainingLives, String date) {
        this.nickname = nickname;
        this.score = score;
        this.remainingLives = remainingLives;
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public String getDate() {
        return date;
    }

}