package com.bash.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private final App app = new App();

    @Test
    void returnsGreetingForProvidedName() {
        assertEquals("Hello, Bash!", app.getGreeting("Bash"));
    }

    @Test
    void returnsGuestGreetingForBlankName() {
        assertEquals("Hello, Guest!", app.getGreeting(""));
    }

    @Test
    void returnsGuestGreetingForNullName() {
        assertEquals("Hello, Guest!", app.getGreeting(null));
    }
}
