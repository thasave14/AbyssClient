package dev.abyss.client.socket;

public class User {

    private String name;
    private boolean usesAbyss;

    public User(String name, boolean usesAbyss) {
        this.name = name;
        this.usesAbyss = usesAbyss;
    }

    public String getProperties() {
        return name + ": " + (usesAbyss ? "true" : "false");
    }

    public String getName() {
        return name;
    }

    public boolean isUsesAbyss() {
        return usesAbyss;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsesAbyss(boolean usesAbyss) {
        this.usesAbyss = usesAbyss;
    }
}
