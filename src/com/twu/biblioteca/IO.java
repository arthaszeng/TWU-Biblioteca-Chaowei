package com.twu.biblioteca;

import java.util.Scanner;

class IO {
    void output(String context) {
        System.out.println(context);
    }

    String input () {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
