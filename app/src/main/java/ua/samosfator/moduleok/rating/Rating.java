package ua.samosfator.moduleok.rating;

public class Rating {

    private String userId;
    private String group;
    private String sendUrl;
    private int totalScore;
    private int scoresCount;
    private int totalScoreLimit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getScoresCount() {
        return scoresCount;
    }

    public void setScoresCount(int scoresCount) {
        this.scoresCount = scoresCount;
    }

    public int getTotalScoreLimit() {
        return totalScoreLimit;
    }

    public void setTotalScoreLimit(int totalScoreLimit) {
        this.totalScoreLimit = totalScoreLimit;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }
}
