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

    public boolean isExam() {
        return getControlType().equals("Екзамен");
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

    public Module getMostValuableModule() {
        int maxModuleWeight = 0;
        int maxModuleWeightIndex = 4;
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (module.getWeight() > maxModuleWeight) {
                maxModuleWeight = module.getWeight();
                maxModuleWeightIndex = i;
            }
        }
        return modules.get(maxModuleWeightIndex);
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
