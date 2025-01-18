package dev.abyss.client.utils;

public class KDTracker {
    private static int kills = 0;
    private static int deaths = 0;

    public static void addKill() {
        kills++;
    }

    public static void addDeath() {
        deaths++;
    }

    public static double getKD() {
        return deaths == 0 ? kills : (double) kills / deaths;
    }
}
