package com.twu.biblioteca;

import com.twu.biblioteca.Resource.Book;
import com.twu.biblioteca.Resource.Movie;
import com.twu.biblioteca.Resource.ResourceManager;
import com.twu.biblioteca.User.UserDataManager;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class BibliotecaTest {
    private BibliotecaApp bibliotecaApp;
    private MockedIO mockedIO = mock(MockedIO.class);
    private UserDataManager userDataManager = new UserDataManager();
    private ResourceManager resourceManager = new ResourceManager();
    @Before
    public void setUp() throws Exception {
        bibliotecaApp = new BibliotecaApp(mockedIO, userDataManager, resourceManager);
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
        bibliotecaApp.listRepository("book");
        verify(mockedIO, times(2)).output(anyString());
    }

    @Test
    public void shouldShowBookList() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        bibliotecaApp.listRepository("book");
        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        bibliotecaApp.listRepositoryWithAllAttributes("book");

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
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        when(mockedIO.input()).thenReturn("LB");

        boolean result = bibliotecaApp.selectOperation();

        assertThat(result, is(true));
        verify(mockedIO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldHintWhenSelectAInvalidOption() throws Exception {
        when(mockedIO.input()).thenReturn("wrongSelection");

//        assertThat(bibliotecaApp.selectOperation(), is(true));
//        verify(mockedIO, times(1)).output("Select a valid option!");
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
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        bibliotecaApp.listRepository("book");    //first time to list books, should display myBook
        when(mockedIO.input()).thenReturn("myBook");
        boolean checkoutResult = bibliotecaApp.checkOutOneResource("Book");
        bibliotecaApp.listRepository("book");      //second time to list books, should not display myBook

        assertThat(checkoutResult, is(true));
        verify(mockedIO, times(1)).output("myBook");     //verify that should not display checked book
    }

    @Test
    public void shouldStillShowTheBookWhenCheckOutFailed() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        bibliotecaApp.listRepository("book");    //first time to list books, should display myBook
        when(mockedIO.input()).thenReturn("wrongName");
        boolean checkoutResult = bibliotecaApp.checkOutOneResource("Book");
        bibliotecaApp.listRepository("book");      //second time to list books, should not display myBook

        assertThat(checkoutResult, is(false));
        verify(mockedIO, times(2)).output("myBook");     //verify that should not display checked book
    }


    @Test
    public void shouldKeepCheckedBooksInCheckedBookListWhenCheckOutSuccessfully() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkOutOneResource("Book");

        assertThat(checkOutResult, is(true));
        assertThat(bibliotecaApp.queryOneResource("myBook", "checkedBook"), is(true));
    }


    @Test
    public void shouldNotKeepCheckedBooksInCheckedBookListWhenCheckOutFailed() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "book");

        when(mockedIO.input()).thenReturn("wrongName");
        boolean checkOutResult = bibliotecaApp.checkOutOneResource("Book");

        assertThat(checkOutResult, is(false));
        assertThat(bibliotecaApp.queryOneResource("myBook", "checkedBook"), is(false));
    }

    @Test
    public void shouldPrintSuccessfulMessageWhenCheckOutOneBookSuccessfully() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "BOOK");

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkOutOneResource("BOOK");

        assertThat(checkOutResult, is(true));
        verify(mockedIO, times(1)).output("Thank you! Enjoy the book.");
    }

    @Test
    public void shouldPrintErrorMessageWhenCheckoutBookFailed() throws Exception {
        bibliotecaApp.addResource(new Book("myBook", "Sli", "2016"), "BOOK");

        when(mockedIO.input()).thenReturn("wrongName");
        boolean checkOutResult = bibliotecaApp.checkOutOneResource("BOOK");

        assertThat(checkOutResult, is(false));
        verify(mockedIO, times(1)).output("That book is not available.");
    }


    @Test
    public void shouldDisableSuccessfulMessageWhenReturnBookSuccessfully() throws Exception {
        bibliotecaApp.addResource(new Book("BookInCheckedBookList", "author", "1"), "CHECKEDBOOK");

        when(mockedIO.input()).thenReturn("BookInCheckedBookList");
        boolean returnResourceResult = bibliotecaApp.returnOneResource("BOOK");

        assertThat(returnResourceResult, is(true));
        verify(mockedIO, times(1)).output("Thank you for returning the book.");
    }


    @Test
    public void shouldDisableFailMessageWhenBookIsInvalid() throws Exception {
        when(mockedIO.input()).thenReturn("wrongName");
        assertThat(bibliotecaApp.returnOneResource("book"), is(false));

        verify(mockedIO, times(1)).output("That is not a valid book to return.");
    }

    @Test
    public void shouldShowAListOfMovies() throws Exception {
        bibliotecaApp.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");
        bibliotecaApp.addResource(new Movie("MV2", "2", "Sli", "1"), "MOVIE");

        bibliotecaApp.listRepository("MOVIE");

        verify(mockedIO, times(1)).output("MV1");
        verify(mockedIO, times(1)).output("MV2");
    }

    @Test
    public void shouldShowAListOfMoviesWithAttributes() throws Exception {
        bibliotecaApp.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");
        bibliotecaApp.addResource(new Movie("MV2", "2", "Sli", "1"), "MOVIE");

        bibliotecaApp.listRepositoryWithAllAttributes("MOVIE");

        verify(mockedIO, times(1)).output("MV1\t1\tSli\t10");
        verify(mockedIO, times(1)).output("MV2\t2\tSli\t1");
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsValid() throws Exception {
        bibliotecaApp.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");

        when(mockedIO.input()).thenReturn("MV1");
        boolean checkoutOneMovie = bibliotecaApp.checkOutOneResource("MOVIE");

        assertThat(checkoutOneMovie, is(true));
        verify(mockedIO, times(1)).output("Thank you! Enjoy the movie.");
        assertThat(bibliotecaApp.queryOneResource("MV1", "CHECKEDMOVIE"), is(true));
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsInvalid() throws Exception {
        bibliotecaApp.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");

        when(mockedIO.input()).thenReturn("WrongName");
        boolean checkoutOneMovie = bibliotecaApp.checkOutOneResource("movie");

        assertThat(checkoutOneMovie, is(Boolean.FALSE));
        verify(mockedIO, times(1)).output("That movie is not available.");
        assertThat(bibliotecaApp.queryOneResource("MV1", "CHECKEDMOVIE"), is(false));
    }
//
////    User Accounts - Login -
////    As a librarian, I want to know who has checked out a book, so that I can hold them accountable for returning it.
////    Users must now login using their library number (which is in the format xxx-xxxx) and a password in order to
////    check-out and return books. User credentials are predefined, so registering new users is not part of this story.


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
    public void shouldNotCheckOutAnyResourceWhenLoginFailedOrHaveNotLogin() throws Exception {
        bibliotecaApp.logOut();
        bibliotecaApp.addResource(new Book("myBook", "Author", "1999"), "BOOK");

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkOutOneResource("BOOK");

        assertThat(checkOutResult, is(false));
    }

    @Test
    public void shouldShowUsersInformationWhenLoginSuccessful() throws Exception {
        boolean showProfileResult = bibliotecaApp.showProfile();

        assertThat(showProfileResult, is(true));
        verify(mockedIO, times(1)).output("test\ttest\ttest");
    }
}
