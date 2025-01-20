package dev.abyss.client.socket;

import co.gongzh.procbridge.Client;

//quickdaffy shit idfk :sob:
public class SocketClient {

    public static final Client client = new Client("localhost", 3115);

    public static Object sendRequest(String... args) {
        return client.request("echo", String.join(" ", args));
    }

    public static boolean isUser(String name) {
        String[] args = client.request("usesAbyss", name).toString().split(":");

        if(args[0].equals("true")) {
            return true;
        } else if(args[0].equals("false")) {
            return false;
        } else {
            return false;
        }
    }

    public static double random(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }
}
