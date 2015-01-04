package ua.samosfator.moduleok.parser;

import java.io.Serializable;

public enum CONTROL_TYPE implements Serializable {

    CREDIT("Залік"),
    EXAM("Екзамен");

    private final String controlName;

    CONTROL_TYPE(String controlName) {
        this.controlName = controlName;
    }

    public String getControlName() {
        return controlName;
    }
}
