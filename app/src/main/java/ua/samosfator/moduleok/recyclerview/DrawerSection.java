package ua.samosfator.moduleok.recyclerview;

import android.content.Intent;

public class DrawerSection {
    private String title;
    private int iconId;

    private Intent intent;

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

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
