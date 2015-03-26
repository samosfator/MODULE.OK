package ua.samosfator.moduleok.student_bean;

import java.util.List;

public class Subject {

    private String name;
    private String controlType;
    private List<Module> modules;
    private int totalScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Module getLastModule() {
        int lastModuleIndex = modules.size() - 1;
        while (modules.get(lastModuleIndex).getScore() == 0) {
            lastModuleIndex--;
            if (lastModuleIndex < 0) {
                return modules.get(0);
            }
        }

        return modules.get(lastModuleIndex);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getScoresSum() {
        int sum = 0;
        for (Module module : this.getModules()) {
            sum = module.getScore();
        }
        return sum;
    }
}
