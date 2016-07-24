package com.twu.biblioteca;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;
import java.util.List;

class BibliotecaApp {
    List<Book> bookList;
    List<Book> checkedBookList;
    private List<String> optionList;
    private MockedIO mockedIO;


    BibliotecaApp(List<Book> bookList, List<String> optionList, MockedIO mockedIO) {
        this.bookList = bookList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
    }

    BibliotecaApp(List<Book> bookList, List<Book> checkedBookList, List<String> optionList, MockedIO mockedIO) {
        this.bookList = bookList;
        this.checkedBookList = checkedBookList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    void listBooks(List<Book> bookList) {
        for (Book book : bookList) {
            mockedIO.output(book.getName());
        }
    }

    void listBooksWithAllAttributes(List<Book> bookList) {
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
        switch (mockedIO.input().toUpperCase()) {
            case "LB":
                listBooksWithAllAttributes(bookList);
                return true;
            case "QUIT":
                mockedIO.output("Over!");
                return false;
            default:
                mockedIO.output("Select a valid option!");
                return true;
        }
    }

    void keepCycle() {
        showOptions();
        while (true) {
            if (!selectOption()) {
                break;
            }
        }
    }

    boolean checkoutOneBook() {
        String inputOfBookName = mockedIO.input();
        if (bookList.isEmpty()) {
            return false;
        } else {
            for (Book book : bookList) {
                if (book.getName().equals(inputOfBookName)) {
                    bookList.remove(book);
                    checkedBookList.add(book);
                    mockedIO.output("Thank you! Enjoy the book");
                    return true;
                }
            }
            mockedIO.output("That book is not available");
            return false;
        }
    }

    boolean returnBook() {
        String inputedBookName = mockedIO.input();
        if (inputedBookName.isEmpty()) {
//            mockedIO.output("error input");
            return false;
        } else {
            Book queriedBook = queryOneBook(inputedBookName, checkedBookList);
            if (queriedBook == null) {
                mockedIO.output("That is not a valid book to return.");
                return false;
            } else {
                checkedBookList.remove(queriedBook);
                bookList.add(queriedBook);
                mockedIO.output("Thank you for returning the book.");
                return true;
            }
        }
    }

    private Book queryOneBook(String bookName, List<Book> bookList) {
        for (Book aBook : bookList) {
            if (aBook.getName().equals(bookName)) {
                return aBook;
            }
        }
        return null;
    }
}
