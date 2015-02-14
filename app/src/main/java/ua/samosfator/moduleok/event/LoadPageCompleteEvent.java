package ua.samosfator.moduleok.event;

public class LoadPageCompleteEvent {

    private String mainPageHtml;

    public LoadPageCompleteEvent(String html) {
        mainPageHtml = html;
    }

    public String getMainPageHtml() {
        return mainPageHtml;
    }
}
