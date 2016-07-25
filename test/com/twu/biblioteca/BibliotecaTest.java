package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



public class BibliotecaTest {
    private BibliotecaApp bibliotecaApp;
    private MockedIO mockedIO = mock(MockedIO.class);
    private CustomerDataManager customerDataManager = new CustomerDataManager();

    @Before
    public void setUp() throws Exception {
        bibliotecaApp = new BibliotecaApp(mockedIO, customerDataManager);

        when(mockedIO.input()).thenReturn("test", "test");
        this.bibliotecaApp.login();
    }

    @Test
    public void shouldShowWelcomeWhenAppStart() throws Exception {
        bibliotecaApp.showWelcome();
        verify(mockedIO, times(1)).output("Welcome, App started");
    }

    @Test
    public void shouldShowEmptyBookList() throws Exception {
        bibliotecaApp.listRepository(bibliotecaApp.bookList);
        verify(mockedIO, times(2)).output(anyString());
    }

    @Test
    public void shouldShowBookList() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        bibliotecaApp.listRepository(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        bibliotecaApp.listRepositoryWithAllAttributes(bibliotecaApp.bookList);

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
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("LB");

        boolean result = bibliotecaApp.selectOption();

        assertThat(result, is(true));
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
    public void shouldNotShowCheckedBookWhenCheckOutSuccessfully() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkoutResult = bibliotecaApp.checkoutOneResource("Book");

        assertThat(checkoutResult, is(true));
        assertThat(bibliotecaApp.queryOneResource("myBook", bibliotecaApp.bookList) == null, is(true));
    }

    @Test
    public void shouldKeepCheckedBooksInCheckedBookList() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneResource("Book");

        assertThat(checkOutResult, is(true));
        assertThat(bibliotecaApp.queryOneResource("myBook", bibliotecaApp.checkedBookList) != null, is(true));
    }

    @Test
    public void shouldPrintSuccessfulMessageWhenCheckOutOneBookSuccessfully() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneResource("Book");

        assertThat(checkOutResult, is(true));
        verify(mockedIO, times(1)).output("Thank you! Enjoy the book.");
    }

    @Test
    public void shouldPrintErrorMessageWhenCheckoutBookFailed() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("wrongName");
        boolean checkOutResult = bibliotecaApp.checkoutOneResource("Book");

        assertThat(checkOutResult, is(false));
        verify(mockedIO, times(1)).output("That book is not available.");
    }

    @Test
    public void shouldAddReturnedBookInBookListAndRemoveFromCheckedBookListWhenBookExistInCheckedBookList() throws Exception {
        Book bookInList = new Book("BookInList", "author", "1");
        Book bookInCheckedList = new Book("BookInList", "author", "1");

        bibliotecaApp.addResource(bookInList, bibliotecaApp.bookList);
        bibliotecaApp.addResource(bookInCheckedList, bibliotecaApp.checkedBookList);

        when(mockedIO.input()).thenReturn("BookInList");
        assertThat(bibliotecaApp.returnOneResource("book"), is(true));
        assertThat(bibliotecaApp.bookList.contains(bookInCheckedList), is(true));
        assertThat(bibliotecaApp.checkedBookList.contains(bookInCheckedList), is(false));
    }

    @Test
    public void shouldDisableSuccessfulMessageWhenBookIsValid() throws Exception {
        Book bookInCheckedList = new Book("BookInList", "author", "1");
        bibliotecaApp.addResource(bookInCheckedList, bibliotecaApp.checkedBookList);

        when(mockedIO.input()).thenReturn("BookInList");
        assertThat(bibliotecaApp.returnOneResource("book"), is(true));

        verify(mockedIO, times(1)).output("Thank you for returning the book.");
    }


    @Test
    public void shouldDisableFailMessageWhenBookIsInvalid() throws Exception {
        when(mockedIO.input()).thenReturn("wrongName");
        assertThat(bibliotecaApp.returnOneResource("book"), is(false));

        verify(mockedIO, times(1)).output("That is not a valid book to return.");
    }

