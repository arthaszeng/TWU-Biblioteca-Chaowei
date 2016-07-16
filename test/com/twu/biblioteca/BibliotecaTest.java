package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        bibliotecaApp.listBooks();
        verify(mockedIO, times(1)).output("myBook");
    }

    @Test
    public void shouldShowBookListWithAllAttributes() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("myBook", "Sli", "2016"));
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, new ArrayList<>(), mockedIO);
        bibliotecaApp.listBooksWithAllAttributes();
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
        bookList.add(new Book ("myBook", "Sli", "2016"));
        optionList.add("[LB] List Books");
        BibliotecaApp  bibliotecaApp = new BibliotecaApp(bookList, optionList, mockedIO);
        when(mockedIO.input()).thenReturn("wrongSelection");
        assertFalse( bibliotecaApp.selectOption());
        verify(mockedIO, times(1)).output("Select a valid option!");
        verify(mockedIO, times(2)).output("[LB] List Books");
    }


}
