package com.twu.biblioteca;

import java.util.List;


class BibliotecaApp {
    List<Resource> bookList;
    List<Movie> movieList;
    List<Resource> checkedBookList;
    List<Movie> checkedMovieList;
    private List<String> optionList;
    private MockedIO mockedIO;
    private CustomerDataManagement customerDataManagement;

    BibliotecaApp(List<Resource> bookList,
                  List<Movie> movieList,
                  List<Resource> checkedBookList,
                  List<Movie> checkedMovieList,
                  List<String> optionList,
                  MockedIO mockedIO,
                  CustomerDataManagement customerDataManagement) {
        this.bookList = bookList;
        this.movieList = movieList;
        this.checkedBookList = checkedBookList;
        this.checkedMovieList = checkedMovieList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
        this.customerDataManagement = customerDataManagement;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    boolean addBook(Book book, List<Resource> bookList) {
        if (book != null) {
            bookList.add(book);
            return true;
        } else
            return false;
    }

    boolean addMovie(Movie movie, List<Movie> movieList) {
        if (movie != null) {
            movieList.add(movie);
            return true;
        } else
            return false;
    }

    void addOption(String option) {
        this.optionList.add(option);
    }

    boolean listRepository(List<Resource> list) {
        if (list.isEmpty()) {
            return false;
        } else {
            for (Resource resource : list) {
                mockedIO.output(resource.getName());
            }
            return true;
        }
    }

    boolean listMovies(List<Movie> movieList) {
        if (movieList.isEmpty()) {
            return false;
        } else {
            for (Movie movie : movieList) {
                mockedIO.output(movie.getName());
            }
            return true;
        }
    }

    boolean listBooksWithAllAttributes(List<Resource> bookList) {
        if (bookList.isEmpty()) {
            return false;
        } else {
            for (Resource book : bookList) {
                mockedIO.output(book.getAll());
            }
            return true;
        }
    }

    boolean listMoviesWithAllAttributes(List<Movie> movieList) {
        if (movieList.isEmpty()) {
            return false;
        } else {
            for (Movie movie : movieList) {
                mockedIO.output(movie.getAll());
            }
            return true;
        }
    }

    boolean showOptions() {
        if (optionList.isEmpty()) {
            return false;
        } else {
            for (String option : optionList) {
                mockedIO.output(option);
            }
            return true;
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
            for (Resource book : bookList) {
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

    private Book queryOneBook(String bookName, List<Resource> bookList) {
        for (Resource aBook : bookList) {
            if (aBook.getName().equals(bookName)) {
                return (Book) aBook;
            }
        }
        return null;
    }

    boolean checkoutOneMovie() {
        String inputMovie = mockedIO.input();
        if (inputMovie.isEmpty()) {
            mockedIO.output("Invalid input.");
            return false;
        } else {
            for (Movie movie : movieList) {
                    if (inputMovie.equals(movie.getName()) && movieList.remove(movie) && checkedMovieList.add(movie)) {
                            mockedIO.output("Thank you! Enjoy the movie.");
                            return true;
                }
            }
            mockedIO.output("That movie is not available.");
            return false;
        }
    }

    Movie queryOneMovie(String movieName, List<Movie> movieList) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }
        return null;
    }

    boolean login() {
        mockedIO.output("Please input your account:");
        String account = mockedIO.input();
        mockedIO.output("Please input your password:");
        String password = mockedIO.input();

        User user = customerDataManagement.login(account, password);
        return user != null;
    }


}
