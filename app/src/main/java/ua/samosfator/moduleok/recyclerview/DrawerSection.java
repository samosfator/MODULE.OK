package ua.samosfator.moduleok.recyclerview;

import android.content.Intent;
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

    public int getIconId() {
        return iconId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
