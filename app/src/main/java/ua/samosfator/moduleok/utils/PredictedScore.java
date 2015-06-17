package ua.samosfator.moduleok.utils;

public class PredictedScore {

    private int modulesAtAll;
    private int modulesPassed;

    private int desiredTotalScore;
    private int sufficientScore;

    public PredictedScore(int modulesAtAll, int modulesPassed, int desiredTotalScore, int sufficientScore) {
        this.modulesAtAll = modulesAtAll;
        this.modulesPassed = modulesPassed;
        this.desiredTotalScore = desiredTotalScore;
        this.sufficientScore = sufficientScore;
    }

    public int getModulesAtAll() {
        return modulesAtAll;
    }

    public int getModulesPassed() {
        return modulesPassed;
    }

    public int getDesiredTotalScore() {
        return desiredTotalScore;
    }

    public int getSufficientScore() {
        return sufficientScore;
    }

    public static PredictedScore emptyInstance() {
        return new PredictedScore(0, 0, 0, 0);
    }
}
