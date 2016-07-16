package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BookTest {
    @Test
    public void shouldReturnRightName() throws Exception {
        Book book = new Book("myBook", "Sli", "2016");
        assertEquals("myBook", book.getName());
    }

    @Test
    public void shouldReturnAuthorAndPublicDate() throws Exception {
        Book book = new Book("myBook", "Sli", "2016");
        assertEquals("myBook\tSli\t2016", book.getAll());
    }
}
