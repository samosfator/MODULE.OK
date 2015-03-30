package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

public enum SectionsEnum {

    LAST_TOTAL(0),
    MODULES(1),
    DETAILED_SUBJECTS(2),
    LOG_IN(3),
    EMPTY(4),
    VERSION(5),
    FEEDBACK(6);

    public final int INDEX;

    SectionsEnum(int index) {
        this.INDEX = index;
    }

    public static SectionsEnum getSectionById(int id) {
        for (SectionsEnum sectionsEnum : SectionsEnum.values()) {
            if (sectionsEnum.INDEX == id) {
                return sectionsEnum;
            }
        }
        return EMPTY;
    }
}