//    List Movies -
//    As a customer, I would like to see a list of available movies, so that I can browse for a movie that I might check-out.
//    Movies have a name, year, director and movie rating (from 1-10 or unrated).

    @Test
    public void shouldShowAListOfMovies() throws Exception {
        bibliotecaApp.addResource(new Movie("MV1", "1", "Sli", "10"), bibliotecaApp.movieList);
        bibliotecaApp.addResource(new Movie("MV2", "2", "Sli", "1"), bibliotecaApp.movieList);

        bibliotecaApp.listRepository(bibliotecaApp.movieList);

        verify(mockedIO, times(1)).output("MV1");
        verify(mockedIO, times(1)).output("MV2");
    }

    @Test
    public void shouldShowAListOfMoviesWithAttributes() throws Exception {
        bibliotecaApp.addResource(new Movie("MV1", "1", "Sli", "10"), bibliotecaApp.movieList);
        bibliotecaApp.addResource(new Movie("MV2", "2", "Sli", "1"), bibliotecaApp.movieList);

        bibliotecaApp.listRepositoryWithAllAttributes(bibliotecaApp.movieList);

        verify(mockedIO, times(1)).output("MV1\t1\tSli\t10");
        verify(mockedIO, times(1)).output("MV2\t2\tSli\t1");
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsValid() throws Exception {
        Movie movie = new Movie("MV1", "1", "Sli", "10");
        bibliotecaApp.addResource(movie, bibliotecaApp.movieList);

        when(mockedIO.input()).thenReturn("MV1");
        boolean checkoutOneMovie = bibliotecaApp.checkoutOneResource("movie");


        assertThat(checkoutOneMovie, is(true));
        verify(mockedIO, times(1)).output("Thank you! Enjoy the movie.");
        assertThat(bibliotecaApp.queryOneResource("MV1", bibliotecaApp.checkedMovieList), is(movie));
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsInvalid() throws Exception {
        Movie movie = new Movie("MV1", "1", "Sli", "10");
        bibliotecaApp.addResource(movie, bibliotecaApp.movieList);

        when(mockedIO.input()).thenReturn("WrongName");
        boolean checkoutOneMovie = bibliotecaApp.checkoutOneResource("movie");

        assertThat(checkoutOneMovie, is(Boolean.FALSE));
        verify(mockedIO, times(1)).output("That movie is not available.");
        assertThat(bibliotecaApp.queryOneResource("MV1", bibliotecaApp.checkedMovieList) == null, is(true));
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
    public void shouldKeepUserInformationInWhenUserLoginSuccessful() throws Exception {
        when(mockedIO.input()).thenReturn("123-4567","admin");
        bibliotecaApp.login();

        assertThat(bibliotecaApp.getCurrentUser().getLibraryNumber(), is("123-4567"));
    }

    @Test
    public void shouldRemoteLibraryNumberToCheckedOutResource() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Author", "1999"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("123-4567", "admin", "myBook");
        bibliotecaApp.login();
        boolean checkOutResult = bibliotecaApp.checkoutOneResource("book");

        assertThat(checkOutResult, is(true));
        assertThat(bibliotecaApp.checkedBookList.get(0).getHolder(), is("123-4567"));
    }

    @Test
    public void shouldNotCheckOutAnyResourceWhenLoginFailedOrHaveNotLogin() throws Exception {
        bibliotecaApp.logOut();
        bibliotecaApp.addResource(new Book("myBook", "Author", "1999"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneResource("book");

        assertThat(checkOutResult, is(false));
    }

    @Test
    public void shouldShowUsersInformationWhenLoginSuccessful() throws Exception {
        boolean showProfileResult = bibliotecaApp.showProfile();

        assertThat(showProfileResult, is(true));
        verify(mockedIO, times(1)).output("test\ttest\ttest");
    }
}
