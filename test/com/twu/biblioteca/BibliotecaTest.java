package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class BibliotecaTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldShowWelcomeWhenAppStart() throws Exception {
        BibliotecaApp bibliotecaApp = new BibliotecaApp();
        bibliotecaApp.showWelcome();
        assertEquals("Welcome, App started\n", outputStream.toString());
    }
}
