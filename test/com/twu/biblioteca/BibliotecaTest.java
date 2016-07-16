package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class BibliotecaTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private MockedIO mockedIO = mock(MockedIO.class);

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldShowWelcomeWhenAppStart() throws Exception {
        BibliotecaApp bibliotecaApp = new BibliotecaApp(new ArrayList<>(), new ArrayList<>(), new MockedIO());
        bibliotecaApp.showWelcome();
        assertEquals("Welcome, App started\n", outputStream.toString());
    }

    @Test
    public void shouldShowBookList() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("myBook", "Sli", "2016"));
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, new ArrayList<>(), mockedIO);
        bibliotecaApp.listBooks(bibliotecaApp.bookList);
        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("myBook", "Sli", "2016"));
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, new ArrayList<>(), mockedIO);
        bibliotecaApp.listBooksWithAllAttributes(bookList);
        verify(mockedIO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldShowListBooksOfOptions() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        optionList.add("[LB] List Books");
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);
        bibliotecaApp.showOptions();
        verify(mockedIO, times(1)).output("[LB] List Books");
    }

    @Test
    public void shouldSelectOneOptionWhenTypeKeyword() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        bookList.add(new Book ("myBook", "Sli", "2016"));
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);
        when(mockedIO.input()).thenReturn("LB");
        assertTrue( bibliotecaApp.selectOption());
        verify(mockedIO, times(1)).output("myBook\tSli\t2016");
    }

    @Test
    public void shouldHintWhenSelectAInvalidOption() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);
        when(mockedIO.input()).thenReturn("wrongSelection");
        assertTrue(bibliotecaApp.selectOption());
        verify(mockedIO, times(1)).output("Select a valid option!");
    }

    @Test
    public void shouldKeepingChooseOptionUntilSelectQuit() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);
        when(mockedIO.input()).thenReturn("QUIT");
        bibliotecaApp.keepCycle();
        verify(mockedIO, times(1)).output("Over!");
    }

    @Test
    public void testMockedIOCouldMockTerminalInputCorectly() throws Exception {
        when(mockedIO.input()).thenReturn("right");
        assertEquals("right", mockedIO.input());
    }

    @Test
    public void shouldDisplayCheckOutOption() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        optionList.add("[LB] List Books");
        optionList.add("[CB] Checkout One Book");
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);

        bibliotecaApp.showOptions();

        verify(mockedIO, times(1)).output("[LB] List Books");
        verify(mockedIO, times(1)).output("[CB] Checkout One Book");
    }

    @Test
    public void shouldNotShowCheckedBook() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        bookList.add(new Book("myBook", "Sli", "2016"));
        optionList.add("[LB] List Books");
        optionList.add("[CB] Checkout One Book");
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);

        bibliotecaApp.listBooks(bibliotecaApp.bookList);
        when(mockedIO.input()).thenReturn("myBook");
        assertTrue(bibliotecaApp.checkoutOneBook());
        bibliotecaApp.listBooks(bibliotecaApp.bookList);

        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldKeepCheckedBooksInCheckedBookList() throws Exception {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        bookList.add(new Book("myBook", "Sli", "2016"));
        optionList.add("[LB] List Books");
        optionList.add("[CB] Checkout One Book");
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, new ArrayList<>(), optionList, mockedIO);

        when(mockedIO.input()).thenReturn("myBook");
        boolean checkOutResult = bibliotecaApp.checkoutOneBook();
        bibliotecaApp.listBooks(bibliotecaApp.checkBookList);

        assertTrue(checkOutResult);
        verify(mockedIO, times(1)).output("myBook");
    }



}
