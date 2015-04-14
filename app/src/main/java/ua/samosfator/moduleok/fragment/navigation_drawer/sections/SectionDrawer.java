package ua.samosfator.moduleok.fragment.navigation_drawer.sections;

public class SectionDrawer {

    private String title;
    private int iconId;

    public SectionDrawer(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
