package com.twu.biblioteca;

import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MovieTest {
    @Test
    public void testMoviesRatingCanNotLittleThan1() throws Exception {
        Movie movie = new Movie("movie", "1", "Sli", "0");

        assertThat(movie.getRating(), is("unrated"));
    }

    @Test
    public void testMoviesRatingCanNotBiggerThan10() throws Exception {
        Movie movie = new Movie("movie", "1", "Sli", "11");

        assertThat(movie.getRating(), is("unrated"));
    }
}
