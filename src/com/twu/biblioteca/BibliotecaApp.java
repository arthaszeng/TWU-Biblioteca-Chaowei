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

    public BibliotecaApp(List<Book> bookList, List<String> optionList) {
        this.bookList = bookList;
        this.optionList = optionList;
    }

    public BibliotecaApp(List<Book> bookList) {
        this.bookList = bookList;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App stated");
    }

    void listBooks() {
        for (Book book : bookList) {
            mockedIO.output(book.getAll());
        }
    }

    void listBooksWithAllAttributes() {
        for (Book book : bookList) {
            mockedIO.output(book.getAll());
        }
    }

    void showOptions() {
        optionList.forEach(System.out::println);
    }

    boolean selectOption(String selectedOption) {
        switch (selectedOption.toUpperCase()) {
            case "LB":
                listBooksWithAllAttributes();
                return true;
            default:
                return false;
        }
    }

}
