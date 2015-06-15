package ua.samosfator.moduleok.student_bean;

public class Module {

    private String date;
    private int weight;
    private int score;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPassed() {
        return score != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        if (weight != module.weight) return false;
        if (score != module.score) return false;
        return !(date != null ? !date.equals(module.date) : module.date != null);

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + weight;
        result = 31 * result + score;
        return result;
    }

    @Override
    public String toString() {
        return "Module{" +
                "date='" + date + '\'' +
                ", weight=" + weight +
                ", score=" + score +
                '}';
    }
}
