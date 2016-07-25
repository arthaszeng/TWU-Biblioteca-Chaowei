package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ResourceManagerTest {
    ResourceManager resourceManager;

    @Before
    public void setUp() throws Exception {
        resourceManager = new ResourceManager();
        resourceManager.addResource(new Book("bookInBookList", "author", "1999"), resourceManager.bookList);
        resourceManager.addResource(new Book("bookInCheckedBookList", "author", "1999"), resourceManager.checkedBookList);
        resourceManager.addResource(new Book("movieInMovieList", "author", "1999"), resourceManager.movieList);
        resourceManager.addResource(new Book("movieInCheckedMovieList", "author", "1999"), resourceManager.checkedMovieList);
    }

    @Test
    public void shouldReturnMatchedResourcesList() throws Exception {
        List<Resource> bookList = resourceManager.fetchResources("Book");
        List<Resource> checkedBookList = resourceManager.fetchResources("CheckedBook");
        List<Resource> movieList = resourceManager.fetchResources("movie");
        List<Resource> checkedMovieList = resourceManager.fetchResources("checkedMovie");

        assertThat(bookList.get(0).getName(), is("bookInBookList"));
        assertThat(checkedBookList.get(0).getName(), is("bookInCheckedBookList"));
        assertThat(movieList.get(0).getName(), is("movieInMovieList"));
        assertThat(checkedMovieList.get(0).getName(), is("movieInCheckedMovieList"));
    }

}
