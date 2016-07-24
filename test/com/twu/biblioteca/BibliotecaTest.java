package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



public class BibliotecaTest {
    private BibliotecaApp bibliotecaApp;
    private MockedIO mockedIO = mock(MockedIO.class);
    private CustomerDataManagement customerDataManagement = new CustomerDataManagement();

    @Before
    public void setUp() throws Exception {
        List<Resource> bookList = new ArrayList<>();
        List<Resource> checkedBookList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();
        List<Movie> checkedMovieList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        bibliotecaApp = new BibliotecaApp(bookList, movieList, checkedBookList, checkedMovieList, optionList, mockedIO, customerDataManagement);
    }

    @Test
    public void shouldShowWelcomeWhenAppStart() throws Exception {
        bibliotecaApp.showWelcome();

        verify(mockedIO, times(1)).output("Welcome, App started");
    }

    @Test
    public void shouldShowBookList() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        bibliotecaApp.listRepository(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        bibliotecaApp.listBooksWithAllAttributes(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldShowListBooksOfOptions() throws Exception {
        bibliotecaApp.addOption("[LB] List Books");

        bibliotecaApp.showOptions();

        verify(mockedIO, times(1)).output("[LB] List Books");
    }

    @Test
    public void shouldSelectOneOptionWhenTypeKeyword() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("LB");

        assertTrue( bibliotecaApp.selectOption());
        verify(mockedIO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldHintWhenSelectAInvalidOption() throws Exception {
        when(mockedIO.input()).thenReturn("wrongSelection");

        assertTrue(bibliotecaApp.selectOption());
        verify(mockedIO, times(1)).output("Select a valid option!");
    }

    @Test
    public void shouldKeepingChooseOptionUntilSelectQuit() throws Exception {
        when(mockedIO.input()).thenReturn("QUIT");
        bibliotecaApp.keepCycle();

        verify(mockedIO, times(1)).output("Over!");
    }

    @Test
    public void testMockedIOCouldMockTerminalInputCorrectly() throws Exception {
        when(mockedIO.input()).thenReturn("right");
        assertEquals("right", mockedIO.input());
    }

    @Test
    public void shouldDisplayCheckOutOption() throws Exception {
        bibliotecaApp.addOption("[LB] List Books");
        bibliotecaApp.addOption("[CB] Checkout One Book");

        bibliotecaApp.showOptions();

        verify(mockedIO, times(1)).output("[LB] List Books");
        verify(mockedIO, times(1)).output("[CB] Checkout One Book");
    }

    @Test
    public void shouldNotShowCheckedBook() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);
        bibliotecaApp.addOption("[LB] List Books");
        bibliotecaApp.addOption("[CB] Checkout One Book");


        bibliotecaApp.listRepository(bibliotecaApp.bookList);
        when(mockedIO.input()).thenReturn("myBook");
        assertTrue(bibliotecaApp.checkoutOneBook());
        bibliotecaApp.listRepository(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldKeepCheckedBooksInCheckedBookList() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneBook();
        bibliotecaApp.listRepository(bibliotecaApp.checkedBookList);

        assertTrue(checkOutResult);
        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldPrintSuccessfulMessageWhenCheckOutOneBookSuccessfully() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneBook();

        assertTrue(checkOutResult);
        verify(mockedIO, times(1)).output("Thank you! Enjoy the book");
    }

    @Test
    public void shouldPrintErrorMessageWhenCheckoutBookFailed() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("wrongName");
        boolean checkOutResult = bibliotecaApp.checkoutOneBook();

        assertFalse(checkOutResult);
        verify(mockedIO, times(1)).output("That book is not available");
    }

    @Test
    public void shouldAddReturnedBookInBookListAndRemoveFromCheckedBookListWhenBookExistInCheckedBookList() throws Exception {
        Book bookInList = new Book("BookInList", "author", "1");
        Book bookInCheckedList = new Book("BookInList", "author", "1");

        bibliotecaApp.addBook(bookInList, bibliotecaApp.bookList);
        bibliotecaApp.addBook(bookInCheckedList, bibliotecaApp.checkedBookList);

        when(mockedIO.input()).thenReturn("BookInList");
        assertTrue(bibliotecaApp.returnBook());
        assertTrue(bibliotecaApp.bookList.contains(bookInCheckedList));
        assertFalse(bibliotecaApp.checkedBookList.contains(bookInCheckedList));
    }

    @Test
    public void shouldDisableSuccessfulMessageWhenBookIsValid() throws Exception {
        Book bookInCheckedList = new Book("BookInList", "author", "1");
        bibliotecaApp.addBook(bookInCheckedList, bibliotecaApp.checkedBookList);

        when(mockedIO.input()).thenReturn("BookInList");
        assertTrue(bibliotecaApp.returnBook());

        verify(mockedIO, times(1)).output("Thank you for returning the book.");
    }


    @Test
    public void shouldDisableFailMessageWhenBookIsInvalid() throws Exception {
        when(mockedIO.input()).thenReturn("wrongName");
        assertFalse(bibliotecaApp.returnBook());

        verify(mockedIO, times(1)).output("That is not a valid book to return.");
    }

//    List Movies -
//    As a customer, I would like to see a list of available movies, so that I can browse for a movie that I might check-out.
//    Movies have a name, year, director and movie rating (from 1-10 or unrated).

    @Test
    public void shouldShowAListOfMovies() throws Exception {
        bibliotecaApp.addMovie(new Movie("MV1", "1", "Sli", "10"), bibliotecaApp.movieList);
        bibliotecaApp.addMovie(new Movie("MV2", "2", "Sli", "1"), bibliotecaApp.movieList);

        bibliotecaApp.listMovies(bibliotecaApp.movieList);

        verify(mockedIO, times(1)).output("MV1");
        verify(mockedIO, times(1)).output("MV2");
    }

    @Test
    public void shouldShowAListOfMoviesWithAttributes() throws Exception {
        bibliotecaApp.addMovie(new Movie("MV1", "1", "Sli", "10"), bibliotecaApp.movieList);
        bibliotecaApp.addMovie(new Movie("MV2", "2", "Sli", "1"), bibliotecaApp.movieList);

        bibliotecaApp.listMoviesWithAllAttributes(bibliotecaApp.movieList);

        verify(mockedIO, times(1)).output("MV1\t1\tSli\t10");
        verify(mockedIO, times(1)).output("MV2\t2\tSli\t1");
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsValid() throws Exception {
        Movie movie = new Movie("MV1", "1", "Sli", "10");
        bibliotecaApp.addMovie(movie, bibliotecaApp.movieList);

        when(mockedIO.input()).thenReturn("MV1");
        boolean checkoutOneMovie = bibliotecaApp.checkoutOneMovie();


        assertThat(checkoutOneMovie, is(true));
        verify(mockedIO, times(1)).output("Thank you! Enjoy the movie.");
        assertThat(bibliotecaApp.queryOneMovie("MV1", bibliotecaApp.checkedMovieList), is(movie));
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsInvalid() throws Exception {
        Movie movie = new Movie("MV1", "1", "Sli", "10");
        bibliotecaApp.addMovie(movie, bibliotecaApp.movieList);

        when(mockedIO.input()).thenReturn("WrongName");
        boolean checkoutOneMovie = bibliotecaApp.checkoutOneMovie();

        assertThat(checkoutOneMovie, is(Boolean.FALSE));
        verify(mockedIO, times(1)).output("That movie is not available.");
        assertThat(bibliotecaApp.queryOneMovie("MV1", bibliotecaApp.checkedMovieList) == null, is(true));
    }

//    User Accounts - Login -
//    As a librarian, I want to know who has checked out a book, so that I can hold them accountable for returning it.
//    Users must now login using their library number (which is in the format xxx-xxxx) and a password in order to
//    check-out and return books. User credentials are predefined, so registering new users is not part of this story.


    @Test
    public void shouldLoginSuccessfulWhenUseValidLibraryNumberAndPassword() throws Exception {
        when(mockedIO.input()).thenReturn("123-4567","admin");

        boolean loginResult = bibliotecaApp.login();

        assertThat(loginResult, is(true));
    }

    @Test
    public void shouldVerifyUserBeforeCheckingOutBook() throws Exception {

    }
}
