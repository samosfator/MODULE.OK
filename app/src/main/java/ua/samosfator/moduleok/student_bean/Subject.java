package ua.samosfator.moduleok.student_bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
        if (modules == null || modules.size() < 1) return new EmptyModule();
        int lastModuleIndex = modules.size() - 1;
        while (modules.get(lastModuleIndex).getScore() == 0 && lastModuleIndex >= 0) {
            lastModuleIndex--;
        }

        return modules.get(lastModuleIndex);
    }

    public Module getNearestModule() {
        if (modules == null || modules.size() < 1) return new EmptyModule();
        int nearestModuleIndex = modules.size() - 1;
        try {
            while (modules.get(nearestModuleIndex).getScore() != 0 && nearestModuleIndex > 0) {
                nearestModuleIndex--;
            }
        } catch (Exception e) {
            System.out.println(modules.size());
            System.out.println(nearestModuleIndex);
            System.out.println(modules);
            throw e;
        }
        if (nearestModuleIndex == 0) {
            return new EmptyModule();
        }

        return modules.get(nearestModuleIndex);
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

    public int tryToPredictScore() {
        int passedModulesNumber = getPassedModulesNumber();
        int allModulesNumber = modules.size();

        if (allModulesNumber - passedModulesNumber == 1) {
            double lastModuleWeight = modules.get(allModulesNumber - 1).getWeight();
            double passedModulesTotalFraction = 0;
            for (Module module : modules) {
                passedModulesTotalFraction += module.getScore() * (module.getWeight() / 100.0);
            }
            double lastModulePredictedScore = ((90 - passedModulesTotalFraction) / (lastModuleWeight / 100.0));
            if (lastModulePredictedScore < 100) {
                return (int) Math.round(lastModulePredictedScore);
            } else {
                lastModulePredictedScore = ((75 - passedModulesTotalFraction) / (lastModuleWeight / 100.0));
                if (lastModulePredictedScore < 100) {
                    return (int) Math.round(lastModulePredictedScore);
                } else {
                    lastModulePredictedScore = ((60 - passedModulesTotalFraction) / (lastModuleWeight / 100.0));
                    if (lastModulePredictedScore < 100) {
                        return (int) Math.round(lastModulePredictedScore);
                    } else {
                        return 0;
                    }
                }
            }
        } else {
            return 0;
        }
    }

    public int getPassedModulesNumber() {
        int count = 0;
        for (Module module : modules) {
            if (module.isPassed()) {
                count++;
            }
        }
        return count;
    }

    public static class ModulesByDateComparator implements Comparator<Module> {

        @Override
        public int compare(Module a, Module b) {
            SimpleDateFormat dateParser = new SimpleDateFormat("dd.mm.yy", Locale.getDefault());

            try {
                return dateParser.parse(a.getDate()).compareTo(dateParser.parse(b.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", controlType='" + controlType + '\'' +
                ", modules=" + modules +
                ", totalScore=" + totalScore +
                '}';
    }
}
