package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

public enum Sections {

    LAST_TOTAL(0),
    MODULES(1),
    UPDATE_TIME(2),
    FEEDBACK(3),
    VERSION(4),
    LOG_IN(5);

    public final int INDEX;

    Sections(int index) {
        this.INDEX = index;
    }
}
