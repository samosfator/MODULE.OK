package ua.samosfator.moduleok.recyclerview;

import android.support.v4.app.Fragment;

public class DrawerSection {
    private String title;
    private int iconId;

    private Fragment fragment;

    public DrawerSection(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
