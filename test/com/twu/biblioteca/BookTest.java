package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookTest {
    @Test
    public void shouldHaveRightAttributeList() throws Exception {
        Book book = new Book("myBook");
        assertEquals("myBook", book.getName());
    }
}
