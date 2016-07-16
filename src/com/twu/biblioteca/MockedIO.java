package com.twu.biblioteca;

public class MockedIO {
    void output(String context) {
        System.out.println(context);
    }

    String input () {
        String context = System.in.toString();
        return context;
    }
}
