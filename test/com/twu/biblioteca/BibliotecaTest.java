package com.twu.biblioteca;

import com.twu.biblioteca.resource.Book;
import com.twu.biblioteca.resource.Movie;
import com.twu.biblioteca.resource.ResourceManager;
import com.twu.biblioteca.user.UserDataManager;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class BibliotecaTest {
    private Biblioteca biblioteca;
    private IO IO = mock(IO.class);
    private UserDataManager userDataManager = new UserDataManager();
    private ResourceManager resourceManager = new ResourceManager();
    @Before
    public void setUp() throws Exception {
        biblioteca = new Biblioteca(IO, userDataManager, resourceManager);
        when(IO.input()).thenReturn("test", "test");
        this.biblioteca.login();
    }

    @Test
    public void shouldShowWelcomeWhenAppStart() throws Exception {
        biblioteca.showWelcome();
        verify(IO, times(1)).output("Welcome, App started");
    }

    @Test
    public void shouldShowEmptyBookList() throws Exception {
        biblioteca.listRepository("book");
        verify(IO, times(2)).output(anyString());
    }

    @Test
    public void shouldShowBookList() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        biblioteca.listRepository("book");
        verify(IO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        biblioteca.listRepositoryWithAllAttributes("book");

        verify(IO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldShowListBooksOfOptions() throws Exception {
        biblioteca.addOption("[LB] List Books");

        biblioteca.showOptions();

        verify(IO, times(1)).output("[LB] List Books");
    }

    @Test
    public void shouldSelectOneOptionWhenTypeKeyword() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        when(IO.input()).thenReturn("LB");

        boolean result = biblioteca.selectOperation();

        assertThat(result, is(true));
        verify(IO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldHintWhenSelectAInvalidOption() throws Exception {
        when(IO.input()).thenReturn("wrongSelection");

        assertThat(biblioteca.selectOperation(), is(false));
    }

    @Test
    public void shouldKeepingChooseOptionUntilSelectQuit() throws Exception {
        when(IO.input()).thenReturn("QUIT");
        biblioteca.keepCycle();

        verify(IO, times(1)).output("Over!");
    }

    @Test
    public void testMockedIOCouldMockTerminalInputCorrectly() throws Exception {
        when(IO.input()).thenReturn("right");

        assertEquals("right", IO.input());
    }

    @Test
    public void shouldDisplayCheckOutOption() throws Exception {
        biblioteca.addOption("[LB] List Books");
        biblioteca.addOption("[CB] Checkout One Book");

        biblioteca.showOptions();

        verify(IO, times(1)).output("[LB] List Books");
        verify(IO, times(1)).output("[CB] Checkout One Book");
    }

    @Test
    public void shouldNotShowCheckedBookWhenCheckOutSuccessfully() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        biblioteca.listRepository("book");    //first time to list books, should display myBook
        when(IO.input()).thenReturn("myBook");
        boolean checkoutResult = biblioteca.checkOutOneResource("Book");
        biblioteca.listRepository("book");      //second time to list books, should not display myBook

        assertThat(checkoutResult, is(true));
        verify(IO, times(1)).output("myBook");     //verify that should not display checked book
    }

    @Test
    public void shouldStillShowTheBookWhenCheckOutFailed() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        biblioteca.listRepository("book");    //first time to list books, should display myBook
        when(IO.input()).thenReturn("wrongName");
        boolean checkoutResult = biblioteca.checkOutOneResource("Book");
        biblioteca.listRepository("book");      //second time to list books, should not display myBook

        assertThat(checkoutResult, is(false));
        verify(IO, times(2)).output("myBook");     //verify that should not display checked book
    }


    @Test
    public void shouldKeepCheckedBooksInCheckedBookListWhenCheckOutSuccessfully() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        when(IO.input()).thenReturn("myBook");
        boolean checkOutResult = biblioteca.checkOutOneResource("Book");

        assertThat(checkOutResult, is(true));
        assertThat(biblioteca.queryOneResource("myBook", "checkedBook"), is(true));
    }


    @Test
    public void shouldNotKeepCheckedBooksInCheckedBookListWhenCheckOutFailed() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "book");

        when(IO.input()).thenReturn("wrongName");
        boolean checkOutResult = biblioteca.checkOutOneResource("Book");

        assertThat(checkOutResult, is(false));
        assertThat(biblioteca.queryOneResource("myBook", "checkedBook"), is(false));
    }

    @Test
    public void shouldPrintSuccessfulMessageWhenCheckOutOneBookSuccessfully() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "BOOK");

        when(IO.input()).thenReturn("myBook");
        boolean checkOutResult = biblioteca.checkOutOneResource("BOOK");

        assertThat(checkOutResult, is(true));
        verify(IO, times(1)).output("Thank you! Enjoy the book.");
    }

    @Test
    public void shouldPrintErrorMessageWhenCheckoutBookFailed() throws Exception {
        biblioteca.addResource(new Book("myBook", "Sli", "2016"), "BOOK");

        when(IO.input()).thenReturn("wrongName");
        boolean checkOutResult = biblioteca.checkOutOneResource("BOOK");

        assertThat(checkOutResult, is(false));
        verify(IO, times(1)).output("That book is not available.");
    }


    @Test
    public void shouldDisableSuccessfulMessageWhenReturnBookSuccessfully() throws Exception {
        biblioteca.addResource(new Book("BookInCheckedBookList", "author", "1"), "CHECKEDBOOK");

        when(IO.input()).thenReturn("BookInCheckedBookList");
        boolean returnResourceResult = biblioteca.returnOneResource("BOOK");

        assertThat(returnResourceResult, is(true));
        verify(IO, times(1)).output("Thank you for returning the book.");
    }


    @Test
    public void shouldDisableFailMessageWhenBookIsInvalid() throws Exception {
        when(IO.input()).thenReturn("wrongName");
        assertThat(biblioteca.returnOneResource("book"), is(false));

        verify(IO, times(1)).output("That is not a valid book to return.");
    }

    @Test
    public void shouldShowAListOfMovies() throws Exception {
        biblioteca.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");
        biblioteca.addResource(new Movie("MV2", "2", "Sli", "1"), "MOVIE");

        biblioteca.listRepository("MOVIE");

        verify(IO, times(1)).output("MV1");
        verify(IO, times(1)).output("MV2");
    }

    @Test
    public void shouldShowAListOfMoviesWithAttributes() throws Exception {
        biblioteca.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");
        biblioteca.addResource(new Movie("MV2", "2", "Sli", "1"), "MOVIE");

        biblioteca.listRepositoryWithAllAttributes("MOVIE");

        verify(IO, times(1)).output("MV1\t1\tSli\t10");
        verify(IO, times(1)).output("MV2\t2\tSli\t1");
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsValid() throws Exception {
        biblioteca.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");

        when(IO.input()).thenReturn("MV1");
        boolean checkoutOneMovie = biblioteca.checkOutOneResource("MOVIE");

        assertThat(checkoutOneMovie, is(true));
        verify(IO, times(1)).output("Thank you! Enjoy the movie.");
        assertThat(biblioteca.queryOneResource("MV1", "CHECKEDMOVIE"), is(true));
    }

    @Test
    public void shouldCheckOutAMovieWhenInputIsInvalid() throws Exception {
        biblioteca.addResource(new Movie("MV1", "1", "Sli", "10"), "MOVIE");

        when(IO.input()).thenReturn("WrongName");
        boolean checkoutOneMovie = biblioteca.checkOutOneResource("movie");

        assertThat(checkoutOneMovie, is(Boolean.FALSE));
        verify(IO, times(1)).output("That movie is not available.");
        assertThat(biblioteca.queryOneResource("MV1", "CHECKEDMOVIE"), is(false));
    }

    @Test
    public void shouldLoginSuccessfulWhenUseValidLibraryNumberAndPassword() throws Exception {
        when(IO.input()).thenReturn("123-4567","admin");

        boolean loginResult = biblioteca.login();

        assertThat(loginResult, is(true));
    }

    @Test
    public void shouldKeepUserInformationInWhenUserLoginSuccessful() throws Exception {
        when(IO.input()).thenReturn("123-4567","admin");
        biblioteca.login();

        assertThat(biblioteca.getCurrentUser().getLibraryNumber(), is("123-4567"));
    }

    @Test
    public void shouldNotCheckOutAnyResourceWhenLoginFailedOrHaveNotLogin() throws Exception {
        biblioteca.logOut();
        biblioteca.addResource(new Book("myBook", "Author", "1999"), "BOOK");

        when(IO.input()).thenReturn("myBook");
        boolean checkOutResult = biblioteca.checkOutOneResource("BOOK");

        assertThat(checkOutResult, is(false));
    }

    @Test
    public void shouldShowUsersInformationWhenLoginSuccessful() throws Exception {
        boolean showProfileResult = biblioteca.showProfile();

        assertThat(showProfileResult, is(true));
        verify(IO, times(1)).output("test\ttest\ttest");
    }
}
