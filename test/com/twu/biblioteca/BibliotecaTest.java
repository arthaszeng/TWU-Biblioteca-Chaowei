package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



public class BibliotecaTest {
    private BibliotecaApp bibliotecaApp;
    private List<Book> bookList;
    private MockedIO mockedIO = mock(MockedIO.class);

    @Before
    public void setUp() throws Exception {
        bookList = new ArrayList<>();
        List<Book> checkedBookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        bibliotecaApp = new BibliotecaApp(bookList, checkedBookList, optionList, mockedIO);
    }

    @Test
    public void shouldShowWelcomeWhenAppStart() throws Exception {
        bibliotecaApp.showWelcome();

        verify(mockedIO, times(1)).output("Welcome, App started");
    }

    @Test
    public void shouldShowBookList() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        bibliotecaApp.listBooks(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        bibliotecaApp.listBooksWithAllAttributes(bookList);

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


        bibliotecaApp.listBooks(bibliotecaApp.bookList);
        when(mockedIO.input()).thenReturn("myBook");
        assertTrue(bibliotecaApp.checkoutOneBook());
        bibliotecaApp.listBooks(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldKeepCheckedBooksInCheckedBookList() throws Exception {
        bibliotecaApp.addBook(new Book("myBook", "Sli", "2016"), bibliotecaApp.bookList);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneBook();
        bibliotecaApp.listBooks(bibliotecaApp.checkedBookList);

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
}
