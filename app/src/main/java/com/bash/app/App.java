package com.bash.app;

public class App {

    public String getGreeting(String name) {
        if (name == null || name.isBlank()) {
            return "Hello, Guest!";
        }

        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        App app = new App();

        String name = args.length > 0 ? args[0] : "Guest";

        System.out.println(app.getGreeting(name));
    }
}
