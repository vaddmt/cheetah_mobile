package cheetah.cheetah_LGC;

public class GameResult {

    private int questionCounter;
    private int correctAnswerCounter;
    private int timeTotal;
    private int pointsTotal;

    public GameResult() {
        questionCounter = 0;
        correctAnswerCounter = 0;
        timeTotal = 0;
        pointsTotal = 0;
    }

    public boolean isBiggerThan(GameResult other) {
        return this.getPointsTotal() > other.getPointsTotal();
    }

    public void setQuestionCounter(int v) {
        questionCounter = v;
    }

    public void setCorrectAnswerCounter(int v) {
        correctAnswerCounter = v;
    }

    public void setPointsTotal(int v) {
        pointsTotal = v;
    }

    public void increaseQuestionCounter() {
        questionCounter++;
    }

    public void increaseCorrectAnswerCounter(int v) {
        correctAnswerCounter += v;
    }

    public void increasePointsTotal(int v) {
        pointsTotal += v;
    }

    public void setTimeTotal(int t) {
        timeTotal = t;
    }

    public int getCorrectAnswerCounter() {
        return correctAnswerCounter;
    }

    public int getQuestionCounter() {
        return questionCounter;
    }

    public int getTimeTotal() {
        return timeTotal;
    }

    public int getPointsTotal() {
        return pointsTotal;
    }
}
