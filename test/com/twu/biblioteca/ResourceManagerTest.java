package com.twu.biblioteca;

import com.twu.biblioteca.Resource.Book;
import com.twu.biblioteca.Resource.Resource;
import com.twu.biblioteca.Resource.ResourceManager;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceManagerTest {
    private ResourceManager resourceManager;

    @Before
    public void setUp() throws Exception {
        resourceManager = new ResourceManager();
        resourceManager.addResource(new Book("bookInBookList", "author", "1999"), "BOOK");
        resourceManager.addResource(new Book("bookInCheckedBookList", "author", "1999"), "CHECKEDBOOK");
        resourceManager.addResource(new Book("movieInMovieList", "author", "1999"), "MOVIE");
        resourceManager.addResource(new Book("movieInCheckedMovieList", "author", "1999"), "CHECKEDMOVIE");
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

    @Test
    public void shouldCheckedOutOneBookWhenInputIsValid() throws Exception {
        boolean result = resourceManager.checkOutOneResource("bookInBookList", "book", "123-4567");

        assertThat(result, is(true));
        List<Resource> resourceList = resourceManager.fetchResources("checkedbook");
        assertThat(resourceList.get(resourceList.size() - 1).getName(), is("bookInBookList"));
    }

    @Test
    public void shouldNotCheckedOutOneBookWhenInputBookNameIsNotExists() throws Exception {
        boolean result = resourceManager.checkOutOneResource("wrongName", "book", "123-4567");

        assertThat(result, is(false));
        assertThat(resourceManager.fetchResources("checkedbook").size(), is(1));
    }

    @Test
    public void shouldCheckedOutOneMovieWhenInputIsValid() throws Exception {
        boolean result = resourceManager.checkOutOneResource("movieInMovieList", "movie", "123-4567");

        assertThat(result, is(true));
        List<Resource> resourceList = resourceManager.fetchResources("checkedMovie");
        assertThat(resourceList.get(resourceList.size() - 1).getName(), is("movieInMovieList"));
    }

    @Test
    public void shouldNotCheckedOutOneMovieWhenInputMovieNameIsNotExists() throws Exception {
        boolean result = resourceManager.checkOutOneResource("wrongName", "movie", "123-4567");

        assertThat(result, is(false));
        assertThat(resourceManager.fetchResources("checkedMovie").size(), is(1));
    }

    @Test
    public void shouldReturnTrueWhenQueriedOneResourceIsExist() throws Exception {
        boolean result1 = resourceManager.queryOneResource("bookInBookList", "book");
        boolean result2 = resourceManager.queryOneResource("bookInCheckedBookList", "checkedBook");
        boolean result3 = resourceManager.queryOneResource("movieInMovieList", "movie");
        boolean result4 = resourceManager.queryOneResource("movieInCheckedMovieList", "checkedMovie");

        assertThat(result1, is(true));
        assertThat(result2, is(true));
        assertThat(result3, is(true));
        assertThat(result4, is(true));
    }

    @Test
    public void shouldReturnFalseWhenQueriedOneResourceIsNotExist() throws Exception {
        boolean result1 = resourceManager.queryOneResource("wrongName", "book");
        boolean result2 = resourceManager.queryOneResource("wrongName", "checkedBook");
        boolean result3 = resourceManager.queryOneResource("wrongName", "movie");
        boolean result4 = resourceManager.queryOneResource("wrongName", "checkedMovie");

        assertThat(result1, is(false));
        assertThat(result2, is(false));
        assertThat(result3, is(false));
        assertThat(result4, is(false));
    }

    @Test
    public void shouldMoveResourceToResourceListFromCheckedResourceListWhenReturnOneReturnSuccessfully() throws Exception {
        assertThat(resourceManager.queryOneResource("bookInCheckedBookList", "BOOK"), is(false));
        assertThat(resourceManager.queryOneResource("bookInCheckedBookList", "CHECKEDBOOK"), is(true));

        boolean result = resourceManager.returnOneResource("bookInCheckedBookList", "BOOK");

        assertThat(result, is(true));
        assertThat(resourceManager.queryOneResource("bookInCheckedBookList", "BOOK"), is(true));
        assertThat(resourceManager.queryOneResource("bookInCheckedBookList", "CHECKEDBOOK"), is(false));
    }
}
