package com.twu.biblioteca;

import java.util.List;

class BibliotecaApp {
    private List<Book> bookList;
    private List<String> optionList;
    private MockedIO mockedIO;

    BibliotecaApp(List<Book> bookList, List<String> optionList, MockedIO mockedIO) {
        this.bookList = bookList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    void listBooks() {
        for (Book book : bookList) {
            mockedIO.output(book.getName());
        }
    }

    void listBooksWithAllAttributes() {
        for (Book book : bookList) {
            mockedIO.output(book.getAll());
        }
    }

    void showOptions() {
        for (String option: optionList) {
            mockedIO.output(option);
        }
    }

    boolean selectOption() {
        showOptions();
        String selectedOption = mockedIO.input();
        switch (selectedOption.toUpperCase()) {
            case "LB":
                listBooksWithAllAttributes();
                return true;
            default:
                mockedIO.output("Select a valid option!");
                showOptions();
                return false;
        }
    }

}
