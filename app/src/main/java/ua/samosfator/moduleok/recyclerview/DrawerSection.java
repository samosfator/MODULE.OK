package ua.samosfator.moduleok.recyclerview;

public class DrawerSection {
    private String title;
    private int iconId;

    public DrawerSection(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconId() {
        return iconId;
    }
}
